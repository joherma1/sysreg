package ui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Sensor {

	private JPanel p_sensor = null;  //  @jve:decl-index=0:visual-constraint="259,24"
	private JLabel l_tipoSensor = null;
	private JButton b_todos = null;
	private JPanel p_botones = null;
	private JButton b_anyadir = null;
	private JButton b_eliminar = null;
	private JPanel p_listado = null;
	private JScrollPane sp_listado = null;
	
	private ArrayList<JLabel> l_id = new ArrayList<JLabel>();
	private ArrayList<JLabel> l_value = new ArrayList<JLabel>();
	private ArrayList<JButton> b_refresh = new ArrayList<JButton>();
	/**
	 * This method initializes p_sensor	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	 JPanel getP_sensor() {
		if (p_sensor == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(0);
			borderLayout.setVgap(0);
			l_tipoSensor = new JLabel();
			l_tipoSensor.setText("Temperatura (¼C)");
			p_sensor = new JPanel();
			p_sensor.setLayout(borderLayout);
			p_sensor.setSize(new Dimension(267, 202));
			p_sensor.add(l_tipoSensor, BorderLayout.NORTH);
			p_sensor.add(getP_botones(), BorderLayout.SOUTH);
			p_sensor.add(getSp_listado(), BorderLayout.CENTER);
		}
		return p_sensor;
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
			b_todos.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return b_todos;
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
			p_botones.add(getB_todos(), BorderLayout.WEST);
			p_botones.add(getB_anyadir(), BorderLayout.CENTER);
			p_botones.add(getB_eliminar(), BorderLayout.EAST);
		}
		return p_botones;
	}

	/**
	 * This method initializes b_anyadir	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getB_anyadir() {
		if (b_anyadir == null) {
			b_anyadir = new JButton();
			b_anyadir.setText("A–adir");
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
	 * This method initializes p_listado	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_listado() {
		if (p_listado == null) {
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.ipadx = 0;
			gridBagConstraints2.ipady = 0;
			gridBagConstraints2.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints2.gridy = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.ipadx = 0;
			gridBagConstraints1.ipady = 0;
			gridBagConstraints1.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.ipadx = 35;
			gridBagConstraints.ipady = 0;
			gridBagConstraints.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints.anchor = GridBagConstraints.CENTER;
			gridBagConstraints.gridy = 0;
			
			l_id.add(new JLabel());
			l_id.get(l_id.size()-1).setText("Identificador: ");
			l_value.add(new JLabel());
			l_value.get(l_value.size()-1).setText("Valor: ");
			b_refresh.add(new JButton());
			//b_refresh.get(b_refresh.size()-1).setText("REF");
			b_refresh.get(b_refresh.size()-1).setIcon(new ImageIcon(getClass().getResource("/imagenes/iconoForosActualizar.png")));
			
			
			p_listado = new JPanel();
			p_listado.setLayout(new GridBagLayout());
//			p_listado.add(l_id.get(l_id.size()-1), gridBagConstraints);
//			p_listado.add(l_value.get(l_value.size()-1), gridBagConstraints1);
//			p_listado.add(b_refresh.get(b_refresh.size()-1), gridBagConstraints2);
		}
		return p_listado;
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
				
		p_listado.add(l_id.get(l_id.size()-1), gridBagConstraints);
		p_listado.add(l_value.get(l_value.size()-1), gridBagConstraints1);
		p_listado.add(b_refresh.get(b_refresh.size()-1), gridBagConstraints2);
	}
}
