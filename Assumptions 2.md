#### Assumptions

### Logic assumptions

# Street and movement
- The car starts at 0 meters
- The car drives the entire 500 meters then stops if no parking spots were found.
- The car moves until a parking meter with a stretch of 5 meters is found
- The car moves exactly 1 meter when MoveForward() or MoveBackward() is called  

# Parking
- 


### Data structure assumptions

## MoveMoveForward
# Movement
- Moves the car exactly 1 metre forward per call, postion increases by 1.
- If the car is already at 500 (end of the street), the call does nothing, position stays 500, no exception is thrown, and the returned data structure reflects the unchanged state
- The car cannot be moved while parked, and IllegalStateException is thrown.

# Sensor
- After moving the method calls isEmpty() once and records the result of the new position.
- A position is recorded as "free" if the returned distance is greater than 100cm, otherwise it is occupied
- Position 0 (the start) is never sensed, since the car hasnt moved yet. positions 1-500 are sensed as the car reaches them.
- Moving backward and forward over the same metre overwrites the earlier reading with the newer one.

# Return value
- Returns a data structure called "ParkingStatus record" containing:
    - position (int 0-500)
    - parkingPlaces