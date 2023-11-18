package cst2110javadicegame;

import java.util.Scanner;

// This class holds the main game code and will call on other classes for their respective responsibilities.
public class DiceGame {

    // The instantiation of objects to help the game run.
    GameManager gameManager = new GameManager();
    ScoreboardManager scoreManager = new ScoreboardManager();
    ValidityManager validityManager = new ValidityManager();
    DiceManager diceManager = new DiceManager();

    final String ONES = "1";
    final String TWOS = "2";
    final String THREES = "3";
    final String FOURS = "4";
    final String FIVES = "5";
    final String SIXES = "6";
    final String SEQUENCE = "7";

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
            String inputOne = sc.next();
            String inputOneLower = inputOne.toLowerCase();
            if (validityManager.throwForfeitInputIsValid(inputOneLower)) {
                if ("f".equals(inputOneLower)) {
                    gameManager.forfeit = true;
                    // Change turn
                    gameManager.changeTurn();
                    System.out.println("Game Exited\n" + gameManager.getCurrentTurn() + " Wins via forfeit!");
                } else if ("t".equals(inputOneLower)) {
                    //System.out.println("\n" + scoreManager.returnUpdatedScoreboard());
                    gameManager.decrementThrows();

                    System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");
                    diceManager.diceRoller();
                    System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
                    System.out.println("Enter 's' to select category (number on die/dice) or 'd' to defer > ");
                    String inputTwo = sc.next();
                    String inputTwoLower = inputTwo.toLowerCase();
                    if (validityManager.selectDeferInputIsValid(inputTwoLower) == true) {
                        if ("s".equals(inputTwoLower)) {
                            System.out.print("Ones (1) Twos (2) Threes (3) fours (4) Fives (5) Sixes (6) or Sequence (7) > ");
                            String inputThree = sc.next();
                            if (validityManager.gameIntInputIsValid(inputThree) == true) {
                                switch (inputThree) {
                                    case ONES:
                                        diceManager.chosenIntegerString = "Ones";
                                        diceManager.chosenInteger = 1;
                                        break;
                                    case TWOS:
                                        diceManager.chosenIntegerString = "Twos";
                                        diceManager.chosenInteger = 2;
                                        break;
                                    case THREES:
                                        diceManager.chosenIntegerString = "Threes";
                                        diceManager.chosenInteger = 3;
                                        break;
                                    case FOURS:
                                        diceManager.chosenIntegerString = "Fours";
                                        diceManager.chosenInteger = 4;
                                        break;
                                    case FIVES:
                                        diceManager.chosenIntegerString = "Fives";
                                        diceManager.chosenInteger = 5;
                                        break;
                                    case SIXES:
                                        diceManager.chosenIntegerString = "Sixes";
                                        diceManager.chosenInteger = 6;
                                        break;
                                    case SEQUENCE:
                                        diceManager.chosenIntegerString = "Sequence";
                                        break;
                                    default:
                                        System.out.println("Error, Switch Case default, println inputThree " + inputThree);
                                }
                                System.out.println(diceManager.chosenIntegerString + " selected");
                                diceManager.diceListInserter();
                                System.out.println("That throw had " + diceManager.diceListMatched() + " dice with value "
                                        + diceManager.chosenInteger + ". Setting aside " + diceManager.diceListMatched() + " dice: "
                                        + diceManager.printDiceList(diceManager.getTurnMatchedDiceList()) + "\n dice list "+ diceManager.getDiceList());

                            } else {
                                System.out.println("Not a valid input.");

                            }
                        } else if ("d".equals(inputTwoLower)) {
                            System.out.println("you have chosen to re-roll your dice at the cost of a throw.");
                            diceManager.diceRoller();
                            gameManager.decrementThrows();
                        }
                    } else {
                        System.out.println("Not a valid input.");

                    }
                    gameManager.roundNumber++;

                }

            } else {
                System.out.println("Not a valid input.");

            }

        }

    }

}
