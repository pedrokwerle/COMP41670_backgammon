package controller;

public enum CommandType {
    INVALID(""),
    ROLL("roll: rolls two dice to be used to move checkers and shows possible moves"),
    MOVE("[select a letter] move: select one of the available moves to move a checker from one lane to another or bear off"),
    QUIT("quit: exit the game"),
    PIP("pip: display the pip score for both players"),
    HINT("hint/help: show this message"),
    TEST("test: run all the moves in a test text file"),
    DOUBLE("double: I don't know yet");

    private final String description;
    public String getDescription(){
        return this.description;
    }
    CommandType(String description){
        this.description = description;
    }


}
