package src.simulator.model;

import java.util.List;

import src.simulator.misc.Pair;


public class SetContClassEvent extends Event {
	List<Pair<String,Integer>> cs;
	
	public SetContClassEvent(int time, List<Pair<String,Integer>> cs) {
		super(time);
		if(cs==null) throw new IllegalArgumentException("the cs cant be null.");
		this.cs = cs;
	}

	@Override
	void execute(RoadMap map) throws Exception {
		for(Pair<String, Integer> p : cs) {
			Vehicle target = map.getVehicle(p.getFirst());
			if(target==null) throw new Exception("the vehicle does not exist.");
			target.setContaminationClass(p.getSecond());
		}
	}

}
