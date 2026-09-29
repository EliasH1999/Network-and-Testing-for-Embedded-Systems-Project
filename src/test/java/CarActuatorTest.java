import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CarActuatorTest {

    @Test 
    public void testStartPosition(){
        CarActuator carActuator = new CarActuator(42);
        assertEquals(42, carActuator.getPosition(), "The initial position should be 250.");
    }

    @Test
    public void testMoveForwardFromMiddle(){
        CarActuator carActuator = new CarActuator(250);
        assertTrue(carActuator.moveForward(), "The move should be accepted");
        assertEquals(251, carActuator.getPosition(), "The position should be 43 after moving forward once.");

    }

    @Test
    public void testMoveForwardFromEnd(){
        CarActuator carActuator = new CarActuator(500);
        assertFalse(carActuator.moveForward(), "The move should be rejected");
        assertEquals(500, carActuator.getPosition(), "The position should remain 500 after a rejected move.");
    }

    @Test 
    public void testMoveBackwardFromMiddle(){
        CarActuator carActuator = new CarActuator(250);
        assertTrue(carActuator.moveBackward(), "The move should be accepted");
        assertEquals(249, carActuator.getPosition(), "The position should be 249 after moving backward once.");
    }

    @Test 
    public void testMoveBackwardFromStart(){
        CarActuator carActuator = new CarActuator(0);
        assertFalse(carActuator.moveBackward(), "The move should be rejected");
        assertEquals(0, carActuator.getPosition(), "The position should remain 0 after a rejected move.");

    }

    @Test 
    public void testStartPositionOutsideBound(){
        assertThrows(IllegalArgumentException.class, () -> new CarActuator(-1));
        assertThrows(IllegalArgumentException.class, () -> new CarActuator(501));

    }

    @Test
    public void TestLastMeterAccepted(){
        CarActuator carActuator = new CarActuator(499);
        assertTrue(carActuator.moveForward(), "The move should be accepted");
        assertEquals(500, carActuator.getPosition(), "The position should be 500 after moving forward from 499.");
    }

}
