package Project4;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**********************************************************************
 * A class that creates a display for entering DVD objects into the
 * rentalStoreGUI and checks that the input is valid.
 *
 * @author Mazen Ashgar and Max Carson
 * @version 6/30/2018
 *********************************************************************/
public class RentDVDDialog extends Dialog implements ActionListener {

    /** Declares a DVD object*/
    private DVD unit;


    /******************************************************************
     * A constructor for the RentDVDDialog Class that creates a display
     * for user to enter a name, title, rental date and due date for a
     * DVD object.
     *
     *@param parent - A Jframe that holds the display
     *@param d - A DVD thats text will be displayed
     *****************************************************************/
    public RentDVDDialog(JFrame parent, DVD d) {

        // Calls parent class and create a 'modal' dialog
        super(parent, true);

        //Adds the title to the JFrame
        setTitle("Rent a DVD:");
        closeStatus = false;

        //Sets the size of the JFrame
        setSize(300, 300);

        //sets unit equal to d
        unit = d;

        // Prevent user from closing the window
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // Instantiate and display text fields
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(6, 2));

        //Creates a text panel to enter a renters name
        textPanel.add(new JLabel("Your Name:"));
        renterTxt = new JTextField("John Doe", 30);
        textPanel.add(renterTxt);

        //Creates a text panel for enter a  renters name
        textPanel.add(new JLabel("Title of DVD:"));
        titleTxt = new JTextField("Avengers", 30);
        textPanel.add(titleTxt);

        //Gets the current date
        Date date = Calendar.getInstance().getTime();

        //Creates a text panel for the  rental date
        textPanel.add(new JLabel("Rented on Date: "));
        rentedOnTxt = new JTextField(DATE_FORMAT.format(date), 30);
        textPanel.add(rentedOnTxt);

        //Creates a new calendar object
        Calendar c = Calendar.getInstance();

        //Adds a day to the current date for the due back date
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();

        //Creates a text panel for the  due date
        textPanel.add(new JLabel("Due Back Date: "));
        DueBackTxt = new JTextField(DATE_FORMAT.format(date),15);
        textPanel.add(DueBackTxt);

        //Adds the text panel to the ContPanel
        getContentPane().add(textPanel, BorderLayout.CENTER);

        // Instantiate display for buttons
        JPanel buttonPanel = new JPanel();
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        //Adds the OK button
        okButton = new JButton("OK");
        buttonPanel.add(okButton);
        okButton.addActionListener(this);

        //Adds the cancel button
        cancelButton = new JButton("Cancel");
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(this);

        //Sets the Jframe to visible
        setVisible(true);
    }

    /******************************************************************
     * A method used to determine what the program does when the user
     * clicks on the OK or cancel button.
     *
     * @param e - An actionEvent when users clicks on OK or cancel
     * button
     *****************************************************************/
    public void actionPerformed(ActionEvent e) {

        JButton clicked = (JButton) e.getSource();

        // If okay is clicked it checks the input information
        if (clicked == okButton) {

            //If the input is all correct, it saves input to a DVD
            closeStatus = inputCheck();

        }
        //Sets closeStatus to false and does not save the input
        else if(clicked == cancelButton){

            closeStatus = false;

            //Makes the dialog box close
            dispose();
        }
    }

    /******************************************************************
     * A method used to check the user inputed information. Displays
     * a variety of message associated with kind of error the user made.
     *
     * @return returns true if the user input is correct.
     *****************************************************************/
    private boolean inputCheck(){

        //Gets the user input
        String name = renterTxt.getText();
        String title = titleTxt.getText();
        String rentDate = rentedOnTxt.getText();
        String dueDate = DueBackTxt.getText();

        //Deletes any leading white space
        name = unit.DelLeadWhiteSpace(name);
        title = unit.DelLeadWhiteSpace(title);
        rentDate = unit.DelLeadWhiteSpace(rentDate);
        dueDate = unit.DelLeadWhiteSpace(dueDate);

        /* Returns false if the date rented is not correct or
        not formatted correctly
         */
        if(!checkDateRented(rentDate)){
            return false;
        }

        /*Returns false if the dueDate is after the rent date or not
         properly formatted
         */
        if(!checkDateDue(dueDate, rentDate)){
            return false;
        }

        //Returns false if the name is empty
        if(name.equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter renter's name",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;


        }

        //Returns false if the title is empty
        else if(title.equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter the title",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;


        }

        //Returns false if the rental date is empty
        else if(rentDate.equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter the date rented on",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;


        }

        //Returns false if the due date is empty
        else if(dueDate.equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter the due back date",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;


        }

        //Sets the DVD name and title
        else {

            unit.setNameOfRenter(name);
            unit.setTitle(title);

            //Declares a temp Date for setting the rental and due date
            Date temp;

            //tries to save the rental date
            try {

                //Parses and sets the rental date
                temp = DATE_FORMAT.parse(rentDate);
                unit.setBought(temp);

                /*Catches the  exceptions if it was unable to parse the
                rental date and returns false
                 */
            } catch (ParseException p) {
                JOptionPane.showMessageDialog(null,
                        "Rented on date is incorrect",
                        "ERROR", JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }

            //tries to save the due date
            try {

                //Parse and saves the due date
                temp = DATE_FORMAT.parse(dueDate);
                unit.setDueBack(temp);

                /*Catches the exceptions if it was unable to parse the
                 due date and returns false
                 */
            } catch (ParseException p) {
                JOptionPane.showMessageDialog(null,
                        "Due back date is incorrect",
                        "ERROR", JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }

            /*If all of the information is correct, makes the
             dialog disappear
             */
            dispose();

            //Returns true if input was correct
            return true;
        }
    }
}
