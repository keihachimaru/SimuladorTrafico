package simulator.view;
import java.io.InputStream;
import java.io.FileInputStream;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.*;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ControlPanel extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private boolean _stopped = true;
	private JPanel toolbar;
	private int _time;
	private RoadMap _map;
	
	public ControlPanel(Controller _ctrl) {
		this._ctrl = _ctrl;
		this._time = 0;
		this._map = _ctrl.getRoadMap();
		this._ctrl.addObserver(this);
		initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(34, 34, 34));
		
		//Toolbar
		toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		toolbar.setBackground(new Color(34,34,34));
		
		JButton fileBTN = new Kbtn("resources/icons/open.png", "Load file");
		fileBTN.addActionListener(e -> importEvents());
		
		JButton contBTN = new Kbtn("resources/icons/co2class.png", "Set contamination class");
		contBTN.addActionListener(e -> {
			try {
				List<Vehicle> vehicles = _map.getVehicles();
				Window parent = SwingUtilities.getWindowAncestor(this);
				ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog(parent, vehicles);
				dialog.setVisible(true);

				if (dialog.isOK()) {
					String vehicleId = dialog.getSelectedVehicle();
					int co2Class = dialog.getSelectedCO2Class();
					int targetTime = dialog.getTicks() + _time;

					List<Pair<String, Integer>> l = new ArrayList<>();
					l.add(new Pair<>(vehicleId, co2Class));
					
					_ctrl.addEvent(new SetContClassEvent(targetTime, l));
				}
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		
		JButton wthrBTN = new Kbtn("resources/icons/weather.png", "Set weather");
		wthrBTN.addActionListener(e -> {
			try {
				List<Road> roads = _map.getRoads();
				Window parent = SwingUtilities.getWindowAncestor(this);
				ChangeWeatherDialog dialog = new ChangeWeatherDialog(parent, roads);
				dialog.setVisible(true);

				if (dialog.isOK()) {
					String roadId = dialog.getSelectedRoad();
					Weather weather = dialog.getSelectedWeather();
					int targetTime = dialog.getTicks() + _time;
					List<Pair<String, Weather>> l = new ArrayList<>();
					l.add(new Pair<>(roadId, weather));
					_ctrl.addEvent(new SetWeatherEvent(targetTime, l));
				}
			}
			catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		JButton execBTN = new Kbtn("resources/icons/run.png", "Run simulation");
		JSpinner ticksSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		ticksSpinner.setToolTipText("Simulation ticks");
		ticksSpinner.setPreferredSize(new Dimension(80, 30));
		JLabel ticksLabel = new JLabel("Ticks :");
		ticksLabel.setForeground(Color.WHITE);
		execBTN.addActionListener(e -> {
			_stopped = false;
			setToolbarEnabled(false);
			int ticks = (int) ticksSpinner.getValue();
			run_sim(ticks);
		});
		
		JButton stopBTN = new Kbtn("resources/icons/stop.png", "Stop simulation");
		stopBTN.addActionListener(e -> _stopped = true);

		toolbar.add(fileBTN);
		toolbar.add(contBTN);
		toolbar.add(wthrBTN);
		toolbar.add(execBTN);
		toolbar.add(stopBTN);
		toolbar.add(ticksLabel);
		toolbar.add(ticksSpinner);

		// Exit
		JPanel exitApp = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		exitApp.setBackground(new Color(34,34,34));

		JButton exitBTN = new Kbtn("resources/icons/exit.png", "Exit");
		exitBTN.addActionListener(e -> System.exit(0));
		exitApp.add(exitBTN);

		
		this.add(toolbar, BorderLayout.CENTER);
		this.add(exitApp, BorderLayout.EAST);
	}
	
	private void importEvents() {
		JFileChooser fileChooser = new JFileChooser();
	    int result = fileChooser.showOpenDialog(this);

	    // Check if a file was selected
	    if (result == JFileChooser.APPROVE_OPTION) {
	        File file = fileChooser.getSelectedFile();

	        try {
	        	InputStream inputStream = new FileInputStream(file);
	        	
	            _ctrl.reset();
	            _ctrl.loadEvents(inputStream);

	        } 
	        catch (Exception ex) {
	            JOptionPane.showMessageDialog(this, "Error loading events: " + ex.getMessage(),
	                    "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
				
				new javax.swing.Timer(50, e -> {
					((Timer) e.getSource()).stop();
					run_sim(n - 1);
				}).start();
			} 
			catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(),
	                    "Error", JOptionPane.ERROR_MESSAGE);
				_stopped = true;
				setToolbarEnabled(true);
			}
		} 
		else {
			_stopped = true;
			setToolbarEnabled(true);
		}
	}
	
	private void setToolbarEnabled(boolean enable) {
		for (Component c : toolbar.getComponents()) {
			if (c instanceof JButton && ((JButton) c).getToolTipText().equals("Stop simulation")) {
				c.setEnabled(!enable);
			} 
			else {
				c.setEnabled(enable);
			}
		}
	}
	
	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		this._time = time;
		this._map = map;
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

}
