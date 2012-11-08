package arduino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Implementación de la clase que establece la comunicación entre una placa Mega
 * con el shield 3G y el Software RegAdmin a través de sockets.
 * 
 * @author Jose Antonio Hernández Martínez (joherma1@gmail.com)
 */
public class Mega3G implements Arduino {
	// -----------------
	// Variables Arduino
	// -----------------
	private int n_sensores_t;
	public String sensores_t[];
	private boolean regando;
	private int n_alarmas;
	private long[] alarmas = new long[56];// Número máximo de alarmas en la placa

	// Variables sockets
	private final String servidor;
	private final int puerto = 80;
	private Socket socket;
	private BufferedReader entrada;
	private PrintWriter salida;

	/**
	 * Contructor de la clase Mega3G que inicializa la dirección IP del servidor
	 * 
	 * @param ip
	 *            Dirección IP de la placa Arduino
	 */
	public Mega3G(String ip) {
		servidor = ip;
	}

	/**
	 * Establece la conexión con la placa abriendo un socket con el servidor a
	 * traves del puerto 80
	 * 
	 * @return 0 -> OK; -1 -> UnknownHostException; -2 -> IOException
	 */
	public int initialize() {
		try {
			socket = new Socket(servidor, puerto);
			// conseguimos el canal de entrada
			entrada = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// conseguimos el canal de salida
			salida = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()), true);
			return 0;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -2;
		}
	}

	/**
	 * Cerramos el socket
	 */
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean startReg() {
		salida.println("sysreg d");
		return true;
	}

	public boolean stopReg() {
		salida.println("sysreg e");
		return false;
	}

	public boolean comprobarReg() {
		try {
			salida.println("sysreg f");
			char[] leido_char = new char[2];
			entrada.read(leido_char);
			if (leido_char[0] == '1')
				return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int contarSensoresT() {
		try {
			char[] leido_char = new char[3];
			salida.println("sysreg j");
			Thread.sleep(2000);
			entrada.read(leido_char);
			String leido = new String(leido_char);
			leido = leido.replace('\r', ' ').replace('\n', ' ').trim();
			this.n_sensores_t = Integer.parseInt(leido);
			return n_sensores_t;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	void resetearBusquedaT() {
		salida.println("sysreg k");
	}

	public String[] listarSensoresT() {
		try {
			this.contarSensoresT();
			this.resetearBusquedaT();
			this.sensores_t = new String[this.n_sensores_t];
			char[] leido = new char[16];
			for (int i = 0; i < this.n_sensores_t; i++) {
				salida.println("sysreg l");
				Thread.sleep(2000);
				entrada.read(leido);
				this.sensores_t[i] = new String(leido);
			}
			return this.sensores_t;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Float obtenerTemperatura(String sensor) {
		if (sensor == this.sensores_t[1]) {
			return new Float("34.2");
		} else {
			return new Float("24.2");
		}
	}

	public Long obtenerPresionBMP085() {
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Long(101325);
	}

	public Float obtenerTemperaturaBMP085() {
		return new Float(30);
	}

	public Float obtenerAlturaBMP085() {
		return new Float(0);
	}

	public Float obtenerHumedadHH10D() {
		return new Float(45.5);
	}

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
}
