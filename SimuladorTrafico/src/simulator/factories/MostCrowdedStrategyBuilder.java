package simulator.factories;

import org.json.JSONObject;

import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy ;

public class MostCrowdedStrategyBuilder  extends Builder<LightSwitchingStrategy> {
	public MostCrowdedStrategyBuilder() {
		super("most_crowded_lss", "most_crowded_lss");
	}

	@Override
	public LightSwitchingStrategy create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		int timeslot = 1;
		if(data.has("timeslot")) {
			timeslot = data.getInt("timeslot");
		}
		MostCrowdedStrategy strat = new MostCrowdedStrategy(timeslot);
		return strat;
	}

}
