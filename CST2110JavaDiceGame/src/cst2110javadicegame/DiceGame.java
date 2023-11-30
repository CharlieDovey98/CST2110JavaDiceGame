package cst2110javadicegame;

import java.util.ArrayList;
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
    Scanner scanner = new Scanner(System.in);

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

            if ("Player One".equals(gameManager.getCurrentTurnString()) && gameManager.forfeit != true) {
                playerTurn();

            }

            if ("Player Two".equals(gameManager.getCurrentTurnString()) && gameManager.forfeit != true) {
                playerTurn();

            }
            gameManager.roundNumber += 1;
            gameManager.roundPresented = false;
        }
        if (!gameManager.forfeit) {
            gameEndProcedure();
        }

    }

    // This method contains a players turn. This will be called a total of 14 times within the game as there are 7 rounds.
    public void playerTurn() {
        throwInformation();
        String inputOne = scanner.nextLine();// Attain the user input and force it to lowercase.
        String inputOneLower = inputOne.toLowerCase();
        while (validityManager.throwForfeitInputIsValid(inputOneLower) == false) {
            System.out.println("Not a valid input.");
            throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
            inputOne = scanner.nextLine();
            inputOneLower = inputOne.toLowerCase();
        }
        if ("f".equals(inputOneLower)) {
            System.out.println(gameManager.forfeitGameProcedure());

            // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
        } else if ("t".equals(inputOneLower)) {
            playerThrows(diceManager.getRoundDiceList());
            System.out.print("Enter 's' to select category (number on die/dice) or 'd' to defer > ");
            String inputTwo = scanner.nextLine();
            String inputTwoLower = inputTwo.toLowerCase();
            // While the user input is invalid keep prompting the user until a correct input is attained.
            while (validityManager.selectDeferInputIsValid(inputTwoLower) == false) {
                System.out.println("Not a valid input.");
                System.out.print("Enter 's' to select category (number on die/dice) or 'd' to defer > ");
                inputTwo = scanner.nextLine();
                inputTwoLower = inputTwo.toLowerCase();
            }
            if ("s".equals(inputTwoLower)) {
                System.out.println("Select a category to play.\n");
                selectionPrinter(gameManager.getCurrentTurnString());
                String userInput = scanner.nextLine();
                // While the user input is invalid keep prompting the user until a correct input is attained.
                while ((validityManager.gameIntInputIsValid(userInput) == false) && (validityManager.hasNumberBeenChosen(userInput, gameManager.getCurrentTurnString()) == true)) {
                    System.out.println("Not a valid input.");
                    System.out.println("Select a category to play.\n");
                    selectionPrinter(gameManager.getCurrentTurnString());
                    userInput = scanner.nextLine();
                }
                if ("7".equals(userInput)) {
                    diceManager.switchOnInput(userInput); // Switch statement to retrieve the chosen die number from the user
                    addToChosenNumbers(gameManager.getCurrentTurnString(), Integer.parseInt(userInput)); // Add the chosen number to the players Chosen Numbers Hash Set
                    sequence(); // call for the Sequence procedure
                    exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                    System.out.println("exited through port 001");

                } else {
                    diceManager.switchOnInput(userInput); // Switch statement to retrieve the chosen die number from the user
                    addToChosenNumbers(gameManager.getCurrentTurnString(), Integer.parseInt(userInput)); // Add the chosen number to the players Chosen Numbers Hash Set
                    diceManager.diceTurnListInserter(); // insert into the turn dice list
                    diceManager.diceRoundListInserter();
                    gameManager.throwString = "Next";
                    throwOutcomeInformation(); // Print matched dice from that throw, the amount of dice set aside and the 'round dice list' containing all matched dice.
                    throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
                    diceManager.clearDiceList(diceManager.getTurnDiceList());

                    String inputFour = scanner.nextLine();
                    String inputFourLower = inputFour.toLowerCase();
                    // While the user input is invalid keep prompting the user until a correct input is attained.
                    while (validityManager.throwForfeitInputIsValid(inputFourLower) == false) {
                        System.out.println("Not a valid input.");
                        System.out.print("Eenter 't' to throw or 'f' to forfeit > ");
                        inputFour = scanner.nextLine();
                        inputFourLower = inputFour.toLowerCase();
                    }
                    if ("f".equals(inputFourLower)) {
                        System.out.println(gameManager.forfeitGameProcedure());

                        // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
                    } else if ("t".equals(inputFourLower)) {
                        playerThrows(diceManager.getRoundDiceList());
                        diceManager.diceTurnListInserter();
                        diceManager.diceRoundListInserter();
                        gameManager.throwString = "Final";
                        throwOutcomeInformation(); // Print matched dice from that throw, the amount of dice set aside and the 'round dice list' containing all matched dice.
                        diceManager.clearDiceList(diceManager.getTurnDiceList());
                        throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
                        String inputFive = scanner.nextLine();
                        String inputFiveLower = inputFive.toLowerCase();
                        // While the user input is invalid keep prompting the user until a correct input is attained.
                        while (validityManager.throwForfeitInputIsValid(inputFiveLower) == false) {
                            System.out.println("Not a valid input.");
                            System.out.print("Eenter 't' to throw or 'f' to forfeit > ");
                            inputFive = scanner.nextLine();
                            inputFiveLower = inputFive.toLowerCase();
                        }
                        if ("f".equals(inputFiveLower)) {
                            System.out.println(gameManager.forfeitGameProcedure());

                            // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
                        } else if ("t".equals(inputFiveLower)) {
                            playerThrows(diceManager.getRoundDiceList());

                            diceManager.diceTurnListInserter();
                            diceManager.diceRoundListInserter();
                            throwOutcomeInformation(); // Print matched dice from that throw, the amount of dice set aside and the 'round dice list' containing all matched dice.
                            endOfPlayerTurnInformation(); // Print what the player rolled and scored for that turn
                            exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                            System.out.println("exited through port 002");
                        }
                    }
                }

            } else if ("d".equals(inputTwoLower)) {
                System.out.println("\nyou have chosen to re-roll your dice, disregarding this throw.");
                gameManager.throwString = "Next";
                throwInformation();
                String inputSeven = scanner.nextLine();// Attain the user input and force it to lowercase.
                String inputSevenLower = inputSeven.toLowerCase();
                while (validityManager.throwForfeitInputIsValid(inputSevenLower) == false) {
                    System.out.println("Not a valid input.");
                    throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
                    inputSeven = scanner.nextLine();
                    inputSevenLower = inputSeven.toLowerCase();
                }
                if ("f".equals(inputSevenLower)) {
                    System.out.println(gameManager.forfeitGameProcedure());

                    // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
                } else if ("t".equals(inputSevenLower)) {
                    playerThrows(diceManager.getRoundDiceList());
                    System.out.print("Enter 's' to select category (number on die/dice) or 'd' to defer > ");
                    String inputEight = scanner.nextLine();
                    String inputEightLower = inputEight.toLowerCase();
                    // While the user input is invalid keep prompting the user until a correct input is attained.
                    while (validityManager.selectDeferInputIsValid(inputEightLower) == false) {
                        System.out.println("Not a valid input.");
                        System.out.print("Enter 's' to select category (number on die/dice) or 'd' to defer > ");
                        inputEight = scanner.nextLine();
                        inputEightLower = inputEight.toLowerCase();
                    }
                    if ("s".equals(inputEightLower)) {
                        System.out.println("Select a category to play.\n");
                        selectionPrinter(gameManager.getCurrentTurnString());
                        String userInput = scanner.nextLine();
                        // While the user input is invalid keep prompting the user until a correct input is attained.
                        while ((validityManager.gameIntInputIsValid(userInput) == false) && (validityManager.hasNumberBeenChosen(userInput, gameManager.getCurrentTurnString()) == true)) {
                            System.out.println("Not a valid input.");
                            System.out.println("Select a category to play.\n");
                            selectionPrinter(gameManager.getCurrentTurnString());
                            userInput = scanner.nextLine();
                        }
                        if ("7".equals(userInput)) {
                            diceManager.switchOnInput(userInput); // Switch statement to retrieve the chosen die number from the user
                            addToChosenNumbers(gameManager.getCurrentTurnString(), Integer.parseInt(userInput)); // Add the chosen number to the players Chosen Numbers Hash Set
                            sequence(); // call for the Sequence procedure
                            exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                            System.out.println("exited through port 003");

                        } else {
                            diceManager.switchOnInput(userInput); // Switch statement to retrieve the chosen die number from the user
                            addToChosenNumbers(gameManager.getCurrentTurnString(), Integer.parseInt(userInput)); // Add the chosen number to the players Chosen Numbers Hash Set
                            diceManager.diceTurnListInserter(); // insert into the turn dice list
                            diceManager.diceRoundListInserter();
                            diceManager.setDiceNumber(diceManager.getRoundDiceList()); // set the amount of dice to roll with
                            gameManager.throwString = "Final";
                            throwOutcomeInformation(); // Print matched dice from that throw, the amount of dice set aside and the 'round dice list' containing all matched dice.
                            throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
                            diceManager.clearDiceList(diceManager.getTurnDiceList());

                            String inputFour = scanner.nextLine();
                            String inputFourLower = inputFour.toLowerCase();
                            // While the user input is invalid keep prompting the user until a correct input is attained.
                            while (validityManager.throwForfeitInputIsValid(inputFourLower) == false) {
                                System.out.println("Not a valid input.");
                                System.out.print("Eenter 't' to throw or 'f' to forfeit > ");
                                inputFour = scanner.nextLine();
                                inputFourLower = inputFour.toLowerCase();
                            }
                            if ("f".equals(inputFourLower)) {
                                System.out.println(gameManager.forfeitGameProcedure());

                                // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
                            } else if ("t".equals(inputFourLower)) {
                                playerThrows(diceManager.getRoundDiceList());
                                diceManager.diceTurnListInserter();
                                diceManager.diceRoundListInserter();
                                throwOutcomeInformation(); // Print matched dice from that throw, the amount of dice set aside and the 'round dice list' containing all matched dice.
                                diceManager.clearDiceList(diceManager.getTurnDiceList());
                                endOfPlayerTurnInformation(); // Print what the player rolled and scored for that turn
                                exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                                System.out.println("exited through port 004");

                            }

                        }
                    } else if ("d".equals(inputTwoLower)) {
                        System.out.println("\nyou have chosen to re-roll your dice, disregarding this throw.");
                        gameManager.throwString = "Final";
                        throwInformation();
                        String inputNine = scanner.nextLine();// Attain the user input and force it to lowercase.
                        String inputNineLower = inputNine.toLowerCase();
                        while (validityManager.throwForfeitInputIsValid(inputNineLower) == false) {
                            System.out.println("Not a valid input.");
                            throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
                            inputNine = scanner.nextLine();
                            inputNineLower = inputNine.toLowerCase();
                        }
                        if ("f".equals(inputNineLower)) {
                            System.out.println(gameManager.forfeitGameProcedure());

                            // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
                        } else if ("t".equals(inputNineLower)) {
                            playerThrows(diceManager.getRoundDiceList());
                            System.out.print("Enter 's' to select category > ");
                            String inputTen = scanner.nextLine();
                            String inputTenLower = inputTen.toLowerCase();
                            // While the user input is invalid keep prompting the user until a correct input is attained.
                            while (!"s".equals(inputTenLower)) {
                                System.out.println("Not a valid input.");
                                System.out.print("Enter 's' to select category > ");
                                inputTen = scanner.nextLine();
                                inputTenLower = inputTen.toLowerCase();
                            }
                            if ("s".equals(inputTenLower)) {
                                System.out.println("Select a category to play.\n");
                                selectionPrinter(gameManager.getCurrentTurnString());
                                String userInput = scanner.nextLine();
                                // While the user input is invalid keep prompting the user until a correct input is attained.
                                while ((validityManager.gameIntInputIsValid(userInput) == false) && (validityManager.hasNumberBeenChosen(userInput, gameManager.getCurrentTurnString()) == true)) {
                                    System.out.println("Not a valid input.");
                                    System.out.println("Select a category to play.\n");
                                    selectionPrinter(gameManager.getCurrentTurnString());
                                    userInput = scanner.nextLine();
                                }
                                if ("7".equals(userInput)) {
                                    diceManager.switchOnInput(userInput); // Switch statement to retrieve the chosen die number from the user
                                    addToChosenNumbers(gameManager.getCurrentTurnString(), Integer.parseInt(userInput)); // Add the chosen number to the players Chosen Numbers Hash Set
                                    sequence(); // call for the Sequence procedure
                                    exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                                    System.out.println("exited through port 005");

                                } else {
                                    diceManager.switchOnInput(userInput); // Switch statement to retrieve the chosen die number from the user
                                    addToChosenNumbers(gameManager.getCurrentTurnString(), Integer.parseInt(userInput)); // Add the chosen number to the players Chosen Numbers Hash Set
                                    diceManager.diceTurnListInserter(); // insert into the turn dice list
                                    diceManager.diceRoundListInserter();
                                    diceManager.setDiceNumber(diceManager.getRoundDiceList()); // set the amount of dice to roll with
                                    throwOutcomeInformation(); // Print matched dice from that throw, the amount of dice set aside and the 'round dice list' containing all matched dice.
                                    diceManager.clearDiceList(diceManager.getTurnDiceList());
                                    endOfPlayerTurnInformation(); // Print what the player rolled and scored for that turn
                                    exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                                    System.out.println("exited through port 006");

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // This is the code for when a player wants to play for a sequence. called when the player enters 7 at the selection stage.
    public void sequence() {
        printInitialThrow(); // Print the players first throw formatted on a new line for each integer with a corresponding index number.
        if (gameManager.playerThrowCount == 1) {
            gameManager.throwString = "Final";
        } else if (gameManager.playerThrowCount == 0) {
            System.out.print("Enter which dice you wish to set aside using the number labels seperated by a space (e.g. 1 3 4 5) > ");
            String sequenceInput = scanner.nextLine();
            // While the user input is invalid keep prompting the user until a correct input is attained.
            while (validityManager.sequenceIntInputIsValid(sequenceInput) == false) {
                System.out.println("Not a valid input.");
                System.out.print("\nEnter which dice you wish to set aside using the number labels seperated by a space (e.g. 1 3 4 5) > ");
                sequenceInput = scanner.nextLine();
            }
            addFromDiceListToSequenceSet(sequenceInput);
            System.out.println("You have selected the following dice to keep: ");
            printSequenceSet();
            sequenceScoreModifier(); // Attain whether the player got a sequence.
            sequenceTreeSet.clear();
            return;
        } else {
            gameManager.throwString = "Next"; // Set the throwString as next to differentiate between their first throw.
        }
        System.out.print("Enter which dice you wish to set aside using the number labels seperated by a space (e.g. 1 3 4 5) or enter 0 for none > ");
        String sequenceInput = scanner.nextLine();
        // While the user input is invalid keep prompting the user until a correct input is attained.
        while (validityManager.sequenceIntInputIsValid(sequenceInput) == false) {
            System.out.println("Not a valid input.");
            System.out.print("\nEnter which dice you wish to set aside using the number labels seperated by a space (e.g. 1 3 4 5) or enter 0 for none > ");
            sequenceInput = scanner.nextLine();
        }
        if ("0".equals(sequenceInput) /*&& gameManager.playerThrowCount != 0*/) { // If the user input is 0 the user has chosen to defer their throw.

            System.out.println("\nyou have chosen to re-roll your dice, disregarding this throw.");

            gameManager.decrementThrows(); // Decrement player throw count.

            diceManager.clearDiceList(diceManager.getDiceList()); // Clear the dice list ready for a fresh roll
            diceManager.diceRoller();
            System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
            System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");
            System.out.print("Enter 's' to select category (number on die/dice) or 'd' to defer > ");

        } else { // The player input has been accepted and will be used to progress the round.
            addFromDiceListToSequenceSet(sequenceInput);
            System.out.println("You have selected the following dice to keep: ");
            printSequenceSet();
            if (checkForSequence()) {
                sequenceScoreModifier(); // Exit the players sequence turn.
            } else {
                diceManager.diceNumber = (5 - sequenceTreeSet.size()); // set the amount of dice to roll with
                if (gameManager.playerThrowCount == 1) {
                    gameManager.throwString = "Final";
                } else if (gameManager.playerThrowCount == 0) {
                    System.out.print("Enter which dice you wish to set aside using the number labels seperated by a space (e.g. 1 3 4 5) > ");
                    String sequenceUserInput = scanner.nextLine();
                    // While the user input is invalid keep prompting the user until a correct input is attained.
                    while (validityManager.sequenceIntInputIsValid(sequenceUserInput) == false) {
                        System.out.println("Not a valid input.");
                        System.out.print("\nEnter which dice you wish to set aside using the number labels seperated by a space (e.g. 1 3 4 5) > ");
                        sequenceUserInput = scanner.nextLine();
                    }
                    addFromDiceListToSequenceSet(sequenceUserInput);
                    System.out.println("You have selected the following dice to keep: ");
                    printSequenceSet();
                    sequenceScoreModifier(); // Attain whether the player got a sequence.
                    sequenceTreeSet.clear();
                    return;
                } else {
                    gameManager.throwString = "Next"; // Set the throwString as next to differentiate between their first throw.
                }
                throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
                String inputOne = scanner.nextLine();
                String inputOneLower = inputOne.toLowerCase();
                // While the user input is invalid keep prompting the user until a correct input is attained.
                while (validityManager.throwForfeitInputIsValid(inputOneLower) == false) {
                    System.out.println("Not a valid input.");
                    System.out.print("Eenter 't' to throw or 'f' to forfeit > ");
                    inputOne = scanner.nextLine();
                    inputOneLower = inputOne.toLowerCase();
                }

                // If the player inputs 'f' the game will end with the player forfeiting
                if ("f".equals(inputOneLower)) {
                    System.out.println(gameManager.forfeitGameProcedure());
                    // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
                } else if ("t".equals(inputOneLower) && gameManager.playerThrowCount > 0) {

                    gameManager.decrementThrows(); // Decrement player throw count.
                    diceManager.clearDiceList(diceManager.getDiceList()); // Clear the dice list ready for a fresh roll
                    diceManager.diceRoller();
                    System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
                    System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");
                    addDieNumberToSet();

                    System.out.println("You have selected the following dice to keep: ");
                    printSequenceSet();
                    // If one of the sequences has been made proceed into the if statement.
                    if (checkForSequence()) {
                        sequenceScoreModifier(); // Exit the players sequence turn.
                    } else {
                        if (gameManager.playerThrowCount == 1) {
                            gameManager.throwString = "Final";
                        } else if (gameManager.playerThrowCount == 0) {
                            sequenceScoreModifier(); // Attain whether the player got a sequence.
                            sequenceTreeSet.clear();
                            return;
                        } else {
                            gameManager.throwString = "Next"; // Set the throwString as next to differentiate between their first throw.
                        }
                        diceManager.diceNumber = (5 - sequenceTreeSet.size()); // set the amount of dice to roll with
                        throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
                        String inputTwo = scanner.nextLine();
                        String inputTwoLower = inputTwo.toLowerCase();
                        // While the user input is invalid keep prompting the user until a correct input is attained.
                        while (validityManager.throwForfeitInputIsValid(inputTwoLower) == false) {
                            System.out.println("Not a valid input.");
                            System.out.print("Eenter 't' to throw or 'f' to forfeit > ");
                            inputTwo = scanner.nextLine();
                            inputTwoLower = inputTwo.toLowerCase();
                        }

                        // If the player inputs 'f' the game will end with the player forfeiting
                        if ("f".equals(inputTwoLower)) {
                            System.out.println(gameManager.forfeitGameProcedure());
                            // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
                        } else if ("t".equals(inputTwoLower) && gameManager.playerThrowCount > 0 && sequenceTreeSet.size() < 5) {
                            gameManager.decrementThrows(); // Decrement player throw count.
                            diceManager.clearDiceList(diceManager.getDiceList()); // Clear the dice list ready for a fresh roll
                            diceManager.diceRoller();
                            System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
                            System.out.println("\n" + gameManager.throwsRemaining() + " throws remaining for this turn");
                            addDieNumberToSet();

                            System.out.println("You have selected the following dice to keep: ");
                            printSequenceSet();
                            sequenceScoreModifier(); // Attain whether the player got a sequence.
                            sequenceTreeSet.clear();

                        } else {
                            System.out.println("You have selected the following dice to keep: ");
                            printSequenceSet();
                            sequenceScoreModifier(); // Attain whether the player got a sequence.
                            sequenceTreeSet.clear();
                        }

                    }

                }
            }
        }
    }

    public void playerThrows(ArrayList list) {
        gameManager.decrementThrows(); // Decrement player throw count.
        diceManager.setDiceNumber(list); // Set the amount of dice to be rolled.
        diceManager.clearDiceList(diceManager.getDiceList()); // Clear the dice list ready for a fresh roll
        diceManager.diceRoller();
        System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
        System.out.println(gameManager.throwsRemaining() + " throws remaining for this turn");
    }

    public boolean checkForSequence() {
        return (sequenceTreeSet.equals(new TreeSet<>(java.util.Arrays.asList(1, 2, 3, 4, 5)))
                || sequenceTreeSet.equals(new TreeSet<>(java.util.Arrays.asList(2, 3, 4, 5, 6))));
    }

    public void printSequenceSet() {
        for (int integer : sequenceTreeSet) {
            System.out.print("[ " + integer + " ]");

        }
        System.out.println("\n");

    }        

    public void addDieNumberToSet() {
        int indexPlaceHolder = 0;
        for (int i : diceManager.getDiceList()) {
            if (!sequenceTreeSet.contains(i)) {
                sequenceTreeSet.add(diceManager.getDiceList().get(indexPlaceHolder));
            }
            indexPlaceHolder++;
        }
    }

    public void addFromDiceListToSequenceSet(String sequenceInput) {
        for (String number : sequenceInput.split("\\s+")) {
            int inputNumber = Integer.parseInt(number.trim());
            if (inputNumber <= 6) {
                sequenceTreeSet.add(diceManager.getDiceList().get(inputNumber - 1));
            }
        }
    }

    public void printInitialThrow() {
        System.out.println("0. None"
                + "\n1. [ " + diceManager.getDiceList().get(0) + " ]"
                + "\n2. [ " + diceManager.getDiceList().get(1) + " ]"
                + "\n3. [ " + diceManager.getDiceList().get(2) + " ]"
                + "\n4. [ " + diceManager.getDiceList().get(3) + " ]"
                + "\n5. [ " + diceManager.getDiceList().get(4) + " ]\n");
    }

    public void sequenceScoreModifier() {
        boolean sequenceAchieved = (sequenceTreeSet.equals(new TreeSet<>(java.util.Arrays.asList(1, 2, 3, 4, 5))) || sequenceTreeSet.equals(new TreeSet<>(java.util.Arrays.asList(2, 3, 4, 5, 6))));
        if (sequenceAchieved) {
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
            System.out.println("A correct sequence has been established.\n"
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
            System.out.println("A correct sequence has not been established.\n"
                    + gameManager.getCurrentTurnString() + " scores 0 for the sequence category");
        }
    }

    public int roundScoreCalculator(String player, int chosenDieNumber) {
        int totalScore = (diceManager.roundDiceListSize() * diceManager.chosenInteger);
        int turnScore = (diceManager.roundDiceListSize() * diceManager.chosenInteger);
        if (turnScore == 0) { // If the players score was 0 for a round, turnscore will show as negative, showing 0 on the scoreboard.
            turnScore = -5; // This shows that the player has chosen a die number and managed to score nothing for that round.
        }
        switch (player) {
            case "Player One":
                scoreManager.playerOneTotalScore += totalScore;
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
                break;
            case "Player Two":
                scoreManager.playerTwoTotalScore += totalScore;
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
                break;
            default:
                System.out.println("Error roundScoreCalculator 003");
                break;
        }
        return totalScore;
    }

    public void exitTurnProcedure() {
        diceManager.clearDiceList(diceManager.getDiceList());
        diceManager.clearDiceList(diceManager.getRoundDiceList());
        diceManager.clearDiceList(diceManager.getTurnDiceList());
        gameManager.playerThrowCount = 3;
        gameManager.throwString = "First";
        diceManager.diceNumber = 5;
        //System.out.println("round dice list " + diceManager.getRoundDiceList());
        //System.out.println("turn dice list " + diceManager.getTurnDiceList());
        //System.out.println("dice list " + diceManager.getDiceList());
        gameManager.changeTurn();
        System.out.println("\n" + scoreManager.returnUpdatedScoreboard());

    }

    public void selectionPrinter(String player) {
        selectionChoices.setLength(0);
        switch (player) {
            case "Player One":
                if (scoreManager.pOneScoreOnes <= 0) {
                    selectionChoices.append("Ones (1) ");
                }
                if (scoreManager.pOneScoreTwos <= 0) {
                    selectionChoices.append("Twos (2) ");
                }
                if (scoreManager.pOneScoreThrees <= 0) {
                    selectionChoices.append("Threes (3) ");
                }
                if (scoreManager.pOneScoreFours <= 0) {
                    selectionChoices.append("Fours (4) ");
                }
                if (scoreManager.pOneScoreFives <= 0) {
                    selectionChoices.append("Fives (5) ");
                }
                if (scoreManager.pOneScoreSixes <= 0) {
                    selectionChoices.append("Sixes (6) ");
                }
                if (scoreManager.pOneSequenceScore <= 0) {
                    selectionChoices.append("Sequence (7) ");
                }
                break;
            case "Player Two":
                if (scoreManager.pTwoScoreOnes <= 0) {
                    selectionChoices.append("Ones (1) ");
                }
                if (scoreManager.pTwoScoreTwos <= 0) {
                    selectionChoices.append("Twos (2) ");
                }
                if (scoreManager.pTwoScoreThrees <= 0) {
                    selectionChoices.append("Threes (3) ");
                }
                if (scoreManager.pTwoScoreFours <= 0) {
                    selectionChoices.append("Fours (4) ");
                }
                if (scoreManager.pTwoScoreFives <= 0) {
                    selectionChoices.append("Fives (5) ");
                }
                if (scoreManager.pTwoScoreSixes <= 0) {
                    selectionChoices.append("Sixes (6) ");
                }
                if (scoreManager.pTwoSequenceScore <= 0) {
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
    
    public void addToChosenNumbers(String player, int number) {
        switch (player) {
            case "Player One":
                validityManager.playerOneChosenNumbers.add(number);
                break;
            case "Player Two":
                validityManager.playerTwoChosenNumbers.add(number);
                break;
            default:
                System.out.println("Error addToChosenNumbers 001");
                break;
        }
        System.out.println("player " + player + "chosen integers " + validityManager.playerTwoChosenNumbers);
    }

    public void throwInformation() {
        System.out.print(gameManager.throwString + " throw of this turn, " + gameManager.getCurrentTurnString() + " to throw " + diceManager.diceNumber + " dice."
                + "\nThrow " + diceManager.diceNumber + " dice, enter 't' to throw or 'f' to forfeit > ");
    }

    public void throwOutcomeInformation() {
        System.out.println("\nThat throw had " + diceManager.turnDiceListSize() + " dice with value "
                + diceManager.chosenInteger + ". Setting aside " + diceManager.roundDiceListSize() + " dice: "
                + diceManager.printDiceList(diceManager.getRoundDiceList()) + "\n");
    }

    public void endOfPlayerTurnInformation() {
        int roundscore = roundScoreCalculator(gameManager.getCurrentTurnString(), diceManager.chosenInteger);
        if (roundscore < 0) {
            System.out.println(gameManager.getCurrentTurnString() + " made " + diceManager.roundDiceListSize()
                    + " with value " + diceManager.chosenInteger
                    + " and scores 0 for that round");
        } else {
            System.out.println(gameManager.getCurrentTurnString() + " made " + diceManager.roundDiceListSize()
                    + " with value " + diceManager.chosenInteger
                    + " and scores " + roundscore + " for that round");
        }
    }

    public void playOrExitGame() {
        System.out.print("Play game (1) or Exit game (0) > ");
        String playGame = scanner.nextLine();
        while (validityManager.startGameIsValid(playGame) == false) {
            System.out.println("Not a valid input.");
            System.out.print("Play game (1) or Exit game (0) > ");
            playGame = scanner.nextLine();
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
                    + ". Player Two has finished with a total score of: " + scoreManager.playerTwoTotalScore);
        } else {
            System.out.println("The game has ended, Player Two wins! and finished with a score of: " + scoreManager.playerTwoTotalScore
                    + ". Player One has finished with a total score of: " + scoreManager.playerOneTotalScore);
        }
    }

}
