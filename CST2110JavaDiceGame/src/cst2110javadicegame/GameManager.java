package cst2110javadicegame;

// A class which holds key variables for the game to run and key functions that help the game run smoothly.
public class GameManager {

    public int roundNumber = 1;
    public int playerThrowCount = 3;
    public boolean roundPresented = false;
    public boolean forfeit = false;
    boolean categorySelected = false;
    public String throwString = "First";
    private Turn whoseTurn; // Initialising whoseTurn to be a enum from the Turn enum.
    //private Set<String> sequenceSet = new HashSet<>();

    // An enum containing the two players.
    public enum Turn {
        PLAYERONE, // enum properties
        PLAYERTWO
    }

    // A constructor for the class to initialise the whoseTurn to PLAYERONE.
    public GameManager() {
        // setting the initial turn to PLAYERONE.
        this.whoseTurn = Turn.PLAYERONE;
    }

    // A method to return 'whoseturn'. this function is to be used by getCurrentTurnString() function.
    // This function is set to private as it wil not be used outside this class.
    private Turn retrieveFromTurn() {
        return whoseTurn;
    }

    // A function which is called to return who's turn it is as a String rather than the enum Turn.
    public String getCurrentTurnString() {
        if (retrieveFromTurn() == Turn.PLAYERONE) {
            return "Player One";
        } else {
            return "Player Two";
        }
    }

    // A function which is called to change the players turn.
    public void changeTurn() {
        // if its PLAYERONEs turn set turn as PLAYERTWO
        if (whoseTurn == Turn.PLAYERONE) {
            whoseTurn = Turn.PLAYERTWO;
        } else {
            // else set the turn as PLAYERONE
            whoseTurn = Turn.PLAYERONE;
        }
    }

    // A function to return a boolean, is the round number equal to 8 OR forfeit equal to true.
    public boolean gameLoop() {
        return roundNumber == 8 || forfeit == true;
    }

    // A function to return the players throw count
    public int throwsRemaining() {
        return playerThrowCount;
    }

    // A function to decrement the players throw count, which starts at 3.
    public void decrementThrows() {
        playerThrowCount--;
    }

    // A function which is called when a player inputs 'f' to forfeit the game.
    public String forfeitGameProcedure() {
        forfeit = true;
        changeTurn(); // Change turn to then print the winner.
        return "Game Ended!\n" + getCurrentTurnString() + " Wins via forfeit!";
    }

    // A function to print out the remaining throws to the user
    public void throwsRemainingOutput() {
        if (throwsRemaining() == 1) {
            System.out.println(throwsRemaining() + " throw remaining for this turn.");
        } else {
            System.out.println(throwsRemaining() + " throws remaining for this turn.");
        }
    }

    // A function to print out a game end statement, showing the players their final score and the winner.
    public void gameEndProcedure(int playerOneTotalScore, int playerTwoTotalScore) {
        if (playerTwoTotalScore < playerOneTotalScore) {
            System.out.println("The game has ended, Player One wins! and finished with a score of: " + playerOneTotalScore
                    + ". Player Two has finished with a total score of: " + playerTwoTotalScore);
        } else {
            System.out.println("The game has ended, Player Two wins! and finished with a score of: " + playerTwoTotalScore
                    + ". Player One has finished with a total score of: " + playerOneTotalScore);
        }
    }
}
