package src.simulator.factories;

import org.json.JSONObject;

import src.simulator.model.LightSwitchingStrategy;
import src.simulator.model.MostCrowdedStrategy ;

public class MostCrowdedStrategyBuilder  extends Builder<LightSwitchingStrategy> {

	public MostCrowdedStrategyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected LightSwitchingStrategy create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		int timeslot = 1;
		if(data.has("timeslot")) {
			timeslot = data.getInt("timeslot");
		}
		MostCrowdedStrategy strat = new MostCrowdedStrategy(timeslot);
		return strat;
	}

}
