package logic;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import logic.Evento3.State;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import arduino.Arduino;
import arduino.Duemilanove;
import arduino.Fake;

import com.google.api.client.auth.oauth2.draft10.AccessTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessProtectedResource;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAccessTokenRequest.GoogleAuthorizationCodeGrant;
import com.google.api.client.googleapis.auth.oauth2.draft10.GoogleAuthorizationRequestUrl;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar.Events.Instances;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class Negocio3 {
	private Arduino ino;
	private String[] sensores_t;
	private byte[][] sensores_t_raw;
	private List<Evento3> sortedEvents;
	private boolean regando;
	com.google.api.services.calendar.Calendar googleCalendar;

	public Negocio3() {
		ino = new Duemilanove();
	}

	public Negocio3(boolean debug) {
		if (debug == true)
			ino = new Fake();
		else
			ino = new Duemilanove();
	}

	/**
	 * Inicializa la placa Arduino y carga la configuración del XML
	 * 
	 * @return 0 -> OK; -1 -> No se ha encontrado el puerto; -2 -> Error al
	 *         abrir el puerto; -3 -> Error al cargar la libreria rxtx;
	 *          -4 -> Error al abrir el calendario
	 */
	public int inicializar() {
		int value_due = ino.initialize();
		boolean value_calendar = false;
		if (value_due == 0) {
			// Comprobamos el estado actual del riego
			regando = ino.comprobarReg();
			cargarConfiguracion();
			value_calendar = abrirCalendario();
		}
		else{ //Error al conectar con la placa
			return value_due;
		}
		if(value_calendar)//Se ha conectado bien y cargado el calendario
			return 0;
		else  //Error con el calendario
			return -4;
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
	public List<Evento3> cargarCalendario() {
		try {
			// Autenticación
//			long benchIni= System.currentTimeMillis();
			if (googleCalendar == null) {
				return null; // Autenticacion fallida
			}
			// Consulta
			com.google.api.services.calendar.Calendar.Events.List consulta = googleCalendar
					.events().list("primary");

			DateTime dateInicio = obtenerHoy();
			DateTime dateFin = obtenerManyana();
			//dateInicio = DateTime.parseRfc3339("2011-11-23T00:00:00Z");
			//dateFin = DateTime.parseRfc3339("2011-11-23T23:59:59Z");
			String inicio = dateInicio.toString();
			String fin = dateFin.toString();
			consulta.setTimeMin(inicio);
			consulta.setTimeMax(fin);
			
			//Revisar
//Probar a hacerlo diferencia de segundos
//Y con la libreria de facto joda
			long diferencia = dateFin.getValue() - dateInicio.getValue();
			diferencia = diferencia / (24 * 60 * 60 * 1000);
			//Revisar: Como calculamos los 2 dias de amplitud en el formato 00:00:00 23:59:59 hay que sumar 1
			//Cambiar a un 00:00:00
			diferencia++;
			Calendar inicioCal = new GregorianCalendar();
			inicioCal.setTimeInMillis(dateInicio.getValue());
			Calendar finCal = new GregorianCalendar();
			finCal.setTimeInMillis(dateFin.getValue());
//			int diferencia = finCal.get(Calendar.DAY_OF_MONTH) - inicioCal.get(Calendar.DAY_OF_MONTH);
		
			Events events = consulta.execute();

			sortedEvents = new ArrayList<Evento3>();
			//Metodo eficiente: Insertamos todos los eventos (con repeticiones) y borramos los cancelados
			//862ms
			while (true) {
				for (Event event : events.getItems()) {
					if (event.getStatus().equals("confirmed")) {
						if (event.getRecurrence() == null) { // Eventos simples
							Evento3 e = new Evento3(event.getSummary(),
									event.getDescription(), event.getStart()
											.getDateTime(), event.getEnd()
											.getDateTime(), event.getId());
							e.setLugar(event.getLocation());
							sortedEvents.add(e);
						} else {// Si es un evento repetido procedemos a
								// insertar una instancia por día
							// Ponemos el calendario en la fecha indicada en
							// evento
							Calendar inicioInstancia = new GregorianCalendar();
							inicioInstancia.setTimeInMillis(event.getStart()
									.getDateTime().getValue());
							// y luego ajustamos el dia
							inicioInstancia.set(inicioCal.get(Calendar.YEAR),
									inicioCal.get(Calendar.MONTH),
									inicioCal.get(Calendar.DATE));
							// Igual para la fecha de fin
							Calendar finInstancia = new GregorianCalendar();
							finInstancia.setTimeInMillis(event.getEnd()
									.getDateTime().getValue());
							finInstancia.set(inicioCal.get(Calendar.YEAR),
									inicioCal.get(Calendar.MONTH),
									inicioCal.get(Calendar.DATE));

							// Ya tenemos la fecha de inicio y de fin ajustadas
							// al primer día
							for (int i = 0; i < diferencia; i++) { // Diferencia
																	// debe ser
																	// 2
								// Añadimos el día
								inicioInstancia.add(Calendar.DAY_OF_MONTH, i);
								finInstancia.add(Calendar.DAY_OF_MONTH, i);
								Evento3 e = new Evento3(event.getSummary(),
										event.getDescription(), new DateTime(
												inicioInstancia.getTime()),
										new DateTime(finInstancia.getTime()),
										event.getId());
								e.setLugar(event.getLocation());
								sortedEvents.add(e);
							}

						}
					}// quitamos las instancias repeticdas
					if (event.getStatus().equals("cancelled")) {
						// Tendran el mismo hash code
						// si son iguales el idCal y la fecha de inicio (con
						// precisión de segundos)Tenemos que parsear el id del
						// evento padre y la fecha
						// de inicio, codificada
						String[] eventoCancelado = event.getId().split("_");
						eventoCancelado[1] = eventoCancelado[1].substring(0, 4)
								+ "-" + eventoCancelado[1].substring(4, 6)
								+ "-" + eventoCancelado[1].substring(6, 11)
								+ ":" + eventoCancelado[1].substring(11, 13)
								+ ":" + eventoCancelado[1].substring(13);
						DateTime fechaCancelada = DateTime
								.parseRfc3339(eventoCancelado[1]);
						//Creamos un evento incompleto
						Evento3 e1 = new Evento3(null, null, fechaCancelada,
								null, eventoCancelado[0]);
						//System.out.println("Evento cancelado= "+eventoCancelado[0]+eventoCancelado[1]);
						sortedEvents.remove(e1);
					}
				}
				//Por cada pagina entran por defecto 100 eventos, si hay mas en la consulta comprobar las siguientes paginas
				String pageToken = events.getNextPageToken();
				if (pageToken != null && !pageToken.isEmpty()) {
					events = googleCalendar.events().list("primary")
							.setPageToken(pageToken).execute();
					//System.out.println("Nueva página");
				} else {
					break;
				}
			}

			//Metodo ineficiente: Recorremos todas las instancias de los repetidos y si estan en la fecha las insertamos
			//20304ms
//			while (true) {
//				for (Event event : events.getItems()) {
//					if (event.getStatus().equals("confirmed")) {
//						if (event.getRecurrence() == null) { // Eventos simples
//							Evento3 e = new Evento3(event.getSummary(),
//									event.getDescription(), event.getStart()
//											.getDateTime(), event.getEnd()
//											.getDateTime(), event.getId());
//							e.setLugar(event.getLocation());
//							sortedEvents.add(e);
//						} else {// Si es un evento repetido procedemos a
//								// insertar las repeticiones
//							// ---------REPASAR-----------
//
//							Instances consulta2 = googleCalendar.events()
//									.instances("primary", event.getId());
//							// NO CONSEGUIMOS QUE NOS DEVUELVA SOLO LAS
//							// INSTANCIAS EN EL PERIODO INDICADO
//							consulta2.put("timeMax", fin);
//							consulta2.put("timeMin", inicio);
//							Events eventsRep = consulta2.execute();
//							// Como no funciona la query para eventos repetidos
//							// tendremos que recorrer excluyendo
//							// MUY INEFICIENTE
//							while (true) {
//								for (Event eventRep : eventsRep.getItems()) {
//									// Si estamos en un evento de día
//									// posterior ya no hace falta seguir
//									// buscando
//									if (eventRep.getStart().getDateTime()
//											.getValue() > dateFin.getValue()
//											|| eventRep.getEnd().getDateTime()
//													.getValue() > dateFin
//													.getValue())
//										break;
//									if (eventRep.getStart().getDateTime()
//											.getValue() >= dateInicio
//											.getValue()) {
//										Evento3 e = new Evento3(
//												eventRep.getSummary(),
//												eventRep.getDescription(),
//												eventRep.getStart()
//														.getDateTime(),
//												eventRep.getEnd().getDateTime(),
//												eventRep.getId());
//										e.setLugar(eventRep.getLocation());
//										sortedEvents.add(e);
//									}
//								}
//								// No entra más de una vez por que solo recibe
//								// cien eventos
//								// LIMITE DE EVENTOS 100, si es un evento
//								// repetido de hace mucho tiempo tendra que
//								// buscar MUCHO
//								// MÁXIMON GOOGLE CALENDAR REPETIR 2 AÑOS, 725
//								// repeticiones
//
//								// long benchLap1 = System.currentTimeMillis() -
//								// benchIni;
//								// System.out.println("El tiempo de obtener los eventos de un token :"
//								// + benchLap1 + " miliseg");
//								String pageToken = eventsRep.getNextPageToken();
//								if (pageToken != null && !pageToken.isEmpty()) {
//									consulta2 = googleCalendar
//											.events()
//											.instances("primary", event.getId())
//											.setPageToken(pageToken);
//									eventsRep = consulta2.execute();
//								} else {
//									break;
//								}
//							}
//
//							// Events eventsRep =
//							// googleCalendar.events().instances("primary",
//							// event.getId()).execute();
//
//							// System.out.println("----" + event.getSummary() +
//							// "----");
//							// for (Event eventRep : eventsRep.getItems()) {
//							// System.out.println(eventRep.getStart() + " -- " +
//							// eventRep.getEnd());
//							// }
//						}
//					}
//				}
//				String pageToken = events.getNextPageToken();
//				if (pageToken != null && !pageToken.isEmpty()) {
//					events = googleCalendar.events().list("primary")
//							.setPageToken(pageToken).execute();
//				} else {
//					break;
//				}
//			}
			
			// Ordenamos los eventos
			Collections.sort(sortedEvents);
			// Coloreamos los estados
			for (int i = 0; i < sortedEvents.size(); i++) {
				Evento3 e = sortedEvents.get(i);
				DateTime now = new DateTime(new Date(),
						TimeZone.getTimeZone("Europe/Madrid"));
				e.colorear(now);
				// e.imprimir();
			}
//			long benchFin = System.currentTimeMillis() - benchIni;
//			System.out.println("El tiempo de demora es :" + benchFin
//					+ " miliseg");
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
	public List<Evento3> comprobarRiego() {
		boolean activo = false;
		if (sortedEvents != null) {
			for (int i = 0; i < sortedEvents.size(); i++) {
				Evento3 e = sortedEvents.get(i);
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
			// Calendario
			// Element raiz_calendario = raiz.getChild("Calendario");
			// String tipo = raiz_calendario.getAttributeValue("tipo");
			// usuario = raiz_calendario.getChildText("usuario");
			// contrasenya = raiz_calendario.getChildText("contraseña");
			// if (tipo.compareTo("gmail") == 0)
			// usuario += "@gmail.com";
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

	private boolean abrirCalendario() {
		try {
			HttpTransport httpTransport = new NetHttpTransport();
			JacksonFactory jsonFactory = new JacksonFactory();

			// The clientId and clientSecret are copied from the API Access tab
			// on
			// the Google APIs Console
			// Claves de la cuenta sysreg1@gmail.com, funciona tambien si habrimos una cuenta diferente
			// es decir, lo que realmente enlaza sesion es el  authorization code
			// con registrar la aplicacion en una cuenta sirve
			// por lo que puede leer eventos de cualquier cuenta!!
			String clientId = "797624020242.apps.googleusercontent.com";
			String clientSecret = "2YlbP9Pt90AUF029Dj4vCHiE";

			// Or your redirect URL for web based applications.
			String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
			String scope = "https://www.googleapis.com/auth/calendar";

			// Step 1: Authorize -->
			String authorizationUrl = new GoogleAuthorizationRequestUrl(
					clientId, redirectUrl, scope).build();

			// Point or redirect your user to the authorizationUrl.
			System.out.println("Go to the following link in your browser:");
			System.out.println(authorizationUrl);
			
			//Open directly on the browser
//			System.out.println("Look your browser!!");
			Desktop.getDesktop().browse(new URI(authorizationUrl));
			
			// Read the authorization code from the standard input stream.
//			BufferedReader in = new BufferedReader(new InputStreamReader(
//					System.in));
//			System.out.println("What is the authorization code?");
//			String code;
//
//			code = in.readLine();
			
			//Esto no cumple con la arquitectura por capas, buscar una solución
			//más elegante
			ImageIcon icono = new ImageIcon(Negocio.class.getResource("/imagenes/Naranjito/Naranjito 64.png"));
			String code = (String)JOptionPane.showInputDialog(null, "Introduzca el código de autorización:", "Autorización para acceder a Google Calendar", JOptionPane.QUESTION_MESSAGE,
					icono, null, null);
			if(code == null)
				return false;
//			String code = JOptionPane.showInputDialog(null,
//					  "Introduzca el código de autorización:",
//					  "Autorización para acceder a Google Calendar",
//					  JOptionPane.QUESTION_MESSAGE);

			// End of Step 1 <--

			// Step 2: Exchange -->
			AccessTokenResponse response = new GoogleAuthorizationCodeGrant(
					httpTransport, jsonFactory, clientId, clientSecret, code,
					redirectUrl).execute();
			// End of Step 2 <--

			GoogleAccessProtectedResource accessProtectedResource = new GoogleAccessProtectedResource(
					response.accessToken, httpTransport, jsonFactory, clientId,
					clientSecret, response.refreshToken);
			googleCalendar = com.google.api.services.calendar.Calendar
					.builder(httpTransport, jsonFactory)
					.setApplicationName("SysReg")
					.setHttpRequestInitializer(accessProtectedResource).build();

			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public List<Alarma> CalendarioAAlarma(DateTime inicio, DateTime fin) {
		if (googleCalendar == null) {
			return null; // Autenticacion fallida
		}
		// Leemos todos los eventos en el periodo indicado
		try {
			com.google.api.services.calendar.Calendar.Events.List consulta = googleCalendar
					.events().list("primary");
			consulta.setTimeMin(inicio.toString());
			consulta.setTimeMax(fin.toString());
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
						} else { // Creamos la alarma repetida
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
					else if (event.getStatus().equals("cancelled")){//y quitamos los cancelados
						//Si ha un evento cancelado significara que se ha creado una 
						//excepción en un evento repetido, tenemos varias opciones:
						//Opción 1: IMPLEMENTADA POR DEFECTO
						//Hacemos caso omiso de la excepción y lo seguimos programando como repetido
						//Opción 2:
						//Elminamos el evento repetido y creamos tantos puntuales como días restantes 
						//siguen existiendo.
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
	
	public boolean AlamarmasAPlaca(List<Alarma> alarmas) {
		// Por ahora cada programación machaca las alarmas de la placa, por lo 
		// no es necesario almacenar ni los ID (Alarma.getId()) de las alarmas
		//ni borrar determinadas alarmas (ino.eliminarAlarma(alarmaId))
		if(!ino.eliminarAlarmas())
			return false;	
		// Las funciones de tiempo en Java trabajan en milisegundos desde epoch
		// mientras que la placa trabaja en segundos
//		if(!ino.establecerHora(Calendar.getInstance().getTimeInMillis() / 1000))
		if(!ino.establecerHora(null))//hora actual
			return false;
		for (Alarma a : alarmas) { 
			if (a instanceof AlarmaRepetitiva) {
				AlarmaRepetitiva arep = (AlarmaRepetitiva) a;
				Calendar fecha = new GregorianCalendar(TimeZone.getTimeZone(("Europe/Madrid")));
				fecha.setTimeInMillis(a.getFecha().getValue());
				if (arep.getModo() == Alarma.Modo.ON) {
					// Por el momento solo tenemos el tipo de alarma repetitiva
					// indefinida. FALTA definir los diferentes tipos de alarmas
					// if(arep.getTipo()==AlarmaRepetitiva.Tipo.DIARIA)
					ino.establecerAlarmaRepOn(fecha.get(Calendar.HOUR_OF_DAY),
							fecha.get(Calendar.MINUTE), fecha.get(Calendar.SECOND));
				} else {// Si no es de encendido es de apagado
					ino.establecerAlarmaRepOff(fecha.get(Calendar.HOUR_OF_DAY),
							fecha.get(Calendar.MINUTE), fecha.get(Calendar.SECOND));
				}
				System.out.println(arep.toString());
			}else{ //Si es una alarma puntual
				if(a.getModo()== Alarma.Modo.ON) {
					ino.establecerAlarmaOn(a.getFecha().getValue()/1000); 
				}else{
					ino.establecerAlarmaOff(a.getFecha().getValue()/1000); 
				}
				System.out.println(a.toString());
			}
		}
		//ino.eliminarAlarmas();
		//ino.establecerHora(1322223572L);
		//ino.establecerAlarmaOn(1322223575L);
		//Por ahora tampoco almacenamos el ID de la alarma ya que no b
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
		Negocio3 main = new Negocio3();
		main.inicializar();
		//main.abrirCalendario();
//		main.cargarCalendario();
//		System.out.println("Eventos insertados");
//		for (Evento3 e : main.sortedEvents)
//			e.imprimir();
		List<Alarma> alarmas = main.CalendarioAAlarma(DateTime.parseRfc3339("2011-11-25T00:00:00Z"), 
				DateTime.parseRfc3339("2011-11-26T00:00:00Z"));
		System.out.println(main.AlamarmasAPlaca(alarmas));
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
