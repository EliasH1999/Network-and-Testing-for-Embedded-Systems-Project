public class CarActuator implements Actuator {
    public static final int STREET_LENGTH = 500;
    private int position;

    public CarActuator(int position) {
        if (position < 0 || position > STREET_LENGTH) {              
            throw new IllegalArgumentException("Start position must be in 0.." + STREET_LENGTH);
        }
        this.position = position;
    }

    @Override
    public boolean moveForward() {
        if (position < STREET_LENGTH) {
            position++;
            return true;
        }
        return false;
    }

    @Override
    public boolean moveBackward() {
        if (position > 0){
            position--;
            return true;
        }
        return false;
    }

    @Override
    public int getPosition() {
        return position;
    }
}
