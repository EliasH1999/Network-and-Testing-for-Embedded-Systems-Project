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
        // Test the isEmpty method
        // Add assertions to verify the expected behavior
    }

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

        ParkingAssistant assistant = new ParkingAssistant();

        ParkingAssistant.CarStatus status = assistant.Wherels();

        assertTrue(status.getPosition() >= 0 && status.getPosition() <= 500);

    }
}
