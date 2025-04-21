package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private List<Event> _events;
	private List<String> _cols;
	
	
	public EventsTableModel(Controller ctrl) {
		this._ctrl = ctrl;
		_ctrl.addObserver(this);
		
		this._cols = new ArrayList<>();
		this._cols.add("Time");
		this._cols.add("Description");
		
		this._events = new ArrayList<>();
	}
	
	@Override
	public int getRowCount() {
		return _events.size();
	}

	@Override
	public int getColumnCount() {
		return _cols.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return _cols.get(col);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Event e = _events.get(rowIndex);
		if(columnIndex==0) return e.getTime();
		else if(columnIndex==1) return e.toString();
		return null;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_events = new ArrayList<>(events);
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
		_events = new ArrayList<>(events);
		fireTableDataChanged();
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_events = new ArrayList<>();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
	}
	
}
