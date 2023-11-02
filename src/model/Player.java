package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    private final ArrayList<Integer> rolls = new ArrayList<Integer>();
    Dice playerDice = new Dice();
    private String playerName;
    public Player(String playerName){
        this.playerName = playerName;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public int roll1(){
        return this.playerDice.rollDice();
    }

    public ArrayList<Integer> rollMoves(){
        this.rolls.clear();
        this.rolls.add(this.playerDice.rollDice());
        this.rolls.add(this.playerDice.rollDice());

        return rolls;
    }
}
