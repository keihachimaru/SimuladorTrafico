package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		List<Vehicle> result = new ArrayList<>();
		if(q.size()>0) result.add(q.get(0));
		return result;
	}

}
