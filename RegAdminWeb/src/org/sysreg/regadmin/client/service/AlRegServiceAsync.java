package org.sysreg.regadmin.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface AlRegServiceAsync {
	void activarRele(String servidor, AsyncCallback<Void> callback);

	void desactivarRele(String servidor, AsyncCallback<Void> callback);

	void contarSensoresTemp(String servidor, AsyncCallback<Integer> callback);

	void listarSensoresTemp(String servidor, AsyncCallback<String[]> callback);

	void obtenerTemperatura(String servidor, String sensor,
			AsyncCallback<Float> callback);

	void obtenerTemperaturaBMP085(String servidor, AsyncCallback<Float> callback);

	void obtenerPresionBMP085(String servidor, AsyncCallback<Long> callback);

	void obtenerAlturaBMP085(String servidor, AsyncCallback<Float> callback);

	void obtenerEstimacionTiempoBMP085(String servidor, AsyncCallback<String> callback);

	void obtenerHumedadHH10D(String servidor, AsyncCallback<Float> callback);
}
