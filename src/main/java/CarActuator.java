public class CarActuator implements Actuator {
    private int position;

    public CarActuator(int position) {
        this.position = position;
    }

    @Override
    public void moveforward() {
        position++;
    }

    @Override
    public void movebackward() {
        position--;
    }

    @Override
    public int getPosition() {
        return position;
    }
}
