package src.simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {
	public NewInterCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit,
			int maxSpeed, Weather weather) {
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	public void execute(RoadMap map) throws Exception {
		Junction srcJuncObj = map.getJunction(srcJunc);
		Junction destJuncObj = map.getJunction(destJunc);
		
		Road newRoad = new InterCityRoad(id, srcJuncObj, destJuncObj, maxSpeed, co2Limit, length, weather);
		map.addRoad(newRoad);
	}

}
