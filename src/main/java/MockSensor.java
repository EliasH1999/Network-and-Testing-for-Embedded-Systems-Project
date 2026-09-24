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

        // Street layout Går att hårdkoda 

        return 0;
    }

}