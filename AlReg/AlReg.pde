#include <OneWire.h>

const int motor1Pin = 3;    // H-bridge leg 1 (pin 2, 1A)
const int motor2Pin = 4;    // H-bridge leg 2 (pin 7, 2A)
const int enable = 2;
OneWire ds(12); 	    // Pin protocolo 1-wire
int command;

void setup(void){
  //Inicializamos las salidas digitales
  pinMode(motor1Pin, OUTPUT); 
  pinMode(motor2Pin, OUTPUT); 
  pinMode(enable,OUTPUT);
  //Inicializamos el puerto de serie
  Serial.begin(9600);
  //Activamos el puente H
  digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high
}
void loop(void){
  byte i; //Variable para bucles
  byte present;
  byte data[12];
  byte addr[8];//Identificador del sensor
  int count = Serial.available();
  if(count > 0){
    command=Serial.read();
  }
  switch (command){
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
    break;
  case 0x6B: //resetBusqueda: k 107 0x6B
    ds.reset_search();
    break;
  case 0x6C: //siguienteSensor: l 108 0x6C
    if(ds.search(addr)){
      for(i=0;i<8;i++){
        Serial.print(addr[i], HEX);
      }
    }
    break;
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


