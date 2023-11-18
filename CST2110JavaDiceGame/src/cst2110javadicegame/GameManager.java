package cst2110javadicegame;

public class GameManager {

    

    public boolean forfeit = false;
    public int roundNumber = 0;

    public boolean isGameOver() {
        return roundNumber == 7;
    }

}
