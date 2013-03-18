package org.sysreg.regadmin.client.gui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class MenuView extends Composite {
	private MainView mainView;
	public MenuView(MainView main) {
		this.mainView = main;
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		
		Button btnSensores = new Button("Sensores");
		btnSensores.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				mainView.openSensors();
			}
		});
		horizontalPanel.add(btnSensores);
		
		Button btnActuadores = new Button("Actuadores");
		btnActuadores.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				mainView.openActuators();
			}
		});
		horizontalPanel.add(btnActuadores);
		
		Button btnConexion = new Button("Conexion");
		btnConexion.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				mainView.openConnection();
			}
		});
		horizontalPanel.add(btnConexion);
	}
}
