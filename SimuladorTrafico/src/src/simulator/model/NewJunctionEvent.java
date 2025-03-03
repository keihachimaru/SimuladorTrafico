package src.simulator.model;

public class NewJunctionEvent extends Event {
	protected String id;
	protected LightSwitchingStrategy lsStrategy;
	protected DequeuingStrategy dqStrategy;
	protected int xCoord;
	protected int yCoord;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoord, int yCoord) {
		super(time);
		this.id = id;
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	@Override
	public void execute(RoadMap map) throws Exception {
		Junction newJunction = new Junction(id, lsStrategy, dqStrategy, xCoord, xCoord);
		map.addJunction(newJunction);
	}

}
