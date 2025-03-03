package src.simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class RoadMap {
	protected List<Junction> juncList;
	protected List<Road> roadList;
	protected List<Vehicle> vehicleList;
	protected Map<String,Junction> juncMap;
	protected Map<String,Road> roadMap;
	protected Map<String,Vehicle> vehicleMap;
	
	public RoadMap() {
        juncList = new ArrayList<>();
        roadList = new ArrayList<>();
        vehicleList = new ArrayList<>();
        juncMap = new HashMap<>();
        roadMap = new HashMap<>();
        vehicleMap = new HashMap<>();
    }
	
	void addJunction(Junction j) throws Exception {
		if(!juncMap.containsKey(j.toString())) {
			juncList.add(j);
			juncMap.put(j.toString(), j);
		}
		else {
			throw new Exception("Juncion already added.");
		}
	}
	
	void addRoad(Road r) throws Exception {
		if(!roadMap.containsKey(r.toString())) {
			roadList.add(r);
			roadMap.put(r.toString(), r);

			this.juncMap.get(r.getDest().toString()).addIncommingRoad(r);
			this.juncMap.get(r.getSrc().toString()).addOutGoingRoad(r);
		}
		else {
			throw new Exception("Road already added.");
		}
	}
	
	void addVehicle(Vehicle v) throws Exception {
		if(!vehicleMap.containsKey(v.toString())) {
			vehicleList.add(v);
			vehicleMap.put(v.toString(), v);
		}
		else {
			throw new Exception("Road already added.");
		}
	}
	
	public Junction getJunction(String id) {
		return this.juncMap.get(id);
	}

	public Road getRoad(String id) {
		return this.roadMap.get(id);
	}

	public Vehicle getVehicle(String id) {
		return this.vehicleMap.get(id);
	}

	public List<Junction> getJunctions() {
		return Collections.unmodifiableList(juncList);
	}

	public List<Road> getRoads() {
		return Collections.unmodifiableList(roadList);
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicleList);
	}
	
	void reset() {
		juncList = new ArrayList<>();
        roadList = new ArrayList<>();
        vehicleList = new ArrayList<>();
        juncMap = new HashMap<>();
        roadMap = new HashMap<>();
        vehicleMap = new HashMap<>();
	}

	public JSONObject report() {
		JSONObject jsonObject = new JSONObject();
		
		List<JSONObject> juncReports = new ArrayList<>();
		List<JSONObject> roadReports = new ArrayList<>();
		List<JSONObject> vehicleReports = new ArrayList<>();
		
		for(Junction j : juncList) {
			juncReports.add(j.report());
		}
		
		for(Road r : roadList) {
			roadReports.add(r.report());
		}
		
		for(Vehicle v : vehicleList) {
			vehicleReports.add(v.report());
		}
		
		jsonObject.put("junctions", juncReports);
		jsonObject.put("roads", roadReports);
		jsonObject.put("vehicles", vehicleReports);
		
		
		return jsonObject;
	}
}
