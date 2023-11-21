package cst2110javadicegame;

import java.util.regex.Pattern;

public class ValidityManager {
    
    
    public boolean startGameIsValid(String number) {
        // set of rules for the word. (a to z) and 3 characters long.
        String regex = "^[01]$";

        // Compile the regex into a pattern.
        Pattern pattern = Pattern.compile(regex);

        // Check the word matches the pattern.
        return pattern.matcher(number).matches();

    }
    
    public boolean throwForfeitInputIsValid(String inputCharacter) {
        // set of rules for the word. (a to z) and 3 characters long.
        String regex = "^[ft]$";

        // Compile the regex into a pattern.
        Pattern pattern = Pattern.compile(regex);

        // Check the word matches the pattern.
        return pattern.matcher(inputCharacter).matches();

    }
    
    public boolean gameIntInputIsValid(String inputNumber) {
        // set of rules for the word. (a to z) and 3 characters long.
        String regex = "^[1234567]$";

        // Compile the regex into a pattern.
        Pattern pattern = Pattern.compile(regex);

        // Check the word matches the pattern.
        return pattern.matcher(inputNumber).matches();

    }
    public boolean sequenceIntInputIsValid(String inputNumber) {
        // set of rules for the word. (a to z) and 3 characters long.
        String regex = "^[012345 ]{1,9}$";

        // Compile the regex into a pattern.
        Pattern pattern = Pattern.compile(regex);

        // Check the word matches the pattern.
        return pattern.matcher(inputNumber).matches();

    }
    
    public boolean selectDeferInputIsValid(String inputCharacter){
        // set of rules for the word. (a to z) and 3 characters long.
        String regex = "^[sd]$";

        // Compile the regex into a pattern.
        Pattern pattern = Pattern.compile(regex);

        // Check the word matches the pattern.
        return pattern.matcher(inputCharacter).matches();
    }
    
}
