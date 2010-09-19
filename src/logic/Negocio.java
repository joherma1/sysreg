package logic;
import java.io.ByteArrayInputStream;

import arduino.Duemilanove;

public class Negocio {
	Duemilanove due;
	String[] sensores;
	byte[][] sensores_raw;
	public Negocio(){
		due=new Duemilanove();
		due.initialize();
	}
	public String[] getSensores() {
		return sensores;
	}
	public byte[][] getSensores_raw() {
		return sensores_raw;
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
		sensores_raw = due.listarSensoresT();
		sensores = new String[sensores_raw.length];
		for(int i=0;i<sensores_raw.length;i++)
			sensores[i] = toHexadecimal(sensores_raw[i]);
		return sensores;
	}
	String toHexadecimal(byte[] datos) 
	{ 
		String resultado=""; 
		ByteArrayInputStream input = new ByteArrayInputStream(datos); 
		String cadAux; 
		int leido = input.read();
		while(leido != -1) 
		{ 
			cadAux = Integer.toHexString(leido); 
			if(cadAux.length() < 2) //Hay que a–adir un 0 
				resultado += "0"; 
			resultado += cadAux; 
			leido = input.read(); 
		} 
		return resultado; 
	}
	public Float obtenerTemperatura(String sensor){
		int i;
		for (i=0;i<sensores.length;i++)
			if(sensor.compareTo(sensores[i])==0)
				break;
		if(i==sensores.length)//No se ha encontrado el sensor
			return null;
		else{
			Float res=due.obtenerTemperatura(sensores_raw[i]);
			return res;
		}
	}

}

