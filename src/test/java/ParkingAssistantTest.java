import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;


public class ParkingAssistantTest {

    @Test
    public void testMoveForward() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        ParkingStatus result = parkingAssistant.MoveForward();

        assertEquals(1, result.getcurrentPosition(), "The car should have moved forward by 1 meter.");

    }

    @Test  
    public void testMoveForwardAtEndOfStreet() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(500);
        ParkingStatus result = parkingAssistant.MoveForward();

        assertEquals(500, result.getcurrentPosition(), "The car should not move forward beyond the end of the street.");

        
    }

    @Test
    public void testMoveForwardFromMiddleofStreet() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(250);
        ParkingStatus result = parkingAssistant.MoveForward();

        assertEquals(251, result.getcurrentPosition(), "The car should have moved forward by 1 meter from the middle of the street.");
    }

    @Test
    public void testMoveForwardWhenParked() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(100);
        parkingAssistant.Park(); // Park the car
        ParkingStatus result = parkingAssistant.MoveForward();

        assertEquals(100, result.getcurrentPosition(), "The car should not move forward when parked.");
    }

    @Test
    public void testMoveForwardWithFreeMeter() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        parkingAssistant.isEmpty(sensor1, sensor2); // Simulate free meter
        ParkingStatus result = parkingAssistant.MoveForward();

        assertEquals(1, result.getcurrentPosition(), "The car should have moved forward by 1 meter. Free meter");
    }

    @Test
    public void testMoveForwardWithOccupiedMeter() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {10, 15, 20, 25, 30};
        int[] sensor2 = {5, 10, 15, 20, 25};
        parkingAssistant.isEmpty(); // Simulate occupied meter
        ParkingStatus result = parkingAssistant.MoveForward();  

        assertEquals(1, result.getcurrentPosition(), "The car should have moved forward by 1 meter. Occupied meter");
    }
    @Test
    public void testIsEmpty() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        int result = parkingAssistant.isEmpty(sensor1, sensor2);
        assertEquals(127, result, "The average distance to the nearest object should be 127 cm.");
    }

    @Test
    public void testIsNotEmpty() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {10, 15, 20, 25, 30};
        int[] sensor2 = {5, 10, 15, 20, 25};
        int result = parkingAssistant.isEmpty(sensor1, sensor2);
        assertEquals(17, result, "The average distance to the nearest object should be 17 cm.");
    }

    @Test
    public void testIsEmptyWithNoisySensor() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 160};
        int[] sensor2 = {110, 115, 125, 135, 140};
        int result = parkingAssistant.isEmpty(sensor1, sensor2);
        assertEquals(125, result, "The distance to the nearest object should be 125 cm, disregarding the noisy sensor.");
    }
    @Test
    public void TestIsEmptyWithBothNoisySensors() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        int[] sensor1 = {100, 120, 130, 140, 160};
        int[] sensor2 = {110, 115, 125, 135, 170};
        int result = parkingAssistant.isEmpty(sensor1, sensor2);
        assertEquals(0, result, "Both sensors are noisy, so the result should be 0.");
    
        // Test the isEmpty method
        // Add assertions to verify the expected behavior
    }

    @Test
    public void testMoveBackward() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(1);
        ParkingStatus result = parkingAssistant.MoveBackward();

        assertEquals(0, result.getcurrentPosition(), "The car should have moved backward by 1 meter.");
    }

    @Test
    public void testMoveBackwardAtStartOfStreet() {
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

    @Test
    public void testWherelsInitialState() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);

        CarStatus result = parkingAssistant.WhereIs();

        assertEquals(0, result.getPosition(), "The initial position should be 0.");
        assertFalse(result.isParked(), "The car should not be parked initially.");
    }

    @Test 
    public void testWherelsAfterMoving() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
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
