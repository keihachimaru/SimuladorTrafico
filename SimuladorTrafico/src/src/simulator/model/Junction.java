package src.simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject {
	private List<Road> incommingRoads;
	private List<List<Vehicle>> queueList;
	private Map<Junction,Road> outgoingRoads;
	private int currGreen;
	private int lastSwitchingTime;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoord;
	private int yCoord;
	
	//Convenient attributes
	private Map<Road, List<Vehicle>> roadQueues;
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoord, int yCoord) {
		super(id);
		if(lsStrategy==null) throw new IllegalArgumentException("the lsStrategy cant be null.");
		this.lsStrategy = lsStrategy;
		
		if(dqStrategy==null) throw new IllegalArgumentException("the dqStrategy cant be null.");
		this.dqStrategy = dqStrategy;
		
		if(xCoord<0) throw new IllegalArgumentException("the xCoord cant be negative.");
		this.xCoord = xCoord;
		
		if(yCoord<0) throw new IllegalArgumentException("the yCoord cant be negative.");
		this.yCoord = yCoord;
	}
	
	void addIncommingRoad(Road r) throws Exception {
		if(r.getDest()!=this) throw new Exception("the road does not lead to this Junction.");
		
		this.incommingRoads.add(r);
		List<Vehicle> newQueue = new ArrayList<>();
		this.queueList.add(newQueue);
		
		this.roadQueues.put(r, newQueue);
	}
	
	void addOutGoingRoad(Road r) throws Exception {
		if(r.getSrc()!=this)  throw new Exception("the road does not come out from this Junction.");
		if(this.outgoingRoads.containsKey(r.getDest())) throw new Exception("already a road incoming from same Junction.");
		
		this.outgoingRoads.put(r.getDest(), r);
	}
	
	void enter(Vehicle v) throws Exception {
		int i = this.incommingRoads.indexOf(v.getRoad());
		this.queueList.get(i).add(v);
		
		this.roadQueues.get(v.getRoad()).add(v);
	}
	
	Road roadTo(Junction j) {
		return this.outgoingRoads.get(j);
	}
	
	@Override
	void advance(int currTime) throws Exception {
		Road key = this.incommingRoads.get(currGreen);
		List<Vehicle> dq = this.roadQueues.get(key);
		dq = this.dqStrategy.dequeue(dq);
		
		for(Vehicle v : dq) {
			this.roadQueues.get(key).remove(v);
			this.queueList.get(currGreen).remove(v);
			v.moveToNextRoad();
		}
		
		int newLight = this.lsStrategy.chooseNextGreen(incommingRoads, queueList, currGreen, lastSwitchingTime, currTime);
		if(newLight!=currGreen) {
			this.currGreen = newLight;
			this.lastSwitchingTime = currTime;
		}
	}

	@Override
	public JSONObject report() {
		List<JSONObject> queues = new ArrayList<>();
		for(Road key : this.roadQueues.keySet()) {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("road", key.toString());
			List<String> vehicles = new ArrayList<>();
			for(Vehicle v : this.roadQueues.get(key)) {
				vehicles.add(v.toString());
			}
			jsonObj.put("vehicles", vehicles);
			queues.add(jsonObj);
		}
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", this.toString());
		jsonObj.put("green", this.incommingRoads.get(this.currGreen)).toString();
		jsonObj.put("queues", queues);
		
		return jsonObj;
	}

}
