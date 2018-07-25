package Project4;

import java.io.Serializable;

/**********************************************************************
 * A class that creates nodes for a doubly linked list with a piece of
 * data and pointers to the next and previous items the List.
 *
 * @author Mazen Ashgar and Max Carson
 * @version 7/25/2018
 *********************************************************************/
public class DNode<T> implements Serializable {

    /** Long used in the serialization process */
    private static final long serialVersionUID = 1L;

    /** A pointer to the next DNode on the list */
    private DNode<T> next;

    /** A pointer to the previous DNode on the list */
    private DNode<T> previous;

    /** The data stored on the node */
    private T data;

    /******************************************************************
     * Constructor for the DNode class. Works by adding in a data to a
     * node and then setting the next and previous items with setters.
     *
     * @param data - The data that is to be added to the mode.
     *****************************************************************/
    public DNode(T data) {

        this.data = data;
    }

    /******************************************************************
     * Returns the pointer for the next DNode on the list.
     *****************************************************************/
    public DNode<T> next() {
        return next;
    }

    /******************************************************************
     * Sets the pointer for the next item on the list.
     *
     * @param next - The DNode that will be next in the list.
     *****************************************************************/
    public void setNext(DNode<T> next) {
        this.next = next;
    }

    /******************************************************************
     * Returns the pointer for the previous node on the list.
     *
     * @return previous - The previous DNode in the list.
     *****************************************************************/
    public DNode<T> previous() {
        return previous;
    }

    /******************************************************************
     * Sets the pointer for the previous node on the list.
     *
     * @param  previous - The DNode<T> that will come before on
     * the list.
     *****************************************************************/
    public void setPrevious(DNode<T> previous) {
        this.previous = previous;
    }

    /******************************************************************
     * Returns the data held in the DNode
     *****************************************************************/
    public T getData() {
        return data;
    }
}