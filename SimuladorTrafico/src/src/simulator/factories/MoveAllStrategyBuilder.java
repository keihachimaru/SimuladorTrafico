package src.simulator.factories;

import org.json.JSONObject;

import src.simulator.model.DequeuingStrategy;
import src.simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {

	public MoveAllStrategyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected DequeuingStrategy create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		MoveAllStrategy strat = new MoveAllStrategy();
		return strat;
	}

}
