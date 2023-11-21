package cst2110javadicegame;

import java.util.Scanner;
import java.util.TreeSet;

// This class holds the main game code and will call on other classes for their respective responsibilities.
public class DiceGame {

    // The instantiation of objects to help the game run.
    GameManager gameManager = new GameManager();
    ScoreboardManager scoreManager = new ScoreboardManager();
    ValidityManager validityManager = new ValidityManager();
    DiceManager diceManager = new DiceManager();
    public StringBuilder selectionChoices = new StringBuilder();
    public TreeSet<Integer> sequenceTreeSet = new TreeSet<>();

    // A "start" function to hold the game itself.
    public void start() {
        System.out.println("This is a two player, Strategic dice game."
                + "\nPlayers will take it in turns to roll 5 dice,"
                + "\nchoosing a die number and scoring as many points as possible by attaining the max number of that die."
                + "\nThe highest scoring player wins the game!");

        playOrExitGame(); // Prompt the user to play or exit the game.

        while (gameManager.gameLoop() == false) {

            if (gameManager.roundPresented == false) {
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
        if (!gameManager.forfeit){
            gameEndProcedure();
        }

    }

    // This method contains a players turn. This will be called a total of 14 times within the game as there are 7 rounds.
    public void playerTurn() {
        Scanner scanner = new Scanner(System.in);
        throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
        String inputOne = scanner.next();// Attain the user input and force it to lowercase.
        String inputOneLower = inputOne.toLowerCase();

        // While the user input is invalid keep prompting the user until a correct input is attained.
        while (validityManager.throwForfeitInputIsValid(inputOneLower) == false) {
            System.out.println("Not a valid input.");
            throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
            inputOne = scanner.next();
            inputOneLower = inputOne.toLowerCase();
        }

        // If the player inputs 'f' the game will end with the player forfeiting
        if ("f".equals(inputOneLower)) {
            System.out.println(gameManager.forfeitGameProcedure());

            // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
        } else if ("t".equals(inputOneLower) && gameManager.playerThrowCount >= 0) {

            gameManager.decrementThrows(); // Decrement player throw count.
            System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");
            diceManager.setDiceNumber(); // Set the amount of dice to be rolled.
            diceManager.clearDiceList(diceManager.getDiceList()); // Clear the dice list ready for a fresh roll
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
                selectionPrinter(gameManager.getCurrentTurnString());
                String inputThree = scanner.next();
                // While the user input is invalid keep prompting the user until a correct input is attained.
                while (validityManager.gameIntInputIsValid(inputThree) == false) {
                    System.out.println("Not a valid input.");
                    System.out.println("Select a category to play.\n");
                    selectionPrinter(gameManager.getCurrentTurnString());
                    inputThree = scanner.next();
                }
                if ("7".equals(inputThree)) {
                    diceManager.switchOnInput(inputThree); // Switch statement to retrieve the chosen die number from the user
                    sequence(); // call for the Sequence procedure
                    exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                    System.out.println("exited through port 006");

                } else {

                    diceManager.switchOnInput(inputThree); // Switch statement to retrieve the chosen die number from the user
                    diceManager.diceTurnListInserter(); // insert into the turn dice list
                    diceManager.clearDiceList(diceManager.getDiceList()); // Clear the rolled dice list
                    diceManager.diceRoundListInserter();
                    diceManager.setDiceNumber(); // set the amount of dice to roll with
                    gameManager.throwString = "Next";
                    throwOutcomeInformation(); // Print matched dice from that throw, the amount of dice set aside and the 'round dice list' containing all matched dice.
                    throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
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

                    // If the player inputs 'f' the game will end with the player forfeiting
                    if ("f".equals(inputFourLower)) {
                        System.out.println(gameManager.forfeitGameProcedure());
                        // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
                    } else if ("t".equals(inputFourLower) && gameManager.playerThrowCount >= 0) {
                        playerThrowsDiceAndAttainsMatches();
                        diceManager.clearDiceList(diceManager.getTurnDiceList());

                        if (gameManager.playerThrowCount >= 0 && diceManager.roundDiceListSize() != 5) {
                            throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.

                            String inputFive = scanner.next();
                            String inputFiveLower = inputFive.toLowerCase();
                            while (validityManager.throwForfeitInputIsValid(inputOneLower) == false) {
                                System.out.println("Not a valid input.");
                                throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
                                inputOne = scanner.next();
                                inputOneLower = inputOne.toLowerCase();
                            }
                            if (validityManager.throwForfeitInputIsValid(inputFiveLower)) {

                                // If the player inputs 'f' the game will end with the player forfeiting
                                if ("f".equals(inputFiveLower)) {
                                    System.out.println(gameManager.forfeitGameProcedure());

                                    // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
                                } else if ("t".equals(inputFiveLower) && gameManager.playerThrowCount >= 0) {
                                    playerThrowsDiceAndAttainsMatches();
                                    endOfPlayerTurnInformation(); // Print what the player rolled and scored for that turn
                                    exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                                    System.out.println("exited through port 001");
                                }
                            }
                        } else {
                            endOfPlayerTurnInformation(); // Print what the player rolled and scored for that turn
                            exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                            System.out.println("exited through port 002");

                        }
                    } else {
                        endOfPlayerTurnInformation(); // Print what the player rolled and scored for that turn
                        exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                        System.out.println("exited through port 003");

                    }
                }

                // If the player inputs 'd' their throw will be ignored, if they have no throws left ther turn will be over.
            } else if ("d".equals(inputTwoLower)) {
                if (gameManager.playerThrowCount == 0) {
                    System.out.println("\nyou have disregarded all your throws.");
                    exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                    System.out.println("exited through port 004");

                } else {
                    System.out.println("\nyou have chosen to re-roll your dice, disregarding this throw.");
                    gameManager.throwString = "Next";
                }

            }

        } else {
            endOfPlayerTurnInformation(); // Print what the player rolled and scored for that turn
            exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
            System.out.println("exited through port 005");

        }

    }

    // This is the code for when a player wants to play for a sequence. called when the player enters 7 at the selection stage.
    public void sequence() {
        Scanner scanner = new Scanner(System.in);
        boolean isSequence = sequenceTreeSet.equals(new TreeSet<>(java.util.Arrays.asList(1, 2, 3, 4, 5)))
                || sequenceTreeSet.equals(new TreeSet<>(java.util.Arrays.asList(2, 3, 4, 5, 6)));
        printInitialThrow();
        System.out.print("Enter which dice you wish to set aside using the number labels seperated by a space (e.g. 1 3 4 5) or enter 0 for none > ");
        String sequenceInput = scanner.nextLine();
        while (validityManager.sequenceIntInputIsValid(sequenceInput) == false) {
            System.out.println("Not a valid input.");
            System.out.print("\nEnter which dice you wish to set aside using the number labels seperated by a space (e.g. 1 3 4 5) or enter 0 for none > ");
            sequenceInput = scanner.nextLine();
        }
        if ("0".equals(sequenceInput) /*&& gameManager.playerThrowCount != 0*/) {

            System.out.println("\nyou have chosen to re-roll your dice, disregarding this throw.");

            gameManager.decrementThrows(); // Decrement player throw count.
            System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");
            diceManager.clearDiceList(diceManager.getDiceList()); // Clear the dice list ready for a fresh roll
            diceManager.diceRoller();
            System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
            System.out.print("Enter 's' to select category (number on die/dice) or 'd' to defer > ");

        } else {
            for (String number : sequenceInput.split("\\s+")) {
                int inputNumber = Integer.parseInt(number.trim());
                sequenceTreeSet.add(diceManager.getDiceList().get(inputNumber - 1));
            }
            System.out.println("You have selected the following dice to keep: ");
            printSequenceSet();
            throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
            String inputOne = scanner.next();
            String inputOneLower = inputOne.toLowerCase();
            // While the user input is invalid keep prompting the user until a correct input is attained.
            while (validityManager.throwForfeitInputIsValid(inputOneLower) == false) {
                System.out.println("Not a valid input.");
                System.out.print("Eenter 't' to throw or 'f' to forfeit > ");
                inputOne = scanner.next();
                inputOneLower = inputOne.toLowerCase();
            }

            // If the player inputs 'f' the game will end with the player forfeiting
            if ("f".equals(inputOneLower)) {
                System.out.println(gameManager.forfeitGameProcedure());
                // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
            } else if ("t".equals(inputOneLower) && gameManager.playerThrowCount > 0) {
                diceManager.diceNumber = (5 - sequenceTreeSet.size()); // set the amount of dice to roll with
                gameManager.decrementThrows(); // Decrement player throw count.
                System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");
                diceManager.clearDiceList(diceManager.getDiceList()); // Clear the dice list ready for a fresh roll
                diceManager.diceRoller();
                System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
                for (int number : diceManager.getDiceList()) {
                    if (!sequenceTreeSet.contains(number)) {
                        sequenceTreeSet.add(diceManager.getDiceList().get(number - 1));
                    }
                }
                System.out.println("You have selected the following dice to keep: ");
                printSequenceSet();
                throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
                String inputTwo = scanner.next();
                String inputTwoLower = inputTwo.toLowerCase();
                // While the user input is invalid keep prompting the user until a correct input is attained.
                while (validityManager.throwForfeitInputIsValid(inputTwoLower) == false) {
                    System.out.println("Not a valid input.");
                    System.out.print("Eenter 't' to throw or 'f' to forfeit > ");
                    inputTwo = scanner.next();
                    inputTwoLower = inputTwo.toLowerCase();
                }

                // If the player inputs 'f' the game will end with the player forfeiting
                if ("f".equals(inputTwoLower)) {
                    System.out.println(gameManager.forfeitGameProcedure());
                    // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
                } else if ("t".equals(inputTwoLower) && gameManager.playerThrowCount > 0) {
                    diceManager.diceNumber = (5 - sequenceTreeSet.size()); // set the amount of dice to roll with
                    gameManager.decrementThrows(); // Decrement player throw count.
                    System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");
                    diceManager.clearDiceList(diceManager.getDiceList()); // Clear the dice list ready for a fresh roll
                    diceManager.diceRoller();
                    System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
                    for (int number : diceManager.getDiceList()) {
                        if (!sequenceTreeSet.contains(number)) {
                            sequenceTreeSet.add(diceManager.getDiceList().get(number - 1));
                        }
                    }
                    System.out.println("You have selected the following dice to keep: ");
                    printSequenceSet();
                    sequenceExitProcedure(isSequence);
                } else {
                    sequenceExitProcedure(isSequence);
                }

            } else {
                sequenceExitProcedure(isSequence);
            }

        }
    }

    public void printSequenceSet() {
        for (int integer : sequenceTreeSet) {
            System.out.print("[ " + integer + " ]");

        }
        System.out.println("\n");
    }

    public void printInitialThrow() {
        System.out.println("0. None"
                + "\n1. [ " + diceManager.getDiceList().get(0) + " ]"
                + "\n2. [ " + diceManager.getDiceList().get(1) + " ]"
                + "\n3. [ " + diceManager.getDiceList().get(2) + " ]"
                + "\n4. [ " + diceManager.getDiceList().get(3) + " ]"
                + "\n5. [ " + diceManager.getDiceList().get(4) + " ]\n");
    }

    public void sequenceExitProcedure(boolean isSequence) {
        if (isSequence) {
            switch (gameManager.getCurrentTurnString()) {
                case "Player One":
                    scoreManager.pOneSequence = "ACHIEVED";
                    break;
                case "Player Two":
                    scoreManager.pTwoSequence = "ACHIEVED";
                    break;
                default:
                    System.out.println("Error Sequence Method 001");
                    break;
            }
            System.out.println("\nA correct sequence has been established.\n"
                    + gameManager.getCurrentTurnString() + " scores 20 for the sequence category");
        } else {
            switch (gameManager.getCurrentTurnString()) {
                case "Player One":
                    scoreManager.pOneSequence = "MISSED";
                    break;
                case "Player Two":
                    scoreManager.pTwoSequence = "MISSED";
                    break;
                default:
                    System.out.println("Error Sequence Method 002");
                    break;
            }
            System.out.println("\nA correct sequence has not been established.\n"
                    + gameManager.getCurrentTurnString() + " scores 0 for the sequence category");
        }
    }

    public int roundScoreCalculator(String player, int chosenDieNumber) {

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
                        System.out.println("Error roundScoreCalculator 001");
                        break;
                }
                scoreManager.playerOneTotalScore = scoreManager.playerOneTotalScore + turnScore;

                break;
            case "Player Two":
                switch (chosenDieNumber) {
                    case 1:
                        scoreManager.pTwoScoreOnes = turnScore;
                        break;
                    case 2:
                        scoreManager.pTwoScoreTwos = turnScore;
                        break;
                    case 3:
                        scoreManager.pTwoScoreThrees = turnScore;
                        break;
                    case 4:
                        scoreManager.pTwoScoreFours = turnScore;
                        break;
                    case 5:
                        scoreManager.pTwoScoreFives = turnScore;
                        break;
                    case 6:
                        scoreManager.pTwoScoreSixes = turnScore;
                        break;
                    default:
                        System.out.println("Error roundScoreCalculator 002");

                        break;
                }
                scoreManager.playerTwoTotalScore = scoreManager.playerTwoTotalScore + turnScore;

                break;
            default:
                System.out.println("Error roundScoreCalculator 003");
                break;
        }
        return turnScore;
    }

    public void exitTurnProcedure() {
        diceManager.clearDiceList(diceManager.getDiceList());
        diceManager.clearDiceList(diceManager.getRoundDiceList());
        diceManager.clearDiceList(diceManager.getTurnDiceList());
        gameManager.playerThrowCount = 3;
        gameManager.throwString = "First";
        diceManager.diceNumber = 5;
        System.out.println("round dice list " + diceManager.getRoundDiceList());
        System.out.println("turn dice list " + diceManager.getTurnDiceList());
        System.out.println("dice list " + diceManager.getDiceList());
        gameManager.changeTurn();
        System.out.println("\n" + scoreManager.returnUpdatedScoreboard());

    }

    public void selectionPrinter(String player) {
        selectionChoices.setLength(0);
        switch (player) {
            case "Player One":
                if (scoreManager.pOneScoreOnes == 0) {
                    selectionChoices.append("Ones (1) ");
                }
                if (scoreManager.pOneScoreTwos == 0) {
                    selectionChoices.append("Twos (2) ");
                }
                if (scoreManager.pOneScoreThrees == 0) {
                    selectionChoices.append("Threes (3) ");
                }
                if (scoreManager.pOneScoreFours == 0) {
                    selectionChoices.append("Fours (4) ");
                }
                if (scoreManager.pOneScoreFives == 0) {
                    selectionChoices.append("Fives (5) ");
                }
                if (scoreManager.pOneScoreSixes == 0) {
                    selectionChoices.append("Sixes (6) ");
                }
                if (scoreManager.pOneSequenceScore == 0) {
                    selectionChoices.append("Sequence (7) ");
                }
                break;
            case "Player Two":
                if (scoreManager.pTwoScoreOnes == 0) {
                    selectionChoices.append("Ones (1) ");
                }
                if (scoreManager.pTwoScoreTwos == 0) {
                    selectionChoices.append("Twos (2) ");
                }
                if (scoreManager.pTwoScoreThrees == 0) {
                    selectionChoices.append("Threes (3) ");
                }
                if (scoreManager.pTwoScoreFours == 0) {
                    selectionChoices.append("Fours (4) ");
                }
                if (scoreManager.pTwoScoreFives == 0) {
                    selectionChoices.append("Fives (5) ");
                }
                if (scoreManager.pTwoScoreSixes == 0) {
                    selectionChoices.append("Sixes (6) ");
                }
                if (scoreManager.pTwoSequenceScore == 0) {
                    selectionChoices.append("Sequence (7) ");
                }
                break;
            default:
                System.out.println("Error selectionPrinter 003");
                break;
        }
        selectionChoices.append(" > ");
        System.out.print(selectionChoices);
    }

    public void playerThrowsDiceAndAttainsMatches() {
        gameManager.decrementThrows();
        System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");
        diceManager.diceRoller();
        System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
        diceManager.diceTurnListInserter(); // insert into the turn dice list
        diceManager.clearDiceList(diceManager.getDiceList());
        diceManager.diceRoundListInserter();
        diceManager.setDiceNumber(); // set the amount of dice to roll with
        throwOutcomeInformation(); // Print matched dice from that throw, the amount of dice set aside and the 'round dice list' containing all matched dice.
    }

    public void throwInformation() {
        System.out.print(gameManager.throwString + " throw of this turn, " + gameManager.getCurrentTurnString() + " to throw " + diceManager.diceNumber + " dice."
                + "\nThrow " + diceManager.diceNumber + " dice, enter 't' to throw or 'f' to forfeit > ");
    }

    public void throwOutcomeInformation() {
        System.out.println("That throw had " + diceManager.turnDiceListSize() + " dice with value "
                + diceManager.chosenInteger + ". Setting aside " + diceManager.roundDiceListSize() + " dice: "
                + diceManager.printDiceList(diceManager.getRoundDiceList()) + "\n");
    }

    public void endOfPlayerTurnInformation() {
        System.out.println(gameManager.getCurrentTurnString() + " made " + diceManager.roundDiceListSize()
                + " with value " + diceManager.chosenInteger
                + " and scores " + roundScoreCalculator(gameManager.getCurrentTurnString(), diceManager.chosenInteger) + " for that round");
    }

    public void playOrExitGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Play game (1) or Exit game (0) > ");
        String playGame = scanner.next();
        while (validityManager.startGameIsValid(playGame) == false) {
            System.out.println("Not a valid input.");
            System.out.print("Play game (1) or Exit game (0) > ");
            playGame = scanner.next();
        }

        if ("0".equals(playGame)) {
            gameManager.forfeit = true;
            System.out.println("Game Exited");
        } else {
            System.out.println("\n" + scoreManager.returnUpdatedScoreboard());
            //gameManager.GamePrompt = true;

        }
    }

    public void gameEndProcedure() {
        if (scoreManager.playerTwoTotalScore < scoreManager.playerOneTotalScore) {
            System.out.println("The game has ended, Player One wins! and finished with a score of: " + scoreManager.playerOneTotalScore
                    + " Player Two has finished with a total score of: " + scoreManager.playerTwoTotalScore);
        } else {
            System.out.println("The game has ended, Player Two wins! and finished with a score of: " + scoreManager.playerTwoTotalScore
                    + " Player Two has finished with a total score of: " + scoreManager.playerOneTotalScore);
        }
    }

}
