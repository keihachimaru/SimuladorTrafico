package simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import org.json.JSONObject;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	RoadMap roadmap;
	Queue<Event> events;
	int time;
	List<TrafficSimObserver> observers;
	
	public TrafficSimulator() {
		this.time = 0;
		this.roadmap = new RoadMap();
		this.events = new PriorityQueue<Event>();
		this.observers = new ArrayList<>();
	}
	
	public void addEvent(Event e) {
		Queue<Event> updated = new PriorityQueue<Event>();
		Boolean added = false;
		for(Event event : this.events) {
			int comparison = e.compareTo(event);
			if (comparison < 0 && !added) {
				updated.add(e);
				added = true;
			}
			updated.add(event);
		}
		if (!added) {
	        updated.add(e);
	    }
		this.events = updated;
		for(TrafficSimObserver observer : this.observers) {
			observer.onEventAdded(roadmap, this.events, e, time);
		}
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
		
		for(TrafficSimObserver observer : this.observers) {
			observer.onAdvance(roadmap, events, time);
		}
	}
	
	public RoadMap getRoadMap() {
		return this.roadmap;
	}
	
	
	public void reset() {
		this.roadmap.reset();
		this.events = new PriorityQueue<Event>();
		this.time = 0;
		
		for(TrafficSimObserver observer : this.observers) {
			observer.onReset(roadmap, events, time);
		}
	}
	
	public JSONObject report() {
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("time", this.time);
		jsonObject.put("state", this.roadmap.report());
		
		return jsonObject;
	}
	
	@Override
	public void addObserver(TrafficSimObserver o) {
		for(TrafficSimObserver observer : this.observers) {
			observer.onRegister(roadmap, events, time);
		}
		this.observers.add(o);
		
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		this.observers.remove(o);
		
	}
}
