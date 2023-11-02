package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    ArrayList<Dice> moveDice = new ArrayList<>();
    private String playerName;
    public Player(String playerName){
        this.playerName = playerName;
        moveDice.add(new Dice());
        moveDice.add(new Dice());
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public ArrayList<Dice> rollMoves(){
        this.moveDice.get(0).rollDice();
        this.moveDice.get(1).rollDice();
        return moveDice;
    }
}
