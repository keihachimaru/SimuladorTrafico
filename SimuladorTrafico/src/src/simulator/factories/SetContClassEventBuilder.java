package src.simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import src.simulator.misc.Pair;
import src.simulator.model.Event;
import src.simulator.model.SetContClassEvent;
import src.simulator.model.SetWeatherEvent;
import src.simulator.model.Weather;

public class SetContClassEventBuilder extends Builder<Event> {

	public SetContClassEventBuilder(String typeTag, String desc) {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		JSONArray info = data.getJSONArray("info");
		List<Pair<String, Integer>> cs = new ArrayList<>();
		
		for (int i=0; i<info.length(); i++) {
	        JSONObject o = info.getJSONObject(i);
	        Pair<String, Integer> p = new Pair<>(o.getString("vehicle"), o.getInt("class"));
	        cs.add(p);
	    }
		
		SetContClassEvent obj = new SetContClassEvent(time, cs);
		return obj;
	}

}
