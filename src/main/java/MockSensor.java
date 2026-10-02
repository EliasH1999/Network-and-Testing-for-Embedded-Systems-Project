public class MockSensor implements Sensor{

    private final boolean breakDown;

    public MockSensor(boolean breakDown){
        this.breakDown = breakDown;
    }

    @Override 
    public int getReading(int position){

        if (breakDown && position >= 249 ){
            return 999;
        }

        // Parking place 1: 20-22
        // 3 metres -> too small
        if (position >= 20 && position <= 22) {
            return 120;
        }

        if(position >= 50. && position <= 53){
            return 150;
        }

        if(position >= 250 && position <= 254){
            return 150;
        }

        if(position >= 450 && position <= 454){
            return 150;
        }
        return 50;
    }

}