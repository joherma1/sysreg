#include <OneWire.h>
#include <Wire.h>
#include <FreqCounter.h>
#include <Time.h>
#include <TimeAlarms.h>

#define BMP085_ADDRESS 0x77  // Direccion I2C del sensor BMP085

const int motor1Pin = 3;    // H-bridge leg 1 (pin 2, 1A)
const int motor2Pin = 4;    // H-bridge leg 2 (pin 7, 2A)
const int enable = 2;
int riego;		//0-> No se esta regando, 1 -> Regando
OneWire ds(12); 	    // Pin protocolo 1-wire
int command;
byte sensor_id[8];
int contador = 0;
int present = 0;
byte data[12];
int HighByte, LowByte, TReading, SignBit, Tc_100, Whole, Fract;

//Reloj
time_t pctime = 0;
time_t alarmtime = 0;
char digito;
int n_digitos = 0;
//I2C Pressure Sensor
const unsigned char OSS = 3;  // Oversampling Setting
//0 Ultra low power
//1 Standard
//2 High resolution
//3 Ultra high resolution
// Calibration values
int ac1;
int ac2; 
int ac3; 
unsigned int ac4;
unsigned int ac5;
unsigned int ac6;
int b1; 
int b2;
int mb;
int mc;
int md;
// b5 is calculated in bmp085GetTemperature(...), this variable is also used in bmp085GetPressure(...)
// so ...Temperature(...) must be called before ...Pressure(...).
long b5; 
short temperature;
long pressure;
const float p0 = 101325;     // Pressure at sea level (Pa)
float altitude;
const float currentAltitude = 34; // current altitude in METERS in Alcasser
const float ePressure = p0 * pow((1-currentAltitude/44330), 5.255);  //100917,669916678 = expected pressure (in Pa) at altitude
float weatherDiff;
const int Sunny = 1;
const int Partly_Cloudy = 2;
const int Rain = 3;

//Humedad
long freq, offset, sens; //Si no gastamos longs se trunca la multiplicacion y salen valores no validos


void setup(void){
  //Inicializamos las salidas digitales
  pinMode(motor1Pin, OUTPUT); 
  pinMode(motor2Pin, OUTPUT); 
  pinMode(enable,OUTPUT);
  pinMode(13, OUTPUT);
  pinMode(6,OUTPUT);//Leda riego activo 
  //Inicializamos el puerto de serie
  Serial.begin(9600);
  //Activamos comunicacion I2C 
  Wire.begin();
  //Calibramos el sensor de presion con los valores de fabrica
  bmp085Calibration();
  //Obtenemos los datos para calibrar el sensor de humedad HH10D
  hh10dCalibration();
  //Desactivamos el puente H
  digitalWrite(enable, LOW);  // set leg 0 of the H-bridge high  
  //Marcamos como apagado el  riego
  riego = 0;
  //Inicializacion por conexion
  Serial.print(6, BYTE);//Codigo de inicializacion, ACK
  Serial.print(4, BYTE);//EndOfTransmission (ASCII);
  digitalWrite(6, HIGH);  
  delay(300); 
  digitalWrite(6, LOW);  
}
void loop(void){
  byte i; //Variable para bucles
  byte present;
  byte data[12];
  byte addr[8];//Identificador del sensor
  int count = Serial.available();
  if(count > 0){
    command=Serial.read();
    switch (command){ 
      //Inicializaion por peticion
    case 0x05: //Solicitud de conexion: ENQ
      Serial.print(6, BYTE);//Codigo de inicializacion, ACK
      Serial.print(4,BYTE);//EndOfTransmission (ASCII)
      break;
    case 0x41: //establecerHora: A 65 0x41
      //Recogemos los 10 siguientes carácteres ASCII que será la hora en tiempo UNIX mas 
      delay(40);
      pctime = 0;
      n_digitos = 0;
      for (int cont=0;cont<10;cont++){
        if(Serial.available()>0){
          digito = Serial.read();  
          //Serial.print(digito);  
          //Serial.print("\t");
          if( digito >= '0' && digito <= '9'){   
            pctime = (10 * pctime) + (digito - '0') ; // convert digits to a number
            n_digitos ++;
            //Serial.print(pctime);
            //Serial.print("\n");    
          } 
        }
      }
      if(n_digitos == 10){
        setTime(pctime);   // Sync Arduino clock to the time received on the serial port
        adjustTime(3600); //Tiempo peninsular +1
        if(timeStatus() == timeSet){ 
          //digitalClockDisplay();  
          Serial.print(1,DEC); //return 1
        }
      }
      else{ //No se ha recibido correctamente el tiempo
        Serial.print(-1,DEC); //return -1
      }
      Serial.print(4,BYTE); //señal EOF
      break; 
    case  0x42: //establecerAlarmaON: B 66 0x42
      //Establecemos una alarma en el tiempo indicado: -1 error  AlarmID otro caso
      delay(40);
      alarmtime = 0;
      n_digitos = 0;
      for (int cont=0;cont<10;cont++){
        if(Serial.available()>0){
          digito = Serial.read();  
          if( digito >= '0' && digito <= '9'){   
            alarmtime = (10 * alarmtime) + (digito - '0') ; // convert digits to a number
            n_digitos ++;
          } 
        }
      }
      //Le sumamos 1 hora para estar en la franja horaria GMT +1
      alarmtime += 3600;
      if(n_digitos == 10 && timeStatus() == timeSet  && alarmtime > now()){ //Condiciones para que se pueda establecer la alarma
        //timerOnce ejecuta un trigger _n_ segundos despues, eso sera 
        AlarmID_t a1 = Alarm.timerOnce(alarmtime - now(), alarmaOn);
        if(a1 == dtINVALID_ALARM_ID)
                Serial.print(-1,DEC); //return -1
        Serial.print(a1,DEC); //return 1
      }
      else{ //No se ha recibido correctamente el tiempo
        Serial.print(-2,DEC); //return -1
      }
      Serial.print(4,BYTE); //señal EOF
      break; 
    case 0x64: //activarRiego: d 100 0x64
      riego = 1;
      digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high
      digitalWrite(motor1Pin, LOW);   // set leg 1 of the H-bridge low
      digitalWrite(motor2Pin, HIGH);  // set leg 2 of the H-bridge high
      digitalWrite(6, HIGH);  
      delay(300);
      digitalWrite(enable, LOW);  // set leg 1 of the H-bridge high
      break;
    case 0x65:  //desactivarRiego: e 101 0x65
      riego = 0;
      digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high
      digitalWrite(motor1Pin, HIGH);  // set leg 1 of the H-bridge high
      digitalWrite(motor2Pin, LOW);   // set leg 2 of the H-bridge low
      digitalWrite(6, LOW);  
      delay(300);
      digitalWrite(enable, LOW);  // set leg 1 of the H-bridge high
      break;
    case 0x66:	//comprobarRiego: f 102 0x66
      if(riego == 1)
        Serial.print(1);
      else
        Serial.print(0);
      Serial.print(4,BYTE);
      break;	
    case 0x6A: //contarSensores: j 106 0x6A
      //Lo enviamos como texto, si lo enviamos como Byte (RAW) solo podremos enviar 1 Byte en Ca2
      Serial.print(contarSensores());
      Serial.print(4,BYTE);
      break;
    case 0x6B: //resetBusqueda: k 107 0x6B
      ds.reset_search();
      break;
    case 0x6C: //siguienteSensor: l 108 0x6C
      if(ds.search(addr)){
        for(i=0;i<8;i++){
          Serial.print(addr[i], BYTE);
        }
        Serial.print(4,BYTE);
      }
      break;
    case 0x6D: //seleccionarCursor: m 109 0x6D
      //leemos los 8 siguientes byte que seran el ID del sensor
      delay(40);
      contador=0;
      for (i=0;i<8;i++){
        if(Serial.available()>0){
          sensor_id[i]=Serial.read();
          contador++;
        }
      }
      if(contador==8){
        if ( OneWire::crc8( sensor_id, 7) != sensor_id[7]) {//CRC no valido
          Serial.print(0,DEC);
          Serial.print(4,BYTE);
          return;
        } 
        //Sensor valido
        Serial.print(1,DEC);
        Serial.print(4,BYTE);
        ds.reset();
        ds.select(sensor_id);
      }
      else{
        Serial.print(-1,DEC);
        Serial.print(4,BYTE);
      }
      break; 
    case 0x6E: //Obtener Tª del sensor seleccionado: n 110 0x6E
      //Codigo Arduino Playground One Wire 
      ds.write(0x44,1);         // start conversion, with parasite power on at the end
      delay(1000);     // maybe 750ms is enough, maybe not
      // we might do a ds.depower() here, but the reset will take care of it.
      present = ds.reset();
      ds.select(sensor_id);    
      ds.write(0xBE);         // Read Scratchpad
      for ( i = 0; i < 9; i++) {           // we need 9 bytes
        data[i] = ds.read();
      }
      LowByte = data[0];
      HighByte = data[1];
      TReading = (HighByte << 8) + LowByte;
      SignBit = TReading & 0x8000;  // test most sig bit
      if (SignBit) // negative
      {
        TReading = (TReading ^ 0xffff) + 1; // 2's comp
      }
      Tc_100 = (6 * TReading) + TReading / 4;    // multiply by (100 * 0.0625) or 6.25

      Whole = Tc_100 / 100;  // separate off the whole and fractional portions
      Fract = Tc_100 % 100;
      if (SignBit) // If its negative
      {
        Serial.print("-");
      }
      Serial.print(Whole);
      Serial.print(".");
      if (Fract < 10)
      {
        Serial.print("0");
      }
      Serial.print(Fract);
      Serial.print(4,BYTE);
      break;
    case 0x70: //Obtener Tª del sensor de presion I2C: p 112 0x70
      temperature = bmp085GetTemperature(bmp085ReadUT());
      Whole = temperature / 10;
      Fract = temperature % 10;
      Serial.print(Whole);
      Serial.print(".");
      Serial.print(Fract);
      Serial.print(4,BYTE);
      break;
    case 0x71: //Obtener Presion del sensor I2C: q 113 0x71
      //Tiene que leer antes la temperatura para calibrar la presion
      temperature = bmp085GetTemperature(bmp085ReadUT());
      pressure = bmp085GetPressure(bmp085ReadUP());    
      //Serial.print("Pressure: ");
      Serial.print(pressure, DEC);
      //Serial.println(" Pa");
      Serial.print(4,BYTE);
      //Por precaucion      
      delay(1000);
      break;
    case 0x72: //Obtener Altura del sensor I2C: r 114 0x72
      //Tiene que leer antes la presion y la temperatura
      temperature = bmp085GetTemperature(bmp085ReadUT());
      pressure = bmp085GetPressure(bmp085ReadUP());    
      altitude = (float)44330 * (1 - pow(((float) pressure/p0), 0.190295));  
      //Serial.print("Altitude: ");
      Serial.print(altitude, 2);
      //Serial.println(" m");  
      Serial.print(4,BYTE);
      //Por precaucion      
      delay(1000);
      break;
    case 0x73: //Obtener estimacion del tiempo del sensor I2C: s 115 0x73
      //Tiene que leer antes la presion y la temperatura
      temperature = bmp085GetTemperature(bmp085ReadUT());
      pressure = bmp085GetPressure(bmp085ReadUP());  
      weatherDiff = pressure - ePressure;
      if(weatherDiff > 250)
        Serial.print(Sunny);
      else if ((weatherDiff <= 250) || (weatherDiff >= -250))
        Serial.print(Partly_Cloudy);
      else if (weatherDiff > -250)
        Serial.print(Rain);
      Serial.print(4,BYTE);
      //Por precaucion      
      delay(1000);  
      break;
    case 0x74: //Obtener datos de todos los sensores:t 116 0x74
      temperature = bmp085GetTemperature(bmp085ReadUT());
      pressure = bmp085GetPressure(bmp085ReadUP());  
      altitude = (float)44330 * (1 - pow(((float) pressure/p0), 0.190295));
      Serial.print("Temperature: ");
      Serial.print(temperature, DEC);
      Serial.println(" *0.1 deg C");
      Serial.print("Pressure: ");
      Serial.print(pressure, DEC);
      Serial.println(" Pa");
      Serial.print("Altitude: ");
      Serial.print(altitude, 2);
      Serial.println(" m");    
      Serial.print(ePressure);
      weatherDiff = pressure - ePressure;
      if(weatherDiff > 250)
        Serial.println("Sunny!");
      else if ((weatherDiff <= 250) || (weatherDiff >= -250))
        Serial.println("Partly Cloudy");
      else if (weatherDiff > -250)
        Serial.println("Rain :-(");
      Serial.println();
      //Por precaucion      
      delay(1000);
      break;
    case 0x75: //Obtener humedad relativa del sensor HH10D 0x75 117 u
      float hum = hh10dReadHumidity();
      if(hum < 0 || hum > 100)
        Serial.print(-1);
      else 
        Serial.print(hum);
      Serial.print(4,BYTE);
      break;
    }
  }
  command=0;
  //digitalClockDisplay();
  Alarm.delay(0);
}

int contarSensores(void){
  int count=0;
  byte addr[8];
  ds.reset_search();
  while(ds.search(addr)){
    count++;
  }
  ds.reset_search();
  return count;
}

// Stores all of the bmp085's calibration values into global variables
// Calibration values are required to calculate temp and pressure
// This function should be called at the beginning of the program
void bmp085Calibration()
{
  ac1 = bmp085ReadInt(0xAA);
  ac2 = bmp085ReadInt(0xAC);
  ac3 = bmp085ReadInt(0xAE);
  ac4 = bmp085ReadInt(0xB0);
  ac5 = bmp085ReadInt(0xB2);
  ac6 = bmp085ReadInt(0xB4);
  b1 = bmp085ReadInt(0xB6);
  b2 = bmp085ReadInt(0xB8);
  mb = bmp085ReadInt(0xBA);
  mc = bmp085ReadInt(0xBC);
  md = bmp085ReadInt(0xBE);
}

// Calculate temperature given ut (unconpensed temperature)
// Value returned will be in units of 0.1 deg C
short bmp085GetTemperature(unsigned int ut)
{
  long x1, x2;

  x1 = (((long)ut - (long)ac6)*(long)ac5) >> 15;
  x2 = ((long)mc << 11)/(x1 + md);
  b5 = x1 + x2;

  return ((b5 + 8)>>4);  
}

// Calculate pressure given up (uncompensed pressure)
// calibration values must be known
// b5 is also required so bmp085GetTemperature(...) must be called first.
// Value returned will be pressure in units of Pa.
long bmp085GetPressure(unsigned long up)
{
  long x1, x2, x3, b3, b6, p;
  unsigned long b4, b7;

  b6 = b5 - 4000;
  // Calculate B3
  x1 = (b2 * (b6 * b6)>>12)>>11;
  x2 = (ac2 * b6)>>11;
  x3 = x1 + x2;
  b3 = (((((long)ac1)*4 + x3)<<OSS) + 2)>>2;

  // Calculate B4
  x1 = (ac3 * b6)>>13;
  x2 = (b1 * ((b6 * b6)>>12))>>16;
  x3 = ((x1 + x2) + 2)>>2;
  b4 = (ac4 * (unsigned long)(x3 + 32768))>>15;

  b7 = ((unsigned long)(up - b3) * (50000>>OSS));
  if (b7 < 0x80000000)
    p = (b7<<1)/b4;
  else
    p = (b7/b4)<<1;

  x1 = (p>>8) * (p>>8);
  x1 = (x1 * 3038)>>16;
  x2 = (-7357 * p)>>16;
  p += (x1 + x2 + 3791)>>4;

  return p;
}

// Read 1 byte from the BMP085 at 'address'
char bmp085Read(unsigned char address)
{
  unsigned char data;

  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.send(address);
  Wire.endTransmission();

  Wire.requestFrom(BMP085_ADDRESS, 1);
  while(!Wire.available())
    ;

  return Wire.receive();
}

// Read 2 bytes from the BMP085
// First byte will be from 'address'
// Second byte will be from 'address'+1
int bmp085ReadInt(unsigned char address)
{
  unsigned char msb, lsb;

  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.send(address);
  Wire.endTransmission();

  Wire.requestFrom(BMP085_ADDRESS, 2);
  while(Wire.available()<2)
    ;
  msb = Wire.receive();
  lsb = Wire.receive();

  return (int) msb<<8 | lsb;
}

// Read the uncompensated temperature value
unsigned int bmp085ReadUT()
{
  unsigned int ut;

  // Write 0x2E into Register 0xF4
  // This requests a temperature reading
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.send(0xF4);
  Wire.send(0x2E);
  Wire.endTransmission();

  // Wait at least 4.5ms
  delay(5);

  // Read two bytes from registers 0xF6 and 0xF7
  ut = bmp085ReadInt(0xF6);
  return ut;
}

// Read the uncompensated pressure value
unsigned long bmp085ReadUP()
{
  unsigned char msb, lsb, xlsb;
  unsigned long up = 0;

  // Write 0x34+(OSS<<6) into register 0xF4
  // Request a pressure reading w/ oversampling setting
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.send(0xF4);
  Wire.send(0x34 + (OSS<<6));
  Wire.endTransmission();

  // Wait for conversion, delay time dependent on OSS
  delay(2 + (3<<OSS));

  // Read register 0xF6 (MSB), 0xF7 (LSB), and 0xF8 (XLSB)
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.send(0xF6);
  Wire.endTransmission();
  Wire.requestFrom(BMP085_ADDRESS, 3);

  // Wait for data to become available
  while(Wire.available() < 3)
    ;
  msb = Wire.receive();
  lsb = Wire.receive();
  xlsb = Wire.receive();

  up = (((unsigned long) msb << 16) | ((unsigned long) lsb << 8) | (unsigned long) xlsb) >> (8-OSS);

  return up;
}
void hh10dCalibration(){
  sens    =  i2cRead2bytes(81, 10); //Read sensitivity from EEPROM
  offset =  i2cRead2bytes(81, 12); //Same for offset  
  //Las dos primera lecturas son erroneas
  hh10dReadHumidity();
  hh10dReadHumidity();
}
int i2cRead2bytes(int deviceaddress, byte address){
  // SET ADDRESS
  Wire.beginTransmission(deviceaddress);
  Wire.send(address); // address for sensitivity
  Wire.endTransmission();

  // REQUEST RETURN VALUE
  Wire.requestFrom(deviceaddress, 2);
  // COLLECT RETURN VALUE
  int rv = 0;
  for (int c = 0; c < 2; c++ )
    if (Wire.available()) rv = rv * 256 + Wire.receive();
  return rv;
}
float hh10dReadHumidity(){
  //Get Frequency
  FreqCounter::f_comp= 8;             // Set compensation to 12
  FreqCounter::start(1000);            // Start counting with gatetime of 1000ms
  while (FreqCounter::f_ready == 0)       // wait until counter ready 
    freq=FreqCounter::f_freq;            // read result
  //Calculate RH
  float RH =  (offset-freq)*sens/4096; //Sure, you can use int - depending on what do you need
  return RH;
}
void digitalClockDisplay(){
  // digital clock display of the time
  Serial.print(hour());
  printDigits(minute());
  printDigits(second());
  Serial.print(" ");
  Serial.print(day());
  Serial.print(" ");
  Serial.print(month());
  Serial.print(" ");
  Serial.print(year()); 
  Serial.println(); 
}
void printDigits(int digits){
  // utility function for digital clock display: prints preceding colon and leading 0
  Serial.print(":");
  if(digits < 10)
    Serial.print('0');
  Serial.print(digits);
}
void alarmaOn(){
  Serial.print("Activando alarma de encendido");
  riego = 1;
  digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high
  digitalWrite(motor1Pin, LOW);   // set leg 1 of the H-bridge low
  digitalWrite(motor2Pin, HIGH);  // set leg 2 of the H-bridge high
  digitalWrite(6, HIGH);  
  delay(300);
  digitalWrite(enable, LOW);  // set leg 1 of the H-bridge high
}
void alarmaOff(){
  riego = 0;
  digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high
  digitalWrite(motor1Pin, HIGH);  // set leg 1 of the H-bridge high
  digitalWrite(motor2Pin, LOW);   // set leg 2 of the H-bridge low
  digitalWrite(6, LOW);  
  delay(300);
  digitalWrite(enable, LOW);  // set leg 1 of the H-bridge high
}
































