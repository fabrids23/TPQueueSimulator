//Di Santo Fabrizio, Ferron Matias, Gonzalez Palermo Tomas
public class Cashier {
    private Queue<Client> queue;
    private Client client;

    public Cashier() {
        queue = new Queue<>();
    }

    public boolean isLazy() {
        if(client == null){
            return true;
        }
        return false;
    }

    public Queue<Client> getQueue() {
        return queue;
    }

    public boolean hasFinishedAttending(int time){
        return time - client.getStartTimeInCashier() == client.getTimeToBeAttendedInQueue();
    }

    public void receiveClient(Client client){
        if(isLazy()){
            this.client = client;
        }else{
            queue.enqueue(client);
        }
    }

    public boolean hasNextInQueue(){
        return queue.size() > 0;
    }

    public void receiveNext(){
        client = queue.dequeue();
    }

    public void noClients(){
        client = null;
    }

    public Client getClient() {
        return client;
    }
}
