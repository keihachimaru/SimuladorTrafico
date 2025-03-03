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
			case Weather.SUNNY:
				x = 2;
				break;
			case Weather.CLOUDY:
				x = 3;
				break;
			case Weather.RAINY:
				x = 10;
				break;
			case Weather.WINDY:
				x = 15;
				break;
			case Weather.STORM:
				x = 20;
				break;
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
