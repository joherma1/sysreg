package logic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import com.google.api.client.util.DateTime;

public class Alarma {
	public enum Modo {
		ON, OFF
	};

	private DateTime fecha;
	/**
	 * Identificador del evento original, lo compartiran las alarmas On y Off
	 * del mismo evento
	 */
	private String idEvento;
	private Modo modo;
	private int id;

	public DateTime getFecha() {
		return fecha;
	}

	public void setFecha(DateTime fecha) {
		this.fecha = fecha;
	}

	public String getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Modo getModo() {
		return modo;
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}

	public Alarma(DateTime fecha, String idEvento, Modo modo) {
		super();
		this.fecha = fecha;
		this.idEvento = idEvento;
		this.modo = modo;
	}
	public String toString(){
		Calendar fecha = new GregorianCalendar(TimeZone.getTimeZone("Europe/Madrid"));
		fecha.setTimeInMillis(this.fecha.getValue());
//		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMM yyyy HH:mm:ss Z");
//		return "Alarma " + this.modo + ":\t\t"+ formatter.format(fecha.getTime());
		SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMM yyyy HH:mm:ss");
		return this.modo + ": " + formatter.format(fecha.getTime());
	}

}
