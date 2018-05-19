//Di Santo Fabrizio, Ferron Matias, Gonzalez Palermo Tomas
public class QueueAssignor {
    private Simulation simulation;

    public QueueAssignor(Simulation simulation){
        this.simulation = simulation;
    }

    public int getPositionOfLeastFullQueue(){
        Cashier[] tmp = getSimulation().getCashiers();
        int positionOfLeastFullQueue = 0;
        if(existsAnEmptyCashier()) {
            for (int i = 0; i < tmp.length; i++) {
                if(tmp[i].isLazy()){
                    return i;
                }
            }
        }else {
            for (int i = 1; i < tmp.length; i++) {
                if (tmp[i].getQueue().size() < tmp[positionOfLeastFullQueue].getQueue().size()) {
                    positionOfLeastFullQueue = i;
                }
            }
        }
        return positionOfLeastFullQueue;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void enqueue(Client client, Cashier cashier){
        cashier.receiveClient(client);
    }

    public boolean existsAnEmptyCashier() {
        Cashier[] tmp = getSimulation().getCashiers();
        for (Cashier cashier : tmp) {
            if(cashier.isLazy()){
                return true;
            }
        }
        return false;
    }
}
