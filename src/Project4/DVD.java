package Project4;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**********************************************************************
 * An abstract representation of a DVD in a redbox machine. The class
 * allows the users to set and get the date rented, due date, the
 * renter's name, and the DVD title. DVD implements the serialized
 * class which allows it to save serialized states.
 *
 * @author Mazen Ashgar and Max Carson
 * @version 6/30/2018
 *********************************************************************/
public class DVD implements Serializable {

    /** Long used in serialization process */
    private static final long serialVersionUID = 1L;

    /** Sets the format for date */
    protected SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("MM/dd/yyyy");

    /** The date the DVD was rented */
    private Date bought;

    /** The date the DVD is due back */
    private Date dueBack;

    /** The title of the DVD */
    private String title;

    /** The name of the person who is renting the DVD */
    private String nameOfRenter;

    /** Final double price to rent a DVD */
    private final double DVD_RENT_FEE = 1.20;

    /** Final double late fee for a DVD */
    private final double DVD_LATE_FEE = 2.00;


    /******************************************************************
     * Constructor for the DVD Class
     *****************************************************************/
    public DVD() {
    }

    /******************************************************************
     * Constructor for the DVD Class that allows the user to set the
     * bought date, due date, title and renter's name for a DVD.
     *
     * @param bought - the date the DVD was rented
     * @param dueBack - the date the date the DVD has to be returned by
     * @param title - the title of the DVD
     * @param name - the name of the person who rented the DVD
     *****************************************************************/
    public DVD(Date bought, Date dueBack, String title, String name){
        super();
        this.bought = bought;
        this.dueBack = dueBack;
        this.title = title;
        this.nameOfRenter = name;
    }

    /******************************************************************
     * Method that returns when the DVD was rented.
     *
     * @return bought - the date DVD was rented
     *****************************************************************/
    public Date getBought() {
        return bought;
    }

    /******************************************************************
     * Method that sets the date rented.
     *
     * @param bought - the date the DVD was rented
     *****************************************************************/
    public void setBought(Date bought) {
        this.bought = bought;
    }

    /******************************************************************
     * Method that returns when the DVD is due.
     *
     * @return dueback - the date the DVD is due by to avoid a fee.
     *****************************************************************/
    public Date getDueBack() {
        return dueBack;
    }

    /******************************************************************
     * A Method that sets when the DVD is due back by.
     *
     * @param dueBack - sets the date the DVD is due.
     *****************************************************************/
    public void setDueBack(Date dueBack) {
        this.dueBack = dueBack;
    }

    /******************************************************************
     * A Method that returns the DVD title's name.
     *
     * @return title - A string with the title of the DVD.
     *****************************************************************/
    public String getTitle() {
        return title;
    }

    /******************************************************************
     * Method that sets the title of the DVD
     *
     * @param title - sets the name of the DVD
     *****************************************************************/
    public void setTitle(String title) {
        this.title = title;
    }

    /******************************************************************
     * Method that gets the name of the renter.
     *
     * @return nameOfRenter - name of the person who rented the DVD
     *****************************************************************/
    public String getNameOfRenter() {
        return nameOfRenter;
    }

    /******************************************************************
     * Method that sets the name of the renter.
     *
     * @param nameOfRenter - name of the person who rented the DVD
     *****************************************************************/
    public void setNameOfRenter(String nameOfRenter) {
        this.nameOfRenter = nameOfRenter;
    }

    /******************************************************************
     * Method that returns the cost for a DVD rental and adds a fee if
     * the DVD is returned late.
     *
     * @param dateReturned - the date the DVD was returned
     * @return total - The double cost of the rental
     *****************************************************************/
    public double getCost(GregorianCalendar dateReturned) {

        double total = DVD_RENT_FEE;

        DATE_FORMAT.setCalendar(dateReturned);

        GregorianCalendar dueDate = new GregorianCalendar();
        dueDate.setTime(dueBack);

        //Adds a late fee if the date is passed the due date
        if (dateReturned.after(dueDate)){
            total += DVD_LATE_FEE;
        }

        return total;
    }

    /******************************************************************
     * A method that checks if the return date is valid date and
     * the return date is after the due date.
     *
     * @param dateReturned - The date the DVD was brought back
     * @return boolean that returns true or false depending on whether
     * the date is valid and the return date is before the due date
     *****************************************************************/
    protected boolean checkReturnDate(String dateReturned){

        String [] returnedDate = dateReturned.split("/");

        int monthReturned = Integer.parseInt(returnedDate[0]);
        int dayReturned = Integer.parseInt(returnedDate[1]);
        int yearReturned = Integer.parseInt(returnedDate[2]);

        //Returns false if year is negative or 0
        if(yearReturned < 1){
            return false;
        }

        //Returns false if the month is greater than 12 or less than 1
        else if(monthReturned > 12 || monthReturned < 1){
            return false;

        }

        //Returns false if the day is greater than 31 or less than 1
        else if(dayReturned > 31 || dayReturned < 1){
            return false;
        }

        //Creates new GregorianCalendar objects
        GregorianCalendar calendarReturn = new GregorianCalendar();
        GregorianCalendar calendarBought = new GregorianCalendar();

        //Declaration the return Date
        Date returnDate;

        //Tries to parse the date returned
        try {
            returnDate = DATE_FORMAT.parse(dateReturned);


        }

        //Returns false if the program cannot parse the date returned
        catch (ParseException e){
            return false;
        }

        //Sets the GregorianCalendarto the return and bought date
        calendarReturn.setTime(returnDate);
        calendarBought.setTime(bought);

        //Returns false if the return date is after the rented on date
        if(calendarBought.after(calendarReturn)){
            return false;
        }

        /*Returns true if the date is valid and the return date is
        after the rented on date
         */
        return true;
    }

    /******************************************************************
     * A method that uses recursion to delete all the white space
     * in the beginning of a string
     *
     * @param string - a String that you want to get rid of the leading
     * white space on
     *****************************************************************/
    public String DelLeadWhiteSpace (String string){

        //Returns the string if the string is empty
        if(string.equals("")){
            return string;
        }

        //Deletes all leading white space using recursion
        if(string.charAt(0) == ' '){
            string = DelLeadWhiteSpace(string.substring(1));
        }

        //Returns a string without leading white space
        return string;
    }
}


