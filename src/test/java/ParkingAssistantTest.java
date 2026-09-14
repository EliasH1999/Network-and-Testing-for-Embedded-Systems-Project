import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class ParkingAssistantTest {

    @Test
    public void testMoveForward() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        MovementDataStructure result = parkingAssistant.MoveForward();

        assertEquals(1, result.getcurrentPosition(), "The car should have moved forward by 1 meter.");

    }

    @Test  
    public void testMoveForwardAtEndOfStreet() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(500);
        MovementDataStructure result = parkingAssistant.MoveForward();

        assertEquals(500, result.getcurrentPosition(), "The car should not move forward beyond the end of the street.");

        
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
        int[] sensor1 = {100, 120, 130, 140, 150}; // Noisy sensor
        int[] sensor2 = {110, 115, 125, 135, 140};
        int result = parkingAssistant.isEmpty(sensor1, sensor2);
        assertEquals(125, result, "The distance to the nearest object should be 125 cm, disregarding the noisy sensor.");
        



    
        // Test the isEmpty method
        // Add assertions to verify the expected behavior
    

    @Test
    public void testMoveBackward() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(1);
        MovementDataStructure result = parkingAssistant.MoveBackward();

        assertEquals(0, result.getcurrentPosition(), "The car should have moved backward by 1 meter.");
    }

    @Test
    public void testMoveBackwardAtStartOfStreet() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(0);
        MovementDataStructure result = parkingAssistant.MoveBackward();

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

    @Test
    public void testWherels() {
        // Test the Wherels method
        // Add assertions to verify the expected behavior
    }
}
