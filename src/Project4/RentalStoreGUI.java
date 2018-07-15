package Project4;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**********************************************************************
 * The graphical interface for the rental store. This class allows user to rent
 * and return games and DVDs through a graphical interface. It allows the users
 * to Save and load lists of games and DVD from serialized files. It allows the
 * user to view how late the DVDs and games listed on the GUI are on a user
 * inputed date.
 *
 * @author Mazen Ashgar and Max Carson
 * @version 6/30/2018
 *********************************************************************/
public class RentalStoreGUI extends JFrame implements ActionListener {

    /** JMenuBar Used to hold the fileMenu and the action menu */
    private JMenuBar menus;

    /** JMenu used to hold items in the file menu */
    private JMenu fileMenu;

    /** JMenu used to Hold items in the action menu */
    private JMenu actionMenu;

    /** JMenuItem used to undo previous action */
    private JMenuItem undo;

    /** JMenuItem used to opens serialized files */
    private JMenuItem openSerItem;

    /** JMenuItem used to exits the program */
    private JMenuItem exitItem;

    /** JMenuItem used to saves serialized files */
    private JMenuItem saveSerItem;

    /** JMenuItem used to opens texts files */
    private JMenuItem openTextItem;

    /** JMenuItem used to saves texts files */
    private JMenuItem saveTextItem;

    /** JMenuItem used to open up the DVD rental dialog box */
    private JMenuItem rentDVD;

    /** JMenuItem used to opens up the game rental dialog box */
    private JMenuItem rentGame;

    /** JMenuItem used to open up the return dialog box */
    private JMenuItem returnItem;

    /** JMenuItem used to opens up the find late dialog box */
    private JMenuItem findLateItem;

    /** Holds the list engine */
    private RentalStore list;

    /** Used for the Scroll pane */
    private JScrollPane scrollList;

    /** Hold the icon for the rental store */
    private ImageIcon icon;

    private JTable table;

    private Object[] colNames = {"Names", "Title", "Rented On", "Due Date", "Console"};

    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    private final int rowCount = 50;
    private final int colCount = 5;

    private String[][] data = new String[rowCount][colCount];

    /******************************************************************
     * Constructor for the RentalStoreGUI that creates the menus and list area for
     * DVD and Games to be displayed.
     *****************************************************************/
    public RentalStoreGUI() {

        // Icon used in the dialog boxes
        icon = new ImageIcon("rentalStore.png");

        // Creates menu bar
        menus = new JMenuBar();

        // Creates menus
        fileMenu = new JMenu("File");
        actionMenu = new JMenu("Action");

        // Creates menus items for the file menu
        undo = new JMenuItem("Undo");

        openSerItem = new JMenuItem("Open Serial");
        exitItem = new JMenuItem("Exit");
        saveSerItem = new JMenuItem("Save Serial");
        openTextItem = new JMenuItem("Open Text");
        saveTextItem = new JMenuItem("Save Text");

        // Creates menus items for the action menu
        rentDVD = new JMenuItem("Rent DVD");
        rentGame = new JMenuItem("Rent Game");
        returnItem = new JMenuItem("Return");
        findLateItem = new JMenuItem("Find late");

        // Adds items to menu bar
        menus.add(fileMenu);
        menus.add(actionMenu);

        // Adds item to the file bar

        fileMenu.add(undo);
        fileMenu.add(openTextItem);
        fileMenu.add(openSerItem);
        fileMenu.add(saveTextItem);
        fileMenu.add(saveSerItem);
        fileMenu.add(exitItem);

        // Adds items to the menu bar
        actionMenu.add(rentDVD);
        actionMenu.add(rentGame);
        actionMenu.add(findLateItem);
        actionMenu.add(returnItem);

        // Adds actionListener for the file menu
        undo.addActionListener(this);

        openTextItem.addActionListener(this);
        openSerItem.addActionListener(this);
        saveTextItem.addActionListener(this);
        saveSerItem.addActionListener(this);
        exitItem.addActionListener(this);

        // Adds actionListener for the action menu
        rentDVD.addActionListener(this);
        rentGame.addActionListener(this);
        findLateItem.addActionListener(this);
        returnItem.addActionListener(this);

        // Sets the Menu bar to menus
        setJMenuBar(menus);

        // Sets the menu to close on exit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add a rental store object to the GUI
        list = new RentalStore();
        updateList();

        table = new JTable (data , colNames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scrollList = new JScrollPane(table);
        add(scrollList);

        // Sets the title
        setTitle("Rental Store");

        // Set the JFrame to visible
        setVisible(true);

        // Sets the default size
        setSize(700, 800);

        undo.setEnabled(false);
    }

    /******************************************************************
     * Action perform menu that determines what happens when the menu items are
     * clicked on.
     *
     * @param e
     *            - the ActionEvent item for the menu items clicked on.
     *****************************************************************/
    public void actionPerformed(ActionEvent e) {

        // Holds what was the item clicked on
        Object comp = e.getSource();

        // If the open serialized menu was clicked on it will opens up a
        // file chooser
        if (openSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);

            // When the user chooses a file it saves the file name to a
            // string
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();

                // This opens the user selected file
                if (openSerItem == comp)
                    list.loadFromSerializable(filename);
            }
        }

        //TODO: comment
        if (undo == comp) {
            list.undo();
        }

        // If the open text menu was clicked on it will opens up a
        // file chooser
        if (openTextItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);

            // When the user chooses a file it saves the file name to a
            // string
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();

                // This opens the user selected file
                if (openTextItem == comp)
                    list.loadFromText(filename);
            }
        }

        // If the user clicks on the save serialized file menu item
        // it prompts the user to choose a location to save file
        if (saveSerItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);

            // This saves the user selected file name to a string
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();

                // This saves list to the user selected file
                if (saveSerItem == e.getSource())
                    list.saveAsSerializable(filename);
            }
        }

        // If the user clicks on save text file menu item it
        // prompts the user to choose a location to save file
        if (saveTextItem == comp) {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showSaveDialog(null);

            // This saves the user selected file name to a string
            if (status == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getAbsolutePath();

                // This saves list to the user selected file
                if (saveTextItem == e.getSource())
                    list.saveAsText(filename);
            }
        }

        // If the user selects exits, the program exits
        if (comp == exitItem) {
            System.exit(1);
        }

        // If the user clicks rent DVD the rent DVD dialog is shown
        if (e.getSource() == rentDVD) {
            DVD dvd = new DVD();
            RentDVDDialog dialog = new RentDVDDialog(this, dvd);

            // This adds the DVD to the list if the input is correct
            if (dialog.closeOK()) {
                list.add(dvd);
            }
        }

        // If the user clicks rent game the rent game dialog is shown
        if (comp == rentGame) {
            Game game = new Game();
            RentGameDialog dialog = new RentGameDialog(this, game);

            // This adds the game to the list if the input is correct
            if (dialog.closeOK()) {
                list.add(game);
            }
        }

        // If the user clicks find late items the find late method shows
        // how many the DVD and games are
        if (comp == findLateItem) {
            lateUnits();
        }

        // Prompts the user to give a return date and returns items if
        // the date is correct and valid.
        if (comp == returnItem) {
            returnUnit();
        }

        if(list.getUndoSize() == 0){
            undo.setEnabled(false);
        }else{
            undo.setEnabled(true);
        }

        updateList();
        list.update();
    }

    /******************************************************************
     * A method that Finds out if lists items are late on a user inputted date and
     * displays how late the DVD items and game items are on that date.
     *****************************************************************/
    private void lateUnits() {

        // Creates an array for DVD and Games
        ArrayList<String> lateList = new ArrayList<>();

        // Prompts the user to enter a date
        String lateOnDate = (String) JOptionPane.showInputDialog(null,
                "Please enter a date to find the late units on it" + "\nPlease use the following format: MM/DD/YYYY",
                "Late Units Finder", JOptionPane.QUESTION_MESSAGE, icon, null, null);

        // Creates a new dialog box
        Dialog dialog = new Dialog();

        // Checks that the user input is not null
        if (lateOnDate != null) {

            // Tries to get the dates for the late units
            try {

                // Deletes any leading white space in the list items
                lateOnDate = list.checkWhiteSpace(lateOnDate);

                // Checks that the date is correctly formatted
                if (dialog.checkDateRented(lateOnDate)) {

                    // Displays an error if the dates are invalid
                    if (!list.findLate(lateOnDate, lateList)) {

                        JOptionPane.showMessageDialog(null, "Please enter a valid date to find " + "late units",
                                "Error", JOptionPane.ERROR_MESSAGE, icon);
                        lateUnits();

                        // Displays a message if there are no late units
                    } else if (lateList.size() == 0) {

                        JOptionPane.showMessageDialog(null, "No units are late on " + lateOnDate, "Error",
                                JOptionPane.INFORMATION_MESSAGE, icon);

                        // Displays a the list of late units
                    } else {
                        JOptionPane.showMessageDialog(null, new JList(lateList.toArray()), "Late Units",
                                JOptionPane.INFORMATION_MESSAGE, icon);
                    }

                    // Prompt user to re-input dates if the date is faulty
                } else {
                    lateUnits();
                }

                // Catches an Exception and displays a message and prompts
                // the user to enter a new date
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid date to find late units", "Error",
                        JOptionPane.ERROR_MESSAGE, icon);
                lateUnits();
            }

            // Returns the method if the date was not valid
        } else {
            return;
        }
    }

    /******************************************************************
     * A Method that allows users to return a DVD or game. It accepts rentals as
     * long as the date returned is acceptable. It then displays the final cost of
     * the rental. With a late fee added if the return date is after the due date.
     *****************************************************************/
    private void returnUnit() {

        // Creates a new GregorianCalendar object used for checking the
        // dates
        GregorianCalendar date = new GregorianCalendar();

        // Prompts the user to enter a return date
        String inputDate = (String) JOptionPane.showInputDialog(null,
                "Enter return date: " + "\nPlease use the following format: MM/DD/YYYY", "Return",
                JOptionPane.QUESTION_MESSAGE, icon, null, null);

        // Used to formats the user entered date
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        NumberFormat numFormatter = NumberFormat.getCurrencyInstance(Locale.US);

        // Creates a new Dialog box
        Dialog dialog = new Dialog();

        // Tries to get the date
        try {

            // Check that the date is not null
            if (inputDate != null) {
                inputDate = list.checkWhiteSpace(inputDate);

                // Checks the date rented is valid
                if (dialog.checkDateRented(inputDate)) {
                    Date newDate = df.parse(inputDate);
                    date.setTime(newDate);

                    // Prompts the user to a new date if date is invalid
                } else {
                    returnUnit();
                    return;
                }
            }

            // Returns the method if no date was entered
            else {
                return;
            }

            // Catches when the program cannot parse the date, prompts the
            // user the to enter a valid date
        } catch (ParseException pe) {
            JOptionPane.showMessageDialog(null, "Please enter a valid date to return", "Error",
                    JOptionPane.ERROR_MESSAGE, icon);
            returnUnit();
        }

        // tries to return the game or DVD
        try {
            //int index = JListArea.getSelectedIndex();
            int index = table.getSelectedRow();

            // Displays if there are no items selected, index = -1
            if(index == -1){

                JOptionPane.showMessageDialog(null, "Please select a unit to return it", "Error", JOptionPane.ERROR_MESSAGE,
                        icon);

            }else {
                DVD unit = list.get(index);

                // Checks the return date is valid and removes the item from
                // the list and thanks the customer and displays the cost
                if (unit.checkReturnDate(inputDate)) {

                    double cost = unit.getCost(date);

                    JOptionPane.showMessageDialog(null,
                            "Thanks " + unit.getNameOfRenter() + " for returning " + unit.getTitle() + "\nYou owe: "
                                    + numFormatter.format(cost) + " dollars",
                            "Returned", JOptionPane.INFORMATION_MESSAGE, icon);

                    list.remove(index, unit);
                    //updateList();
                    //list.update();

                    // Displays an error if there is a problem with the return date
                } else {
                    JOptionPane.showMessageDialog(null, "Return date is invalid or it is before the rented on Date",
                            "Error", JOptionPane.ERROR_MESSAGE, icon);
                    returnUnit();
                }
            }

        }catch (NumberFormatException n) {

            // Return the method if there is a problem with how the date is
            // formatted
            return;
        }
    }

    public void updateList() {


        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {

                    data[row][col] = "";
            }
        }


        for (int row = 0; row < list.getSize(); row++) {
            for (int col = 0; col < colCount; col++) {

                if (col == 0)
                    data[row][col] = list.get(row).getNameOfRenter();
                if (col == 1)
                    data[row][col] = list.get(row).getTitle();

                if (col == 2)
                    data[row][col] = DATE_FORMAT.format(list.get(row).getBought());
                if (col == 3)
                    data[row][col] = DATE_FORMAT.format(list.get(row).getDueBack());

                if (col == 4) {
                    if (list.get(row) instanceof Game) {
                        data[row][col] = ((Game) list.get(row)).getPlayer().toString();
                    } else {
                        data[row][col] = "";
                    }
                }
            }
        }
    }

    /******************************************************************
     * A static Method that launches the RentalStoreGUI
     *****************************************************************/
    public static void main(String[] args) {
        new RentalStoreGUI();
    }
}
