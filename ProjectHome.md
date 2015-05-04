![http://sysreg.googlecode.com/svn/trunk/src/imagenes/Naranjito/Naranjito%2064.png](http://sysreg.googlecode.com/svn/trunk/src/imagenes/Naranjito/Naranjito%2064.png)

&lt;FONT face="arial" size=6 color=black&gt;

 

&lt;B&gt;

  SYSREG 

&lt;/B&gt;

 

&lt;/FONT&gt;

 

&lt;FONT size=3 color=gray&gt;

 

&lt;I&gt;

  Sistemas y soluciones para el regadío

&lt;/I&gt;

 

&lt;/FONT&gt;

 

&lt;BR&gt;

 

&lt;BR&gt;




&lt;FONT face="Times New Roman" size=4 &gt;

 

&lt;I&gt;

 Sistema de gestión agrícola remoto, inteligente y extensible 

&lt;/I&gt;

 

&lt;/FONT&gt;



---

El proyecto Sysreg surgió en el año 2010 con la intención de introducir en el sector agrícola valenciano componentes tecnológicos que permitan digitalizar la información y proveer de actuadores directos pensando en su utilización en  sistemas de información agrícolas. 

&lt;BR&gt;

 

&lt;BR&gt;


Este sistema de gestión agrícola tiene como objetivo conseguir una mejora productiva, gracias a la monitorización en tiempo real, la optimización de los recursos en función de los sensores (programando con eventos a través de Google Calendar o estableciendo alarmas) y la extensibilidad del sistema. Y todo ello utilizando únicamente dispositivos de muy bajo coste y de código abierto. 

&lt;BR&gt;

 

&lt;BR&gt;


Aunque el proyecto fue ideado en el marco de la agricultura citrícola minifundista propia de la costa valenciana, puede ser extrapolado para gestionar desde pequeños jardines hasta grandes industrias hortofruticolas (e.g. empresas productoras, semilleros, víveros...).

El proyecto sysreg esta formado por dos componentes, siguiendo la siguiente arquitectura: 

&lt;BR&gt;



&lt;BR&gt;


![https://sysreg.googlecode.com/files/Arquitectura.png](https://sysreg.googlecode.com/files/Arquitectura.png) 

&lt;BR&gt;

 

&lt;BR&gt;



Por un lado tenemos la parte software, RegAdmin. Aquí se engloban las aplicaciones encargadas de conectar con la placa y proveer las funcionalidades básicas. Actualmente disponemos de una versión de escritorio multiplataforma y de una aplicación web alojada en el dominio arroveitor.no-ip.org (restringida a usuarios autorizados).

**RegAdmin Escritorio**: Programa diseñado en JAVA para controlar una placa Arduino conectada con un solenoide de tipo latch y con sensores de temperatura, presión, humedad relativa, humedad del suelo. Para gestionar la lógica de eventos utiliza los servicios de Google Calendar que sincronizará con la placa, bien mediante alarmas o bien enviando señales de encendido y apagad en las horas señaladas.

&lt;BR&gt;

 

&lt;BR&gt;


![https://sysreg.googlecode.com/files/Screenshot%20RegAdmin%204.0.png](https://sysreg.googlecode.com/files/Screenshot%20RegAdmin%204.0.png)

**RegAdminWeb**: Aplicación web desarrollada en GWT y que permite conectarse a través del navegador a placas AlReg mediante socket(soporta AlReg3G y AlRegEth). Permite acceder de manera concurrente a los diferentes sensores y actuadores configurando previamente la conexión de red.

&lt;BR&gt;



&lt;BR&gt;


![https://sysreg.googlecode.com/files/Screenshot%20RegAdminWeb%201.0.png](https://sysreg.googlecode.com/files/Screenshot%20RegAdminWeb%201.0.png)

Como se puede observar en la arquitectura existe un componente pendiente por desarrollar: SIA. Estas son las iniciales del Sistema de Información Agricola, un proyecto independiente de sysreg que pretende ser un ERP agrario. SIA utilizará sysreg como toma de datos y sistema de ejecución, es decir, sysreg será la digitalización del proceso físico.

**AlRegUSB** (Hardware): Placa Arduino a la que se ha conectado:
  * Solenoide de tipo latch de 9V mediante un puente H (pines digitales 2, 3 y 4).
  * Sensores de humedad DS18B20 conectados al enlace One-wire (pin digital 12)
  * Sensor de presión BMP085 (Protocolo I2C, pines analógicos 4 y 5 )
  * Sensor de humedad relativa HH10D (Librería FreqCounter, pin digtal 5)
  * Sensor de humedad del suelo artesanal (pin analógico 0 y digitales 8 y 9)
El software cargado en la placa (AlReg.ino) transmite los datos por el puerto USB en función de la orden introducida.

&lt;BR&gt;

 

&lt;BR&gt;


![https://sysreg.googlecode.com/files/AlRegUSB.png](https://sysreg.googlecode.com/files/AlRegUSB.png)

**AlReg3G** (Hardware): Evolución de la placa AlReg que la dota de conectividad 3G y nuevos sensores:
  * 3G/GPRS SHIELD de cooking-hacks con una velocidad de hasta 7.2Mbps de bajada y 5.5Mbps de subida.
  * Interfaz a través de sockets
  * Conectividad con un servidor FTP para guardar datos.
  * Display retroiluminado donde se muestra la información del dispositivo así como la actividad.
  * Transistores que permiten la activación de válvulas de 3 vías de DC.
  * Relé para la gestión de todo tipo de dispositivos (AC/DC) .
El software cargado en esta placa (AlReg3G.ino) transmite los datos a traves de redes de datos móviles 3G, en función del comando enviado por el puerto 80.

&lt;BR&gt;

 

&lt;BR&gt;


![https://sysreg.googlecode.com/files/AlReg3G%201.1.png](https://sysreg.googlecode.com/files/AlReg3G%201.1.png)

**AlRegLAN** (Hardware): Placa AlReg dotada de conectividad Ethernet para poder acceder a traves de la red mediante sockets. Este modelo cuenta con los sensores implementados en las dos otras placas con las siguientes novedades:
  * Utilización de una placa estándar Arduino UNO [r3](https://code.google.com/p/sysreg/source/detail?r=3) con el shield Ethernet [r3](https://code.google.com/p/sysreg/source/detail?r=3)
  * Conexión mucho más rápida, segura y estable (hasta 100Mbps) mediante el protocolo ethernet.
  * IP mediante DHCP.
  * Posibilidad de conectar la placa por 3G haciendo uso de MIFIs o mediante WIFI utilizando un router neutro en modo bridge.

&lt;BR&gt;

 

&lt;BR&gt;


![https://sysreg.googlecode.com/files/AlRegLAN%201.0.png](https://sysreg.googlecode.com/files/AlRegLAN%201.0.png)

Como puede verse en la hoja de ruta original, el origen de este proyecto se remonta a la tesis "Sistemas y Soluciones para el regadío" presentada en el Master de Ingeniería del Software, Métodos Formales y Sistemas de Información en el DSIC (Universidad Politécnica de Valencia) y cuya memoria esta disponible en las descargas.
![http://sysreg.googlecode.com/files/Roadmap.jpg](http://sysreg.googlecode.com/files/Roadmap.jpg)