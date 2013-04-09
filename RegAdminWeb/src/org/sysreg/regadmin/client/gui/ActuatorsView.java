package org.sysreg.regadmin.client.gui;

import org.sysreg.regadmin.client.service.AlRegServiceClientImpl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Image;

public class ActuatorsView extends Composite {

	private AlRegServiceClientImpl alregImpl;
	private ToggleButton tglbtnSol1;
	private ToggleButton tglbtnSol2;
	private ToggleButton tglbtnRele;
	private ToggleButton tglbtnValvula3V;

	public ActuatorsView(AlRegServiceClientImpl clientImpl) {
		this.alregImpl = clientImpl;

		HorizontalPanel horizontalPanel = new HorizontalPanel();
		horizontalPanel.setSpacing(3);
		initWidget(horizontalPanel);
		horizontalPanel.setSize("319px", "51px");

		FlexTable ftSolenoide1 = new FlexTable();
		horizontalPanel.add(ftSolenoide1);

		Label lblSolenoide1 = new Label("Solenoide 1");
		ftSolenoide1.setWidget(0, 0, lblSolenoide1);

		tglbtnSol1 = new ToggleButton("ON", "OFF");
		tglbtnSol1.setEnabled(false);
		ftSolenoide1.setWidget(1, 0, tglbtnSol1);
		tglbtnSol1.setSize("42px", "20px");
		tglbtnSol1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (tglbtnSol1.isDown()) {// Acaba de pasar a down
					// Cuando preguntamos ya ha cambiado el estado, es decir, si
					// esta
					// down ahora es que estaba up antes (ON) debemos apagar el
					// riego
					alregImpl.desactivarReg();
				} else { // Ahora va a estar UP: tenemos que encender el riego
					alregImpl.activarReg();
				}
			}
		});
		// Leemos y actualizamos el estado del riego
		alregImpl.comprobarReg();

		PushButton pshbtnRefSol1 = new PushButton(new Image(
				"images/iconic/reload24.png"));
		pshbtnRefSol1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Refrescamos el estado del riego
				tglbtnSol1.setEnabled(false);
				alregImpl.comprobarReg();
			}
		});
		ftSolenoide1.setWidget(1, 1, pshbtnRefSol1);
		pshbtnRefSol1.setSize("19px", "20px");
		ftSolenoide1.getFlexCellFormatter().setColSpan(0, 0, 2);
		ftSolenoide1.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		ftSolenoide1.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);

		FlexTable ftSolenoide2 = new FlexTable();
		horizontalPanel.add(ftSolenoide2);

		Label lblSolenoide2 = new Label("Solenoide 2");
		ftSolenoide2.setWidget(0, 0, lblSolenoide2);

		tglbtnSol2 = new ToggleButton("ON", "OFF");
		tglbtnSol2.setEnabled(false);
		ftSolenoide2.setWidget(1, 0, tglbtnSol2);
		tglbtnSol2.setSize("42px", "20px");

		PushButton pshbtnRefSol2 = new PushButton(new Image(
				"images/iconic/reload24.png"));
		pshbtnRefSol2.setEnabled(false);
		ftSolenoide2.setWidget(1, 1, pshbtnRefSol2);
		pshbtnRefSol2.setSize("19px", "20px");
		ftSolenoide2.getFlexCellFormatter().setColSpan(0, 0, 2);
		ftSolenoide2.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		ftSolenoide2.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);

		FlexTable ftRele = new FlexTable();
		horizontalPanel.add(ftRele);

		Label lblRele = new Label("Rel\u00E9");
		ftRele.setWidget(0, 0, lblRele);

		tglbtnRele = new ToggleButton("ON", "OFF");
		tglbtnRele.setEnabled(false);
		ftRele.setWidget(1, 0, tglbtnRele);
		tglbtnRele.setSize("42px", "20px");
		tglbtnRele.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Se produce el evento de click y
				if (tglbtnRele.isDown()) {// Acaba de pasar a down -> apagar
					alregImpl.desactivarRele();
				} else { // Ahora va a estar UP: tenemos que encender el riego
					alregImpl.activarRele();
				}
			}
		});
		// Leemos y actualizamos el estado del riego
		alregImpl.comprobarRele();

		PushButton pshbtnRefRele = new PushButton(new Image(
				"images/iconic/reload24.png"));
		pshbtnRefRele.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Refrescamos el estado del riego
				tglbtnRele.setEnabled(false);
				alregImpl.comprobarRele();
			}
		});
		ftRele.setWidget(1, 1, pshbtnRefRele);
		pshbtnRefRele.setSize("19px", "20px");
		ftRele.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
		ftRele.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);

		FlexTable ftValvula3V = new FlexTable();
		horizontalPanel.add(ftValvula3V);

		Label lblValvula3V = new Label("V\u00E1lvula 3V");
		ftValvula3V.setWidget(0, 0, lblValvula3V);

		tglbtnValvula3V = new ToggleButton("ON", "OFF");
		tglbtnValvula3V.setEnabled(false);
		ftValvula3V.setWidget(1, 0, tglbtnValvula3V);
		tglbtnValvula3V.setSize("42px", "20px");
		tglbtnValvula3V.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Se produce el evento de click y
				if (tglbtnValvula3V.isDown()) {// Acaba de pasar a down -> apagar
					alregImpl.desactivarSolenoide3V();
				} else { // Ahora va a estar UP: tenemos que encender el riego
					alregImpl.activarSolenoide3V();
				}
			}
		});
		// Leemos y actualizamos el estado del riego
		alregImpl.comprobarSolenoide3V();

		PushButton pshbtnRefValvula3V = new PushButton(new Image(
				"images/iconic/reload24.png"));
		pshbtnRefValvula3V.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Refrescamos el estado del riego
				tglbtnValvula3V.setEnabled(false);
				alregImpl.comprobarSolenoide3V();
			}
		});
		ftValvula3V.setWidget(1, 1, pshbtnRefValvula3V);
		pshbtnRefValvula3V.setSize("19px", "20px");
		ftValvula3V.getFlexCellFormatter().setColSpan(0, 0, 2);
		ftValvula3V.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		ftValvula3V.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_CENTER);
	}

	public void updateTglBtnSol1(Boolean value) {
		if (value == true) { // Si el riego esta activado ponemos el boton a ON
			tglbtnSol1.setEnabled(true);
			tglbtnSol1.setDown(false);
		} else {
			tglbtnSol1.setEnabled(true);
			tglbtnSol1.setDown(true);
		}
	}

	public void updateTglBtnRele(Boolean value) {
		if (value == true) { // Si el rele esta activado ponemos el boton a ON
			tglbtnRele.setEnabled(true);
			tglbtnRele.setDown(false);
		} else {
			tglbtnRele.setEnabled(true);
			tglbtnRele.setDown(true);
		}
	}

	public void updateTglBtnValvula3V(Boolean value) {
		if (value == true) { // Si el rele esta activado ponemos el boton a ON
			tglbtnValvula3V.setEnabled(true);
			tglbtnValvula3V.setDown(false);
		} else {
			tglbtnValvula3V.setEnabled(true);
			tglbtnValvula3V.setDown(true);
		}
	}
}
