package cst2110javadicegame;

import java.util.Scanner;

// This class holds the main game code and will call on other classes for their respective responsibilities.
public class DiceGame {

    // The instantiation of objects to help the game run.
    GameManager gameManager = new GameManager();
    ScoreboardManager scoreManager = new ScoreboardManager();
    ValidityManager validityManager = new ValidityManager();
    DiceManager diceManager = new DiceManager();

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

            while (gameManager.roundPresented != true) {
                System.out.println("---------\nRound " + gameManager.roundNumber + " |\n---------\n");
                gameManager.roundPresented = true;
            }

            while ("Player One".equals(gameManager.getCurrentTurnString()) && gameManager.forfeit != true) {
                playerTurn();

            }

            while ("Player Two".equals(gameManager.getCurrentTurnString()) && gameManager.forfeit != true) {
                playerTurn();

            }
            gameManager.roundNumber += 1;
            gameManager.roundPresented = false;
        }

    }

    // The method for a players turn. This will be called a total of 14 times within the game.
    public void playerTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(gameManager.throwNumber + " throw of this turn, " + gameManager.getCurrentTurnString() + " to throw " + diceManager.diceNumber + " dice."
                + "\nThrow " + diceManager.diceNumber + " dice, enter 't' to throw or 'f' to forfeit > ");
        String inputOne = scanner.next();
        String inputOneLower = inputOne.toLowerCase();
        // While the user input is invalid keep prompting the user until a correct input is attained.
        while (validityManager.throwForfeitInputIsValid(inputOneLower) == false) {
            System.out.println("Not a valid input.");
            System.out.print(gameManager.throwNumber + " throw of this turn, " + gameManager.getCurrentTurnString() + " to throw " + diceManager.diceNumber + " dice."
                    + "\nThrow " + diceManager.diceNumber + " dice, enter 't' to throw or 'f' to forfeit > ");
            inputOne = scanner.next();
            inputOneLower = inputOne.toLowerCase();
        }
        if ("f".equals(inputOneLower)) {
            System.out.println(gameManager.forfeitGameProcedure());
        } else if ("t".equals(inputOneLower)) {
            gameManager.decrementThrows();
            System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");
            diceManager.setDiceNumber();
            diceManager.clearDiceList(diceManager.getDiceList());
            diceManager.diceRoller();
            System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
            System.out.print("Enter 's' to select category (number on die/dice) or 'd' to defer > ");
            String inputTwo = scanner.next();
            String inputTwoLower = inputTwo.toLowerCase();
            // While the user input is invalid keep prompting the user until a correct input is attained.
            while (validityManager.selectDeferInputIsValid(inputTwoLower) == false) {
                System.out.println("Not a valid input.");
                System.out.print("Enter 's' to select category (number on die/dice) or 'd' to defer > ");
                inputTwo = scanner.next();
                inputTwoLower = inputTwo.toLowerCase();
            }

            if ("s".equals(inputTwoLower)) {
                System.out.println("Select a category to play.\n");
                System.out.print("Ones (1) Twos (2) Threes (3) fours (4) Fives (5) Sixes (6) or Sequence (7) > "); // function with string builder for options, changes when an option has been chosen
                String inputThree = scanner.next();
                // While the user input is invalid keep prompting the user until a correct input is attained.
                while (validityManager.gameIntInputIsValid(inputThree) == false) {
                    System.out.println("Not a valid input.");
                    System.out.println("Select a category to play.\n");
                    System.out.print("Ones (1) Twos (2) Threes (3) fours (4) Fives (5) Sixes (6) or Sequence (7) > "); // function with string builder for options, changes when an option has been chosen
                    inputThree = scanner.next();
                }
                if ("7".equals(inputThree)) {
                    diceManager.switchOnInput(inputThree); // switch statement with the numbers
                    System.out.println(diceManager.chosenIntegerString + " selected");
                    System.out.println(
                            "0. None"
                            + "\n1. " + diceManager.getDiceList().get(0) 
                            + "\n2. " + diceManager.getDiceList().get(1)
                            + "\n3. " + diceManager.getDiceList().get(2)
                            + "\n4. " + diceManager.getDiceList().get(3)
                            + "\n5. " + diceManager.getDiceList().get(4));
                    sequence();
                    exitTurnProcedure();
                } else {

                    diceManager.switchOnInput(inputThree); // switch statement with the numbers
                    System.out.println(diceManager.chosenIntegerString + " selected");
                    diceManager.diceTurnListInserter(); // insert into the turn dice list
                    diceManager.clearDiceList(diceManager.getDiceList()); // remove from the dice list
                    diceManager.diceRoundListInserter();
                    diceManager.setDiceNumber(); // set the amount of dice to roll with

                    gameManager.throwNumber = "Next";
                    System.out.println("That throw had " + diceManager.turnDiceListSize() + " dice with value "
                            + diceManager.chosenInteger + ". Setting aside " + diceManager.roundDiceListSize() + " dice: "
                            + diceManager.printDiceList(diceManager.getRoundDiceList()) + "\n");
                    System.out.print(gameManager.throwNumber + " throw of this turn, " + gameManager.getCurrentTurnString() + " to throw " + diceManager.diceNumber + " dice."
                            + "\nThrow " + diceManager.diceNumber + " dice, enter 't' to throw or 'f' to forfeit > ");
                    diceManager.clearDiceList(diceManager.getTurnDiceList());

                    String inputFour = scanner.next();
                    String inputFourLower = inputFour.toLowerCase();
                    // While the user input is invalid keep prompting the user until a correct input is attained.
                    while (validityManager.throwForfeitInputIsValid(inputFourLower) == false) {
                        System.out.println("Not a valid input.");
                        System.out.print("Eenter 't' to throw or 'f' to forfeit > ");
                        inputFour = scanner.next();
                        inputFourLower = inputFour.toLowerCase();
                    }
                    if ("f".equals(inputFourLower)) {
                        System.out.println(gameManager.forfeitGameProcedure());
                    } else if ("t".equals(inputFourLower)) {
                        gameManager.decrementThrows();
                        System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");

                        diceManager.diceRoller();
                        System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
                        diceManager.diceTurnListInserter(); // insert into the turn dice list
                        diceManager.clearDiceList(diceManager.getDiceList());
                        diceManager.diceRoundListInserter();
                        diceManager.setDiceNumber(); // set the amount of dice to roll with

                        System.out.println("That throw had " + diceManager.turnDiceListSize() + " dice with value "
                                + diceManager.chosenInteger + ". Setting aside " + diceManager.roundDiceListSize() + " dice: "
                                + diceManager.printDiceList(diceManager.getRoundDiceList()) + "\n");
                        diceManager.clearDiceList(diceManager.getTurnDiceList());

                        if (gameManager.playerOneThrowCount >= 0 || diceManager.roundDiceListSize() == 5) {
                            System.out.print(gameManager.throwNumber + " throw of this turn, " + gameManager.getCurrentTurnString() + " to throw " + diceManager.diceNumber + " dice."
                                    + "\nThrow " + diceManager.diceNumber + " dice, enter 't' to throw or 'f' to forfeit > ");
                            String inputFive = scanner.next();
                            String inputFiveLower = inputFive.toLowerCase();
                            if (validityManager.throwForfeitInputIsValid(inputFiveLower)) {
                                if ("f".equals(inputFiveLower)) {
                                    System.out.println(gameManager.forfeitGameProcedure());
                                } else if ("t".equals(inputFiveLower)) {
                                    gameManager.decrementThrows();
                                    System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");

                                    diceManager.diceRoller();
                                    System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
                                    diceManager.diceTurnListInserter(); // insert into the turn dice list
                                    diceManager.clearDiceList(diceManager.getDiceList());
                                    diceManager.diceRoundListInserter();
                                    diceManager.setDiceNumber(); // set the amount of dice to roll with

                                    System.out.println("That throw had " + diceManager.turnDiceListSize() + " dice with value "
                                            + diceManager.chosenInteger + ". Setting aside " + diceManager.roundDiceListSize() + " dice: "
                                            + diceManager.printDiceList(diceManager.getRoundDiceList()) + "\n");
                                    System.out.println(gameManager.getCurrentTurnString() + " made " + diceManager.roundDiceListSize() + " with value "
                                            + diceManager.chosenInteger + " and scores " + roundScoreCalculator(gameManager.getCurrentTurnString(), diceManager.chosenInteger) + " for that round");
                                    exitTurnProcedure();
                                }
                            }
                        } else {
                            System.out.println(gameManager.getCurrentTurnString() + " made " + diceManager.roundDiceListSize() + " with value "
                                    + diceManager.chosenInteger + " and scores " + roundScoreCalculator(gameManager.getCurrentTurnString(), diceManager.chosenInteger) + " for that round");
                            exitTurnProcedure();
                        }
                    }
                }

            } else if ("d".equals(inputTwoLower)) {
                if (gameManager.playerOneThrowCount == 0) {
                    System.out.println("\nyou have disregarded all your throws.");
                    exitTurnProcedure();
                } else {
                    System.out.println("\nyou have chosen to re-roll your dice, disregarding this throw.");
                    gameManager.throwNumber = "Next";
                }

            }

        }

    }

    public int roundScoreCalculator(String player, int chosenDieNumber) {
        int Score = 0;
        int turnScore = (diceManager.roundDiceListSize() * diceManager.chosenInteger);
        switch (player) {
            case "Player One":
                switch (chosenDieNumber) {
                    case 1:
                        scoreManager.pOneScoreOnes = turnScore;
                        break;
                    case 2:
                        scoreManager.pOneScoreTwos = turnScore;
                        break;
                    case 3:
                        scoreManager.pOneScoreThrees = turnScore;
                        break;
                    case 4:
                        scoreManager.pOneScoreFours = turnScore;
                        break;
                    case 5:
                        scoreManager.pOneScoreFives = turnScore;
                        break;
                    case 6:
                        scoreManager.pOneScoreSixes = turnScore;
                        break;
                    default:
                        break;
                }
                scoreManager.playerOneTotalScore = scoreManager.playerOneTotalScore + turnScore;
                Score = turnScore;
                break;
            case "Player Two":
                switch (chosenDieNumber) {
                    case 1:
                        scoreManager.pOneScoreOnes = turnScore;
                        break;
                    case 2:
                        scoreManager.pOneScoreTwos = turnScore;
                        break;
                    case 3:
                        scoreManager.pOneScoreThrees = turnScore;
                        break;
                    case 4:
                        scoreManager.pOneScoreFours = turnScore;
                        break;
                    case 5:
                        scoreManager.pOneScoreFives = turnScore;
                        break;
                    case 6:
                        scoreManager.pOneScoreSixes = turnScore;
                        break;
                    default:
                        break;
                }
                scoreManager.pOneScoreOnes = turnScore;
                scoreManager.playerTwoTotalScore = scoreManager.playerTwoTotalScore + turnScore;
                Score = turnScore;
                break;
            default:
                break;
        }
        return Score;
    }

    public void exitTurnProcedure() {
        diceManager.clearDiceList(diceManager.getDiceList());
        diceManager.clearDiceList(diceManager.getRoundDiceList());
        diceManager.clearDiceList(diceManager.getTurnDiceList());
        if ("Player One".equals(gameManager.getCurrentTurnString())) {
            gameManager.playerOneThrowCount = 3;
        } else {
            gameManager.playerTwoThrowCount = 3;
        }
        gameManager.throwNumber = "First";
        diceManager.diceNumber = 5;
        System.out.println("round dice list " + diceManager.getRoundDiceList());
        System.out.println("turn dice list " + diceManager.getTurnDiceList());
        System.out.println("dice list " + diceManager.getDiceList());
        gameManager.changeTurn();
        System.out.println("\n" + scoreManager.returnUpdatedScoreboard());

    }

    public void sequence() {
        System.out.println("y");
    }

}
