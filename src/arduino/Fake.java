package arduino;

import java.io.IOException;

import arduino.Duemilanove.Monitor;

public class Fake implements Arduino{
	//-----------------
	//Variables Arduino
	//-----------------
	int n_sensores_t=0;
	public byte sensores_t[][]=null;
	boolean regando = false;

	public boolean startReg(){
		regando = true;
		return true;
	}

	public boolean stopReg(){
		regando = false;
		return false;
	}
	public boolean comprobarReg(){
		//Con regando simulamos el ultimo estado que hayamos puesto
		//iniciando la placa en off
		return regando; //Siempre off
	}

	public int contarSensoresT(){
		this.n_sensores_t = 2;
		return 2;
	}


	public byte[][] listarSensoresT(){
		this.contarSensoresT();
		this.sensores_t= new byte[this.n_sensores_t][8];
		this.sensores_t[0][0] = new Byte("00").byteValue();
		this.sensores_t[0][1] = new Byte("00").byteValue();
		this.sensores_t[0][2] = new Byte("00").byteValue();
		this.sensores_t[0][3] = new Byte("00").byteValue();
		this.sensores_t[0][4] = new Byte("00").byteValue();
		this.sensores_t[0][5] = new Byte("00").byteValue();
		this.sensores_t[0][6] = new Byte("00").byteValue();
		this.sensores_t[0][7] = new Byte("00").byteValue();
		this.sensores_t[1][0] = new Byte("11").byteValue();
		this.sensores_t[1][1] = new Byte("11").byteValue();
		this.sensores_t[1][2] = new Byte("11").byteValue();
		this.sensores_t[1][3] = new Byte("11").byteValue();
		this.sensores_t[1][4] = new Byte("11").byteValue();
		this.sensores_t[1][5] = new Byte("11").byteValue();
		this.sensores_t[1][6] = new Byte("11").byteValue();
		this.sensores_t[1][7] = new Byte("11").byteValue();
		return this.sensores_t;
	}

	public Float obtenerTemperatura(byte[] sensor){
		if(sensor == this.sensores_t[1]){
			return new Float("34.2");
		}else{
			return new Float("24.2");
		}
	}

	public Long obtenerPresionBMP085(){
		return new Long (101325);
	}

	public Float obtenerTemperaturaBMP085(){
		return new Float(30);
	}
	public Float obtenerAlturaBMP085(){
		return new Float(0);
	}
	public Float obtenerHumedadHH10D(){
		return new Float(45.5);
	}
	public int initialize(){
		return 0;
	}
	public void close(){
	}
}
