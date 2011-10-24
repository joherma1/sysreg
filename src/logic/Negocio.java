package logic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import logic.Evento.State;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import arduino.Arduino;
import arduino.Duemilanove;
import arduino.Fake;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.BaseEventEntry.EventStatus;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class Negocio {
	private Arduino ino;
	private String[] sensores_t;
	private byte[][] sensores_t_raw;
	private String usuario;
	private String contrasenya;
	private List<Evento> sortedEvents;
	private boolean regando;

	public Negocio() {
		ino = new Duemilanove();
	}

	public Negocio(boolean debug) {
		if (debug == true)
			ino = new Fake();
		else
			ino = new Duemilanove();
	}

	/**
	 * Inicializa la placa Arduino y carga la configuración del XML
	 * 
	 * @return 0 -> OK; -1 -> No se ha encontrado el puerto; -2 -> Error al
	 *         abrir el puerto
	 */
	public int inicializar() {
		int value_due = ino.initialize();
		// Comprobamos el estado actual del riego
		regando = ino.comprobarReg();
		cargarConfiguracion();
		return value_due;
	}

	/**
	 * Cierra la conexión con la placa correctamente. Recomendable en sistemas
	 * UNIX para evitar bloqueos
	 */
	public void cerrar() {
		ino.close();
	}

	/**
	 * Obtiene un vector de cadenas con los identificadores completos de los
	 * sensores de temperatura DS18S2
	 * 
	 * @return Un vector de strings con los identificadores One-Wire de los
	 *         sensores de temperatura devueltos por la placa Arduino
	 */
	public String[] getSensoresT() {
		return sensores_t;
	}

	/**
	 * Obtiene un los bytes que identifican los sensores. El resultado se
	 * obtiene en forma de matrix en la que las filas son los sensores y las
	 * columnas los bytes
	 * 
	 * @return Matriz de bytes con los identificadores One-Wire de los sensores
	 *         de temperatura devueltos por la placa Arduino
	 */
	public byte[][] getSensoresTRaw() {
		return sensores_t_raw;
	}

	/**
	 * Envía la señal de activar el riego a la placa Arduino. Esta señal solo se
	 * envía si el dispositivo no está regando.
	 * 
	 * @return true si se ha iniciado el riego; false en otro caso;
	 */
	public boolean iniciarRiego() {
		if (!regando) {
			System.out.println("Señal activar riego");
			regando = true;
			return ino.startReg();
		} else
			return true;
	}

	/**
	 * Envía la señal de parar el riego a la placa Arduino. Esta señal solo se
	 * envía si el dispositivo está regando.
	 * 
	 * @return true si se ha detenido el riego; false en otro caso;
	 */
	public boolean pararRiego() {
		if (regando) {
			System.out.println("Señal desactivar riego");
			regando = false;
			return ino.stopReg();
		} else
			return true;
	}

	/**
	 * Fuerza el envío de la señal de parar el riego a la placa Arduino. Esta
	 * señal solo se envía si el dispositivo no está regando.
	 * 
	 * @return true si se ha iniciado el riego; false en otro caso;
	 */
	public boolean forzarIniciarRiego() {
		System.out.println("Señal activar riego");
		regando = true;
		return ino.startReg();
	}

	/**
	 * Fuerza el envío de la señal de parar el riego a la placa Arduino. Esta
	 * señal solo se envía si el dispositivo está regando.
	 * 
	 * @return true si se ha detenido el riego; false en otro caso;
	 */
	public boolean forzarPararRiego() {
		System.out.println("Señal desactivar riego");
		regando = false;
		return ino.stopReg();
	}

	/**
	 * Obtiene de la placa Arduino el número de sensores de temperatura DS18S20
	 * conectado al pin One-Wire
	 * 
	 * @return Número de sensores de temperatura DS18S20
	 */
	public int contarSensoresT() {
		return ino.contarSensoresT();
	}

	/**
	 * Convierte la información bruta obtenida de la placa (en bytes) a cadenas
	 * de caracteres que identifican los sensores de temperatura DS18S20
	 * conectados al pin One-Wire
	 * 
	 * @return Vector de caracteres con los identificadores de los sensores
	 */
	public String[] listarSensoresT() {
		// Los indices son los mismos para sensores y sensores_raw
		sensores_t_raw = ino.listarSensoresT();
		sensores_t = new String[sensores_t_raw.length];
		for (int i = 0; i < sensores_t_raw.length; i++)
			sensores_t[i] = toHexadecimal(sensores_t_raw[i]);
		return sensores_t;
	}

	/**
	 * Convierte el vector de bytes a una cadena de caracteres
	 * 
	 * @param datos
	 *            El vector de bytes a transformar
	 * @return La cadena convertida
	 */
	private String toHexadecimal(byte[] datos) {
		String resultado = "";
		ByteArrayInputStream input = new ByteArrayInputStream(datos);
		String cadAux;
		int leido = input.read();
		while (leido != -1) {
			cadAux = Integer.toHexString(leido);
			if (cadAux.length() < 2) // Hay que añadir un 0
				resultado += "0";
			resultado += cadAux;
			leido = input.read();
		}
		return resultado;
	}

	/**
	 * Obtiene la temperatura del sensor DS18S20 que se identifica con la cadena
	 * de caracteres que se pasa por parámetro.
	 * 
	 * @param sensor
	 *            Identificador alfanumérico del sensor DS18S20
	 * @return Si existe el sensro, la termperatura en formato de coma flotante,
	 *         en otro caso null
	 */
	public Float obtenerTemperatura(String sensor) {
		int i;
		// Buscamos el índice del sensor
		for (i = 0; i < sensores_t.length; i++)
			if (sensor.compareTo(sensores_t[i]) == 0)
				break;
		if (i == sensores_t.length)// No se ha encontrado el sensor
			return null;
		else {
			Float res = ino.obtenerTemperatura(sensores_t_raw[i]);// Los dos
																	// índices
																	// coinciden
			return res;
		}
	}

	/**
	 * Obtiene la presión del sensor barométrico BMP085 conectado a la placa.
	 * 
	 * @return La presión del sensor BPM085
	 */
	public Long obtenerPresionBMP085() {
		return ino.obtenerPresionBMP085();
	}

	/**
	 * Obtiene la temperatura del sensor barométrico BMP085 conectado a la
	 * placa.
	 * 
	 * @return La temperatura del sensor BPM085
	 */
	public Float obtenerTemperaturaBMP085() {
		return ino.obtenerTemperaturaBMP085();
	}

	/**
	 * Obtiene la altura estimada, en función de la presión, del sensor
	 * barométrico BMP085 conectado a la placa.
	 * 
	 * @return La temperatura del sensor BPM085
	 */
	public Float obtenerAlturaBMP085() {
		return ino.obtenerAlturaBMP085();
	}

	/**
	 * Obtiene la humedad del sensor analógico de humedad HH10D conectado a la
	 * placa.
	 * 
	 * @return La humedad del sensor
	 */
	public Float obtenerHumedadHH10D() {
		return ino.obtenerHumedadHH10D();
	}

	/**
	 * Obtiene, de la cuenta de Google Calendar indicada en el archivo de
	 * configuración, la lista de eventos del día actual ordenada
	 * cronológicamente. Utiliza la API de Google para obtener únicamente los
	 * eventos confirmados. Además para evitar duplicados comprobamos que no
	 * existe otro evento igual (con la misma fecha y el mismo identificador de
	 * Google). Los eventos se colorean en función del estado actual.
	 * 
	 * @return La lista de Eventos del día ordenados y coloreados
	 */
	public List<Evento> cargarCalendario() {
		try {
			// Autenticación
			CalendarService myService = new CalendarService("RegAdmin");
			myService.setUserCredentials(usuario, contrasenya);
			URL feedUrl = new URL(
					"https://www.google.com/calendar/feeds/default/private/full");
			CalendarQuery myQuery = new CalendarQuery(feedUrl);
			myQuery.setMinimumStartTime(obtenerHoy());
			myQuery.setMaximumStartTime(obtenerManyana());
			// myQuery.setMinimumStartTime(DateTime.parseDateTime("2011-10-24T00:00:00"));
			// myQuery.setMaximumStartTime(DateTime.parseDateTime("2011-10-30T23:59:59"));
			CalendarEventFeed resultFeed = myService.query(myQuery,
					CalendarEventFeed.class);
			sortedEvents = new ArrayList<Evento>();
			for (int i = 0; i < resultFeed.getEntries().size(); i++) {
				CalendarEventEntry entry = (CalendarEventEntry) resultFeed
						.getEntries().get(i);
				// Solo añadimos los eventos que están confirmados (Google
				// Calendar guarda otro también eventos cancelados)
				if (entry.getStatus().equals(EventStatus.CONFIRMED)) {
					for (int j = 0; j < entry.getTimes().size(); j++) {
						Evento e = new Evento(entry.getTitle().getPlainText(),
								entry.getPlainTextContent(), entry.getTimes()
										.get(j).getStartTime(), entry
										.getTimes().get(j).getEndTime(),
								entry.getIcalUID());
						String lugar = "";
						if (entry.getLocations().size() > 0
								&& entry.getLocations().get(0).getValueString()
										.length() > 0)
							lugar = entry.getLocations().get(0)
									.getValueString();
						e.setLugar(lugar);
						if (!sortedEvents.contains(e)) {
							sortedEvents.add(e);
						}
					}
				}
			}
			// Ordenamos los eventos
			Collections.sort(sortedEvents);
			// Coloreamos los estados
			for (int i = 0; i < sortedEvents.size(); i++) {
				Evento e = sortedEvents.get(i);
				DateTime now = new DateTime(new Date(),
						TimeZone.getTimeZone("Europe/Madrid"));
				e.colorear(now);
				e.imprimir();
			}
			return sortedEvents;

		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Función que devuelve un objeto de la clase DateTime indicando el día
	 * actual. Se encuentra normalizado a la hora de la península Ibérica
	 * 
	 * @return El día de hoy
	 */
	private DateTime obtenerHoy() {
		Calendar now = Calendar.getInstance();
		now.set((now.get(Calendar.YEAR)), now.get(Calendar.MONTH),
				now.get(Calendar.DAY_OF_MONTH), now.getMinimum(Calendar.HOUR),
				now.getMinimum(Calendar.MINUTE),
				now.getMinimum(Calendar.SECOND));
		return new DateTime(now.getTime(),
				TimeZone.getTimeZone("Europe/Madrid"));

	}

	/**
	 * Función que devuelve un objeto de la clase DateTime indicando el día de
	 * mañana. Se encuentra normalizado a la hora de la península Ibérica
	 * 
	 * @return El día de mañana
	 */
	private DateTime obtenerManyana() {
		Calendar now = Calendar.getInstance();
		now.set((now.get(Calendar.YEAR)), now.get(Calendar.MONTH),
				now.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		now.add(Calendar.DAY_OF_MONTH, 1);
		return new DateTime(now.getTime(),
				TimeZone.getTimeZone("Europe/Madrid"));
	}

	/**
	 * Comprueba si se debe activar o desactivar el riego. Esta función,
	 * diseñada para una sola válvula, comprueba el calendario, obteniendo los
	 * eventos coloreados, y en función de su color, activa o desactiva el riego
	 * 
	 * @return Lista de eventos ordenados y coloreados
	 */
	public List<Evento> comprobarRiego() {
		boolean activo = false;
		for (int i = 0; i < sortedEvents.size(); i++) {
			Evento e = sortedEvents.get(i);
			DateTime now = new DateTime(new Date(),
					TimeZone.getTimeZone("Europe/Madrid"));
			e.colorear(now);
			if (e.getEstado() == State.VERDE)
				activo = true;
		}
		if (activo)
			iniciarRiego();
		else
			pararRiego();
		return sortedEvents;
	}

	private void cargarConfiguracion() {
		try {
			// JDOM no tiene un parser nativo. Utiliza SAX2 como builder
			SAXBuilder builder = new SAXBuilder(true);//
			Document conf = builder.build(getClass().getResource(
					"/configuracion/RegAdmin.xml"));
			Element raiz = conf.getRootElement();
			// Los sensores
			// Los guardamos en el fichero aunque no los utilizamos
			Element raiz_sensores = raiz.getChild("Sensores");
			List sensores = raiz_sensores.getChildren();
			Iterator i = sensores.iterator();
			while (i.hasNext()) {
				Element e = (Element) i.next();
				String id = e.getChildText("id");
				String alias = e.getChildText("alias");
				// System.out.println("Sensor: " + id + " \t" + alias);
				e.setText("Hola");
			}
			// Calendario
			Element raiz_calendario = raiz.getChild("Calendario");
			String tipo = raiz_calendario.getAttributeValue("tipo");
			usuario = raiz_calendario.getChildText("usuario");
			contrasenya = raiz_calendario.getChildText("contraseña");
			if (tipo.compareTo("gmail") == 0)
				usuario += "@gmail.com";
			// System.out.println("Usuario: " + usuario + " \tContraseña:" +
			// contrasenya);

			// Escribir en el xml (No funciona si el XML está dentro del jar.
			// Imposibilidad de escribir en un jar abierto)
			// XMLOutputter outputter = new XMLOutputter();
			// FileOutputStream file = new
			// FileOutputStream(getClass().getResource("RegAdmin.xml").toExternalForm());
			// outputter.output(conf,file);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void pintarMenu() {
		System.out.println("------------------");
		System.out.println("1-Activar riego");
		System.out.println("j-Parar riego");
		System.out.println("3-Contar sensores");
		System.out.println("4-Listar sensores");
		System.out.println("-------------------");
	}

	public static void main(String[] args) throws Exception {
		Negocio main = new Negocio();
		// main.inicializar();
		// main.pintarMenu();
		// while(true){
		// int value = System.in.read();
		// switch (value) {
		// case 0x6A:
		// System.out.println("Hay "+main.contarSensoresT());
		// break;
		//
		// default:
		// break;
		// }
		// main.pintarMenu();
		// }
		// TIEMPOS
		// Inicio 1450
		// contarSensoresT 40
		// ListarSensoresT 40

		// System.out.println(main.contarSensoresT());
		// main.listarSensoresT();
		// System.out.println(main.obtenerTemperatura(main.sensores_t[0]));
		main.cargarConfiguracion();
		main.cargarCalendario();
		// System.out.println(main.obtenerPresionBMP085());
		// System.out.println(main.obtenerTemperaturaBMP085());
		System.exit(0);
	}

}
