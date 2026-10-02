public class CarActuator implements Actuator {
    private int position;

     /**
     * CarActuator(int position)
     * Description: Creates the controller of the car engine at a starting position.
     * Pre-condition: none
     * Post-condition: if 0 <= position <= 500, getPosition() == position;
     *                 otherwise IllegalArgumentException is thrown.
     * Test-cases:
     *   TC-CA-1  start at 42 -> getPosition() == 42              (testStartPosition)
     *   TC-CA-6  start at -1 or 501 -> IllegalArgumentException  (testStartPositionOutsideBound)
     */

    public CarActuator(int position) {
        if (position < 0 || position > 500) {
            throw new IllegalArgumentException("Position must be between 0 and 500");
        }
        this.position = position;
    }

    /**
     * moveForward()
     * Description: Moves the car 1 metre forward unless it is at the end of the street.
     * Pre-condition: none
     * Post-condition: if position < 500: position' = position + 1 and returns true;
     *                 otherwise position is unchanged and returns false.
     * Test-cases:
     *   TC-CA-2  forward from 250 -> 251                         (testMoveForwardFromMiddle)
     *   TC-CA-3  forward from 500 -> refused, stays 500          (testMoveForwardFromEnd)
     *   TC-CA-7  forward from 499 -> 500, last metre accepted    (TestLastMeterAccepted)
     */

    @Override
    public boolean moveForward() {
        if (position < 500) {
            position++;
            return true;
        }
        return false;
    }

    /**
     * moveBackward()
     * Description: Moves the car 1 metre backward unless it is at the start of the street.
     * Pre-condition: none
     * Post-condition: if position > 0: position' = position - 1 and returns true;
     *                 otherwise position is unchanged and returns false.
     * Test-cases:
     *   TC-CA-4  backward from 250 -> 249                        (testMoveBackwardFromMiddle)
     *   TC-CA-5  backward from 0 -> refused, stays 0             (testMoveBackwardFromStart)
     */

    @Override
    public boolean moveBackward() {
        if (position > 0){
            position--;
            return true;
        }
        return false;
    }

    /**
     * getPosition()
     * Description: Returns the current position in metres.
     * Pre-condition: none
     * Post-condition: returns position. No state change.
     * Test-cases: read in every test above (TC-CA-1 to TC-CA-7).
     */

    @Override
    public int getPosition() {
        return position;
    }
}
