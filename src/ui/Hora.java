package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class Hora extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JFormattedTextField textField;
	private Date fecha;
	private boolean modo;
	private boolean ok = false;

	public boolean isOk() {
		return ok;
	}

	public boolean isModo() {
		return modo;
	}

	public Date getFecha() {
		return fecha;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Hora dialog = new Hora(new Date());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Hora(Date hora) {
		this.fecha = hora;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 216, 134);
		getContentPane().setLayout(new BorderLayout());
		FlowLayout fl_contentPanel = new FlowLayout();
		fl_contentPanel.setHgap(20);
		contentPanel.setLayout(fl_contentPanel);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblHora = new JLabel("Hora");
			contentPanel.add(lblHora);
		}
		{
			textField = new JFormattedTextField(
					new SimpleDateFormat("kk:mm:ss"));
			textField.setValue(fecha);
			contentPanel.add(textField);
			textField.setColumns(8);
		}

		JLabel lblModo = new JLabel("Modo");
		contentPanel.add(lblModo);

		final JRadioButton rdbtnOn = new JRadioButton("ON");
		contentPanel.add(rdbtnOn);

		final JRadioButton rdbtnOff = new JRadioButton("OFF");
		contentPanel.add(rdbtnOff);

		// Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnOn);
		group.add(rdbtnOff);
		rdbtnOn.setSelected(true);

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						fecha = (Date) textField.getValue();
						modo = rdbtnOn.isSelected();
						ok = true;
						dispose();
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
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}

		}
	}
}
