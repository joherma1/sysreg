package arduino;

public interface Arduino {
	public int initialize();
	public void close();
	public boolean startReg();
	public boolean stopReg();
	public int contarSensoresT();
	public byte[][] listarSensoresT();
	public Float obtenerTemperatura(byte[] sensor);
	public Long obtenerPresionBMP085();
	public Float obtenerTemperaturaBMP085();
	public Float obtenerAlturaBMP085();
	public Float obtenerHumedadHH10D();
}
