package ui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
public class AcercaDe2 {

	private JDialog d_acercade = null;  //  @jve:decl-index=0:visual-constraint="196,125"
	private JPanel cp_acercade = null;
	private JLabel l_icono = null;
	private JTextArea ta_descripcion = null;
	private JPanel p_superior = null;
	private JPanel p_inferior = null;
	private JTextArea t_licencia = null;
	private JScrollPane sp_descripcion = null;
	/**
	 * This method initializes d_acercade	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private JDialog getD_acercade() {
		if (d_acercade == null) {
			d_acercade = new JDialog();
			d_acercade.setSize(new Dimension(444, 198));
			d_acercade.setTitle("Acerca de RegAdmin");
			d_acercade.setResizable(false);
			d_acercade.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/naranjito.png")));
			d_acercade.setContentPane(getCp_acercade());
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
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(10);
			borderLayout.setVgap(0);
			l_icono = new JLabel();
			l_icono.setIcon(new ImageIcon(getClass().getResource("/imagenes/Naranjito/Naranjito 64.png")));
			l_icono.setText("");
			cp_acercade = new JPanel();
			cp_acercade.setLayout(borderLayout);
			cp_acercade.setBackground(new Color(238, 238, 238));
			cp_acercade.add(getP_superior(), BorderLayout.CENTER);
			cp_acercade.add(getP_inferior(), BorderLayout.SOUTH);
			cp_acercade.add(l_icono, BorderLayout.WEST);
		}
		return cp_acercade;
	}

	/**
	 * This method initializes ta_descripcion	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getTa_descripcion() {
		if (ta_descripcion == null) {
			ta_descripcion = new JTextArea();
			ta_descripcion.setText("\n\nSoftware para trabajar con una placa Arduino con el sketch AlReg para controlar un sistema remoto agrario con la arquitectura SysReg");
			ta_descripcion.setLineWrap(true);
			ta_descripcion.setFont(new Font("Tahoma", Font.PLAIN, 12));
			ta_descripcion.setEditable(false);
		}
		return ta_descripcion;
	}
	
	/**
	 * This method initializes p_superior	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_superior() {
		if (p_superior == null) {
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.weighty = 1.0;
			gridBagConstraints5.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints5.weightx = 1.0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.ipady = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.insets = new Insets(10, 0, 10, 0);
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(5, 10, 5, 1);
			gridBagConstraints.gridy = -1;
			gridBagConstraints.ipadx = 4;
			gridBagConstraints.ipady = 15;
			gridBagConstraints.gridx = -1;
			p_superior = new JPanel();
			p_superior.setLayout(new GridBagLayout());
			p_superior.setBackground(Color.white);
			p_superior.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			p_superior.add(getSp_descripcion(), gridBagConstraints5);
		}
		return p_superior;
	}

	/**
	 * This method initializes p_inferior	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getP_inferior() {
		if (p_inferior == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.NONE;
			gridBagConstraints3.weighty = 1.0;
			gridBagConstraints3.ipadx = 0;
			gridBagConstraints3.ipady = 0;
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.insets = new Insets(15, 5, 15, 5);
			gridBagConstraints3.weightx = 1.0;
			p_inferior = new JPanel();
			p_inferior.setLayout(new GridBagLayout());
			p_inferior.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.lightGray));
			p_inferior.add(getT_licencia(), gridBagConstraints3);
		}
		return p_inferior;
	}

	/**
	 * This method initializes t_licencia	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getT_licencia() {
		if (t_licencia == null) {
			t_licencia = new JTextArea();
			t_licencia.setBackground(new Color(238, 238, 238));
			t_licencia.setText("Licencia GNU v3");
			t_licencia.setFont(new Font("Tahoma", Font.PLAIN, 12));
			t_licencia.setEditable(false);
		}
		return t_licencia;
	}

	/**
	 * This method initializes sp_descripcion	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getSp_descripcion() {
		if (sp_descripcion == null) {
			sp_descripcion = new JScrollPane();
			sp_descripcion.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			sp_descripcion.setBackground(Color.white);
			sp_descripcion.setViewportView(getTa_descripcion());
		}
		return sp_descripcion;
	}

	public static void main(String [] args)
	{
		AcercaDe2 m1 = new AcercaDe2();
		JDialog f1 = m1.getD_acercade();
		f1.setVisible(true);
	}

}
