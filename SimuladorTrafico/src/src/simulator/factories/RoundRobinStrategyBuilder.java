package src.simulator.factories;

import org.json.JSONObject;

import src.simulator.model.LightSwitchingStrategy;
import src.simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {
	public RoundRobinStrategyBuilder() {
		super("round_robin_lss","round_robin_lss");
	}

	@Override
	protected LightSwitchingStrategy create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		int timeslot = 1;
		if(data.has("timeslot")) {
			timeslot = data.getInt("timeslot");
		}
		RoundRobinStrategy strat = new RoundRobinStrategy(timeslot);
		return strat;
	}

}
