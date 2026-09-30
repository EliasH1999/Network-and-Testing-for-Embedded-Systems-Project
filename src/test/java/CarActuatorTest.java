import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CarActuatorTest {

    @Test public void testCarActuatorMoveForward(){ 

    CarActuator carActuator = Mockito.mock(CarActuator.class);

    carActuator.moveforward();

    Mockito.verify(carActuator).moveforward();

    Actuator actuatorMock = Mockito.mock(Actuator.class);
    }
@Test public void testCarActuatorGetPosition() {
    Actuator actuatorMock = Mockito.mock(Actuator.class);
    Mockito.when(actuatorMock.getPosition())
       .thenReturn(0)
       .thenReturn(1);  

    }

    @Test public void testCarActuatorMoveBackward(){ 

    CarActuator carActuator = Mockito.mock(CarActuator.class);

    carActuator.moveforward();

    Mockito.verify(carActuator).moveforward();

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
    @Test
public void debugBrokenSensors() {

    CarActuator actuator = new CarActuator(249);
    Sensor sensor1 = new MockSensor(true);
    Sensor sensor2 = new MockSensor(true);

    ParkingAssistant parkingAssistant =
        new ParkingAssistant(sensor1, sensor2, actuator);

    System.out.println("Position 249:");
    System.out.println("Sensor1: " + sensor1.getReading(249));
    System.out.println("Sensor2: " + sensor2.getReading(249));
    System.out.println("isEmpty: " + parkingAssistant.isEmpty());

    actuator.moveforward(); // 250

    System.out.println("Position 250:");
    System.out.println("Sensor1: " + sensor1.getReading(250));
    System.out.println("Sensor2: " + sensor2.getReading(250));
    System.out.println("isEmpty: " + parkingAssistant.isEmpty());
}
    }


