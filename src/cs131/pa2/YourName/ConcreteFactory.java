package cs131.pa2.YourName;

import java.util.Collection;

import cs131.pa2.Abstract.Direction;
import cs131.pa2.Abstract.Factory;
import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

public class ConcreteFactory implements Factory {

    @Override
    public Tunnel createNewBasicTunnel(String label){
    	return new BasicTunnel(label);	
    	//throw new UnsupportedOperationException("Not supported yet.");    
    }

    @Override
    public Vehicle createNewCar(String label, Direction direction){
    	return new Car(label, direction);
    	//throw new UnsupportedOperationException("Not supported yet.");    
    }

    @Override
    public Vehicle createNewSled(String label, Direction direction){
    	return new Sled(label, direction);
    	//throw new UnsupportedOperationException("Not supported yet.");    
    }

    @Override
    public Tunnel createNewPriorityScheduler(String label, Collection<Tunnel> tunnels, Log log){
    		throw new UnsupportedOperationException("Not supported yet.");
    }
}
