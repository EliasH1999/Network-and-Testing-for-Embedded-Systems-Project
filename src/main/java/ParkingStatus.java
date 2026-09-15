
public class ParkingStatus {
    // Define the fields and methods for the MovementDataStructure class
    // This class should contain information about the current position of the car and the situation 
    // of the detected parking places
    private int currentPosition;
    public ParkingStatus(int currentPosition) {
        this.currentPosition = currentPosition;
    }
        public int getcurrentPosition() {
        return currentPosition;
    }
    /*
    Description: Returns the current position of the car as well as the parking record.
    Pre-condition: Parking places it non null and has length of 501
    Post-condition: getcurrentPosition() == position, getparkingPlaces() is a new array equal in content to parkingPlaces
    at construction time; later changes to the original do not affect it. 
    Test-cases: TC1: content equals the array passed in, TC2 modifying the original array does not change
    the snapshot
    */
}