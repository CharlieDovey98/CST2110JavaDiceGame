package cst2110javadicegame;

import java.util.HashSet;
import java.util.Set;

public class GameManager {

    public boolean forfeit = false;
    public int roundNumber = 1;
    public int playerThrowCount = 3;
    public boolean roundPresented = false;
    public String throwString = "First";
    private Turn whoseTurn; // Initialising whoseTurn to be a enum from the Turn enum.
    private Set<String> sequenceSet;

    // /An enum containing the two players.
    public enum Turn {
        PLAYERONE,
        PLAYERTWO // enum properties
    }

    // A constructor for the class to initialise the whoseTurn to PLAYERONE.
    public GameManager() {
        // setting the initial turn to PLAYERONE.
        this.whoseTurn = Turn.PLAYERONE;
        sequenceSet = new HashSet<>();

    }

    // A method to return 'whoseturn'. this function is to be used by getCurrentTurnString() function.
    // This function is set to private as it wil not be used outside this class.
    private Turn retrieveFromTurn() {
        return whoseTurn;
    }

    public String getCurrentTurnString() {
        if (retrieveFromTurn() == Turn.PLAYERONE) {
            return "Player One";
        } else {
            return "Player Two";
        }
    }

    // A function to change players turn.
    public void changeTurn() {
        // if its PLAYERONEs turn set turn as PLAYERTWO
        if (whoseTurn == Turn.PLAYERONE) {
            whoseTurn = Turn.PLAYERTWO;
        } else {
            // else set the turn as PLAYERONE
            whoseTurn = Turn.PLAYERONE;
        }
    }

    public boolean gameLoop() {
        return roundNumber == 8 || forfeit == true;
    }

    public int throwsRemaining() {
        if (whoseTurn == Turn.PLAYERONE) {
            return playerThrowCount;
        } else {
            return playerThrowCount;
        }
    }

    public void decrementThrows() {
        playerThrowCount--;
    }

    public String forfeitGameProcedure() {
        System.out.println("exit game port 006");
        forfeit = true;
        // Change turn
        changeTurn();
        return "Game Exited\n" + getCurrentTurnString() + " Wins via forfeit!";
    }
}
