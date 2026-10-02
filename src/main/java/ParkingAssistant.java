import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class ParkingAssistant{
    private Actuator actuator;
    private static final int streetLength = 500;
    private List<Boolean> parkingPlaces = new ArrayList<>(Collections.nCopies(streetLength + 1, false)); 
    private boolean parked = false;

    private MockSensor mockSensor;

    private int[] sensor1Readings;
    private int[] sensor2Readings;
    private Sensor sensor1;
    private Sensor sensor2;



    /**
     * -----PHASE2 Constructor----- 
     * ParkingAssistant(Sensor sensor1, Sensor sensor2, Actuator actuator)
     * Description: Creates the parking assistant with two sensors and an engine actuator.
     *              Any implementation of Sensor and Actuator can be injected.
     * Pre-condition: sensor1, sensor2 and actuator are non-null.
     * Post-condition: the car is at actuator.getPosition(), not parked, and no parking
     *                 places are recorded yet.
     * Test-cases: used by every test, TC-WI-1 checks the initial state (0, false).
     */
    public ParkingAssistant(Sensor sensor1, Sensor sensor2, Actuator actuator) {
        this.actuator = actuator;
        this.sensor1 = sensor1;
        this.sensor2 = sensor2;
    }

    /**
     * MoveForward()
     * Description: Moves the car 1 metre forward, queries the two sensors through 
     * isEmpty() and returns a data structure with the current position of the car 
     * and the parking places detected so far. The car cannot be moved beyond the end 
     * of the street; the actuator refuses such a move.
     * Pre-condition: none; all edge cases are handled by the post-condition.
     * Post-condition:
     *   If not parked and the actuator accepts the move:
     *     position' = position + 1
     *     parkingPlaces[position'] = isEmpty() >= 100
     *     all other entries of parkingPlaces remain unchanged
     *   Otherwise (parked, or the actuator refuses because position == 500):
     *     position and parkingPlaces remain unchanged and the sensors are not read.
     *   Returns a ParkingStatus snapshot of the resulting state.
     * Test-cases:
     *   TC-MF-1  normal move from the middle of the street (250 -> 251)
     *   TC-MF-2  first move from 0 (0 -> 1)
     *   TC-MF-3  at 500: position stays 500, no exception
     *   TC-MF-4  while parked: position unchanged, still parked, nothing recorded
     *   TC-MF-5  free metre (isEmpty() >= 100) recorded as true
     *   TC-MF-6  occupied metre (isEmpty() < 100) recorded as false
     *   TC-MF-7  returned status is a snapshot, unchanged by a later move
     *   TC-MF-8  actuator refuses the move -> sensors never read, position as reported by the actuator
     *   TC-MF-9  actuator accepts the move -> one moveForward() command sent, new metre recorded
     */

    public ParkingStatus MoveForward(){


        if(!parked && actuator.moveForward()){                 // TC-MF-3, TC-MF-4                        // TC-MF-1, TC-MF-2
            parkingPlaces.set(actuator.getPosition(), isEmpty() >= 100);      // TC-MF-5, TC-MF-6
        }
            
        return new ParkingStatus(actuator.getPosition(), parkingPlaces);      // TC-MF-1, TC-MF-7
        
    }

    /**
     * isEmpty()
     * Description: Queries the two ultrasound sensors 5 times each, filters the noise and
     *              returns the distance in cm to the nearest object on the right-hand side.
     *              A sensor that is noisy or broken is completely disregarded.
     * Pre-condition: both sensors are non-null.
     * Post-condition: for each sensor, 5 readings are taken and sorted;
     *     noisy  = max - min > 50
     *     broken = any reading < 0 or > 200
     *     valid  = not noisy and not broken
     *   both valid     -> average of the two medians
     *   only one valid -> median of the valid sensor
     *   neither valid  -> 0
     *   The position of the car and parkingPlaces remain unchanged.
     * Test-cases:
     *   TC-IE-1   both clean, identical readings -> 130
     *   TC-IE-2   both clean, different readings -> average of medians, 127
     *   TC-IE-3   one outlier within the limit -> median filters it out, 150
     *   TC-IE-4   sensor 1 noisy -> median of sensor 2, 125
     *   TC-IE-5   sensor 2 noisy -> median of sensor 1, 125
     *   TC-IE-6   both noisy -> 0
     *   TC-IE-7   spread exactly 50 -> not noisy, 130
     *   TC-IE-8   no side effects on position or parked flag
     *   TC-IE-9   spread 51 -> noisy (boundary), both noisy -> 0
     *   TC-IE-10  both sensors read 150 -> 150, each sensor queried exactly 5 times
     *   TC-IE-11  sensor 2 all 999 -> broken, returns sensor 1's median 130
     *   TC-IE-12  sensor 2 all -1 -> broken, returns 130
     *   TC-IE-13  sensor 1 all 200 -> valid (upper limit), returns 165
     *   TC-IE-14  sensor 2 all 0 -> valid (lower limit), returns 65
     *   TC-IE-15  sensor 1 has one 201 -> broken, returns 130
     *   TC-IE-16  sensor 2 has one -1 -> broken, returns 130
     *   TC-IE-17  sensor 1 all -5, sensor 2 all 999 -> both broken, returns 0
     */
    
    public int isEmpty(){
        
        int position = actuator.getPosition();
        
        int[] readings1 = new int[5];
        int[] readings2 = new int[5];

        for(int i = 0; i < 5; i++){

            readings1[i] = sensor1.getReading(position);
            readings2[i] = sensor2.getReading(position);
        }
    
        Arrays.sort(readings1);
        Arrays.sort(readings2);

        int median1 = readings1[2];                             // TC-IE-1, TC-IE-2, TC-IE-3
        int median2 = readings2[2];

        // int DifferenceValue1 = readings1[4] - readings1[0];
        // int DifferenceValue2 = readings2[4] - readings2[0];

        // Noisy (Phase 1): the sensor works, but its readings vary too much
        boolean Sensor1Noisy = readings1[4] - readings1[0] > 50;              // TC-IE-7, TC-IE-9
        boolean Sensor2Noisy = readings2[4] - readings2[0] > 50;

        // Broken (Phase 2): a reading outside the valid range 0..200 cm
        boolean Sensor1Broken = readings1[0] < 0 || readings1[4] > 200;       // TC-IE-11 to TC-IE-17
        boolean Sensor2Broken = readings2[0] < 0 || readings2[4] > 200;

        boolean Sensor1Valid = !Sensor1Noisy && !Sensor1Broken;
        boolean Sensor2Valid = !Sensor2Noisy && !Sensor2Broken;

        if(!Sensor1Valid && !Sensor2Valid){
            return 0;                                           // TC-IE-6
        }
        else if(!Sensor1Valid){
            return median2;                                     // TC-IE-4
        }
        else if(!Sensor2Valid){
            return median1;                                     // TC-IE-5  
        }
        else{
            return (median1 + median2) / 2;                     // TC-IE-1, TC-IE-2
        }
       
    }

    /**
     * MoveBackward()
     * Description: The same as MoveForward(), only it moves the car 1 metre backwards.
     *              The car cannot be moved behind the beginning of the street; the actuator
     *              refuses such a move.
     * Pre-condition: none; all edge cases are handled by the post-condition.
     * Post-condition:
     *   If not parked and the actuator accepts the move:
     *     position' = position - 1
     *     parkingPlaces[position'] = isEmpty() >= 100
     *   Otherwise (parked, or the actuator refuses because position == 0):
     *     state unchanged, isEmpty() not called.
     *   Returns a ParkingStatus snapshot of the resulting state.
     * Test-cases:
     *   TC-MB-1  normal move backward (250 -> 249)
     *   TC-MB-2  at 0: position stays 0, no exception
     *   TC-MB-3  while parked: position unchanged
     *   TC-MB-4  arriving metre re-sensed and earlier reading overwritten;
     *            metres not re-sensed keep their reading
     *   TC-MB-5  moving from 1 to 0: position 0 (reading stored at index 0, which Park never uses)
     *   TC-MB-6  returned status is a snapshot, unchanged by a later move
     */
     
    
    public ParkingStatus MoveBackward(){
        if(!parked && actuator.moveBackward()){                               // TC-MB-2, TC-MB-3                                           // TC-MB-1, TC-MB-5
        parkingPlaces.set(actuator.getPosition(), isEmpty() >= 100);          // TC-MB-4
    }
        return new ParkingStatus(actuator.getPosition(), parkingPlaces);      // TC-MB-1, TC-MB-6

         
    }

    /**
     * Park()
     * Description: Parks the car in the first available 5-metre space. If already at the end
     *              of such a space, parks immediately; otherwise moves forward until one is
     *              found or the end of the street is reached.
     * Pre-condition: none; if no space exists the car stops at the end of the street.
     * Post-condition:
     *   Let parkable(p) = p >= 5 and parkingPlaces[p-4..p] all true.
     *   If parked: state unchanged.
     *   If parkable(position): parked' = true, position unchanged.
     *   Otherwise: MoveForward() is applied repeatedly until parkable(position) or
     *   position == 500. If parkable, parked = true; else parked = false and position = 500.
     * Test-cases:
     *   TC-PK-1  already at end of a free stretch: parks without moving (at 11)
     *   TC-PK-2  stretch ahead: drives to its end and parks (at 5)
     *   TC-PK-3  sensors disagree, averaged distance below threshold: no space, ends at 500 unparked
     *   TC-PK-4  no stretch anywhere: ends at 500 unparked
     *   TC-PK-5  stretch is the last 5 metres (496-500): parks at 500
     *   TC-PK-6  already parked: nothing happens, position unchanged
     *   TC-PK-7  called at 0 with free street: parks at 5
     *   TC-PK-8  at 500, unparked, nothing sensed: nothing happens
     */
    

    public void Park(){
        while(!parked) {                // TC-PK-6
            boolean parkable = actuator.getPosition() >= 5;                       // TC-PK-7
            
            if(parkable){
                for(int i = actuator.getPosition() - 4; i <= actuator.getPosition(); i++) {
                    if(!parkingPlaces.get(i)) {                     // TC-PK-1, TC-PK-2
                        parkable = false;
                        break;
                    }
                }
            } if(parkable) {
                parked = true;                                       // TC-PK-1, TC-PK-2, TC-PK-5
            } else if(actuator.getPosition() < streetLength) {
                MoveForward();                                       // TC-PK-2, TC-PK-7
            } else {
                break; // Reached the end of the street              // TC-PK-4, TC-PK-8
            }
        }
    }

    /**
     * Unpark()
     * Description: Moves the car forward (and to the left) to the front of the parking place,
     *              if it is parked.
     * Pre-condition: none
     * Post-condition: if the car was parked, parked becomes false; position and parkingPlaces
     *                 are unchanged. If the car was not parked, nothing changes.
     * Test-cases:
     *   TC-UP-1  parked car becomes unparked
     *   TC-UP-2  not parked: nothing happens
     *   TC-UP-3  after unparking, MoveForward() works again
     *   TC-UP-4  parking record unchanged: a second Park() parks immediately at the same position
     */

    public void Unpark(){
        
        if(parked) {            // TC-UP-2
            parked = false;     // TC-UP-1, TC-UP-3
        }
    }

    /**
     * WhereIs()
     * Description: Returns the current position of the car in the street as well as its
     *              (un)parked status.
     * Pre-condition: none
     * Post-condition: returns CarStatus(position, parked). No state changes.
     * Test-cases:
     *   TC-WI-1  initial state: (0, false)
     *   TC-WI-2  after two moves: (2, false)
     *   TC-WI-3  after Park(): (5, true)
     *   TC-WI-4  after Unpark(): (5, false)
     *   TC-WI-5  no side effects: repeated calls give the same result
     */

    public CarStatus WhereIs(){

        CarStatus carStatus = new CarStatus(actuator.getPosition(), parked); // TC-WI-1
        return carStatus;


    }

    }
