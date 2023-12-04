package cst2110javadicegame;

// A Class which holds the Scoreboard information and player scores for the game.
public class ScoreboardManager {

    // The initialisations of the players scores.
    // 0 is neutral, negative numbers mean the player scored nothing for that choice, and positive integers is the players score.
    // These are updated after each round when the player makes a choice and scores against that choice.
    public int pOneScoreOnes = 0;
    public int pTwoScoreOnes = 0;

    public int pOneScoreTwos = 0;
    public int pTwoScoreTwos = 0;

    public int pOneScoreThrees = 0;
    public int pTwoScoreThrees = 0;

    public int pOneScoreFours = 0;
    public int pTwoScoreFours = 0;

    public int pOneScoreFives = 0;
    public int pTwoScoreFives = 0;

    public int pOneScoreSixes = 0;
    public int pTwoScoreSixes = 0;

    // Two boolean variables to show whether the player has attempted a sequence or not.
    public boolean pOneSequenceAttempted = false;
    public boolean pTwoSequenceAttempted = false;

    // both players total scores which will be updated each round and shown on the scoreboard.
    public int playerOneTotalScore = 0;
    public int playerTwoTotalScore = 0;

    // Strings which will be updated when the player has attempted a sequence.
    public String pOneSequence = "NULL";
    public String pTwoSequence = "NULL";

    final String NULL = "NULL";
    final String MISSED = "MISSED";
    final String ACHIEVED = "ACHIEVED";

    // The initialisation of the StringBuilder scoreboard.
    public StringBuilder scoreboard = new StringBuilder();

    // A function which when called will reset the scoreboard, then call different functions to update and append to the scoreboard. Returning it once complete.
    public StringBuilder returnUpdatedScoreboard() {
        scoreboard.setLength(0);
        scoreboard.append("------------------------------------------\n");
        scoreboard.append("| Category     |  Player 1  |  Player 2  |");
        scoreboard.append("\n------------------------------------------\n");
        scoreboard.append(updateRow("Ones", pOneScoreOnes, pTwoScoreOnes));
        scoreboard.append("------------------------------------------\n");
        scoreboard.append(updateRow("Twos", pOneScoreTwos, pTwoScoreTwos));
        scoreboard.append("------------------------------------------\n");
        scoreboard.append(updateRow("Threes", pOneScoreThrees, pTwoScoreThrees));
        scoreboard.append("------------------------------------------\n");
        scoreboard.append(updateRow("Fours", pOneScoreFours, pTwoScoreFours));
        scoreboard.append("------------------------------------------\n");
        scoreboard.append(updateRow("Fives", pOneScoreFives, pTwoScoreFives));
        scoreboard.append("------------------------------------------\n");
        scoreboard.append(updateRow("Sixes", pOneScoreSixes, pTwoScoreSixes));
        scoreboard.append("------------------------------------------\n");
        scoreboard.append(sequence(pOneSequence, pTwoSequence));
        scoreboard.append("------------------------------------------\n");
        scoreboard.append(totalScores());
        scoreboard.append("------------------------------------------\n"); // method to update sequence and overall scores
        return scoreboard;
    }

    // A function to update a row of the scoreboard. 
    // This function is passed both players scores as parameters and will return a String.format showing the outcome of a players turn.
    public String updateRow(String round, int pOneScoreAfterTurn, int pTwoScoreAfterTurn) {
        String space = "   ";
        if (pOneScoreAfterTurn < 0 && pTwoScoreAfterTurn > 0) {
            return String.format("| %-8s     |     %3s    |     %3s    |\n", round, "0", pTwoScoreAfterTurn);
        } else if (pOneScoreAfterTurn > 0 && pTwoScoreAfterTurn < 0) {
            return String.format("| %-8s     |     %3s    |     %3s    |\n", round, pOneScoreAfterTurn, "0");
        } else if (pOneScoreAfterTurn < 0 && pTwoScoreAfterTurn == 0) {
            return String.format("| %-8s     |     %3s    |     %3s    |\n", round, "0", space);
        } else if (pOneScoreAfterTurn == 0 && pTwoScoreAfterTurn < 0) {
            return String.format("| %-8s     |     %3s    |     %3s    |\n", round, space, "0");
        } else if (pOneScoreAfterTurn == 0 && pTwoScoreAfterTurn == 0) {
            return String.format("| %-8s     |     %3s    |     %3s    |\n", round, space, space);
        } else if (pOneScoreAfterTurn > 0 && pTwoScoreAfterTurn == 0) {
            return String.format("| %-8s     |     %3d    |     %3s    |\n", round, pOneScoreAfterTurn, space);
        } else if (pOneScoreAfterTurn > 0 && pTwoScoreAfterTurn > 0) {
            return String.format("| %-8s     |     %3d    |     %3d    |\n", round, pOneScoreAfterTurn, pTwoScoreAfterTurn);
        } else if (pOneScoreAfterTurn == 0 && pTwoScoreAfterTurn > 0) {
            return String.format("| %-8s     |     %3s    |     %3d    |\n", round, space, pTwoScoreAfterTurn);
        } else if (pOneScoreAfterTurn < 0 && pTwoScoreAfterTurn < 0) {
            return String.format("| %-8s     |     %3s    |     %3s    |\n", round, "0", "0");
        } else {
            return "Scoreboard error 0001";
        }
    }

    // A function to return both players total scores as a String.
    private String totalScores() {
        String title = "Total:";
        return String.format("| %-6s       |     %3d    |     %3d    |\n", title, playerOneTotalScore, playerTwoTotalScore);
    }

    // A function called to update the scoreboard with the players sequence outcomes. 
    private String sequence(String playerOneSequence, String playerTwoSequence) {
        String title = "Sequence";
        String playerOneSequenceOutcome;
        String playerTwoSequenceOutcome;
        switch (playerOneSequence) {
            case NULL:
                playerOneSequenceOutcome = " ";
                break;
            case MISSED:
                playerOneSequenceOutcome = "0";
                break;
            case ACHIEVED:
                playerOneSequenceOutcome = "20";
                break;
            default:
                playerOneSequenceOutcome = " ";
        }
        switch (playerTwoSequence) {
            case NULL:
                playerTwoSequenceOutcome = " ";
                break;
            case MISSED:
                playerTwoSequenceOutcome = "0";
                break;
            case ACHIEVED:
                playerTwoSequenceOutcome = "20";
                break;
            default:
                playerTwoSequenceOutcome = " ";
        }
        return String.format("| %-11s  |      %2s    |      %2s    |\n", title, playerOneSequenceOutcome, playerTwoSequenceOutcome);
    }
}
