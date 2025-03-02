package src.simulator.model;

public abstract class NewRoadEvent extends Event {
	protected int time;
    protected String id;
    protected String srcJunc;
    protected String destJunc;
    protected int length;
    protected int co2Limit;
    protected int maxSpeed;
    protected Weather weather;

	
    public NewRoadEvent(int time, String id, String srcJunc, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		this.id = id;
	    this.srcJunc = srcJunc;
	    this.destJunc = destJunc;
	    this.length = length;
	    this.co2Limit = co2Limit;
	    this.maxSpeed = maxSpeed;
	    this.weather = weather;
	}
}
