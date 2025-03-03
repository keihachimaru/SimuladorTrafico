package src.simulator.factories;

import org.json.JSONObject;

import src.simulator.model.DequeuingStrategy;
import src.simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {
	public MoveFirstStrategyBuilder() {
		super("move_first_dqs", "move_first_dqs");
	}

	@Override
	public DequeuingStrategy create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		MoveFirstStrategy strat = new MoveFirstStrategy();
		return strat;
	}

}
