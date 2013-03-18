package org.sysreg.regadmin.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * La interfaz del servicio que el cliente implementara. Es la funcion que
 * realizara el cliente, no devuelve nada, ACTUA directamente sobre la GUI Como
 * si fuese la logica
 * 
 * @author jose
 * 
 */
public interface AlRegServiceClient {
	void activarRele();

	void desactivarRele();

	void contarSensoresTemp();

	void listarSensoresTemp();

	void obtenerTemperatura(String sensor);

	void obtenerPresionBMP085();

	void obtenerTemperaturaBMP085();

	void obtenerAlturaBMP085();

	void obtenerEstimacionTiempoBMP085();

	void obtenerHumedadHH10D();

}
