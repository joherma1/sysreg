package org.sysreg.regadmin.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface AlRegServiceAsync {
	void activarRele(String servidor, AsyncCallback<Void> callback);

	void desactivarRele(String servidor, AsyncCallback<Void> callback);

	void comprobarRele(String servidor, AsyncCallback<Boolean> callback);

	void startReg(String servidor, AsyncCallback<Void> callback);

	void stopReg(String servidor, AsyncCallback<Void> callback);

	void comprobarReg(String servidor, AsyncCallback<Boolean> callback);

	void startSolenoide3V(String servidor, AsyncCallback<Void> callback);

	void stopSolenoide3V(String servidor, AsyncCallback<Void> callback);

	void comprobarSolenoide3V(String servidor, AsyncCallback<Boolean> callback);

	void contarSensoresTemp(String servidor, AsyncCallback<Integer> callback);

	void listarSensoresTemp(String servidor, AsyncCallback<String[]> callback);

	void obtenerTemperatura(String servidor, String sensor,
			AsyncCallback<Float> callback);

	void obtenerTemperaturaBMP085(String servidor, AsyncCallback<Float> callback);

	void obtenerPresionBMP085(String servidor, AsyncCallback<Long> callback);

	void obtenerAlturaBMP085(String servidor, AsyncCallback<Float> callback);

	void obtenerEstimacionTiempoBMP085(String servidor,
			AsyncCallback<String> callback);

	void obtenerHumedadHH10D(String servidor, AsyncCallback<Float> callback);
}
