package cs131.pa2.YourName;

import cs131.pa2.Abstract.Direction;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

public class Ambulance extends Vehicle{

	public Ambulance (String name, Direction direction,
            int priority, Log log) {
		super(name, direction, 4,log);
	}

	@Override
	protected int getDefaultSpeed() {
		return 9;
	}
}
