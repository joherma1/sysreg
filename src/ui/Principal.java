package ui;
import logic.Negocio;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class Principal {

	private Negocio logica = null;  //  @jve:decl-index=0:
	private JFrame f_Principal = null;  //  @jve:decl-index=0:visual-constraint="181,23"
	private JPanel cp_Principal = null;
	private JButton b_start = null;
	private JButton b_stop = null;
	private JButton b_contar = null;
	private JButton b_listar_t = null;
	private JTextArea a_datos = null;
	private JScrollPane sc_datos = null;
	private JButton b_selec_sensor = null;
	private JButton b_obtenerTemperatura = null;
	private JComboBox cb_sensoresT = null;

	private void inicializar(){
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
			f_Principal.setSize(new Dimension(557, 247));
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
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.gridy = 3;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.gridx = 2;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 5;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 1;
			gridBagConstraints4.gridy = 4;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.weighty = 1.0;
			gridBagConstraints3.gridwidth = 1;
			gridBagConstraints3.gridheight = 7;
			gridBagConstraints3.gridx = 0;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.gridx = 1;
			gridBagConstraints12.gridy = 3;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridy = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			cp_Principal = new JPanel();
			cp_Principal.setLayout(new GridBagLayout());
			cp_Principal.add(getB_start(), gridBagConstraints);
			cp_Principal.add(getB_stop(), gridBagConstraints1);
			cp_Principal.add(getB_contar(), gridBagConstraints11);
			cp_Principal.add(getB_listar_t(), gridBagConstraints12);
			cp_Principal.add(getSc_datos(), gridBagConstraints3);
			cp_Principal.add(getB_selec_sensor(), gridBagConstraints4);
			cp_Principal.add(getB_obtenerTemperatura(), gridBagConstraints5);
			cp_Principal.add(getCb_sensoresT(), gridBagConstraints6);
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
			b_contar.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					a_datos.setText(a_datos.getText() + logica.contarSensoresT()+ "\n");
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
			b_listar_t.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String[] aux= logica.listarSensoresT();
					for (int i=0;i<aux.length;i++){
						a_datos.setText(a_datos.getText() + aux[i] + "\n");
						
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
			sc_datos.setViewportView(getA_datos());
		}
		return sc_datos;
	}
	/**
	 * This method initializes b_selec_sensor	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_selec_sensor() {
		if (b_selec_sensor == null) {
			b_selec_sensor = new JButton();
			b_selec_sensor.setText("Seleccionar Sensor");
			b_selec_sensor.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					a_datos.setText(a_datos.getText() + logica.seleccionaSensorT()+ "\n");
				}
			});
		}
		return b_selec_sensor;
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
			b_obtenerTemperatura.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					a_datos.setText(a_datos.getText() + logica.obtenerTemperatura()+ "\n");
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
		}
		return cb_sensoresT;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Principal main = new Principal();
		main.inicializar();
		JFrame frame = main.getF_Principal();
		frame.setVisible(true);
	}

}
