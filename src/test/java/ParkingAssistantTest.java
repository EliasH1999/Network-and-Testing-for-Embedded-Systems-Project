import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


public class ParkingAssistantTest {
    private int[] values1; 
    private int[] values2;
    private int index1;
    private int index2;

    /* 
    Creates a ParkingAssistant instance with mocked sensors and a car actuator at the start position.
    The mocks return the 5 given sensor values in order and then start over.
    So every isEmpty() call gets exatcly these 5 values, as setSensorReadings did in phase1.
    
    This is a helper method for the Phase 1 test to be able to keep the phase1 tests but also prove the refactoring doesent brake anything.
    */

    private ParkingAssistant create(int[] sensor1, int[] sensor2, int position){
        values1 = sensor1;
        values2 = sensor2;
        index1 = 0;
        index2 = 0; 
        
        Sensor sensor1Mock = mock(Sensor.class);
        Sensor sensor2Mock = mock(Sensor.class);

        when(sensor1Mock.getReading(anyInt())).thenAnswer(invocation -> values1[index1++ % values1.length]);
        when(sensor2Mock.getReading(anyInt())).thenAnswer(invocation -> values2[index2++ % values2.length]);

        return new ParkingAssistant(sensor1Mock, sensor2Mock, new CarActuator(position));
    }

    private void changeSensorReading(int[] sensor1, int[] sensor2){
        values1 = sensor1;
        values2 = sensor2;
    }

    //PHASE1 Test cases for MoveForward method

    @Test
    public void testMoveForwardFromMiddleofStreet() { // TC-MF-1: normal move from the middle of the street
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 250);
        ParkingStatus result = parkingAssistant.MoveForward();

        assertEquals(251, result.getcurrentPosition(), "The car should have moved forward by 1 meter from the middle of the street.");
    }

    // test cases for the moveForward method
    @Test
    public void testMoveForward() { // TC-MF-2: first move from 0
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        ParkingStatus result = parkingAssistant.MoveForward();
        assertEquals(1, result.getcurrentPosition(), "The car should have moved forward by 1 meter.");

    }

    @Test  
    public void testMoveForwardAtEndOfStreet() { // TC-MF-3: last move from 500
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 500);
        ParkingStatus result = parkingAssistant.MoveForward();

        assertEquals(500, result.getcurrentPosition(), "The car should not move forward beyond the end of the street.");
        
    }

    @Test
    public void testMoveForwardWhenParked() { // TC-MF-4: move when parked, state unchanged
         int[] sensor1 = {100, 120, 130, 140, 145};
         int[] sensor2 = {110, 115, 125, 135, 140};
         ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
         parkingAssistant.Park(); // Park the car
         ParkingStatus result = parkingAssistant.MoveForward();

         assertEquals(5, result.getcurrentPosition(), "The car should not move forward when parked.");
         assertTrue(parkingAssistant.WhereIs().isParked(), "The car should still be parked.");
         assertFalse(result.getparkingPlaces().get(6), "No metre should be recorded when parked.");
        }


    @Test
    public void testMoveForwardWithFreeMeter() { // TC-MF-5: free meter (isEmpty() >= 100) recorded as true
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        ParkingStatus result = parkingAssistant.MoveForward();

        assertTrue(result.getparkingPlaces().get(1), "The first parking place should be recorded as free (true).");
        assertEquals(1, result.getcurrentPosition(), "The car should have moved forward by 1 meter. Free meter");
    }

    @Test
    public void testMoveForwardWithOccupiedMeter() { // TC-MF-6: occupied meter (isEmpty() < 100) recorded as false
        int[] sensor1 = {10, 15, 20, 25, 30};
        int[] sensor2 = {5, 10, 15, 20, 25};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        ParkingStatus result = parkingAssistant.MoveForward();  

        assertFalse(result.getparkingPlaces().get(1), "The first parking place should be recorded as occupied (false).");
        assertEquals(1, result.getcurrentPosition(), "The car should have moved forward by 1 meter. Occupied meter");
    }

    @Test
    public void testMoveForwardStatusUnchangedByLaterMove(){ //TC-MF-7: 
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);

        ParkingStatus a = parkingAssistant.MoveForward();
        ParkingStatus b = parkingAssistant.MoveForward();

        assertEquals(1, a.getcurrentPosition(), "The car should have moved forward by 1 meter on the first move.");
        assertFalse(a.getparkingPlaces().get(2), "First status should not know about metre 2.");
        assertEquals(2, b.getcurrentPosition(), "The car should have moved forward by 1 more meter on the second move.");
        assertTrue(b.getparkingPlaces().get(2), "Second status should  know about metre 2.");
        assertTrue(b.getparkingPlaces().get(1), "Second status should know about metre 1.");
    }


    //PHASE1 Test for isEmpty() method

    @Test
    public void testIsEmptySameSensorInputs() { // TC-IE-1: both sensors are not noisy, same sensor values return average of medians
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {100, 120, 130, 140, 145};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        int result = parkingAssistant.isEmpty();

        assertEquals(130, result, "The average distance to the nearest object should be 130 cm.");
    }


    @Test
    public void testIsEmptyAverageOfTheMedians() { // TC-IE-2: both sensors are not noisy, diffrent sensor values return average of medians 
        
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        int result = parkingAssistant.isEmpty();

        assertEquals(127, result, "The average distance to the nearest object should be 127 cm.");
    }

    @Test
    public void testIsEmptyOutlierFilteredByMedian() { // TC-IE-3: one outlier within limit is filtered out
        
        int[] sensor1 = {150, 150, 190, 150, 150};   // One outlier within limit
        int[] sensor2 = {150, 150, 150, 150, 150};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);

        assertEquals(150, parkingAssistant.isEmpty(), "A single outlier must not affect the median.");
    }

    @Test
    public void testIsEmptyWithNoisySensor1() { // TC-IE-4: sensor 1 is noisy, return median of the other sensor
        
        int[] sensor1 = {100, 120, 130, 140, 160}; //NOISY
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        int result = parkingAssistant.isEmpty();
        assertEquals(125, result, "The distance to the nearest object should be 125 cm, disregarding the noisy sensor.");
        }   


    @Test
    public void testIsEmptyWithNoisySensor2() { // TC-IE-5: sensor 2 is noisy, return median of the other sensor
        
        int[] sensor1 = {110, 115, 125, 135, 140};
        int[] sensor2 = {100, 120, 130, 140, 160}; //NOISY
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        int result = parkingAssistant.isEmpty();
        assertEquals(125, result, "The distance to the nearest object should be 125 cm, disregarding the noisy sensor.");
    }

    @Test
    public void TestIsEmptyWithBothNoisySensors() { // TC-IE-6: both sensors are noisy, return 0
        
        int[] sensor1 = {100, 120, 130, 140, 160};
        int[] sensor2 = {110, 115, 125, 135, 170};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        int result = parkingAssistant.isEmpty();
        assertEquals(0, result, "Both sensors are noisy, so the result should be 0.");
    }
    
    @Test
    public void testIsEmptyWithSpreadExactly50() { // TC-IE-7: spread exactly 50, not noisy
        
        int[] sensor1 = {100, 120, 130, 140, 150}; // Spread is 50
        int[] sensor2 = {100, 120, 130, 140, 150}; // Spread is 50
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        int result = parkingAssistant.isEmpty();
        assertEquals(130, result, "The average distance to the nearest object should be 130 cm.");
    }

    @Test
    public void testIsEmptyHasNoSideEffects() { // TC-IE-8
        int[] sensor1 = {100, 120, 130, 140, 145}; // Spread is 50
        int[] sensor2 = {110, 115, 125, 135, 140}; // Spread is 50
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 10);
        parkingAssistant.isEmpty();
        parkingAssistant.isEmpty();

        CarStatus status = parkingAssistant.WhereIs();
        assertEquals(10, status.getPosition(), "isEmpty() must not move the car.");
        assertFalse(status.isParked(), "isEmpty() must not park the car.");
        assertEquals(11, parkingAssistant.MoveForward().getcurrentPosition(), "Next move behaves normally.");
    }

    @Test
    public void TestIsEmptyHigherBoundary() { // TC-IE-9: both sensors are noisy right at the higher boundary (51)
        int[] sensor1 = {100, 120, 130, 140, 151};
        int[] sensor2 = {110, 115, 125, 135, 161};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        int result = parkingAssistant.isEmpty();
        assertEquals(0, result, "Both sensors are noisy, so the result should be 0.");
    }

    // PHASE2 Test cases for isEmpty method 
    @Test
    public void testIsEmptyReadsMockedSensors(){ //TC-IE-10
        Sensor mockSensor1 = mock(Sensor.class); 
        Sensor mockSensor2 = mock(Sensor.class);
        when(mockSensor1.getReading(anyInt())).thenReturn(150);
        when(mockSensor2.getReading(anyInt())).thenReturn(150);
        ParkingAssistant parkingAssistant = new ParkingAssistant(mockSensor1, mockSensor2, new CarActuator(42));

        assertEquals(150, parkingAssistant.isEmpty(), "Both sensors report 150, so isEmpty() should return 150.");
        verify(mockSensor1, times(5)).getReading(anyInt());
        verify(mockSensor2, times(5)).getReading(anyInt());
    }

    

    // PHASE1 Test cases for MoveBackward method

     @Test
    public void testMoveBackwardFromMiddleofStreet() { // TC-MB-1: normal move from the middle of the street
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 250);
        ParkingStatus result = parkingAssistant.MoveBackward();
        assertEquals(249, result.getcurrentPosition(), "The car should have moved backward by 1 meter from the middle of the street.");
    }

     @Test
    public void testMoveBackwardAtStartOfStreet() { // TC-MB-2: first move from 0
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        ParkingStatus result = parkingAssistant.MoveBackward();
        assertEquals(0, result.getcurrentPosition(), "The car should not move backward beyond the start of the street.");
    }

    @Test 
    public void testMoveBackwardWhileParked(){ //TC-MB-3: while parked, state unchanged
        
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        parkingAssistant.Park();

        ParkingStatus result = parkingAssistant.MoveBackward();
        assertEquals(5, result.getcurrentPosition(), "The car should not move backward while parked.");
    }

    @Test 
    public void testArrivingMeterSensedAndEarlierReadingOverwritten(){ //TC-MB-4: arriving meter sensed and earlier reading overwritten
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();
        ParkingStatus a = parkingAssistant.MoveForward();
        int[] sensor1_2 = {99, 99, 99, 99, 99};
        int[] sensor2_2 = {99, 99, 99, 99, 99};
        changeSensorReading(sensor1_2, sensor2_2);
        parkingAssistant.MoveBackward();
        parkingAssistant.MoveBackward();
        ParkingStatus b = parkingAssistant.MoveBackward();

        assertEquals(3, a.getcurrentPosition(), "A should have moved to position 3");
        assertEquals(0, b.getcurrentPosition(), "B should have moved back to position 0");
        assertTrue(a.getparkingPlaces().get(2), "Position 2 should be free when first sensed");
        assertFalse(b.getparkingPlaces().get(2), "Position 2 should be occupied after moving back");
        assertTrue(b.getparkingPlaces().get(3), "Position 3 should be free since it is not resensed");
    }

    @Test 
    public void testMovebackwardToZero(){ //TC-MB-5:  
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        parkingAssistant.MoveForward();
        ParkingStatus a = parkingAssistant.MoveBackward();

        assertEquals(0, a.getcurrentPosition(), "A should have moved back to position 0");
    }



    @Test
    public void testMoveBackwardStatusUnchangedByLaterMove() { // TC-MB-6: returned status is a snapshot
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();                                 // at 3, metres 1–3 free

        int[] sensor1_2 = {99, 99, 99, 99, 99};
        int[] sensor2_2 = {99, 99, 99, 99, 99};
        changeSensorReading(sensor1_2, sensor2_2);
        ParkingStatus a = parkingAssistant.MoveBackward();              // at 2, metre 2 re-sensed occupied
        ParkingStatus b = parkingAssistant.MoveBackward();              // at 1, metre 1 re-sensed occupied

        assertEquals(2, a.getcurrentPosition(), "First backward status should still say position 2.");
        assertTrue(a.getparkingPlaces().get(1), "First status was taken before metre 1 was re-sensed.");
        assertEquals(1, b.getcurrentPosition());
        assertFalse(b.getparkingPlaces().get(1), "Second status has the new reading for metre 1.");
}
    





    // Test cases for the Park method
    @Test
    public void testParkAtEndOfFreeStrech() { // TC-PK-1: Already at the end of a free strech, should park rightaway 
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 6);
        for (int i = 0; i < 5; i++){
            parkingAssistant.MoveForward();
        }
        parkingAssistant.Park();
        CarStatus result = parkingAssistant.WhereIs();

        assertTrue(result.isParked(), "The car should be parked.");
        assertEquals(11, result.getPosition(), "The car should be parked at position 11.");
    }

    @Test
    public void testParkStretchAhead(){ //TC-PK-2
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        parkingAssistant.Park();
        CarStatus result = parkingAssistant.WhereIs();

        assertTrue(result.isParked(), "The car should be parked.");
        assertEquals(5, result.getPosition(), "The car should be parked at position 5.");
    }
    @Test
    public void testParkFullyOccupiedStreet(){ //TC-PK-3
        int[] sensor1 = {20, 30, 40, 45, 50};
        int[] sensor2 = {120, 130, 140, 150, 160};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        parkingAssistant.Park();
        CarStatus result = parkingAssistant.WhereIs();
        
        assertFalse(result.isParked(), "The car should not be parked.");
        assertEquals(500, result.getPosition(), "The car should moved from 0 to 500 since no space will be avilable.");
    }

    @Test
    public void testParkWhenAllStrechesOccupied(){ //TC-PK-4
        int[] sensor1 = {99, 99, 99, 99, 99};
        int[] sensor2 = {99, 99, 99, 99, 99};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        parkingAssistant.Park();
        CarStatus result = parkingAssistant.WhereIs();

        assertFalse(result.isParked(), "The car should not be parked.");
        assertEquals(500, result.getPosition(), "The car should have moved from 0 to 500.");
    }
    
    @Test
    public void testParkStretchLastFiveMeters(){ //TC-PK-5
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 495);
        parkingAssistant.Park();
        CarStatus result = parkingAssistant.WhereIs();

        assertTrue(result.isParked(), "The car should be parked.");
        assertEquals(500, result.getPosition(), "The car should be parked at position 500.");
    }
    
    @Test
    public void testParkAlreadyParked(){ //TC-PK-6
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        parkingAssistant.Park();
        CarStatus resultBeforePark = parkingAssistant.WhereIs();
        
        assertTrue(resultBeforePark.isParked(), "The car should be parked.");
        assertEquals(5, resultBeforePark.getPosition(), "Precondition: the car is parked at 5.");

        parkingAssistant.Park();
        CarStatus resultAfterPark = parkingAssistant.WhereIs();
        
        assertTrue(resultAfterPark.isParked(), "The car should still be parked after attempting to park again.");
        assertEquals(5, resultAfterPark.getPosition(), "The car should not have moved when Park() is called while parked.");
    }

    @Test
    public void testParkAsSoonAsPossibleStartFromZero() { // TC-PK-7
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);        parkingAssistant.Park();
        CarStatus result = parkingAssistant.WhereIs();

        assertTrue(result.isParked(), "The car should be parked.");
        assertEquals(5, result.getPosition(), "The car should be parked at position 5.");
    }

    @Test
    public void testParkAt500WithoutAnyFreeSpace() { // TC-PK-8
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 500);
        CarStatus resultBeforePark = parkingAssistant.WhereIs();
        parkingAssistant.Park();
        CarStatus resultAfterPark = parkingAssistant.WhereIs();

        assertFalse(resultBeforePark.isParked(), "The car should not be parked.");
        assertFalse(resultAfterPark.isParked(), "The car should not be parked.");
        assertEquals(500, resultBeforePark.getPosition(), "The car should be at position 500.");
        assertEquals(500, resultAfterPark.getPosition(), "The car should still be at position 500.");

    }









    // Test cases for the Unpark method
    @Test
    public void testIfCarCanUnParkwhenParked() { //TC-UP-1
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 10);
        parkingAssistant.Park(); // Park the car
        assertTrue(parkingAssistant.WhereIs().isParked(), "The car should be parked before calling Unpark().");
        parkingAssistant.Unpark(); // Unpark the car
        assertFalse(parkingAssistant.WhereIs().isParked(), "The car should be unparked after calling Unpark().");
    }
   
    @Test
    public void testUnParkWhenNotParked() { //TC-UP-2
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 10);
        assertFalse(parkingAssistant.WhereIs().isParked(), "The car should not be parked before calling Unpark().");
        parkingAssistant.Unpark(); // Unpark the car when it's not parked
        assertFalse(parkingAssistant.WhereIs().isParked(), "The car should remain unparked when Unpark() is called while not parked.");
    }
    
    @Test
    public void testMoveForwardAfterUnparking() { //TC-UP-3
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 10);
        parkingAssistant.Park(); // Park the car
        assertTrue(parkingAssistant.WhereIs().isParked(), "The car should be parked before calling Unpark().");
        parkingAssistant.Unpark(); // Unpark the car
        assertFalse(parkingAssistant.WhereIs().isParked(), "The car should be unparked after calling Unpark().");
        ParkingStatus result = parkingAssistant.MoveForward();
        assertEquals(16, result.getcurrentPosition(), "The car should move forward by 5 meter when park is called and after unparking it should have moved 1 more meter.");
    }

    @Test
    public void testParkingRecordUnchangedAfterUnparking() { //TC-UP-4
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 10);

        //Create initial movement to reach position 15 with 5 empty spaces
        for(int i = 0; i < 5; i++) {
            parkingAssistant.MoveForward();
        }
        CarStatus beforeParkAndUnpark = parkingAssistant.WhereIs();
        assertEquals(15, beforeParkAndUnpark.getPosition(), "The position should remain unchanged after unparking and parking again.");


        parkingAssistant.Park(); // Park the car
        assertTrue(parkingAssistant.WhereIs().isParked(), "The car should be parked before calling Unpark().");
        parkingAssistant.Unpark(); // Unpark the car
        assertFalse(parkingAssistant.WhereIs().isParked(), "The car should be unparked after calling Unpark().");
        parkingAssistant.Park(); // Park the car

        CarStatus afterParkAndUnpark = parkingAssistant.WhereIs();
        assertTrue(afterParkAndUnpark.isParked(), "The car should be parked after calling Park() again.");
    }

    
    // test for WhereIs() method
    @Test
    public void testWherelsInitialState() { // TC-WI-1: initial state (0, false)
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        CarStatus result = parkingAssistant.WhereIs();

        assertEquals(0, result.getPosition(), "The initial position should be 0.");
        assertFalse(result.isParked(), "The car should not be parked initially.");
    }

    @Test 
    public void testWherelsAfterMoving() { //TC-WI-2: after moving forward twice (2, false)
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();

        CarStatus result = parkingAssistant.WhereIs();

        assertEquals(2, result.getPosition(), "The position should be 2 after moving forward twice.");
        assertFalse(result.isParked(), "The car should not be parked after moving.");
    }

    @Test
    public void testWhereIsafterPark(){ //TC-WI-3: after parking (5, true)
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();

        parkingAssistant.Park(); // Park the car

        CarStatus carStatus = parkingAssistant.WhereIs();

        assertEquals(5, carStatus.getPosition(), "The position should be 5 after moving forward 5 times.");
        assertTrue(carStatus.isParked(), "The car should be parked after calling Park().");
    }

    @Test 
    public void testWhereIsafterUnpark(){ //TC-WI-4: after unparking (5, false)
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();
        parkingAssistant.MoveForward();

        parkingAssistant.Park(); // Park the car
        assertTrue(parkingAssistant.WhereIs().isParked(), "Precondition: car is parked before Unpark().");


        parkingAssistant.Unpark(); // Unpark the car

        CarStatus carStatus = parkingAssistant.WhereIs();

        assertEquals(5, carStatus.getPosition(), "The position should be 5 after moving forward 5 times.");
        assertFalse(carStatus.isParked(), "The car should not be parked after calling Park() and then Unpark().");
    }
    
    @Test 
    public void testWhereIsHasNoSideEffects(){ //TC-WI-5
        int[] sensor1 = {100, 120, 130, 140, 145};
        int[] sensor2 = {110, 115, 125, 135, 140};
        ParkingAssistant parkingAssistant = create(sensor1, sensor2, 0);

        CarStatus carStatusBefore = parkingAssistant.WhereIs();
        CarStatus carStatusAfter = parkingAssistant.WhereIs();

        assertEquals(0, carStatusBefore.getPosition(), "The initial position should be 0.");
        assertEquals(0, carStatusAfter.getPosition(), "The position should remain 0 after calling WhereIs().");
        assertFalse(carStatusBefore.isParked(), "The car should not be parked initially.");
        assertFalse(carStatusAfter.isParked(), "The car should not be parked after calling WhereIs().");

    }


}

