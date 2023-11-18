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
    
    public boolean throwForfeitInputIsValid(String input) {
        // set of rules for the word. (a to z) and 3 characters long.
        String regex = "^[ft]$";

        // Compile the regex into a pattern.
        Pattern pattern = Pattern.compile(regex);

        // Check the word matches the pattern.
        return pattern.matcher(input).matches();

    }
    
    public boolean gameIntInputIsValid(String number) {
        // set of rules for the word. (a to z) and 3 characters long.
        String regex = "^[1234567]$";

        // Compile the regex into a pattern.
        Pattern pattern = Pattern.compile(regex);

        // Check the word matches the pattern.
        return pattern.matcher(number).matches();

    }
    
}
