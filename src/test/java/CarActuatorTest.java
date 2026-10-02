import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CarActuatorTest {

 @Test 
    public void testStartPosition(){
        CarActuator carActuator = new CarActuator(42);
        assertEquals(42, carActuator.getPosition(), "The initial position should be 42.");
    }

    @Test
    public void testMoveForwardFromMiddle(){
        CarActuator carActuator = new CarActuator(250);
        ParkingAssistant parkingAssistant = new ParkingAssistant(new MockSensor(false), new MockSensor(false), carActuator);
        parkingAssistant.MoveForward();
        assertTrue(true, "The move should be accepted");
        assertEquals(251, carActuator.getPosition(), "The position should be 251 after moving forward once.");

    }

    @Test
    public void testMoveForwardFromEnd(){
        CarActuator carActuator = new CarActuator(500);
        ParkingAssistant parkingAssistant = new ParkingAssistant(new MockSensor(false), new MockSensor(false), carActuator);
        parkingAssistant.MoveForward();
        assertFalse(false, "The move should be rejected");
        assertEquals(500, carActuator.getPosition(), "The position should remain 500 after a rejected move.");
    }

    @Test 
    public void testMoveBackwardFromMiddle(){
        CarActuator carActuator = new CarActuator(250);
        ParkingAssistant parkingAssistant = new ParkingAssistant(new MockSensor(false), new MockSensor(false), carActuator);
        parkingAssistant.MoveBackward();
        assertTrue(true, "The move should be accepted");
        assertEquals(249, carActuator.getPosition(), "The position should be 249 after moving backward once.");
    }

    @Test 
    public void testMoveBackwardFromStart(){
        CarActuator carActuator = new CarActuator(0);
        ParkingAssistant parkingAssistant = new ParkingAssistant(new MockSensor(false), new MockSensor(false), carActuator);
        parkingAssistant.MoveBackward();
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
        ParkingAssistant parkingAssistant = new ParkingAssistant(new MockSensor(false), new MockSensor(false), carActuator);
        parkingAssistant.MoveForward();
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


        parkingAssistant.MoveForward();
        assertEquals(255, parkingAssistant.WhereIs().getPosition());
        parkingAssistant.Park();
        assertTrue(parkingAssistant.WhereIs().isParked());

        parkingAssistant.Unpark();
        assertEquals(454, parkingAssistant.WhereIs().getPosition());

        parkingAssistant.MoveForward();

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


        parkingAssistant.MoveForward();
        assertEquals(255, parkingAssistant.WhereIs().getPosition());
        parkingAssistant.Park();
        assertTrue(parkingAssistant.WhereIs().isParked());

        parkingAssistant.Unpark();
        assertEquals(454, parkingAssistant.WhereIs().getPosition());

        parkingAssistant.MoveForward();

        parkingAssistant.Park();
        assertEquals(500, parkingAssistant.WhereIs().getPosition());

        for(int i = 0; i < 61; i++){
            parkingAssistant.MoveBackward();
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

    @Test
    // This test uses mockito simulate the sensors and test the parking assistant's behavior in a controlled scenario.
    public void TestScenarioFour(){
    
        Sensor sensor1 = mock(Sensor.class);
        Sensor sensor2 = mock(Sensor.class);
    
        CarActuator actuator = new CarActuator(0);

        ParkingAssistant parkingAssistant = new ParkingAssistant(sensor1, sensor2, actuator);
    
        // Normal street: no parking place
        when(sensor1.getReading(anyInt())).thenReturn(30);
        when(sensor2.getReading(anyInt())).thenReturn(30);
    
        // Parking place 1: positions 20-22
        // Only 3 meters -> too small
        when(sensor1.getReading(20)).thenReturn(120);
        when(sensor1.getReading(21)).thenReturn(120);
        when(sensor1.getReading(22)).thenReturn(120);

        when(sensor2.getReading(20)).thenReturn(120);
        when(sensor2.getReading(21)).thenReturn(120);
        when(sensor2.getReading(22)).thenReturn(120);
    
        // Parking place 2: positions 50-54
        // 5 meters -> ok
        when(sensor1.getReading(50)).thenReturn(120);
        when(sensor1.getReading(51)).thenReturn(120);
        when(sensor1.getReading(52)).thenReturn(120);
        when(sensor1.getReading(53)).thenReturn(120);
        when(sensor1.getReading(54)).thenReturn(120);
    
        when(sensor2.getReading(50)).thenReturn(120);
        when(sensor2.getReading(51)).thenReturn(120);
        when(sensor2.getReading(52)).thenReturn(120);
        when(sensor2.getReading(53)).thenReturn(120);
        when(sensor2.getReading(54)).thenReturn(120);
    
        // Start at position 0
        assertEquals(0, parkingAssistant.WhereIs().getPosition());
    
        // Search for a parking place and park
        parkingAssistant.Park();
    
        // The first place was too small.
        // The second place is 5 meters and should be selected.
        assertEquals(54, parkingAssistant.WhereIs().getPosition());
        assertEquals(true, parkingAssistant.WhereIs().isParked());

        }

}

