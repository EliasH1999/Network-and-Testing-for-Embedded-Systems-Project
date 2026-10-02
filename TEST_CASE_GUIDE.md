# Test Case Guide

This guide summarizes what each numbered test verifies in `CarActuatorTest` and the Phase 2 sections of `ParkingAssistantTest`.

## CarActuatorTest

| ID | Test | Coverage |
|---|---|---|
| TC-CA-1 | `testStartPosition` | A new actuator starts at the supplied position, 42. |
| TC-CA-2 | `testMoveForwardFromMiddle` | Moving forward from position 250 updates the position to 251. |
| TC-CA-3 | `testMoveForwardFromEnd` | Moving forward at the street limit, position 500, leaves the position at 500. |
| TC-CA-4 | `testMoveBackwardFromMiddle` | Moving backward from position 250 updates the position to 249. |
| TC-CA-5 | `testMoveBackwardFromStart` | Moving backward at position 0 leaves the position at 0. |
| TC-CA-6 | `testStartPositionOutsideBound` | Construction rejects positions below 0 or above 500 with `IllegalArgumentException`. |
| TC-CA-7 | `TestLastMeterAccepted` | Moving forward from 499 reaches the final position, 500. |
| TC-CA-8 | `TestScenarioOne` | Exercises a full two-sensor parking flow: park, unpark, move between spaces, and park at the end of the street. |
| TC-CA-9 | `TestScenarioTwo` | Extends the full parking flow with backward movement from 500 to 439, then checks that the car can park again. |
| TC-CA-10 | `TestScenarioThree` | With both mock sensors broken, parking is unsuccessful and the search reaches position 500. |
| TC-CA-11 | `scenario4WithMockito_twoParkingPlacesOneTooSmall` | Skips a three-meter space that is too small, selects the later five-meter space, and parks at position 54. |
| TC-CA-12 | `TestScenarioOneSensorBroken` | Checks that parking can succeed at position 254 using the working sensor when the other sensor is broken. |

## ParkingAssistantTest: Phase 2

### `MoveForward`

| ID | Test | Coverage |
|---|---|---|
| TC-MF-8 | `testMoveForwardRefusedByActuatorDoesNotReadSensor` | If the actuator refuses movement, the reported position stays at 250, the actuator receives one command, and neither sensor is read. |
| TC-MF-9 | `testMoveForwardSendsOneCommandToActuator` | A successful forward move sends one actuator command, reports position 11, and records meter 11 as free from the mocked sensor reading. |

### `isEmpty`

| ID | Test | Coverage |
|---|---|---|
| TC-IE-10 | `testIsEmptyReadsMockedSensors` | With both sensors returning 150, `isEmpty()` returns 150 and reads each sensor five times. |
| TC-IE-11 | `testOverUpperBoundaryRecgonizedAsBroken` | Rejects sensor 2 readings of 999 and uses sensor 1's valid median, 130. |
| TC-IE-12 | `testUnderLowerBoundaryRecgonizedAsBroken` | Rejects sensor 2 readings of -1 and uses sensor 1's valid median, 130. |
| TC-IE-13 | `testOnOverUpperBoundaryRecgonizedAsBroken` | Checks readings at 200; the expected result, 165, averages that boundary reading with sensor 2's median of 130. |
| TC-IE-14 | `testOnLowerBoundaryRecgonizedAsValid` | Checks readings at the lower boundary, 0; averages them with sensor 1's median of 130 to get 65. |
| TC-IE-15 | `testOneOverUpperBoundaryRecgonizedAsValid` | Includes one reading of 201 among readings of 200; expects 130, the other sensor's median. |
| TC-IE-16 | `testOneUnderLowerBoundaryRecgonizedAsBroken` | Includes one reading of -1 among readings of 0; expects 130, sensor 1's median. |
| TC-IE-17 | `testBothSensorsRecgonizedAsBroken` | When one sensor returns values below the lower bound and the other above the upper bound, `isEmpty()` returns 0. |

**Coverage note:** In `CarActuatorTest`, the position assertions verify the movement results. The `assertTrue(true)` and `assertFalse(false)` statements in several movement tests are constant checks and do not independently verify a return value.
