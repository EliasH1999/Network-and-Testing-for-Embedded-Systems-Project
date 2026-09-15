import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testWhereIs() {
    ParkingAssistant assistant = new ParkingAssistant();
    Car testCar = new Car(250, true);

    Car result = assistant.WhereIs(testCar);

    assertEquals(250, result.getPosition());
    assertTrue(result.isParked());
}
}