package src.simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
	private List<Junction> itinerary;
	private int maxSpeed;
	private int currSpeed;
	private VehicleStatus state;
	private Road road;
	private int location;
	private int contClass;
	private int totalCont;
	private int totalDist;
	
	//Convenient attributes
	private int iter;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		this.road = null;
		this.location = 0;
		
		if(maxSpeed<0) throw  new IllegalArgumentException("the speed must be a positive number.");
		this.maxSpeed = maxSpeed;
		
		if(contClass<0 || contClass>10) throw  new IllegalArgumentException("the contamination class must be in the interval [0, 10].");
		this.contClass = contClass;
		
		if(itinerary.size()<2) throw  new IllegalArgumentException("Itinerary must have at least 2 Junctions.");
		this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));;
		
		this.state = VehicleStatus.PENDING;
	}
	
	void setSpeed(int s) {
		if(s<0) throw  new IllegalArgumentException("the speed 's' must be a positive number.");
		this.currSpeed = s>this.maxSpeed?this.maxSpeed:s;
	}
	
	void setContaminationClass(int c) {
		if(c<0 || c>10) throw new IllegalArgumentException("the contamination class must be in the interval [0, 10].");
		this.contClass = c;
	}
	
	@Override
	void advance(int currTime) throws Exception {
		if(this.state==VehicleStatus.TRAVELING) {
			int org = this.location;
			//Calculate new Vehicle location
			int roadLen = road.getLength();
			int newDist = this.location+this.currSpeed;
			this.location = newDist>roadLen?roadLen:newDist;
			
			int travelDist  = this.location-org;
			this.totalDist += travelDist;
			
			
			//Calculate Vehicle's contamination
			int c = this.contClass*travelDist;
			this.totalCont += c;
			this.road.addContamination(c);
			
			
			if(this.location>=roadLen) {
				this.state = VehicleStatus.WAITING;
				this.setSpeed(0);
				Junction currJunc =  this.itinerary.get(this.iter); 
				currJunc.enter(this);
			}
		}
	}
	
	void moveToNextRoad() throws Exception {
		if(this.road!=null) this.road.exit(this);
		Road nextRoad;
		
		if(this.state == VehicleStatus.PENDING || this.state == VehicleStatus.WAITING) {
			Junction currJunc =  this.itinerary.get(this.iter);
			this.iter ++;
			Junction nextJunc =  this.itinerary.get(this.iter); 
			nextRoad = currJunc.roadTo(nextJunc);
			
			this.road = nextRoad;
			nextRoad.enter(this);
			this.location = 0;
			this.state = VehicleStatus.TRAVELING;
		}
		else {
			throw new Exception("Vehicle has to be WAITING or PENDING.");
		}
	}
	
	
	public int getLocation() {
		return this.location;
	}
	
	public int getSpeed() {
		return this.currSpeed;
	}
	
	public int getMaxSpeed() {
		return this.maxSpeed;
	}
	
	public int getContClass() {
		return this.contClass;
	}
	
	public VehicleStatus getStatus() {
		return this.state;
	}
	
	public int getTotalCO2() {
		return this.totalCont;
	}
	
	public List<Junction> getItinerary() {
		return this.itinerary;
	}
	
	public Road getRoad() {
		return this.road;
	}

	@Override
	public JSONObject report() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", this.toString());
		jsonObj.put("speed", this.currSpeed);
		jsonObj.put("distance", this.totalDist);
		jsonObj.put("co2", this.totalCont);
		jsonObj.put("class", this.contClass);
		jsonObj.put("status", this.state);
		jsonObj.put("road", this.road.toString());
		jsonObj.put("location", this.location);
		
		return jsonObj;
	}

}
