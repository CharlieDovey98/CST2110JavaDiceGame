package cst2110javadicegame;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

// This class holds the main game code and will call on other classes for their respective responsibilities.
// This class also has several methods which use other class' functions and variables for the game.
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
        System.out.println("This is a two player, Strategic dice game." // A print out of the game rules and objectives.
                + "\nPlayers will take it in turns to roll 5 dice,"
                + "\nchoosing a die number and scoring as many points as possible by attaining the max number of that die."
                + "\nPlayers must attempt for a sequence within the game,\nattaining a run of either (1,2,3,4,5) or (2,3,4,5,6) for 20 points."
                + "\nThe highest scoring player wins the game after 7 rounds!\n");

        playOrExitGame(); // Prompt the user to play or exit the game.

        while (gameManager.gameLoop() == false) { // While the games rounds havent complete nor a player forfeit, continue.
            if (gameManager.roundPresented == false) { // Present the game round Print out whilst round presented == false.
                System.out.println("---------\nRound " + gameManager.roundNumber + " |\n---------\n");
                gameManager.roundPresented = true;
            }
            if ("Player One".equals(gameManager.getCurrentTurnString()) && gameManager.forfeit != true) { // If its player One's turn call the playerTurn Function.
                playerTurn();
            }
            if ("Player Two".equals(gameManager.getCurrentTurnString()) && gameManager.forfeit != true) { // If its player Two's turn call the playerTurn Function.
                playerTurn();
            }
            gameManager.roundNumber += 1;  // Increment the round number by 1.
            gameManager.roundPresented = false; // Set the round presented to false, to show on the next round.
        }
        if (!gameManager.forfeit) { // If a player should forfeit, call the gameEndProcedure to print the winner.
            gameManager.gameEndProcedure(scoreManager.playerOneTotalScore, scoreManager.playerTwoTotalScore);
        }
    }

    // This method contains a players turn. This will be called a total of 14 times within the game as there are 7 rounds.
    public void playerTurn() {
        if (gameManager.playerThrowCount == 1 && !(diceManager.roundDiceListSize() == 5)) {
            gameManager.throwString = "Final";
            if (gameManager.categorySelected == false) {
                playerFinalThrow();
            } else {
                playerNextThrow();
                endOfPlayerTurnInformation();
                exitTurnProcedure();
                gameManager.categorySelected = false;
            }
        } else if (gameManager.playerThrowCount == 2 && !(diceManager.roundDiceListSize() == 5)) {
            gameManager.throwString = "Next";
            if (gameManager.categorySelected == false) {
                playerFirstThrow();
            } else {
                playerNextThrow();
                playerTurn();
            }
        } else if (gameManager.playerThrowCount == 3) {
            gameManager.throwString = "First";
            playerFirstThrow();
        } else {
            endOfPlayerTurnInformation();
            exitTurnProcedure();
            gameManager.categorySelected = false;
        }
    }

    // This method is the first throw of a players turn.
    public void playerFirstThrow() {
        throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
        String inputOne = scanner.nextLine().trim();// Attain the user input and push it to lowercase.
        String inputOneLower = inputOne.toLowerCase();
        while (validityManager.throwForfeitInputIsValid(inputOneLower) == false) { // while user input is invalid keep prompting the user until a valid input is attained.
            System.out.println("Not a valid input.");
            throwInformation();
            inputOne = scanner.nextLine().trim();
            inputOneLower = inputOne.toLowerCase();
        }
        if ("f".equals(inputOneLower)) { // If the player inputs 'f' the forfeit game procedure will initiate.
            System.out.println(gameManager.forfeitGameProcedure());
        }
        if ("t".equals(inputOneLower)) { // If the player inputs 't' the players turn proceeds.
            playerThrows(diceManager.getRoundDiceList());
            System.out.print("Enter 's' to select category or 'd' to defer and re-roll > ");
            String inputTwo = scanner.nextLine().trim();
            String inputTwoLower = inputTwo.toLowerCase();
            // While the user input is invalid keep prompting the user until a correct input is attained.
            while (validityManager.selectDeferInputIsValid(inputTwoLower) == false) {
                System.out.println("Not a valid input.");
                System.out.print("Enter 's' to select category or 'd' to defer and re-roll > ");
                inputTwo = scanner.nextLine().trim();
                inputTwoLower = inputTwo.toLowerCase();
            }
            if ("d".equals(inputTwoLower)) { // If the player inputs 'd' the turn is deferred 
                System.out.println("\nyou have chosen to re-roll your dice, disregarding this throw.");
                playerTurn();
            }
            if ("s".equals(inputTwoLower)) { // If the player inputs 's' the user will be prompted to select a category that they havent already chosen.
                System.out.println("Select a category not chosen before to play.\n");
                selectionPrinter(gameManager.getCurrentTurnString());
                String inputThree = scanner.nextLine().trim();
                // While the user input is invalid keep prompting the user until a correct input is attained.
                while (validityManager.gameIntInputIsValid(inputThree) == false || validityManager.hasNumberBeenChosen(inputThree, gameManager.getCurrentTurnString()) == true) {
                    System.out.println("Not a valid input.");
                    System.out.println("Select a category not chosen before to play.\n");
                    selectionPrinter(gameManager.getCurrentTurnString()); // Print out the choices left over for the user.
                    inputThree = scanner.nextLine().trim();
                }
                if ("7".equals(inputThree)) { // If the player inputs '7' the sequence turn will be initiated.
                    diceManager.switchOnInput(inputThree); // Switch statement to retrieve the chosen die number from the user.
                    addToChosenNumbers(gameManager.getCurrentTurnString(), Integer.parseInt(inputThree)); // Add the chosen number to the players Chosen Numbers Hash Set.
                    sequence(); // call for the Sequence procedure.
                    exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
                } else {
                    diceManager.switchOnInput(inputThree); // Switch statement to retrieve the chosen die number from the user.
                    addToChosenNumbers(gameManager.getCurrentTurnString(), Integer.parseInt(inputThree)); // Add the chosen number to the players Chosen Numbers Hash Set.
                    diceManager.diceTurnListInserter(); // insert into the turn dice list.
                    diceManager.diceRoundListInserter(); // insert into the round dice list.
                    throwOutcomeInformation(); // Print matched dice from that throw, the amount of dice set aside and the 'round dice list' containing all matched dice.
                    diceManager.clearDiceList(diceManager.getTurnDiceList());
                    gameManager.categorySelected = true;
                    playerTurn();
                }
            }
        }
    }

    // This method is another throw of a players turn.
    public void playerNextThrow() {
        throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
        String inputFour = scanner.nextLine().trim();
        String inputFourLower = inputFour.toLowerCase();
        // While the user input is invalid keep prompting the user until a correct input is attained.
        while (validityManager.throwForfeitInputIsValid(inputFourLower) == false) {
            System.out.println("Not a valid input.");
            System.out.print("Enter 't' to throw or 'f' to forfeit > ");
            inputFour = scanner.nextLine().trim();
            inputFourLower = inputFour.toLowerCase();
        }
        if ("f".equals(inputFourLower)) { // If the player inputs 'f' the forfeit game procedure will initiate.
            System.out.println(gameManager.forfeitGameProcedure());
        }
        if ("t".equals(inputFourLower)) { // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
            playerThrows(diceManager.getRoundDiceList());
            diceManager.diceTurnListInserter();
            diceManager.diceRoundListInserter();
            throwOutcomeInformation(); // Print matched dice from that throw, the amount of dice set aside and the 'round dice list' containing all matched dice.
            diceManager.clearDiceList(diceManager.getTurnDiceList());
        }
    }

    // This method is another throw of a players turn.
    public void playerFinalThrow() {
        throwInformation();
        String inputNine = scanner.nextLine().trim();// Attain the user input and force it to lowercase.
        String inputNineLower = inputNine.toLowerCase();
        while (validityManager.throwForfeitInputIsValid(inputNineLower) == false) {
            System.out.println("Not a valid input.");
            throwInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
            inputNine = scanner.nextLine().trim();
            inputNineLower = inputNine.toLowerCase();
        }
        if ("f".equals(inputNineLower)) { // If the player inputs 'f' the forfeit game procedure will initiate.
            System.out.println(gameManager.forfeitGameProcedure());
        }
        if ("t".equals(inputNineLower)) { // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
            playerThrows(diceManager.getRoundDiceList());
            System.out.print("Enter 's' to select category > ");
            String inputTen = scanner.nextLine().trim();
            String inputTenLower = inputTen.toLowerCase();
            // While the user input is invalid keep prompting the user until a correct input is attained.
            while (!"s".equals(inputTenLower)) {
                System.out.println("Not a valid input.");
                System.out.print("Enter 's' to select category > ");
                inputTen = scanner.nextLine().trim();
                inputTenLower = inputTen.toLowerCase();
            }
            if ("s".equals(inputTenLower)) {
                System.out.println("Select a category not chosen before to play.\n");
                selectionPrinter(gameManager.getCurrentTurnString());
                String userInput = scanner.nextLine().trim();
                // While the user input is invalid keep prompting the user until a correct input is attained.
                while (validityManager.gameIntInputIsValid(userInput) == false || validityManager.hasNumberBeenChosen(userInput, gameManager.getCurrentTurnString()) == true) {
                    System.out.println("Not a valid input.");
                    System.out.println("Select a category not chosen before to play.\n");
                    selectionPrinter(gameManager.getCurrentTurnString());
                    userInput = scanner.nextLine().trim();
                }
                if ("7".equals(userInput)) {
                    diceManager.switchOnInput(userInput); // Switch statement to retrieve the chosen die number from the user
                    addToChosenNumbers(gameManager.getCurrentTurnString(), Integer.parseInt(userInput)); // Add the chosen number to the players Chosen Numbers Hash Set
                    sequence(); // call for the Sequence procedure
                    exitTurnProcedure(); // The player has finished their turn, Run the nessecary tasks to setup the next turn.
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
                }
            }
        }
    }

    // This method controls the players sequence turn based on the amount of throws they have. 
    public void sequence() {
        if (gameManager.playerThrowCount == 0) { // if no more throws remaining attaempt a sequence with the dice list
            addDieNumberToSet();
            System.out.println("Your sequence attempt: ");
            printSequenceSet();
            sequenceScoreModifier(); // Attain whether the player got a sequence.
            sequenceTreeSet.clear();
            return;
        }
        if (gameManager.playerThrowCount == 1) { // if 1 throw remaining, select what to keep and throw again for a sequence.
            gameManager.throwString = "Final";
            sequenceTurn();
        }
        if (gameManager.playerThrowCount == 2) { // if 2 throws remaining, run the sequence .
            gameManager.throwString = "Next";
            sequenceTurn();
        }
    }

    // This method holds the sequence turn for a player, called by the sequence functon
    public void sequenceTurn() {
        printInitialThrow(); // Print the players first throw formatted on a new line for each integer with a corresponding index number.
        System.out.print("Choose which dice you wish to set aside.\nEnter a number seperated by a space (e.g. >1 3 4 5) > ");
        String sequenceInput = scanner.nextLine().trim();
        // While the user input is invalid keep prompting the user until a correct input is attained.
        while (validityManager.sequenceIntInputIsValid(sequenceInput, diceManager.diceListSize()) == false) {
            System.out.println("Not a valid input.");
            System.out.print("Choose which dice you wish to set aside.\nEnter a number seperated by a space (e.g. >1 3 4 5) > ");
            sequenceInput = scanner.nextLine().trim();
        }
        if ("0".equals(sequenceInput)) { // If the user input is 0 the user has chosen to defer their throw.
            System.out.println("\nyou have chosen to take none of the dice from this roll. Reroll: ");
            diceManager.diceNumber = (5 - sequenceTreeSet.size());
            gameManager.decrementThrows(); // Decrement player throw count.
            diceManager.clearDiceList(diceManager.getDiceList()); // Clear the dice list ready for a fresh roll
            diceManager.diceRoller();
            System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
            gameManager.throwsRemainingOutput(); // Print the remaining throws the player has.
            sequence();
        } else {
            addFromDiceListToSequenceSet(sequenceInput);
            System.out.println("You have selected the following dice to keep: ");
            printSequenceSet();
            if (checkForSequence()) {
                sequenceScoreModifier(); // calculate the score and set variables for the players sequence turn.
                sequenceTreeSet.clear();
            } else if (sequenceTreeSet.size() == 5) {
                System.out.println("Your Sequence list has accumilated the maximum number of dice possible (5)");
                sequenceScoreModifier(); // calculate the score and set variables for the players sequence turn.
                sequenceTreeSet.clear();
            } else {
                sequenceThrowInformation(); // Print initial information and prompt the user to 't' throw of 'f' forfeit.
                String inputTwo = scanner.nextLine().trim();
                String inputTwoLower = inputTwo.toLowerCase();
                // While the user input is invalid keep prompting the user until a correct input is attained.
                while (validityManager.throwForfeitInputIsValid(inputTwoLower) == false) {
                    System.out.println("Not a valid input.");
                    System.out.print("Eenter 't' to throw or 'f' to forfeit > ");
                    inputTwo = scanner.nextLine().trim();
                    inputTwoLower = inputTwo.toLowerCase();
                }
                // If the player inputs 'f' the game will end with the player forfeiting
                if ("f".equals(inputTwoLower)) {
                    System.out.println(gameManager.forfeitGameProcedure());
                }
                // If the player inputs 't' and they have turns left, the throw dice procedure will initiate.
                if ("t".equals(inputTwoLower)) {
                    gameManager.decrementThrows(); // Decrement player throw count.
                    diceManager.clearDiceList(diceManager.getDiceList()); // Clear the dice list ready for a fresh roll
                    diceManager.diceRoller();
                    System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList()));
                    gameManager.throwsRemainingOutput(); // Print the remaining throws the player has.
                    sequence();
                }
            }
        }
    }

    // This method holds the necessary functions for a player to throw the dice.
    public void playerThrows(ArrayList list) {
        gameManager.decrementThrows(); // Decrement player throw count.
        diceManager.setDiceNumber(list); // Set the amount of dice to be rolled.
        diceManager.clearDiceList(diceManager.getDiceList()); // Clear the dice list ready for a fresh roll.
        diceManager.diceRoller(); // Roll the dice.
        System.out.println("\nThrow outcome: " + diceManager.printDiceList(diceManager.getDiceList())); // Print the outcome of the dice roll.
        gameManager.throwsRemainingOutput(); // Print the remaining throws the player has.
    }

    // A function to return whether the sequenceTreeSet matches either sequence array.
    public boolean checkForSequence() {
        return (sequenceTreeSet.equals(new TreeSet<>(java.util.Arrays.asList(1, 2, 3, 4, 5)))
                || sequenceTreeSet.equals(new TreeSet<>(java.util.Arrays.asList(2, 3, 4, 5, 6))));
    }

    // A function to print out the selected integers as the player tries to form a sequence.
    public void printSequenceSet() {
        for (int integer : sequenceTreeSet) {
            System.out.print("[ " + integer + " ]");
        }
        System.out.println("\n");
    }

    // A function to add the diceList integers to the sequenceTreeSet if they have not yet already been added.
    public void addDieNumberToSet() {
        int indexPlaceHolder = 0;
        for (int i : diceManager.getDiceList()) {
            if (!sequenceTreeSet.contains(i)) {
                sequenceTreeSet.add(diceManager.getDiceList().get(indexPlaceHolder));
            }
            indexPlaceHolder++;
        }
    }

    // A function attain the users chosen sequence input, seperate the input into integers and pass the correct number to the sequenceTreeSet.
    public void addFromDiceListToSequenceSet(String sequenceInput) {
        for (String number : sequenceInput.split("\\s+")) {
            int inputNumber = Integer.parseInt(number.trim());
            if (inputNumber <= 6) {
                sequenceTreeSet.add(diceManager.getDiceList().get(inputNumber - 1));
            }
        }
    }

    // A function to print out the rolled dice integers along with their corresponding place.
    public void printInitialThrow() {
        System.out.println("\nChoices:\n" + "0. None");
        if (diceManager.diceListSize() == 5) {
            System.out.print("1. [ " + diceManager.getDiceList().get(0) + " ]"
                    + "\n2. [ " + diceManager.getDiceList().get(1) + " ]"
                    + "\n3. [ " + diceManager.getDiceList().get(2) + " ]"
                    + "\n4. [ " + diceManager.getDiceList().get(3) + " ]"
                    + "\n5. [ " + diceManager.getDiceList().get(4) + " ]\n");
        }
        if (diceManager.diceListSize() == 4) {
            System.out.print("1. [ " + diceManager.getDiceList().get(0) + " ]"
                    + "\n2. [ " + diceManager.getDiceList().get(1) + " ]"
                    + "\n3. [ " + diceManager.getDiceList().get(2) + " ]"
                    + "\n4. [ " + diceManager.getDiceList().get(3) + " ]\n");
        }
        if (diceManager.diceListSize() == 3) {
            System.out.print("1. [ " + diceManager.getDiceList().get(0) + " ]"
                    + "\n2. [ " + diceManager.getDiceList().get(1) + " ]"
                    + "\n3. [ " + diceManager.getDiceList().get(2) + " ]\n");
        }
        if (diceManager.diceListSize() == 2) {
            System.out.print("1. [ " + diceManager.getDiceList().get(0) + " ]"
                    + "\n2. [ " + diceManager.getDiceList().get(1) + " ]\n");
        }
        if (diceManager.diceListSize() == 1) {
            System.out.print("1. [ " + diceManager.getDiceList().get(0) + " ]\n");
        }
    }

    // A function to set the players sequence score and associated string to allow the players total score and scoreboard to accurately reflect the outcome.
    public void sequenceScoreModifier() {
        boolean sequenceAchieved = (sequenceTreeSet.equals(new TreeSet<>(java.util.Arrays.asList(1, 2, 3, 4, 5)))
                || sequenceTreeSet.equals(new TreeSet<>(java.util.Arrays.asList(2, 3, 4, 5, 6))));
        if (sequenceAchieved) { // If the sequence has been achieved switch on the 'Player' and update some variables.
            switch (gameManager.getCurrentTurnString()) {
                case "Player One":
                    scoreManager.pOneSequence = "ACHIEVED";
                    scoreManager.pOneSequenceAttempted = true;
                    scoreManager.playerOneTotalScore += 20;
                    break;
                case "Player Two":
                    scoreManager.pTwoSequence = "ACHIEVED";
                    scoreManager.pTwoSequenceAttempted = true;
                    scoreManager.playerTwoTotalScore += 20;
                    break;
                default:
                    System.out.println("Error Sequence Method 001");
                    break;
            }
            System.out.println("A correct sequence (1,2,3,4,5) / (2,3,4,5,6) has been established.\n"
                    + gameManager.getCurrentTurnString() + " scores 20 for the sequence category");
        } else { // Else switch on the 'Player' and update some variables for not achieving the sequence
            switch (gameManager.getCurrentTurnString()) {
                case "Player One":
                    scoreManager.pOneSequence = "MISSED";
                    scoreManager.pOneSequenceAttempted = true;
                    break;
                case "Player Two":
                    scoreManager.pTwoSequence = "MISSED";
                    scoreManager.pTwoSequenceAttempted = true;
                    break;
                default:
                    System.out.println("Error Sequence Method 002");
                    break;
            }
            System.out.println("A correct sequence (1,2,3,4,5) / (2,3,4,5,6) has not been established.\n"
                    + gameManager.getCurrentTurnString() + " scores 0 for the sequence category");
        }
    }

    // A function to set the players score for that round based on their chosen integer and diceList size.
    public int roundScoreCalculator(String player, int chosenDieNumber) {
        int totalScore = (diceManager.roundDiceListSize() * diceManager.chosenInteger);
        int turnScore = (diceManager.roundDiceListSize() * diceManager.chosenInteger);
        if (turnScore == 0) { // If the players score was 0 for a round, turnscore will show as negative, showing 0 on the scoreboard.
            turnScore = -5; // This shows that the player has chosen a die number and managed to score nothing for that round.
        }
        switch (player) {
            case "Player One":
                scoreManager.playerOneTotalScore += totalScore; // Update the players totalScore.
                switch (chosenDieNumber) {
                    case 1:
                        scoreManager.pOneScoreOnes = turnScore; // Update the players turnScore.
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
                        System.out.println("Error roundScoreCalculator 001"); // An error statement under the default case.
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

    // A function to run the nessecary tasks to setup the next turn as the player has completed their turn.
    public void exitTurnProcedure() {
        diceManager.clearDiceList(diceManager.getDiceList()); // Clears the diceList array.
        diceManager.clearDiceList(diceManager.getRoundDiceList());  // Clears the roundDiceList array.
        diceManager.clearDiceList(diceManager.getTurnDiceList()); // Clear the turnDiceList array.
        gameManager.playerThrowCount = 3; // Reset the players throw count for the next player.
        gameManager.throwString = "First"; // Reset the throw String to "First".
        diceManager.diceNumber = 5; // Reset the diceNumber which is used to 
        gameManager.changeTurn(); // Change the players turn
        System.out.println("\n" + scoreManager.returnUpdatedScoreboard());
    }

    // A function to print out a stringBuilder of the numbers the player has left to choose. 
    public void selectionPrinter(String player) {
        selectionChoices.setLength(0);
        switch (player) { // Switch on the 'player' and if player is equal to "Player One" append their choices to the StringBuilder 'selectionchoices'
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
                if (scoreManager.pOneSequenceAttempted == false) {
                    selectionChoices.append("Sequence (7) ");
                }
                break;
            case "Player Two": // If player is equal to "Player Two" append their choices to the StringBuilder 'selectionchoices'
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
                if (scoreManager.pTwoSequenceAttempted == false) {
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

    // A function to add the players chosen number for the round to an array.
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
    }

    // A function to set the dice number using the roundDiceList. Also to print some necessary information for the player and promt them for an input.
    public void throwInformation() {
        diceManager.setDiceNumber(diceManager.getRoundDiceList()); // Set the dicenumber 
        System.out.print(gameManager.throwString + " throw of this turn, " + gameManager.getCurrentTurnString() + " to throw " + diceManager.diceNumber + " dice."
                + "\nThrow " + diceManager.diceNumber + " dice, enter 't' to throw or 'f' to forfeit > ");
    }

    // A function to set the dice number for the user, using the sequenceTreeSet' size. Also to print some necessary information for the player and promt them for an input.
    public void sequenceThrowInformation() {
        diceManager.diceNumber = (5 - sequenceTreeSet.size()); // Set the diceNumber
        System.out.print(gameManager.throwString + " throw of this turn, " + gameManager.getCurrentTurnString() + " to throw " + diceManager.diceNumber + " dice."
                + "\nThrow " + diceManager.diceNumber + " dice, enter 't' to throw or 'f' to forfeit > ");
    }

    // A function to print out the throw outcome information, which includes how many dice they managed to match and  how many dice are then to be set aside.
    public void throwOutcomeInformation() {
        System.out.println("\nThat throw had " + diceManager.turnDiceListSize() + " dice with value "
                + diceManager.chosenInteger + ". Setting aside " + diceManager.roundDiceListSize() + " dice: "
                + diceManager.printDiceList(diceManager.getRoundDiceList()) + "\n");
    }

    // A function to print what the player rolled and scored for that turn. if they havent managed to score anything for that turn the first if statement will be true.
    public void endOfPlayerTurnInformation() {
        int roundscore = roundScoreCalculator(gameManager.getCurrentTurnString(), diceManager.chosenInteger);
        if (roundscore < 0) {
            System.out.println(gameManager.getCurrentTurnString() + " made " + diceManager.roundDiceListSize()
                    + " dice with value " + diceManager.chosenInteger
                    + " and scores 0 for that round");
        } else {
            System.out.println(gameManager.getCurrentTurnString() + " made " + diceManager.roundDiceListSize()
                    + " dice with value " + diceManager.chosenInteger
                    + " and scores " + roundscore + " for that round");
        }
    }

    // A function which starts or ends the game. This function promtps the user to enter '1' or '0' to play of quit.
    public void playOrExitGame() {
        System.out.print("Play game (1) or Exit game (0) > ");
        String playGame = scanner.nextLine().trim();
        while (validityManager.startGameIsValid(playGame) == false) { // While loop until a valid input is attained
            System.out.println("Not a valid input.");
            System.out.print("Play game (1) or Exit game (0) > ");
            playGame = scanner.nextLine().trim();
        }
        if ("0".equals(playGame)) {
            gameManager.forfeit = true;
            System.out.println("Game Exited");
        } else {
            System.out.println("\n" + scoreManager.returnUpdatedScoreboard());
        }
    }
}
