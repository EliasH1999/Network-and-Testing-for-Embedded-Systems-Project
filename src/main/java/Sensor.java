public interface Sensor {
    /*
    Description: Returns the distance in cm to the nearest object in the right hand side.
    Pre-condition: None
    Post-condition: Returns an int for a working sensor it is in the range of 0-200, successive calls
    may return different values (noise). If the sensor is not working it returns 0. No state of ParkingAssistant 
    is affected 
    Test-cases: Not tested in this phase.
    
    
    */
    int read();

}
