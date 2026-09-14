#### Assumptions

# General
- The street is 500 metres long. Valid positions are the integers 0 to 500 (501 positions). The car starts at position 0.
- The car is modelled as a point at a single integer position, it never occupies a rang of positions. Its physical length is abstracted away, since neither the acutators nor the parking manoeuvre geometry are modelled in this phase.
- The "5 metres" in the spec refers to the parking space, not the car. A parking space is any 5 consecutive free metres.
- A metre is free if isEmpty() returns more than 100cm, otherwise it is occuped. The threshold is a single named constant. 
- The end of the 5 metre stretch is the last free metre of the space. The car is at a parkable position when metres postion-4->position are all recorded as free. The earliest parkable position is 5 (space1-5), the lastest is 500 (space 496-500)
- The parking record is kept for the whole street and is never cleared by parking or unparking. 

# MoveForward
- Moves the car exactly 1 metre forward per call, postion increases by 1.
- If the car is already at 500 (end of the street), the call does nothing, position stays 500, no exception is thrown, and the returned data structure reflects the unchanged state
- The car cannot be moved while parked, and the call is ignored and the unchanged state is returned
- Calls isEmpty() exactly once after moving and records the result for the new position. Position 0 is therefore never sensed; positions 1-500 are sensed as the car reaches them. 
- Returns a ParkingStatus contaning the position and the parking record. The returned object is a copy
-Returns a ParkingStatus containing the position and the parking record. The returned object is a copy, so later moves do not alter an earlier result.

# IsEmpty
- Sampels each sensor 5 times per call (10 readings in total.)
- Noise filtering for each sensor, the 5 readings are combined using the median. The two sensor medians are then averaged (to give the final distance)
- Noisy sensors is judged per call, a sensor is very noisy if the diffrence between its highest and lowest reading in the sample exceeds 50cm. Such sensor is disregarded completely for that call and the result is the median of the remaning sensor
- If both sensors are very noisy, returns 0 (the space is treated as occupied)
- Returns an int in cm. Has no side effects on position, parked status or the parking record; recording is done by the move methods. 

# MoveBackward
- Decreases the position by exactly 1
- If the car is already at 0, the call does nothing, in the same way as MoveForward at 500. 
- Not allowed while parked; ignored same as MoveForward.
- Because it is "the same as MoveForward", it also calls isEmpty() once after moving and records the results. Moving back over an already-sensed mtetre overwrites the earlier reading so a metre is always described by its most recent reading. 
- Returns a ParkingStatus copy, same as MoveForward. 

# Park
- If the car is unparked and at a parkable position, it performs the manoeuvre. In this phase that means, the parked flag becomes true and the position is unchanged. The actuator signal for the manoeuvre is not modelled. 
- Otherwise it calls MoveForward() repeatedly until a position is reached, then parks as above. 
- If the car reaches 500 without finding a space it stays at 500 unparked.
- If the car is already parked, park() does nothing.
- Returns nothing, the result can be observed through WhereIs()

# UnPark
- If the car is parked, it performs the pre-programmed unparking manoeuvre. In the point model the end of the stretch is the front of the parking place. so the position is unchanged and the parked flag becomes false. 
- If the car is not parked, it does nothing
- Returns nothing

# WhereIs
- Returns a CarStatus containing postion (int, 0-500) and parked (boolean) 
