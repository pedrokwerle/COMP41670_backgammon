package model;

public enum CommandType {
    INVALID(""),
    ROLL("roll: rolls two dice to be used to move checkers and shows possible moves"),
    MOVE("[select a letter] move: select one of the available moves to move a checker from one lane to another or bear off"),
    QUIT("quit: exit the game"),
    PIP("pip: display the pip score for both players"),
    HINT("hint/help: show this message"),
    DICE("dice <int> <int> : roll two dice with the values equals to the given numbers"),
    TEST("test <filepath> : run all the moves in a test text file"),
    DOUBLE("double: ask other player to double the stake of the game"),
    SCORE("score: display the match score");

    private final String description;
    public String getDescription(){
        return this.description;
    }
    CommandType(String description){
        this.description = description;
    }


}
