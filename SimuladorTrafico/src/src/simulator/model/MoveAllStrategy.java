package src.simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> result = new ArrayList<>();
		for(Vehicle v : q) {
			result.add(v);
		}
		return result;
	}

}
