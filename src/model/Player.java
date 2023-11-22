package model;

import userInterface.ColorsAscii;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    ArrayList<Dice> moveDie = new ArrayList<>();
    private String playerName;

    private ColorsAscii playerColour;
    private int pipScore;

    public Player(String playerName, ColorsAscii playerColour){
        this.playerName = playerName;
        this.playerColour = playerColour;
        moveDie.add(new Dice());
        moveDie.add(new Dice());
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public ColorsAscii getPlayerColour(){return  this.playerColour;}

    public ArrayList<Dice> rollMoves(){
        this.moveDie.get(0).rollDice();
        this.moveDie.get(1).rollDice();
        return moveDie;
    }

    public ArrayList<Dice> getDie(){
        return moveDie;
    }
    public void setDie(ArrayList<Dice> rolls){
        this.moveDie = rolls;
    }

    public int getPipScore() {
        return pipScore;
    }

    public void setPipScore(int pipScore) {
        this.pipScore = pipScore;
    }
}
