package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;
public class SetContClassEventBuilder extends Builder<Event> {
	public SetContClassEventBuilder() {
		super("set_cont_class", "set_cont_class");
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
