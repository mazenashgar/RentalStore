package Project4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**********************************************************************
 * A class that creates a display for entering Game objects into the
 * rentalStoreGUI and checks that the input is valid.
 *
 * @author Mazen Ashgar and Max Carson
 * @version 6/30/2018
 *********************************************************************/
public class RentGameDialog  extends Dialog implements ActionListener {

    /** Game object used in saving player entered game Info*/
    private Game unit;

    /** The console the renter rented the game for*/
    private PlayerType player;

    /** Options of player types to choose from */
    private JComboBox playerOptions;


    /******************************************************************
     * A constructor for the RentGameDialog Class that creates a
     * display for user to enter a name, title, rental date, due date,
     * and player type for a Game object.
     *
     *@param parent - A Jframe that holds the display
     *@param d - A Game thats text will be displayed
     ******************************************************************/
    public RentGameDialog(JFrame parent, Game d) {

        // Calls the parent class and create a 'modal' dialog
        super(parent, true);

        // Set the Jframe title
        setTitle("Rent a Game:");

        // Sets closeStatus to false
        closeStatus = false;

        // Sets the size of the JFrame
        setSize(300, 300);

        // Sets unit to d
        unit = d;

        // Prevent user from closing window
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        // Instantiate and display text fields
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(6, 2));

        // Creates a text panel to enter a renters name
        textPanel.add(new JLabel("Your Name:"));
        renterTxt = new JTextField("John Doe", 30);
        textPanel.add(renterTxt);

        // Creates a text panel to enter a game title
        textPanel.add(new JLabel("Title of Game:"));
        titleTxt = new JTextField("Super Mario III", 30);
        textPanel.add(titleTxt);

        // Creates a JComboBox to choose the console rented on
        textPanel.add(new JLabel("Player Type;;"));
        playerOptions = new JComboBox<Enum>(PlayerType.values());
        textPanel.add(playerOptions);

        // Creates a text panel to enter the rental date
        Date date = Calendar.getInstance().getTime();
        textPanel.add(new JLabel("Rented on Date: "));
        rentedOnTxt = new JTextField(DATE_FORMAT.format(date), 30);
        textPanel.add(rentedOnTxt);

        // Adds one to the current date for the due date
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);  // number of days to add
        date = c.getTime();

        // Creates a text panel to enter the due date
        textPanel.add(new JLabel("Due Back Date: "));
        DueBackTxt = new JTextField(DATE_FORMAT.format(date),15);
        textPanel.add(DueBackTxt);

        //Adds the text panels to the content panel
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
    @Override
    public void actionPerformed(ActionEvent e) {

        JButton clicked = (JButton) e.getSource();

        // If the okay button is clicked it checks the input
        if (clicked == okButton) {

            /*if the input is correct, it saves input to the game
             object
             */
            closeStatus = inputCheck();

        }

        //If the cancel button is clicked it cancel the input
        else if(clicked == cancelButton){

            //Sets closeStatus to false and does not save the input
            closeStatus = false;

            //Close the dialog box
            dispose();
        }
    }

    /******************************************************************
     * A method used to check the user inputed information. Displays
     * a variety of message associated with kind of error the user made
     *
     * @return returns true if the user input is correct.
     *****************************************************************/
    private boolean inputCheck(){

        //get the game info from the text fields
        String name = renterTxt.getText();
        String title = titleTxt.getText();
        String rentDate = rentedOnTxt.getText();
        String dueDate = DueBackTxt.getText();

        //deletes any leading white space
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

        /*Returns false if the name is empty and displays an error
         message
         */
        if(name.equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter renter's name",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;


        }

        /*Return false if the game title is empty and displays an
         error message
         */
        else if(title.equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter the title",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;


        }

        /*Returns false if the date is empty and displays an
         error message
         */
        else if(rentDate.equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter the date rented on",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;


        }

        /*Returns false if the due date is empty and displays an
         error message
         */
        else if(dueDate.equals("")){

            JOptionPane.showMessageDialog(null,
                    "Please enter the due back date",
                    "ERROR", JOptionPane.ERROR_MESSAGE, icon);
            return false;


        }

        //Sets the renters name and the game title
        else {

            unit.setNameOfRenter(name);
            unit.setTitle(title);
            player = (PlayerType) playerOptions.getSelectedItem();
            unit.setPlayer(player);

            Date temp;

            //tries to set the rental date
            try {
                temp = DATE_FORMAT.parse(rentDate);
                unit.setBought(temp);

                //Catches error if the it cannot parse the date
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null,
                        "Date rented on is incorrect",
                        "ERROR", JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }

            //tries to set the due date
            try {
                temp = DATE_FORMAT.parse(dueDate);
                unit.setDueBack(temp);

                /*Catches exception if unable to parse date and
                 displays error
                 */
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null,
                        "Due back date is incorrect",
                        "ERROR", JOptionPane.ERROR_MESSAGE, icon);
                return false;
            }

            //close the dialog box if the input is correct
            dispose();

            //returns true if the input is correct
            return true;
        }
    }
}
