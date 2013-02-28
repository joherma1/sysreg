package arduino;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * Implementación de la clase que establece la comunicación entre una placa Uno
 * y el Software RegAdmin.
 * 
 * @author Jose Antonio Hernández Martínez (joherma1@gmail.com)
 */
@SuppressWarnings("restriction")
public class Uno implements SerialPortEventListener, Arduino {
	// Variables RXTX
	private SerialPort serialPort;
	// Flujo de entrada al puerto
	private InputStream input;
	// Flujo de salida al puerto
	private OutputStream output;
	// Milisegundos en espera a que el puerto se abra
	private static final int TIME_OUT = 2000;
	// Bits por segundos, debe coincidir con la placa Arduino
	private static final int DATA_RATE = 9600;

	// Variables Arduino
	private int n_sensores_t = 0;
	public String sensores_t[] = null;
	private byte sensores_tRaw[][] = null;
	private Monitor m = new Monitor();

	/**
	 * Inicializa la librería RXTX. Sigue el protocolo implementado en AlReg,
	 * esperando la confirmación (ACK). Por defecto coge el primer puerto de
	 * serie detectado, si este no es válido mostrara una lista para seleccionar
	 * el puerto correcto
	 * 
	 * @return 0 -> OK; -1 -> No se ha encontrado el puerto; -2 -> Error al
	 *         abrir el puerto
	 */
	public int initialize() {
		// Inicialización librería RXTX
		Enumeration portEnum;
		portEnum = CommPortIdentifier.getPortIdentifiers();
		// Iteramos buscando el puerto
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			if (currPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				String curr_port = currPortId.getName();
				System.out.println(curr_port);
				// Probamos que el puerto es correcto
				try {
					// Abrimos el puerto de serie, indicándole el nombre de la
					// aplicación (this.getClass().getName())
					serialPort = (SerialPort) currPortId.open("RegAdmin",
							TIME_OUT);

					// Parámetros del puerto
					serialPort.setSerialPortParams(DATA_RATE,
							SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
							SerialPort.PARITY_NONE);

					// Abrimos los flujos de comunicación
					input = serialPort.getInputStream();
					output = serialPort.getOutputStream();

					// Listeners
					serialPort.addEventListener(this);
					serialPort.notifyOnDataAvailable(true);
					// Tiempo de arranque, unos 1450
					// Le enviamos una petición de enlace
					// Inicializamos por petición aunque aceptamos la
					// inicialización por conexión
					output.write(0x05);
					byte[] res = this.leerArduinoBytes();
					if (res != null && res[0] == 6) {
						// Responde correctamente y ya no tenemos que seguir
						// buscando puerto
						return 0;
					} else { // Timeout o error de comunicación
						System.out
								.println("El puerto "
										+ currPortId.getName()
										+ " no responde correctamente a nuestra petición");
						// Cerramos el puerto
						this.close();
					}
				} catch (Exception e) {
					// Si ha habido un error al conectar probamos con el
					// siguiente
					System.out.println("Error al conectar con el puerto "
							+ currPortId.getName());
					System.out.println(e.toString());
					// Cerramos el puerto
					this.close();
				}
			}
		}
		// Si no hay ningún puerto de comunicación de serie válido
		System.out
				.println("No se ha podido encontrar un puerto de comunicación válido");
		return -1;
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
				// Cuando llegue información la depositamos
				// en el buffer del monitor
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
	private String leerArduino() {
		byte[] data = m.recoger();
		if (data == null) { // Si ha saltado el timeout
			return null;
		}
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
		// carácter EOT
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
	private byte[] leerArduinoBytes() {
		byte[] data = m.recoger();
		if (data == null) { // Si ha saltado el timeout
			return null;
		}
		while (data[data.length - 1] != 4) {
			// Mientras no nos diga fin de Transmisión seguimos esperando
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

	private void resetearBusquedaT() {
		try {
			output.write(0x6B);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int seleccionarSensorT(byte[] sensor) {
		// 0: CRC NO VALIDO 1: OK -1:No se han pasado 8 B -2: Excepción
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
			e.printStackTrace();
		}
		return -2;
	}

	// ------------------------
	// Métodos públicos
	// ------------------------
	/**
	 * Enviamos a la placa una señal para que active la válvula de riego
	 * 
	 * @return true: Señal enviada correctamente; false: En otro caso
	 */
	public boolean startReg() {
		try {
			output.write(0x64);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Enviamos a la placa una señal para que desactive la válvula de riego
	 * 
	 * @return true: Señal enviada correctamente; false: En otro caso
	 */
	public boolean stopReg() {
		try {
			output.write(0x65);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Devuelve el estado de la válvula de riego
	 * 
	 * @return true: Riego activo; false: En otro caso
	 */
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
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * PENDIENTE INSTALAR EN LA PLACA UNO EL RELÉ Enviamos a la placa una señal
	 * para que active el relé Como aun no está instalado el rele en dicha placa
	 * actua como startReg
	 * 
	 * @return true: Señal enviada correctamente; false: En otro caso
	 */
	public boolean startRele() {
		try {
			output.write(0x64); // CAMBIAR por 0x61
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * PENDIENTE INSTALAR EN LA PLACA UNO EL RELÉ Enviamos a la placa una señal
	 * para que desactive el relé Como aun no está instalado el rele en dicha
	 * placa actua como stopReg
	 * 
	 * @return true: Señal enviada correctamente; false: En otro caso
	 */
	public boolean stopRele() {
		try {
			output.write(0x65); // CAMBIAR por 0x62
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * PENDIENTE INSTALAR EN LA PLACA UNO EL RELÉ Devuelve el estado del relé
	 * Como aun no está instalado el relé en dicha placa actua como ComprobarReg
	 * 
	 * @return true: Relé activo; false: En otro caso
	 */
	public boolean comprobarRele() {
		try {
			output.write(0x66); // CAMBIAR por 0x63
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
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * PENDIENTE INSTALAR EN LA PLACA UNO LOS TRANSISTORES DEL SOLENOIDE
	 * Enviamos a la placa una señal para que active el solenoide Como aun no
	 * está instalado los transistores en dicha placa actua como startReg
	 * 
	 * @return true: Señal enviada correctamente; false: En otro caso
	 */
	public boolean startSolenoide3V() {
		try {
			output.write(0x64); // CAMBIAR por 0x67
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * PENDIENTE INSTALAR EN LA PLACA UNO LOS TRANSISTORES DEL SOLENOIDE
	 * Enviamos a la placa una señal para que desactive el solenoide Como aun no
	 * está instalado los transistores en dicha placa actua como stopReg
	 * 
	 * @return true: Señal enviada correctamente; false: En otro caso
	 */
	public boolean stopSolenoide3V() {
		try {
			output.write(0x65); // CAMBIAR por 0x68
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * PENDIENTE INSTALAR EN LA PLACA UNO LOS TRANSISTORES DEL SOLENOIDE
	 * Devuelve el estado del solenoide Como aun no está instalado los
	 * transistores en dicha placa actua como ComprobarReg
	 * 
	 * @return true: Relé activo; false: En otro caso
	 */
	public boolean comprobarSolenoide3V() {
		try {
			output.write(0x66); // CAMBIAR por 0x69
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
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Envía a la placa el comando para contar el número de sensores de
	 * temperatura DS18B20 conectados al protocolo One Wire
	 * 
	 * @return Número de sensores de temperatura conectados; -1: En caso de
	 *         error
	 */
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
			e.printStackTrace();
			this.n_sensores_t = -1;
		}
		return this.n_sensores_t;
	}

	/**
	 * Lista el identificador de los sensores de temperatura DS18B20 conectados
	 * al protocolo One Wire
	 * 
	 * @return Matriz de bytes con los identificadores de los sensores de
	 *         temperatura; null: En otro caso
	 */
	public String[] listarSensoresT() {
		try {
			this.contarSensoresT();
			this.resetearBusquedaT();
			this.sensores_tRaw = new byte[this.n_sensores_t][8];
			for (int i = 0; i < this.n_sensores_t; i++) {
				output.write(0x6C);
				byte res[] = leerArduinoBytes();
				this.sensores_tRaw[i] = res;
			}
			this.resetearBusquedaT();
			// Los índices son los mismos para sensores y sensores_raw
			sensores_t = new String[sensores_tRaw.length];
			for (int i = 0; i < sensores_tRaw.length; i++)
				sensores_t[i] = toHexadecimal(sensores_tRaw[i]);
			return this.sensores_t;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Convierte el vector de bytes a una cadena de caracteres
	 * 
	 * @param datos
	 *            El vector de bytes a transformar
	 * @return La cadena convertida
	 */
	private String toHexadecimal(byte[] datos) {
		String resultado = "";
		ByteArrayInputStream input = new ByteArrayInputStream(datos);
		String cadAux;
		int leido = input.read();
		while (leido != -1) {
			cadAux = Integer.toHexString(leido);
			if (cadAux.length() < 2) // Hay que añadir un 0
				resultado += "0";
			resultado += cadAux;
			leido = input.read();
		}
		return resultado;
	}

	/**
	 * Obtiene la temperatura del sensor de temperatura DS18B20 conectados al
	 * protocolo One Wire
	 * 
	 * @param sensor
	 *            Identificador del sensor DS18B20
	 * 
	 * @return Valor de temperatura leído; null: En otro caso
	 */
	public Float obtenerTemperatura(String sensor) {
		try {
			int i;
			// Buscamos el índice del sensor
			for (i = 0; i < sensores_t.length; i++)
				if (sensor.compareTo(sensores_t[i]) == 0)
					break;
			if (i == sensores_t.length)// No se ha encontrado el sensor
				return null;
			else {// Los dos índices coinciden --> Se ha encontrado el sensor
					// Seleccionamos el sensor
				if (seleccionarSensorT(sensores_tRaw[i]) != 1)
					return null;
				else {
					output.write(0x6E);
					// Tiempo necesario para la conversion, unos 1000¡
					String res = leerArduino();
					Float res_f = Float.parseFloat(res);
					return res_f;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtiene la presión del sensor analógico BMP085
	 * 
	 * @return Valor de presión leído; null: En otro caso
	 */
	public Long obtenerPresionBMP085() {
		try {
			output.write(0x71);
			String res = leerArduino();
			long res_l = Long.parseLong(res);
			return res_l;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtiene la temperatura del sensor analógico BMP085
	 * 
	 * @return Valor de temperatura leído; null: En otro caso
	 */
	public Float obtenerTemperaturaBMP085() {
		try {
			output.write(0x70);
			String res = leerArduino();
			Float res_f = Float.parseFloat(res);
			return res_f;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtiene la altura estimada del sensor analógico BMP085
	 * 
	 * @return Valor de altura leído; null: En otro caso
	 */
	public Float obtenerAlturaBMP085() {
		try {
			output.write(0x72);
			String res = leerArduino();
			Float res_f = Float.parseFloat(res);
			return res_f;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtiene la estimación del tiempo basandose en la altura y en la presión
	 * obtenida por el sensor BMP085
	 * 
	 * @return String con la descripción del tiempo: Soleado, nublado o lluvia
	 */
	public String obtenerEstimacionTiempoBMP085() {
		try {
			output.write(0x73);
			String res = leerArduino();
			return res;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtiene la humedad relativa del sensor analógico HH10D
	 * 
	 * @return Valor de humedad leído; null: En otro caso
	 */
	public Float obtenerHumedadHH10D() {
		try {
			output.write(0x75);
			String res = leerArduino();
			Float res_f = Float.parseFloat(res);
			return res_f;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Obtiene la humedad sensor resistivo de humedad del suelo
	 * 
	 * @return Valor de humedad leído; -1: En otro caso
	 */
	public int obtenerHumedadSuelo() {
		try {
			output.write(0x76);
			String res = leerArduino();
			Integer res_i = Integer.parseInt(res);
			return res_i;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Sincroniza la hora de la placa, en caso de no indicar hora (null) se
	 * establecerá la hora actual
	 * 
	 * @param tiempoUnix
	 *            Instante de tiempo que se establecerá en la placa
	 * @return true: Hora sincronizada correctamente; false: En otro caso
	 */
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
	 * @return ID de la alarma si se ha creado correctamente; -1 si no se ha
	 *         creado correctamente; -2 excepción
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
	 * @return ID de la alarma si se ha creado correctamente; -1 si no se ha
	 *         creado correctamente; -2 excepción
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
	 * @return ID de la alarma si se ha creado correctamente; -1 si no se ha
	 *         creado correctamente; -2 excepción
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
	 * @return ID de la alarma si se ha creado correctamente; -1 si no se ha
	 *         creado correctamente; -2 excepción
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
	 * @return true si se ha enviado correctamente la señal de borrado; false
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
			e.printStackTrace();
			return false;
		}
	}

	public class Monitor {
		private byte[] buffer;
		private boolean vacio = true;

		/**
		 * Variable bandera para saber si ha saltado el timeout
		 */
		// private boolean timeout = true;

		public synchronized byte[] recoger() {
			if (vacio == true) {
				try {
					wait(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			vacio = true;
			byte[] res = buffer;
			buffer = null;
			// timeout = true;
			return res;
		}

		public synchronized void depositar(byte[] data) {
			buffer = data;
			vacio = false;
			// timeout = false;
			notify();
		}
	}

	public static void main(String[] args) {
		Uno d = new Uno();
		// if (d.initialize() == 0) {
		// System.out.println("Numero de sensores " + d.contarSensoresT());
		// byte[][] l = d.listarSensoresT();
		// for (int i = 0; i < d.n_sensores_t; i++) {
		// for (int j = 0; j < l[i].length; j++)
		// System.out.print(String.format("%02X", l[i][j]) + " ");
		// System.out.println();
		// }
		// }
		// d.establecerHora(1321873956L); // 12:12:37D
		// System.out.println(d.establecerAlarmaRepOn(12, 13, 00));
		// System.out.println(d.establecerAlarmaRepOff(12, 13, 10));
		// System.out.println(d.eliminarAlarmas());

		// System.out.println("1-Contar sensores");
		// System.out.println("2-Listar sensores");
		// System.out.println("3-Obtener temperatura del 1r sensor");
		// System.out.println("4-Obtener temperatura del 2o sensor");
		// int opcion;
		// for (int ii = 0; ii < 1; ii++) {
		// int n=d.contarSensoresT();
		// System.out.println("Numero de sensores " + n);
		// byte[][] l = d.listarSensoresT();
		// for (int i = 0; i < d.n_sensores_t; i++) {
		// String aux = new String(l[i]);
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
		//
		// try {
		// opcion = System.in.read();
		// while (opcion != -1) {
		// switch (opcion) {
		// case 49:// 1 ASCII
		// int n = d.contarSensoresT();
		// System.out.println("Numero de sensores " + n);
		// break;
		// case 50:
		// byte[][] l = d.listarSensoresT();
		// for (int i = 0; i < d.n_sensores_t; i++) {
		// String aux = new String(l[i]);
		// System.out.println(aux);
		// }
		// default:
		// break;
		// }
		// opcion = System.in.read();
		// }
		//
		// } catch (IOException e) {
		//
		// e.printStackTrace();
		// }
		d.close();
		System.exit(0);
	}
}
