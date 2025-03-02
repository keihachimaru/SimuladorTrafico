package src.simulator.model;

import java.util.List;

public interface DequeuingStrategy {
	List<Vehicle> dequeue(List<Vehicle> q);
}
