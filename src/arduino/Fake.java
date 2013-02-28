package arduino;

public class Fake implements Arduino {
	// -----------------
	// Variables Arduino
	// -----------------
	int n_sensores_t;
	public String sensores_t[];
	boolean regando;
	boolean rele;
	boolean solenoide3V;
	int n_alarmas;
	long[] alarmas = new long[56];// Número máximo de alarmas en la placa

	public boolean startReg() {
		regando = true;
		return true;
	}

	public boolean stopReg() {
		regando = false;
		return false;
	}

	public boolean comprobarReg() {
		// Con regando simulamos el ultimo estado que hayamos puesto
		// iniciando la placa en off
		return regando; // Siempre off
	}

	public int contarSensoresT() {
		this.n_sensores_t = 2;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return 2;
	}

	public String[] listarSensoresT() {
		this.contarSensoresT();
		this.sensores_t = new String[this.n_sensores_t];
		this.sensores_t[0] = "00000000000000";
		this.sensores_t[1] = "11111111111111";
		return this.sensores_t;
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

	public String obtenerEstimacionTiempoBMP085() {
		return "Soleado";
	}

	public Float obtenerHumedadHH10D() {
		return new Float(45.5);
	}

	public int obtenerHumedadSuelo() {
		return 50;
	}

	public int initialize() {
		return 0;
	}

	public void close() {
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

	public boolean startRele() {
		regando = true;
		return true;
	}

	public boolean stopRele() {
		rele = false;
		return false;
	}

	public boolean comprobarRele() {
		// Con rele simulamos el ultimo estado que hayamos puesto
		// iniciando la placa en off
		return rele; // Siempre off
	}

	public boolean startSolenoide3V() {
		solenoide3V = true;
		return true;
	}

	public boolean stopSolenoide3V() {
		solenoide3V = false;
		return false;
	}

	public boolean comprobarSolenoide3V() {
		// Con el solenoide simulamos el ultimo estado que hayamos puesto
		// iniciando la placa en off
		return solenoide3V; // Siempre off
	}
}
