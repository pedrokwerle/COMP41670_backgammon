package model;

public class Player {
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
}
