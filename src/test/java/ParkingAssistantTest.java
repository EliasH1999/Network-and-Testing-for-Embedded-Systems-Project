import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class ParkingAssistantTest {

    @Test
    public void testMoveForward() {
        // Test the MoveForward method
        // Add assertions to verify the expected behavior

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

        ParkingAssistant assistant = new ParkingAssistant();

        ParkingAssistant.CarStatus status = assistant.Wherels();

        assertTrue(status.getPosition() >= 0 && status.getPosition() <= 500);

    }
}