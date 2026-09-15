import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ParkingAssistantTest {


    // test cases for the moveForward method
    @Test
    public void testMoveForward() { // TC 1: first move from 0
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        ParkingStatus result = parkingAssistant.MoveForward();

        assertEquals(1, result.getcurrentPosition(), "The car should have moved forward by 1 meter.");

    }

    @Test  
    public void testMoveForwardAtEndOfStreet() { // TC 2: last move from 500
        ParkingAssistant parkingAssistant = new ParkingAssistant(500);
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        ParkingStatus result = parkingAssistant.MoveForward();

        assertEquals(500, result.getcurrentPosition(), "The car should not move forward beyond the end of the street.");

        
    }

    @Test
    public void testMoveForwardFromMiddleofStreet() { // TC 3: normal move from the middle of the street
        ParkingAssistant parkingAssistant = new ParkingAssistant(250);
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        ParkingStatus result = parkingAssistant.MoveForward();

        assertEquals(251, result.getcurrentPosition(), "The car should have moved forward by 1 meter from the middle of the street.");
    }

    // @Test
    // public void testMoveForwardWhenParked() { // TC 4: move when parked, state unchanged
    //     ParkingAssistant parkingAssistant = new ParkingAssistant(100);
    //     int[] sensor1 = {100, 120, 130, 140, 145};
    //     int[] sensor2 = {110, 115, 125, 135, 140};
    //     parkingAssistant.setSensorReadings(sensor1, sensor2);
    //     //parkingAssistant.Park(); // Park the car
    //     ParkingStatus result = parkingAssistant.MoveForward();

    //     assertEquals(100, result.getcurrentPosition(), "The car should not move forward when parked.");
    // }

    @Test
    public void testMoveForwardWithFreeMeter() { // TC 5: free meter (isEmpty() >= 100) recorded as true
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        ParkingStatus result = parkingAssistant.MoveForward();

        assertTrue(result.getparkingPlaces().get(0), "The first parking place should be recorded as free (true).");
        assertEquals(1, result.getcurrentPosition(), "The car should have moved forward by 1 meter. Free meter");
    }

    @Test
    public void testMoveForwardWithOccupiedMeter() { // TC 6: occupied meter (isEmpty() < 100) recorded as false
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {10, 15, 20, 25, 30};
        int[] sensor2 = {5, 10, 15, 20, 25};

        parkingAssistant.setSensorReadings(sensor1, sensor2);
        ParkingStatus result = parkingAssistant.MoveForward();  

        assertFalse(result.getparkingPlaces().get(0), "The first parking place should be recorded as occupied (false).");
        assertEquals(1, result.getcurrentPosition(), "The car should have moved forward by 1 meter. Occupied meter");
    }






    // Test for isEmpty() method
    @Test
    public void testIsEmpty() { // TC1: both sensors are not noisy, return average of medians
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        int result = parkingAssistant.isEmpty();

        assertEquals(127, result, "The average distance to the nearest object should be 127 cm.");
    }

    @Test
    public void testIsNotEmpty() { // TC2: no sensor is noisy, but no free space, return average of medians
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {10, 15, 20, 25, 30};
        int[] sensor2 = {5, 10, 15, 20, 25};
        
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        int result = parkingAssistant.isEmpty();

        assertEquals(17, result, "The average distance to the nearest object should be 17 cm.");
    }

    @Test
    public void testIsEmptyWithNoisySensor1() { // TC3: sensor 1 is noisy, return median of the other sensor
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 160};
        int[] sensor2 = {110, 115, 125, 135, 140};
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        int result = parkingAssistant.isEmpty();
        assertEquals(125, result, "The distance to the nearest object should be 125 cm, disregarding the noisy sensor.");
    }

    @Test
    public void TestIsEmptyWithBothNoisySensors() { // TC4: both sensors are noisy, return 0
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 160};
        int[] sensor2 = {110, 115, 125, 135, 170};
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        int result = parkingAssistant.isEmpty();
        assertEquals(0, result, "Both sensors are noisy, so the result should be 0.");
    }

    @Test
    public void testIsEmptyWithNoisySensor2() { // TC5: sensor 2 is noisy, return median of the other sensor
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 160};
        int[] sensor2 = {110, 115, 125, 135, 140};
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        int result = parkingAssistant.isEmpty();
        assertEquals(125, result, "The distance to the nearest object should be 125 cm, disregarding the noisy sensor.");
    }

    @Test
    public void testIsEmptyWithSpreadExactly50() { // TC6: spread exactly 50, not noisy
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 150}; // Spread is 50
        int[] sensor2 = {110, 115, 125, 135, 140}; // Spread is 30
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        int result = parkingAssistant.isEmpty();
        assertEquals(127, result, "The average distance to the nearest object should be 127 cm.");
    }
    

    // Test cases for MoveBackward method

     @Test
    public void testMoveBackwardFromMiddleofStreet() { // TC 1: normal move from the middle of the street
        ParkingAssistant parkingAssistant = new ParkingAssistant(250);
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        ParkingStatus result = parkingAssistant.MoveBackward();

        assertEquals(249, result.getcurrentPosition(), "The car should have moved backward by 1 meter from the middle of the street.");
    }

     @Test
    public void testMoveBackwardAtStartOfStreet() { // TC 2: first move from 0
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        ParkingStatus result = parkingAssistant.MoveBackward();

        assertEquals(0, result.getcurrentPosition(), "The car should not move backward beyond the start of the street.");
    }



    

    @Test
    public void testPark() {
        // Test the Park method
        // Add assertions to verify the expected behavior
        
    }

    @Test
    public void testUnpark() {
        // Test the Unpark method
        // Add assertions to verify the expected behavior
    }

        // Test the Wherels method
        // Add assertions to verify the expected behavior

    
    // test for WhereIs() method
    @Test
    public void testWherelsInitialState() { // TC1: initial state (0, false)
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);

        CarStatus result = parkingAssistant.WhereIs();

        assertEquals(0, result.getPosition(), "The initial position should be 0.");
        assertFalse(result.isParked(), "The car should not be parked initially.");
    }

    @Test 
    public void testWherelsAfterMoving() { //TC2: after moving forward twice (2, false)
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        parkingAssistant.setSensorReadings(sensor1, sensor2);
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();

        CarStatus result = parkingAssistant.WhereIs();

        assertEquals(2, result.getPosition(), "The position should be 2 after moving forward twice.");
        assertFalse(result.isParked(), "The car should not be parked after moving.");
    }

    @Test
    public void testWhereIsafterPark(){}

    @Test 
    public void testWhereIsafterUnpark(){}
}
