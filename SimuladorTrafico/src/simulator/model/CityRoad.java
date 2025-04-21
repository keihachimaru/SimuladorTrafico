package simulator.model;

public class CityRoad extends Road {

	public CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws Exception {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		srcJunc.addOutGoingRoad(this);
		destJunc.addIncommingRoad(this);
	}

	@Override
	void reduceTotalContamination() {
		int newCont = this.totalCont;
		if(this.weather==Weather.WINDY || this.weather==Weather.STORM) {
			newCont -= 10; 
		}
		else {
			newCont -= 2;
		}
		this.totalCont = newCont>0?newCont:0;
	}

	@Override
	void updateSpeedLimit() {}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int newSpeed = ((11-v.getContClass())*this.currSpeedLim)/11;
		return newSpeed;
	}

}
