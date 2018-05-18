import java.io.*;

public class Stats {
    private int duration;
    private int numberOfCashiers;
    private int numberOfClientsThatArrive;
    private int numberOfFullyAttendedClients;
    private int totalTimeFromQueueToCashier;
    private int numberOfClientsThatReachTheCashier;
    private int totalQueueLength;
    private int maxLength;
    private int lazyTime;
    private String fileOut;

    public Stats(int duration, int numberOfCashiers, String fileOut){
        this.duration = duration;
        this.numberOfCashiers = numberOfCashiers;
        this.fileOut = fileOut;
    }

    public String data() {
        String data = "Number of clients that arrive = " + numberOfClientsThatArrive + "\n";
        data += "Number of  fully attended clients = " + numberOfFullyAttendedClients + "\n";
        data += "Average time in queue = " + getAverageTimeInQueue() + "\n";
        data += "Average queue length = " + getAverageQueueLength() + "\n";
        data += "Max length a queue reach = " + maxLength + "\n";
        data += "Lazy time = " + lazyTime + "\n";
        return data;
    }

    public double getAverageTimeInQueue(){
        double averageTimeInQueue = ( (double) totalTimeFromQueueToCashier/ (double) numberOfClientsThatReachTheCashier);
        int averageTimeInQueueINT = (int) (averageTimeInQueue*10);
        return (double) averageTimeInQueueINT/10;
    }

    public int getAverageQueueLength(){
        return ((totalQueueLength/duration)/numberOfCashiers);
    }

    public void saveData(){
        File file = new File(fileOut);

        if(file.exists()){
            file.delete();
        }
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data());
            oos.close();
            fos.close();
        }catch (FileNotFoundException fileNotFoundEx){
            throw new RuntimeException("File could not be created.");
        }catch (IOException ioEx){
            throw new RuntimeException("IOException");
        }
    }


    public void aClientHasCome() {
        numberOfClientsThatArrive++;
    }

    public void aClientHasBeenFullyAttended(){
        numberOfFullyAttendedClients++;
    }

    public void addTimeFromQueueToCashier(int time){
        totalTimeFromQueueToCashier += time;
    }

    public void aClientHasReachTheCashier(){
        numberOfClientsThatReachTheCashier++;
    }

    public void addTotalQueueLenght(int length){
        totalQueueLength += length;
    }

    public void checkMaxLength(int length){
        if(length > maxLength){
            maxLength = length;
        }
    }

    public void addLazyTime(){
        lazyTime++;
    }
}
