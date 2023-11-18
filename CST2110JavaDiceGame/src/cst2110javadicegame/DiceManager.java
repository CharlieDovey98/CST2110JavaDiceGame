package cst2110javadicegame;

import java.util.ArrayList;
import java.util.Random;

public class DiceManager {

    private ArrayList<Integer> diceList;
    
    public DiceManager() {
        diceList = new ArrayList<>();
    }

    public void diceRoller() {
        Random randomGenerator = new Random();
        for (int i = 0; i < 5; i++) {
            int randomDiceNumber = randomGenerator.nextInt(6) + 1; 
            diceList.add(randomDiceNumber);
        }
    }
    
    
    
}
