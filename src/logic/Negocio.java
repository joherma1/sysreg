package logic;

import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import logic.Evento.State;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import arduino.Arduino;
import arduino.Mega3G;
import arduino.Uno;
import arduino.Fake;

import com.google.api.client.auth.oauth2.draft10.AccessTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessTokenRequest.GoogleAuthorizationCodeGrant;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAuthorizationRequestUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class Negocio {
	private Arduino ino;
	private String[] sensores_t;
	private List<Evento> sortedEvents;
	private boolean regando;
	com.google.api.services.calendar.Calendar googleCalendar;
	private final String IPV4_REGEX = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private Pattern IPV4_PATTERN = Pattern.compile(IPV4_REGEX);

	public boolean validarIP(final String s) {
		return IPV4_PATTERN.matcher(s).matches();
	}

	/**
	 * Constructor de la clase negocio que implementa la lógica del software
	 * RegAdmin. Este constructor establece la conexión con una placa AlReg por
	 * USB, a través de la implementación Uno.java siguiendo la interfaz
	 * Arduino.java.
	 */
	public Negocio() {
		ino = new Uno();
	}

	/**
	 * Constructor de la clase negocio que implementa la lógica del software
	 * RegAdmin. Constructor indicado para llevar a cabo tareas de depuración
	 * permitiendo indicar a través de su parametro si queremos simular la placa
	 * AlReg (clase Fake.java) o utilizar la placa Uno por USB (Uno.java).
	 * 
	 * @param debug
	 *            Tipo de conexión - true: Simulacion - false: USB
	 * 
	 */
	public Negocio(boolean debug) {
		if (debug == true)
			ino = new Fake();
		else
			ino = new Uno();
	}

	/**
	 * Constructor de la clase negocio que implementa la lógica del software
	 * RegAdmin. Este constructor requiere del parametro IP, ya que establece la
	 * conexión con la placa AlReg3G mediante sockets.
	 * 
	 * @param IP
	 *            Dirección IP de la placa AlReg3G
	 */
	public Negocio(String IP) {
		if (IP != null && IP.length() > 0 && validarIP(IP)) {
			ino = new Mega3G(IP);
		} else
			System.err.println("IP no válida");
	}

	/**
	 * Inicializa la placa Arduino y carga la configuración del XML
	 * 
	 * @return 0 -> OK; -1 -> No se ha encontrado el puerto; -2 -> Error al
	 *         abrir el puerto; -3 -> Error al cargar la librería rxtx; -4: Se
	 *         ha cancelado el inicio de sesión; -5: Código de autorización
	 *         incorrecto; -6: En otro caso
	 */
	public int inicializar() {
		int value_due = ino.initialize();
		if (value_due == 0) {
			// Comprobamos el estado actual del riego
			regando = ino.comprobarReg();
			cargarConfiguracion();
		}
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

	public String[] listarSensoresT() {
		// Los índices son los mismos para sensores y sensores_raw
		sensores_t = ino.listarSensoresT();
		return sensores_t;
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
		return ino.obtenerTemperatura(sensor);
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
	 * Obtiene la humedad del sensor resistivo de humedad del suelo placa.
	 * 
	 * @return La humedad del suelo
	 */
	public int obtenerHumedadSuelo() {
		return ino.obtenerHumedadSuelo();
	}

	/**
	 * Obtiene, de la cuenta de Google Calendar indicada en el archivo de
	 * configuración, la lista de eventos del día actual ordenada
	 * cronológicamente. Utiliza la API de Google para obtener únicamente los
	 * eventos confirmados. Además para evitar duplicados comprobamos que no
	 * existe otro evento igual (con la misma fecha y el mismo identificador de
	 * Google). Los eventos se colorean en función del estado actual.
	 * 
	 * @return La lista de Eventos del día ordenados y coloreados; null si no se
	 *         ha podido obtener eventos
	 * 
	 */
	public List<Evento> cargarCalendario() {
		try {
			// Autenticación
			if (googleCalendar == null) {
				// Si no se ha conectado todavía con Google Calendar lo
				// intentamos una vez
				if (abrirCalendario() != 0)
					// Si ha vuelto ha haber algún error al conectar desistimos
					return null;
			}
			// Consulta
			com.google.api.services.calendar.Calendar.Events.List consulta = googleCalendar
					.events().list("primary");

			DateTime dateInicio = obtenerHoy();
			DateTime dateFin = obtenerPasadoManyana();
			// dateInicio = DateTime.parseRfc3339("2011-11-23T00:00:00Z");
			// dateFin = DateTime.parseRfc3339("2011-11-23T23:59:59Z");
			consulta.setTimeMin(dateInicio);
			consulta.setTimeMax(dateFin);
			// Para que nos retorne los eventos repetidos como eventos simples
			consulta.setSingleEvents(true);

			Events events = consulta.execute();
			sortedEvents = new ArrayList<Evento>();
			while (true) {
				for (Event event : events.getItems())
					if (event.getStatus().equals("confirmed")) {
						Evento e = new Evento(event.getSummary(),
								event.getDescription(), event.getStart()
										.getDateTime(), event.getEnd()
										.getDateTime(), event.getId());
						e.setLugar(event.getLocation());
						sortedEvents.add(e);
					}
				// Por cada página entran por defecto 100 eventos, si hay
				// mas en la consulta comprobar las siguientes páginas
				String pageToken = events.getNextPageToken();
				if (pageToken != null && !pageToken.isEmpty()) {
					events = googleCalendar.events().list("primary")
							.setPageToken(pageToken).execute();
				} else {
					break;
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
				// e.imprimir();
			}
			return sortedEvents;

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
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
	 * pasado mañana. Se encuentra normalizado a la hora de la península Ibérica
	 * 
	 * @return El día de pasado mañana
	 */
	private DateTime obtenerPasadoManyana() {
		Calendar now = Calendar.getInstance();
		now.set((now.get(Calendar.YEAR)), now.get(Calendar.MONTH),
				now.get(Calendar.DAY_OF_MONTH), now.getMinimum(Calendar.HOUR),
				now.getMinimum(Calendar.MINUTE),
				now.getMinimum(Calendar.SECOND));
		now.add(Calendar.DAY_OF_MONTH, 2);
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
		if (sortedEvents != null) {
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
		}
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

	/**
	 * Establece la conexión con la API de Google Calendar a través del
	 * protocolo OAuth 2.0
	 * 
	 * @return 0: Se ha abierto correctamente el calendario; -4: Se ha cancelado
	 *         el inicio de sesión; -5: Código de autorización incorrecto; -6:
	 *         En otro caso
	 */
	private int abrirCalendario() {
		try {
			HttpTransport httpTransport = new NetHttpTransport();
			JacksonFactory jsonFactory = new JacksonFactory();

			// Las claves copiadas de la pestaña API Access en la consola de
			// APIs de Google

			// Claves de la cuenta sysreg1@gmail.com, funciona también si
			// habrimos una cuenta diferente es decir, lo que realmente enlaza
			// sesión es el authorization code con registrar la aplicación en
			// una cuenta sirve por lo que puede leer eventos de cualquier
			// cuenta
			String clientId = "797624020242.apps.googleusercontent.com";
			String clientSecret = "2YlbP9Pt90AUF029Dj4vCHiE";
			String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
			String scope = "https://www.googleapis.com/auth/calendar";

			// Paso 1: Autorización
			String authorizationUrl = new GoogleAuthorizationRequestUrl(
					clientId, redirectUrl, scope).build();

			// Point or redirect your user to the authorizationUrl.
			// Redirección para usar la url de autorización
			System.out.println("Vaya al siguiente enlace en su navegador:");
			System.out.println(authorizationUrl);

			// Abrimos directamente el navegador
			ImageIcon icono = new ImageIcon(
					Negocio.class
							.getResource("/imagenes/Naranjito/Naranjito 64.png"));
			int res = JOptionPane.showConfirmDialog(null,
					"¿Desea iniciar sesión en Google Calendar?",
					"Iniciar sesión", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, icono);
			if (res == JOptionPane.YES_OPTION)
				Desktop.getDesktop().browse(new URI(authorizationUrl));
			else
				// NO_OPTION
				return -4;
			// Lectura por la entrada estándard
			// BufferedReader in = new BufferedReader(new InputStreamReader(
			// System.in));
			// System.out.println("What is the authorization code?");
			// String code;
			// code = in.readLine();

			// Lectura del código a través de un JOptionPane
			String code = (String) JOptionPane.showInputDialog(null,
					"Introduzca el código de autorización:",
					"Autorización para acceder a Google Calendar",
					JOptionPane.QUESTION_MESSAGE, icono, null, null);
			if (code == null) // Se ha pulsado la Cancelar
				return -4;

			// Paso 2: Intercambio de códigos
			AccessTokenResponse response = new GoogleAuthorizationCodeGrant(
					httpTransport, jsonFactory, clientId, clientSecret, code,
					redirectUrl).execute();
			GoogleAccessProtectedResource accessProtectedResource = new GoogleAccessProtectedResource(
					response.accessToken, httpTransport, jsonFactory, clientId,
					clientSecret, response.refreshToken);
			googleCalendar = com.google.api.services.calendar.Calendar
					.builder(httpTransport, jsonFactory)
					.setApplicationName("SysReg")
					.setHttpRequestInitializer(accessProtectedResource).build();
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -5;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -6;
		}
	}

	/**
	 * Lee de Google Calendar con la conexión establecida anteriormente la lista
	 * de eventos en el rango indicado. A continuación convierte los eventos en
	 * Alarmas de encendido y apagado de riego y las devuelve en un ArrayList
	 * 
	 * @param inicio
	 *            Fecha de inicio de los eventos
	 * @param fin
	 *            Fecha de fin de los eventos
	 * @return ArrayList con todos las alarmas; null: En otro caso
	 */
	public List<Alarma> CalendarioAAlarma(DateTime inicio, DateTime fin) {
		if (googleCalendar == null) {
			// Si no se ha conectado todavía con Google Calendar lo
			// intentamos una vez
			if (abrirCalendario() != 0)
				// Si ha vuelto ha haber algún error al conectar desistimos
				return null;
		}
		// Leemos todos los eventos en el periodo indicado
		try {
			com.google.api.services.calendar.Calendar.Events.List consulta = googleCalendar
					.events().list("primary");
			consulta.setTimeMin(inicio);
			consulta.setTimeMax(fin);
			Events events = consulta.execute();
			List<Alarma> alarmas = new ArrayList<Alarma>();

			while (events.getItems() != null) {
				for (Event event : events.getItems()) {
					if (event.getStatus().equals("confirmed")) {
						if (event.getRecurrence() == null) { // Eventos simples
							// Creamos la alarma puntual
							Alarma on = new Alarma(event.getStart()
									.getDateTime(), event.getId(),
									Alarma.Modo.ON);
							Alarma off = new Alarma(event.getEnd()
									.getDateTime(), event.getId(),
									Alarma.Modo.OFF);
							alarmas.add(on);
							alarmas.add(off);
						} else { // Eventos repetidos
							// Creamos la alarma repetida
							// *****------------------******
							// Por ahora solo acepta alarmas repetidas
							// diariamente
							// Si hay una en ese periodo se insertará
							// Necesaria modificación en los 4 niveles
							// *****------------------******
							AlarmaRepetitiva on = new AlarmaRepetitiva(event
									.getStart().getDateTime(), event.getId(),
									Alarma.Modo.ON,
									AlarmaRepetitiva.Tipo.DIARIA,
									AlarmaRepetitiva.Periodo.TODOS);
							AlarmaRepetitiva off = new AlarmaRepetitiva(event
									.getEnd().getDateTime(), event.getId(),
									Alarma.Modo.OFF,
									AlarmaRepetitiva.Tipo.DIARIA,
									AlarmaRepetitiva.Periodo.TODOS);
							alarmas.add(on);
							alarmas.add(off);
						}
					}
				}
				// Si hay más resultados
				String pageToken = events.getNextPageToken();
				if (pageToken != null && !pageToken.isEmpty()) {
					events = googleCalendar.events().list("primary")
							.setPageToken(pageToken).execute();
				} else {
					break;
				}
			}
			return alarmas;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Envía a la placa la lista de Alarmas indicada. En primer lugar ELIMINA
	 * todas las alarmas previamente establecidas y sincroniza la hora
	 * 
	 * @param alarmas
	 *            Alarmas de encendido y apagado de riego
	 * 
	 * @return true: Alarmas establecidas correctamente; false: En otro caso
	 */
	public boolean AlamarmasAPlaca(List<Alarma> alarmas) {
		// Por ahora cada programación machaca las alarmas de la placa, por lo
		// no es necesario almacenar ni los ID (Alarma.getId()) de las alarmas
		// ni borrar determinadas alarmas (ino.eliminarAlarma(alarmaId))
		if (!ino.eliminarAlarmas())
			return false;
		// Las funciones de tiempo en Java trabajan en milisegundos desde epoch
		// mientras que la placa trabaja en segundos
		// if(!ino.establecerHora(Calendar.getInstance().getTimeInMillis() /
		// 1000))
		if (!ino.establecerHora(null))// hora actual
			return false;
		for (Alarma a : alarmas) {
			if (a instanceof AlarmaRepetitiva) {
				AlarmaRepetitiva arep = (AlarmaRepetitiva) a;
				Calendar fecha = new GregorianCalendar(
						TimeZone.getTimeZone(("Europe/Madrid")));
				fecha.setTimeInMillis(a.getFecha().getValue());
				if (arep.getModo() == Alarma.Modo.ON) {
					// Por el momento solo tenemos el tipo de alarma repetitiva
					// indefinida. FALTA definir los diferentes tipos de alarmas
					// if(arep.getTipo()==AlarmaRepetitiva.Tipo.DIARIA)
					ino.establecerAlarmaRepOn(fecha.get(Calendar.HOUR_OF_DAY),
							fecha.get(Calendar.MINUTE),
							fecha.get(Calendar.SECOND));
				} else {// Si no es de encendido es de apagado
					ino.establecerAlarmaRepOff(fecha.get(Calendar.HOUR_OF_DAY),
							fecha.get(Calendar.MINUTE),
							fecha.get(Calendar.SECOND));
				}
				System.out.println(arep.toString());
			} else { // Si es una alarma puntual
				if (a.getModo() == Alarma.Modo.ON) {
					ino.establecerAlarmaOn(a.getFecha().getValue() / 1000);
				} else {
					ino.establecerAlarmaOff(a.getFecha().getValue() / 1000);
				}
				System.out.println(a.toString());
			}
		}
		return true;
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
		Negocio main = new Negocio("95.126.199.162");
		main.inicializar();
		for (String s : main.listarSensoresT()) {
			System.out.println(main.obtenerTemperatura(s));
		}
		//System.out.println(main.obtenerTemperatura("28F5E9AF020000D2"));
		// main.abrirCalendario();
		// main.cargarCalendario();
		// System.out.println("Eventos insertados");
		// for (Evento3 e : main.sortedEvents)
		// e.imprimir();
		// List<Alarma> alarmas = main.CalendarioAAlarma(
		// DateTime.parseRfc3339("2011-11-25T00:00:00Z"),
		// DateTime.parseRfc3339("2011-11-26T00:00:00Z"));
		// System.out.println(main.AlamarmasAPlaca(alarmas));
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
		// main.cargarConfiguracion();
		// main.cargarCalendario();
		// System.out.println(main.obtenerPresionBMP085());
		// System.out.println(main.obtenerTemperaturaBMP085());

		System.exit(0);
	}

}
