package arduino;

/**
 * Interfaz que debe cumplir todo microntrolador que quiera comunicarse con
 * RegAdmin
 * 
 * @author Jose Antonio Hernández Martínez (joherma1@gmail.com)
 * 
 */
public interface Arduino {
	public int initialize();

	public void close();
	
	public boolean startRele();

	public boolean stopRele();

	public boolean comprobarRele();

	public boolean startReg();

	public boolean stopReg();

	public boolean comprobarReg();
	
	public boolean startSolenoide3V();
	
	public boolean stopSolenoide3V();
	
	public boolean comprobarSolenoide3V();

	public int contarSensoresT();

	public String[] listarSensoresT();

	public Float obtenerTemperatura(String sensor);

	public Long obtenerPresionBMP085();

	public Float obtenerTemperaturaBMP085();

	public Float obtenerAlturaBMP085();

	public String obtenerEstimacionTiempoBMP085();

	public Float obtenerHumedadHH10D();
	
	public int obtenerHumedadSuelo();

	public boolean establecerHora(Long tiempoUnix);

	public int establecerAlarmaOn(Long tiempoUnix);

	public int establecerAlarmaOff(Long tiempoUnix);

	public int establecerAlarmaRepOn(int horas, int minutos, int segundos);

	public int establecerAlarmaRepOff(int horas, int minutos, int segundos);

	public boolean eliminarAlarma(int alarmaId);

	public boolean eliminarAlarmas();
}
