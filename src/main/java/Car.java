public class Car{
        
        private int position;
        // Parked is not defined yet, define it ass false in the initialization of the car object
        private boolean parked;

        public Car(int position, boolean parked) {

            this.position = position;
            this.parked = parked;

        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getPosition() {
            return position;
        }

        public boolean isParked() {
            return parked;
        }
    }