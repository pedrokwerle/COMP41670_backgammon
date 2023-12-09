package model;

public enum EndGameType {
    SINGLE("single",1),
    GAMMON("gammon",2),
    BACKGAMMON("backgammon",3),
    FORFEIT("forfeit",-1);
    private final String description;
    private final int stake;
    public String getDescription(){
        return this.description;
    }
    public int getStake(){return this.stake;}
    EndGameType(String description, int stake){
        this.description = description;
        this.stake = stake;
    }
}
