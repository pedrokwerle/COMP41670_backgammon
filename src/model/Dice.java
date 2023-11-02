package model;
import java.util.random.RandomGenerator;

public class Dice {
    private final int NUMBER_OF_SIDES = 6;

    public int rollDice(){
        return RandomGenerator.getDefault().nextInt(NUMBER_OF_SIDES)+1;
    }
}


