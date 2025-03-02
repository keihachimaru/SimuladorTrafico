package src.simulator.model;

import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONObject;

public class TrafficSimulator {
	RoadMap roadmap;
	Queue<Event> events;
	int time;
	
	TrafficSimulator() {
		this.time = 0;
		this.roadmap = new RoadMap();
		this.events = new PriorityQueue<Event>();
	}
	
	public void addEvent(Event e) {
		Queue<Event> updated = new PriorityQueue<Event>();
		Boolean added = false;
		for(Event event : events) {
			int comparison = e.compareTo(event);
			if (comparison < 0 && !added) {
				updated.add(e);
			}
			updated.add(event);
		}
		if (!added) {
	        updated.add(e);
	    }
		events = updated;
	}
	
	public void advance() throws Exception {
		this.time++;
		
		Queue<Event> updated = new PriorityQueue<Event>();
		for(Event event : events) {
			if(event.getTime()==this.time) {
				event.execute(roadmap);
			}
			else {
				updated.add(event);
			}
		}
		this.events = updated;
		
		for(Junction j : roadmap.getJunctions()) {
			j.advance(time);
		}
		
		for(Road r : roadmap.getRoads()) {
			r.advance(time);
		}
	}
	
	public void reset() {
		this.roadmap.reset();
		this.events = new PriorityQueue<Event>();
		this.time = 0;
	}
	
	public JSONObject report() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("time", this.time);
		jsonObject.put("state", this.roadmap.report());
		
		return jsonObject;
	}
}
