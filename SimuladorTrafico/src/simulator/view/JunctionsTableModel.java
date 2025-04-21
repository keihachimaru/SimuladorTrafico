package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Road;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private List<Junction> _junctions;
	private String[] _cols = {"id", "Green", "Queues"};
	
	
	public JunctionsTableModel(Controller ctrl) {
		this._ctrl = ctrl;
		_ctrl.addObserver(this);
		
		this._junctions = new ArrayList<>();
	}
	
	@Override
	public int getRowCount() {
		return _junctions.size();
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
		Junction e = _junctions.get(rowIndex);
		if(columnIndex==0) return e.getId();
		else if(columnIndex==1) return e.getGreen();
		else if(columnIndex==2) return e.getQueues();
		return null;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_junctions = new ArrayList<>(map.getJunctions());
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_junctions = new ArrayList<>();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
	}
	
}

