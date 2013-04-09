package org.sysreg.regadmin.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.sysreg.regadmin.client.service.AlRegService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class AlRegServiceImpl extends RemoteServiceServlet implements
		AlRegService {

	// Variables sockets
	private int puerto = 80;

	@Override
	public void activarRele(String servidor) {
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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void desactivarRele(String servidor) {
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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean comprobarRele(String servidor) {
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

	@Override
	public void startReg(String servidor) {
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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stopReg(String servidor) {
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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean comprobarReg(String servidor) {
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

	@Override
	public void startSolenoide3V(String servidor) {
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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stopSolenoide3V(String servidor) {
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
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean comprobarSolenoide3V(String servidor) {
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

	@Override
	public int contarSensoresTemp(String servidor) {
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

	@Override
	public String[] listarSensoresTemp(String servidor) {
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

			int n_sensores_t = Integer.parseInt(new String(res_char).trim());

			// Reseteamos la busqueda
			salida.println("sysreg k");
			// Comprobamos que le ha llegado y responde
			res = 0;
			do {
				res = entrada.read();
			} while (res != 4);

			// Obtenemos los sensores
			String[] sensores_t = new String[n_sensores_t];
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

			// Cerramos la conexion
			salida.close();
			entrada.close();
			socket.close();
			return sensores_t;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Float obtenerTemperatura(String servidor, String sensor) {
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
			// System.out.println(sensor + " m " + System.currentTimeMillis());

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
			// System.out.println(sensor + " n " + System.currentTimeMillis());

			salida.close();
			entrada.close();
			socket.close();
			return Float.parseFloat(new String(res_char).trim());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Float obtenerTemperaturaBMP085(String servidor) {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg p");
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

	@Override
	public Long obtenerPresionBMP085(String servidor) {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg q");
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

	@Override
	public Float obtenerAlturaBMP085(String servidor) {
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

	@Override
	public String obtenerEstimacionTiempoBMP085(String servidor) {
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

	@Override
	public Float obtenerHumedadHH10D(String servidor) {
		try {
			Socket socket = new Socket(servidor, puerto);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);

			salida.println("sysreg u");
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
}
