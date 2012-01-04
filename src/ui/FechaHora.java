package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class FechaHora extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JFormattedTextField txtHora;
	private Date fecha;
	private boolean modo;
	private boolean ok = false;
	private JFormattedTextField txtFecha;

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
			FechaHora dialog = new FechaHora(new Date());
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FechaHora(Date hora) {
		this.fecha = hora;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 222, 162);
		getContentPane().setLayout(new BorderLayout());
		FlowLayout fl_contentPanel = new FlowLayout();
		fl_contentPanel.setAlignment(FlowLayout.RIGHT);
		fl_contentPanel.setHgap(20);
		contentPanel.setLayout(fl_contentPanel);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblFecha = new JLabel("Fecha");
			contentPanel.add(lblFecha);
		}
		{
			txtFecha = new JFormattedTextField(new SimpleDateFormat("dd/MM/yy"));
			txtFecha.setValue(fecha);
			contentPanel.add(txtFecha);
			txtFecha.setColumns(8);
		}
		{
			JLabel lblHora = new JLabel("Hora ");
			contentPanel.add(lblHora);
		}
		{
			txtHora = new JFormattedTextField(
					new SimpleDateFormat("HH:mm:ss"));
			txtHora.setValue(fecha);
			contentPanel.add(txtHora);
			txtHora.setColumns(8);
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
						Calendar c_hora = new GregorianCalendar(TimeZone.getTimeZone("Europe/Madrid"));
						c_hora.setTime((Date) txtHora.getValue());
						Calendar c_fecha = new GregorianCalendar(TimeZone.getTimeZone("Europe/Madrid"));
						c_fecha.setTime((Date) txtFecha.getValue());
						//en c_fecha guardamos la hora introducida
						c_fecha.set(Calendar.HOUR_OF_DAY, c_hora.get(Calendar.HOUR_OF_DAY));
						c_fecha.set(Calendar.MINUTE, c_hora.get(Calendar.MINUTE));
						c_fecha.set(Calendar.SECOND, c_hora.get(Calendar.SECOND));
						fecha = c_fecha.getTime(); 
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
