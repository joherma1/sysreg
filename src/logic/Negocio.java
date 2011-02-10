package logic;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.google.gdata.client.*;
import com.google.gdata.client.calendar.*;
import com.google.gdata.data.*;
import com.google.gdata.data.acl.*;
import com.google.gdata.data.calendar.*;
import com.google.gdata.data.extensions.*;
import com.google.gdata.util.*;


import arduino.Duemilanove;

public class Negocio {
	Duemilanove due;
	String[] sensores_t;
	byte[][] sensores_t_raw;
	String usuario;
	String contrasenya;
	public Negocio(){
		due=new Duemilanove();
	}
	public int inicializar(){
		return due.initialize();
	}
	public void cerrar(){
		due.close();
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
			if(cadAux.length() < 2) //Hay que añadir un 0 
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

		//		System.out.println(main.contarSensoresT());
		//		main.listarSensoresT();
		//		System.out.println(main.obtenerTemperatura(main.sensores_t[0]));

		main.cargarCalendario();
		System.exit(0);
	}


	//--------CALENDAR-----------
	//Devuelve una cadena con los eventos para hoy y mañana
	//Hoy:
	//hora_ini - hora_fin => Titulo: Descripcion
	public List<Evento> cargarCalendario(){	
		try {
			//Autenticacion
			CalendarService myService = new CalendarService("RegAdmin");
			myService.setUserCredentials("sysreg1@gmail.com","agricultura.1");
			//Obtener todos los calendarios
			//			URL feedUrl = new URL("https://www.google.com/calendar/feeds/default/allcalendars/full");
			//			CalendarFeed resultFeed = myService.getFeed(feedUrl, CalendarFeed.class);
			//			System.out.println("Your calendars:");
			//			System.out.println();
			//			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
			//			  CalendarEntry entry = resultFeed.getEntries().get(i);
			//			  System.out.println("\t" + entry.getTitle().getPlainText());
			//			}
			URL feedUrl = new URL("https://www.google.com/calendar/feeds/default/private/full");
			
			
			//The metafeed is a private, read-only feed that contains an entry for each calendar that a user has access to
			//URL feedUrl = new URL("https://www.google.com/calendar/feeds/default");
			//The allcalendars feed is a private read/write feed that is used for managing subscriptions and personalization settings of a user's calendars
			//URL feedUrl = new URL("https://www.google.com/calendar/feeds/default/allcalendars/full");
			CalendarQuery myQuery = new CalendarQuery(feedUrl);
			DateTime ahora = obtenerHoy();
			DateTime mañana = obtenerManyana();
			//EL BUENO
			//myQuery.setMinimumStartTime(obtenerHoy());
			//myQuery.setMaximumStartTime(obtenerManyana());
			myQuery.setMinimumStartTime(DateTime.parseDateTime("2011-01-29T00:00:00"));
			myQuery.setMaximumStartTime(DateTime.parseDateTime("2011-02-06T23:59:59"));
			CalendarEventFeed resultFeed = myService.query(myQuery, CalendarEventFeed.class);
			List<Evento> sortedEvents= new ArrayList<Evento>();
			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
				CalendarEventEntry entry = (CalendarEventEntry)resultFeed.getEntries().get(i);
				for(int j = 0; j < entry.getTimes().size(); j++){
					Evento e= new Evento(entry.getTitle().getPlainText(), entry.getPlainTextContent(),
							entry.getTimes().get(j).getStartTime(), entry.getTimes().get(j).getEndTime());
					String lugar = "";
					if( entry.getLocations().size() > 0 && entry.getLocations().get(0).getValueString().length() > 0)
						lugar = entry.getLocations().get(0).getValueString();
					e.setLugar(lugar);
					sortedEvents.add(e);
					
//					System.out.print(entry.getTimes().get(j).getStartTime()+ " --> " + entry.getTimes().get(0).getEndTime().toString() + "=> ");
//					System.out.print(entry.getTitle().getPlainText() + ": " + entry.getPlainTextContent());
//					System.out.print(" en " + lugar);
//					System.out.println();
				}
			}
			//Ordenamos los eventos
			Collections.sort(sortedEvents);
			for(int i = 0; i < sortedEvents.size(); i++){
				Evento e = sortedEvents.get(i);
			}
			//Coloreamos los estados
			for(int i = 0; i < sortedEvents.size(); i++){
				Evento e = sortedEvents.get(i);
				//verde			
				e.colorear(DateTime.parseDateTime("2011-01-30T19:10:00.000+01:00"));
				//rojo			
				//e.colorear(DateTime.parseDateTime("2011-02-09T11:10:00.000+01:00"));
				//negro			
				//e.colorear(DateTime.parseDateTime("2001-01-29T11:10:00.000+01:00"));
				//EL BUENO
				//DateTime now = new DateTime(new Date(), TimeZone.getTimeZone("Europe/Madrid"));
				//e.colorear(now);
			}
			return sortedEvents;

		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private DateTime obtenerHoy(){
		Calendar now= Calendar.getInstance();
		now.set((now.get(Calendar.YEAR)), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), now.getMinimum(Calendar.HOUR), now.getMinimum(Calendar.MINUTE), now.getMinimum(Calendar.SECOND));
		return new DateTime(now.getTime(), TimeZone.getTimeZone("Europe/Madrid"));


	}	
	private DateTime obtenerManyana(){
		Calendar now= Calendar.getInstance();
		now.set((now.get(Calendar.YEAR)), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		now.add(Calendar.DAY_OF_MONTH, 1);
		return new DateTime(now.getTime(),TimeZone.getTimeZone("Europe/Madrid"));
	}
}

