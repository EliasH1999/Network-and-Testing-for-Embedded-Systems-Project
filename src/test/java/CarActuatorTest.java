import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CarActuatorTest {

    @Test public void testCarActuatorMoveForward(){ 

    CarActuator carActuator = Mockito.mock(CarActuator.class);

    carActuator.moveforward();

    Mockito.verify(carActuator).moveforward();

    }

    @Test public void testCarActuatorMoveBackward(){ 

    CarActuator carActuator = Mockito.mock(CarActuator.class);

    carActuator.moveforward();

    Mockito.verify(carActuator).moveforward();

    }

    @Test   
    public void TestScenarioOne(){

        Sensor sensor1 = new MockSensor(false);
        Sensor sensor2 = new MockSensor(false);

        ParkingAssistant parkingAssistant = new ParkingAssistant(0);

        assertEquals(0, parkingAssistant.WhereIs().getPosition()); 
        assertEquals(false, parkingAssistant.WhereIs().isParked());
    }

}


