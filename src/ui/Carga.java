package ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import logic.Alarma;
import logic.AlarmaRepetitiva;
import logic.Negocio3;

import com.google.api.client.util.DateTime;

public class Carga {

	private JFrame frame;
	private Negocio3 logica;
	private List<Alarma> alarmas;
	private JList l_puntuales;
	private JList l_indefinidos;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Negocio3 logica = new Negocio3();
					logica.inicializar();
					Carga window = new Carga(logica);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Carga(Negocio3 logica) {
		this.logica = logica;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 504, 315);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, -70, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 148, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0,
				0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		frame.setMinimumSize(new Dimension(410, 307));

		JLabel lblAlarmasPuntuales = new JLabel("Alarmas puntuales");
		GridBagConstraints gbc_lblAlarmasPuntuales = new GridBagConstraints();
		gbc_lblAlarmasPuntuales.anchor = GridBagConstraints.WEST;
		gbc_lblAlarmasPuntuales.gridwidth = 3;
		gbc_lblAlarmasPuntuales.insets = new Insets(10, 10, 5, 5);
		gbc_lblAlarmasPuntuales.gridx = 0;
		gbc_lblAlarmasPuntuales.gridy = 0;
		frame.getContentPane()
				.add(lblAlarmasPuntuales, gbc_lblAlarmasPuntuales);

		JLabel lblAlarmasIndefinidas = new JLabel("Alarmas indefinidas");
		GridBagConstraints gbc_lblAlarmasIndefinidas = new GridBagConstraints();
		gbc_lblAlarmasIndefinidas.anchor = GridBagConstraints.WEST;
		gbc_lblAlarmasIndefinidas.gridwidth = 3;
		gbc_lblAlarmasIndefinidas.insets = new Insets(10, 0, 5, 10);
		gbc_lblAlarmasIndefinidas.gridx = 4;
		gbc_lblAlarmasIndefinidas.gridy = 0;
		frame.getContentPane().add(lblAlarmasIndefinidas,
				gbc_lblAlarmasIndefinidas);

		JScrollPane sp_puntuales = new JScrollPane();
		GridBagConstraints gbc_sp_puntuales = new GridBagConstraints();
		gbc_sp_puntuales.gridwidth = 4;
		gbc_sp_puntuales.insets = new Insets(0, 10, 5, 10);
		gbc_sp_puntuales.fill = GridBagConstraints.BOTH;
		gbc_sp_puntuales.gridx = 0;
		gbc_sp_puntuales.gridy = 1;
		frame.getContentPane().add(sp_puntuales, gbc_sp_puntuales);
		l_puntuales = new JList();
		l_puntuales.setVisibleRowCount(100);
		l_puntuales.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sp_puntuales.setViewportView(l_puntuales);

		JScrollPane sp_indefinidos = new JScrollPane();
		GridBagConstraints gbc_sp_indefinidos = new GridBagConstraints();
		gbc_sp_indefinidos.gridwidth = 4;
		gbc_sp_indefinidos.insets = new Insets(0, 0, 5, 10);
		gbc_sp_indefinidos.fill = GridBagConstraints.BOTH;
		gbc_sp_indefinidos.gridx = 4;
		gbc_sp_indefinidos.gridy = 1;
		frame.getContentPane().add(sp_indefinidos, gbc_sp_indefinidos);

		l_indefinidos = new JList();
		sp_indefinidos.setViewportView(l_indefinidos);
		l_indefinidos.setVisibleRowCount(100);
		l_indefinidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JButton b_anyadirEventual = new JButton("");
		b_anyadirEventual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FechaHora nueva = new FechaHora(new Date());
				nueva.setModal(true);
				nueva.setVisible(true);
				Date fechaHoraNueva = nueva.getFecha();
				if (nueva.isOk()) {
					boolean modo = nueva.isModo();
					Alarma anueva;
					if (modo) // ON
						anueva = new Alarma(new DateTime(fechaHoraNueva), null,
								Alarma.Modo.ON);
					else
						anueva = new Alarma(new DateTime(fechaHoraNueva), null,
								Alarma.Modo.OFF);
					if (alarmas == null)
						alarmas = new ArrayList<Alarma>();
					alarmas.add(anueva);
					pintarAlarmas();
				}
			}
		});
		b_anyadirEventual.setIcon(new ImageIcon(Carga.class
				.getResource("/imagenes/iconic/plus_alt16.png")));
		GridBagConstraints gbc_b_anyadirEventual = new GridBagConstraints();
		gbc_b_anyadirEventual.anchor = GridBagConstraints.WEST;
		gbc_b_anyadirEventual.insets = new Insets(0, 10, 5, 5);
		gbc_b_anyadirEventual.gridx = 0;
		gbc_b_anyadirEventual.gridy = 2;
		frame.getContentPane().add(b_anyadirEventual, gbc_b_anyadirEventual);

		JButton b_eliminarEventual = new JButton("");
		b_eliminarEventual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Alarma a = (Alarma) l_puntuales.getSelectedValue();
				if (a != null) {
					alarmas.remove(a);
					pintarAlarmas();
				}
			}
		});
		b_eliminarEventual.setIcon(new ImageIcon(Carga.class
				.getResource("/imagenes/iconic/minus_alt16.png")));
		GridBagConstraints gbc_b_eliminarEventual = new GridBagConstraints();
		gbc_b_eliminarEventual.anchor = GridBagConstraints.WEST;
		gbc_b_eliminarEventual.insets = new Insets(0, 0, 5, 5);
		gbc_b_eliminarEventual.gridx = 1;
		gbc_b_eliminarEventual.gridy = 2;
		frame.getContentPane().add(b_eliminarEventual, gbc_b_eliminarEventual);

		JButton b_modificarEventual = new JButton("");
		b_modificarEventual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Alarma a = (Alarma) l_puntuales.getSelectedValue();
				if (a != null) {
					FechaHora modificada = new FechaHora(new Date(a.getFecha()
							.getValue()));
					modificada.setModal(true);
					modificada.setVisible(true);
					Date fechaNueva = modificada.getFecha();
					if (modificada.isOk()) {
						boolean modo = modificada.isModo();
						if (modo) // ON
							a.setModo(Alarma.Modo.ON);
						else
							a.setModo(Alarma.Modo.OFF);
						a.setFecha(new DateTime(fechaNueva, TimeZone
								.getTimeZone("Europe/Madrid")));
						// Como estamos trabajando con objetos estamos moviendo
						// referencias,por tanto si modificamos la referencia de
						// la arep y volvemos a pintar modificaremos el
						// contenido
						pintarAlarmas();
					}
				}
			}
		});
		b_modificarEventual.setIcon(new ImageIcon(Carga.class
				.getResource("/imagenes/iconic/pen16.png")));
		GridBagConstraints gbc_b_modificarEventual = new GridBagConstraints();
		gbc_b_modificarEventual.insets = new Insets(0, 0, 5, 5);
		gbc_b_modificarEventual.anchor = GridBagConstraints.WEST;
		gbc_b_modificarEventual.gridx = 2;
		gbc_b_modificarEventual.gridy = 2;
		frame.getContentPane()
				.add(b_modificarEventual, gbc_b_modificarEventual);

		JButton b_anyadirIndefinido = new JButton("");
		b_anyadirIndefinido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Hora nueva = new Hora(new Date());
				nueva.setModal(true);
				nueva.setVisible(true);
				Date fechaNueva = nueva.getFecha();
				if (nueva.isOk()) {
					boolean modo = nueva.isModo();
					AlarmaRepetitiva anueva;
					if (modo) // ON
						anueva = new AlarmaRepetitiva(new DateTime(fechaNueva),
								null, Alarma.Modo.ON);
					else
						anueva = new AlarmaRepetitiva(new DateTime(fechaNueva),
								null, Alarma.Modo.OFF);
					if (alarmas == null)
						alarmas = new ArrayList<Alarma>();
					alarmas.add(anueva);
					pintarAlarmas();
				}
			}
		});
		b_anyadirIndefinido.setIcon(new ImageIcon(Carga.class
				.getResource("/imagenes/iconic/plus_alt16.png")));
		GridBagConstraints gbc_b_anyadirIndefinido = new GridBagConstraints();
		gbc_b_anyadirIndefinido.anchor = GridBagConstraints.WEST;
		gbc_b_anyadirIndefinido.insets = new Insets(0, 0, 5, 5);
		gbc_b_anyadirIndefinido.gridx = 4;
		gbc_b_anyadirIndefinido.gridy = 2;
		frame.getContentPane()
				.add(b_anyadirIndefinido, gbc_b_anyadirIndefinido);

		JButton b_eliminarIndefinido = new JButton("");
		b_eliminarIndefinido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AlarmaRepetitiva arep = (AlarmaRepetitiva) l_indefinidos
						.getSelectedValue();
				if (arep != null) {
					alarmas.remove(arep);
					pintarAlarmas();
				}
			}
		});
		b_eliminarIndefinido.setIcon(new ImageIcon(Carga.class
				.getResource("/imagenes/iconic/minus_alt16.png")));
		GridBagConstraints gbc_b_eliminarIndefinido = new GridBagConstraints();
		gbc_b_eliminarIndefinido.insets = new Insets(0, 0, 5, 5);
		gbc_b_eliminarIndefinido.anchor = GridBagConstraints.WEST;
		gbc_b_eliminarIndefinido.gridx = 5;
		gbc_b_eliminarIndefinido.gridy = 2;
		frame.getContentPane().add(b_eliminarIndefinido,
				gbc_b_eliminarIndefinido);

		JButton m_modificarIndefinido = new JButton("");
		m_modificarIndefinido.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlarmaRepetitiva arep = (AlarmaRepetitiva) l_indefinidos
						.getSelectedValue();
				if (arep != null) {
					Hora modificada = new Hora(new Date(arep.getFecha()
							.getValue()));
					modificada.setModal(true);
					modificada.setVisible(true);
					Date fechaNueva = modificada.getFecha();
					if (modificada.isOk()) {
						boolean modo = modificada.isModo();
						if (modo) // ON
							arep.setModo(Alarma.Modo.ON);
						else
							arep.setModo(Alarma.Modo.OFF);
						arep.setFecha(new DateTime(fechaNueva, TimeZone
								.getTimeZone("Europe/Madrid")));
						pintarAlarmas();
					}
				}
			}
		});
		m_modificarIndefinido.setIcon(new ImageIcon(Carga.class
				.getResource("/imagenes/iconic/pen16.png")));
		GridBagConstraints gbc_m_modificarIndefinido = new GridBagConstraints();
		gbc_m_modificarIndefinido.insets = new Insets(0, 0, 5, 5);
		gbc_m_modificarIndefinido.anchor = GridBagConstraints.WEST;
		gbc_m_modificarIndefinido.gridx = 6;
		gbc_m_modificarIndefinido.gridy = 2;
		frame.getContentPane().add(m_modificarIndefinido,
				gbc_m_modificarIndefinido);

		JLabel lblInicio = new JLabel("Inicio");
		GridBagConstraints gbc_lblInicio = new GridBagConstraints();
		gbc_lblInicio.anchor = GridBagConstraints.WEST;
		gbc_lblInicio.gridwidth = 2;
		gbc_lblInicio.insets = new Insets(0, 10, 5, 5);
		gbc_lblInicio.gridx = 0;
		gbc_lblInicio.gridy = 3;
		frame.getContentPane().add(lblInicio, gbc_lblInicio);

		final JFormattedTextField txtInicio = new JFormattedTextField(
				new SimpleDateFormat("dd/MM/yy"));
		txtInicio.setMinimumSize(new Dimension(77, 28));
		GridBagConstraints gbc_txtInicio = new GridBagConstraints();
		gbc_txtInicio.fill = GridBagConstraints.BOTH;
		gbc_txtInicio.insets = new Insets(0, 0, 5, 5);
		gbc_txtInicio.gridx = 2;
		gbc_txtInicio.gridy = 3;
		frame.getContentPane().add(txtInicio, gbc_txtInicio);
		txtInicio.setValue(new Date());

		JLabel lblFin = new JLabel("Fin");
		GridBagConstraints gbc_lblFin = new GridBagConstraints();
		gbc_lblFin.anchor = GridBagConstraints.WEST;
		gbc_lblFin.gridwidth = 2;
		gbc_lblFin.insets = new Insets(0, 0, 5, 5);
		gbc_lblFin.gridx = 4;
		gbc_lblFin.gridy = 3;
		frame.getContentPane().add(lblFin, gbc_lblFin);

		final JFormattedTextField txtFin = new JFormattedTextField(
				new SimpleDateFormat("dd/MM/yy"));
		txtFin.setMinimumSize(new Dimension(77, 28));
		GridBagConstraints gbc_txtFin = new GridBagConstraints();
		gbc_txtFin.fill = GridBagConstraints.BOTH;
		gbc_txtFin.insets = new Insets(0, 0, 5, 5);
		gbc_txtFin.gridx = 6;
		gbc_txtFin.gridy = 3;
		frame.getContentPane().add(txtFin, gbc_txtFin);
		txtFin.setValue(new Date());

		JButton btnCargar = new JButton("Cargar");
		btnCargar.setToolTipText("Cargar eventos de Google Calendar");
		GridBagConstraints gbc_btnCargar = new GridBagConstraints();
		gbc_btnCargar.gridwidth = 2;
		gbc_btnCargar.anchor = GridBagConstraints.WEST;
		gbc_btnCargar.insets = new Insets(0, 0, 10, 5);
		gbc_btnCargar.gridx = 2;
		gbc_btnCargar.gridy = 4;
		frame.getContentPane().add(btnCargar, gbc_btnCargar);

		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calendar ini = Calendar.getInstance();
				ini.setTime((Date) txtInicio.getValue());
				ini.set((ini.get(Calendar.YEAR)), ini.get(Calendar.MONTH),
						ini.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
				Calendar fin = Calendar.getInstance();
				fin.setTime((Date) txtFin.getValue());
				fin.set((fin.get(Calendar.YEAR)), fin.get(Calendar.MONTH),
						fin.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
				alarmas = logica.CalendarioAAlarma(new DateTime(ini.getTime(),
						TimeZone.getTimeZone("Europe/Madrid")), new DateTime(
						fin.getTime(), TimeZone.getTimeZone("Europe/Madrid")));

				// Panel de eventos puntuales
				if (alarmas != null)
					pintarAlarmas();
			}
		});

		JButton btnSubir = new JButton("Subir");
		btnSubir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (logica.AlamarmasAPlaca(alarmas))
					JOptionPane.showMessageDialog(null,
							"Alarmas subidas a la placa", "Subir alarmas",
							JOptionPane.INFORMATION_MESSAGE);
				else
					JOptionPane.showMessageDialog(null,
							"Error al subir las alamrmas a la placa",
							"Subir alarmas", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnSubir.setToolTipText("Subir alarmas a la placa Arduino");
		GridBagConstraints gbc_btnSubir = new GridBagConstraints();
		gbc_btnSubir.insets = new Insets(0, 0, 10, 5);
		gbc_btnSubir.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSubir.gridx = 6;
		gbc_btnSubir.gridy = 4;
		frame.getContentPane().add(btnSubir, gbc_btnSubir);
		btnCargar.requestFocusInWindow();
	}

	private void pintarAlarmas() {
		DefaultListModel modeloPuntuales = new DefaultListModel();
		DefaultListModel modeloIndefinidos = new DefaultListModel();
		for (int i = 0; i < alarmas.size(); i++) {
			Alarma a = alarmas.get(i);
			if (a instanceof AlarmaRepetitiva) {
				AlarmaRepetitiva arep = (AlarmaRepetitiva) a;
				modeloIndefinidos.addElement(arep);
			} else {
				modeloPuntuales.addElement(a);
			}
		}
		l_puntuales.setModel(modeloPuntuales);
		l_indefinidos.setModel(modeloIndefinidos);
	}

	public void setVisible(boolean b) {
		this.frame.setVisible(b);
	}
}
