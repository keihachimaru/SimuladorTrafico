package src.simulator.factories;

import org.json.JSONObject;

import src.simulator.model.Event;
import src.simulator.model.NewCityRoadEvent;
import src.simulator.model.Weather;

public class NewCityRoadEventBuilder extends Builder<Event> {
	public NewCityRoadEventBuilder() {
		super("new_city_road", "new_city_road");
	}

	@Override
	protected Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String src = data.getString("src");
		String dest = data.getString("dest");
		int length = data.getInt("length");
		int co2limit = data.getInt("co2limit");
		int maxspeed = data.getInt("maxspeed");
		Weather weather = Weather.valueOf(data.getString("weather"));
		
		NewCityRoadEvent obj = new NewCityRoadEvent(time, id, src, dest, length, co2limit, maxspeed, weather);
		return obj;
	}

}
