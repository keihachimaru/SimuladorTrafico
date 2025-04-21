package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {
	public RoundRobinStrategyBuilder() {
		super("round_robin_lss","round_robin_lss");
	}

	@Override
	public LightSwitchingStrategy create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		int timeslot = 1;
		if(data.has("timeslot")) {
			timeslot = data.getInt("timeslot");
		}
		RoundRobinStrategy strat = new RoundRobinStrategy(timeslot);
		return strat;
	}

}
