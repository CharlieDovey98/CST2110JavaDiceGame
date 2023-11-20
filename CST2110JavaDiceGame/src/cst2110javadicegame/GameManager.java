package cst2110javadicegame;

public class GameManager {
    
    // The instantiation of objects to help the game run.
    //ScoreboardManager scoreManager = new ScoreboardManager();
    //DiceManager diceManager = new DiceManager();

    public boolean GamePrompt = true;
    public boolean forfeit = false;
    public int roundNumber = 1;
    public int playerOneThrowCount = 3;
    public int playerTwoThrowCount = 3;
    public boolean roundPresented = false;
    public String throwNumber = "First";
    private Turn whoseTurn; // Initialising whoseTurn to be a enum from the Turn enum.

    // /An enum containing the two players.
    public enum Turn {
        PLAYERONE,
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

    public boolean startGamePrompt() {
        return GamePrompt == true && forfeit == false;
    }

    public boolean gameLoop() {
        return roundNumber == 7 || forfeit == true;
    }

    public int throwsRemaining() {
        if (whoseTurn == Turn.PLAYERONE) {
            return playerOneThrowCount;
        } else {
            return playerTwoThrowCount;
        }
    }

    public void decrementThrows() {
        if (whoseTurn == Turn.PLAYERONE) {
            playerOneThrowCount--;
        } else {
            playerTwoThrowCount--;

        }
    }

    public String forfeitGameProcedure() {
        forfeit = true;
        // Change turn
        changeTurn();
        return "Game Exited\n" + getCurrentTurnString() + " Wins via forfeit!";
    }
    
    
    

}
