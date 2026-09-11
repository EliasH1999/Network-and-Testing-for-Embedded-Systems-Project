public class ParkingAssistant{
    
    public ??? MoveForward(){
        // This method moves the car 1 meter forward, queries the two sensors through the isEmpty method described below and returns a data structure that contains the current position of the car, 
        // and the situation of the detected parking places up to now. The car cannot be moved forward beyond the end of the street.

        /**
        Pre-condition: 
        Post-condition:
        Test-cases:
        */
    }
    
    public ??? isEmpty(){
        // This method queries the two ultrasound sensors at least 5 times and filters the noise in their results and returns the distance in cm to the nearest object 
        // in the right hand side. If one sensor is detected to continuously return very noisy output, 
        // it should be completely disregarded. You can use averaging or any other statistical method to filter the noise from the signals received from the ultrasound 
        // sensors.  

        /**
        Pre-condition:
        Post-condition:
        Test-cases:
        */
    }

    public ??? MoveBackward(){
        // The same as MoveForward above; only it moves the car 1 meter backwards. 
        // The car cannot be moved behind if it is already at the beginning of the street.  

        /**
        Pre-condition:
        Post-condition:
        Test-cases:
        */
    }

    public ??? Park(){
        // It performs a pre-programmed reverse parallel parking maneuver, if it is already positioned at an empty parking space stretch, 
        // or moves the car forwards towards the end of the street until such a stretch is detected, and then parks it. 

        /**
        Pre-condition:
        Post-condition:
        Test-cases:
        */
    }

    public ??? Unpark(){
        // It moves the car forward (and to left) to front of the parking place, if it is parked. 

        /**
        Pre-condition:
        Post-condition:
        Test-cases:
        */
    }

    public ??? Wherels(){
        // This method returns the current position of the car in the street as well as its (un)parked status.

        /**
        Pre-condition:
        Post-condition:
        Test-cases:
        */
    }
}