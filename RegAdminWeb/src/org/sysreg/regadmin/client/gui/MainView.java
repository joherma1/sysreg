package org.sysreg.regadmin.client.gui;

import org.sysreg.regadmin.client.service.AlRegServiceClientImpl;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

public class MainView extends Composite {
	private String server = null;
	private VerticalPanel contentPanel;
	private AlRegServiceClientImpl alregImpl;
	private SensorsView sensorsPage;
	private Label errorMessage;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public MainView(AlRegServiceClientImpl clientImpl) {
		alregImpl = clientImpl;
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("90px", "25px");

		MenuView menu = new MenuView(this);
		verticalPanel.add(menu);

		contentPanel = new VerticalPanel();
		verticalPanel.add(contentPanel);

		errorMessage = new Label();
		errorMessage.setStyleName("messageError");
		errorMessage.setVisible(false);
		contentPanel.add(errorMessage);

		ConnectionView connectionPage = new ConnectionView(this);
		contentPanel.add(connectionPage);
		// Abrimos al iniciar el panel de conexion
		openConnection();
	}

	public void openSensors() {
		// Para abrir la vista de sensores debe haber guardado un servidor
		if (server == null) {
			contentPanel.clear();
			errorMessage
					.setText("No hay configurada una direccion IP para la placa");
			errorMessage.setVisible(true);
			contentPanel.add(errorMessage);
		} else {
			contentPanel.clear();
			sensorsPage = new SensorsView(alregImpl);
			contentPanel.add(sensorsPage);
		}

	}

	public void openActuators() {
		// Para abrir la vista de sensores debe haber guardado un servidor
		if (server == null) {
			contentPanel.clear();
			errorMessage
					.setText("No hay configurada una direccion IP para la placa");
			errorMessage.setVisible(true);
			contentPanel.add(errorMessage);
		} else {
			contentPanel.clear();
			ActuatorsView actuatorsPage = new ActuatorsView(alregImpl);
			contentPanel.add(actuatorsPage);
		}
	}

	public void openConnection() {
		contentPanel.clear();
		ConnectionView connectionPage = new ConnectionView(this);
		contentPanel.add(connectionPage);
	}

	public void updateSensorTList(String[] sensors) {
		sensorsPage.updatatSensorTList(sensors);
	}

	public void updateLblTemp(String sensor, Float value) {
		sensorsPage.updateLblTemp(sensor, value);
	}

	public void updateLblBmpPresion(Long value) {
		sensorsPage.updateLblBmpPresion(value);
	}

	public void updateLblBmpTemp(Float value) {
		sensorsPage.updateLblBmpTemp(value);
	}

	public void updateLblBmpAltura(Float value) {
		sensorsPage.updateLblBmpAltura(value);
	}

	public void updateLblBmpEstimacionTiempo(String value) {
		sensorsPage.updateLblBmpEstimacionTiempo(value);
	}
	
	public void updateLblHH10Humedad(Float value){
		sensorsPage.updateLblHH10Humedad(value);
	}
}
