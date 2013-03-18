package org.sysreg.regadmin.client;

import org.sysreg.regadmin.client.gui.MainView;
import org.sysreg.regadmin.client.service.AlRegServiceClientImpl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RegAdminWeb implements EntryPoint {

	public void onModuleLoad() {
		
		//MainView mainView = new MainView();
		//RootPanel.get().add(mainView);
		
		AlRegServiceClientImpl clientImpl = new AlRegServiceClientImpl(GWT.getModuleBaseURL() + "alreg");
		RootPanel.get().add(clientImpl.getMainView());
	}

}
