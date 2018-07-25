package Project4;

import java.io.Serializable;

/**********************************************************************
 * A doubly linked list class used to create lists in the RentalStore
 * class. It Works by connecting data with the next and the previous
 * data held in the list.
 *
 * @author Mazen Ashgar and Max Carson
 * @version 7/25/2018
 *********************************************************************/
public class MyDoublyLinkedList<T> implements Serializable {

    /** Long used in serialization process */
    private static final long serialVersionUID = 1L;

    /** The last item in the list */
    private DNode<T> tail;

    /** The first item in the list */
    private DNode<T> head;

    /******************************************************************
     * The default constructor for the list that sets the head and tail
     * to null.
     *****************************************************************/
    public MyDoublyLinkedList() {
        this.tail = null;
        this.head = null;
    }

    /******************************************************************
     * Clears all the nodes in the list.
     ******************************************************************/
    public void clear() {

        // Removes all items on the list from the tail to the head
        for (int i = (getSize()); i >= 0; i--) {
            remover(i);
        }
    }

    /******************************************************************
     * Adds a node to the last position in the list. Updates the head
     * necessary and tail of the list .
     *
     * @param data - The data that you want to add to the
     * last place in the linked list.
     *****************************************************************/
    public void addLast(T data) {
        DNode<T> temp = new DNode(data);

        // Adds the first node to the list
        if (this.head == null) {
            this.head = temp;
            this.head.setPrevious(null);
            this.head.setNext(null);
            this.tail = this.head;
        }

        // Adds nodes after the first node
        else {

            // Adds the 2nd node in list and sets a new the tail
            if (head.next() == null) {
                this.tail = temp;
                head.setNext(tail);
                tail.setPrevious(head);
                tail.setNext(null);
            }

            // Adds a new node and sets the tail after the 1st index.
            else {
                this.tail.setNext(temp);
                temp.setPrevious(tail);
                this.tail = temp;
                tail.setNext(null);
            }
        }
    }

    /******************************************************************
     * Adds a node to the first index in the list. Updates heads and
     * tail of the list of the list if it is necessary.
     *
     * @param data - The data to be added to the first index.
     *****************************************************************/
    public void addFirst(T data) {

        //create a temp Node
        DNode<T> temp = new DNode(data);

		/*
		Calls the addLast method to add the node to the first position
		on the list
		*/
        if (this.head == null) {
            addLast(data);
        }

        // Adds a 2nd item to the front of the list
        else if (getSize() == 1) {
            this.head.setPrevious(temp);
            temp.setPrevious(null);
            temp.setNext(head);
            this.tail = this.head;
            this.head = temp;
        }

		/*
	    Adds a node to the beginning of the list when it's bigger
	    than 2 nodes.
	     */
        else {
            this.head.setPrevious(temp);
            temp.setPrevious(null);
            temp.setNext(head);
            this.head = temp;
        }
    }

    /******************************************************************
     * Adds a node to the chosen position in the list. Updates the
     * head and the tail of the list if necessary.
     *
     * @param data - The data that you want to add to the linked list.
     * @param index - The index where the data will be placed on
     * the list.
     *****************************************************************/
    public void add(int index, T data) {

        //Checks that the index is less than the size
        if (index < getSize()) {
            DNode<T> temp = head;
            DNode<T> insert = new DNode(data);
            int count = 1;

            // If the size is 0 calls the addLast method
            if (getSize() == 0) {
                addLast(data);
            }

            // If index is 0 add the data first
            else if (index == 0) {
                addFirst(data);
            }

            // Goes to the requested index and adds the data
            else {
                temp = temp.next();
                while (count < index) {
                    temp = temp.next();
                    count++;
                }
                insert.setPrevious(temp.previous());
                insert.setNext(temp);
                temp.previous().setNext(insert);
                temp.setPrevious(insert);
            }
        }

        //Adds the data last if the index is smaller than the size

        else {
            addLast(data);
        }
    }

    /******************************************************************
     * Removes the data at the index and updates the list.
     * @param index - The index of the item that will be removed.
     *****************************************************************/
    public void remover(int index) {

        DNode<T> temp = head;
        int count = 0;

        // Checks that the index is not out of bounds
        if (index < getSize()) {

			/*
			If the index is the first item on the list it updates the
			head
			*/
            if (index == 0) {

                // sets the head to the next item on the list
                if (getSize() > 1) {
                    this.head = this.head.next();
                    this.head.setPrevious(null);
                }
                // Sets the everything to null if the size is 1
                else {
                    this.head = null;
                    this.tail = null;
                }
            }

            // If the index is greater than one
            else {

                // Counts for requested index
                while (count < index) {
                    temp = temp.next();
                    count++;
                }

                /*
                  If the next is null gets rid of the current and
                  updates the tail
                 */
                if (temp.next() == null) {
                    tail = temp.previous();
                    tail.setNext(null);
                }

                // removes the current node.
                else {
                    temp.previous().setNext(temp.next());
                    temp.next().setPrevious(temp.previous());
                }
            }
        }
    }

    /******************************************************************
     * Removes All occurrences of the requested data. Returns true if
     * the data was removed. Returns false if nothing was removed.
     *
     * @param t - The data that needs to be removed
     * @return returns True if something was removed or
     * returns false if nothing was removed.
     *****************************************************************/
    public boolean removeAll(T t) {

        //declare a boolean
        boolean removed = false;

        /*
        Removes everything while the find method finds instances of
        the data
        */
        while(find(t) != -1) {
            remover(find(t));
            removed = true;
        }

        return removed;
    }

    /******************************************************************
     * Removes the data at the index and returns the data that
     * was removed.
     *
     * @param index - The integer index of the data you want to remove.
     * @return T - The data that was removed or null if nothing was
     * removed.
     *****************************************************************/
    public T remove(int index) {

        //Gets the data at the index
        T temp = get(index);

        //Removes the data at the index, if it is not null
        if( temp != null) {
            remover(index);
        }

        //Returns what was removed or null
        return temp;
    }

    /******************************************************************
     * Finds the first index of occurrence of the data or returns -1,
     * if no index is found.
     *
     * @return T data - the index of the requested data.
     * @return int - The index of the requested data.
     *****************************************************************/
    public int find(T data) {

        DNode<T> temp = head;

        int count = 0;

        // searches till the last index
        while (temp.next() != null) {

            //Returns the data if data was found
            if (temp.getData() == data) {
                return count;
            }
            temp = temp.next();
            count++;
        }

        // Checks the last index
        if (temp.getData() == data) {
            return count;
        }

        // Returns -1 if nothing was found.
        else {
            return -1;
        }
    }

    /******************************************************************
     * Counts the amount of data in the link and returns the size.
     * Returns 0 if the list is empty.
     *
     * @return int - The size of the list.
     *****************************************************************/
    public int getSize() {
        DNode<T> temp = this.head;
        int count = 1;

        // Returns 0 if the list is empty
        if (this.head == null) {
            return 0;
        } else {

            // Counts the list of the length
            while (temp.next() != null) {
                temp = temp.next();
                count++;
            }
        }
        return count;
    }

    /******************************************************************
     * Gets the data for the requested the index.
     *
     * @param index - The index for data you that will be retrieve.
     * @return T - The data of the at the requested index or returns
     * null.
     *****************************************************************/
    public T get(int index) {
        int count = 0;

        // Checks if the list is empty
        if (head != null) {

            DNode<T> temp = this.head;

            // Goes to selected data
            while (count < index) {
                temp = temp.next();
                count++;
            }

            return temp.getData();
        }

        //returns null if the head is empty
        return null;
    }

    /******************************************************************
     * Gets the tail of the list.
     * @return DNode<T> tail -The current DNode tail of the list.
     *****************************************************************/
    public DNode<T> getTail() {
        return tail;
    }

    /******************************************************************
     * Gets the head of the list.
     * @return DNode<T> head - current head of the list.
     *****************************************************************/
    public DNode<T> getHead() {
        return head;
    }
}
