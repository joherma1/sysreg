package ui;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
public class AcercaDe {

	private JDialog d_acercade = null;  //  @jve:decl-index=0:visual-constraint="196,125"
	private JPanel cp_acercade = null;
	private JLabel l_icono = null;
	private JTextArea ta_descripcion = null;
	private JPanel p_superior = null;
	private JPanel p_inferior = null;
	private JScrollPane sp_descripcion = null;
	private JLabel l_licencia = null;
	private JLabel l_vacio = null;
	/**
	 * This method initializes d_acercade	
	 * 	
	 * @return javax.swing.JDialog	
	 */
	private JDialog getD_acercade() {
		if (d_acercade == null) {
			d_acercade = new JDialog();
			d_acercade.setSize(new Dimension(444, 190));
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
			l_licencia = new JLabel();
			l_licencia.setText("Licencia GNU GPL v3");
			l_licencia.setFont(new Font("Dialog", Font.BOLD, 12));
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
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.insets = new Insets(0, 10, 0, 5);
			gridBagConstraints6.gridy = 0;
			gridBagConstraints6.ipady = 63;
			gridBagConstraints6.gridx = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridwidth = 2;
			gridBagConstraints4.gridy = 2;
			gridBagConstraints4.ipadx = 0;
			gridBagConstraints4.anchor = GridBagConstraints.CENTER;
			gridBagConstraints4.ipady = 0;
			gridBagConstraints4.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints4.gridx = 0;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.ipady = 0;
			gridBagConstraints2.gridx = 1;
			l_icono = new JLabel();
			l_icono.setIcon(new ImageIcon(getClass().getResource("/imagenes/Naranjito/Naranjito 64.png")));
			l_icono.setText("");
			cp_acercade = new JPanel();
			cp_acercade.setLayout(new GridBagLayout());
			cp_acercade.setBackground(new Color(238, 238, 238));
			cp_acercade.add(getP_superior(), gridBagConstraints2);
			cp_acercade.add(getP_inferior(), gridBagConstraints4);
			cp_acercade.add(l_icono, gridBagConstraints6);
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
			gridBagConstraints5.insets = new Insets(5, 5, 5, 5);
			gridBagConstraints5.ipadx = 334;
			gridBagConstraints5.ipady = 102;
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
			p_superior.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.lightGray));
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
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.ipadx = 293;
			gridBagConstraints7.gridy = 0;
			l_vacio = new JLabel();
			l_vacio.setText("");
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.anchor = GridBagConstraints.EAST;
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 0;
			gridBagConstraints3.fill = GridBagConstraints.BOTH;
			gridBagConstraints3.gridwidth = 1;
			gridBagConstraints3.ipadx = 2;
			gridBagConstraints3.ipady = 15;
			gridBagConstraints3.insets = new Insets(0, 10, 0, 5);
			p_inferior = new JPanel();
			p_inferior.setLayout(new GridBagLayout());
			p_inferior.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.lightGray));
			p_inferior.add(l_licencia, gridBagConstraints3);
			p_inferior.add(l_vacio, gridBagConstraints7);
		}
		return p_inferior;
	}

	/**
	 * This method initializes sp_descripcion	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getSp_descripcion() {
		if (sp_descripcion == null) {
			sp_descripcion = new JScrollPane();
			sp_descripcion.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.lightGray));
			sp_descripcion.setBackground(Color.white);
			sp_descripcion.setViewportView(getTa_descripcion());
		}
		return sp_descripcion;
	}

	public static void main(String [] args)
	{
		AcercaDe m1 = new AcercaDe();
		JDialog f1 = m1.getD_acercade();
		f1.setVisible(true);
	}

}
