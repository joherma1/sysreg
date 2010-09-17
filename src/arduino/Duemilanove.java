package arduino;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;

import javax.sql.rowset.spi.SyncResolver;

//MEJORA

public class Duemilanove implements SerialPortEventListener{
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
	"/dev/tty.usbserial-A700e0xk"};/*, // Mac OS X
		"/dev/ttyUSB0", // Linux
		"COM3", // Windows
	};*/
	/** Buffered input stream from the port */
	private InputStream input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	//Variables arduino
	int sensores_t=0;
	String sensores[]=null;
	String res= new String();

	public void initialize() {
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
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

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
		} catch (Exception e) {
			System.err.println(e.toString());
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

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */


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
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			synchronized (res) {
				res.notify();
			}
			//				try {
			//					int available = input.available();
			//					byte chunk[] = new byte[available];
			//					input.read(chunk, 0, available);
			//	
			//					// Displayed results are codepage dependent
			//					System.out.print(new String(chunk));
			//				} catch (Exception e) {
			//					System.err.println(e.toString());
			//				}

		}
		// Ignore all the other eventTypes, but you should consider the other ones.
	}

	//	public static void main(String[] args) throws Exception {
	//		Duemilanove main = new Duemilanove();
	//		main.initialize();
	//		System.out.println("Started");
	//		while(true){
	//			int value = System.in.read();
	//			main.output.write(value);
	//		}
	//	}

	private synchronized String leerArduinoHilos(){
		
		synchronized (res) {
			try {
				res.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				int available;
				try {
					available = input.available();
					byte data[] = new byte[available];
					int count = input.read(data, 0, available);
					if(count > 0){//convertimos el dato en ASCII a int
						String res = new String(data);
						return res;
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return null;
	}
	private  String leerArduino(){
		//********************************
		//MEJORA: QUE NO ESPERE, CON HILOS Y WAIT NOTIFY
		//*********************************
		try {
			//tenemos que esperar algo para que vuelque la informacion
			Thread.sleep(40);

			int available = input.available();
			byte data[] = new byte[available];
			int count = input.read(data, 0, available);
			if(count > 0){//convertimos el dato en ASCII a int
				String res = new String(data);
				return res;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int contarSensoresT(){
		try {
			output.write(0x6A);
			String res = leerArduino();
			if(res!=null)
				this.sensores_t = Integer.parseInt(res);
			else{
				this.sensores_t = -1;	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.sensores_t = -1;
		}
		return this.sensores_t;
	}
	public void resetearBusqueda(){
		try {
			output.write(0x6B);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[] listarSensores(){
		try {
			this.contarSensoresT();
			this.resetearBusqueda();
			this.sensores= new String[this.sensores_t];
			for(int i=0;i<this.sensores_t;i++){
				output.write(0x6C);
				String res =  leerArduino();
				this.sensores[i]=res;
			}
			this.resetearBusqueda();
			return this.sensores;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
