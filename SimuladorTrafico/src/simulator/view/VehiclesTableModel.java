package simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Vehicle;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private List<Vehicle> _vehicles;
	private String[] _cols = {"id", "Location", "Itinerary", "CO2 Class", " Max Speed", "Speed", "Total CO2", "Distance"};
	
	
	public VehiclesTableModel(Controller ctrl) {
		this._ctrl = ctrl;
		_ctrl.addObserver(this);
		
		this._vehicles = new ArrayList<>();
	}
	
	@Override
	public int getRowCount() {
		return _vehicles.size();
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
		Vehicle e = _vehicles.get(rowIndex);
		if(columnIndex==0) return e.getId();
		else if(columnIndex==1) return e.getLocation();
		else if(columnIndex==2) return e.getItinerary();
		else if(columnIndex==3) return e.getContClass();
		else if(columnIndex==4) return e.getMaxSpeed();
		else if(columnIndex==5) return e.getSpeed();
		else if(columnIndex==6) return e.getTotalCO2();
		else if(columnIndex==7) return e.getDistance();
		return null;
	}

	@Override
	public void onAdvance(RoadMap map, Collection<Event> events, int time) {
		_vehicles = new ArrayList<>(map.getVehicles());
		fireTableDataChanged();
	}

	@Override
	public void onEventAdded(RoadMap map, Collection<Event> events, Event e, int time) {
	}

	@Override
	public void onReset(RoadMap map, Collection<Event> events, int time) {
		_vehicles = new ArrayList<>();
		fireTableDataChanged();
	}

	@Override
	public void onRegister(RoadMap map, Collection<Event> events, int time) {
	}
	
}
