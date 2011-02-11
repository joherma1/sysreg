package ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.lang.Thread.State;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;

import logic.Evento;
import logic.Negocio;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class Interfaz {

	private JFrame f_interfaz = null;  //  @jve:decl-index=0:visual-constraint="165,50"
	private JPanel p_interfaz = null;
	private JButton b_activarRiego = null;
	private JButton b_desactivarRiego = null;
	private JLabel l_solenoide = null;
	private Negocio logica = null;  //  @jve:decl-index=0:
	private String [] sensores = null;
	private List<Evento> eventos = null;
	//Panel Tª
	private ArrayList<JLabel> l_id = new ArrayList<JLabel>();
	private ArrayList<JLabel> l_value = new ArrayList<JLabel>();  //  @jve:decl-index=0:
	private ArrayList<JButton> b_refresh = new ArrayList<JButton>();  //  @jve:decl-index=0:
	private JPanel p_sensor = null;
	private JLabel l_tipoSensor = null;
	private JPanel p_botones = null;
	private JButton b_todos = null;
	private JButton b_anyadir = null;
	private JButton b_eliminar = null;
	private JScrollPane sp_listado = null;
	private JPanel p_listado = null;
	private JProgressBar pb_procesando = null;
	private JLabel esp_progresbar = null;
	private JFrame f_iniciando = null;  //  @jve:decl-index=0:visual-constraint="953,138"
	private JPanel cp_iniciando = null;
	private JLabel l_iniciando = null;
	private JProgressBar pb_iniciando = null;
	private JScrollPane cp_horario = null;
	private JTextPane tp_horario = null;
	private JButton b_recargar = null;
	private JLabel l_ultimaact = null;
	private JLabel l_hora = null;
	/**
	 * This method initializes f_interfaz	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getF_interfaz() {
		if (f_interfaz == null) {
			f_interfaz = new JFrame();
			f_interfaz.setSize(new Dimension(459, 426));
			f_interfaz.setTitle("RegAdmin");
			f_interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f_interfaz.setVisible(false);
			f_interfaz.setContentPane(getP_interfaz());
			f_interfaz.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					logica.cerrar();
				}
			});
			f_interfaz.setLocationRelativeTo(null);
		}
		return f_interfaz;
	}

	/**
	 * This method initializes p_interfaz	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_interfaz() {
		if (p_interfaz == null) {
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.gridx = 1;
			gridBagConstraints14.anchor = GridBagConstraints.WEST;
			gridBagConstraints14.insets = new Insets(5, 20, 5, 5);
			gridBagConstraints14.gridy = 3;
			l_hora = new JLabel();
			l_hora.setText("Hora:");
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 3;
			gridBagConstraints13.gridwidth = 2;
			gridBagConstraints13.anchor = GridBagConstraints.EAST;
			gridBagConstraints13.insets = new Insets(0, 0, 0, 60);
			gridBagConstraints13.gridy = 3;
			l_ultimaact = new JLabel();
			l_ultimaact.setText("Última actualización:");
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 4;
			gridBagConstraints10.anchor = GridBagConstraints.EAST;
			gridBagConstraints10.insets = new Insets(0, 10, 1, 10);
			gridBagConstraints10.gridy = 3;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.weighty = 1.0;
			gridBagConstraints12.gridheight = 3;
			gridBagConstraints12.insets = new Insets(10, 5, 0, 10);
			gridBagConstraints12.gridwidth = 3;
			gridBagConstraints12.gridx = 2;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 1;
			gridBagConstraints7.ipadx = 0;
			gridBagConstraints7.ipady = 30;
			gridBagConstraints7.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints7.gridy = 5;
			esp_progresbar = new JLabel();
			esp_progresbar.setText("");
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 3;
			gridBagConstraints6.insets = new Insets(0, 5, 5, 5);
			gridBagConstraints6.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints6.ipadx = 150;
			gridBagConstraints6.anchor = GridBagConstraints.EAST;
			gridBagConstraints6.ipady = 0;
			gridBagConstraints6.gridwidth = 1;
			gridBagConstraints6.gridheight = 1;
			gridBagConstraints6.gridy = 5;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridwidth = 4;
			gridBagConstraints3.insets = new Insets(5, 5, 0, 5);
			gridBagConstraints3.ipady = 80;
			gridBagConstraints3.ipadx = 10;
			gridBagConstraints3.gridy = 4;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.anchor = GridBagConstraints.NORTH;
			gridBagConstraints5.insets = new Insets(5, 10, 5, 5);
			gridBagConstraints5.fill = GridBagConstraints.NONE;
			gridBagConstraints5.gridy = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.insets = new Insets(5, 10, 5, 5);
			gridBagConstraints4.anchor = GridBagConstraints.NORTH;
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridheight = 1;
			gridBagConstraints11.anchor = GridBagConstraints.CENTER;
			gridBagConstraints11.gridwidth = 1;
			gridBagConstraints11.insets = new Insets(10, 10, 5, 5);
			gridBagConstraints11.gridy = 0;
			l_solenoide = new JLabel();
			l_solenoide.setText("");
			l_solenoide.setIcon(new ImageIcon(getClass().getResource("/imagenes/thumb-PGV-100G.jpg")));

			//Panel Tª
			GridBagConstraints gridBagConstraints0 = new GridBagConstraints();
			gridBagConstraints0.gridx = 1;
			gridBagConstraints0.gridy = 2;

			p_interfaz = new JPanel();
			p_interfaz.setLayout(new GridBagLayout());
			p_interfaz.add(l_solenoide, gridBagConstraints11);
			p_interfaz.add(getB_activarRiego(), gridBagConstraints4);
			p_interfaz.add(getB_desactivarRiego(), gridBagConstraints5);
			p_interfaz.add(getP_sensor(), gridBagConstraints3);
			p_interfaz.add(getPb_procesando(), gridBagConstraints6);
			p_interfaz.add(esp_progresbar, gridBagConstraints7);
			p_interfaz.add(getCp_horario(), gridBagConstraints12);
			p_interfaz.add(getB_recargar(), gridBagConstraints10);
			p_interfaz.add(l_ultimaact, gridBagConstraints13);
			p_interfaz.add(l_hora, gridBagConstraints14);
		}
		return p_interfaz;
	}

	/**
	 * This method initializes b_activarRiego	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_activarRiego() {
		if (b_activarRiego == null) {
			b_activarRiego = new JButton();
			b_activarRiego.setText("Iniciar riego");
			b_activarRiego.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					logica.iniciarRiego(true);
				}
			});
		}
		return b_activarRiego;
	}

	/**
	 * This method initializes b_desactivarRiego	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_desactivarRiego() {
		if (b_desactivarRiego == null) {
			b_desactivarRiego = new JButton();
			b_desactivarRiego.setText("Finalizar riego");
			b_desactivarRiego.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					logica.pararRiego(true);
				}
			});
		}
		return b_desactivarRiego;
	}

	/**
	 * This method initializes p_sensor	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_sensor() {
		if (p_sensor == null) {
			l_tipoSensor = new JLabel();
			l_tipoSensor.setText("Temperatura (ºC)");
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(0);
			borderLayout.setVgap(0);
			p_sensor = new JPanel();
			p_sensor.setLayout(borderLayout);
			p_sensor.add(l_tipoSensor, java.awt.BorderLayout.NORTH);
			p_sensor.add(getP_botones(), java.awt.BorderLayout.SOUTH);
			p_sensor.add(getSp_listado(), java.awt.BorderLayout.CENTER);
		}
		return p_sensor;
	}

	/**
	 * This method initializes p_botones	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_botones() {
		if (p_botones == null) {
			p_botones = new JPanel();
			p_botones.setLayout(new BorderLayout());
			p_botones.add(getB_todos(), java.awt.BorderLayout.WEST);
			p_botones.add(getB_anyadir(), java.awt.BorderLayout.CENTER);
			p_botones.add(getB_eliminar(), java.awt.BorderLayout.EAST);
		}
		return p_botones;
	}

	/**
	 * This method initializes b_todos	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_todos() {
		if (b_todos == null) {
			b_todos = new JButton();
			b_todos.setText("Todos");
		}
		return b_todos;
	}

	/**
	 * This method initializes b_anyadir	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_anyadir() {
		if (b_anyadir == null) {
			b_anyadir = new JButton();
			b_anyadir.setText("Añadir");
		}
		return b_anyadir;
	}

	/**
	 * This method initializes b_eliminar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_eliminar() {
		if (b_eliminar == null) {
			b_eliminar = new JButton();
			b_eliminar.setText("Eliminar");
		}
		return b_eliminar;
	}

	/**
	 * This method initializes sp_listado	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getSp_listado() {
		if (sp_listado == null) {
			sp_listado = new JScrollPane();
			sp_listado.setViewportView(getP_listado());
		}
		return sp_listado;
	}

	/**
	 * This method initializes p_listado	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_listado() {
		if (p_listado == null) {
			p_listado = new JPanel();
			p_listado.setLayout(new GridBagLayout());
		}
		return p_listado;
	}

	/**
	 * This method initializes pb_procesando	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getPb_procesando() {
		if (pb_procesando == null) {
			pb_procesando = new JProgressBar();
			pb_procesando.setIndeterminate(true);
			pb_procesando.setVisible(false);
		}
		return pb_procesando;
	}

	/**
	 * This method initializes f_iniciando	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getF_iniciando() {
		if (f_iniciando == null) {
			f_iniciando = new JFrame();
			f_iniciando.setSize(new Dimension(228, 132));
			f_iniciando.setTitle("RegAdmin");
			f_iniciando.setContentPane(getCp_iniciando());
			f_iniciando.setLocationRelativeTo(null);
		}
		return f_iniciando;
	}

	/**
	 * This method initializes cp_iniciando	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCp_iniciando() {
		if (cp_iniciando == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.gridy = 1;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.gridx = 0;
			gridBagConstraints8.gridy = 0;
			l_iniciando = new JLabel();
			l_iniciando.setText("Conectando...");
			cp_iniciando = new JPanel();
			cp_iniciando.setLayout(new GridBagLayout());
			cp_iniciando.add(l_iniciando, gridBagConstraints8);
			cp_iniciando.add(getPb_iniciando(), gridBagConstraints9);
		}
		return cp_iniciando;
	}

	/**
	 * This method initializes pb_iniciando	
	 * 	
	 * @return javax.swing.JProgressBar	
	 */
	private JProgressBar getPb_iniciando() {
		if (pb_iniciando == null) {
			pb_iniciando = new JProgressBar();
			pb_iniciando.setIndeterminate(true);
		}
		return pb_iniciando;
	}

	/**
	 * This method initializes cp_horario	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getCp_horario() {
		if (cp_horario == null) {
			cp_horario = new JScrollPane();
			cp_horario.setViewportView(getTp_horario());
		}
		return cp_horario;
	}

	/**
	 * This method initializes tp_horario	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getTp_horario() {
		if (tp_horario == null) {
			tp_horario = new JTextPane();
			tp_horario.setEditable(false);
			//Para que aparezca el scroll horizontal hemos metido el jtextpane en un jpane y este a su vez en un jscrollpane
			//Si queremos que aparezca con su comportamiento por defecto (sin scroll horizontal pero con wrap line) solo hay que meter el jtextpane en un jscrollpane
		}
		return tp_horario;
	}

	/**
	 * This method initializes b_recargar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_recargar() {
		if (b_recargar == null) {
			b_recargar = new JButton();
			b_recargar.setIcon(new ImageIcon(getClass().getResource("/imagenes/icon_refresh_captcha.png")));
			b_recargar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					actualizarCalendario();
				}
			});
		}
		return b_recargar;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Interfaz main = new Interfaz();
		JFrame frame_main = main.getF_interfaz();
		JFrame frame_iniciando = main.getF_iniciando();
		Image icono = new ImageIcon(frame_main.getClass().getResource("/imagenes/thumb-PGV-100G.jpg")).getImage();
		frame_main.setIconImage(icono);
		frame_iniciando.setIconImage(icono);
		frame_iniciando.setVisible(true);
		main.inicializar();
	}
	void inicializar(){
		logica = new Negocio();
		int ini= logica.inicializar();
		if(ini == -1){
			JOptionPane.showMessageDialog(this.f_iniciando, "No se ha encontrado el puerto de comunicación", "Error", JOptionPane.ERROR_MESSAGE);
			f_iniciando.dispose();
			f_interfaz.dispose();
			System.exit(-1);
		}else if(ini == -2){
			JOptionPane.showMessageDialog(this.f_iniciando, "Error al inicializar el puerto", "Error", JOptionPane.ERROR_MESSAGE);
			f_iniciando.dispose();
			f_interfaz.dispose();
			System.exit(-1);
		}else{
			TareaInicializar tarea = new TareaInicializar();
			tarea.execute();
			Thread r_cal = new Thread(new RelojCalendar(),"Cuenta5min");
			r_cal.start();
			Thread r_reg = new Thread(new RelojRiego(),"Cuenta1min");
			r_reg.start();
		}
	}

	void anyardirSensor(String id, Float temp){
		//Constraints
		//id
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.ipadx = 0;
		gridBagConstraints.ipady = 0;
		gridBagConstraints.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints.gridy = l_id.size();
		//valor
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 1;
		gridBagConstraints1.ipadx = 0;
		gridBagConstraints1.ipady = 0;
		gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints1.gridy = l_value.size();
		//boton
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 2;
		gridBagConstraints2.ipadx = 0;
		gridBagConstraints2.ipady = 0;
		gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
		gridBagConstraints2.gridy = b_refresh.size();

		l_id.add(new JLabel());
		l_id.get(l_id.size()-1).setText(id);
		l_value.add(new JLabel());
		l_value.get(l_value.size()-1).setText(temp.toString());
		b_refresh.add(new JButton());
		//b_refresh.get(b_refresh.size()-1).setText("REF");
		b_refresh.get(b_refresh.size()-1).setIcon(new ImageIcon(getClass().getResource("/imagenes/iconoForosActualizar.png")));
		b_refresh.get(b_refresh.size()-1).addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int indice = b_refresh.indexOf((JButton)e.getSource());
				//la etiqueta, el valor y el boton comparten el mismo id
				pb_procesando.setVisible(true);
				//No se puede obtener la temperatura de mas de dos sensores a la vez
				//Desactivamos la actualizacion de los todos
				for (int i=0; i<b_refresh.size();i++)
					b_refresh.get(i).setEnabled(false);
				TareaTemperatura tarea = new TareaTemperatura(indice);
				tarea.execute();
			}
		});

		p_listado.add(l_id.get(l_id.size()-1), gridBagConstraints);
		p_listado.add(l_value.get(l_value.size()-1), gridBagConstraints1);
		p_listado.add(b_refresh.get(b_refresh.size()-1), gridBagConstraints2);
	}
	private void actualizarCalendario(){
		eventos = logica.cargarCalendario();
		//Pintamos los eventos
		pintarEventos();
		//Actualizar la hora
		DecimalFormat entero = new DecimalFormat("00");
		Calendar now= Calendar.getInstance();
		l_ultimaact.setText("Última actualización: "+ entero.format(now.get(Calendar.HOUR_OF_DAY)) + ":" + entero.format(now.get(Calendar.MINUTE)));
		//Comprobamos como tiene que estar el riego
		eventos = logica.comprobarRiego();
		//Actualizar la hora
		l_hora.setText("Hora: "+ entero.format(now.get(Calendar.HOUR_OF_DAY)) + ":" + entero.format(now.get(Calendar.MINUTE)));			

	}	
	private void pintarEventos(){
		try {
			tp_horario.setText("");
			StyledDocument doc = tp_horario.getStyledDocument();
			//ROJO
			// Create a style object and then set the style attributes
			Style styleRed = doc.addStyle("StyleRed", null);
			// Italic -> cursiva
			StyleConstants.setItalic(styleRed, true);
			// Bold -> negrita
			StyleConstants.setBold(styleRed, false);
			// Font family
			StyleConstants.setFontFamily(styleRed, "SansSerif");
			// Font size
			StyleConstants.setFontSize(styleRed, 12);
			// Background color
			StyleConstants.setBackground(styleRed, Color.white);
			// Foreground color
			StyleConstants.setForeground(styleRed, Color.red);
			//NEGRO
			Style styleBlack = doc.addStyle("StyleBlack", null);
			StyleConstants.setItalic(styleBlack, false);
			StyleConstants.setBold(styleBlack, false);
			StyleConstants.setFontFamily(styleBlack, "SansSerif");
			StyleConstants.setFontSize(styleBlack, 12);
			StyleConstants.setBackground(styleBlack, Color.white);
			StyleConstants.setForeground(styleBlack, Color.black);
			//VERDE
			Style styleGreen = doc.addStyle("StyleGreen", null);
			StyleConstants.setItalic(styleGreen, true);
			StyleConstants.setBold(styleGreen, true);
			StyleConstants.setFontFamily(styleGreen, "SansSerif");
			StyleConstants.setFontSize(styleGreen, 12);
			StyleConstants.setBackground(styleGreen, Color.white);
			StyleConstants.setForeground(styleGreen, Color.green);

			// Append to document
			for(int i = 0; i < eventos.size(); i++){
				Evento e = eventos.get(i);
				Evento.State es = e.getEstado();
				switch(es){
				case NEGRO:
					doc.insertString(doc.getLength(), e.toString() + "\n", styleBlack);
					break;
				case ROJO:
					doc.insertString(doc.getLength(), e.toString() + "\n", styleRed);
					break;
				case VERDE:
					doc.insertString(doc.getLength(), e.toString() + "\n", styleGreen);
					break;						
				}
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class TareaTemperatura extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		Float nuevo_valor = null;
		int indice;
		TareaTemperatura(int indice){
			this.indice = indice;
		}
		@Override
		public Void doInBackground() {			
			//System.out.println("Obteniendo temperatura de: " + l_id.get(indice).getText());
			nuevo_valor = logica.obtenerTemperatura(l_id.get(indice).getText());
			l_value.get(indice).setText(nuevo_valor.toString());
			//System.out.println( l_id.get(indice).getText() + ": " + nuevo_valor);
			return null;		
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			pb_procesando.setVisible(false);
			//Volvemos a permitir actualizar los sensores
			for (int i=0; i<b_refresh.size();i++)
				b_refresh.get(i).setEnabled(true);
			b_refresh.get(indice).setFocusPainted(true);
		}
	}	
	class TareaInicializar extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {			
			//System.out.println("Obteniendo temperatura de: " + l_id.get(indice).getText());
			//Sensores Tª
			String[] sensores = logica.listarSensoresT();
			for(String sensor: sensores){
				Float res=logica.obtenerTemperatura(sensor);
				anyardirSensor(sensor, res);
			}
			actualizarCalendario();
			return null;		
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			f_iniciando.setVisible(false);
			f_interfaz.setVisible(true);
		}
	}
	private class RelojCalendar implements Runnable{
		public void run() {
			// TODO Auto-generated method stub
			try {
				while(true){
					Thread.sleep(300000);
					System.out.println("Hilo actualizar calendario");
					actualizarCalendario();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private class RelojRiego implements Runnable{
		public void run() {
			// TODO Auto-generated method stub
			try {
				while(true){
					Thread.sleep(60000);
					System.out.println("Hilo comprobar riego");
					eventos=logica.comprobarRiego();
					pintarEventos();
					//Actualizar la hora
					DecimalFormat entero = new DecimalFormat("00");
					Calendar now= Calendar.getInstance();
					l_hora.setText("Hora: "+ entero.format(now.get(Calendar.HOUR_OF_DAY)) + ":" + entero.format(now.get(Calendar.MINUTE)));					
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}