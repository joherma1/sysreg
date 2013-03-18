package org.sysreg.regadmin.client.gui;

import org.sysreg.regadmin.client.service.AlRegServiceClientImpl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;

public class SensorsView extends Composite {
	private AlRegServiceClientImpl alregImpl;
	private Label[] lblTextoTemp;
	private Label[] lblTemperatura;
	private Button[] btnTemperatura;
	private FlexTable ftTemperatura;
	private Label lblBmpPresion;
	private Label lblBmpTemp;
	private Label lblBmpAltura;
	private Label lblBmpTiempo;
	private Label lblHH10;

	public SensorsView(final AlRegServiceClientImpl clientImpl) {
		alregImpl = clientImpl;

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setWidth("500px");

		ftTemperatura = new FlexTable();
		ftTemperatura.setBorderWidth(1);
		horizontalPanel.add(ftTemperatura);

		Label lblTemperatura = new Label("Temperatura");
		ftTemperatura.setWidget(0, 0, lblTemperatura);

		// Leemos los sensores y escribimos los campo
		alregImpl.listarSensoresTemp();

		FlexTable ftPresion = new FlexTable();
		ftPresion.setBorderWidth(1);
		horizontalPanel.add(ftPresion);

		Label lblSensorBmp = new Label("Sensor BMP085");
		ftPresion.setWidget(0, 0, lblSensorBmp);

		Label lblTextoBmpPre = new Label("Presion (Pa)");
		ftPresion.setWidget(1, 0, lblTextoBmpPre);

		lblBmpPresion = new Label("...");
		ftPresion.setWidget(1, 1, lblBmpPresion);
		//leemos el valor y lo actualizamos
		alregImpl.obtenerPresionBMP085();

		Label lblTextoBmpTemp = new Label("Temperatura");
		ftPresion.setWidget(2, 0, lblTextoBmpTemp);

		lblBmpTemp = new Label("...");
		ftPresion.setWidget(2, 1, lblBmpTemp);
		//Leemos y actualizamos
		alregImpl.obtenerTemperaturaBMP085();
		
		Label lblTextoBmpAlt = new Label("Altura");
		ftPresion.setWidget(3, 0, lblTextoBmpAlt);
		
		lblBmpAltura = new Label("...");
		ftPresion.setWidget(3, 1, lblBmpAltura);
		//Leemos y actualizamos
		alregImpl.obtenerAlturaBMP085();
		
		Label lblTextoBmpTiempo = new Label("Tiempo");
		ftPresion.setWidget(4, 0, lblTextoBmpTiempo);
		
		lblBmpTiempo = new Label("...");
		ftPresion.setWidget(4, 1, lblBmpTiempo);
		//Leemos y actualizamos
		alregImpl.obtenerEstimacionTiempoBMP085();

		Button btnRefrescar_2 = new Button("Refrescar");
		btnRefrescar_2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				alregImpl.obtenerPresionBMP085();
				alregImpl.obtenerTemperaturaBMP085();
				alregImpl.obtenerAlturaBMP085();
				alregImpl.obtenerEstimacionTiempoBMP085();
			}
		});
		ftPresion.setWidget(5, 1, btnRefrescar_2);
		ftPresion.getFlexCellFormatter().setColSpan(0, 0, 2);

		FlexTable ftHumedad = new FlexTable();
		ftHumedad.setBorderWidth(1);
		horizontalPanel.add(ftHumedad);

		Label lblSensorHhd = new Label("Sensor HH10D");
		ftHumedad.setWidget(0, 0, lblSensorHhd);

		Label lblTextoHH10 = new Label("Humedad");
		ftHumedad.setWidget(1, 0, lblTextoHH10);

		lblHH10 = new Label("...");
		ftHumedad.setWidget(1, 1, lblHH10);
		ftHumedad.getFlexCellFormatter().setColSpan(0, 0, 2);
		//Leemos y actualizamos
		alregImpl.obtenerHumedadHH10D();

		Button btnRefrescar_3 = new Button("Refrescar");
		btnRefrescar_3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				alregImpl.obtenerHumedadHH10D();
			}
		});
		ftHumedad.setWidget(2, 1, btnRefrescar_3);
	}

	public void updatatSensorTList(String[] sensors) {
		lblTextoTemp = new Label[sensors.length];
		lblTemperatura = new Label[sensors.length];
		btnTemperatura = new Button[sensors.length];
		for (int i = 0; i < sensors.length; i++) {
			// Nos lo guardamos como final para poder leerlo en el manejador
			// Si hacemos una clase aparte para la excepcion, lo tendriamos que
			// pasar como parametro
			final String sensor = sensors[i];
			lblTextoTemp[i] = new Label(sensor);
			ftTemperatura.setWidget(i + 1, 0, lblTextoTemp[i]);

			// Rellenamos con algo y pedimos que actualice la temperatura
			// asincronamente
			lblTemperatura[i] = new Label("...");
			alregImpl.obtenerTemperatura(sensor);
			
			ftTemperatura.setWidget(i + 1, 1, lblTemperatura[i]);

			// Boton refrescar
			btnTemperatura[i] = new Button("Refrescar");
			btnTemperatura[i].addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					alregImpl.obtenerTemperatura(sensor);
				}
			});
			ftTemperatura.setWidget(i + 1, 2, btnTemperatura[i]);
		}
		// Ampliamos el span de ftTemperatura
		ftTemperatura.getFlexCellFormatter().setColSpan(0, 0, 3);
	}

	public void updateLblTemp(String sensor, Float value) {
		// Buscamos el indice del sensor a actualizar
		int i;
		for (i = 0; i < lblTextoTemp.length; i++) {
			if (lblTextoTemp[i].getText().equals(sensor))
				break;
		}
		NumberFormat format = NumberFormat.getFormat(".##");
		lblTemperatura[i].setText(format.format(value));
	}
	
	public void updateLblBmpPresion(Long value){
		NumberFormat format = NumberFormat.getFormat(".##");
		lblBmpPresion.setText(format.format(value));
	}
	
	public void updateLblBmpTemp(Float value){
		NumberFormat format = NumberFormat.getFormat(".##");
		lblBmpTemp.setText(format.format(value));
	}
	
	public void updateLblBmpAltura(Float value){
		NumberFormat format = NumberFormat.getFormat(".##");
		lblBmpAltura.setText(format.format(value));
	}
	
	public void updateLblBmpEstimacionTiempo(String value){
		lblBmpTiempo.setText(value);
	}
	
	public void updateLblHH10Humedad(Float value){
		NumberFormat format = NumberFormat.getFormat(".##");
		lblHH10.setText(format.format(value));
	}
}
