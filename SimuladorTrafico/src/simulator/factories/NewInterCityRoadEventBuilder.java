package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event> {
	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road", "new_inter_city_road");
	}

	@Override
	public Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		String src = data.getString("src");
		String dest = data.getString("dest");
		int length = data.getInt("length");
		int co2limit = data.getInt("co2limit");
		int maxspeed = data.getInt("maxspeed");
		Weather weather = Weather.valueOf(data.getString("weather"));
		
		NewInterCityRoadEvent obj = new NewInterCityRoadEvent(time, id, src, dest, length, co2limit, maxspeed, weather);
		return obj;
	}

}
