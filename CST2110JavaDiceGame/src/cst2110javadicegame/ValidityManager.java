package cst2110javadicegame;

import java.util.HashSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class ValidityManager {

    public HashSet<Integer> playerOneChosenNumbers = new HashSet<>();
    public HashSet<Integer> playerTwoChosenNumbers = new HashSet<>();
    public HashSet<Integer> playersNumbers = new HashSet<>();

    public boolean startGameIsValid(String number) {
        String regex = "^[01]$"; // A set of rules for the number. (1 or 0) and 1 character long.
        Pattern pattern = Pattern.compile(regex); // Compile the regex into a pattern.
        return pattern.matcher(number).matches(); // Return boolean true if the number matches the pattern.
    }

    public boolean throwForfeitInputIsValid(String inputCharacter) {
        String regex = "^[ft]$"; // A set of rules for the character. (f or t) and 1 character long.
        Pattern pattern = Pattern.compile(regex); // Compile the regex into a pattern.
        return pattern.matcher(inputCharacter).matches(); // Return boolean true if the character matches the pattern.
    }

    public boolean gameIntInputIsValid(String inputNumber) {
        String regex = "^[1234567]$"; // A set of rules for the number. (1 to 7) and 1 character long.
        Pattern pattern = Pattern.compile(regex); // Compile the regex into a pattern.
        return pattern.matcher(inputNumber).matches(); // Return boolean true if the number matches the pattern.
    }

    public boolean selectDeferInputIsValid(String inputCharacter) {
        String regex = "^[sd]$"; // A set of rules for the character. (s or d) and 1 character long. 
        Pattern pattern = Pattern.compile(regex); // Compile the regex into a pattern.
        return pattern.matcher(inputCharacter).matches(); // Return boolean true if the character matches the pattern.
    }

    public boolean sequenceIntInputIsValid(String sequenceInput, int diceListSize) {
        String regex = "^[012345 ]{1,9}$"; // A set of rules for the sequence input. (0 to 5 including spaces) and between 1 and 9 characters long.
        Pattern pattern = Pattern.compile(regex); // Compile the regex into a pattern.
        boolean patternMatcherCheck = pattern.matcher(sequenceInput).matches(); // A boolean to show whether the user input matches the regex
        boolean integerSizeCheck = true; // A boolean to show whether the integers are the right size (single digit integers)
        boolean inputFailureCheck = true; // A boolean to show whether there is a zero amongst other numbers enterred.

        if (patternMatcherCheck) { // If the input matches the pattern proceed.
            for (String number : sequenceInput.split("\\s+")) { // For each number, seperated with a space in the input, split the numbers into their own strings
                int inputNumber = Integer.parseInt(number.trim()); // This then pushes the given string into an int after trimming
                if (inputNumber > diceListSize) { // If the number is greater than 5 the boolean integerSizeCheck will become false 
                    integerSizeCheck = false;
                }
            }
            for (String number : sequenceInput.split("\\s+")) { // For each number, seperated with a space in the input, split the numbers into their own strings
                int inputNumber = Integer.parseInt(number.trim()); // This then pushes the given string into an int after trimming
                if (inputNumber == 0 && sequenceInput.length() > 1) { // If the number is greater than 5 the boolean integerSizeCheck will become false 
                    inputFailureCheck = false;
                }
            }
        }
        return patternMatcherCheck && integerSizeCheck && inputFailureCheck; // Return boolean true if the sequence of numebrs matches the pattern and each input is a digit less than 5.
    }

    public boolean hasNumberBeenChosen(String inputNumber, String player) {
        boolean hasBeenChosen = false;
        int userChosenNumber = Integer.parseInt(inputNumber);
        switch (player) {
            case "Player One":
                if (playerOneChosenNumbers.contains(userChosenNumber)) {
                    hasBeenChosen = true;
                }
                break;
            case "Player Two":
                if (playerTwoChosenNumbers.contains(userChosenNumber)) {
                    hasBeenChosen = true;
                }
                break;
            default:
                System.out.println("Error hasNumberBeenChosen 001");
                break;
        }
        System.out.println(hasBeenChosen);
        return hasBeenChosen;
    }

}
