package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {
	public NewVehicleEventBuilder() {
		super("new_vehicle", "new_vehicle");
	}

	@Override
	public Event create_instance(JSONObject data) {
		int time = data.getInt("time");
		String id = data.getString("id");
		int maxSpeed = data.getInt("maxspeed");
		int contClass = data.getInt("class");
		JSONArray itineraryJSON = data.getJSONArray("itinerary");
		List<String> itinerary = new ArrayList<>();
		for(Object v : itineraryJSON) {
			itinerary.add((String) v);
		}
		
		NewVehicleEvent obj = new NewVehicleEvent(time, id, maxSpeed, contClass, itinerary);
		return obj;
	}

}
