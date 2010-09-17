package ui;
import logic.Negocio;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;

public class Principal {

	private Negocio logica = null;  //  @jve:decl-index=0:
	private JFrame f_Principal = null;  //  @jve:decl-index=0:visual-constraint="181,75"
	private JPanel cp_Principal = null;
	private JButton b_start = null;
	private JButton b_stop = null;
	private JButton b_contar = null;

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
			f_Principal.setSize(new Dimension(334, 192));
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
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 0;
			gridBagConstraints11.gridy = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 0;
			cp_Principal = new JPanel();
			cp_Principal.setLayout(new GridBagLayout());
			cp_Principal.add(getB_start(), gridBagConstraints);
			cp_Principal.add(getB_stop(), gridBagConstraints1);
			cp_Principal.add(getB_contar(), gridBagConstraints11);
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
					String[] aux= logica.listarSensores();
					for (int i=0;i<aux.length;i++)
						System.out.println(aux[i]);
				}
			});
		}
		return b_contar;
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
