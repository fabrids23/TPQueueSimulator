//Di Santo Fabrizio, Ferron Matias, Gonzalez Palermo Tomas
public interface IQueue<T> {
    T dequeue();
    void enqueue(T elem);
    boolean isEmpty();
    int size();

}
