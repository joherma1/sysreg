package arduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;

//MEJORA

@SuppressWarnings("restriction")
public class Duemilanove implements SerialPortEventListener{
	//--------------
	//Variables RXTX
	//--------------
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
		"/dev/tty.usbserial-A700e0xk", // Mac OS X
		"/dev/ttyUSB0", // Linux
		"COM11", // Windows
	};
	/** Buffered input stream from the port */
	private InputStream input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	//-----------------
	//Variables Arduino
	//-----------------
	int n_sensores_t=0;
	public byte sensores_t[][]=null;
	Monitor m=new Monitor();

	//------------
	//Métodos RXTX
	//------------
	public int initialize() {
		//0 OK
		//-1 No se ha encontrado puerto
		//-2 Error Abrir el puerto
		//Inicializacion libreria RXTX
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}

		if (portId == null) {
			System.out.println("Could not find COM port.");
			return -1;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			//Tiempo de arranque, unos 1450
			if(this.leerArduinoBytes()[0]==6)//Arduino nos responde con ACK
				return 0;
			else return -2;
		} catch (Exception e) {
			System.err.println(e.toString());
			return -2;
		}
	}
	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int available = input.available();
				byte chunk[] = new byte[available];
				input.read(chunk, 0, available);
				//Cuando llegue información la depositamos en el buffer del monitor
				m.depositar(chunk);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

	//---------------------------
	//Métodos privados al paquete
	//---------------------------
	String leerArduino(){
		byte[] data = m.recoger();
		while(data[data.length -1]!=4){//Mientras no nos diga fin de transmision seguimos esperando
			byte[] data2 = m.recoger();
			//creamos el nuevo vector y lo rellenamos
			byte[] aux= new byte[data.length+data2.length];
			for(int i=0;i<aux.length;i++){
				if(i<data.length)
					aux[i]=data[i];
				else
					aux[i]=data2[i-data.length];
			}
			data=aux;
		}
		//ya tenemos todo el mensaje, lo pasamos a string elminando el caracter EOT
		String res= new String(data,0,data.length-1);
		return res;
	}

	byte[] leerArduinoBytes(){
		byte[] data = m.recoger();
		while(data[data.length -1]!=4){//Mientras no nos diga fin de transmision seguimos esperando
			byte[] data2 = m.recoger();
			//creamos el nuevo vector y lo rellenamos
			byte[] aux= new byte[data.length+data2.length];
			for(int i=0;i<aux.length;i++){
				if(i<data.length)
					aux[i]=data[i];
				else
					aux[i]=data2[i-data.length];
			}
			data=aux;
		}
		//ya tenemos todo el mensaje,elminamos el caracter EOT
		byte[] res = new byte[data.length-1];
		for(int i=0;i<res.length;i++)
			res[i]=data[i];
		return res;
	}

	void resetearBusquedaT(){
		try {
			output.write(0x6B);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	int seleccionarSensorT(byte[] sensor){ 
		//0: CRC NO VALIDO		1: OK	-1:No se han pasado 8 B		-2:Excepcion
		try {
			//el comando es mXXXXXXXX
			byte[] comando= {0x6D,sensor[0],sensor[1],sensor[2],sensor[3],sensor[4],sensor[5],sensor[6],sensor[7]};
			output.write(comando);
			//Tiempo seleccionar cursor, 6ms
			String res1 = leerArduino();
			int res= Integer.parseInt(res1);
			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -2;
	}

	//------------------------
	//Métodos públicos
	//------------------------
	public boolean startReg(){
		try {
			output.write(0x64);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean stopReg(){
		try {
			output.write(0x65);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	public int contarSensoresT(){
		try {
			output.write(0x6A);
			String res = leerArduino();
			if(res!=null)
				this.n_sensores_t = Integer.parseInt(res);
			else{
				this.n_sensores_t = -1;	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.n_sensores_t = -1;
		}
		return this.n_sensores_t;
	}


	public byte[][] listarSensoresT(){
		try {
			this.contarSensoresT();
			this.resetearBusquedaT();
			this.sensores_t= new byte[this.n_sensores_t][8];
			for(int i=0;i<this.n_sensores_t;i++){
				output.write(0x6C);
				byte res[] =  leerArduinoBytes();
				this.sensores_t[i]=res;
			}
			this.resetearBusquedaT();
			return this.sensores_t;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Float obtenerTemperatura(byte[] sensor){
		try {
			//Seleccionamos el sensor
			if(seleccionarSensorT(sensor) != 1)
				return null;
			else {
				output.write(0x6E);
				//Tiempo necesario para la conversion, unos 1000¡
				String res= leerArduino();
				Float res_f= Float.parseFloat(res);
				return res_f;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	public class Monitor {
		private byte[] buffer;
		private boolean vacio = true;

		public synchronized byte[] recoger(){
			while(vacio==true){
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			vacio=true;
			byte[] res = buffer;
			buffer=null;;
//			for(int i=0;i<res.length;i++)
//				System.out.println("|"+res[i]+"|");
			return res;
		}

		public synchronized void depositar(byte[] data){
			buffer=data;
			vacio=false;
			notify();
		}
	}
	
	public static void main(String[] args){
		Duemilanove d= new Duemilanove();
		d.initialize();
//		System.out.println("1-Contar sensores");
//		System.out.println("2-Listar sensores");
//		System.out.println("3-Obtener temperatura del 1r sensor");
//		System.out.println("4-Obtener temperatura del 2o sensor");
		int opcion;
		for(int ii=0;ii<1;ii++){
			//int n=d.contarSensoresT();
			//System.out.println("Numero de sensores "+ n);
//			byte[][] l=d.listarSensoresT();
//			for(int i=0;i<d.n_sensores_t;i++){
//				String aux= new String(l[i]);
//				System.out.println(aux);
//			}
			//System.out.println("Temperatura Sensor 1");
			//d.obtenerTemperatura(l[0]);
			//System.out.println("Ya listados");
			//d.obtenerTemperatura(l[1]);
		}
		byte[] sensor= new byte[8];
		sensor[0] = 40;
		sensor[1] = -11;
		sensor[2] = -23;
		sensor[3] = -81;
		sensor[4] = 2;
		sensor[5] = 0;
		sensor[6] = 0;
		sensor[7] = -46;
		d.seleccionarSensorT(sensor);
		int n=d.contarSensoresT();
		System.exit(0);
			
//		try {
//			opcion = System.in.read();
//			while(opcion!=-1){
//				switch (opcion) {
//				case 49://1 ASCII
//					int n=d.contarSensoresT();
//					System.out.println("Numero de sensores "+ n);
//					break;
//				case 50:
//					byte[][] l=d.listarSensoresT();
//					for(int i=0;i<d.n_sensores_t;i++){
//						String aux= new String(l[i]);
//						System.out.println(aux);
//					}							
//				default:
//					break;
//				}
//				opcion = System.in.read();
//			}
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
