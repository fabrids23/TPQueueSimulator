public interface IQueue<T> {
    T dequeue();
    void enqueue(T elem);
    boolean isEmpty();
    int size();

}
