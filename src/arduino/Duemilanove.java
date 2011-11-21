package arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Enumeration;

/**
 * Implementación de la clase que establece la comunicación entre una placa
 * Duemilanove y el Software RegAdmin.
 * 
 * @author Jose Antonio Hernández Martínez (joherma1@gmail.com)
 */
@SuppressWarnings("restriction")
public class Duemilanove implements SerialPortEventListener, Arduino {
	// Variables RXTX
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "/dev/tty.usbserial-A700e0xk", // Mac
			"/dev/tty.usbmodem1d11", "/dev/ttyUSB0", // Linux
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

	// Variables Arduino
	int n_sensores_t = 0;
	public byte sensores_t[][] = null;
	Monitor m = new Monitor();

	/**
	 * Inicializa la librería RXTX. Sigue el protocolo implementado en AlReg,
	 * esperando la confirmación (ACK).
	 * 
	 * @return 0 -> OK; -1 -> No se ha encontrado el puerto; -2 -> Error al
	 *         abrir el puerto
	 */
	public int initialize() {
		// Inicialización librería RXTX
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// iterate through, looking for the port
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
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
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			input = serialPort.getInputStream();
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
			// Tiempo de arranque, unos 1450
			// Le enviamos una petición de enlace
			// Inicializamos por petición aunque aceptamos la inicialización por
			// conexión
			output.write(0x05);
			if (this.leerArduinoBytes()[0] == 6)// Arduino nos responde con ACK
				return 0;
			else
				return -2;
		} catch (Exception e) {
			System.err.println(e.toString());
			return -2;
		}
	}

	/**
	 * Cierra la conexión correctamente. Es recomendable utilizarla en
	 * plataformas UNIX para evitar bloqueos del puerto.
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
				// Cuando llegue información la depositamos en el buffer del
				// monitor
				m.depositar(chunk);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignoramos el resto de eventos
	}

	// ---------------------------
	// Métodos privados al paquete
	// ---------------------------
	/**
	 * Lee de Arduino una cadena de caracteres. Se ha implementado con un
	 * monitor, de manera que cuando llegue la información se lanzará un evento
	 * que despertara el hilo.
	 * 
	 * @return La cadena leída
	 */
	String leerArduino() {
		byte[] data = m.recoger();
		while (data[data.length - 1] != 4) {// Mientras no nos diga fin de
			// Transmisión seguimos esperando
			byte[] data2 = m.recoger();
			// creamos el nuevo vector y lo rellenamos
			byte[] aux = new byte[data.length + data2.length];
			for (int i = 0; i < aux.length; i++) {
				if (i < data.length)
					aux[i] = data[i];
				else
					aux[i] = data2[i - data.length];
			}
			data = aux;
		}
		// ya tenemos todo el mensaje, lo pasamos a string eliminando el
		// carácter
		// EOT
		String res = new String(data, 0, data.length - 1);
		return res;
	}

	/**
	 * Lee de Arduino una vector de Bytes. Se ha implementado con un monitor, de
	 * manera que cuando llegue la información se lanzará un evento que
	 * despertara el hilo.
	 * 
	 * @return Los Bytes leídos
	 */
	byte[] leerArduinoBytes() {
		byte[] data = m.recoger();
		while (data[data.length - 1] != 4) {// Mientras no nos diga fin de
			// Transmisión seguimos esperando
			byte[] data2 = m.recoger();
			// creamos el nuevo vector y lo rellenamos
			byte[] aux = new byte[data.length + data2.length];
			for (int i = 0; i < aux.length; i++) {
				if (i < data.length)
					aux[i] = data[i];
				else
					aux[i] = data2[i - data.length];
			}
			data = aux;
		}
		// ya tenemos todo el mensaje,eliminamos el carácter EOT
		byte[] res = new byte[data.length - 1];
		for (int i = 0; i < res.length; i++)
			res[i] = data[i];
		return res;
	}

	void resetearBusquedaT() {
		try {
			output.write(0x6B);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	int seleccionarSensorT(byte[] sensor) {
		// 0: CRC NO VALIDO 1: OK -1:No se han pasado 8 B -2:Excepcion
		try {
			// el comando es mXXXXXXXX
			byte[] comando = { 0x6D, sensor[0], sensor[1], sensor[2],
					sensor[3], sensor[4], sensor[5], sensor[6], sensor[7] };
			output.write(comando);
			// Tiempo seleccionar cursor, 6ms
			String res1 = leerArduino();
			int res = Integer.parseInt(res1);
			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -2;
	}

	// ------------------------
	// Métodos públicos
	// ------------------------
	public boolean startReg() {
		try {
			output.write(0x64);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean stopReg() {
		try {
			output.write(0x65);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean comprobarReg() {
		try {
			output.write(0x66);
			String res = leerArduino();
			if (res != null) {
				int estado = Integer.parseInt(res);
				if (estado == 1)
					return true;
				else
					return false;
			} else {
				return false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public int contarSensoresT() {
		try {
			output.write(0x6A);
			String res = leerArduino();
			if (res != null)
				this.n_sensores_t = Integer.parseInt(res);
			else {
				this.n_sensores_t = -1;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.n_sensores_t = -1;
		}
		return this.n_sensores_t;
	}

	public byte[][] listarSensoresT() {
		try {
			this.contarSensoresT();
			this.resetearBusquedaT();
			this.sensores_t = new byte[this.n_sensores_t][8];
			for (int i = 0; i < this.n_sensores_t; i++) {
				output.write(0x6C);
				byte res[] = leerArduinoBytes();
				this.sensores_t[i] = res;
			}
			this.resetearBusquedaT();
			return this.sensores_t;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Float obtenerTemperatura(byte[] sensor) {
		try {
			// Seleccionamos el sensor
			if (seleccionarSensorT(sensor) != 1)
				return null;
			else {
				output.write(0x6E);
				// Tiempo necesario para la conversion, unos 1000¡
				String res = leerArduino();
				Float res_f = Float.parseFloat(res);
				return res_f;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Long obtenerPresionBMP085() {
		try {
			output.write(0x71);
			String res = leerArduino();
			long res_l = Long.parseLong(res);
			return res_l;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Float obtenerTemperaturaBMP085() {
		try {
			output.write(0x70);
			String res = leerArduino();
			Float res_f = Float.parseFloat(res);
			return res_f;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Float obtenerAlturaBMP085() {
		try {
			output.write(0x72);
			String res = leerArduino();
			Float res_f = Float.parseFloat(res);
			return res_f;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public Float obtenerHumedadHH10D() {
		try {
			output.write(0x75);
			String res = leerArduino();
			Float res_f = Float.parseFloat(res);
			return res_f;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public boolean establecerHora(Long tiempoUnix) {
		Long tiempo;
		if (tiempoUnix == null)
			tiempo = System.currentTimeMillis() / 1000L;
		else
			tiempo = tiempoUnix;
		try {
			// Hay que pasarle la información en código ASCII, la placa
			// arduino lee caracteres de texto
			// Si queremos que enviarle la cadena A1321805111 tendremos que
			// escribir los bytes 0x41,49,51,50,49,56,48,53,49,49,49
			byte[] t = tiempo.toString().getBytes("US-ASCII");
			// Tendrá de ancho 10, además la placa está programada para leer
			// 10 caracteres
			byte[] comando = new byte[t.length + 1];
			comando[0] = 0x41; // A 65 0x41 establecer hora
			for (int i = 0; i < t.length; i++)
				comando[i + 1] = t[i];
			output.write(comando);
			String res = leerArduino();
			int res_l = Integer.parseInt(res);
			if (res_l == 1)
				return true;
			else
				return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Establece una alarma de encendido de riego en la placa. El tiempo se pasa
	 * en formato UNIX
	 * 
	 * @param tiempoUnix
	 *            Instante de tiempo en el que se activará el riego
	 * @return ID de la alarma si se ha creado correctamente, -1 si no se ha
	 *         creado correctamente, -2 excepción
	 */
	public int establecerAlarmaOn(Long tiempoUnix) {
		if (tiempoUnix == null)
			return -1;
		try {
			// Hay que pasarle la información en código ASCII, la placa
			// arduino lee caracteres de texto
			// Si queremos que enviarle la cadena B1321805121 tendremos que
			// escribir los bytes 0x42,49,51,50,49,56,48,53,49,50,49
			byte[] t = tiempoUnix.toString().getBytes("US-ASCII");
			byte[] comando = new byte[t.length + 1];
			comando[0] = 0x42; // B 66 0x42 establecerAlarmaOn
			for (int i = 0; i < t.length; i++)
				comando[i + 1] = t[i];
			output.write(comando);
			String res = leerArduino();
			int res_l = Integer.parseInt(res);
			if (res_l != -1)
				return res_l;
			else
				// error
				return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}

	/**
	 * Establece una alarma de apagado de riego en la placa. El tiempo se pasa
	 * en formato UNIX
	 * 
	 * @param tiempoUnix
	 *            Instante de tiempo en el que se desactivará el riego
	 * @return ID de la alarma si se ha creado correctamente, -1 si no se ha
	 *         creado correctamente, -2 excepción
	 */
	public int establecerAlarmaOff(Long tiempoUnix) {
		if (tiempoUnix == null)
			return -1;
		try {
			// Hay que pasarle la información en código ASCII, la placa
			// arduino lee caracteres de texto
			// Queremos que enviarle la cadena C1321805131 tendremos que
			// escribir los bytes 0x43,49,51,50,49,56,48,53,49,51,49
			byte[] t = tiempoUnix.toString().getBytes("US-ASCII");
			byte[] comando = new byte[t.length + 1];
			comando[0] = 0x43; // C 67 0x43 establecerAlarmaOff
			for (int i = 0; i < t.length; i++)
				comando[i + 1] = t[i];
			output.write(comando);
			String res = leerArduino();
			int res_l = Integer.parseInt(res);
			if (res_l != -1)
				return res_l;
			else
				// error
				return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}

	/**
	 * Establece una alarma de encendido repetido de riego en la placa. La hora
	 * exacta se pasa por parámetros
	 * 
	 * @param horas
	 *            Hora, en formato 24 horas.
	 * @param minutos
	 * @param segundos
	 * @return ID de la alarma si se ha creado correctamente, -1 si no se ha
	 *         creado correctamente, -2 excepción
	 */
	public int establecerAlarmaRepOn(int horas, int minutos, int segundos) {
		try {
			// Hay que pasarle la información en código ASCII
			// Queremos enviarle una cadena del estilo D130500
			// 0x44,49,51,48,56,8,48
			byte[] hh = String.format("%02d", horas).getBytes("US-ASCII");
			byte[] mm = String.format("%02d", minutos).getBytes("US-ASCII");
			byte[] ss = String.format("%02d", segundos).getBytes("US-ASCII");
			byte[] comando = { 0x44, hh[0], hh[1], mm[0], mm[1], ss[0], ss[1] };
			output.write(comando);
			String res = leerArduino();
			int res_l = Integer.parseInt(res);
			if (res_l != -1)
				return res_l;
			else
				// error
				return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}

	/**
	 * Establece una alarma de apagado repetido de riego en la placa. La hora
	 * exacta se pasa por parámetros
	 * 
	 * @param horas
	 *            Hora, en formato 24 horas.
	 * @param minutos
	 * @param segundos
	 * @return ID de la alarma si se ha creado correctamente, -1 si no se ha
	 *         creado correctamente, -2 excepción
	 */
	public int establecerAlarmaRepOff(int horas, int minutos, int segundos) {
		try {
			// Hay que pasarle la información en código ASCII
			// Queremos enviarle una cadena del estilo E130500
			// 0x45,49,51,48,56,8,48
			byte[] hh = String.format("%02d", horas).getBytes("US-ASCII");
			byte[] mm = String.format("%02d", minutos).getBytes("US-ASCII");
			byte[] ss = String.format("%02d", segundos).getBytes("US-ASCII");
			byte[] comando = { 0x45, hh[0], hh[1], mm[0], mm[1], ss[0], ss[1] };
			output.write(comando);
			String res = leerArduino();
			int res_l = Integer.parseInt(res);
			if (res_l != -1)
				return res_l;
			else
				// error
				return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
	}

	/**
	 * Elimina la alarma con el identificador indicado. Actualmente no se usa.
	 * Simplemente libera el disparador de la alarma indicada, por lo que no
	 * comprueba si la alarma existe o no
	 * 
	 * @param alarmaId
	 *            Identificador de la alarma 0 <= id < 255
	 * @return true si se ha enviado correctamente la señal de borrado, false
	 *         número enviado no válido
	 */
	public boolean eliminarAlarma(int alarmaId) {
		try {
			// Hay que pasarle la información en código ASCII
			// Queremos enviarle una cadena del estilo Fx ó Fxx ó Fxxx
			// 0x46,49
			if (alarmaId < 255) {
				byte[] id = Integer.toString(alarmaId).getBytes("US-ASCII");
				byte[] comando = new byte[id.length + 1];
				comando[0] = 0x46;
				for (int i = 0; i < id.length; i++)
					comando[i + 1] = id[i];
				output.write(comando);
				String res = leerArduino();
				int res_l = Integer.parseInt(res);
				if (res_l == 1)
					return true;
			} // error
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Elimina todas las alarmas. El número máximo de alarmas viene indicado en
	 * la librería TimeAlarms.h de Arduino, como dtNBR_ALARMS
	 * 
	 * @return true si se ha enviado correctamente la orden de eliminar, false
	 *         en otro caso
	 */
	public boolean eliminarAlarmas() {
		try {
			output.write(0x47);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public class Monitor {
		private byte[] buffer;
		private boolean vacio = true;

		public synchronized byte[] recoger() {
			while (vacio == true) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			vacio = true;
			byte[] res = buffer;
			buffer = null;
			return res;
		}

		public synchronized void depositar(byte[] data) {
			buffer = data;
			vacio = false;
			notify();
		}
	}

	public static void main(String[] args) {
		Duemilanove d = new Duemilanove();
		d.initialize();
		d.establecerHora(1321873956L); // 12:12:37D
		System.out.println(d.establecerAlarmaRepOn(12, 13, 00));
		System.out.println(d.establecerAlarmaRepOff(12, 13, 10));
		System.out.println(d.eliminarAlarmas());
		// System.out.println(d.establecerAlarmaOn(1321805121L));
		// System.out.println(d.establecerAlarmaOff(1321805131L));
		// System.out.println(d.establecerHora(null));
		// System.out.println("1-Contar sensores");
		// System.out.println("2-Listar sensores");
		// System.out.println("3-Obtener temperatura del 1r sensor");
		// System.out.println("4-Obtener temperatura del 2o sensor");
		// int opcion;
		// for (int ii = 0; ii < 1; ii++) {
		// // int n=d.contarSensoresT();
		// System.out.println("Numero de sensores "+ n);
		// byte[][] l=d.listarSensoresT();
		// for(int i=0;i<d.n_sensores_t;i++){
		// String aux= new String(l[i]);
		// System.out.println(aux);
		// }
		// System.out.println("Temperatura Sensor 1");
		// d.obtenerTemperatura(l[0]);
		// System.out.println("Ya listados");
		// d.obtenerTemperatura(l[1]);
		// }
		// byte[] sensor = new byte[8];
		// sensor[0] = 40;
		// sensor[1] = -11;
		// sensor[2] = -23;
		// sensor[3] = -81;
		// sensor[4] = 2;
		// sensor[5] = 0;
		// sensor[6] = 0;
		// sensor[7] = -46;
		// d.seleccionarSensorT(sensor);
		// int n = d.contarSensoresT();
		// d.close();
		// System.exit(0);

		// try {
		// opcion = System.in.read();
		// while(opcion!=-1){
		// switch (opcion) {
		// case 49://1 ASCII
		// int n=d.contarSensoresT();
		// System.out.println("Numero de sensores "+ n);
		// break;
		// case 50:
		// byte[][] l=d.listarSensoresT();
		// for(int i=0;i<d.n_sensores_t;i++){
		// String aux= new String(l[i]);
		// System.out.println(aux);
		// }
		// default:
		// break;
		// }
		// opcion = System.in.read();
		// }
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}
