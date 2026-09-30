import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CarActuatorTest {

 @Test 
    public void testStartPosition(){
        CarActuator carActuator = new CarActuator(42);
        assertEquals(42, carActuator.getPosition(), "The initial position should be 250.");
    }

    @Test
    public void testMoveForwardFromMiddle(){
        CarActuator carActuator = new CarActuator(250);
        carActuator.moveforward();
        assertTrue(true, "The move should be accepted");
        assertEquals(251, carActuator.getPosition(), "The position should be 43 after moving forward once.");

    }

    @Test
    public void testMoveForwardFromEnd(){
        CarActuator carActuator = new CarActuator(500);
        carActuator.moveforward();
        assertFalse(false, "The move should be rejected");
        assertEquals(500, carActuator.getPosition(), "The position should remain 500 after a rejected move.");
    }

    @Test 
    public void testMoveBackwardFromMiddle(){
        CarActuator carActuator = new CarActuator(250);
        carActuator.movebackward();
        assertTrue(true, "The move should be accepted");
        assertEquals(249, carActuator.getPosition(), "The position should be 249 after moving backward once.");
    }

    @Test 
    public void testMoveBackwardFromStart(){
        CarActuator carActuator = new CarActuator(0);
        carActuator.movebackward();
        assertFalse(false, "The move should be rejected");
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
        carActuator.moveforward();
        assertTrue(true, "The move should be accepted");
        assertEquals(500, carActuator.getPosition(), "The position should be 500 after moving forward from 499.");
    }

    @Test   
    public void TestScenarioOne(){
        CarActuator actuator = new CarActuator(0);
        Sensor sensor1 = new MockSensor(false);
        Sensor sensor2 = new MockSensor(false);
        ParkingAssistant parkingAssistant = new ParkingAssistant(sensor1, sensor2, actuator);

        assertEquals(0, parkingAssistant.WhereIs().getPosition()); 
        assertEquals(false, parkingAssistant.WhereIs().isParked());

        parkingAssistant.Park();
        assertTrue(parkingAssistant.WhereIs().isParked());
        
        parkingAssistant.Unpark();
        assertFalse(parkingAssistant.WhereIs().isParked());
        assertEquals(254, parkingAssistant.WhereIs().getPosition());


        actuator.moveforward();
        assertEquals(255, parkingAssistant.WhereIs().getPosition());
        parkingAssistant.Park();
        assertTrue(parkingAssistant.WhereIs().isParked());

        parkingAssistant.Unpark();
        assertEquals(454, parkingAssistant.WhereIs().getPosition());

        actuator.moveforward();

        parkingAssistant.Park();
        assertEquals(500, parkingAssistant.WhereIs().getPosition());

    }
    @Test
    public void TestScenarioTwo(){
        CarActuator actuator = new CarActuator(0);
        Sensor sensor1 = new MockSensor(false);
        Sensor sensor2 = new MockSensor(false);
        ParkingAssistant parkingAssistant = new ParkingAssistant(sensor1, sensor2, actuator);

        assertEquals(0, parkingAssistant.WhereIs().getPosition()); 
        assertEquals(false, parkingAssistant.WhereIs().isParked());

        parkingAssistant.Park();
        assertTrue(parkingAssistant.WhereIs().isParked());
        
        parkingAssistant.Unpark();
        assertFalse(parkingAssistant.WhereIs().isParked());
        assertEquals(254, parkingAssistant.WhereIs().getPosition());


        actuator.moveforward();
        assertEquals(255, parkingAssistant.WhereIs().getPosition());
        parkingAssistant.Park();
        assertTrue(parkingAssistant.WhereIs().isParked());

        parkingAssistant.Unpark();
        assertEquals(454, parkingAssistant.WhereIs().getPosition());

        actuator.moveforward();

        parkingAssistant.Park();
        assertEquals(500, parkingAssistant.WhereIs().getPosition());

        for(int i = 0; i < 61; i++){
            actuator.movebackward();
        }
        assertEquals(439, parkingAssistant.WhereIs().getPosition());

        parkingAssistant.Park();
        assertTrue(parkingAssistant.WhereIs().isParked());
    }
        @Test   
    public void TestScenarioThree(){
        CarActuator actuator = new CarActuator(0);
        Sensor sensor1 = new MockSensor(true);
        Sensor sensor2 = new MockSensor(true);
        ParkingAssistant parkingAssistant = new ParkingAssistant(sensor1, sensor2, actuator);

        assertEquals(0, parkingAssistant.WhereIs().getPosition()); 
        assertEquals(false, parkingAssistant.WhereIs().isParked());

        parkingAssistant.Park();
        assertFalse(parkingAssistant.WhereIs().isParked());
        assertEquals(500, parkingAssistant.WhereIs().getPosition());

    }
}

