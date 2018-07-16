package Project4;

import java.io.Serializable;

public class myDoubleLinkedList<T> implements Serializable {


    /** Long used in serialization process */
    private static final long serialVersionUID = 1L;

    private DNode<T> tail;

    private DNode<T> head;

    public myDoubleLinkedList() {

        this.tail = null;
        this.head = null;
    }

    public void clear() {
        for(int i = (getSize()); i>=0; i-- ) {
            remover(i);
        }

    }

    public void addLast(T Data) {
        DNode<T> temp = new DNode(Data);

        // Adds the first node
        if (this.head == null) {
            this.head = temp;
            this.head.setPrevious(null);
            this.head.setNext(null);
            this.tail = this.head;

        } else {
            if (head.next() == null) {

                this.tail = temp;
                head.setNext(tail);
                tail.setPrevious(head);
                tail.setNext(null);

            } else {
                this.tail.setNext(temp);
                temp.setPrevious(tail);
                this.tail = temp;
                tail.setNext(null);

            }

        }

    }

    public void remover(int index) {
        DNode<T> temp = head;
        int count = 0;

        if ( index < getSize()) {
            if (index == 0) {
                if (getSize() > 1) {
                    this.head = this.head.next();
                    this.head.setPrevious(null);

                } else {
                    this.head = null;

                }

            } else {

                while (count < index) {
                    temp = temp.next();
                    count++;
                }
                if (temp.next() == null) {



                    tail = temp.previous();
                    tail.setNext(null);



                    //temp.previous().setNext(null);
                    //tail = temp.previous();
                    //temp.setPrevious(null);

                } else {

                    temp.previous().setNext(temp.next());

                    temp.next().setPrevious(temp.previous());

                }

            }
        }

    }

    public int getSize() {
        DNode<T> temp = this.head;
        int count = 1;

        if (this.head == null) {
            return 0;
        } else {
            while (temp.next() != null) {
                temp = temp.next();
                count++;
            }
        }
        return count;
    }

    public T get(int i) {
        int count = 0;

        DNode<T> temp = this.head;
        while (count < i) {
            temp = temp.next();
            count++;
        }

        return temp.getData();

    }

    public DNode<T> getHead() {
        return head;
    }

    public DVD[] getArray(myDoubleLinkedList list) {
        DVD[] array = new DVD[list.getSize()];

        DNode<T> temp = list.getHead();
        for(int i = 0; i<list.getSize(); i++ ) {
            array[i] = (DVD) temp.getData();
            System.out.println(array[i]);
            temp = temp.next();

        }
        return array;
    }
}
