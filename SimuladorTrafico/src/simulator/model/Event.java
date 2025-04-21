package simulator.model;

public abstract class Event implements Comparable<Event> {

  private static long _counter = 0;

  protected int _time;
  protected long _time_stamp;

  Event(int time) {
    if ( time < 1 )
      throw new IllegalArgumentException("Invalid time: "+time);
    else {
      _time = time;
      _time_stamp = _counter++;
    }
  }

  public int getTime() {
    return _time;
  }

  @Override
  public int compareTo(Event o) {
	  if(o._time==this._time) {
		  return (int) (this._time_stamp-o._time_stamp); 
	  }
	  else {
		  return this._time-o._time;
	  }
  }

  public abstract void execute(RoadMap map) throws Exception;
}