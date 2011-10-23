package arduino;

/**
 * Interfaz que debe cumplir todo microntrolador que quiera comunicarse con RegAdmin
 * @author Jose Antonio Hernández Martínez (joherma1@gmail.com)
 * 
 */
public interface Arduino {
	public int initialize();

	public void close();

	public boolean startReg();

	public boolean stopReg();

	public boolean comprobarReg();

	public int contarSensoresT();

	public byte[][] listarSensoresT();

	public Float obtenerTemperatura(byte[] sensor);

	public Long obtenerPresionBMP085();

	public Float obtenerTemperaturaBMP085();

	public Float obtenerAlturaBMP085();

	public Float obtenerHumedadHH10D();
}
