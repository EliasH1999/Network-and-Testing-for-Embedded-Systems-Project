public class CarActuator implements Actuator {
    private int position;

    public CarActuator(int position) {
        if (position < 0 || position > 500) {
            throw new IllegalArgumentException("Position must be between 0 and 500");
        }
        this.position = position;
    }

    @Override
    public boolean moveForward() {
        if (position < 500) {
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
