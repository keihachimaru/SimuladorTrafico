package src.simulator.factories;

import org.json.JSONObject;

import src.simulator.model.DequeuingStrategy;
import src.simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {

	public MoveFirstStrategyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected DequeuingStrategy create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		MoveFirstStrategy strat = new MoveFirstStrategy();
		return strat;
	}

}
