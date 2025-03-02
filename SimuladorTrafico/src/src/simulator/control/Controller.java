package src.simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import src.simulator.factories.*;
import src.simulator.model.*;

public class Controller {
	protected TrafficSimulator simulator;
	protected Factory<Event> eventsFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if(sim==null || eventsFactory==null) throw new IllegalArgumentException("invalid arguments.");
		this.simulator = sim;
		this.eventsFactory = eventsFactory;
	}
	
	public void  loadEvents(InputStream in) {
		JSONObject jo = new JSONObject(new JSONTokener(in));
		JSONArray events = jo.getJSONArray("events");
		if(events==null) throw new IllegalArgumentException("invalid JSON.");
		
		for(int i=0; i<events.length(); i++) {
			JSONObject o = events.getJSONObject(i);
			Event event = eventsFactory.create_instance(o);
			simulator.addEvent(event);
		}
	}
	
	public void run(int n, OutputStream  out) throws Exception {
		List<JSONObject> states = new ArrayList<>();
		
		for(int i=0; i<n; i++) {
			this.simulator.advance();
			states.add(this.simulator.report());
		}
		
		PrintStream p = new PrintStream(out);
		
		JSONObject output = new JSONObject();
		output.put("states", states);
		String str = output.toString(3);
		
		p.print(str);
	}
	
	public void reset() {
		this.simulator.reset();
	}
}
