package src.simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {
	protected int timeSlot;
	
	RoundRobinStrategy(int timeSlot) {
		if(timeSlot<0) throw new IllegalArgumentException("the timeSlot must be a positive number.");
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if(roads.size()==0) return -1;
		if(currGreen==-1) return 0;
		if(currTime-lastSwitchingTime < timeSlot) return currGreen;
		return (currGreen+1)%roads.size();
	}

}
