//Di Santo Fabrizio, Ferron Matias, Gonzalez Palermo Tomas
public class Queue<T> implements IQueue<T> {
    private List<T> queue;
    private int size;

    public Queue(){
        queue = new List<>();
    }

    public Queue(List<T> queue) {
        this.queue = queue;
    }

    public void enqueue(T elem){
        queue = queue.add(elem);
        size++;
    }

    public T dequeue(){
        if(isEmpty()){
            return null;
        }
        T elem = queue.getElem();
        queue = queue.getTail();
        size--;
        return elem;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size() == 0;
    }

}
