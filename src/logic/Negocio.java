package logic;
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
	public int contarSensores(){
		return due.contarSensoresT();
	}
	public String[] listarSensores(){
		return due.listarSensores();
	}
}

