package Project4;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*****************************************************************
 *
 *
 * @author Mazen Ashgar and Max Carson
 * @version 6/30/2018
 *****************************************************************/
public class RentalStore extends AbstractTableModel {

    private myDoubleLinkedList<DVD> listDVDs;

    private myDoubleLinkedList<DVD[]> undoList;//changed

    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    private String [] colNames = {"Names", "Title", "Rented On", "Due Date", "Console"};

    public RentalStore() {
        super();
        listDVDs = new myDoubleLinkedList<DVD>();

        undoList = new myDoubleLinkedList<DVD[]>();

    }

    public void add(DVD a) {

        this.undoList.addLast(listDVDs.getArray(listDVDs));//changed

        this.listDVDs.addLast(a);

        fireTableRowsInserted(0, listDVDs.getSize());

    }

    public void addSpecial(DVD a) {

        listDVDs.addLast(a);
        fireTableRowsInserted(0, listDVDs.getSize());
    }

    public DVD get(int i) {
        return listDVDs.get(i);
    }

    public void update() {
        fireTableRowsUpdated(0, listDVDs.getSize());
    }

    public void remove(int index, DVD unit) {

        this.undoList.addLast(listDVDs.getArray(listDVDs));//changed

        this.listDVDs.remover(index);


        fireTableRowsDeleted(0, listDVDs.getSize());

    }

    private String linePrinter(int index) {

        DVD unit = listDVDs.get(index);

        String line = "Name: " + listDVDs.get(index).getNameOfRenter() + ",\t\t";

        if (unit instanceof Game) {
            line += "Game: ";
        } else {
            line += "DVD: ";
        }

        line += listDVDs.get(index).getTitle() + ",\t\t" + "Rented: "
                + (DATE_FORMAT.format(listDVDs.get(index).getBought())) + ",\t\t" + "Due Date: "
                + (DATE_FORMAT.format(listDVDs.get(index).getDueBack()));

        if (unit instanceof Game)
            line += ",\t\t" + "Console: " + ((Game) unit).getPlayer();

        return line;
    }

    public void saveAsSerializable(String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(listDVDs);
            os.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error in saving db");

        }
    }

    public void loadFromSerializable(String filename) {

        undoList = new myDoubleLinkedList<DVD[]>();//changed


        try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream is = new ObjectInputStream(fis);

            listDVDs = (myDoubleLinkedList<DVD>) is.readObject();

            fireTableRowsInserted(0, listDVDs.getSize());
            is.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error in loading file");
        }
    }

    public void saveAsText(String filename) {

        int index = 0;
        PrintWriter out = null;

        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

            while (index < listDVDs.getSize()) {
                out.println(linePrinter(index));
                index++;
            }
            out.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error in saving text to" + filename);

        }
    }

    public void loadFromText(String filename) {

        Scanner fileReader;
        listDVDs = new myDoubleLinkedList<DVD>();
        undoList = new myDoubleLinkedList<DVD[]>();//changed


        String temp = null;
        String[] s;

        try {
            fileReader = new Scanner(new File(filename));

            while (fileReader.hasNextLine()) {

                temp = fileReader.nextLine();
                s = temp.split("\t\t");

                String name = s[0].substring(6, s[0].length() - 1);
                String title;
                if (s[1].substring(0, 5).equals("Game:")) {
                    title = s[1].substring(6, s[1].length() - 1);
                } else {
                    title = s[1].substring(5, s[1].length() - 1);
                }
                String rentDate = s[2].substring(8);
                String dueDate = s[3].substring(10);

                if (s.length == 4) {
                    DVD dvd = new DVD(DATE_FORMAT.parse(rentDate), DATE_FORMAT.parse(dueDate), title, name);
                    addSpecial(dvd);

                } else if (s.length == 5) {
                    String player = s[4].substring(9);
                    Game game = new Game(DATE_FORMAT.parse(rentDate), DATE_FORMAT.parse(dueDate), title, name,
                            PlayerType.valueOf(player));
                    addSpecial(game);
                }

                update();

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error in load text from" + filename);
        }

        if (temp == null) {
            JOptionPane.showMessageDialog(null, "Error in load text from" + filename);
        }

    }

    public boolean findLate(String lateOn, ArrayList lateList) throws ParseException {

        // check if date is valid
        String[] lateOnDate = lateOn.split("/");
        int lateOnMonth = Integer.parseInt(lateOnDate[0]);
        int lateOnDay = Integer.parseInt(lateOnDate[1]);
        int lateOnYear = Integer.parseInt(lateOnDate[2]);

        GregorianCalendar gc = new GregorianCalendar();

        if (lateOnYear < 1) {
            return false;
        } else if (lateOnMonth > 12 || lateOnMonth < 1) {
            return false;
        } else if (!gc.isLeapYear(lateOnYear)) {
            if (lateOnMonth == 2 && lateOnDay > 28) {
                return false;
            }
        } else if (gc.isLeapYear(lateOnYear)) {
            if (lateOnMonth == 2 && lateOnDay > 29) {
                return false;
            }
        } else if ((lateOnMonth == 4 || lateOnMonth == 6 || lateOnMonth == 9 || lateOnMonth == 11) && lateOnDay > 30) {
            return false;
        } else if (lateOnDay > 31 || lateOnDay < 1) {
            return false;
        }

        Date lateDate = DATE_FORMAT.parse(lateOn);
        long diff;
        String info;

        for (int i = 0; i < listDVDs.getSize(); i++) {

            if (lateDate.after(listDVDs.get(i).getDueBack())) {

                diff = (lateDate.getTime() - listDVDs.get(i).getDueBack().getTime()) / (1000 * 60 * 60 * 24);
                info = linePrinter(i);
                lateList.add(i, "" + diff + " Day(s) late for:" + info.substring(5));
            }
        }

        return true;

    }

    public int getUndoSize (){
        return undoList.getSize();
    }

    public String checkWhiteSpace(String string) {

        DVD unit = new DVD();
        return unit.DelLeadWhiteSpace(string);
    }


    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public int getRowCount() {
        return listDVDs.getSize();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (col == 0) {
            return listDVDs.get(row).getNameOfRenter();
        }
        if (col == 1) {
            return listDVDs.get(row).getTitle();

        }
        if (col == 2) {
            return DATE_FORMAT.format(listDVDs.get(row).getBought());
        }
        if (col == 3) {
            return DATE_FORMAT.format(listDVDs.get(row).getDueBack());
        }
        if (col == 4) {
            if (listDVDs.get(row) instanceof Game){
                return ((Game) listDVDs.get(row)).getPlayer().toString();
            } else {
                return "DVD";
            }
        }
        return null;

    }

    public void loadDVDs(DVD[] list) {
        listDVDs.clear();
        for (int i = 0; i < list.length; i++) {
            addSpecial(list[i]);
        }
    }

    public void undo() {
        if (undoList.getSize() > 0) {
            loadDVDs(undoList.get( undoList.getSize() -1));
            undoList.remover(undoList.getSize() - 1);
        } else {
            undoList = new myDoubleLinkedList<DVD[]>() ;
        }
    }
}

