package simulator.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.model.Road;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private boolean _ok;
	private JComboBox<String> roadBox;
	private JComboBox<Weather> weatherBox;
	private JSpinner ticksSpinner;
	
	public ChangeWeatherDialog(Window parent, List<Road> roads) {
		super(parent, "Change Road Weather", ModalityType.APPLICATION_MODAL);
		initGUI(roads);
		pack();
		setLocationRelativeTo(parent);
	}
	
	private void initGUI(List<Road> roads) {
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JPanel up = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		mainPanel.add(up, BorderLayout.NORTH);
		
		JPanel down = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		mainPanel.add(down, BorderLayout.CENTER);

		up.add(new JLabel("Road:"));
		roadBox = new JComboBox<>();
		for (Road r : roads) roadBox.addItem(r.getId());
		up.add(roadBox);

		up.add(new JLabel("Weather:"));
		weatherBox = new JComboBox<Weather>(Weather.values());
		up.add(weatherBox);

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

	public String getSelectedRoad() {
		return (String) roadBox.getSelectedItem();
	}

	public Weather getSelectedWeather() {
		return (Weather) weatherBox.getSelectedItem();
	}

	public int getTicks() {
		return (Integer) ticksSpinner.getValue();
	}
}
