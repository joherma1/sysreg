
#include <OneWire.h>
#include <Wire.h>
#include <BMP085.h>

const int motor1Pin = 3;    // H-bridge leg 1 (pin 2, 1A)
const int motor2Pin = 4;    // H-bridge leg 2 (pin 7, 2A)
const int enable = 2;
OneWire ds(12); 	    // Pin protocolo 1-wire
int command;
byte sensor_id[8];
int contador = 0;
int present = 0;
byte data[12];
int HighByte, LowByte, TReading, SignBit, Tc_100, Whole, Fract;

BMP085 dps = BMP085();
long Temperature = 0, Pressure = 0, Altitude = 0;
const float currentAltitude = 34; // current altitude in METERS in Alcasser
const float p0 = 101325;     // Pressure at sea level (Pa)
const float ePressure = p0 * pow((1-currentAltitude/44330), 5.255);  // expected pressure (in Pa) at altitude
float weatherDiff;
const int Sunny = 1;
const int Partly_Cloudy = 2;
const int Rain = 3;

void setup(void){
  //Inicializamos las salidas digitales
  pinMode(motor1Pin, OUTPUT); 
  pinMode(motor2Pin, OUTPUT); 
  pinMode(enable,OUTPUT);
  pinMode(13, OUTPUT);
  //Inicializamos el puerto de serie
  Serial.begin(9600);
  //Activamos comunicacion I2C 
  Wire.begin();
  delay(1000);
  //Conexion con el sensor de presion BMP085
  dps.init(MODE_ULTRA_HIGHRES, currentAltitude*100, true);
  //dps.init(MODE_ULTRA_HIGHRES,0, true);
  //Activamos el puente H
  digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high  
  //Inicializacion por conexion
  Serial.print(6, BYTE);//Codigo de inicializacion, ACK
  Serial.print(4, BYTE);//EndOfTransmission (ASCII);
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
    case 0x64: //activarRiego: d 100 0x64
      digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high
      digitalWrite(motor1Pin, LOW);   // set leg 1 of the H-bridge low
      digitalWrite(motor2Pin, HIGH);  // set leg 2 of the H-bridge high
      digitalWrite(13, HIGH);  
      delay(300);
      digitalWrite(enable, LOW);  // set leg 1 of the H-bridge high
      break;
    case 0x65:  //desactivarRiego: e 101 0x65
      digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high
      digitalWrite(motor1Pin, HIGH);  // set leg 1 of the H-bridge high
      digitalWrite(motor2Pin, LOW);   // set leg 2 of the H-bridge low
      digitalWrite(13, LOW);  
      delay(300);
      digitalWrite(enable, LOW);  // set leg 1 of the H-bridge high
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
      dps.getTemperature(&Temperature);
      Whole = Temperature / 10;
      Fract = Temperature % 10;
      Serial.print(Whole);
      Serial.print(".");
      Serial.print(Fract);
      Serial.print(4,BYTE);
      break;
    case 0x71: //Obtener Presion del sensor I2C: q 113 0x71
      dps.getPressure(&Pressure);   
      Serial.print(Pressure);
      Serial.print(4,BYTE);
      break;
    case 0x72: //Obtener Altura del sensor I2C: r 114 0x72 
      dps.getAltitude(&Altitude);
      Serial.print(Altitude);
      Serial.print(4,BYTE);
      break;
    case 0x73: //Obtener estimacion del tiempo del sensor I2C: s 115 0x43
      //Tiene que leer antes la presion y la temperatura
      dps.getTemperature(&Temperature);
      dps.getPressure(&Pressure);  
      weatherDiff = Pressure - ePressure;
      if(weatherDiff > 250)
        Serial.print(Sunny);
      else if ((weatherDiff <= 250) || (weatherDiff >= -250))
        Serial.print(Partly_Cloudy);
      else if (weatherDiff > -250)
        Serial.print(Rain);
      Serial.print(4,BYTE);
      break;
    case 0x74:
      dps.getTemperature(&Temperature); 
      dps.getPressure(&Pressure);
      dps.getAltitude(&Altitude);
      Serial.print("Temp(C):");
      Serial.print(Temperature);
      Serial.print("  Alt(cm):");
      Serial.print(Altitude);
      Serial.print("  Pressure(Pa):");
      Serial.print(Pressure);
      Serial.print("  ePressure(Pa):");
      Serial.println(ePressure);
      break;
    }
  }
  command=0; 
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

