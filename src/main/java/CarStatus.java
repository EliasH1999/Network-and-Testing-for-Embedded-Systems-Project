public class CarStatus {
    private final int position;
    private final boolean parked;

    /**
     * Snapshot of the car's state, returned by ParkingAssistant.WhereIs().
     *
     * Description: Holds the position of the car and whether it is parked.
     * Pre-condition: 0 <= position <= 500
     * Post-condition: getPosition() == position and isParked() == parked;
     *                 the values never change after construction.
     * Test-cases: covered through WhereIs() (TC-WI-1 to TC-WI-5).
     */

    public CarStatus(int position, boolean parked) {
        this.position = position;
        this.parked = parked;
    }

    public int getPosition() {
        return position;
    }
    
    public boolean isParked() {
        return parked;
    }
}