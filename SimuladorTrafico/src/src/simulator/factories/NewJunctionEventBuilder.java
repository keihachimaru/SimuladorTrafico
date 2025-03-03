package src.simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import src.simulator.model.DequeuingStrategy;
import src.simulator.model.Event;
import src.simulator.model.LightSwitchingStrategy;
import src.simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {
	Factory<LightSwitchingStrategy> lssFactory;
	Factory<DequeuingStrategy> dqsFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory,
		Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction", "new_junction");
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
	public Event create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		int time = data.getInt("time");
		String id = data.getString("id");
		JSONArray coor = data.getJSONArray("coor");
		int x = (int) coor.get(0);
		int y = (int) coor.get(1);
		
		JSONObject lsStrategyJSON = data.getJSONObject("ls_strategy");
		LightSwitchingStrategy lsStrat = lssFactory.create_instance(lsStrategyJSON);
		
		JSONObject dqStrategyJSON = data.getJSONObject("dq_strategy");
		DequeuingStrategy dqStrat = dqsFactory.create_instance(dqStrategyJSON);
		
		NewJunctionEvent juncEvent = new NewJunctionEvent(time, id, lsStrat, dqStrat, x, y);
		
		return juncEvent;
	}

}
