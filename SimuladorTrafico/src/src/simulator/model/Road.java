package src.simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONObject;


public abstract class Road extends SimulatedObject {
	protected Junction srcJunc;
	protected Junction destJunc;
	protected int length;
	protected int maxSpeedLim;
	protected int currSpeedLim;
	protected int contLimit;
	protected Weather weather;
	protected int totalCont;
	protected List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id);
		
		if(maxSpeed <= 0) throw new IllegalArgumentException("the maxSpeed must be a positive number.");
		this.maxSpeedLim = maxSpeed;
		
		if(contLimit < 0) throw new IllegalArgumentException("the contLimit must be a positive number.");
		this.contLimit = contLimit;
		
		if(length <= 0) throw new IllegalArgumentException("the length must be a positive number.");
		this.length = length;
		
		if(srcJunc==null) throw new IllegalArgumentException("the srcJunc cant be null.");
		this.srcJunc = srcJunc;
		
		if(destJunc==null) throw new IllegalArgumentException("the destJunc cant be null.");
		this.destJunc = destJunc;
		
		if(weather==null) throw new IllegalArgumentException("the weather cant be null.");
		this.weather = weather;
	}
	
	void enter(Vehicle v) throws Exception {
		if(v.getLocation()!=0 || v.getSpeed()!=0) {
			throw new Exception("the Location and Speed must be 0.");
		}
		this.vehicles.add(v);
	}
	
	void exit(Vehicle v) {
		this.vehicles.remove(v);
	}
	
	void setWeather(Weather w) {
		if(w==null) throw new IllegalArgumentException("the weather cant be null");
		this.weather = w;
	}
	
	void addContamination(int c) {
		if(c<0) throw new IllegalArgumentException("the 'c' cant be negative");
		this.totalCont += c;
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	
	
	@Override
	void advance(int currTime) throws Exception {
		this.reduceTotalContamination();
		this.updateSpeedLimit();
		for(Vehicle v : this.vehicles) {
			int newSpeed = this.calculateVehicleSpeed(v);
			v.setSpeed(newSpeed);
			v.advance(currTime);
		}
		
		// Reorder in descendant order
		Collections.sort(this.vehicles, new Comparator<Vehicle>() {
			public int compare(Vehicle v1, Vehicle v2) {
				return v2.getLocation() - v1.getLocation();
			}
		});
	}
	
	public int getLength() {
		return this.length;
	}
	
	public Junction getDest() {
		return this.destJunc;
	}
	
	public Junction getSrc() {
		return this.srcJunc;
	}
	
	public Weather getWeather() {
		return this.weather;
	}
	
	public int getContLimit() {
		return this.contLimit;
	}
	
	public int getMaxSpeed() {
		return this.maxSpeedLim;
	}
	
	public int getTotalCO2() {
		return this.totalCont;
	}
	
	public int getSpeedLimit() {
		return this.currSpeedLim;
	}
	
	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(this.vehicles);
	}
	
	
	@Override
	public JSONObject report() {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", this.toString());
		jsonObj.put("speedLimit", this.currSpeedLim);
		jsonObj.put("weather", this.weather);
		jsonObj.put("co2", this.totalCont);
		
		List<String> vehicleIDs = new ArrayList<>();
		for(Vehicle v : this.vehicles) {
			vehicleIDs.add(v.toString());
		}
		jsonObj.put("vehicles", vehicleIDs);
		
		return jsonObj;
	}

}
