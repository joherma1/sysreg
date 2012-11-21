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

public class Selector extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JRadioButton rdbtnUsb, rdbtng;
	private String IP = "-1"; //NULL -> usb; ###.###.###.### IP, -1 EXIT
	private final String ipFormat = 
	        "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
	        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	private boolean validateIP(final String ip){          
	      Pattern pattern = Pattern.compile(ipFormat);
	      Matcher matcher = pattern.matcher(ip);
	      return matcher.matches();             
	}
	public String getIP() {
		return IP;
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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Selector.class.getResource("/imagenes/Naranjito/Naranjito 64.png")));
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 227, 144);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 117, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0,
				Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		
		rdbtnUsb = new JRadioButton("USB");
		GridBagConstraints gbc_rdbtnUsb = new GridBagConstraints();
		gbc_rdbtnUsb.insets = new Insets(10, 10, 5, 5);
		gbc_rdbtnUsb.gridx = 0;
		gbc_rdbtnUsb.gridy = 0;
		contentPanel.add(rdbtnUsb, gbc_rdbtnUsb);

		rdbtng = new JRadioButton("3G");
		GridBagConstraints gbc_rdbtng = new GridBagConstraints();
		gbc_rdbtng.anchor = GridBagConstraints.WEST;
		gbc_rdbtng.insets = new Insets(0, 10, 0, 5);
		gbc_rdbtng.gridx = 0;
		gbc_rdbtng.gridy = 1;
		contentPanel.add(rdbtng, gbc_rdbtng);
		
		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtng);
		group.add(rdbtnUsb);
		rdbtnUsb.setSelected(true);
		
		{
			textField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy = 1;
			contentPanel.add(textField, gbc_textField);
			textField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(rdbtng.isSelected()){
							//Check ip format
							if(!validateIP(textField.getText()))
								JOptionPane.showMessageDialog(null,
										"IP inválida", "Error",
										JOptionPane.ERROR_MESSAGE);
							else{//Valid IP
								IP = textField.getText();
								dispose();
							}
						}else{ //rdbtnUsb selected
							IP = null;
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
						textField.setText("-1");
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
