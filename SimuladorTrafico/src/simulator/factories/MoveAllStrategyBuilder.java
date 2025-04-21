package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {
	public MoveAllStrategyBuilder() {
		super("move_all_dqs", "move_all_dqs");
	}

	@Override
	public DequeuingStrategy create_instance(JSONObject data) {
		// TODO Auto-generated method stub
		MoveAllStrategy strat = new MoveAllStrategy();
		return strat;
	}

}
