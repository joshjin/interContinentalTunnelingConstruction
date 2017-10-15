package cs131.pa2.YourName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;



public class PriorityScheduler extends Tunnel {
	//To determine if a tunnel is occupied
    private final Map<Tunnel, Boolean> mapUseFlag = new HashMap<Tunnel, Boolean>();
    //To keep track of which tunnel a vehicle is in, such that the means
    //is internal to this file.
    private final Map<Vehicle, Tunnel> tunnelUsed = new HashMap<Vehicle, Tunnel>();
    private final Lock lock = new ReentrantLock();
    //Specified capacity of one does not interfere with the Queue size, it will grow as needed
    private final PriorityQueue<Vehicle> waitingQueue = new PriorityQueue<Vehicle>(1, priorityComparator);
    //A waiting list for vehicles not yet at the front of the queue
    private final Condition notcurrent = lock.newCondition();
    //For when a vehicle is at the front but all tunnels are 
    //occupied or something stranger is wrong
    private final Condition currentwaiting = lock.newCondition();
    private Collection<Tunnel> tunnels;
    
    public PriorityScheduler(String name, Collection<Tunnel> tunnels, Log log) {
        super(name, log);
        Iterator<Tunnel> iter = tunnels.iterator();
        this.tunnels = tunnels;
        //Initialize all tunnels as available
        while (iter.hasNext()) {
            this.addTunnel(iter.next());
        }
    }
    
    public void addTunnel(Tunnel tunnel) {
        this.mapUseFlag.put(tunnel, false);
    }
    
    public PriorityScheduler(String name) {
        super(name);
    }
    
    @Override
    public boolean tryToEnterInner(Vehicle vehicle){
    	lock.lock();
        waitingQueue.add(vehicle);
        //wait while not at the head of the queue
        //i.e. while there exists a vehicle with 
        //higher priority
        while (!waitingQueue.peek().equals(vehicle)){
            try {
                notcurrent.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //wait while all the tunnels are in use even if you
        //are at the head of the queue
        while (allInUse()){
            try {
                currentwaiting.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        Tunnel available = firstAvailable();
        //In case something went wrong
        while (available == null) {
        	available = firstAvailable();
        	try {
				currentwaiting.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        //this is more of a precautionary loop
        //and is probably unecessary in
        //any event.  But it has the thread
        //wait while there are any issues entering
        //the chosen available tunnel
        while (!available.tryToEnter(vehicle)){
            try {
            	currentwaiting.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //assign the vehicle to the tunnel
        tunnelUsed.put(vehicle, available);
        //indicate the tunnel is in use
        mapUseFlag.put(available, true);
        waitingQueue.poll();
        //wake up threads waiting to
        //arrive at the head of the queue
        notcurrent.signalAll();
        lock.unlock();
        return true;
    }
    
    
    @Override
    public void exitTunnelInner(Vehicle vehicle){
    	lock.lock();
    	//find which tunnel the vehicle is exiting
        Tunnel tunnel = tunnelUsed.get(vehicle);
        //call exitTunnel on the tunnel
        tunnel.exitTunnel(vehicle);
        //indicate the tunnel is no longer in use
        mapUseFlag.put(tunnel, false);
        //wake up the threads at the head of the queue
        //waiting on the tunnel
        currentwaiting.signalAll();
        lock.unlock();
    }
    
    
    
    //checks if all of the tunnels are currently in use
    private synchronized boolean allInUse() {
    	for (Tunnel tunnel :  tunnels) {
    		if (!mapUseFlag.get(tunnel)) {
    			return false;
    		}
    	}
    	return true;
    }
    
    //find the first available tunnel
    private synchronized Tunnel firstAvailable() {
    	for (Tunnel tunnel : tunnels) {
    		if(!mapUseFlag.get(tunnel)) {
    			return tunnel;
    		}
    	}
    	return null;
    }
    
    //Use to compare vehicle priorities for the queue to sort the vehicles
    public static Comparator<Vehicle> priorityComparator = new Comparator<Vehicle>(){	
		@Override
		public int compare(Vehicle v_1, Vehicle v_2) {
            return (int) (v_1.getPriority() - v_2.getPriority());
        }
	};
}
