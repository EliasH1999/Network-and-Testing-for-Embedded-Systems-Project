import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


public class ParkingAssistantTest {

    @Test
    public void testMoveForward() {
        ParkingAssistant parkingAssistant = new ParkingAssistant();
        MovementDataStructure result = parkingAssistant.MoveForward();

        assertEquals(1, result.getcurrentPosition(), "The car should have moved forward by 1 meter.");

    }

    @Test  
    public void testMoveForwardAtEndOfStreet() {
        ParkingAssistant parkingAssistant = new ParkingAssistant();
    
    }
    @Test
    public void testIsEmpty() {
        // Test the isEmpty method
        // Add assertions to verify the expected behavior
    }

    @Test
    public void testMoveBackward() {
        // Test the MoveBackward method
        // Add assertions to verify the expected behavior
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
