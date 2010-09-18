package logic;
import java.io.ByteArrayInputStream;

import arduino.Duemilanove;

public class Negocio {
	Duemilanove due;

	public Negocio(){
		due=new Duemilanove();
		due.initialize();
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
		byte raw[][] = due.listarSensoresT();
		String [] sensore_hex = new String[raw.length];
		for(int i=0;i<raw.length;i++)
			sensore_hex[i] = toHexadecimal(raw[i]);
		return sensore_hex;
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
	public boolean seleccionaSensorT(){
		int res=due.seleccionsSensorT(due.sensores[0]);
		if(res==1)//Seleccionado el sensor
			return true;
		else return false;
	}
	
	public Float obtenerTemperatura(){
		return due.obtenerTemperatura(due.sensores[0]);
	}

}

