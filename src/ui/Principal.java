package ui;

import logic.Negocio;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.Rectangle;
import java.awt.Insets;

public class Principal {

	private Negocio logica = null; // @jve:decl-index=0:
	private JFrame f_Principal = null; // @jve:decl-index=0:visual-constraint="181,23"
	private JPanel cp_Principal = null;
	private JButton b_start = null;
	private JButton b_stop = null;
	private JButton b_contar = null;
	private JButton b_listar_t = null;
	private JTextArea a_datos = null;
	private JScrollPane sc_datos = null;
	private JButton b_obtenerTemperatura = null;
	private JComboBox cb_sensoresT = null;
	private JButton b_limpiar = null;
	private String[] sensores = null;

	private void inicializar() {
		logica = new Negocio();
	}

	/**
	 * This method initializes f_Principal
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getF_Principal() {
		if (f_Principal == null) {
			f_Principal = new JFrame();
			f_Principal.setSize(new Dimension(561, 248));
			f_Principal.setTitle("SysReg");
			f_Principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f_Principal.setContentPane(getCp_Principal());
		}
		return f_Principal;
	}

	/**
	 * This method initializes cp_Principal
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getCp_Principal() {
		if (cp_Principal == null) {
			cp_Principal = new JPanel();
			cp_Principal.setLayout(null);
			cp_Principal.add(getB_start(), null);
			cp_Principal.add(getB_stop(), null);
			cp_Principal.add(getB_contar(), null);
			cp_Principal.add(getB_listar_t(), null);
			cp_Principal.add(getSc_datos(), null);
			cp_Principal.add(getB_obtenerTemperatura(), null);
			cp_Principal.add(getCb_sensoresT(), null);
			cp_Principal.add(getB_limpiar(), null);
		}
		return cp_Principal;
	}

	/**
	 * This method initializes b_start
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getB_start() {
		if (b_start == null) {
			b_start = new JButton();
			b_start.setText("Start");
			b_start.setBounds(new Rectangle(240, 30, 75, 29));
			b_start.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					logica.iniciarRiego();
				}
			});
		}
		return b_start;
	}

	/**
	 * This method initializes b_stop
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getB_stop() {
		if (b_stop == null) {
			b_stop = new JButton();
			b_stop.setText("Stop");
			b_stop.setBounds(new Rectangle(435, 30, 75, 29));
			b_stop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					logica.pararRiego();
				}
			});
		}
		return b_stop;
	}

	/**
	 * This method initializes b_contar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getB_contar() {
		if (b_contar == null) {
			b_contar = new JButton();
			b_contar.setText("Contar Sensores");
			b_contar.setBounds(new Rectangle(210, 135, 146, 29));
			b_contar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					a_datos.setText(a_datos.getText()
							+ logica.contarSensoresT() + "\n");
				}
			});
		}
		return b_contar;
	}

	/**
	 * This method initializes b_listar_t
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getB_listar_t() {
		if (b_listar_t == null) {
			b_listar_t = new JButton();
			b_listar_t.setText("Listar Sensores");
			b_listar_t.setBounds(new Rectangle(210, 90, 139, 29));
			b_listar_t.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String[] aux = logica.listarSensoresT();
					cb_sensoresT.removeAllItems();
					for (int i = 0; i < aux.length; i++) {
						cb_sensoresT.addItem(aux[i]);
						;
					}
				}
			});
		}
		return b_listar_t;
	}

	/**
	 * This method initializes a_datos
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getA_datos() {
		if (a_datos == null) {
			a_datos = new JTextArea();
			a_datos.setText("");
			a_datos.setEditable(false);
		}
		return a_datos;
	}

	/**
	 * This method initializes sc_datos
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getSc_datos() {
		if (sc_datos == null) {
			sc_datos = new JScrollPane();
			sc_datos.setBounds(new Rectangle(0, 0, 195, 196));
			sc_datos.setViewportView(getA_datos());
		}
		return sc_datos;
	}

	/**
	 * This method initializes b_obtenerTemperatura
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getB_obtenerTemperatura() {
		if (b_obtenerTemperatura == null) {
			b_obtenerTemperatura = new JButton();
			b_obtenerTemperatura.setText("Obtener Temperatura");
			b_obtenerTemperatura.setBounds(new Rectangle(375, 135, 168, 29));
			b_obtenerTemperatura
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							String sensor = (String) cb_sensoresT
									.getSelectedItem();
							if (sensor != null) {
								a_datos.setText(a_datos.getText()
										+ logica.obtenerTemperatura(sensor)
										+ "\n");
							} else {
								JOptionPane.showMessageDialog(f_Principal,
										"Selecciona un sensor", "Error sensor",
										JOptionPane.ERROR_MESSAGE);
							}

						}
					});
		}
		return b_obtenerTemperatura;
	}

	/**
	 * This method initializes cb_sensoresT
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCb_sensoresT() {
		if (cb_sensoresT == null) {
			cb_sensoresT = new JComboBox();
			cb_sensoresT.setBounds(new Rectangle(360, 90, 191, 27));
		}
		return cb_sensoresT;
	}

	/**
	 * This method initializes b_limpiar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getB_limpiar() {
		if (b_limpiar == null) {
			b_limpiar = new JButton();
			b_limpiar.setText("Limpiar");
			b_limpiar.setBounds(new Rectangle(49, 197, 91, 29));
			b_limpiar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					a_datos.setText("");
				}
			});
		}
		return b_limpiar;
	}

	private void inicializar_sensores() {
		this.sensores = logica.listarSensoresT();
		for (int i = 0; i < sensores.length; i++)
			cb_sensoresT.addItem(sensores[i]);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Principal main = new Principal();
		main.inicializar();
		JFrame frame = main.getF_Principal();
		main.inicializar_sensores();
		frame.setVisible(true);
	}

}
