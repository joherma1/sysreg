#include <OneWire.h>
#include <Time.h>
#include <TimeAlarms.h>
#include <LiquidCrystal.h>

#define motor1Pin 3 // H-bridge leg 1 (pin 2, 1A)
#define motor2Pin 4 // H-bridge leg 2 (pin 7, 2A)
#define enable 14 // H-bridge enable
#define led 13
#define relePin 7 // Pin que activa el transistor para activar el Rele
int verbose = 1;

// Estado de los actuadores
int riego;
int estadoRele;

// Display PINS (rs, enable, d4, d5, d6, d7) 
LiquidCrystal lcd(29, 28, 25, 24, 23, 22);

int onModulePin = 2;        // the pin to switch on the module (without press on button) 
int x=0;
char data[1024];
char incoming = 0;

char port[]="80";
//MOVISTAR
char apn[]="movistar.es";
char userApn[]="MOVISTAR";
char passApn[]="MOVISTAR";

//SIMYO
//char apn[]="gprs-service.com";
//char userApn[]="";
//char passApn[]="";

char serverFTP[ ]="arroveitor.no-ip.org";
char portFTP[ ]="21";
char user_nameFTP[ ]="proto1";
char passwordFTP[ ]="agricultura.1";
int file_size;
String ip = "";
boolean estadoFTP;

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

boolean esperarSendOk(){
  char data = 0;
  char data_old = 0;
  timeout = false;
  for (int i=0;i<dtNBR_ALARMS;i++){
    Alarm.free(i);
  }
  AlarmId timer = Alarm.timerOnce(15, t_agotado);
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
  while((data_old != 'o' || data !='k') && !timeout);    //Esperamos "send ok" o que salte el timeout
  if(!timeout){ //si no ha saltado el timeout lo desactivamos
    Alarm.disable(timer);
    timeout = false;
    if(verbose){
      Serial.println();     
      Serial.flush(); 
    }
    return true;
  }
  return false;
}

boolean esperarBEGIN(){
  char data = 0;
  char data_old = 0;
  timeout = false;
  for (int i=0;i<dtNBR_ALARMS;i++){
    Alarm.free(i);
  }
  AlarmId timer = Alarm.timerOnce(20, t_agotado);
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
  while((data_old != 'I' || data !='N') && !timeout);    //Esperamos hasta tener el mensaje "Network opened" o que salte el timeout
  if(!timeout){ //si no ha saltado el timeout lo desactivamos
    Alarm.disable(timer);
    timeout = false;
    return true;
  }
  return false;
}

void t_agotado(){
  timeout = true;
  Serial.println("<<Timeout>>"); 
  Serial.flush();
}

//Funcion reset de la placa por software
void(* resetBoard) (void) = 0;

boolean guardarIP(String ip){
  Serial1.print("AT+CFTPSERV=\""); //Sets the FTP server
  Serial1.print(serverFTP);
  Serial1.println("\""); 
  Serial1.flush();
  esperarOK();

  Serial1.print("AT+CFTPPORT=");    //Sets FTP port
  Serial1.println(portFTP);
  Serial1.flush();
  esperarOK();

  Serial1.print("AT+CFTPUN=\""); //Sets the user name
  Serial1.print(user_nameFTP);
  Serial1.println("\""); 
  Serial1.flush();
  esperarOK();

  Serial1.print("AT+CFTPPW=\""); //Sets password
  Serial1.print(passwordFTP);
  Serial1.println("\"");     
  Serial1.flush();
  esperarOK();

  Serial1.println("AT+CFTPMODE=1");    //Selects pasive mode
  Serial1.flush();
  esperarOK();

  Serial1.println("AT+CFTPTYPE=I");    //Selects Binary mode
  Serial1.flush();
  esperarOK();

  Serial1.println("AT+CFTPPUT=\"/alreg/proto1/ip.txt\"");    //Creates a file and sends data (ASCII)
  Serial1.flush();   
  if(!esperarBEGIN()){ //Si ha fallado la conexion FTP
    Serial.println("<<FTP PUT Timeout>>  Error al guardar la IP");
    Serial.println("-->Error al guardar la IP<--");
    Serial.flush();
    return false;
  }
  //Guardamos la IP
  Serial1.print(ip);
  Serial1.write(0x1A);    //EOL character
  Serial1.write(0x0D);
  Serial1.write(0x0A);
  esperarOK();
  delay(1000);
  return true;
}

void setup(){
  //Inicializamos las salidas digitales
  pinMode(motor1Pin, OUTPUT); 
  pinMode(motor2Pin, OUTPUT); 
  pinMode(enable,OUTPUT);
  pinMode(led, OUTPUT);
  pinMode(relePin, OUTPUT);

  Serial.begin(9600);      //RX0 Pin 0, TX0 Pin 1: USB 
  Serial1.begin(9600);     //RX1 Pin 19, TX1 Pin 18: Sheild 3G
  delay(2000);

  //LCD DISPLAY
  // set up the LCD's number of columns and rows: 
  lcd.begin(16, 2);
  // Print a message to the LCD.
  lcd.print("Iniciando...");

  //Desactivamos el puente H
  digitalWrite(enable, LOW);  // set leg 0 of the H-bridge high  
  //Marcamos como apagado el  riego
  riego = 0;
  //Desactivamos el rele
  digitalWrite(relePin, LOW);
  //Marcamos como apagado el rele
  estadoRele = 0;

  //Encendemos el modulo 3G
  pinMode(onModulePin, OUTPUT);
  offModule();  // switches the module ON
  onModule();
  //switchModule();
  if(verbose){
    Serial.println("-->Iniciando<--");
    Serial.flush();
  }
  for (int i=0;i<5;i++){ //5 OK
    delay(5000);
  } 

  //APN
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
    Serial.println("<<Network opened timeout>>");
    Serial.println("-->Reiniciando<--");
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
      ip+=incoming;
    }    
  }
  while(incoming !='K');
  //Limpiamos la IP
  ip = ip.substring(23,ip.indexOf(0x0D,23)); //Primera posicion de la IP
  //Arrancamos el servidor TCP
  Serial1.println("AT+SERVERSTART");
  Serial1.flush();
  esperarOK();
  delay(2000);

  //Señal de inicializacion OK
  digitalWrite(led, HIGH);  
  delay(300); 
  digitalWrite(led, LOW);  

  //Guardamos la IP en servidor FTP
  estadoFTP =guardarIP(ip);

  Serial.println();
  Serial.println("-->Inicializacion terminada<--");
  Serial.flush();

  //Mostramos en el display la IP y un mensaje
  lcd.setCursor(0, 0);  //Primera linea
  lcd.print(ip);
  lcd.setCursor(15,0);  //El estado de la sincronizacion con el FTP
  if(estadoFTP)
    lcd.print("S");
  else
    lcd.print("E");
  lcd.setCursor(0, 1);  //Segunda linea
  lcd.print("Ini. terminada");
}

void loop(){
  byte addr[8];//Identificador del sensor
  if(Serial1.available()){
    input = leerLinea();
    if(verbose){
      Serial.println(input); 
      Serial.flush();
    }
    //RECV FROM: 193.144.127.13:6766
    if(input != NULL && input.startsWith("RECV FROM")){ //Si ha encontrado un mensaje entrante
      //Copiamos la informacion del cliente despues de leer rapidamente
      //para evitar perdidas de datos
      leerLinea(); //Descartamos el salto de linea
      String input_ans; //Comando que envia el cliente
      input_ans = leerLinea();
      if(verbose){
        Serial.println(input_ans); 
        Serial.flush();
      }
      if(input_ans != NULL) {
        String c_conected = ""; //xxx.xxx.xxx.xxx:ppppp
        for(int i=10; i < input.length(); i++) //Copiamos la IP y el puerto
          if(input[i]!=0x0D) //Para que no guarde el salto de linea que no envia en OS X
            c_conected = c_conected + input[i];

        //Si cumple con el reconocimiento
        //capturamos el mensaje: sysreg comando arg1
        char comando = 0;
        String argumento = "";
        if(input_ans.startsWith("sysreg")){
          comando = input_ans[7];
          int input_ans_length  = input_ans.length();
          if(input_ans_length > 12 ){//Si tiene argumento
            //En Windows y OS X envia finales de linea diferentes, si queremos ver lo que envia
            //for(int i=0; i<length;i++)
            //  Serial.print(input_ans[i],HEX);
            argumento = input_ans.substring(9,input_ans_length);
          }
          //Obtenemos el ID del cliente
          input = leerClientes();
          if(input != NULL){
            //Para ver los clientes conectados
            //Serial.println("<!" + input + "!>"); 
            //Serial.flush();
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
                if(client.equals(c_conected)){
                  break;
                }
              }
            }
            if(verbose){
              Serial.print("-->Cliente: " + client + " con id: " + id_client);
              Serial.print(" Comando: ");
              Serial.print(comando);
              if(argumento.length() > 0){
                Serial.print(" Argumento:" + argumento);
              }
              Serial.println("<--");
              Serial.flush();
            }

            lcd.clear(); //Limpia y pone en la primera posicion
            lcd.print(ip);
            lcd.setCursor(15,0);  //El estado de la sincronizacion con el FTP
            if(estadoFTP)
              lcd.print("S");
            else
              lcd.print("E");
            lcd.setCursor(0, 1);
            lcd.print("Client IN ");
            lcd.print(id_client);

            switch(comando){
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
              if(estadoRele == 1)
                Serial1.println(1);
              else
                Serial1.println(0);
              Serial1.flush();
              if(esperarSendOk()){//Comunicacion enviada correctamente
                if(verbose){
                  Serial.println("-->Comunicacion terminada correctamente con " + client + "<--"); 
                  Serial.flush();
                  //Mostramos en el display la IP y un mensaje
                  lcd.clear(); //Limpia y pone en la primera posicion
                  lcd.print(ip);
                  lcd.setCursor(15,0);  //El estado de la sincronizacion con el FTP
                  if(estadoFTP)
                    lcd.print("S");
                  else
                    lcd.print("E");
                  lcd.setCursor(0, 1);
                  lcd.print("Client OUT ");
                  lcd.print(id_client);
                }
              }
              else{
                if(verbose){
                  Serial.println("<<Comunicacion fallida con " + client + ">>"); 
                  Serial.flush();
                }
              }
              break;  
            case 0x64: //activarRiego: d 100 0x64
              riego = 1;
              digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high
              digitalWrite(motor1Pin, LOW);   // set leg 1 of the H-bridge low
              digitalWrite(motor2Pin, HIGH);  // set leg 2 of the H-bridge high
              digitalWrite(led, HIGH);  
              delay(300);
              digitalWrite(enable, LOW);  // set leg 1 of the H-bridge high
              break;
            case 0x65:  //desactivarRiego: e 101 0x65
              riego = 0;
              digitalWrite(enable, HIGH);  // set leg 1 of the H-bridge high
              digitalWrite(motor1Pin, HIGH);  // set leg 1 of the H-bridge high
              digitalWrite(motor2Pin, LOW);   // set leg 2 of the H-bridge low
              digitalWrite(led, LOW);  
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
              if(esperarSendOk()){//Comunicacion enviada correctamente
                if(verbose){
                  Serial.println("-->Comunicacion terminada correctamente con " + client + "<--"); 
                  Serial.flush();
                  //Mostramos en el display la IP y un mensaje
                  lcd.clear(); //Limpia y pone en la primera posicion
                  lcd.print(ip);
                  lcd.setCursor(15,0);  //El estado de la sincronizacion con el FTP
                  if(estadoFTP)
                    lcd.print("S");
                  else
                    lcd.print("E");
                  lcd.setCursor(0, 1);
                  lcd.print("Client OUT ");
                  lcd.print(id_client);
                }
              }
              else{
                if(verbose){
                  Serial.println("<<Comunicacion fallida con " + client + ">>"); 
                  Serial.flush();
                }
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
              Serial.print("Sensores contados "); 
              Serial.flush();
              if(esperarSendOk()){//Comunicacion enviada correctamente
                if(verbose){
                  Serial.println("<--Comunicacion terminada correctamente con " + client + "<--"); 
                  Serial.flush();    
                  //Mostramos en el display la IP y un mensaje
                  lcd.clear(); //Limpia y pone en la primera posicion
                  lcd.print(ip);
                  lcd.setCursor(15,0);  //El estado de la sincronizacion con el FTP
                  if(estadoFTP)
                    lcd.print("S");
                  else
                    lcd.print("E");
                  lcd.setCursor(0, 1);
                  lcd.print("Client OUT ");
                  lcd.print(id_client);
                }
              }
              else{
                if(verbose){
                  Serial.println("<<Comunicacion fallida con " + client + ">>"); 
                  Serial.flush();
                }
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
              if(esperarSendOk()){//Comunicacion enviada correctamente
                if(verbose){
                  Serial.println("-->Comunicacion terminada correctamente con " + client + "<--"); 
                  Serial.flush();
                  //Mostramos en el display la IP y un mensaje
                  lcd.clear(); //Limpia y pone en la primera posicion
                  lcd.print(ip);
                  lcd.setCursor(15,0);  //El estado de la sincronizacion con el FTP
                  if(estadoFTP)
                    lcd.print("S");
                  else
                    lcd.print("E");
                  lcd.setCursor(0, 1);
                  lcd.print("Client OUT ");
                  lcd.print(id_client);
                }
              }
              else{
                if(verbose){
                  Serial.println("<<Comunicacion fallida con " + client + ">>"); 
                  Serial.flush();
                }
              }
              break;
            case 0x6D: //seleccionarCursor: m 109 0x6D
              //leemos los 8 siguientes byte que seran el ID del sensor
              if(argumento.length() < 16 ){
                Serial.println("<<Argumento invalido>>");
                Serial.flush();
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
                  Serial.println("<<Sensor no valido>>");
                  break;
                }
                //Sensor valido
                ds.reset();
                ds.select(sensor_id);
                Serial.println("-->Sensor valido<--");
              }
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

              Serial.print("Data = ");
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
              Serial.print("Temperatura = ");
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
              if(esperarSendOk()){//Comunicacion enviada correctamente
                if(verbose){
                  Serial.println("<--Comunicacion terminada correctamente con " + client + "-->"); 
                  Serial.flush();
                  //Mostramos en el display la IP y un mensaje
                  lcd.clear(); //Limpia y pone en la primera posicion
                  lcd.print(ip);
                  lcd.setCursor(15,0);  //El estado de la sincronizacion con el FTP
                  if(estadoFTP)
                    lcd.print("S");
                  else
                    lcd.print("E");
                  lcd.setCursor(0, 1);
                  lcd.print("Client OUT ");
                  lcd.print(id_client);
                }
              }
              else{
                if(verbose){
                  Serial.println("<<Comunicacion fallida con " + client + ">>"); 
                  Serial.flush();
                }
              }
              break;
            }
          }
        }
      }
    }
  }

  if(Serial.available()){
    char comando =  Serial.read();
    switch(comando){
    case '<': //Modo consola
      Serial.println();
      Serial.println("-->Modo Terminal (para salir pulse > + Intro)<--");
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
      Serial.println("-->Modo Terminal cerrado<--");
      break;
    }
  }
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
  do{
    if(Serial1.available()){
      character_old = character;
      character = Serial1.read();
      data[length] = character;
      length++;  
    }
    Alarm.delay(0);
  } 
  while(character !=0x0A && character_old != 0x0D && !timeout); 
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



























