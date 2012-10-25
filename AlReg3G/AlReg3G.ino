#include <OneWire.h>
#include <Time.h>
#include <TimeAlarms.h>


#define motor1Pin 3 // H-bridge leg 1 (pin 2, 1A)
#define motor2Pin 4 // H-bridge leg 2 (pin 7, 2A)
#define enable 2 // H-bridge enable
int verbose = 1;

int led = 13;
int onModulePin = 2;        // the pin to switch on the module (without press on button) 
int x=0;
char data[1024];
char incoming = 0;
int riego;
char port[]="80";

char apn[]="movistar.es";
char userApn[]="MOVISTAR";
char passApn[]="MOVISTAR";

String input;

//Timeout
boolean timeout = false;

//Temperatura
OneWire  ds(12);
byte sensor_id[8];
int command;
int contador = 0;
int present = 0;
byte dataTemp[12];
int HighByte, LowByte, TReading, SignBit, Tc_100, Whole, Fract;

void switchModule(){
  digitalWrite(onModulePin,HIGH);
  delay(2000);
  digitalWrite(onModulePin,LOW);
}
void offModule(){
  digitalWrite(onModulePin, LOW);
  delay(2000);
}
void onModule(){
  digitalWrite(onModulePin, HIGH);
  delay(2000);
}

void esperarOK(){
  char data = 0;
  char data_old = 0;
  do{          
    if(Serial1.available()){
      data_old = data;
      data = Serial1.read();
      if(verbose){
        Serial.print(data);    
        Serial.flush();
      }
    }    
  }
  while(data_old != 'O' || data !='K');    //Esperamos el OK
}
boolean esperarNet(){
  char data = 0;
  char data_old = 0;
  timeout = false;
  for (int i=0;i<dtNBR_ALARMS;i++){
    Alarm.free(i);
  }
  AlarmId timer = Alarm.timerOnce(10, t_agotado);
  do{          
    if(Serial1.available()){
      data_old = data;
      data = Serial1.read();  
      if(verbose){
        Serial.print(data);     
        Serial.flush(); 
      }
    }
    Alarm.delay(0);
  }
  while((data_old != 'e' || data !='d') && !timeout);    //Esperamos hasta tener el mensaje "Network opened" o que salte el timeout
  if(!timeout){ //si no ha saltado el timeout lo desactivamos
    Alarm.disable(timer);
    timeout = false;
    return true;
  }
  return false;
}

void t_agotado(){
  timeout = true;
  if(verbose){
    Serial.println("< Timeout >"); 
    Serial.flush();
  }
}

//Funcion reset de la placa por software
void(* resetBoard) (void) = 0;


void setup(){
  Serial.begin(9600);      //RX0 Pin 0, TX0 Pin 1: USB 
  Serial1.begin(9600);     //RX1 Pin 19, TX1 Pin 18: Sheild 3G
  delay(2000);
  pinMode(led, OUTPUT);
  pinMode(onModulePin, OUTPUT);
  digitalWrite(led, LOW);
  offModule();  // switches the module ON
  onModule();
  //switchModule();
  if(verbose){
    Serial.println("Esperando inicio...");

    Serial.flush();
  }
  for (int i=0;i<5;i++){ //5 OK
    delay(5000);
  } 

  //APN
  //Serial.println("-->Enviando comandos");
  Serial1.print("AT+CGSOCKCONT=1,\"IP\",\"");
  Serial1.print(apn);
  Serial1.println("\"");
  Serial1.flush();
  esperarOK(); 
  if(strlen(userApn) > 0 || strlen(passApn) > 0){ //Autentación
    Serial1.print("AT+CSOCKAUTH=1,1,\""); // Autenticación PAP
    Serial1.print(userApn);
    Serial1.print("\",\"");
    Serial1.print(passApn);
    Serial1.println("\"");
    Serial1.flush();
    esperarOK();
  }

  //Abrimos el socket TCP por el puerto indicado
  Serial1.print("AT+NETOPEN=\"TCP\",");  
  Serial1.println(port);        
  Serial1.flush();
  if(!esperarNet()){
    Serial.println("Network opened timeout - Reiniciando");
    Serial.flush();
    resetBoard();  
  }
  esperarOK();

  //Obtenemos la IP
  Serial1.println("AT+IPADDR");
  Serial1.flush();
  incoming = 0;
  do{          
    if(Serial1.available()){
      incoming = Serial1.read();
      Serial.print(incoming);     
      Serial.flush();
    }    
  }
  while(incoming !='K');

  //Arrancamos el servidor TCP
  Serial1.println("AT+SERVERSTART");
  Serial1.flush();
  esperarOK();

  delay(2000);  
  digitalWrite(led, LOW);
}
void loop(){
  byte addr[8];//Identificador del sensor
  if(Serial1.available()){
    input = leerLinea();
    Serial.print(input); 
    Serial.flush();
    //RECV FROM: 193.144.127.13:6766
    if(input != NULL && input.startsWith("RECV FROM")){ //Si ha encontrado un mensaje entrante
      //Serial.println("<<"+input+">>");
      //Copiamos la informacion del cliente despues de leer rapidamente
      //para evitar perdidas de datos
      leerLinea();
      String input_ans;
      input_ans = leerLinea();
      if(input_ans != NULL) {
        String c_conected = ""; //xxx.xxx.xxx.xxx:ppppp
        for(int i=10; i < input.length(); i++) //Copiamos la IP y el puerto
          c_conected = c_conected + input[i];

        //Si cumple con el reconocimiento
        //capturamos el mensaje: sysreg comando arg1
        char comando = 0;
        String argumento = "";
        if(input_ans.startsWith("sysreg")){
          comando = input_ans[7];
          Serial.print("Comando: ");
          Serial.println(comando, HEX); 
          Serial.flush();
          if(input_ans.length() > 12 ){//Si tiene argumento
            for(int i=0; i<input_ans.length();i++)
              Serial.print(input_ans[i],HEX);
            argumento = input_ans.substring(9,input_ans.length());
            Serial.println("Argumento: " + argumento);//incluye <cr><lf><cr><lf>
            Serial.flush();
          }
          //Obtenemos el ID del cliente
          input = leerClientes();
          if(input != NULL){
            Serial.println("<!" + input + "!>"); 
            Serial.flush();
            String client;
            int id_client;
            for(int i=0; i< input.length(); i++){ //Recorremos el string 
              if(input.startsWith("+LISTCLIENT:",i)){//Linea con un cliente
                client="";
                id_client = atoi(&input[i+13]);
                //adelantamos 16 posiciones y guardamos el clieente
                for(i += 16; input[i] != 0x0D; i++){//guardamos solo la IP y el puerto como ID
                  if(input[i] == ','){
                    client = client + ':';
                  }
                  else if(input[i] != 0x20){ //diferente de espacio en blanco
                    client = client + input[i];

                  }//Si es espacio en blanco adelantamos el indice de la linea pero no de la matriz
                }
                //Si coincide con el conectado paramos
                if(client == c_conected){ 
                  break;
                }
              }
            }
            Serial.print("Repeticion: ");
            Serial.print(client);
            Serial.print(" con id: ");
            Serial.print(id_client);

            Serial.flush();
            switch(comando){
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
              Serial1.print("AT+ACTCLIENT=");    //Activamos la conexion con el cliente  
              Serial1.println(id_client);
              Serial1.flush();
              esperarOK();  
              Serial1.println("AT+TCPWRITE=1");        //Sends TCP data
              Serial1.flush();
              incoming = 0;
              do{
                if(Serial1.available()){
                  incoming=Serial1.read();  
                  if(verbose){
                    Serial.print(incoming);  
                    Serial.flush(); 
                  }
                }                     
              }
              while(incoming != '>');
              //DATA
              if(riego == 1)
                Serial1.println(1);
              else
                Serial1.println(0);
              Serial1.flush();
              x=0;
              do{
                while(Serial1.available() == 0);
                data[x]=Serial1.read();  
                if(verbose){
                  Serial.print(data[x]);

                  Serial.flush();
                }
                x++;                        
              }
              while(!(data[x-1]=='k'&&data[x-2]=='o'));    //Waits for "send ok"
              if(verbose){
                Serial.println("Comunicacion terminada con " + client); 
                Serial.flush();
              }
              break;
            case 0x6A: //contarSensores: j 106 0x6A
              //Lo enviamos como texto, si lo enviamos como Byte (RAW) solo podremos enviar 1 Byte en Ca2
              Serial1.print("AT+ACTCLIENT=");    //Activamos la conexion con el cliente  
              Serial1.println(id_client);
              Serial1.flush();
              Serial.print("AT+ACTCLIENT=");

              Serial.flush();
              esperarOK();  
              Serial1.println("AT+TCPWRITE=3");        //Sends TCP data
              Serial1.flush();
              incoming = 0;
              do{
                if(Serial1.available()){
                  incoming=Serial1.read();  
                  if(verbose){
                    Serial.print(incoming);

                    Serial.flush();  
                  }
                }                     
              }
              while(incoming != '>');
              Serial1.println(contarSensores());            //DATA
              Serial1.flush();
              Serial.print("Sensores contados"); 
              Serial.flush();
              x=0;
              do{
                while(Serial1.available() == 0);
                data[x]=Serial1.read();  
                if(verbose){
                  Serial.print(data[x]); 
                  Serial.flush();
                }
                x++;                        
              }
              while(!(data[x-1]=='k'&&data[x-2]=='o'));    //Waits for "send ok"
              //Serial1.print("AT+CLOSECLIENT="); //desactivates the connection with the client  
              //Serial1.println(id_client);
              //Serial1.flush();
              //esperarOK();
              if(verbose){
                Serial.println("Comunicacion terminada con " + client); 
                Serial.flush();
              }
              break;
            case 0x6B: //resetBusqueda: k 107 0x6B
              ds.reset_search();
              break;
            case 0x6C: //siguienteSensor: l 108 0x6C
              Serial1.print("AT+ACTCLIENT=");    //Activamos la conexion con el cliente  
              Serial1.println(id_client);
              Serial1.flush();
              esperarOK();  
              Serial1.println("AT+TCPWRITE=16");        //Sends TCP data
              Serial1.flush();
              incoming = 0;
              do{
                if(Serial1.available()){
                  incoming=Serial1.read();  
                  if(verbose){
                    Serial.print(incoming);  
                    Serial.flush(); 
                  }
                }                     
              }
              while(incoming != '>');
              //DATA
              if(ds.search(addr)){
                for(int i=0;i<8;i++){ //Enviamos el byte en hexadecimal
                  char byteHexadecimal[2];
                  sprintf(byteHexadecimal, "%02X", addr[i]); 
                  Serial1.print( byteHexadecimal);
                }
              }
              else
                Serial1.print("No more address");
              Serial1.println();
              Serial1.flush();
              x=0;
              do{
                while(Serial1.available() == 0);
                data[x]=Serial1.read();  
                if(verbose){
                  Serial.print(data[x]);
                  Serial.flush();
                }
                x++;                        
              }
              while(!(data[x-1]=='k'&&data[x-2]=='o'));    //Waits for "send ok"
              if(verbose){
                Serial.println("Comunicacion terminada con " + client); 
                Serial.flush();
              }
              break;
            case 0x6D: //seleccionarCursor: m 109 0x6D
              //leemos los 8 siguientes byte que seran el ID del sensor

              Serial.println("Entrando");
              Serial.println(argumento + argumento.length());
              if(argumento.length() != 16 ){
                Serial.println("Arumento invalido " + argumento.length());
                break;
              }
              else{
                delay(40);
                char dato[2];
                for(int i=0; i<argumento.length(); i+=2){
                  dato[0] = argumento[i];
                  dato[1] =  argumento[i+1];
                  sensor_id[i/2]  = strtol(dato,NULL,16);
                }
                if ( OneWire::crc8( sensor_id, 7) != sensor_id[7]) {//CRC no valido
                  Serial.println("Sensor no valido");
                  break;
                }
                //Sensor valido
                ds.reset();
                ds.select(sensor_id);
                Serial.println("Sensor valido");
                break; 
              case 0x6E: //Obtener Tª del sensor seleccionado: n 110 0x6E
                byte type_s;
                float celsius;
                ds.write(0x44,1);         // start conversion, with parasite power on at the end

                delay(1000);     // maybe 750ms is enough, maybe not
                // we might do a ds.depower() here, but the reset will take care of it.

                present = ds.reset();
                ds.select(sensor_id);    
                ds.write(0xBE);         // Read Scratchpad

                Serial.print("  Data = ");
                Serial.print(present,HEX);
                Serial.print(" ");
                for (int  i = 0; i < 9; i++) {           // we need 9 bytes
                  dataTemp[i] = ds.read();
                  Serial.print(dataTemp[i], HEX);
                  Serial.print(" ");
                }
                Serial.print(" CRC=");
                Serial.print(OneWire::crc8(dataTemp, 8), HEX);
                Serial.println();

                // convert the data to actual temperature

                unsigned int raw = (dataTemp[1] << 8) | dataTemp[0];
                if (type_s) {
                  raw = raw << 3; // 9 bit resolution default
                  if (dataTemp[7] == 0x10) {
                    // count remain gives full 12 bit resolution
                    raw = (raw & 0xFFF0) + 12 - dataTemp[6];
                  }
                } 
                else {
                  byte cfg = (dataTemp[4] & 0x60);
                  if (cfg == 0x00) raw = raw << 3;  // 9 bit resolution, 93.75 ms
                  else if (cfg == 0x20) raw = raw << 2; // 10 bit res, 187.5 ms
                  else if (cfg == 0x40) raw = raw << 1; // 11 bit res, 375 ms
                  // default is 12 bit resolution, 750 ms conversion time
                }
                celsius = (float)raw / 16.0;
                Serial.print("  Temperature = ");
                Serial.println(celsius);

                //Respondemos al cliente
                Serial1.print("AT+ACTCLIENT=");    //Activamos la conexion con el cliente  
                Serial1.println(id_client);
                Serial1.flush();
                esperarOK();  
                Serial1.println("AT+TCPWRITE=6");        //Sends TCP data
                Serial1.flush();
                incoming = 0;
                do{
                  if(Serial1.available()){
                    incoming=Serial1.read();  
                    if(verbose){
                      Serial.print(incoming);  
                      Serial.flush(); 
                    }
                  }                     
                }
                while(incoming != '>');
                //DATA
                Serial1.println(celsius);
                Serial1.flush();
                x=0;
                do{
                  while(Serial1.available() == 0);
                  data[x]=Serial1.read();  
                  if(verbose){
                    Serial.print(data[x]);
                    Serial.flush();
                  }
                  x++;                        
                }
                while(!(data[x-1]=='k'&&data[x-2]=='o'));    //Waits for "send ok"
                if(verbose){
                  Serial.println("Comunicacion terminada con " + client); 
                  Serial.flush();
                }
                break;
              }
            }
          }
          //CONTESTAMOS
        }
      }
    }
  }

  if(Serial.available()){
    char comando =  Serial.read();
    switch(comando){
    case '<': //Modo consola
      Serial.println();
      Serial.println("Modo Terminal (para salir pulse > + Intro)");
      Serial.flush();
      char  incoming_char = 0;
      do{
        if(Serial.available()){
          incoming_char=Serial.read();  //Get the character coming from the terminal
          Serial1.print(incoming_char);    //Send the character to the cellular module.
        }
        if(Serial1.available()){
          char incoming_3G=Serial1.read();    //Get the character from the cellular serial port.
          Serial.print(incoming_3G);  //Print the incoming character to the terminal.

          Serial.flush();
        }
      }
      while(incoming_char != '>');
      break;

    }
  }
}

String leerPlaca(){//NO utilizar la clase STRING CUANDO EL TIEMPO  
  //SEA IMPORTANTE POR QUE SE PERDERA INFORMACION AL SER EL BUFFER PEQUEÑO
  //YA QUE RALENTIZA MUCHO 
  //GUARDAR EN vector de string y luego transformar
  String res = "";
  char character = 0;
  while(Serial1.available()){
    character = Serial1.read();
    res = res + character;
  }  

  return res;
}
String leerLinea(){// Para que no dependa del tiempo de procesamiento
  char data[1024];
  char character = 0;
  char character_old = 0;
  int length = 0;
  timeout = false;
  for (int i=0;i<dtNBR_ALARMS;i++){
    Alarm.free(i);
  }
  AlarmId timer = Alarm.timerOnce(5, t_agotado);
  if(verbose){
    Serial.print("<<");

    Serial.flush();
  }
  do{
    if(Serial1.available()){
      character_old = character;
      character = Serial1.read();
      data[length] = character;
      if(verbose) {
        Serial.print(character); 
        Serial.flush();
      }
      length++;  
    }
    Alarm.delay(0);
  } 
  while(character !=0x0A && character_old != 0x0D && !timeout); 
  if(verbose){
    Serial.print(">>"); 
    Serial.flush();
  }
  if(!timeout){ //si no ha saltado el timeout lo desactivamos
    Alarm.disable(timer);
    timeout = false; 
    String res= "";
    for(int i=0; i < length-1;i++){ //os x -1, win -2, REVISARRR
      res += data[i];
    }
    return res;
  }
  else{
    Serial.println("Tiemout leyendo linea");
    Serial.flush();
    return NULL;
  }
}



String leerClientes(){ //NO utilizar la clase STRING CUANDO EL TIEMPO  
  //SEA IMPORTANTE POR QUE SE PERDERA INFORMACION AL SER EL BUFFER PEQUEÑO
  //YA QUE RALENTIZA MUCHO 
  //GUARDAR EN vector de string y luego transformar
  char data[1024];
  char character = 0;
  int length =0;
  //Eliminamos las anteriores alarmas
  for (int i=0;i<dtNBR_ALARMS;i++){
    Alarm.free(i);
  }
  AlarmId timer = Alarm.timerOnce(15, t_agotado);
  Serial1.println("AT+LISTCLIENT");  //Listamos todos los clientes (Max = 10)
  Serial1.flush();
  do{  
    if(Serial1.available()){
      character = Serial1.read();
      data[length] = character;
      length++;      
      //Serial.print(character);
    }    
    Alarm.delay(0);
  }
  while(character !='K' && !timeout);
  if(!timeout){ //si no ha saltado el timeout lo desactivamos
    Alarm.disable(timer);
    timeout = false; 
    String res= "";
    for(int i=0; i < length;i++){
      res += data[i];
    }
    return res;  
  }
  else{
    Serial.println("Tiemout leyendo clientes");
    Serial.flush();
    return NULL;
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


unsigned char charToHexDigit(char c){

  if (c >= 'A')
    return c - 'A' + 10;
  else
    return c - '0';
}

unsigned char stringToByte(char c[2])
{
  Serial.print("<");
  Serial.println(charToHexDigit(c[0]));
  Serial.println(charToHexDigit(c[1]));  
  return charToHexDigit(c[0]) * 16 + charToHexDigit(c[1]);
}
