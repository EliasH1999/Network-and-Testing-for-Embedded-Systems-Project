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

        if(position >= 50. && position <= 52){
            return 150;
        }

        if(position >= 250 && position <= 256){
            return 150;
        }

        if(position >= 450 && position <= 455){
            return 150;
        }
        return 50;
    }

}