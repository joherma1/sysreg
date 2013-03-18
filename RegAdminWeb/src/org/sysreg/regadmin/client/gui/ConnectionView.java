package org.sysreg.regadmin.client.gui;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class ConnectionView extends Composite {
	private MainView main;
	private final String ipFormat = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private Label ipMsgLabel = new Label();
	private TextBox textBox;

	private boolean validateIP(final String ip) {
		RegExp regExp = RegExp.compile(ipFormat);
		MatchResult matcher = regExp.exec(ip);
		return matcher != null; // equivalent to regExp.test(inputStr);
	}

	public ConnectionView(MainView mainView) {
		main = mainView;

		FlexTable flexTable = new FlexTable();
		initWidget(flexTable);
		flexTable.setSize("225px", "123px");

		Label lblSeleccioneLaPlaca = new Label("Seleccione la placa AlReg:");
		flexTable.setWidget(0, 0, lblSeleccioneLaPlaca);
		flexTable.getFlexCellFormatter().setColSpan(0, 0, 2);

		//ipMsgLabel.setStyleName("ipError");
		//ipMsgLabel.setText("IP invalida");
		ipMsgLabel.setVisible(false);
		flexTable.setWidget(1, 0, ipMsgLabel);

		final RadioButton rdbtnRed = new RadioButton("InterfazAlRegGroup",
				"Red");
		flexTable.setWidget(2, 0, rdbtnRed);
		rdbtnRed.setValue(true);

		textBox = new TextBox();
		if (main.getServer() != null)
			textBox.setText(main.getServer());
		flexTable.setWidget(2, 1, textBox);

		RadioButton rdbtnExplorarServidor = new RadioButton(
				"InterfazAlRegGroup", "Explorar servidor");
		flexTable.setWidget(3, 0, rdbtnExplorarServidor);
		flexTable.getFlexCellFormatter().setColSpan(3, 0, 2);

		Button btnGuardar = new Button("Guardar");
		btnGuardar.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (rdbtnRed.getValue() == true) {
					if (!validateIP(textBox.getText())) {
						ipMsgLabel.setStyleName("ipError");
						ipMsgLabel.setText("IP invalida");
						ipMsgLabel.setVisible(true);
					} else { //Si es valida
						ipMsgLabel.setStyleName("ipOK");
						ipMsgLabel.setText("IP guardada correctamente");
						ipMsgLabel.setVisible(true);
						main.setServer(textBox.getText());
						// main.openSensors();
					}
				} else {// rdbtnExplorarServidor
						// TODO explorar servidor
						// Leyendo en la red o leyendo un fichero
				}
			}
		});
		flexTable.setWidget(4, 1, btnGuardar);
		flexTable.getFlexCellFormatter().setColSpan(1, 0, 2);
		flexTable.getCellFormatter().setHorizontalAlignment(4, 1,
				HasHorizontalAlignment.ALIGN_RIGHT);
	}

}
