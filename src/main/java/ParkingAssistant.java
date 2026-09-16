import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class ParkingAssistant{
    private int position = 0;
    private static final int streetLength = 500;
    private List<Boolean> parkingPlaces = new ArrayList<>(Collections.nCopies(streetLength + 1, false)); 
    private boolean parked = false;

    /* Vet inte om detta behövs
    public boolean getParkedStatus() {
        return parked;
    }

    public int getPosition() {
        return position;
    }*/

    private int[] sensor1Readings;
    private int[] sensor2Readings;

    public void setSensorReadings(int[] sensor1, int[] sensor2) {
        this.sensor1Readings = sensor1;
        this.sensor2Readings = sensor2;
    }


    public ParkingStatus MoveForward(){


        if(position < streetLength && !parked){
            position++;
            parkingPlaces.set(position, isEmpty() >= 100);
        }
            
        return new ParkingStatus(position, parkingPlaces);  
        

        /**
        Description: This method moves the car 1 meter forward, queries the two sensors through the isEmpty method described below and returns a data structure 
        that contains the current position of the car, and the situation of the detected parking places up to now. The car cannot be moved forward beyond the end of the street.
        Pre-condition: None, all of the edge cases are handled by the post-condition.
        Post-condition: 
        If position < 500 and not parked.
            position = position + 1
            parkingPlaces[position] = isEmpty() > DifferenceValue
            all other entries of parkingPlaces remain unchanged
            Otherwise (position == 500 or parked)
            state of the car and parkingPlaces remain unchanged
        Test-cases: 
        TC1: first move from 0
        TC2: last move from 500
        TC3: normal move from the middle of the street
        TC4: move when parked, state unchanged
        TC5: free meter (isEmpty() >= 100) recorded as true
        TC6: occupied meter (isEmpty() < 100) recorded as false
        TC7 returned status is a snapshot (unchanged by a later move)

        */
    //}
    }
    
    public ParkingAssistant(int position) {
        this.position = position;
    }
    public int isEmpty(){

        int[] readings1 = sensor1Readings;
        int[] readings2 = sensor2Readings;

        /*
        for(int i = 0; i < 5; i++){
            readings1[i] = sensor1.checkSensor()[i];
            readings2[i] = sensor2.checkSensor()[i];
        }*/

        Arrays.sort(readings1);
        Arrays.sort(readings2);

        int median1 = readings1[2];
        int median2 = readings2[2];

        int DifferenceValue1 = readings1[4] - readings1[0];
        int DifferenceValue2 = readings2[4] - readings2[0];

        boolean Sensor1Noisy = DifferenceValue1 > 50;
        boolean Sensor2Noisy = DifferenceValue2 > 50;

        if(Sensor1Noisy && Sensor2Noisy){
            return 0;
        }
        else if(Sensor1Noisy){
            return median2;
        }
        else if(Sensor2Noisy){
            return median1;
        }
        else{
            return (median1 + median2) / 2;
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
        TC1: both sensors are not noisy, return average of medians
        TC2: no sensor is noisy, but no free space, return average of medians
        TC3: sensor 1 is noisy, return median of the other sensor
        TC4: both sensors are noisy, return 0
        TC5: sensor 2 is noisy, return median of the other sensor
        TC6: spread exactly 50, not noisy
        TC6: each sensor is read exactly 5 times
        TC7: no side effects, position and parkingPlaces unchanged
        */
       
    }
     
    
        public ParkingStatus MoveBackward(){
            if(position > 0) {
                position--;
            }
            return new ParkingStatus(position, parkingPlaces);

         
        /*   Description: The same as MoveForward above; only it moves the car 1 meter backwards.
          The car cannot be moved behind if it is already at the beginning of the street.
          Pre-condition: None. All of the edge cases are handled by the post-condition.
          Post-condition: If position > 0 and not parked.
          position = position - 1
          parkingPlaces[position] = isEmpty() > DifferenceValue
          Otherwise (position == 0 or parked)
          state unchanged, isEmpty() not called
          A ParkingStatus snapchat of the resulting state is returned.
          Test-cases: 
            TC1: normal move from the middle of the street
            TC2: first move from 0
            TC3: while parked: state unchanged
            TC4: arriving meter re-sensed and earlier reading is overwritten
            TC5: moving back to 0 does not write to index 0
            TC6: returned status is a snapshot (unchanged by a later move)
        */ 
         
        }
    

     public void Park(){


    while(position <= streetLength && !parked) {
        boolean parkable = position >= 5;
        
        if(parkable){
            for(int i = position - 4; i <= position; i++) {
                if(!parkingPlaces.get(i)) {
                    parkable = false;
                    break;
                }
            }
        } if(parkable) {
            parked = true;
        } else if(position < streetLength) {
            MoveForward();
        } else {
            break; // Reached the end of the street
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
                or position == 500. If parkable, parked' = true; else
                parked' = false and position' = 500.

    Test-cases:
        Test1:  Already at end of a free stretch: parks without moving
        Test2:  Stretch ahead --> moves forward to its end and parks
        Test3:  Occupied metre inside the window: continues past it, parks at the next stretch
        Test4:  No stretch anywhere: ends at 500, unparked
        Test5:  Stretch is the last 5 metres (496–500): parks at 500
        Test6:  Already parked --> nothing happens
        Test7:  Called at 0 with free street: parks at 5
        Test8:  At 500 unparked with no stretch --> nothing happens
    */
    }

    public void Unpark(){
        
        if(parked) {
            parked = false;
        }

    /*
            Description: It moves the car forward (and to left) to front of the parking place, if it is parked.
            Pre-condition: None.
            Post-condition: If the car was parked: parked becomes false; position and parkingPlaces are unchanged. If the car was not parked: nothing changes.
            Test-cases:
                Test1:  Parked car becomes unparked, position unchanged
                Test2:  Car is not parked --> nothing happens
                Test3:  After unparking, moveForward works again
                Test4:  Parking record unchanged by unparking
    */
    }

    public CarStatus WhereIs(){
    // This method returns the current position of the car in the street as well as its (un)parked status.
        
        CarStatus carStatus = new CarStatus(position, parked);
        return carStatus;

        /*
        Pre-condition: None.

        Post-condition: Returns CarStatus(position, parked). No state changes.

        Test-cases:
            Test1:  Initial state: (0, false)
            Test2:  After some moves: correct position, false
            Test3:  After park(): correct position, true
            Test4:  After unPark(): correct position, false
        */
    }

    }
