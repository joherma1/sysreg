package logic;

import java.text.DecimalFormat;
import java.util.Date;

import com.google.api.client.util.DateTime;


/**
 * Clase utilizada para almacenar y procesar los eventos de Google Calendar.
 * 
 * @author Jose Antonio Hernández Martínez (joherma1@gmail.com)
 * 
 */
public class Evento3 implements Comparable<Evento3> {
	public enum State {
		ROJO, VERDE, NEGRO
	};

	private String titulo;
	private String descripcion;
	private DateTime inicio;
	private DateTime fin;
	private String lugar;
	private State estado;
	private String iCalUID;
	private volatile int hashCode = 0;

	/**
	 * Constructor de la clase evento.
	 * 
	 * @param titulo
	 *            Título del evento
	 * @param descripcion
	 *            Descripción del evento
	 * @param comienzo
	 *            Hora de inicio del evento
	 * @param fin
	 *            Hora de fin del evento
	 * @param lugar
	 *            Lugar en el que se sitúa el evento
	 * @param UID
	 *            Identificador único proporcionado por la API de Google
	 */
	public Evento3(String titulo, String descripcion, DateTime comienzo,
			DateTime fin, String lugar, String UID) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.inicio = comienzo;
		this.fin = fin;
		this.lugar = lugar;
		this.estado = State.NEGRO;
		this.iCalUID = UID;
	}

	/**
	 * Constructor de la clase evento
	 * 
	 * @param titulo
	 *            Título del evento
	 * @param descripcion
	 *            Descripción del evento
	 * @param comienzo
	 *            Hora de inicio del evento
	 * @param fin
	 *            Hora de fin del evento
	 * @param UID
	 *            Identificador único proporcionado por la API de Google
	 */
	public Evento3(String titulo, String descripcion, DateTime comienzo,
			DateTime fin, String UID) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.inicio = comienzo;
		this.fin = fin;
		this.estado = State.NEGRO;
		this.iCalUID = UID;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public DateTime getInicio() {
		return inicio;
	}

	public void setInicio(DateTime comienzo) {
		this.inicio = comienzo;
	}

	public DateTime getFin() {
		return fin;
	}

	public void setFin(DateTime fin) {
		this.fin = fin;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getiCalUID() {
		return iCalUID;
	}

	public void setiCalUID(String iCalUID) {
		this.iCalUID = iCalUID;
	}

	@Override
	/**
	 * Método utilizado para ordenar eventos según su fecha de inicio.
	 */
	public int compareTo(Evento3 o) {
		//Nosotros solo queremos comparar con una precisión de segundo, equals comparar con precisión de milisegundo
		return (int)(this.inicio.getValue()/1000 -  o.getInicio().getValue()/1000);
	}
	@Override
	/**
	 * Dos eventos serán iguales si tienen la misma hora de inicio y de fin y el identificador
	 * único de Google. Utilizado para purgar eventos leídos de Google Calendar.
	 * 
	 * @return true Si son iguales los dos eventos; false En otro caso
	 */
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Evento3)) {
			return false;
		}
		Evento3 evento = (Evento3) o;
		//Para nosotros dos fechas son iguales en segundos, no en milisegundos (metodo equals)
		if (this.iCalUID.compareTo(evento.iCalUID) == 0
				&& this.inicio.getValue()/1000 == evento.getInicio().getValue()/1000)
				//&& this.getInicio().equals(evento.getInicio()))
				//&& this.getFin().equals(evento.getFin()))
			return true;
		else
			return false;
	}

	@Override
	/**
	 * Método complementario al equals. Dos eventos tendrán el mismo hashCode si tienen la misma
	 * fecha de inicio y de fin y el identificador único de Google es el mismo.
	 * 
	 * @return Código hash del evento
	 */
	public int hashCode() {
		final int multiplier = 23;
		if (hashCode == 0) {
			int code = 133;
			code = multiplier * code + this.iCalUID.hashCode();
			code = multiplier * code + (int)this.inicio.getValue()/1000;
			//code = multiplier * code + getInicio().hashCode();
			//code = multiplier * code + getFin().hashCode();
			hashCode = code;
		}
		return hashCode;
	}

	public State getEstado() {
		return estado;
	}

	public void setEstado(State estado) {
		this.estado = estado;
	}

	public Date getDateInicio() {
		return new Date(inicio.getValue());
	}

	public Date getDateFin() {
		return new Date(fin.getValue());
	}

	/**
	 * Imprime por la salida estándard el evento
	 */
	public void imprimir() {
		System.out.print(getDateInicio().toString() + " -> "
				+ getDateFin().toString() + " => " + titulo);
		if (descripcion != null && descripcion.length() > 0)
			System.out.print(": " + descripcion);
		if (lugar != null && lugar.length() > 0)
			System.out.println(" (" + lugar + ")");
		else
			System.out.println();
	}

	/**
	 * Convierte en un string el evento
	 * 
	 * @return Una cadena de caracteres que describe el evento
	 */
	public String toString() {
//		if(getDateInicio() == null || getDateFin() == null || descripcion == null)
//			return "Evento incompleto";
		Date ini = getDateInicio();
		Date fin = getDateFin();
		DecimalFormat entero = new DecimalFormat("00");
		@SuppressWarnings("deprecation")
		String res = entero.format(ini.getDate()) + "/"
				+ entero.format(ini.getMonth() + 1) + "/"
				+ (ini.getYear() + 1900) + ": " + entero.format(ini.getHours())
				+ ":" + entero.format(ini.getMinutes()) + " -> "
				+ entero.format(fin.getHours()) + ":"
				+ entero.format(fin.getMinutes()) + " => " + titulo;
		if (descripcion!= null && descripcion.length() > 0)
			res += ": " + descripcion;
		if ( lugar != null && lugar.length() > 0)
			res += " (" + lugar + ")";
		return res;
	}

	/**
	 * En función del la hora indicada por parámetro pinta los eventos de
	 * colores: Verde: Evento activo Rojo: Evento pasado Negro: Evento pendiente
	 * 
	 * @param ahora
	 *            Hora de referencia
	 * @return 0 Color rojo; 1 Color verde; 2 Color negro; -1 en cualquier otro
	 *         caso
	 */
	public int colorear(DateTime ahora) {
		//a.comparteTo(object b) >0 -> b>a 0 -> b=a <0 -> b<a
		// Si la hora de fin es menor o igual a la de ahora => ROJO (ha pasado)
		
		if (this.getFin().getValue() - ahora.getValue() <= 0) {
			this.estado = State.ROJO;
			return 0;
		}
		// Si la hora de inicio es mayor que la de ahora => NEGRO
		else if (this.getInicio().getValue() - ahora.getValue() > 0) {
			this.estado = State.NEGRO;
			return 2;
		}
		// La hora de inicio es menor o igual a la de ahora y la de fin es mayor
		// o igual a la de ahora => Verde
		else if (this.getInicio().getValue() - ahora.getValue() <= 0
				&& this.getFin().getValue() - ahora.getValue() >= 0) {
			this.estado = State.VERDE;
			return 1;
		}
		return -1;
	}

}
