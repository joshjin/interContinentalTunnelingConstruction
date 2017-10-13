package cs131.pa2.YourName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

public class PriorityScheduler extends Tunnel{

	public PriorityScheduler(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean tryToEnterInner(Vehicle vehicle) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void exitTunnelInner(Vehicle vehicle) {
		// TODO Auto-generated method stub
		
	}
	
}
