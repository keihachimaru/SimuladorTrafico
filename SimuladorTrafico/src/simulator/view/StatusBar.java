package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private JLabel timeLabel;
	private JLabel eventMsgLabel;
	
	public StatusBar(Controller _ctrl) {
		this._ctrl = _ctrl;
		_ctrl.addObserver(this);
		this.initGUI();
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		this.setBackground(new Color(34, 34, 34));

		timeLabel = new JLabel("Time: 0");
		timeLabel.setForeground(Color.WHITE);
		eventMsgLabel = new JLabel("");
		eventMsgLabel.setForeground(Color.WHITE);

		this.add(timeLabel);
		this.add(eventMsgLabel);
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		timeLabel.setText("Time: " + time);
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		timeLabel.setText("Time: "+time);
		eventMsgLabel.setText(e.toString());
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		timeLabel.setText("Time: 0");
		eventMsgLabel.setText("");
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
	}

}
