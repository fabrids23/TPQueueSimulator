//Di Santo Fabrizio, Ferron Matias, Gonzalez Palermo Tomas
public class Client {

    private int startTimeInQueue;
    private int startTimeInCashier;
    private int timeToBeAttendedInQueue;

    public Client(int timeToBeAttendedInQueue) {
        this.timeToBeAttendedInQueue = timeToBeAttendedInQueue;
    }

    public int getStartTimeInQueue() {
        return startTimeInQueue;
    }

    public int getStartTimeInCashier() {
        return startTimeInCashier;
    }

    public int getTimeToBeAttendedInQueue() {
        return timeToBeAttendedInQueue;
    }

    public void saveStartTimeInQueue(int startTimeInQueue) {
        this.startTimeInQueue = startTimeInQueue;
    }

    public void saveStartTimeInCashier(int startTimeInCashier) {
        this.startTimeInCashier = startTimeInCashier;
    }
}
