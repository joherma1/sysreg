package logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.google.api.client.util.DateTime;

public class AlarmaRepetitiva extends Alarma {
	public enum Tipo {
		DIARIA, SEMANAL, MENSUAL, ANUAL
	};

	public enum Periodo {
		LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO, TODOS
	}

	private Tipo tipo;
	private Periodo periodo;

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public AlarmaRepetitiva(DateTime fecha, String idEvento, Modo modo) {
		super(fecha, idEvento, modo);
	}

	public AlarmaRepetitiva(DateTime fecha, String idEvento, Modo modo, Tipo tipo,
			Periodo periodo) {
		super(fecha, idEvento, modo);
		this.tipo = tipo;
		this.periodo = periodo;
	}
	public String toString(){
		Calendar fecha = new GregorianCalendar(TimeZone.getTimeZone("Europe/Madrid"));
		fecha.setTimeInMillis(this.getFecha().getValue());
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss"); //HH 24 horas
//		return "Alarma repetida " + this.getModo() + ":\t"+ formatter.format(fecha.getTime());
		return this.getModo() + ": " + formatter.format(fecha.getTime());
	}

}
