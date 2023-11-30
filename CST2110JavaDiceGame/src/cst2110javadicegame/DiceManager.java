package cst2110javadicegame;

import java.util.ArrayList;
import java.util.Random;

public class DiceManager {

    public int chosenInteger = 1;
    public String chosenIntegerString = " ";
    public int diceNumber = 5;

    final String ONES = "1";
    final String TWOS = "2";
    final String THREES = "3";
    final String FOURS = "4";
    final String FIVES = "5";
    final String SIXES = "6";
    final String SEQUENCE = "7";

    private ArrayList<Integer> roundMatchedDiceList = new ArrayList<>();
    public ArrayList<Integer> turnMatchedDiceList = new ArrayList<>();
    private ArrayList<Integer> diceList = new ArrayList<>();
    
    public void clearDiceList(ArrayList dicelist) {
        dicelist.clear();
    }

    public void diceRoller() {
        Random randomGenerator = new Random();
        for (int i = 0; i < diceNumber; i++) {
            int randomDiceNumber = randomGenerator.nextInt(6) + 1;
            diceList.add(randomDiceNumber);
        }
    }

    public void diceTurnListInserter() {
        for (int i = 0; i < diceNumber; i++) {
            if (diceList.get(i) == chosenInteger) {
                turnMatchedDiceList.add(chosenInteger);
            }
        }
    }

    public void diceRoundListInserter() {
        for (int i = 0; i < turnMatchedDiceList.size(); i++) {
            roundMatchedDiceList.add(chosenInteger);
        }
    }

    // not needed?
    public void diceListRemover() {
        for (int i = 0; i < diceList.size(); i++) {
            if (diceList.get(i) == chosenInteger) {
                diceList.remove(i);
                i--;
            }
        }
    }

    public StringBuilder printDiceList(ArrayList dicelist) {
        StringBuilder list = new StringBuilder();
        list.setLength(0);
        for (int i = 0; i < dicelist.size(); i++) {
            list.append("[ ").append(dicelist.get(i)).append(" ] ");
        }
        return list;
    }
    
    public ArrayList<Integer> getDiceList() {
        return diceList;
    }

    public ArrayList<Integer> getRoundDiceList() {
        return roundMatchedDiceList;
    }

    public ArrayList<Integer> getTurnDiceList() {
        return turnMatchedDiceList;
    }

    public int turnDiceListSize() {
        return turnMatchedDiceList.size();
    }
    public int roundDiceListSize() {
        return roundMatchedDiceList.size();
    }

    public void setDiceNumber(ArrayList list) {
        diceNumber = (5 - list.size());
    }
    public int returnChosenInteger(){
        return chosenInteger;
    }

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
                System.out.println("Error, Switch Case default, println inputThree " + input);
        }
        System.out.println(chosenIntegerString + " selected");
    }
}
