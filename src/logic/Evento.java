package logic;

import java.util.Date;

import com.google.gdata.data.DateTime;

public class Evento implements Comparable<Evento>{
	public Evento(String titulo, String descripcion, DateTime comienzo, DateTime fin, String lugar) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.inicio = comienzo;
		this.fin = fin;
		this.lugar = lugar;
	}	
	public Evento(String titulo, String descripcion, DateTime comienzo, DateTime fin) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.inicio = comienzo;
		this.fin = fin;
	}
	public Evento(){		
	}
	private String titulo;
	private String descripcion;
	private DateTime inicio;
	private DateTime fin;
	private String lugar;
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
	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
	    return this.getInicio().compareTo(o.getInicio());
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
		String res =getDateInicio().toString() + " -> " + getDateFin().toString() + " => " + titulo;
		if(descripcion.length()>0)
			res += ": " + descripcion;
		if(lugar.length()>0)
			res += " (" + lugar + ")";
		return res;
	}

}
