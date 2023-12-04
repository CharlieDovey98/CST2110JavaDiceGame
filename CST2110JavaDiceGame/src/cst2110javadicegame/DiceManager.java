package cst2110javadicegame;

import java.util.ArrayList;
import java.util.Random;

// A class to hold the necessary information for the Dice, including functions to roll, add, and clear several dice lists integral to the game.
public class DiceManager {

    // The instantiation of key variables for the Dice Manager class
    public int chosenInteger = 1;
    public String chosenIntegerString = " ";
    public int diceNumber = 5;

    // The instantiation of final Strings to be used within funtions in this class. These strings will be used within a Switch case below.
    final String ONES = "1";
    final String TWOS = "2";
    final String THREES = "3";
    final String FOURS = "4";
    final String FIVES = "5";
    final String SIXES = "6";
    final String SEQUENCE = "7";

    // Private ArrayLists needed to hold the dice rolled, Dice collected from a turn 
    private ArrayList<Integer> roundDiceList = new ArrayList<>();
    private ArrayList<Integer> turnMatchedDiceList = new ArrayList<>();
    private ArrayList<Integer> diceList = new ArrayList<>();

    // A public function to clear the list of rolled dice.
    public void clearDiceList(ArrayList dicelist) {
        dicelist.clear();
    }

    // This function creates randomly generated integers and adds them to the 'diceList'.
    public void diceRoller() {
        Random randomNumberGenerator = new Random(); // Instantiating a new object to hold the random numbers.
        for (int i = 0; i < diceNumber; i++) { // A for loop to add the randomDiceNumber to the diceList
            int randomDiceNumber = randomNumberGenerator.nextInt(6) + 1; // .nextInt needs to be (6) + 1 as java is a 0 indexing language
            diceList.add(randomDiceNumber);
        }
    }

    // A function to insert the players chosen integer into the turnMatchedDiceList where the number in diceList matches the chosenInteger.
    public void diceTurnListInserter() {
        for (int i = 0; i < diceNumber; i++) { // A for loop starting at 0, stopping at the dice number and incrementing by 1.
            if (diceList.get(i) == chosenInteger) { // if the number at i in diceList is equal to the chosenInteger add it to the turnMatchedDiceList.
                turnMatchedDiceList.add(chosenInteger);
            }
        }
    }

    // A Function to insert the players chosenInteger into the roundDiceList.
    public void diceRoundListInserter() {
        for (int i = 0; i < turnMatchedDiceList.size(); i++) { // A for loop to add the players chosenInteger to the roundDiceList.
            roundDiceList.add(chosenInteger);
        }
    }

    // A function to print out the diceList in a String where the numbers are surrounded by '[' i ']'.
    public StringBuilder printDiceList(ArrayList dicelist) {
        StringBuilder list = new StringBuilder(); // Instantiating a StringBuilder for the list of die's.
        list.setLength(0);
        for (int i = 0; i < dicelist.size(); i++) { // A for loop to append the string format to a StringBuilder 'list'.
            list.append("[ ").append(dicelist.get(i)).append(" ] ");
        }
        return list;
    }

    // A function to return the diceList as an Array.
    public ArrayList<Integer> getDiceList() {
        return diceList;
    }

    // A function to return the roundDiceList as an Array.
    public ArrayList<Integer> getRoundDiceList() {
        return roundDiceList;
    }

    // A function to return the turnMatchedDiceList as an Array.
    public ArrayList<Integer> getTurnDiceList() {
        return turnMatchedDiceList;
    }

    // A function to return the turnMatchedDiceList' size as an int, which would range from 0 - 5.
    public int turnDiceListSize() {
        return turnMatchedDiceList.size();
    }

    // A function to return the roundDiceList' size as an int, which would range from 0 - 5.
    public int roundDiceListSize() {
        return roundDiceList.size();
    }

    // A function to return the diceList' size as an int, which would range from 0 - 5.
    public int diceListSize() {
        return diceList.size();
    }

    // A function to set the diceNumber variable to (5 - the list that is passed into the function).
    public void setDiceNumber(ArrayList list) {
        diceNumber = (5 - list.size());
    }

    // A function which when passed an input string from the player, will update the chosen integer and its String representation.
    // This function will also print to the console which number the player has chosen.
    public void switchOnInput(String input) {
        switch (input) {
            case ONES:
                chosenIntegerString = "Ones";
                chosenInteger = 1;
                break;
            case TWOS:
                chosenIntegerString = "Twos";
                chosenInteger = 2;
                break;
            case THREES:
                chosenIntegerString = "Threes";
                chosenInteger = 3;
                break;
            case FOURS:
                chosenIntegerString = "Fours";
                chosenInteger = 4;
                break;
            case FIVES:
                chosenIntegerString = "Fives";
                chosenInteger = 5;
                break;
            case SIXES:
                chosenIntegerString = "Sixes";
                chosenInteger = 6;
                break;
            case SEQUENCE:
                chosenIntegerString = "Sequence";
                break;
            default:
                System.out.println("Error, Switch Case default, println input " + input);
        }
        System.out.println(chosenIntegerString + " selected");
    }
}
