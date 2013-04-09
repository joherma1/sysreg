package arduino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Implementación de la clase que establece la comunicación entre una placa
 * Arduino con el shield Ethernet el Software RegAdmin a través de sockets. En
 * este caso las conexiones se cerrarán despues de enviar cada comando, para
 * poder liberar la placa
 * 
 * @author Jose Antonio Hernández Martínez (joherma1@gmail.com)
 */
public class Ethernet implements Arduino {
	// Variables Arduino
	private int n_sensores_t;
	public String sensores_t[];
	private int n_alarmas;
	private long[] alarmas = new long[56];// Número máximo de alarmas en la
											// placa

	// Variables sockets
	private final String servidor;
	private final int puerto = 80;

	/**
	 * Contructor de la clase Ethernet que inicializa la dirección IP del
	 * servidor
	 * 
	 * @param ip
	 *            Dirección IP de la placa Arduino
	 */
	public Ethernet(String ip) {
		servidor = ip;
	}

	/**
	 * En la inicialización comprobamos que todo esta correctamente, haciendo un
	 * test de la conexión
	 */
	public int initialize() {
		try {
			Socket socket = new Socket(servidor, puerto);
			// conseguimos el canal de entrada
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// conseguimos el canal de salida
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			socket.close();
			salida.close();
			entrada.close();
			return 0;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -2;
		}
	}

	public void close() {
	}

	public boolean startRele() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg a");
			// Comprobamos que le ha llegado y responde
			int res = 0;
			do {
				res = entrada.read();
			} while (res != 4);

			entrada.close();
			salida.close();
			socket.close();
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean stopRele() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg b");
			// Comprobamos que le ha llegado y responde
			int res = 0;
			do {
				res = entrada.read();
			} while (res != 4);

			entrada.close();
			salida.close();
			socket.close();
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean comprobarRele() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg c");
			int i = 0, res = 0;
			char[] res_char = new char[2];
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4); // Mientras no nos llegue el codigo de EOT

			salida.close();
			entrada.close();
			socket.close();
			if (res_char[0] == '1')
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean startReg() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg d");
			// Comprobamos que le ha llegado y responde
			int res = 0;
			do {
				res = entrada.read();
			} while (res != 4);

			entrada.close();
			salida.close();
			socket.close();
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean stopReg() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg e");
			// Comprobamos que le ha llegado y responde
			int res = 0;
			do {
				res = entrada.read();
			} while (res != 4);

			entrada.close();
			salida.close();
			socket.close();
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean comprobarReg() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg f");
			int i = 0, res = 0;
			char[] res_char = new char[2];
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4); // Mientras no nos llegue el codigo de EOT

			salida.close();
			entrada.close();
			socket.close();
			if (res_char[0] == '1')
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean startSolenoide3V() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg g");
			// Comprobamos que le ha llegado y responde
			int res = 0;
			do {
				res = entrada.read();
			} while (res != 4);

			entrada.close();
			salida.close();
			socket.close();
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean stopSolenoide3V() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg h");
			// Comprobamos que le ha llegado y responde
			int res = 0;
			do {
				res = entrada.read();
			} while (res != 4);

			entrada.close();
			salida.close();
			socket.close();
			return true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean comprobarSolenoide3V() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg i");
			int i = 0, res = 0;
			char[] res_char = new char[2];
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4); // Mientras no nos llegue el codigo de EOT

			salida.close();
			entrada.close();
			socket.close();
			if (res_char[0] == '1')
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int contarSensoresT() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg j");
			int i = 0, res = 0;
			char[] res_char = new char[3];
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4);// Mientras no sea final de transmision seguimos
								// leyendo

			salida.close();
			entrada.close();
			socket.close();
			return Integer.parseInt(new String(res_char).trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Lista el identificador de los sensores de temperatura DS18B20 conectados
	 * al protocolo One Wire. Utiliza una única conexión
	 * 
	 * @return Matriz de bytes con los identificadores de los sensores de
	 *         temperatura; null: En otro caso
	 */
	public String[] listarSensoresT() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			// Contamos los sensores
			salida.println("sysreg j");
			int i = 0, res = 0;
			char[] res_char = new char[4];
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4);// Mientras no sea final de transmision seguimos
								// leyendo

			this.n_sensores_t = Integer.parseInt(new String(res_char).trim());

			// Reseteamos la búsqueda
			salida.println("sysreg k");
			// Comprobamos que le ha llegado y responde
			res = 0;
			do {
				res = entrada.read();
			} while (res != 4);

			// Obtenemos los sensores
			this.sensores_t = new String[this.n_sensores_t];
			res_char = new char[17]; // 16 identificador + EOT
			for (int j = 0; j < n_sensores_t; j++) {
				salida.println("sysreg l");
				i = 0;
				do {
					res = entrada.read();
					res_char[i] = (char) res;
					i++;
				} while (res != 4);// Mientras no sea final de transmision
									// seguimos
									// leyendo

				sensores_t[j] = new String(res_char);
			}

			// Cerramos la conexión
			salida.close();
			entrada.close();
			socket.close();
			return this.sensores_t;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Obtiene la temperatura del sensor de temperatura DS18B20 conectados al
	 * protocolo One Wire. Lo hacemos en una única conexión.
	 * 
	 * 
	 * @param sensor
	 *            Identificador del sensor DS18B20
	 * 
	 * @return Valor de temperatura leído; null: En otro caso
	 */
	public Float obtenerTemperatura(String sensor) {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			// Seleccionamos el sensor
			salida.println("sysreg m " + sensor);
			int i = 0, res = 0;
			char[] res_char = new char[2];
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4); // Mientras no nos llegue el codigo de EOT

			// Obtenemos la temperatura
			salida.println("sysreg n");
			// recibimos la respuesta del servidor
			i = res = 0;
			res_char = new char[8]; // reinicializamos por si ha leido mas de la
									// cuenta
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4);

			salida.close();
			entrada.close();
			socket.close();
			return Float.parseFloat(new String(res_char).trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Float obtenerTemperaturaBMP085() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg p");
			// recibimos la respuesta del servidor
			int i = 0, res = 0;
			char[] res_char = new char[8];
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4);

			salida.close();
			entrada.close();
			socket.close();
			return Float.parseFloat(new String(res_char));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Long obtenerPresionBMP085() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg q");
			// recibimos la respuesta del servidor
			int i = 0, res = 0;
			char[] res_char = new char[20];
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4);

			salida.close();
			entrada.close();
			socket.close();
			return Long.parseLong(new String(res_char).trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Float obtenerAlturaBMP085() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg r");
			int i = 0, res = 0;
			char[] res_char = new char[10];
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4);

			salida.close();
			entrada.close();
			socket.close();
			return Float.parseFloat(new String(res_char));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String obtenerEstimacionTiempoBMP085() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg s");
			int i = 0, res = 0;
			char[] res_char = new char[25];
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4);

			salida.close();
			entrada.close();
			socket.close();
			return new String(res_char).trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Float obtenerHumedadHH10D() {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg u");
			// recibimos la respuesta del servidor
			int i = 0, res = 0;
			char[] res_char = new char[6];
			do {
				res = entrada.read();
				res_char[i] = (char) res;
				i++;
			} while (res != 4);

			salida.close();
			entrada.close();
			socket.close();
			return Float.parseFloat(new String(res_char));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// ------------------------------
	// Métodos no implementados, copiados de FAKE.java
	// ------------------------------
	public int obtenerHumedadSuelo() {
		return 50;
	}

	public boolean establecerHora(Long tiempoUnix) {
		return true;
	}

	public int establecerAlarmaOn(Long tiempoUnix) {
		this.alarmas[n_alarmas] = tiempoUnix;
		return n_alarmas++;
	}

	public int establecerAlarmaOff(Long tiempoUnix) {
		this.alarmas[n_alarmas] = tiempoUnix;
		return n_alarmas++;
	}

	public int establecerAlarmaRepOn(int horas, int minutos, int segundos) {
		this.alarmas[n_alarmas] = horas * 24 + minutos * 60 + segundos;
		return n_alarmas++;
	}

	public int establecerAlarmaRepOff(int horas, int minutos, int segundos) {
		this.alarmas[n_alarmas] = horas * 24 + minutos * 60 + segundos;
		return n_alarmas++;
	}

	public boolean eliminarAlarma(int alarmaId) {
		n_alarmas++;
		return true;
	}

	public boolean eliminarAlarmas() {
		n_alarmas = 0;
		return false;
	}

	// Métodos por implementar, partimos de la base de Mega3G
	// /**
	// * Cerramos el socket
	// */
	//
	//
	// public boolean startReg() {
	// salida.println("sysreg d");
	// return true;
	// }
	//
	// public boolean stopReg() {
	// salida.println("sysreg e");
	// return false;
	// }
	//
	// public boolean comprobarReg() {
	// try {
	// salida.println("sysreg f");
	// char[] leido_char = new char[2];
	// entrada.read(leido_char);
	// if (leido_char[0] == '1')
	// return true;
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return false;
	// }
	//
	// public boolean startSolenoide3V() {
	// salida.println("sysreg g");
	// return true;
	// }
	//
	// public boolean stopSolenoide3V() {
	// salida.println("sysreg h");
	// return false;
	// }
	//
	// public boolean comprobarSolenoide3V() {
	// try {
	// salida.println("sysreg i");
	// char[] leido_char = new char[2];
	// entrada.read(leido_char);
	// if (leido_char[0] == '1')
	// return true;
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return false;
	// }
	//
	// public Long obtenerPresionBMP085() {
	// try {
	// Thread.sleep(800);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// return new Long(101325);
	// }
	//
	// public Float obtenerTemperaturaBMP085() {
	// return new Float(30);
	// }
	//
	// public Float obtenerAlturaBMP085() {
	// return new Float(0);
	// }
	//
	//
	// public int obtenerHumedadSuelo() {
	// return 50;
	// }
	//
	// public boolean establecerHora(Long tiempoUnix) {
	// return true;
	// }
	//
	// public int establecerAlarmaOn(Long tiempoUnix) {
	// this.alarmas[n_alarmas] = tiempoUnix;
	// return n_alarmas++;
	// }
	//
	// public int establecerAlarmaOff(Long tiempoUnix) {
	// this.alarmas[n_alarmas] = tiempoUnix;
	// return n_alarmas++;
	// }
	//
	// public int establecerAlarmaRepOn(int horas, int minutos, int segundos) {
	// this.alarmas[n_alarmas] = horas * 24 + minutos * 60 + segundos;
	// return n_alarmas++;
	// }
	//
	// public int establecerAlarmaRepOff(int horas, int minutos, int segundos) {
	// this.alarmas[n_alarmas] = horas * 24 + minutos * 60 + segundos;
	// return n_alarmas++;
	// }
	//
	// public boolean eliminarAlarma(int alarmaId) {
	// n_alarmas++;
	// return true;
	// }
	//
	// public boolean eliminarAlarmas() {
	// n_alarmas = 0;
	// return false;
	// }
}
