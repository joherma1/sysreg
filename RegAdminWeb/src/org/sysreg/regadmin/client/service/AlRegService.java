package org.sysreg.regadmin.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("alreg")
public interface AlRegService extends RemoteService {
	void activarRele(String servidor);

	void desactivarRele(String servidor);

	int contarSensoresTemp(String ip);

	String[] listarSensoresTemp(String servidor);

	Float obtenerTemperatura(String servidor, String sensor);

	Float obtenerTemperaturaBMP085(String servidor);

	Long obtenerPresionBMP085(String servidor);

	Float obtenerAlturaBMP085(String servidor);

	String obtenerEstimacionTiempoBMP085(String servidor);

	Float obtenerHumedadHH10D(String servidor);

}
