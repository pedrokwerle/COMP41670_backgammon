package model;

import userInterface.ColorsAscii;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    ArrayList<Dice> moveDice = new ArrayList<>();
    private String playerName;

    private ColorsAscii playerColour;

    public Player(String playerName, ColorsAscii playerColour){
        this.playerName = playerName;
        this.playerColour = playerColour;
        moveDice.add(new Dice());
        moveDice.add(new Dice());
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public ColorsAscii getPlayerColour(){return  this.playerColour;}

    public ArrayList<Dice> rollMoves(){
        this.moveDice.get(0).rollDice();
        this.moveDice.get(1).rollDice();
        return moveDice;
    }
}
