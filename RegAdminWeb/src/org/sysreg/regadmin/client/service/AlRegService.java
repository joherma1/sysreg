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
	
	boolean comprobarRele(String servidor);
	
	void startReg(String servidor);
	
	void stopReg(String servidor);
	
	boolean comprobarReg(String servidor);
	
	void startSolenoide3V(String servidor);
	
	void stopSolenoide3V(String servidor);
	
	boolean comprobarSolenoide3V(String servidor);

	int contarSensoresTemp(String servidor);

	String[] listarSensoresTemp(String servidor);

	Float obtenerTemperatura(String servidor, String sensor);

	Float obtenerTemperaturaBMP085(String servidor);

	Long obtenerPresionBMP085(String servidor);

	Float obtenerAlturaBMP085(String servidor);

	String obtenerEstimacionTiempoBMP085(String servidor);

	Float obtenerHumedadHH10D(String servidor);

}
