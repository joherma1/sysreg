package ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.io.IOException;
import java.lang.Thread.State;
import java.net.URI;
import java.net.URISyntaxException;
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
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import java.awt.event.KeyEvent;
import javax.swing.JMenuItem;
import javax.swing.JDialog;
import javax.swing.BorderFactory;
import javax.swing.WindowConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;

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
	private JButton b_modificar = null;
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
	private JMenuBar mb_menu = null;
	private JMenu m_configuracion = null;
	private JMenu m_sensores = null;
	private JMenu m_calendario = null;
	private JMenuItem mi_exportar = null;
	private JMenu m_ayuda = null;
	private JMenuItem mi_acercade = null;
	private JMenuItem mi_importar = null;
	private JMenuItem mi_temperatura = null;
	private JMenuItem mi_cuenta = null;
	private JDialog d_acercade = null;  //  @jve:decl-index=0:visual-constraint="801,345"
	private JPanel cp_acercade = null;
	private JPanel p_superior = null;
	private JScrollPane sp_descripcion = null;
	private JTextArea ta_descripcion = null;
	private JPanel p_inferior = null;
	private JLabel l_licencia = null;
	private JLabel l_icono = null;
	private JLabel l_homepage = null;
	private JLabel l_mailto = null;
	private JPanel p_BMP085;
	private JLabel l_SensorMP085;
	private JLabel l_TextTemperatura;
	private JLabel l_TextoPresion;
	private JLabel l_Presion;
	private JLabel l_Temperatura;
	private JLabel l_TextoAltura;
	private JLabel l_Altura;
	private JButton b_RecargarBMP085;
	private JPanel p_HH10D;
	private JLabel l_HH10D;
	private JLabel l_textHumedad;
	private JLabel l_humedad;
	private JButton b_recargarHumedad;
	/**
	 * This method initializes f_interfaz	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getF_interfaz() {
		if (f_interfaz == null) {
			f_interfaz = new JFrame();
			f_interfaz.setSize(new Dimension(656, 490));
			f_interfaz.setTitle("RegAdmin");
			f_interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f_interfaz.setVisible(false);
			f_interfaz.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/Naranjito/Naranjito 16.png")));
			f_interfaz.setJMenuBar(getMb_menu());
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
			gridBagConstraints14.gridx = 0;
			gridBagConstraints14.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints14.insets = new Insets(5, 20, 5, 5);
			gridBagConstraints14.gridy = 3;
			l_hora = new JLabel();
			l_hora.setText("Hora:");
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 3;
			gridBagConstraints10.anchor = GridBagConstraints.EAST;
			gridBagConstraints10.insets = new Insets(0, 10, 5, 10);
			gridBagConstraints10.fill = GridBagConstraints.NONE;
			gridBagConstraints10.ipadx = 0;
			gridBagConstraints10.weightx = 0.0;
			gridBagConstraints10.ipady = 0;
			gridBagConstraints10.gridy = 3;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.BOTH;
			gridBagConstraints12.gridy = 0;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.weighty = 1.0;
			gridBagConstraints12.gridheight = 3;
			gridBagConstraints12.insets = new Insets(10, 5, 5, 10);
			gridBagConstraints12.gridwidth = 3;
			gridBagConstraints12.gridx = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.ipadx = 0;
			gridBagConstraints7.ipady = 30;
			gridBagConstraints7.insets = new Insets(5, 5, 0, 5);
			gridBagConstraints7.gridy = 5;
			esp_progresbar = new JLabel();
			esp_progresbar.setText("");
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.anchor = GridBagConstraints.NORTH;
			gridBagConstraints5.insets = new Insets(5, 10, 5, 5);
			gridBagConstraints5.fill = GridBagConstraints.NONE;
			gridBagConstraints5.gridy = 2;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.insets = new Insets(5, 10, 5, 5);
			gridBagConstraints4.anchor = GridBagConstraints.NORTH;
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
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
			GridBagLayout gbl_p_interfaz = new GridBagLayout();
			gbl_p_interfaz.columnWidths = new int[]{268, 0, 185, 57};
			gbl_p_interfaz.rowHeights = new int[]{0, 0, 27, 0, 174, 0};
			gbl_p_interfaz.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
			gbl_p_interfaz.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0};
			p_interfaz.setLayout(gbl_p_interfaz);
			p_interfaz.add(l_solenoide, gridBagConstraints11);
			p_interfaz.add(getB_activarRiego(), gridBagConstraints4);
			p_interfaz.add(getB_desactivarRiego(), gridBagConstraints5);
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.gridx = 2;
			gridBagConstraints13.anchor = GridBagConstraints.WEST;
			gridBagConstraints13.insets = new Insets(0, 10, 5, 5);
			gridBagConstraints13.gridy = 3;
			l_ultimaact = new JLabel();
			l_ultimaact.setText("Última actualización:");
			p_interfaz.add(l_ultimaact, gridBagConstraints13);
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.gridy = 4;
			p_interfaz.add(getP_sensor(), gridBagConstraints3);
			GridBagConstraints gbc_p_BMP085 = new GridBagConstraints();
			gbc_p_BMP085.insets = new Insets(5, 5, 5, 5);
			gbc_p_BMP085.fill = GridBagConstraints.BOTH;
			gbc_p_BMP085.gridx = 2;
			gbc_p_BMP085.gridy = 4;
			p_interfaz.add(getP_BMP085(), gbc_p_BMP085);
			GridBagConstraints gbc_p_HH10D = new GridBagConstraints();
			gbc_p_HH10D.fill = GridBagConstraints.VERTICAL;
			gbc_p_HH10D.anchor = GridBagConstraints.WEST;
			gbc_p_HH10D.insets = new Insets(5, 5, 5, 0);
			gbc_p_HH10D.gridx = 3;
			gbc_p_HH10D.gridy = 4;
			p_interfaz.add(getPanel_1(), gbc_p_HH10D);
			p_interfaz.add(esp_progresbar, gridBagConstraints7);
			p_interfaz.add(getCp_horario(), gridBagConstraints12);
			p_interfaz.add(getB_recargar(), gridBagConstraints10);
			p_interfaz.add(l_hora, gridBagConstraints14);
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.anchor = GridBagConstraints.EAST;
			gridBagConstraints6.gridx = 3;
			gridBagConstraints6.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints6.ipady = 0;
			gridBagConstraints6.gridy = 5;
			p_interfaz.add(getPb_procesando(), gridBagConstraints6);
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
			p_sensor.setBorder(new LineBorder(new Color(0, 0, 0)));
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
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 1;
			gridBagConstraints17.ipadx = 0;
			gridBagConstraints17.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints17.gridy = 0;
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 0;
			gridBagConstraints16.anchor = GridBagConstraints.CENTER;
			gridBagConstraints16.ipadx = 0;
			gridBagConstraints16.gridwidth = 1;
			gridBagConstraints16.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints16.gridy = 0;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.gridx = 2;
			gridBagConstraints15.insets = new Insets(0, 0, 5, 0);
			gridBagConstraints15.gridy = 0;
			p_botones = new JPanel();
			p_botones.setLayout(new GridBagLayout());
			p_botones.add(getB_modificar(), gridBagConstraints15);
			p_botones.add(getB_anyadir(), gridBagConstraints16);
			p_botones.add(getB_eliminar(), gridBagConstraints17);
		}
		return p_botones;
	}

	/**
	 * This method initializes b_modificar	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_modificar() {
		if (b_modificar == null) {
			b_modificar = new JButton();
			b_modificar.setText("");
			b_modificar.setIcon(new ImageIcon(getClass().getResource("/imagenes/iconic/pen16.png")));
		}
		return b_modificar;
	}

	/**
	 * This method initializes b_anyadir	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_anyadir() {
		if (b_anyadir == null) {
			b_anyadir = new JButton();
			b_anyadir.setText("");
			b_anyadir.setIcon(new ImageIcon(getClass().getResource("/imagenes/iconic/plus_alt16.png")));
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
			b_eliminar.setText("");
			b_eliminar.setIcon(new ImageIcon(getClass().getResource("/imagenes/iconic/minus_alt16.png")));
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
			f_iniciando.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/Naranjito/Naranjito 16.png")));
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
			b_recargar.setIcon(new ImageIcon(getClass().getResource("/imagenes/iconic/reload24.png")));
			b_recargar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					actualizarCalendario();
				}
			});
		}
		return b_recargar;
	}

	/**
	 * This method initializes mb_menu	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getMb_menu() {
		if (mb_menu == null) {
			mb_menu = new JMenuBar();
			mb_menu.add(getM_configuracion());
			mb_menu.add(getM_sensores());
			mb_menu.add(getM_calendario());
			mb_menu.add(getM_ayuda());
		}
		return mb_menu;
	}

	/**
	 * This method initializes m_configuracion	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getM_configuracion() {
		if (m_configuracion == null) {
			m_configuracion = new JMenu();
			m_configuracion.setText("Configuración");
			m_configuracion.add(getMi_exportar());
			m_configuracion.add(getMi_importar());
		}
		return m_configuracion;
	}

	/**
	 * This method initializes m_sensores	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getM_sensores() {
		if (m_sensores == null) {
			m_sensores = new JMenu();
			m_sensores.setText("Sensores");
			m_sensores.add(getMi_temperatura());
		}
		return m_sensores;
	}

	/**
	 * This method initializes m_calendario	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getM_calendario() {
		if (m_calendario == null) {
			m_calendario = new JMenu();
			m_calendario.setMnemonic(KeyEvent.VK_UNDEFINED);
			m_calendario.setText("Calendario");
			m_calendario.add(getMi_cuenta());
		}
		return m_calendario;
	}

	/**
	 * This method initializes mi_exportar	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_exportar() {
		if (mi_exportar == null) {
			mi_exportar = new JMenuItem();
			mi_exportar.setText("Exportar");
		}
		return mi_exportar;
	}

	/**
	 * This method initializes m_ayuda	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getM_ayuda() {
		if (m_ayuda == null) {
			m_ayuda = new JMenu();
			m_ayuda.setText("Ayuda");
			m_ayuda.add(getMi_acercade());
		}
		return m_ayuda;
	}

	/**
	 * This method initializes mi_acercade	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_acercade() {
		if (mi_acercade == null) {
			mi_acercade = new JMenuItem();
			mi_acercade.setText("Acerca de");
			mi_acercade.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					getD_acercade();//Así la primera vez lo crea
					d_acercade.setVisible(true);
				}
			});
		}
		return mi_acercade;
	}

	/**
	 * This method initializes mi_importar	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_importar() {
		if (mi_importar == null) {
			mi_importar = new JMenuItem();
			mi_importar.setText("Importar");
		}
		return mi_importar;
	}

	/**
	 * This method initializes mi_temperatura	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_temperatura() {
		if (mi_temperatura == null) {
			mi_temperatura = new JMenuItem();
			mi_temperatura.setText("Temperatura");
		}
		return mi_temperatura;
	}

	/**
	 * This method initializes mi_cuenta	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getMi_cuenta() {
		if (mi_cuenta == null) {
			mi_cuenta = new JMenuItem();
			mi_cuenta.setText("Cuenta");
		}
		return mi_cuenta;
	}

	/**
	 * This method initializes d_acercade	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private JDialog getD_acercade() {
		if (d_acercade == null) {
			d_acercade = new JDialog();
			d_acercade.setResizable(false);
			d_acercade.setSize(new Dimension(444, 190));
			d_acercade.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			d_acercade.setModal(true);
			d_acercade.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/Naranjito/Naranjito 16.png")));
			d_acercade.setResizable(false);
			d_acercade.setContentPane(getCp_acercade());
			d_acercade.setTitle("Acerca de RegAdmin");
			d_acercade.setLocationRelativeTo(null);
		}
		return d_acercade;
	}

	/**
	 * This method initializes cp_acercade	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getCp_acercade() {
		if (cp_acercade == null) {
			GridBagConstraints gridBagConstraints61 = new GridBagConstraints();
			gridBagConstraints61.insets = new Insets(0, 15, 0, 5);
			gridBagConstraints61.gridy = 0;
			gridBagConstraints61.ipady = 63;
			gridBagConstraints61.gridx = 0;
			l_icono = new JLabel();
			l_icono.setIcon(new ImageIcon(getClass().getResource("/imagenes/Naranjito/Naranjito 64.png")));
			l_icono.setText("");
			GridBagConstraints gridBagConstraints41 = new GridBagConstraints();
			gridBagConstraints41.anchor = GridBagConstraints.CENTER;
			gridBagConstraints41.gridwidth = 2;
			gridBagConstraints41.gridx = 0;
			gridBagConstraints41.gridy = 2;
			gridBagConstraints41.ipadx = 0;
			gridBagConstraints41.ipady = 0;
			gridBagConstraints41.fill = GridBagConstraints.HORIZONTAL;
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints21.gridy = 0;
			gridBagConstraints21.ipady = 0;
			gridBagConstraints21.gridx = 1;
			cp_acercade = new JPanel();
			cp_acercade.setLayout(new GridBagLayout());
			cp_acercade.setBackground(new Color(238, 238, 238));
			cp_acercade.add(getP_superior(), gridBagConstraints21);
			cp_acercade.add(getP_inferior(), gridBagConstraints41);
			cp_acercade.add(l_icono, gridBagConstraints61);
		}
		return cp_acercade;
	}

	/**
	 * This method initializes p_superior	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_superior() {
		if (p_superior == null) {
			GridBagConstraints gridBagConstraints51 = new GridBagConstraints();
			gridBagConstraints51.fill = GridBagConstraints.BOTH;
			gridBagConstraints51.ipadx = 334;
			gridBagConstraints51.ipady = 102;
			gridBagConstraints51.weightx = 1.0;
			gridBagConstraints51.weighty = 1.0;
			gridBagConstraints51.insets = new Insets(10, 5, 5, 5);
			p_superior = new JPanel();
			p_superior.setLayout(new GridBagLayout());
			p_superior.setBackground(Color.white);
			p_superior.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.lightGray));
			p_superior.add(getSp_descripcion(), gridBagConstraints51);
		}
		return p_superior;
	}

	/**
	 * This method initializes sp_descripcion	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getSp_descripcion() {
		if (sp_descripcion == null) {
			sp_descripcion = new JScrollPane();
			sp_descripcion.setBackground(Color.white);
			sp_descripcion.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.lightGray));
			sp_descripcion.setViewportView(getTa_descripcion());
		}
		return sp_descripcion;
	}

	/**
	 * This method initializes ta_descripcion	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTa_descripcion() {
		if (ta_descripcion == null) {
			ta_descripcion = new JTextArea();
			ta_descripcion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_descripcion.setEditable(false);
			ta_descripcion.setLineWrap(true);
			ta_descripcion.setWrapStyleWord(true);//Para que busque espacios al final y corte ahí
			ta_descripcion.setText("Software para trabajar con una placa Arduino con el sketch AlReg para controlar un sistema remoto agrario con la arquitectura SysReg\nAutor: Jose Antonio Hernández Martínez\nAgradecimientos:\n\tIconos \"iconic\" ");
		}
		return ta_descripcion;
	}

	/**
	 * This method initializes p_inferior	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_inferior() {
		if (p_inferior == null) {
			GridBagConstraints gridBagConstraints19 = new GridBagConstraints();
			gridBagConstraints19.gridx = 0;
			gridBagConstraints19.anchor = GridBagConstraints.WEST;
			gridBagConstraints19.ipady = 15;
			gridBagConstraints19.ipadx = 7;
			gridBagConstraints19.insets = new Insets(0, 8, 0, 0);
			gridBagConstraints19.gridy = 0;
			l_mailto = new JLabel();
			l_mailto.setFont(new Font("Dialog", Font.BOLD, 12));
			l_mailto.setText("joherma1@gmail.com");
			l_mailto.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseExited(java.awt.event.MouseEvent e) {    
					e.getComponent().setCursor(Cursor.getDefaultCursor());
				}   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
					try {
		                URI uriMailTo = new URI("mailto", "joherma1@gmail.com", null);
		                Desktop.getDesktop().mail(uriMailTo);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			GridBagConstraints gridBagConstraints18 = new GridBagConstraints();
			gridBagConstraints18.gridx = 2;
			gridBagConstraints18.ipady = 15;
			gridBagConstraints18.ipadx = 2;
			gridBagConstraints18.anchor = GridBagConstraints.CENTER;
			gridBagConstraints18.insets = new Insets(0, 35, 0, 30);
			gridBagConstraints18.gridy = 0;
			l_homepage = new JLabel();
			l_homepage.setFont(new Font("Dialog", Font.BOLD, 12));
			l_homepage.setText("Página Principal");
			l_homepage.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseExited(java.awt.event.MouseEvent e) {    
					e.getComponent().setCursor(Cursor.getDefaultCursor());
				}   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new java.net.URI("http://code.google.com/p/sysreg/"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
			gridBagConstraints31.anchor = GridBagConstraints.EAST;
			gridBagConstraints31.insets = new Insets(0, 0, 0, 10);
			gridBagConstraints31.gridwidth = 1;
			gridBagConstraints31.gridx = 3;
			gridBagConstraints31.gridy = 0;
			gridBagConstraints31.ipadx = 2;
			gridBagConstraints31.ipady = 15;
			gridBagConstraints31.fill = GridBagConstraints.BOTH;
			l_licencia = new JLabel();
			l_licencia.setFont(new Font("Dialog", Font.BOLD, 12));
			l_licencia.setText("Licencia GNU GPL v3");
			l_licencia.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseExited(java.awt.event.MouseEvent e) {    
					e.getComponent().setCursor(Cursor.getDefaultCursor());
				}   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
				public void mouseClicked(java.awt.event.MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new java.net.URI("http://www.gnu.org/licenses/gpl.html"));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			p_inferior = new JPanel();
			p_inferior.setLayout(new GridBagLayout());
			p_inferior.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.lightGray));
			p_inferior.add(l_licencia, gridBagConstraints31);
			p_inferior.add(l_homepage, gridBagConstraints18);
			p_inferior.add(l_mailto, gridBagConstraints19);
		}
		return p_inferior;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(isMac())
			System.setProperty("apple.laf.useScreenMenuBar", "true"); //Barra de menu Mac OS
		Interfaz main = new Interfaz();
		JFrame frame_main = main.getF_interfaz();
		JFrame frame_iniciando = main.getF_iniciando();
//		Image icono = new ImageIcon(frame_main.getClass().getResource("/imagenes/thumb-PGV-100G.jpg")).getImage();
//		frame_main.setIconImage(icono);
//		frame_iniciando.setIconImage(icono);
		frame_iniciando.setVisible(true);
		main.inicializar();
	}
	
	 
	private static boolean isWindows(){
		String os = System.getProperty("os.name").toLowerCase();
		//windows
	    return (os.indexOf( "win" ) >= 0); 
 
	}
 
	private static boolean isMac(){
		String os = System.getProperty("os.name").toLowerCase();
		//Mac
	    return (os.indexOf( "mac" ) >= 0); 
 
	}
 
	private static boolean isUnix(){
		String os = System.getProperty("os.name").toLowerCase();
		//linux or unix
	    return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);
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
		b_refresh.get(b_refresh.size()-1).setIcon(new ImageIcon(getClass().getResource("/imagenes/iconic/reload24.png")));
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
			//Sensor BMP085
			Long presion = logica.obtenerPresionBMP085();
			l_Presion.setText(presion.toString());
			Float temp = logica.obtenerTemperaturaBMP085();
			l_Temperatura.setText(temp.toString());
			Float alt =  logica.obtenerAlturaBMP085();
			l_Altura.setText(alt.toString());
			
			//Sensor HH10D
			Float humedad = logica.obtenerHumedadHH10D();
			l_humedad.setText(humedad.toString());
			
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
	private JPanel getP_BMP085() {
		if (p_BMP085 == null) {
			p_BMP085 = new JPanel();
			p_BMP085.setBorder(new LineBorder(new Color(0, 0, 0)));
			GridBagLayout gbl_p_BMP085 = new GridBagLayout();
			gbl_p_BMP085.columnWidths = new int[]{106, 55, 0};
			gbl_p_BMP085.rowHeights = new int[]{16, 0, 16, 0, 0, 0};
			gbl_p_BMP085.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_p_BMP085.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
			p_BMP085.setLayout(gbl_p_BMP085);
			GridBagConstraints gbc_l_SensorMP085 = new GridBagConstraints();
			gbc_l_SensorMP085.gridwidth = 2;
			gbc_l_SensorMP085.anchor = GridBagConstraints.NORTHWEST;
			gbc_l_SensorMP085.insets = new Insets(5, 5, 5, 0);
			gbc_l_SensorMP085.gridx = 0;
			gbc_l_SensorMP085.gridy = 0;
			p_BMP085.add(getL_SensorMP085(), gbc_l_SensorMP085);
			GridBagConstraints gbc_l_TextoPresion = new GridBagConstraints();
			gbc_l_TextoPresion.anchor = GridBagConstraints.WEST;
			gbc_l_TextoPresion.insets = new Insets(5, 5, 5, 5);
			gbc_l_TextoPresion.gridx = 0;
			gbc_l_TextoPresion.gridy = 1;
			p_BMP085.add(getL_TextoPresion(), gbc_l_TextoPresion);
			GridBagConstraints gbc_l_Presion = new GridBagConstraints();
			gbc_l_Presion.anchor = GridBagConstraints.WEST;
			gbc_l_Presion.insets = new Insets(5, 5, 5, 0);
			gbc_l_Presion.gridx = 1;
			gbc_l_Presion.gridy = 1;
			p_BMP085.add(getL_Presion(), gbc_l_Presion);
			GridBagConstraints gbc_l_TextTemperatura = new GridBagConstraints();
			gbc_l_TextTemperatura.insets = new Insets(0, 5, 5, 5);
			gbc_l_TextTemperatura.anchor = GridBagConstraints.NORTHWEST;
			gbc_l_TextTemperatura.gridx = 0;
			gbc_l_TextTemperatura.gridy = 2;
			p_BMP085.add(getL_TextTemperatura(), gbc_l_TextTemperatura);
			GridBagConstraints gbc_l_Temperatura = new GridBagConstraints();
			gbc_l_Temperatura.anchor = GridBagConstraints.WEST;
			gbc_l_Temperatura.insets = new Insets(0, 5, 5, 0);
			gbc_l_Temperatura.gridx = 1;
			gbc_l_Temperatura.gridy = 2;
			p_BMP085.add(getL_Temperatura(), gbc_l_Temperatura);
			GridBagConstraints gbc_l_TextoAltura = new GridBagConstraints();
			gbc_l_TextoAltura.anchor = GridBagConstraints.WEST;
			gbc_l_TextoAltura.insets = new Insets(0, 5, 5, 5);
			gbc_l_TextoAltura.gridx = 0;
			gbc_l_TextoAltura.gridy = 3;
			p_BMP085.add(getL_TextoAltura(), gbc_l_TextoAltura);
			GridBagConstraints gbc_l_Altura = new GridBagConstraints();
			gbc_l_Altura.anchor = GridBagConstraints.WEST;
			gbc_l_Altura.insets = new Insets(0, 5, 5, 0);
			gbc_l_Altura.gridx = 1;
			gbc_l_Altura.gridy = 3;
			p_BMP085.add(getL_Altura(), gbc_l_Altura);
			GridBagConstraints gbc_b_RecargarBMP085 = new GridBagConstraints();
			gbc_b_RecargarBMP085.anchor = GridBagConstraints.SOUTHEAST;
			gbc_b_RecargarBMP085.gridx = 1;
			gbc_b_RecargarBMP085.gridy = 4;
			p_BMP085.add(getB_RecargarBMP085(), gbc_b_RecargarBMP085);
		}
		return p_BMP085;
	}
	private JLabel getL_SensorMP085() {
		if (l_SensorMP085 == null) {
			l_SensorMP085 = new JLabel("Sensor BMP085");
		}
		return l_SensorMP085;
	}
	private JLabel getL_TextTemperatura() {
		if (l_TextTemperatura == null) {
			l_TextTemperatura = new JLabel("Temperatura (ªC)");
		}
		return l_TextTemperatura;
	}
	private JLabel getL_TextoPresion() {
		if (l_TextoPresion == null) {
			l_TextoPresion = new JLabel("Presión (Pa)");
			l_TextoPresion.setAlignmentY(0.0f);
			l_TextoPresion.setAlignmentX(1.0f);
		}
		return l_TextoPresion;
	}
	private JLabel getL_Presion() {
		if (l_Presion == null) {
			l_Presion = new JLabel("101196");
		}
		return l_Presion;
	}
	private JLabel getL_Temperatura() {
		if (l_Temperatura == null) {
			l_Temperatura = new JLabel("28.5");
		}
		return l_Temperatura;
	}
	private JLabel getL_TextoAltura() {
		if (l_TextoAltura == null) {
			l_TextoAltura = new JLabel("Altura (m)");
		}
		return l_TextoAltura;
	}
	private JLabel getL_Altura() {
		if (l_Altura == null) {
			l_Altura = new JLabel("34");
		}
		return l_Altura;
	}
	private JButton getB_RecargarBMP085() {
		if (b_RecargarBMP085 == null) {
			b_RecargarBMP085 = new JButton();
			b_RecargarBMP085.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					Long presion = logica.obtenerPresionBMP085();
					l_Presion.setText(presion.toString());
					Float temp = logica.obtenerTemperaturaBMP085();
					l_Temperatura.setText(temp.toString());
					Float alt =  logica.obtenerAlturaBMP085();
					l_Altura.setText(alt.toString());
				}
			});
			b_RecargarBMP085.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/iconic/reload24.png")));
		}
		return b_RecargarBMP085;
	}
	private JPanel getPanel_1() {
		if (p_HH10D == null) {
			p_HH10D = new JPanel();
			p_HH10D.setBorder(new LineBorder(new Color(0, 0, 0)));
			GridBagLayout gbl_p_HH10D = new GridBagLayout();
			gbl_p_HH10D.columnWidths = new int[]{106, 55, 0};
			gbl_p_HH10D.rowHeights = new int[]{16, 0, 0, 0};
			gbl_p_HH10D.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
			gbl_p_HH10D.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
			p_HH10D.setLayout(gbl_p_HH10D);
			GridBagConstraints gbc_l_HH10D = new GridBagConstraints();
			gbc_l_HH10D.anchor = GridBagConstraints.NORTHWEST;
			gbc_l_HH10D.gridwidth = 2;
			gbc_l_HH10D.insets = new Insets(5, 5, 5, 0);
			gbc_l_HH10D.gridx = 0;
			gbc_l_HH10D.gridy = 0;
			p_HH10D.add(getL_HH10D(), gbc_l_HH10D);
			GridBagConstraints gbc_l_textHumedad = new GridBagConstraints();
			gbc_l_textHumedad.anchor = GridBagConstraints.WEST;
			gbc_l_textHumedad.insets = new Insets(5, 5, 5, 5);
			gbc_l_textHumedad.gridx = 0;
			gbc_l_textHumedad.gridy = 1;
			p_HH10D.add(getL_textHumedad(), gbc_l_textHumedad);
			GridBagConstraints gbc_l_humedad = new GridBagConstraints();
			gbc_l_humedad.anchor = GridBagConstraints.WEST;
			gbc_l_humedad.insets = new Insets(5, 5, 5, 0);
			gbc_l_humedad.gridx = 1;
			gbc_l_humedad.gridy = 1;
			p_HH10D.add(getL_humedad(), gbc_l_humedad);
			GridBagConstraints gbc_b_recargarHumedad = new GridBagConstraints();
			gbc_b_recargarHumedad.anchor = GridBagConstraints.SOUTHEAST;
			gbc_b_recargarHumedad.gridx = 1;
			gbc_b_recargarHumedad.gridy = 2;
			p_HH10D.add(getB_recargarHumedad(), gbc_b_recargarHumedad);
		}
		return p_HH10D;
	}
	private JLabel getL_HH10D() {
		if (l_HH10D == null) {
			l_HH10D = new JLabel("Sensor HH10D");
		}
		return l_HH10D;
	}
	private JLabel getL_textHumedad() {
		if (l_textHumedad == null) {
			l_textHumedad = new JLabel("Humedad (%)");
			l_textHumedad.setAlignmentY(0.0f);
			l_textHumedad.setAlignmentX(1.0f);
		}
		return l_textHumedad;
	}
	private JLabel getL_humedad() {
		if (l_humedad == null) {
			l_humedad = new JLabel("55.0");
		}
		return l_humedad;
	}
	private JButton getB_recargarHumedad() {
		if (b_recargarHumedad == null) {
			b_recargarHumedad = new JButton();
			b_recargarHumedad.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					Float humedad = logica.obtenerHumedadHH10D();
					l_humedad.setText(humedad.toString());
				}
			});
			b_recargarHumedad.setIcon(new ImageIcon(Interfaz.class.getResource("/imagenes/iconic/reload24.png")));
		}
		return b_recargarHumedad;
	}
}