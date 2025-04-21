package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	protected int timeSlot;
	
public MostCrowdedStrategy(int timeSlot) {
		if(timeSlot<0) throw new IllegalArgumentException("the timeSlot must be a positive number.");
		this.timeSlot = timeSlot;
	}
	
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		if(roads.size()==0) return -1;
		if(currGreen==-1) {
			List<Vehicle> longest = qs.get(0);
			int index = 0;
			for(int i=0; i<qs.size(); i++) {
				if(qs.get(i).size()>longest.size()) {
					longest = qs.get(i);
					index = i;
				}
			}
			
			return index;
		};
		if(currTime-lastSwitchingTime < timeSlot) return currGreen;
		else {
			int nextIndex = (currGreen + 1) % qs.size();
			List<Vehicle> longest = qs.get(nextIndex);
			int index = nextIndex;

			for (int i = 1; i < qs.size(); i++) {
				int j = (currGreen + 1 + i) % qs.size();
				if (qs.get(j).size() > longest.size()) {
					longest = qs.get(j);
					index = j;
				}
			}
			return index;
		}
	}

	@Override
	public String toString() {
		return "mostcrowded";
	}
}
