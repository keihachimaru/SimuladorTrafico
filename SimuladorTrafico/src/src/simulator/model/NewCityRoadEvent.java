package src.simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {
	public NewCityRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit,
			int maxSpeed, Weather weather) {
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) throws Exception {
		Junction srcJuncObj = map.getJunction(srcJunc);
		Junction destJuncObj = map.getJunction(destJunc);
		
		Road newRoad = new CityRoad(id, srcJuncObj, destJuncObj, maxSpeed, co2Limit, length, weather);
		map.addRoad(newRoad);
	}

}
