import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
public class ParkingAssistant{
    private int currentPosition = 0;
    private int streetLength = 500;
    private List<Boolean> parkingPlaces = new ArrayList<Boolean>();


    public ParkingStatus MoveForward(){
        
        if(currentPosition < streetLength) {
            currentPosition++;
        }

        int distance = isEmpty();
        boolean free = distance >= 100;

        parkingPlaces.add(free);
            
        return new ParkingStatus(currentPosition, parkingPlaces);  
        


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
        Test-cases: TC1: normal move from the middle of the street
        TC2: first move from 0
        TC3: last move from 500
        TC4: move when parked, state unchanged
        TC5: free meter (isEmpty() >= 100) recorded as true
        TC6: occupied meter (isEmpty() < 100) recorded as false
        TC7 returned status is a snapshot (unchanged by a later move)

        */
    //}
    }
    
    public ParkingAssistant(int currentPosition) {
        this.currentPosition = currentPosition;
    }
    public int isEmpty(){

        int[] readings1 = new int[5];
        int[] readings2 = new int[5];

        for(int i = 0; i < 5; i++){
            readings1[i] = sensor1.read();
            readings2[i] = sensor2.read();
        }

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
        Test-cases: TC1: both sensors are not noisy, return average of medians
        TC2: sensor 1 is noisy, return median of the other sensor
        TC3: both sensors are noisy, return 0
        TC4: sensor 2 is noisy, return median of the other sensor
        TC5: spread exactly 50, not noisy
        TC6: each sensor is read exactly 5 times
        TC7: no side effects, position and parkingPlaces unchanged
        */
       
    }
     

        public ParkingStatus MoveBackward(){
            if(currentPosition > 0) {
                currentPosition--;
            }
            return new ParkingStatus(currentPosition);

         
        /*   Description: The same as MoveForward above; only it moves the car 1 meter backwards.
          The car cannot be moved behind if it is already at the beginning of the street.
          Pre-condition: None. All of the edge cases are handled by the post-condition.
          Post-condition: If position > 0 and not parked.
          position = position - 1
          parkingPlaces[position] = isEmpty() > DifferenceValue
          Otherwise (position == 0 or parked)
          state unchanged, isEmpty() not called
          A ParkingStatus snapchat of the resulting state is returned.
          Test-cases: TC1: normal move from the middle of the street
            TC2: first move from 0
            TC3: while parked: state unchanged
            TC4: arriving meter re-sensed and earlier reading is overwritten
            TC5: moving back to 0 does not write to index 0
            TC6: returned status is a snapshot (unchanged by a later move)
        */ 
         
     }

    // public ??? Park(){
       
    //     /**
    //     Description: It performs a pre-programmed reverse parallel parking maneuver, if it is already positioned at an empty parking space stretch, 
    //     or moves the car forwards towards the end of the street until such a stretch is detected, and then parks it. 
    //     Pre-condition:
    //     Post-condition:
    //     Test-cases:
    //     */
    // }

    // public ??? Unpark(){

    //     /**
    //     Description: It moves the car forward (and to left) to front of the parking place, if it is parked.
    //     Pre-condition:
    //     Post-condition:
    //     Test-cases:
    //     */
    // }

    // public ??? Wherels(){
    //     // This method returns the current position of the car in the street as well as its (un)parked status.

    //     /**
    //     Pre-condition:
    //     Post-condition:
    //     Test-cases:
    //     */
    // } 
    }
