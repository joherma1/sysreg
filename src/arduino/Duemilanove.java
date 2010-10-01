package arduino;

import java.awt.Event;
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

public class Duemilanove{
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
	"/dev/tty.usbserial-A700e0xk", // Mac OS X
	"/dev/ttyUSB0", // Linux
	"COM3", // Windows
	};
	/** Buffered input stream from the port */
	private InputStream input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	//Variables arduino
	int n_sensores_t=0;
	public byte sensores_t[][]=null;

	public void initialize() {
		//Inicializaci—n libreria RXTX
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
			serialPort = (SerialPort) portId.open(this.getClass().getName(),TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();
			//Tiempo de arranque, unos 1450
			Thread.sleep(1500);
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

	private  String leerArduino(){
		try {
			//tenemos que esperar algo para que vuelque la informacion
			//contarSensores = 40
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

	byte[] leerArduinoBytes(){
		try {
			//tenemos que esperar algo para que vuelque la informacion
			//listarSensoresT = 28
			Thread.sleep(30);
			int available = input.available();
			byte data[] = new byte[available];
			input.read(data, 0, available);
			return data;
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
	public void resetearBusquedaT(){
		try {
			output.write(0x6B);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	int seleccionsSensorT(byte[] sensor){ //0: CRC NO VALIDO		1: OK	-1:No se han pasado 8 B		-2:Excepcion
		try {
			//el comando es mXXXXXXXX
			byte[] comando= {0x6D,sensor[0],sensor[1],sensor[2],sensor[3],sensor[4],sensor[5],sensor[6],sensor[7]};
			output.write(comando);
			//Tiempo seleccionar cursor, 6ms
			Thread.sleep(10);
			int res= Integer.parseInt(leerArduino());
			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -2;
	}
	public Float obtenerTemperatura(byte[] sensor){
		try {
			//Seleccionamos el sensor
			if(seleccionsSensorT(sensor) != 1)
				return null;
			else {
				output.write(0x6E);
				//Tiempo necesario para la conversion
				Thread.sleep(1000);
				String res= leerArduino();
				Float res_f= Float.parseFloat(res);
				return res_f;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
}
