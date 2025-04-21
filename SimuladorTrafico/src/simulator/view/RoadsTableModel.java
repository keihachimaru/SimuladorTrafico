package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Road;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private List<Road> _roads;
	private String[] _cols = {"id", "Length", "Weather", "Max Speed", "Speed Limit", "Total CO2", "CO2 Limit"};
	
	
	public RoadsTableModel(Controller ctrl) {
		this._ctrl = ctrl;
		_ctrl.addObserver(this);
		
		this._roads = new ArrayList<>();
	}
	
	@Override
	public int getRowCount() {
		return _roads.size();
	}

	@Override
	public int getColumnCount() {
		return _cols.length;
	}
	
	@Override
	public String getColumnName(int col) {
		return _cols[col];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Road e = _roads.get(rowIndex);
		if(columnIndex==0) return e.getId();
		else if(columnIndex==1) return e.getLength();
		else if(columnIndex==2) return e.getWeather();
		else if(columnIndex==3) return e.getMaxSpeed();
		else if(columnIndex==4) return e.getSpeedLimit();
		else if(columnIndex==5) return e.getTotalCO2();
		else if(columnIndex==6) return e.getContLimit();
		return null;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_roads = new ArrayList<>(map.getRoads());
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_roads = new ArrayList<>();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
	}
	
}
