package logic;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

import com.google.gdata.data.DateTime;

public class Evento implements Comparable<Evento>{
	public enum State { ROJO, VERDE, NEGRO};
	private String titulo;
	private String descripcion;
	private DateTime inicio;
	private DateTime fin;
	private String lugar;
	private State estado; 
	private String iCalUID;
	public Evento(String titulo, String descripcion, DateTime comienzo, DateTime fin, String lugar,String UID) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.inicio = comienzo;
		this.fin = fin;
		this.lugar = lugar;
		this.estado = State.NEGRO;
		this.iCalUID = UID;
	}	
	public Evento(String titulo, String descripcion, DateTime comienzo, DateTime fin, String UID) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.inicio = comienzo;
		this.fin = fin;
		this.estado = State.NEGRO;
		this.iCalUID = UID;
	}
	public Evento(){		
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
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
	    return this.getInicio().compareTo(o.getInicio());
	}	
	public boolean equals(Evento o) {
		if(this.iCalUID.compareTo(o.iCalUID)==0 && this.getInicio().equals(o.getInicio()) && this.getFin().equals(o.getFin()))
				return true;
		else
			return false;
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
	public void imprimir(){
		System.out.print(getDateInicio().toString() + " -> " + getDateFin().toString() + " => "
				+ titulo );
		if(descripcion.length()>0)
			System.out.print(": " + descripcion);
		if(lugar.length()>0)
			System.out.println(" (" + lugar + ")");
		else System.out.println();
	}	
	public String toString(){
		Date ini = getDateInicio();
		Date fin = getDateFin();
		DecimalFormat entero = new DecimalFormat("00");
		@SuppressWarnings("deprecation")
		String res =entero.format(ini.getDate()) + "/" + entero.format(ini.getMonth() + 1) + "/" + (ini.getYear() + 1900)  + ": "
			+ entero.format(ini.getHours())+":" + entero.format(ini.getMinutes()) + " -> "
			+ entero.format(fin.getHours())+":" + entero.format(fin.getMinutes()) + " => " + titulo;
		if(descripcion.length()>0)
			res += ": " + descripcion;
		if(lugar.length()>0)
			res += " (" + lugar + ")";
		return res;
	}
	public int colorear(DateTime ahora){
		/*
		 * a.comparteTo(object b)
		 * >0  -> b>a
		 * 0  -> b=a
		 * <0 -> b<a
		 */		
		//Si la hora de fin es menor o igual a la de ahora => ROJO (ha pasado)
		if(this.getFin().compareTo(ahora) <= 0){
			this.estado = State.ROJO;
			return 0;
		}
		//Si la hora de inicio es mayor que la de ahora => NEGRO
		else if(this.getInicio().compareTo(ahora) > 0){
			this.estado = State.NEGRO;
			return 2;
		}
		//La hora de inicio es menor o igual a la de ahora y la de fin es mayor o igual a la de ahora => Verde
		else if(this.getInicio().compareTo(ahora) <= 0 && this.getFin().compareTo(ahora) >= 0){
			this.estado = State.VERDE;
			return 1;
		}
		return -1;
	}

}
