import java.util.Arrays;
public class ParkingAssistant{
    private int position = 0;
    private int streetLength = 500;
    private boolean parked = false;
    public ParkingStatus MoveForward(){
        
        if(position < streetLength) {
            position++;
        }
        return new ParkingStatus(position);  
        
        
        /**
        Description: This method moves the car 1 meter forward, queries the two sensors through the isEmpty method described below and returns a data structure 
        that contains the current position of the car, and the situation of the detected parking places up to now. The car cannot be moved forward beyond the end of the street.
        Pre-condition: The car has been initialized and the current position is less than the street length so it can move
        forward
        Post-condition: The car has moved forward by 1 meter and the current position has been updated accordingly.
        Test-cases: Test that the car moves forward correctly when the current position is less than the street length.

        */
    //}
    }
    
    public ParkingAssistant(int position) {
        this.position = position;
    }
    public int isEmpty(int [] sensor1, int [] sensor2){

        Arrays.sort(sensor1);
        Arrays.sort(sensor2);

        int median1 = sensor1[2];
        int median2 = sensor2[2];

        int DifferenceValue1 = sensor1[4] - sensor1[0];
        int DifferenceValue2 = sensor2[4] - sensor2[0];

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
        Pre-condition:
        Post-condition:
        Test-cases:
        */
       
    }


    //     /**
    //     Description:This method queries the two ultrasound sensors at least 5 times and filters the noise in their results and returns the distance in cm to the nearest object 
    //     in the right hand side. If one sensor is detected to continuously return very noisy output, 
    //     it should be completely disregarded. You can use averaging or any other statistical method to filter the noise from the signals received from the ultrasound 
    //     sensors.  
    //     Pre-condition:
    //     Post-condition:
    //     Test-cases:
    //     */
     

        public ParkingStatus MoveBackward(){
            if(position > 0) {
                position--;
            }
            return new ParkingStatus(position);

         
        //  Description: The same as MoveForward above; only it moves the car 1 meter backwards.
        //  The car cannot be moved behind if it is already at the beginning of the street.
        //  Pre-condition:
        //  Post-condition:
        //  Test-cases:
         
     }

    // public ??? Park(){
       
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
    // }

    // public ??? Unpark(){

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
    // }

    public CarStatus Wherels(){
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
