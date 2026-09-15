public class CarStatus {
    private int position;
    private boolean parked;

    public CarStatus(int position, boolean parked) {
        this.position = position;
        this.parked = parked;
    }

    public int getPosition() {
        return position;
    }
    
    public boolean isParked() {
        return parked;
    }
}