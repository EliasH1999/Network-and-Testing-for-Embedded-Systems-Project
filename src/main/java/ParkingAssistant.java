import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class ParkingAssistant{
    private int position = 0;
    private static final int streetLength = 500;
    private List<Boolean> parkingPlaces = new ArrayList<>(Collections.nCopies(streetLength + 1, false)); 
    private boolean parked = false;

    private int[] sensor1Readings;
    private int[] sensor2Readings;

    //Constructor
    public ParkingAssistant(int position) {
        this.position = position;
    }

    public void setSensorReadings(int[] sensor1, int[] sensor2) {
        this.sensor1Readings = sensor1;
        this.sensor2Readings = sensor2;
    }


    public ParkingStatus MoveForward(){


        if(position < streetLength && !parked){                 // TC-MF-3, TC-MF-4
            position++;                                         // TC-MF-1, TC-MF-2
            parkingPlaces.set(position, isEmpty() >= 100);      // TC-MF-5, TC-MF-6
        }
            
        return new ParkingStatus(position, parkingPlaces);      // TC-MF-1, TC-MF-7
        

        /**
        Description: This method moves the car 1 meter forward, queries the two sensors through the isEmpty method described below and returns a data structure 
        that contains the current position of the car, and the situation of the detected parking places up to now. The car cannot be moved forward beyond the end of the street.
        Pre-condition: None, all of the edge cases are handled by the post-condition.
        Post-condition: 
        If position < 500 and not parked.
            position = position + 1
            parkingPlaces[position] = isEmpty() >= 100
            all other entries of parkingPlaces remain unchanged3
            Otherwise (position == 500 or parked)
            state of the car and parkingPlaces remain unchanged
        Test-cases: 
        TC-MF-1  normal move from the middle of the street (250 → 251)
        TC-MF-2  first move from 0 (0 -> 1)
        TC-MF-3  at 500: position stays 500, no exception
        TC-MF-4  while parked: position unchanged, still parked, nothing recorded
        TC-MF-5  free metre (isEmpty() >= 100) recorded as true
        TC-MF-6  occupied metre (isEmpty() < 100) recorded as false
        TC-MF-7  returned status is a snapshot, unchanged by a later move

        */
    }
    
    public int isEmpty(){

        int[] readings1 = sensor1Readings.clone();
        int[] readings2 = sensor2Readings.clone();

        Arrays.sort(readings1);
        Arrays.sort(readings2);

        int median1 = readings1[2];                             // TC-IE-1, TC-IE-2, TC-IE-3
        int median2 = readings2[2];

        int DifferenceValue1 = readings1[4] - readings1[0];
        int DifferenceValue2 = readings2[4] - readings2[0];

        boolean Sensor1Noisy = DifferenceValue1 > 50;           // TC-IE-7, TC-IE-9
        boolean Sensor2Noisy = DifferenceValue2 > 50;

        if(Sensor1Noisy && Sensor2Noisy){
            return 0;                                           // TC-IE-6
        }
        else if(Sensor1Noisy){
            return median2;                                     // TC-IE-4
        }
        else if(Sensor2Noisy){
            return median1;                                     // TC-IE-5  
        }
        else{
            return (median1 + median2) / 2;                     // TC-IE-1, TC-IE-2
        }

        /**
        Description:This method queries the two ultrasound sensors at least 5 times and filters the noise in their results and returns the distance in cm to the nearest object 
        in the right hand side. If one sensor is detected to continuously return very noisy output, 
        it should be completely disregarded. You can use averaging or any other statistical method to filter the noise from the signals received from the ultrasound 
        sensors.  
        Pre-condition: Both sensors as are non-null
        Post-condition: For each sensor: 5 readings taken, noisy = max - min > 50
            if neither sensor is noisy, return the average of the medians of both sensors
            if one sensor is noisy, return the median of the other sensor
            if both sensors are noisy, return 0
        position of the car and parkingPlaces remain unchanged
        Test-cases: 
        TC-IE-1  both clean, identical readings -> 130
        TC-IE-2  both clean, different readings -> average of medians, 127
        TC-IE-3  one outlier within the limit -> median filters it out, 150
        TC-IE-4  sensor 1 noisy -> median of sensor 2, 125
        TC-IE-5  sensor 2 noisy -> median of sensor 1, 125
        TC-IE-6  both noisy -> 0
        TC-IE-7  spread exactly 50 -> not noisy, 130
        TC-IE-8  no side effects on position or parked flag
        TC-IE-9  spread 51 -> noisy (boundary), both noisy -> 0
        */
       
    }
     
    
        public ParkingStatus MoveBackward(){
            if(position > 0 && !parked){                            // TC-MB-2, TC-MB-3
            position--;                                             // TC-MB-1, TC-MB-5
            parkingPlaces.set(position, isEmpty() >= 100);          // TC-MB-4
        }
            return new ParkingStatus(position, parkingPlaces);      // TC-MB-1, TC-MB-6

         
        /*   Description: The same as MoveForward above; only it moves the car 1 meter backwards.
          The car cannot be moved behind if it is already at the beginning of the street.
          Pre-condition: None. All of the edge cases are handled by the post-condition.
          Post-condition: If position > 0 and not parked.
          position = position - 1
          parkingPlaces[position] = isEmpty() >= 100
          Otherwise (position == 0 or parked)
          state unchanged, isEmpty() not called
          A ParkingStatus snapchat of the resulting state is returned.
          Test-cases: 
          TC-MB-1  normal move backward (250 -> 249)
          TC-MB-2  at 0: position stays 0, no exception
          TC-MB-3  while parked: position unchanged
          TC-MB-4  arriving metre re-sensed and earlier reading overwritten; metres not re-sensed keep their reading
          TC-MB-5  moving from 1 to 0: position 0 (reading stored at index 0,which Park never uses)
          TC-MB-6  returned status is a snapshot, unchanged by a later move
        */ 
         
        }
    

    public void Park(){
    while(position <= streetLength && !parked) {                // TC-PK-6
        boolean parkable = position >= 5;                       // TC-PK-7
        
        if(parkable){
            for(int i = position - 4; i <= position; i++) {
                if(!parkingPlaces.get(i)) {                     // TC-PK-1, TC-PK-2
                    parkable = false;
                    break;
                }
            }
        } if(parkable) {
            parked = true;                                       // TC-PK-1, TC-PK-2, TC-PK-5
        } else if(position < streetLength) {
            MoveForward();                                       // TC-PK-2, TC-PK-7
        } else {
            break; // Reached the end of the street              // TC-PK-4, TC-PK-8
        }
    }
    /*
    Description: Parks the car in the first available 5-metre space. If already at the end
                 of such a space, parks immediately; otherwise moves forward until one is
                 found or the end of the street is reached.
          
    Pre-condition: 5 metre space is available in the street.

    Post-condition:
        Let parkable(p) = p >= 5 and parkingPlaces[p-4..p] all true.
        If parked: state unchanged.
        If parkable(position): parked' = true, position unchanged.
        Otherwise: moveForward() is applied repeatedly until parkable(position)
        or position == 500. If parkable, parked = true; else 
        parked = false and position = 500.

    Test-cases:
       TC-PK-1  already at end of a free stretch: parks without moving (at 11)
       TC-PK-2  stretch ahead: drives to its end and parks (at 5)
       TC-PK-3  sensors disagree, averaged distance below threshold: no space, ends at 500 unparked
       TC-PK-4  no stretch anywhere: ends at 500 unparked
       TC-PK-5  stretch is the last 5 metres (496–500): parks at 500
       TC-PK-6  already parked: nothing happens, position unchanged
       TC-PK-7  called at 0 with free street: parks at 5
       TC-PK-8  at 500, unparked, nothing sensed: nothing happens
    */
    }

    public void Unpark(){
        
        if(parked) {            // TC-UP-2
            parked = false;     // TC-UP-1, TC-UP-3
        }

    /*
        Description: It moves the car forward (and to left) to front of the parking place, if it is parked.
        Pre-condition: None.
        Post-condition: If the car was parked: parked becomes false; position and parkingPlaces are unchanged. If the car was not parked: nothing changes.
        Test-cases:
        TC-UP-1  parked car becomes unparked
        TC-UP-2  not parked: nothing happens
        TC-UP-3  after unparking, MoveForward() works again
        TC-UP-4  parking record unchanged: a second Park() parks immediately at the same position
    */
    }

    public CarStatus WhereIs(){

        CarStatus carStatus = new CarStatus(position, parked); // TC-WI-1
        return carStatus;

        /*
        Description: This method returns the current position of the car in the street as well as its (un)parked status.
        Pre-condition: None.
        Post-condition: Returns CarStatus(position, parked). No state changes.
        Test-cases:
        TC-WI-1  initial state: (0, false)
        TC-WI-2  after two moves: (2, false)
        TC-WI-3  after Park(): (5, true)
        TC-WI-4  after Unpark(): (5, false)
        TC-WI-5  no side effects: repeated calls give the same result
        */
    }

    }
