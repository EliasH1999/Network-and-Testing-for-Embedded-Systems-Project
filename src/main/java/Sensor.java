public class Sensor {
    
    int[] readings1 = new int[5];
    int[] readings2 = new int[5];
    /*int[] readings3 = new int[5];
    int[] readings4 = new int[5];
    int[] readings5 = new int[5];*/

    public Sensor() {

    }

    /*
    Description: Returns the distance in cm to the nearest object in the right hand side.
    Pre-condition: None
    Post-condition: Returns an int for a working sensor it is in the range of 0-200, successive calls
    may return different values (noise). If the sensor is not working it returns 0. No state of ParkingAssistant 
    is affected 
    Test-cases: Not tested in this phase.
    
    
    */
    public int[] checkSensor() {
        return new int[]{100, 120, 130, 140, 145};
    }

}
