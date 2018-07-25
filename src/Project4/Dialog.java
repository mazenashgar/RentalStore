package Project4;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**********************************************************************
 * A class displays dialog boxes and checks the inputted dialog for
 * errors. The class extends the JDialog class.
 *
 * @author Mazen Ashgar and Max Carson
 * @version 7/25/2018
 *********************************************************************/
public class Dialog extends JDialog {

    /** JTextFeild used to display the title */
    protected JTextField titleTxt;

    /** JTextFeild used for display the renters name */
    protected JTextField renterTxt;

    /** JTextFeild used to display rented on date */
    protected JTextField rentedOnTxt;

    /** JTextFeild used to display due back date */
    protected JTextField DueBackTxt;

    /** JButton used to confirm inputed Dialog */
    protected JButton okButton;

    /** JButton used to confirm inputed Dialog */
    protected JButton cancelButton;

    /** Boolean used to check if dialog is correct */
    protected boolean closeStatus;

    /** SimpleDateFormat sets the how the date is formatted*/
    protected SimpleDateFormat DATE_FORMAT
            = new SimpleDateFormat("MM/dd/yyyy");

    /** ImageIcon holds the icon on the dialog boxes*/
    protected ImageIcon icon;

    /******************************************************************
     * Constructor for Dialog class
     *****************************************************************/
    public Dialog() {
    }

    /******************************************************************
     * Constructor for Dialog class that setup dialog boxes by calling
     * parent JDialog constructor.
     *
     * @param parent is a Jframe that displays dialog
     * @param modal boolean if true sets the modality to
     * DEFAULT_MODALITY_TYPE, OtherWise the dialog is modeless
     *****************************************************************/
    public Dialog(JFrame parent, boolean modal) {
        super(parent, modal);
        icon = new ImageIcon("rentalStore.png");
    }

    /******************************************************************
     * A method that Checks to see if the date rented is properly
     * formatted and that the date exists.
     *
     * @param dateRented - a string with the user entered rental date
     * @return true or false depending if the date rented is
     * correctly formatted and the date exists.
     * @throws NumberFormatException if the user entered a date
     * with more than "/"
     *****************************************************************/
    protected boolean checkDateRented(String dateRented) {

        /* String array list that splits the date into months, days and
        years
         */
        String[] s = dateRented.split("/");

        //Integers for the month, day, and year rented
        int monthRented;
        int dayRented;
        int yearRented;

        //Defines and initiates new calendar
        GregorianCalendar gc = new GregorianCalendar();

        // tries to parse integers from user entered date
        try {
            monthRented = Integer.parseInt(s[0]);
            dayRented = Integer.parseInt(s[1]);
            yearRented = Integer.parseInt(s[2]);

            if(s.length > 3){
                throw new NumberFormatException();
            }

            /* Catches when the user enters non-numbers and displays
            an error message
             */
        } catch (NumberFormatException e) {
            JOptionPane
                    .showMessageDialog(null,
                            "Please check your inputs" +
                                    "\nNOTE: "
                                    + "The dates can"
                                    + " only contain numbers"
                                    + "\n**No letters or "
                                    + "special characters**",
                            "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } catch (ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null,
                    "Please check your inputs", "Error",
                    JOptionPane.ERROR_MESSAGE, icon);
            return false;
        }

        // Returns false if the year rented is negative
        if (yearRented < 0) {
            JOptionPane.showMessageDialog(null, "Year rented on is"
                            + " incorrect", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            /* Returns false if the day is less than 1 or greater
            than 31
             */
        } else if (dayRented < 1 || dayRented > 31) {
            JOptionPane.showMessageDialog(null, "Day rented on is "
                            + "incorrect", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            /* Returns false if the month is less than 1 and greater than
            12
             */
        } else if (monthRented < 1 || monthRented > 12) {
            JOptionPane.showMessageDialog(null, "Month rented on is"
                            + " incorrect", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            /* Returns false if the month is April and if the day is
            greater than 30
            */
        } else if (monthRented == 4 && dayRented > 30) {
            JOptionPane.showMessageDialog(null, "April has only 30"
                            + " days", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            /* Returns false if the month is June and the day is
            greater than 30
            */
        } else if (monthRented == 6 && dayRented > 30) {
            JOptionPane.showMessageDialog(null, "June has only 30"
                            + " days", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            /* Returns false if the month is September and the day is
            greater than 30
            */
        } else if (monthRented == 9 && dayRented > 30) {
            JOptionPane.showMessageDialog(null, "September has only"
                            + " 30 days", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            /* Returns false if the month is November and has the day is
            greater than 30
            */
        } else if (monthRented == 11 && dayRented > 30) {
            JOptionPane.showMessageDialog(null, "November has only"
                            + " 30 days", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            // Returns false if the year is not a leap year
        } else if (!gc.isLeapYear(yearRented)) {

            /* Returns false if the month is February and if the number
            of days is greater than 28
            */
            if (monthRented == 2 && dayRented > 28) {
                JOptionPane.showMessageDialog(null, "February has only"
                                + " 28 days on " + yearRented, "ERROR",
                        JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }

            // Checks if the year is a leap year
        } else if (gc.isLeapYear(yearRented)) {

            /* Returns false if the month is February and if the number
            of days is greater than 29
            */
            if (monthRented == 2 && dayRented > 29) {
                JOptionPane.showMessageDialog(null, "February has only"
                                + " 29 days on " + yearRented, "ERROR",
                        JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }
        }

        // Returns true if the date is properly formated and exists
        return true;
    }

    /******************************************************************
     * A method that Checks to see if the date rented is properly
     *formatted and the date exists. It also checks that the date
     *rented is not before the due date.
     *
     * @param dateDue - A String with that entered due date
     * @param dateRented - A string the entered date rented
     * @return true or false depending on if the date rented is before
     * the due date, the due date is properly formatted, and the date
     * exists.
     * @throws NumberFormatException if the user entered a date
     * with more than "/"
     *****************************************************************/
    protected boolean checkDateDue(String dateDue, String dateRented) {

        // String array that split the date by checking for slashes
        String[] s = dateDue.split("/");
        int monthDue;
        int dayDue;
        int yearDue;

        GregorianCalendar gc = new GregorianCalendar();

        // Tries to parse the integers from the date inputted
        try {
            monthDue = Integer.parseInt(s[0]);
            dayDue = Integer.parseInt(s[1]);
            yearDue = Integer.parseInt(s[2]);

            if(s.length > 3){
                throw new NumberFormatException();
            }

            /* Catches an errors when the user enters non-numbers and
             displays an error message
            */
        } catch (NumberFormatException e) {
            JOptionPane
                    .showMessageDialog(null,
                            "Please check your inputs" + "\nNOTE: The "
                                    + "dates can only contain numbers"
                                    + "\n**No letters or special"
                                    + " characters**",
                            "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;
        } catch (ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(null,
                    "Please check your inputs", "Error",
                    JOptionPane.ERROR_MESSAGE, icon);
            return false;
        }

        // boolean used to check if date rented is after date returned
        boolean mistake = false;

        // Returns false if the year is negative and displays an error
        if (yearDue < 0) {
            JOptionPane.showMessageDialog(null, "Year due back is "
                            + "incorrect", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            /* Returns false if the month is less than 1 or greater than 12
            and displays an error message
            */
        } else if (monthDue < 1 || monthDue > 12) {
            JOptionPane.showMessageDialog(null, "Month due back is"
                            + " incorrect", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            /* Returns false if the day is less than one or greater than 31
            and displays an error message
            */
        } else if (dayDue < 1 || dayDue > 31) {
            JOptionPane.showMessageDialog(null, "Day due back is "
                            + "incorrect", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            /* Returns false the month is April and the day is greater than
             30 and displays an error message
            */
        } else if (monthDue == 4 && dayDue > 30) {
            JOptionPane.showMessageDialog(null, "April has only 30 "
                            + "days", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            /* Returns false the month is June and the day is greater than
             30 and displays an error message
            */
        } else if (monthDue == 6 && dayDue > 30) {
            JOptionPane.showMessageDialog(null, "June has only 30 days"
                    , "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;

            /* Returns false if the month is September and the day is
             greater than 30 and displays an error message
            */
        } else if (monthDue == 9 && dayDue > 30) {
            JOptionPane.showMessageDialog(null, "September has only 30"
                            + " days", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            /* Returns false if the month is November and day is greater
             than 30 and displays an error message
            */
        } else if (monthDue == 11 && dayDue > 30) {
            JOptionPane.showMessageDialog(null, "November has only 30 "
                            + "days", "ERROR", JOptionPane.ERROR_MESSAGE,
                    icon);
            return false;

            // Checks that is not a leap year
        } else if (!gc.isLeapYear(yearDue)) {

            /* Returns false if the month is February and day is greater
             than 28 and displays an error message
            */
            if (monthDue == 2 && dayDue > 28) {
                JOptionPane.showMessageDialog(null, "February has only"
                                + " 28 days on " + yearDue, "ERROR",
                        JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }

            // Checks if it is a leap year
        } else if (gc.isLeapYear(yearDue)) {

            /* Returns false if the month is February and day is
            greater than 29 and displays an error message
            */
            if (monthDue == 2 && dayDue > 29) {
                JOptionPane.showMessageDialog(null, "February has "
                                + "only 29 days on "+ yearDue, "ERROR"
                        , JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }
        }

        // Instantiates dueDate and rentDate
        Date dueDate;
        Date rentDate;

        // Tries to parse the input for due date and return date
        try {
            dueDate = DATE_FORMAT.parse(dateDue);
            rentDate = DATE_FORMAT.parse(dateRented);

            // returns false if it cannot parse the dates
        } catch (ParseException e) {
            return false;
        }

        // checks if rent date is after the due date
        if (rentDate.after(dueDate)) {
            mistake = true;
        }

        // returns false if rent date is after the due date
        if (mistake) {
            JOptionPane.showMessageDialog(null, "Due date can't be"
                            + " before rented date", "ERROR",
                    JOptionPane.ERROR_MESSAGE, icon);
            return false;
        }

        /* returns true if rent date is after the due date and the date
        is properly formatted
        */
        else {
            return true;
        }
    }

    /******************************************************************
     * A method that returns closeStatus
     *
     * @return closeStatus - boolean that returns true or false
     * depending on whether the inputs are correct.
     *****************************************************************/
    public boolean closeOK() {
        return closeStatus;
    }
}
