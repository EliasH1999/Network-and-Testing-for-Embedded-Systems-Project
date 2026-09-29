public class MockSensor implements Sensor{

    private final boolean breakDown;

    public MockSensor(boolean breakDown){
        this.breakDown = breakDown;
    }

    @Override 
    public int getReading(int position){

        if (breakDown && position >= 250 ){
            return 999;
        }

        // Parking place 1: 20-22
        // 3 metres -> too small
        if (position >= 20 && position <= 22) {
            return 120;
        }

        // Parking place 2: 50-54
        // 5 metres -> enough for parking
        if (position >= 50 && position <= 54) {
            return 120;
        }

        // Parking place 3: 80-86
        // 7 metres -> enough for parking
        if (position >= 80 && position <= 86) {
            return 120;
        }

        // No parking place
        return 30;
    }

}