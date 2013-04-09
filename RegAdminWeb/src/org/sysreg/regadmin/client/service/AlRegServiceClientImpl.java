package org.sysreg.regadmin.client.service;

import org.sysreg.regadmin.client.gui.MainView;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Implementacion de servicio que utiliza el cliente. La interfaz esta definida
 * en la clase AlRegServiceClientInt
 * 
 * @author jose
 * 
 */
public class AlRegServiceClientImpl implements AlRegServiceClient {
	AlRegServiceAsync alregService;
	MainView mainView;

	public AlRegServiceClientImpl(String url) {
		/**
		 * Create a remote service proxy to talk to the server-side Greeting
		 * service.
		 */
		alregService = GWT.create(AlRegService.class);

		// Parte del tutorial
		// Sets the URL of a service implementation
		ServiceDefTarget endpoint = (ServiceDefTarget) alregService;
		endpoint.setServiceEntryPoint(url);

		this.mainView = new MainView(this);
	}

	public MainView getMainView() {
		return mainView;
	}

	@Override
	public void activarRele() {
		alregService.activarRele(mainView.getServer(),
				new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						System.out.println("Rele activado");
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de activar rele");
					}
				});
	}

	@Override
	public void desactivarRele() {
		alregService.desactivarRele(mainView.getServer(),
				new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						System.out.println("Rele desactivado");
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de deactivar rele");
					}
				});
	}

	@Override
	public void comprobarRele() {
		alregService.comprobarRele(mainView.getServer(),
				new AsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						mainView.updateTglBtnRele(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de comprobar rele");
					}
				});
	}

	@Override
	public void activarReg() {
		alregService.startReg(mainView.getServer(), new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				System.out.println("Riego activado");
			}

			@Override
			public void onFailure(Throwable caught) {
				System.err.println("Error en el callback de activar riego");
			}
		});
	}

	@Override
	public void desactivarReg() {
		alregService.stopReg(mainView.getServer(), new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				System.out.println("Riego desactivado");
			}

			@Override
			public void onFailure(Throwable caught) {
				System.err.println("Error en el callback de desactivar riego");
			}
		});
	}

	@Override
	public void comprobarReg() {
		alregService.comprobarReg(mainView.getServer(),
				new AsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						mainView.updateTglBtnSol1(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de comprobar riego");
					}
				});
	}

	@Override
	public void activarSolenoide3V() {
		alregService.startSolenoide3V(mainView.getServer(),
				new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						System.out.println("Solenoide de 3 vias activado");
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de activar solenoide de 3 vias");
					}
				});
	}

	@Override
	public void desactivarSolenoide3V() {
		alregService.stopSolenoide3V(mainView.getServer(),
				new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void result) {
						System.out.println("Solenoide de 3 vias desactivado");
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de desactivar solenoide de 3 vias");
					}
				});
	}

	@Override
	public void comprobarSolenoide3V() {
		alregService.comprobarSolenoide3V(mainView.getServer(),
				new AsyncCallback<Boolean>() {
					@Override
					public void onSuccess(Boolean result) {
						mainView.updateTglBtnValvula3V(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de comprobar solenoide de 3 vias");
					}
				});
	}

	@Override
	public void contarSensoresTemp() {
		alregService.contarSensoresTemp(mainView.getServer(),
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result != null && result >= 0) {
							// TODO
						} else {
							// TODO
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de contar sensores");
					}
				});
	}

	@Override
	public void listarSensoresTemp() {
		alregService.listarSensoresTemp(mainView.getServer(),
				new AsyncCallback<String[]>() {

					@Override
					public void onSuccess(String[] result) {
						mainView.updateSensorTList(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de listar sensores");
					}
				});
	}

	@Override
	public void obtenerTemperatura(final String sensor) {
		alregService.obtenerTemperatura(mainView.getServer(), sensor,
				new AsyncCallback<Float>() {

					@Override
					public void onSuccess(Float result) {
						if (result != null) {
							mainView.updateLblTemp(sensor, result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de temperatura");
					}
				});

	}

	@Override
	public void obtenerPresionBMP085() {
		alregService.obtenerPresionBMP085(mainView.getServer(),
				new AsyncCallback<Long>() {

					@Override
					public void onSuccess(Long result) {
						mainView.updateLblBmpPresion(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de obtener presion BMP085");
					}
				});

	}

	@Override
	public void obtenerTemperaturaBMP085() {
		alregService.obtenerTemperaturaBMP085(mainView.getServer(),
				new AsyncCallback<Float>() {

					@Override
					public void onSuccess(Float result) {
						mainView.updateLblBmpTemp(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de obtener temperatura BMP085");
					}
				});

	}

	@Override
	public void obtenerAlturaBMP085() {
		alregService.obtenerAlturaBMP085(mainView.getServer(),
				new AsyncCallback<Float>() {

					@Override
					public void onSuccess(Float result) {
						mainView.updateLblBmpAltura(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de obtener altura BMP085");
					}
				});

	}

	@Override
	public void obtenerEstimacionTiempoBMP085() {
		alregService.obtenerEstimacionTiempoBMP085(mainView.getServer(),
				new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						mainView.updateLblBmpEstimacionTiempo(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de obtener estimacion del tiempo BMP085");
					}
				});

	}

	@Override
	public void obtenerHumedadHH10D() {
		alregService.obtenerHumedadHH10D(mainView.getServer(),
				new AsyncCallback<Float>() {

					@Override
					public void onSuccess(Float result) {
						mainView.updateLblHH10Humedad(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.err
								.println("Error en el callback de obtener humedad HH10D");
					}
				});
	}
}
