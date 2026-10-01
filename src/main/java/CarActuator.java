public class CarActuator implements Actuator {
    private int position;

    public CarActuator(int position) {
        if (position < 0 || position > 500) {
            throw new IllegalArgumentException("Position must be between 0 and 500");
        }
        this.position = position;
    }

    @Override
    public void moveforward() {
        if (position < 500) {
            position++;
        }
    }

    @Override
    public void movebackward() {
        if (position > 0) {
            position--;
        }
    }

    @Override
    public int getPosition() {
        return position;
    }
}
