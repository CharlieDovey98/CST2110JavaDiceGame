package cst2110javadicegame;

public class ScoreboardManager {

    /*GameManager gameManager = new GameManager();*/
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

    public int pOneSequenceScore; // this might have to be initialised to 0 becuase of the choice selector switch case
    public int pTwoSequenceScore; // same for this

    public int playerOneTotalScore = 0;
    public int playerTwoTotalScore = 0;

    public String pOneSequence = "NULL";
    public String pTwoSequence = "NULL";

    final String NULL = "NULL";
    final String MISSED = "MISSED";
    final String ACHIEVED = "ACHIEVED";

    public StringBuilder scoreboard = new StringBuilder();

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

    public String updateRow(String round, int pOneScoreAfterTurn, int pTwoScoreAfterTurn) {
        String space = "   ";
        if (pOneScoreAfterTurn == 0 && pTwoScoreAfterTurn == 0) {
            return String.format("| %-8s     |     %3s    |     %3s    |\n", round, space, space);
        } else if (pOneScoreAfterTurn > 0 && pTwoScoreAfterTurn == 0) {
            return String.format("| %-8s     |     %3d    |     %3s    |\n", round, pOneScoreAfterTurn, space);
        } else if (pOneScoreAfterTurn > 0 && pTwoScoreAfterTurn > 0) {
            return String.format("| %-8s     |     %3d    |     %3d    |\n", round, pOneScoreAfterTurn, pTwoScoreAfterTurn);
        } else if (pOneScoreAfterTurn == 0 && pTwoScoreAfterTurn > 0) {
            return String.format("| %-8s     |     %3s    |     %3d    |\n", round, space, pTwoScoreAfterTurn);
        } else {
            return "Scoreboard error 0001";
        }
    }

    /*private void playerOneTotalScoreCalculator(){
         playerOneTotalScore = pOneScoreOnes + pOneScoreTwos + pOneScoreThrees + pOneScoreFours + pOneScoreFives + pOneScoreSixes + pOneSequenceScore;
    }
    private void playerTwoTotalScoreCalculator(){
         playerTwoTotalScore = pTwoScoreOnes + pTwoScoreTwos + pTwoScoreThrees + pTwoScoreFours + pTwoScoreFives + pTwoScoreSixes + pTwoSequenceScore;
    }
     */
    private String totalScores() {
        String title = "Total:";
        return String.format("| %-6s       |     %3d    |     %3d    |\n", title, playerOneTotalScore, playerTwoTotalScore);
    }

    private String sequence(String playerOneSequence, String playerTwoSequence) {
        String title = "Sequence 20";
        String playerOneSequenceOutcome;
        String playerTwoSequenceOutcome;
        switch (playerOneSequence) {
            case NULL:
                playerOneSequenceOutcome = " ";
                break;
            case MISSED:
                playerOneSequenceOutcome = "0";
                pOneSequenceScore = 0;
                break;
            case ACHIEVED:
                playerOneSequenceOutcome = "20";
                pOneSequenceScore = 20;

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
                pTwoSequenceScore = 0;
                break;
            case ACHIEVED:
                playerTwoSequenceOutcome = "20";
                pTwoSequenceScore = 20;
                break;
            default:
                playerTwoSequenceOutcome = " ";
        }
        return String.format("| %-11s  |      %2s    |      %2s    |\n", title, playerOneSequenceOutcome, playerTwoSequenceOutcome);

    }
}
