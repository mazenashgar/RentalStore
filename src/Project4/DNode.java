package Project4;

import java.io.Serializable;

public class DNode<T> implements Serializable {
    private DNode next;
    private DNode previous;
    private T data;

    public DNode(T data) {

        this.data = data;
    }

    public DNode(T data, DNode next, DNode previous) {

        this.data = data;
        this.next = next;
        this.previous = previous;
    }

    public DNode next() {
        return next;
    }

    public void setNext(DNode next) {
        this.next = next;
    }

    public DNode previous() {
        return previous;
    }

    public void setPrevious(DNode previous) {
        this.previous = previous;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}