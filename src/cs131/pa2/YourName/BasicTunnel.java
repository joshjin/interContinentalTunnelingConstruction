package cs131.pa2.YourName;

import java.util.LinkedList;

import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

public class BasicTunnel extends Tunnel{
	// list of vehicles
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
	
	// 10/13/2017
	@Override
	public synchronized boolean tryToEnterInner(Vehicle vehicle) {
		// case of 3 vehicles #no enter
		if (count == 3 && vehicle_list.size() == 3) {
			return false;
		}
		// case of 1 or 2 vehicles # car enter only
		if ((count < 3 && count > 0) 
				&& (vehicle_list.size() < 3 && vehicle_list.size() > 0)){
			Vehicle tmp_vehicle = vehicle_list.peek();
			// case of sled in the list # no enter
			if (tmp_vehicle instanceof Sled) {
				return false;
			} else {
				// case of sled trying to enter # no enter
				if (vehicle instanceof Sled) {
					return false;
				} 
				// case of different directions # no enter
				else if (!(vehicle.getDirection().toString()
						.equals(tmp_vehicle.getDirection().toString()))) {
					return false;
				}
			}
		}
		// all other cases # enter!
		vehicle_list.add(vehicle);
		++count;
		return true;
	}

	// 10/13/2017
	@Override
	public synchronized void exitTunnelInner(Vehicle vehicle) {
		// remove matching vehicle
		removeVehicle(vehicle);
		for (Vehicle tmp_vehicle : vehicle_list) {
			if (tmp_vehicle.equals(vehicle)) {
				vehicle_list.remove(vehicle);
				break;
			}
		}
		--count;
	}
}
