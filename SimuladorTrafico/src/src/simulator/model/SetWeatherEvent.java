package src.simulator.model;

import java.util.List;

import src.simulator.misc.Pair;


public class SetWeatherEvent extends Event {
	List<Pair<String,Weather>> ws;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		if(ws==null) throw new IllegalArgumentException("the ws cant be null.");
		this.ws = ws;
	}

	@Override
	public void execute(RoadMap map) throws Exception {
		for(Pair<String, Weather> p : ws) {
			Road target = map.getRoad(p.getFirst());
			if(target==null) throw new Exception("the road does not exist.");
			target.setWeather(p.getSecond());
		}
	}

}
