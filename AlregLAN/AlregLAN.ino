#include <SPI.h>
#include <Ethernet.h>
#include <OneWire.h>
#include <Wire.h>
#include <FreqCounter.h>

#define enable 2 // H-bridge enable
#define motorPin 3 // H-bridge leg 1 (pin 2, 1A) && !leg 2 (pin 7, 2A); con puerta inversora
#define relePin 7 // Pin que activa el transistor para activar el Rele
#define solenoide3VOn 8 // Cable negro, pulso abre
#define solenoide3VOff 9 //Cable rojo, pulso cierra, mantiene abre
#define BMP085_ADDRESS 0x77  // Direccion I2C del sensor BMP085

//Estado de los actuadores
int riego; //Solenoide 2 vias
int estadoRele;
int solenoide3V;

//Presion
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
float hum;

//Ethernet
//Completar con la MAC indicada en la placa
byte mac[] = {
  0x90, 0xA2, 0xDA, 0x0D, 0x50, 0x0F};
//Usaremos DHCP, sino configurar IP
//IPAddress ip(192,168,1,20);
//IPAddress gateway(192,168,1,1);	
//IPAddress subnet(255, 255, 255, 0);

//Inicializamos la libreria Ethernet Server con el puerto deseado
EthernetServer server(80); 

OneWire ds(6); 	    // Pin protocolo 1-wire
byte addr[8];//Identificador del sensor
byte sensor_id[8];
char dato_sensor[2];
int contador = 0;
int present = 0;
byte data[12];
int HighByte, LowByte, TReading, SignBit, Tc_100, Whole, Fract;
String message;

void setup()
{
  //Inicializamos las salidas digitales
  pinMode(enable,OUTPUT);
  pinMode(motorPin, OUTPUT); 
  pinMode(relePin, OUTPUT);
  pinMode(solenoide3VOn, OUTPUT);
  pinMode(solenoide3VOff, OUTPUT);
  //Inicializamos el puerto de serie
  Serial.begin(9600);

  //Activamos comunicacion I2C 
  Wire.begin();  
  //Calibramos el sensor de presion con los valores de 
  bmp085Calibration();
  //Obtenemos los datos para calibrar el sensor de humedad HH10D
  hh10dCalibration();

  //Desactivamos el puente H
  digitalWrite(enable, LOW);  // set leg 0 of the H-bridge high  
  //Marcamos como apagado el  riego
  riego = 0;
  //Marcamos como apagado el rele
  estadoRele = 0;
  //Desactivamos el solenoide de 3 vias
  digitalWrite(solenoide3VOn, LOW);
  digitalWrite(solenoide3VOff, LOW);
  //Marcamos como apagado el solenoide de 3 vias
  solenoide3V = 0;  
  
  //Solicitamos IP por DHCP, devuleve 1 si es correcto
  if (Ethernet.begin(mac) == 0) {
    Serial.println("Failed to configure Ethernet using DHCP");
    // no point in carrying on, so do nothing forevermore:
    for(;;)
      ;
  }

  // print your local IP address:
  Serial.println(Ethernet.localIP());
  //Iniciamos el servidor y empezamos a escuchar
  server.begin();
}


void loop () {
  // if an incoming client connects, there will be bytes available to read:
  EthernetClient client = server.available();
  if (client) {  
    Serial.println("Cliente conectado");  
    message = "";
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();//read the next byte from the incoming client
        if(c != '\n'){
          message += c;
        }
        else{ //Fin del comando, que no incluye el fin de linea
          if(message.startsWith("sysreg") && message.length() > 7){
            int command = message[7];
            String argument = "";
            if(message.length() > 10){
              argument = message.substring(9,message.length());
            }
            Serial.println("Mensaje: " + message);
            if(message.length() > 10)
              Serial.println("Argumento: " + argument);

            switch(command){
            case 0x61:  //activarRele: a 97 0x61
              estadoRele = 1;
              digitalWrite(relePin, HIGH);
              delay(300);
              break;
            case 0x62:  //desactivarRele: b 98 0x62
              estadoRele = 0;
              digitalWrite(relePin, LOW);
              delay(300);
              break;
            case 0x63:  //comprobarRele: c 99 0x63
              //DATA
              if(estadoRele == 1)
                client.print(1);
              else
                client.print(0);
              break; 
            case 0x64: //activarRiego: d 100 0x64
              riego = 1;
              digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high
              digitalWrite(motorPin, LOW);   // set leg 1 of the H-bridge low
              //Con la puerta inversora: set leg 2 of the H-bridge high
              delay(300);
              digitalWrite(enable, LOW);  // set leg 1 of the H-bridge high
              break;
            case 0x65:  //desactivarRiego: e 101 0x65
              riego = 0;
              digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high
              digitalWrite(motorPin, HIGH);  // set leg 1 of the H-bridge high
              //Con la puerta inversora: set leg 2 of the H-bridge low
              delay(300);
              digitalWrite(enable, LOW);  // set leg 1 of the H-bridge high
              break;
            case 0x66:	//comprobarRiego: f 102 0x66
              if(riego == 1)
                client.print(1);
              else
                client.print(0);
              break;
            case 0x67: //activarSolenoide3V: g 103 0x67
              solenoide3V = 1;
              digitalWrite(solenoide3VOn, HIGH);  // Activamos la via ON (cable negro) 
              delay(300);
              digitalWrite(solenoide3VOn, LOW);  // set leg 1 of the H-bridge high
              break;
            case 0x68:  //desactivarSolenoide3V: h 104 0x68
              solenoide3V = 0;
              digitalWrite(solenoide3VOff, HIGH);  
              delay(300);
              digitalWrite(solenoide3VOff, LOW);  // Activamos la via OFF (cable rojo)
              break;
            case 0x69:	//comprobarSolenoide3V: i 105 0x69 
              if(solenoide3V == 1)
                client.print(1);
              else
                client.print(0);
              break;
            case 0x6A: //contarSensores: j 106 0x6A
              //Lo enviamos como texto, si lo enviamos como Byte (RAW) solo podremos enviar 1 Byte en Ca2
              client.print(contarSensores());
              break;
            case 0x6B: //resetBusqueda: k 107 0x6B
              ds.reset_search();
              break;
            case 0x6C: //siguienteSensor: l 108 0x6C
              if(ds.search(addr)){
                for(int i=0;i<8;i++){ //Enviamos la inforamcion en hexadecimal
                  char byteHexadecimal[2];
                  sprintf(byteHexadecimal, "%02X", addr[i]); 
                  client.print( byteHexadecimal);
                }
              }
              else 
                client.print("No more address");
              break;
            case 0x6D: //seleccionarCursor: m 109 0x6D    
              if(argument.length() < 16 ){
                Serial.println("<<Argumento invalido>>");
                Serial.flush();
                client.print(-1,DEC);
              }
              else{ //Convertimos la informacion los char enviados en hexadecimal a char
                delay(40);
                dato_sensor[0] = '0';
                dato_sensor[1] = '0';
                for(int i=0; i<argument.length(); i+=2){
                  dato_sensor[0] = argument[i];
                  dato_sensor[1] = argument[i+1];
                  sensor_id[i/2] = 0;
                  sensor_id[i/2] = (byte)strtol(dato_sensor,NULL,16); 
                }
                if ( OneWire::crc8( sensor_id, 7) != sensor_id[7]) {//CRC no valido
                  Serial.println("<<Sensor no valido>>");
                  break;
                }
                //Sensor valido
                ds.reset();
                ds.select(sensor_id);
                Serial.println("<<Sensor valido>>");
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
              for (int i = 0; i < 9; i++) {           // we need 9 bytes
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
                client.print("-");
              }
              Serial.print(Whole);
              client.print(Whole);
              Serial.print(".");
              client.print(".");
              if (Fract < 10)
              {
                Serial.print("0");
                client.print("0");
              }
              Serial.println(Fract);
              client.print(Fract);
              break;
            case 0x70: //Obtener Tª del sensor de presion I2C: p 112 0x70
              temperature = bmp085GetTemperature(bmp085ReadUT());
              Whole = temperature / 10;
              Fract = temperature % 10;
              Serial.print(Whole);
              client.print(Whole);
              Serial.print(".");
              client.print(".");
              Serial.println(Fract);
              client.print(Fract);
              break;
            case 0x71: //Obtener Presion del sensor I2C: q 113 0x71
              //Tiene que leer antes la temperatura para calibrar la presion
              temperature = bmp085GetTemperature(bmp085ReadUT());
              pressure = bmp085GetPressure(bmp085ReadUP());    
              Serial.println(pressure, DEC);
              client.print(pressure, DEC);
              delay(1000);
              break;
            case 0x72: //Obtener Altura del sensor I2C: r 114 0x72
              //Tiene que leer antes la presion y la temperatura
              temperature = bmp085GetTemperature(bmp085ReadUT());
              pressure = bmp085GetPressure(bmp085ReadUP());    
              altitude = (float)44330 * (1 - pow(((float) pressure/p0), 0.190295));  
              Serial.println(altitude, 2); //Con dos decimales
              client.print(altitude, 2);
              delay(1000);
              break;
            case 0x73: //Obtener estimacion del tiempo del sensor I2C: s 115 0x73
              //Tiene que leer antes la presion y la temperatura
              temperature = bmp085GetTemperature(bmp085ReadUT());
              pressure = bmp085GetPressure(bmp085ReadUP());  
              weatherDiff = pressure - ePressure;
              if(weatherDiff > 250){
                //Serial.print(Sunny);
                Serial.println("Soleado");
                client.print("Soleado");
              }
              else if ((weatherDiff <= 250) || (weatherDiff >= -250)){
                //Serial.print(Partly_Cloudy);
                Serial.println("Nublado");
                client.print("Nublado");
              }
              else if (weatherDiff > -250){
                //Serial.print(Rain);
                Serial.println("Lluvia");
                client.print("Lluvia");
              }   
              delay(1000);  
              break;
            case 0x75: //Obtener humedad relativa del sensor HH10D 0x75 117 u
              hum = hh10dReadHumidity();
              if(hum < 0 || hum > 100){
                Serial.println(-1);
                client.print(-1);
              }
              else{
                Serial.println(hum);
                client.print(hum);
              }
              break;
            }
            client.write(4);//Codigo EOT
            // give the Client time to receive the data
            delay(100);
            message = "";//mensaje terminado empezamos con una nueva orden
          }
        }
      }
    }
    Serial.println("Cliente desconectado");
  }
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
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(address);
  Wire.endTransmission();

  Wire.requestFrom(BMP085_ADDRESS, 1);
  while(!Wire.available())
    ;

  return Wire.read();
}

// Read 2 bytes from the BMP085
// First byte will be from 'address'
// Second byte will be from 'address'+1
int bmp085ReadInt(unsigned char address)
{
  unsigned char msb, lsb;

  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(address);
  Wire.endTransmission();

  Wire.requestFrom(BMP085_ADDRESS, 2);
  while(Wire.available()<2)
    ;
  msb = Wire.read();
  lsb = Wire.read();

  return (int) msb<<8 | lsb;
}

// Read the uncompensated temperature value
unsigned int bmp085ReadUT()
{
  unsigned int ut;

  // Write 0x2E into Register 0xF4
  // This requests a temperature reading
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(0xF4);
  Wire.write(0x2E);
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
  Wire.write(0xF4);
  Wire.write(0x34 + (OSS<<6));
  Wire.endTransmission();

  // Wait for conversion, delay time dependent on OSS
  delay(2 + (3<<OSS));

  // Read register 0xF6 (MSB), 0xF7 (LSB), and 0xF8 (XLSB)
  Wire.beginTransmission(BMP085_ADDRESS);
  Wire.write(0xF6);
  Wire.endTransmission();
  Wire.requestFrom(BMP085_ADDRESS, 3);

  // Wait for data to become available
  while(Wire.available() < 3)
    ;
  msb = Wire.read();
  lsb = Wire.read();
  xlsb = Wire.read();

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
  Wire.write(address); // address for sensitivity
  Wire.endTransmission();

  // REQUEST RETURN VALUE
  Wire.requestFrom(deviceaddress, 2);
  // COLLECT RETURN VALUE
  int rv = 0;
  for (int c = 0; c < 2; c++ )
    if (Wire.available())
      rv = rv * 256 + Wire.read();
  return rv;
}

float hh10dReadHumidity(){
  //Get Frequency
  FreqCounter::f_comp= 8;             // Set compensation to 12
  FreqCounter::start(1000);            // Start counting with gatetime of 1000ms
  while (FreqCounter::f_ready == 0)  // wait until counter ready 
    freq=FreqCounter::f_freq;            // read result

  //Calculate RH
  float RH =  (offset-freq)*sens/4096; //Sure, you can use int - depending on what do you need
  return RH;
}






















