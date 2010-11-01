package logic;
import java.io.ByteArrayInputStream;

import arduino.Duemilanove;

public class Negocio {
	Duemilanove due;
	String[] sensores_t;
	byte[][] sensores_t_raw;
	public Negocio(){
		due=new Duemilanove();
	}
	public int inicializar(){
		return due.initialize();
	}
	public String[] getSensoresT() {
		return sensores_t;
	}
	public byte[][] getSensoresTRaw() {
		return sensores_t_raw;
	}
	public boolean iniciarRiego(){
		return due.startReg();
	}
	public boolean pararRiego(){
		return due.stopReg();
	}
	public int contarSensoresT(){
		return due.contarSensoresT();
	}
	public String[] listarSensoresT(){
		//Los indices son los mismos para sensores y sensores_raw
		sensores_t_raw = due.listarSensoresT();
		sensores_t = new String[sensores_t_raw.length];
		for(int i=0;i<sensores_t_raw.length;i++)
			sensores_t[i] = toHexadecimal(sensores_t_raw[i]);
		return sensores_t;
	}
	private String toHexadecimal(byte[] datos) 
	{ 
		String resultado=""; 
		ByteArrayInputStream input = new ByteArrayInputStream(datos); 
		String cadAux; 
		int leido = input.read();
		while(leido != -1) 
		{ 
			cadAux = Integer.toHexString(leido); 
			if(cadAux.length() < 2) //Hay que a�adir un 0 
				resultado += "0"; 
			resultado += cadAux; 
			leido = input.read(); 
		} 
		return resultado; 
	}
	public Float obtenerTemperatura(String sensor){
		int i;
		//Buscamos el indice del sensor
		for (i=0;i<sensores_t.length;i++)
			if(sensor.compareTo(sensores_t[i])==0)
				break;
		if(i==sensores_t.length)//No se ha encontrado el sensor
			return null;
		else{
			Float res=due.obtenerTemperatura(sensores_t_raw[i]);//Los dos indices coinciden
			return res;
		}
	}
	private void pintarMenu(){
		System.out.println("------------------");
		System.out.println("1-Activar riego");
		System.out.println("j-Parar riego");
		System.out.println("3-Contar sensores");
		System.out.println("4-Listar sensores");
		System.out.println("-------------------");
	}
	public static void main(String[] args) throws Exception {
		Negocio main = new Negocio();
//		main.pintarMenu();
//		while(true){
//			int value = System.in.read();
//			switch (value) {
//			case 0x6A:
//				System.out.println("Hay "+main.contarSensoresT());
//				break;
//
//			default:
//				break;
//			}
//			main.pintarMenu();
//		}
		//TIEMPOS
		//Inicio 1450
		//contarSensoresT 40
		//ListarSensoresT 40
		
		System.out.println(main.contarSensoresT());
		main.listarSensoresT();
		System.out.println(main.obtenerTemperatura(main.sensores_t[0]));
		System.exit(0);
	}

}

