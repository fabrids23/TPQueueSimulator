public class List<T> {
    T elem;
    List<T> tail;

    public List() {
        this(null,null);
    }

    private List(T elem, List<T> tail) {
        this.elem = elem;
        this.tail = tail;
    }

    public T getElem() {
        return elem;
    }

    public List<T> getTail() {
        return tail;
    }

    public List<T> add(T elem) {
        if(this.elem == null){
            return new List<>(elem,this);
        }
        this.tail = tail.add(elem);
        return this;
    }
}
