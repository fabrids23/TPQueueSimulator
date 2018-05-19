import java.io.*;

public class Simulation extends Thread{
    private int numberOfQueues;
    private int duration;
    private double probabilityOfAClientToAppear;
    private int minTimeToAttend;
    private int maxTimeToAttend;
    private Cashier[] cashiers;
    private Stats stats;
    private Time time;

    public Simulation(String fileIn, String fileOut){
        create(fileIn);
        cashiers = new Cashier[numberOfQueues];
        for (int i = 0; i < numberOfQueues ; i++) {
            Cashier cashier = new Cashier();
            cashiers[i] = cashier;
        }
        stats = new Stats(duration, numberOfQueues, fileOut);
        time = new Time();
        evaluate();
    }

    private void evaluate() {
        if(numberOfQueues <= 0
                || duration <= 0
                || probabilityOfAClientToAppear <= 0
                || probabilityOfAClientToAppear > 1
                || minTimeToAttend <= 0
                || minTimeToAttend > maxTimeToAttend) {
            throw new IllegalArgumentException();
        }
    }

    private void create(String fileIn) {
        String executionPath = "";
        try {
            FileReader fileReader = new FileReader(fileIn);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            executionPath = System.getProperty("user.dir");
            executionPath.replace("\\", File.pathSeparator);
            executionPath = bufferedReader.readLine();
            if (executionPath != null) {
                try {
                    numberOfQueues = Integer.parseInt(executionPath);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException();
                }
            }

            executionPath = bufferedReader.readLine();
            if (executionPath != null) {
                try {
                    duration = Integer.parseInt(executionPath);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException();
                }
            }

            executionPath = bufferedReader.readLine();
            if (executionPath != null) {
                try {
                    probabilityOfAClientToAppear = Double.parseDouble(executionPath);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException();
                }
            }

            executionPath = bufferedReader.readLine();
            if (executionPath != null) {
                try {
                    String[] str = executionPath.split(" ");
                    minTimeToAttend = Integer.parseInt(str[0]);

                    for (int i = 1; i < str.length; i++) {
                        if (!str[i].equals("")) {
                            maxTimeToAttend = Integer.parseInt(str[i]);
                        }
                    }
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException();
                }
            }
            bufferedReader.close();
        } catch (IllegalArgumentException ex) {
            System.out.println("Illegal arguments in " + fileIn);
            System.exit(1);
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file " + fileIn);
        } catch (IOException ex) {
            System.out.println("Error reading file " + fileIn);
        }
    }

    public void nextIteration(){
        time.advanceTime();
        int actualTime = time.getCurrentTime();
        QueueAssignor queueAssignor = new QueueAssignor(this);

        if(hasAClientCome()){
            int timeToBeAttended = (int) (Math.random() * (maxTimeToAttend - minTimeToAttend)) + minTimeToAttend;
            Client client = new Client(timeToBeAttended);
            if(queueAssignor.existsAnEmptyCashier()){
                client.saveStartTimeInCashier(time.getCurrentTime());
            }else{
                client.saveStartTimeInQueue(time.getCurrentTime());
            }
            queueAssignor.enqueue(client, cashiers[queueAssignor.getPositionOfLeastFullQueue()]);
            stats.aClientHasCome();
        }

        for (Cashier cashier : cashiers) {
            if (!cashier.isLazy()) {
                if(cashier.hasFinishedAttending(actualTime)){
                        stats.aClientHasBeenFullyAttended();
                    if(cashier.hasNextInQueue()){
                        cashier.receiveNext();
                        cashier.getClient().saveStartTimeInCashier(actualTime);
                        stats.aClientHasReachTheCashier();
                        stats.addTimeFromQueueToCashier(actualTime - cashier.getClient().getStartTimeInQueue());
                    }else{
                        cashier.noClients();
                    }
                    stats.addTotalQueueLenght(cashier.getQueue().size());
                    stats.checkMaxLength(cashier.getQueue().size());
                }
            }else{
                stats.addLazyTime();
            }
        }
    }

    public boolean hasAClientCome(){
        return Math.random() <= probabilityOfAClientToAppear;
    }

    public Cashier[] getCashiers() {
        return cashiers;
    }

    public int getNumberOfQueues() {
        return numberOfQueues;
    }

    public int getDuration() {
        return duration;
    }

    public Stats getStats(){
        return stats;
    }

    public Time getTime() {
        return time;
    }
}
