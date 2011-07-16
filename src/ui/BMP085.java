package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class BMP085 extends JPanel {

	/**
	 * Create the panel.
	 */
	public BMP085() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{127, 86, 0};
		gridBagLayout.rowHeights = new int[]{41, 24, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel l_SensorBmp = new JLabel("Sensor BMP085");
		GridBagConstraints gbc_l_SensorBmp = new GridBagConstraints();
		gbc_l_SensorBmp.anchor = GridBagConstraints.WEST;
		gbc_l_SensorBmp.gridwidth = 2;
		gbc_l_SensorBmp.insets = new Insets(5, 5, 5, 0);
		gbc_l_SensorBmp.gridx = 0;
		gbc_l_SensorBmp.gridy = 0;
		add(l_SensorBmp, gbc_l_SensorBmp);
		
		JLabel l_TexoPresion = new JLabel("Presión (Pa)");
		l_TexoPresion.setAlignmentX(Component.RIGHT_ALIGNMENT);
		l_TexoPresion.setAlignmentY(Component.TOP_ALIGNMENT);
		GridBagConstraints gbc_l_TexoPresion = new GridBagConstraints();
		gbc_l_TexoPresion.anchor = GridBagConstraints.WEST;
		gbc_l_TexoPresion.insets = new Insets(5, 5, 5, 5);
		gbc_l_TexoPresion.gridx = 0;
		gbc_l_TexoPresion.gridy = 1;
		add(l_TexoPresion, gbc_l_TexoPresion);
		
		JLabel l_Presion = new JLabel("101196");
		GridBagConstraints gbc_l_Presion = new GridBagConstraints();
		gbc_l_Presion.anchor = GridBagConstraints.WEST;
		gbc_l_Presion.insets = new Insets(5, 5, 5, 0);
		gbc_l_Presion.gridx = 1;
		gbc_l_Presion.gridy = 1;
		add(l_Presion, gbc_l_Presion);
		
		JLabel l_TextoTemperatura = new JLabel("Temperatura (ºC)");
		GridBagConstraints gbc_l_TextoTemperatura = new GridBagConstraints();
		gbc_l_TextoTemperatura.anchor = GridBagConstraints.WEST;
		gbc_l_TextoTemperatura.insets = new Insets(5, 5, 5, 5);
		gbc_l_TextoTemperatura.gridx = 0;
		gbc_l_TextoTemperatura.gridy = 2;
		add(l_TextoTemperatura, gbc_l_TextoTemperatura);
		
		JLabel l_Temperatura = new JLabel("28.5");
		GridBagConstraints gbc_l_Temperatura = new GridBagConstraints();
		gbc_l_Temperatura.anchor = GridBagConstraints.WEST;
		gbc_l_Temperatura.insets = new Insets(5, 5, 5, 0);
		gbc_l_Temperatura.gridx = 1;
		gbc_l_Temperatura.gridy = 2;
		add(l_Temperatura, gbc_l_Temperatura);
		
		JLabel l_TextoAltura = new JLabel("Altura (m)");
		GridBagConstraints gbc_l_TextoAltura = new GridBagConstraints();
		gbc_l_TextoAltura.anchor = GridBagConstraints.WEST;
		gbc_l_TextoAltura.insets = new Insets(5, 5, 0, 5);
		gbc_l_TextoAltura.gridx = 0;
		gbc_l_TextoAltura.gridy = 3;
		add(l_TextoAltura, gbc_l_TextoAltura);
		
		JLabel l_altura = new JLabel("34");
		GridBagConstraints gbc_l_altura = new GridBagConstraints();
		gbc_l_altura.insets = new Insets(0, 5, 0, 0);
		gbc_l_altura.anchor = GridBagConstraints.WEST;
		gbc_l_altura.gridx = 1;
		gbc_l_altura.gridy = 3;
		add(l_altura, gbc_l_altura);

	}
}
