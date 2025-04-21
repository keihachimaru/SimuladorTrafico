package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private boolean _ok;
	private JComboBox<String> vehicleBox;
	private JComboBox<Integer> co2ClassBox;
	private JSpinner ticksSpinner;
	
	public ChangeCO2ClassDialog(Window parent, List<Vehicle> vehicles) {
		super(parent, "Change CO2 Class", ModalityType.APPLICATION_MODAL);
		initGUI(vehicles);
		pack();
		setLocationRelativeTo(parent);
	}
	
	private void initGUI(List<Vehicle> vehicles) {
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JPanel up = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		mainPanel.add(up, BorderLayout.NORTH);
		
		JPanel down = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		mainPanel.add(down, BorderLayout.CENTER);
		
		up.add(new JLabel("Vehicle:"));
		vehicleBox = new JComboBox<>();
		for (Vehicle v : vehicles) vehicleBox.addItem(v.getId());
		up.add(vehicleBox);

		up.add(new JLabel("CO2 Class:"));
		co2ClassBox = new JComboBox<>();
		for (int i = 0; i <= 10; i++) co2ClassBox.addItem(i);
		up.add(co2ClassBox);

		up.add(new JLabel("Ticks:"));
		ticksSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		up.add(ticksSpinner);

		JButton okBtn = new JButton("OK");
		okBtn.addActionListener(e -> {
			_ok = true;
			setVisible(false);
		});

		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(e -> {
			_ok = false;
			setVisible(false);
		});

		down.add(okBtn);
		down.add(cancelBtn);
		
		this.add(mainPanel);
	}

	public boolean isOK() {
		return _ok;
	}

	public String getSelectedVehicle() {
		return (String) vehicleBox.getSelectedItem();
	}

	public int getSelectedCO2Class() {
		return (Integer) co2ClassBox.getSelectedItem();
	}

	public int getTicks() {
		return (Integer) ticksSpinner.getValue();
	}
}
