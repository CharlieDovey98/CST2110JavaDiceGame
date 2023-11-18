package cst2110javadicegame;

import java.util.Scanner;

// This class holds the main game code and will call on other classes for their respective responsibilities.
public class DiceGame {

    // The instantiation of objects to help the game run.
    GameManager gameManager = new GameManager();
    ScoreboardManager scoreManager = new ScoreboardManager();
    ValidityManager validityManager = new ValidityManager();

    // A "start" function to hold the game itself.
    public void start() {
        Scanner sc = new Scanner(System.in);

        System.out.println("This is a two player, Strategic dice game."
                + "\nPlayers will take it in turns to roll 5 dice, choosing a die number and scoring as many points as possible by attaining the max number of that die."
                + "\nThe highest scoring player wins the game!");
        while (gameManager.startGamePrompt()) {
            System.out.print("Play game (1) or Exit game (0) > ");

            String playGame = sc.next();
            if (validityManager.startGameIsValid(playGame)) {

                if ("0".equals(playGame)) {
                    gameManager.forfeit = true;
                    System.out.println("Game Exited");
                } else {
                    System.out.println("\n" + scoreManager.returnUpdatedScoreboard());
                    gameManager.GamePrompt = false;

                }
            } else {
                System.out.println("Not a valid input.");
            }
        }

        while (gameManager.gameLoop() == false) {
            System.out.println("---------\nRound " + gameManager.roundNumber + " |\n---------\n");
            System.out.print("First throw of this turn, " + gameManager.getCurrentTurn() + " to throw 5 dice.\nThrow 5 dice, enter 't' to throw or 'f' to forfeit > ");
            String input = sc.next();
            String inputLower = input.toLowerCase();
            if (validityManager.throwForfeitInputIsValid(inputLower)) {
                if ("f".equals(inputLower)) {
                    gameManager.forfeit = true;
                    // Change turn
                    gameManager.changeTurn();
                    System.out.println("Game Exited\n" + gameManager.getCurrentTurn() + " Wins via forfeit!");
                } else if ("t".equals(inputLower)) {
                    //System.out.println("\n" + scoreManager.returnUpdatedScoreboard());
                    gameManager.decrementThrows();

                    System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");
                    gameManager.roundNumber++;
                }
            } else {
                System.out.println("Not a valid input.");

            }

        }

    }

}
