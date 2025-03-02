package src.simulator.model;

public class InterCityRoad extends Road {

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
		int x;
		switch(this.weather) {
			case SUNNY:
				x = 2;
			case CLOUDY:
				x = 3;
			case RAINY:
				x = 10;
			case WINDY:
				x = 15;
			case STORM:
				x = 20;
			default:
				x = 0;
		}
		int newCont = ((100-x)*this.totalCont)/100;
		this.totalCont = newCont;
	}

	@Override
	void updateSpeedLimit() {
		if (this.totalCont>this.contLimit) {
			this.currSpeedLim = this.maxSpeedLim/2;
		}
		else {
			this.currSpeedLim = this.maxSpeedLim;
		}
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int newSpeed = this.currSpeedLim;
		if(this.weather==Weather.STORM) newSpeed = (newSpeed*8)/10;
		return newSpeed;
	}

}
