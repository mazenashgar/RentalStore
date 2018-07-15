package Project4;

import java.io.Serializable;

public class myDoublelyLinkedList<T> implements Serializable {


    /** Long used in serialization process */
    private static final long serialVersionUID = 1L;

    private DNode<T> tail;

    private DNode<T> head;

    private int counter;

    public myDoublelyLinkedList() {

        this.tail = null;
        this.head = null;
    }

    public void clear() {

        this.tail = null;
        this.head = null;
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

    public void addFirst(T t) {
        DNode<T> temp = new DNode(t);

        if (this.head == null) {
            addLast(t);
        } else if (this.tail == null) {
            this.head.setPrevious(temp);
            temp.setPrevious(null);
            temp.setNext(head);
            this.tail= this.head;
            this.head = temp;

        }else {
            this.head.setPrevious(temp);
            temp.setPrevious(null);
            temp.setNext(head);

            this.head = temp;
        }
    }

    public void add(int index, T t) {
        if (index < getSize()) {
            DNode<T> temp = head;
            DNode<T> insert = new DNode(t);
            int count = 0;

            if (getSize() == 0) {
                addLast(t);
            } else if (index == 0) {
                addFirst(t);
            } else {

                while (count < index) {
                    temp = temp.next();
                    count++;
                }

                temp.previous().setNext(insert);
                temp.setPrevious(insert);
                insert.setPrevious(temp.previous()); //
                insert.setNext(temp);
            }
        } else {
            addLast(t);
            System.out.println("added last");
        }
    }

    public void remove(T unit) {

        int index = find(unit);
        remover(index);
    }

    public void remover(int index) {
        DNode<T> temp = head;
        int count = 0;

        if (getSize() >= index) {
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

                    temp.previous().setNext(null);
                    tail = temp.previous();
                    temp.setPrevious(null);

                } else {

                    temp.previous().setNext(temp.next());

                    temp.next().setPrevious(temp.previous());

                }

            }
        }

    }

    public int find(T t) {

        DNode<T> temp = head;

        int count = 0;

        while (temp.next() != null) {

            if (temp.getData() == t) {
                return count;
            }
            temp = temp.next();
            count++;
        }
        if (temp.getData() == t) {
            return count;

        } else {

            return -1;
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


    public boolean removeAll(T t) {
        boolean removeT = false;;
        while(find(t) != -1) {
            remover(find(t));
            removeT=true;

        }

        return removeT;
    }

    public DNode<T> getTail() {
        return tail;
    }

    public void setTail(DNode<T> tail) {
        this.tail = tail;
    }

    public DNode<T> getHead() {
        return head;
    }

    public void setHead(DNode<T> head) {
        this.head = head;
    }

    // TODO: delete this method
    public void testRemoveData() {
        String a = "a1";
        String b = "b2";

        myDoublelyLinkedList<String> chester = new myDoublelyLinkedList<String>();
        chester.addLast(a);
        chester.addLast(b);
        chester.addLast(a);
        chester.addLast(a);
        chester.addLast(a);
        chester.addLast(a);
        chester.addLast(a);
        chester.addLast(a);
        chester.addLast(a);
        chester.addLast(a);
        chester.addFirst(b);

        System.out.println(chester.getSize());

        chester.removeAll(a);

        DNode temp = chester.head;
        System.out.println(chester.head.getData());
        while (temp.next() != null) {
            temp = chester.head.next();
            System.out.println(temp.getData());

        }

        System.out.println(chester.getSize());
    }

    // TODO: delete this method
    public void testData() {
        String a = "0";
        String b = "1";
        String c = "2";
        String d = "3";
        String e = "4";
        String f = "5";
        String g = "6";
        String h = "7";
        String swap = "MAX";

        myDoublelyLinkedList<String> chester = new myDoublelyLinkedList<String>();
        chester.addLast(a);
        chester.addLast(b);
        chester.addLast(c);
        chester.addLast(d);
        chester.addLast(e);
        chester.addLast(f);
        chester.addLast(g);
        chester.addLast(h);
        chester.add(6, swap);

        System.out.println(chester.head.getData());
        while (chester.head.next() != null) {
            chester.head = (chester.head.next());
            System.out.println(chester.head.getData());
        }

    }

    // TODO: delete this method
    public static void main(String[] args) {
        myDoublelyLinkedList<String> a = new myDoublelyLinkedList<String>();
        //a.testData();
        a.testRemoveData();
    }
}
