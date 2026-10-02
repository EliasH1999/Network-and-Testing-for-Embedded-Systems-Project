public class MockSensor implements Sensor{

    private final boolean breakDown;

    /**
     * Manual mock-up of an ultrasound sensor on a fixed 500 m street.
     *
     * Street layout (free = distance of at least 100 cm):
     *   20-22    3 m  free (120 cm)  too small to park
     *   50-53    4 m  free (150 cm)  too small to park
     *   250-254  5 m  free (150 cm)  long enough
     *   450-454  5 m  free (150 cm)  long enough
     *   all other positions: a parked car at 50 cm
     *
     * Created with dependecy injection, breakDown = true, the sensor breaks at the middle of the street:
     * from position 249 on it returns 999, a recognizable out-of-bound value.
     */

    public MockSensor(boolean breakDown){
        this.breakDown = breakDown;
    }

    /**
     * getReading(int position)
     * Description: Returns the distance in cm at the given position on the fixed street,
     *              or 999 if the sensor is broken.
     * Pre-condition: 0 <= position <= 500
     * Post-condition: if breakDown and position >= 249, returns 999;
     *                 else if position is in 20..22, returns 120;
     *                 else if position is in 50..53, 250..254 or 450..454, returns 150;
     *                 else returns 50.
     */

    @Override 
    public int getReading(int position){

        if (breakDown && position >= 249 ){
            return 999;
        }

        // Parking place 1: 20-22
        // 3 metres -> too small
        if (position >= 20 && position <= 22) {
            return 120;
        }

        if(position >= 50. && position <= 53){
            return 150;
        }

        if(position >= 250 && position <= 254){
            return 150;
        }

        if(position >= 450 && position <= 454){
            return 150;
        }
        return 50;
    }

}