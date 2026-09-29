import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CarActuatorTest {
    @Test public void testCarActuatorMoveForward(){ 

    CarActuator carActuator = Mockito.mock(CarActuator.class);

    carActuator.moveforward();

    Mockito.verify(carActuator).moveforward();

    }
}
