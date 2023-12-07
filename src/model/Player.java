package model;

import userInterface.ColorsAscii;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    ArrayList<Dice> moveDie = new ArrayList<>();
    private String playerName;

    private ColorsAscii playerColour;
    private boolean hasRolled;
    private int pipScore;
    private boolean canBearOff;


    public Player(String playerName, ColorsAscii playerColour){
        this.playerName = playerName;
        this.playerColour = playerColour;
    }

    public boolean getCanBearOff() {
        return canBearOff;
    }

    public void setCanBearOff(boolean canBearOff) {
        this.canBearOff = canBearOff;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public ColorsAscii getPlayerColour(){return  this.playerColour;}

    public ArrayList<Dice> rollMoves(){
        moveDie.clear();
        moveDie.add(new Dice());
        moveDie.add(new Dice());
        this.moveDie.get(0).rollDice();
        this.moveDie.get(1).rollDice();

        if (this.moveDie.get(0).value == this.moveDie.get(1).value){
            this.moveDie.add(new Dice());
            this.moveDie.get(2).setValue(this.moveDie.get(0).value);
            this.moveDie.add(new Dice());
            this.moveDie.get(3).setValue(this.moveDie.get(0).value);
        }
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

    public boolean getHasRolled(){
        return this.hasRolled;
    }

    public void setHasRolled(boolean hasRolled){
        this.hasRolled = hasRolled;
    }

    public void setMoveDie(ArrayList<Dice> moveDie) {
        this.moveDie = moveDie;
    }
}
