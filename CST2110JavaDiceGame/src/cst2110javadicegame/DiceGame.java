package cst2110javadicegame;

import java.util.Scanner;

// This class holds the main game code and will call on other classes for their respective responsibilities.
public class DiceGame {

    // The instantiation of objects to help the game run.
    GameManager gameManager = new GameManager();
    ScoreboardManager scoreManager = new ScoreboardManager();

    // A "start" function to hold the game itself.
    public void start() {
        Scanner sc = new Scanner(System.in);

        System.out.println("This is a two player game, played through the console"
                + "\nplayers will take it in turns to roll 5 dice, choosing a die number and scoring as many points as possible by attaining the max number of that die."
                + "\nAfter 7 rounds your score will be calculated with the highest scoring player winning the game!"
                + "\nLet's play...");

        while (gameManager.isGameOver() == false) {
            System.out.println(scoreManager.returnUpdatedScoreboard());
            

                gameManager.roundNumber = 7;

            }

        }

    }
