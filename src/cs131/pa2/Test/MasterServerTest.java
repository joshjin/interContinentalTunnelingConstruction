package cs131.pa2.Test;

import java.util.ArrayList;
import java.util.Collection;
import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;

import cs131.pa2.Abstract.Direction;
import cs131.pa2.Abstract.Tunnel;
import cs131.pa2.Abstract.Vehicle;
import cs131.pa2.Abstract.Log.Log;

public class MasterServerTest {

    private final String masterServerName = "SCHEDULER";

    @Before
    public void setUp() {
        Tunnel.DEFAULT_LOG.clearLog();
    }

    @BeforeClass
    public static void broadcast() {
        System.out.printf("Running Master Server Tests using %s \n", TestUtilities.factory.getClass().getCanonicalName());
    }

    private Tunnel setupSimplePriorityScheduler(String name) {
        Collection<Tunnel> tunnels = new ArrayList<Tunnel>();
        tunnels.add(TestUtilities.factory.createNewBasicTunnel(name));
        return TestUtilities.factory.createNewPriorityScheduler(masterServerName, tunnels, new Log());
    }

    @Test
    public void Car_Enter() {
        Vehicle car = TestUtilities.factory.createNewCar(TestUtilities.gbNames[0], Direction.random());
        Tunnel tunnel = setupSimplePriorityScheduler(TestUtilities.mrNames[0]);
        TestUtilities.VehicleEnters(car, tunnel);
    }

    @Test
    public void Sled_Enter() {
    		Vehicle sled = TestUtilities.factory.createNewSled(TestUtilities.gbNames[0], Direction.random());
        Tunnel tunnel = setupSimplePriorityScheduler(TestUtilities.mrNames[0]);
        TestUtilities.VehicleEnters(sled, tunnel);
    }
}
