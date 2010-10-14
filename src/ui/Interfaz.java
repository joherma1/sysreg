package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.ImageIcon;

import logic.Negocio;
import javax.swing.JScrollPane;

public class Interfaz {

	private JFrame f_interfaz = null;  //  @jve:decl-index=0:visual-constraint="169,48"
	private JPanel p_interfaz = null;
	private JButton b_activarRiego = null;
	private JButton b_desactivarRiego = null;
	private JLabel l_solenoide = null;
	private Negocio logica = null;  //  @jve:decl-index=0:
	private String [] sensores = null;
	//Panel T�
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
	/**
	 * This method initializes f_interfaz	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getF_interfaz() {
		if (f_interfaz == null) {
			f_interfaz = new JFrame();
			f_interfaz.setSize(new Dimension(324, 287));
			f_interfaz.setTitle("RegAdmin");
			f_interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f_interfaz.setContentPane(getP_interfaz());
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
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridwidth = 2;
			gridBagConstraints3.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints3.ipady = 80;
			gridBagConstraints3.gridy = 2;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 2;
			gridBagConstraints5.gridy = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints4.gridy = 0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 1;
			gridBagConstraints11.gridheight = 2;
			gridBagConstraints11.anchor = GridBagConstraints.WEST;
			gridBagConstraints11.gridwidth = 1;
			gridBagConstraints11.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints11.gridy = 0;
			l_solenoide = new JLabel();
			l_solenoide.setText("");
			l_solenoide.setIcon(new ImageIcon(getClass().getResource("/imagenes/thumb-PGV-100G.jpg")));
			
			//Panel T�
			GridBagConstraints gridBagConstraints0 = new GridBagConstraints();
			gridBagConstraints0.gridx = 1;
			gridBagConstraints0.gridy = 2;
			
			p_interfaz = new JPanel();
			p_interfaz.setLayout(new GridBagLayout());
			p_interfaz.add(l_solenoide, gridBagConstraints11);
			p_interfaz.add(getB_activarRiego(), gridBagConstraints4);
			p_interfaz.add(getB_desactivarRiego(), gridBagConstraints5);
			p_interfaz.add(getP_sensor(), gridBagConstraints3);
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
					logica.iniciarRiego();
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
					logica.pararRiego();
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
			b_eliminar.setText("Eliminár");
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Interfaz main = new Interfaz();
		JFrame frame_main = main.getF_interfaz();
		main.inicializar();
		//main.inicializar_sensores();
		frame_main.setVisible(true);
	}
	void inicializar(){
			logica = new Negocio();
			//Sensores T�
			String[] sensores = logica.listarSensoresT();
			for(String sensor: sensores){
				Float res=logica.obtenerTemperatura(sensor);
				anyardirSensor(sensor, res);
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
				Float nuevo_valor = logica.obtenerTemperatura(l_id.get(indice).getText());
				l_value.get(indice).setText(nuevo_valor.toString());
			}
		});
				
		p_listado.add(l_id.get(l_id.size()-1), gridBagConstraints);
		p_listado.add(l_value.get(l_value.size()-1), gridBagConstraints1);
		p_listado.add(b_refresh.get(b_refresh.size()-1), gridBagConstraints2);
	}
}
