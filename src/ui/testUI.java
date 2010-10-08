package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class testUI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame ventana= new JFrame("Test de la UI");
		Sensor panel=new Sensor();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.getContentPane().add(panel.getP_sensor(),BorderLayout.CENTER);
		ventana.setSize(200,300);
		ventana.setVisible(true);
		panel.anyardirSensor("1234567890123", 25.5F);
		panel.anyardirSensor("1234567890123", 25.5F);
		panel.anyardirSensor("1234567890123", 25.5F);
		panel.anyardirSensor("1234567890123", 25.5F);
		panel.anyardirSensor("1234567890123", 25.5F);
		panel.anyardirSensor("1234567890123", 25.5F);
		panel.anyardirSensor("1234567890123", 25.5F);
		panel.anyardirSensor("1234567890123", 25.5F);
		panel.anyardirSensor("1234567890123", 25.5F);
		panel.anyardirSensor("1234567890123", 25.5F);
		panel.anyardirSensor("1234567890123", 25.5F);
	}

}
