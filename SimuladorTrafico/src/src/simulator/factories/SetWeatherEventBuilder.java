package src.simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import src.simulator.misc.Pair;
import src.simulator.model.Event;
import src.simulator.model.SetWeatherEvent;
import src.simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {
	public SetWeatherEventBuilder() {
		super("set_weather", "set_weather");
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		JSONArray info = data.getJSONArray("info");
		List<Pair<String, Weather>> ws = new ArrayList<>();
		
		for (int i=0; i<info.length(); i++) {
	        JSONObject o = info.getJSONObject(i);
	        Pair<String, Weather> p = new Pair<>(o.getString("road"), Weather.valueOf(o.getString("weather")));
	        ws.add(p);
	    }
		
		SetWeatherEvent obj = new SetWeatherEvent(time, ws);
		return obj;
	}

}
