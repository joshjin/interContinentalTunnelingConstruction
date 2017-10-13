package cs131.pa2.YourName;

import java.util.LinkedList;

import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

public class BasicTunnel extends Tunnel{
	final LinkedList<Vehicle> vehicle_list = new LinkedList<>();
	int count = 0;
	// constructor
	public BasicTunnel(String name) {
		super(name);
	}
	// constructor
	public BasicTunnel(String name, Log log) {
		super(name, log);
	}
	
	@Override
	public synchronized boolean tryToEnterInner(Vehicle vehicle) {
		if (count == 3 && vehicle_list.size() == 3) {
			return false;
		}
		if ((count < 3 && count > 0) 
				&& (vehicle_list.size() < 3 && vehicle_list.size() > 0)){
			Vehicle tmp_vehicle = vehicle_list.peek();
			if (tmp_vehicle instanceof Sled) {
				return false;
			} else {
				if (vehicle instanceof Sled) {
					return false;
				} else if (!(vehicle.getDirection().toString()
						.equals(tmp_vehicle.getDirection().toString()))) {
					return false;
				}
			}
		}
		vehicle_list.add(vehicle);
		++count;
		return true;
	}

	@Override
	public synchronized void exitTunnelInner(Vehicle vehicle) {
		for (Vehicle tmp_vehicle : vehicle_list) {
			if (tmp_vehicle.equals(vehicle)) {
				vehicle_list.remove(vehicle);
				break;
			}
		}
		--count;
	}
}
