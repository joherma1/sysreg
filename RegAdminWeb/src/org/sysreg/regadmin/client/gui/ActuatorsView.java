package org.sysreg.regadmin.client.gui;

import org.sysreg.regadmin.client.service.AlRegServiceClientImpl;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class ActuatorsView extends Composite {

	private AlRegServiceClientImpl alregImpl;
	public ActuatorsView(AlRegServiceClientImpl clientImpl) {
		this.alregImpl = clientImpl;
		
		HorizontalPanel horizontalPanel = new HorizontalPanel();
		initWidget(horizontalPanel);
		horizontalPanel.setSize("319px", "51px");
		
		VerticalPanel vpSolenoide1 = new VerticalPanel();
		horizontalPanel.add(vpSolenoide1);
		
		Label lblSolenoide1 = new Label("Solenoide 1");
		vpSolenoide1.add(lblSolenoide1);
		
		Button btnSol1On = new Button("ON");
		vpSolenoide1.add(btnSol1On);
		btnSol1On.setWidth("42px");
		vpSolenoide1.setCellHorizontalAlignment(btnSol1On, HasHorizontalAlignment.ALIGN_CENTER);
		
		Button btnSol1Off = new Button("OFF");
		vpSolenoide1.add(btnSol1Off);
		vpSolenoide1.setCellHorizontalAlignment(btnSol1Off, HasHorizontalAlignment.ALIGN_CENTER);
		
		VerticalPanel vpSolenoide2 = new VerticalPanel();
		horizontalPanel.add(vpSolenoide2);
		
		Label lblSolenoide2 = new Label("Solenoide 2");
		vpSolenoide2.add(lblSolenoide2);
		
		Button btnSol2On = new Button("ON");
		vpSolenoide2.add(btnSol2On);
		btnSol2On.setWidth("42px");
		vpSolenoide2.setCellHorizontalAlignment(btnSol2On, HasHorizontalAlignment.ALIGN_CENTER);
		
		Button btnSol2Off = new Button("OFF");
		vpSolenoide2.add(btnSol2Off);
		vpSolenoide2.setCellHorizontalAlignment(btnSol2Off, HasHorizontalAlignment.ALIGN_CENTER);
		
		VerticalPanel vpRele = new VerticalPanel();
		horizontalPanel.add(vpRele);
		vpRele.setSize("66px", "74px");
		
		Label lblRele = new Label("Rel\u00E9");
		vpRele.add(lblRele);
		
		Button btnReleOn = new Button("ON");
		btnReleOn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				alregImpl.activarRele();
			}
		});
		vpRele.add(btnReleOn);
		btnReleOn.setWidth("42px");
		vpRele.setCellHorizontalAlignment(btnReleOn, HasHorizontalAlignment.ALIGN_CENTER);
		
		Button btnReleOff = new Button("OFF");
		btnReleOff.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				alregImpl.desactivarRele();
			}
		});
		vpRele.add(btnReleOff);
		vpRele.setCellHorizontalAlignment(btnReleOff, HasHorizontalAlignment.ALIGN_CENTER);
		
		VerticalPanel vpValvula3V = new VerticalPanel();
		horizontalPanel.add(vpValvula3V);
		vpValvula3V.setSize("66px", "74px");
		
		Label lblValvula3V = new Label("V\u00E1lvula 3V");
		vpValvula3V.add(lblValvula3V);
		
		Button btnValvulaOn = new Button("ON");
		vpValvula3V.add(btnValvulaOn);
		vpValvula3V.setCellHorizontalAlignment(btnValvulaOn, HasHorizontalAlignment.ALIGN_CENTER);
		btnValvulaOn.setWidth("42px");
		
		Button btnValvulaOff = new Button("OFF");
		vpValvula3V.add(btnValvulaOff);
		vpValvula3V.setCellHorizontalAlignment(btnValvulaOff, HasHorizontalAlignment.ALIGN_CENTER);
	}

}
