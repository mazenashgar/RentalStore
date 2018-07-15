package Project4;

import java.util.Date;
import java.util.GregorianCalendar;

/**********************************************************************
 * An abstract representation of a Game in a redbox machine. The Class
 * allows the users to set and get the date rented, due date, the
 * renter's name, Game title, and the game console. Game Extends the
 * DVD Class that implements the serialized class which allows it to
 * save serialized states.
 *
 * @author Mazen Ashgar and Max Carson
 * @version 6/30/2018
 *********************************************************************/
public class Game extends DVD {

    /** Represents the type of game console */
    private PlayerType player;

    /** Double that represents the cost of renting a game */
    private final double GAME_RENT_FEE = 5.00;

    /** Double that represents the cost of renting a game */
    private final double GAME_LATE_FEE = 10.00;

    /******************************************************************
     * A constructor for Game Class
     *****************************************************************/
    public Game() {}

    /******************************************************************
     * Constructor for the Game Class that allows the user to set the
     * rental date, due date, title of the game, renter's name,
     * and the console the game is played on.
     *
     * @param bought - the date the DVD was rented
     * @param dueBack - the date the date the DVD has to be returned by
     * @param title - the title of the DVD
     * @param name - the name of the person who rented the DVD
     * @param player - the console the player rented the game for
     *****************************************************************/
    public Game(Date bought, Date dueBack, String title, String name,
                PlayerType player) {

        setBought(bought);
        setDueBack(dueBack);
        setTitle(title);
        setNameOfRenter(name);
        setPlayer(player);
    }

    /******************************************************************
     * Method that gets player type or the console the game
     * was rented for.
     *
     * @return player - the console type (Xbox1, WiiU, PS4, Xbox360,
     * and NintendoSwitch)
     ******************************************************************/
    public PlayerType getPlayer() {
        return player;
    }

    /******************************************************************
     * Method that sets the player type or the console the game
     * was rented on.
     *
     * @param player - the console type (Xbox1, WiiU, PS4, etc)
     *****************************************************************/
    public void setPlayer(PlayerType player) {
        this.player = player;
    }

    /******************************************************************
     * A method that determines the final cost of the game rental.
     * The method charges an additional fee if the date returned is
     * after the due date.
     *
     * @param dateReturned - A gregorianCalandar object that represents
     * the date the game was returned.
     * @return total - A double that represents the cost of the rental
     ******************************************************************/
    public double getCost(GregorianCalendar dateReturned) {

        //The initial cost of renting a game
        double total = GAME_RENT_FEE;

        //Formats the date Returned
        DATE_FORMAT.setCalendar(dateReturned);

        //Creates an gregorianCalandar object
        GregorianCalendar dueDate = new GregorianCalendar();

        //Sets the the date with the date the game is due
        dueDate.setTime(getDueBack());

        //Adds a late fee if the game was returned after the due date
        if (dateReturned.after(dueDate)) {
            total += GAME_LATE_FEE;
        }

        //return the total due for the game
        return total;
    }
}
