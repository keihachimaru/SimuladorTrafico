package src.simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {
	protected int time;
	protected String id;
	protected int maxSpeed;
	protected int contClass;
	protected List<String> itinerary;
	
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
	  super(time);
	  this.time = time;
	  this.id = id;
	  this.maxSpeed = maxSpeed;
	  this.contClass = contClass;
	  this.itinerary = itinerary;
	}

	@Override
	public void execute(RoadMap map) throws Exception {
		List<Junction> itineraryObjs = new ArrayList<>();
		for(String s : this.itinerary) {
			itineraryObjs.add(map.getJunction(s));
		}
		Vehicle newVehicle = new Vehicle(id, maxSpeed, contClass, itineraryObjs);
		map.addVehicle(newVehicle);
		newVehicle.moveToNextRoad();
	}

}
