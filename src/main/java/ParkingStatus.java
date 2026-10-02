import java.util.ArrayList;
import java.util.List;

/**
 * Snapshot returned by MoveForward() and MoveBackward(): the current position of the
 * car and the parking places detected so far.
 *
 * Description: Returns the current position of the car as well as the parking record.
 * Pre-condition: parkingPlaces is non-null and has length 501.
 * Post-condition: getcurrentPosition() == currentPosition; getparkingPlaces() returns a new
 *                 list equal in content to parkingPlaces at construction time, so later
 *                 changes to the original do not affect it.
 * Test-cases: covered through TC-MF-7 and TC-MB-6 (returned status is a snapshot).
 */

public class ParkingStatus {
    private int currentPosition;
    private List<Boolean> parkingPlaces = new ArrayList<Boolean>();
    public ParkingStatus(int currentPosition, List<Boolean> parkingPlaces) {
        this.currentPosition = currentPosition;
        this.parkingPlaces = new ArrayList<>(parkingPlaces);
    }
    public int getcurrentPosition() {
        return currentPosition;
    }

    public List<Boolean> getparkingPlaces() {
        return new ArrayList<>(parkingPlaces);
    }   
}