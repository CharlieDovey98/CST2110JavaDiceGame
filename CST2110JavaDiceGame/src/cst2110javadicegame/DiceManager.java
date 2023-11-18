package cst2110javadicegame;

import java.util.ArrayList;
import java.util.Random;

public class DiceManager {

    public int chosenInteger = 0;
    public String chosenIntegerString = " ";

    private ArrayList<Integer> roundMatchedDiceList;
    private ArrayList<Integer> turnMatchedDiceList;

    private ArrayList<Integer> diceList;

    public DiceManager() {
        diceList = new ArrayList<>();
        roundMatchedDiceList = new ArrayList<>();
        turnMatchedDiceList = new ArrayList<>();

    }

    public void diceRoller() {
        diceList.clear();
        Random randomGenerator = new Random();
        for (int i = 0; i < 5; i++) {
            int randomDiceNumber = randomGenerator.nextInt(6) + 1;
            diceList.add(randomDiceNumber);
        }
    }

    public void diceListInserter() {
        for (int i = 0; i <= diceList.size(); i++) {
            if (diceList.get(i) == chosenInteger){
                turnMatchedDiceList.add(chosenInteger);
                diceList.remove(i);
            }
            
        }
    }

    public ArrayList<Integer> getDiceList() {
        return diceList;
    }

    public StringBuilder printDiceList(ArrayList dicelist) {
        StringBuilder list = new StringBuilder();
        list.setLength(0);
        for (int i = 0; i < dicelist.size(); i++) {
            list.append("[ ").append(dicelist.get(i)).append(" ] ");
        }
        return list;
    }

    public ArrayList<Integer> getRoundMatchedDiceList() {
        return roundMatchedDiceList;
    }

    public ArrayList<Integer> getTurnMatchedDiceList() {
        return turnMatchedDiceList;
    }

    public int diceListMatched() {
        return turnMatchedDiceList.size();
    }
}
