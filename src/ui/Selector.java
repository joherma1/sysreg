package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Toolkit;

import logic.Negocio.IPMode;

public class Selector extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField3G, textFieldEthernet;
	private JRadioButton rdbtnUsb, rdbtng, rdbtnEthernet;
	private String IP = "-1"; // NULL -> usb; ###.###.###.### IP, -1 EXIT
	private IPMode mode = null;
	private final String ipFormat = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	private boolean validateIP(final String ip) {
		Pattern pattern = Pattern.compile(ipFormat);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	public String getIP() {
		return IP;
	}
	
	public IPMode getIPMode(){
		return mode;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Selector dialog = new Selector();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Selector() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Seleccione la interfaz");
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Selector.class
						.getResource("/imagenes/Naranjito/Naranjito 64.png")));
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 259, 175);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 75, 150, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);

		rdbtnUsb = new JRadioButton("USB");
		GridBagConstraints gbc_rdbtnUsb = new GridBagConstraints();
		gbc_rdbtnUsb.anchor = GridBagConstraints.WEST;
		gbc_rdbtnUsb.insets = new Insets(10, 10, 5, 5);
		gbc_rdbtnUsb.gridx = 0;
		gbc_rdbtnUsb.gridy = 0;
		contentPanel.add(rdbtnUsb, gbc_rdbtnUsb);

		rdbtng = new JRadioButton("3G");
		GridBagConstraints gbc_rdbtng = new GridBagConstraints();
		gbc_rdbtng.anchor = GridBagConstraints.WEST;
		gbc_rdbtng.insets = new Insets(0, 10, 5, 5);
		gbc_rdbtng.gridx = 0;
		gbc_rdbtng.gridy = 1;
		contentPanel.add(rdbtng, gbc_rdbtng);

		textField3G = new JTextField();
		GridBagConstraints gbc_textField3G = new GridBagConstraints();
		gbc_textField3G.insets = new Insets(0, 0, 5, 0);
		gbc_textField3G.gridx = 1;
		gbc_textField3G.gridy = 1;
		contentPanel.add(textField3G, gbc_textField3G);
		textField3G.setColumns(10);

		textFieldEthernet = new JTextField();
		GridBagConstraints gbc_textFieldEthernet = new GridBagConstraints();
		gbc_textFieldEthernet.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldEthernet.gridx = 1;
		gbc_textFieldEthernet.gridy = 2;
		contentPanel.add(textFieldEthernet, gbc_textFieldEthernet);
		textFieldEthernet.setColumns(10);
		
		rdbtnEthernet = new JRadioButton("Ethernet");
		GridBagConstraints gbc_rdbtnEthernet = new GridBagConstraints();
		gbc_rdbtnEthernet.anchor = GridBagConstraints.WEST;
		gbc_rdbtnEthernet.insets = new Insets(0, 10, 10, 5);
		gbc_rdbtnEthernet.gridx = 0;
		gbc_rdbtnEthernet.gridy = 2;
		contentPanel.add(rdbtnEthernet, gbc_rdbtnEthernet);



		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtng);
		group.add(rdbtnUsb);
		group.add(rdbtnEthernet);
		rdbtnUsb.setSelected(true);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (rdbtng.isSelected()) {
							// Check ip format
							if (!validateIP(textField3G.getText()))
								JOptionPane.showMessageDialog(null,
										"IP inválida", "Error",
										JOptionPane.ERROR_MESSAGE);
							else {// Valid IP
								IP = textField3G.getText();
								mode = IPMode.UMTS;
								dispose();
							}
						}else if (rdbtnEthernet.isSelected()){
							// Check ip format
							if (!validateIP(textFieldEthernet.getText()))
								JOptionPane.showMessageDialog(null,
										"IP inválida", "Error",
										JOptionPane.ERROR_MESSAGE);
							else {// Valid IP
								IP = textFieldEthernet.getText();
								mode = IPMode.Ethernet;
								dispose();
							}
						} else { // rdbtnUsb selected
							IP = null;
							mode = null;
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						IP = "-1";
						mode = null;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}

		}
	}
}
