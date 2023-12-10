package Tests.controllerTests;

import model.CommandType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandTypeTest {

    @Test
    void getDescription() {
        assertEquals(CommandType.ROLL.getDescription(),
                "roll: rolls two dice to be used to move checkers and shows possible moves");
        assertEquals(CommandType.MOVE.getDescription(),
                "[select a letter] move: select one of the available moves to move a checker from one lane to another or bear off");
        assertEquals(CommandType.QUIT.getDescription(),
                "quit: exit the game");
        assertEquals(CommandType.PIP.getDescription(),
                "pip: display the pip score for both players");
        assertEquals(CommandType.HINT.getDescription(),
                "hint/help: show this message");
        assertEquals(CommandType.DICE.getDescription(),
                "dice <int> <int> : roll two dice with the values equals to the given numbers");
        assertEquals(CommandType.TEST.getDescription(),
                "test <filepath> : run all the moves in a test text file");
        assertEquals(CommandType.DOUBLE.getDescription(),
                "double: ask other player to double the stake of the game");
        assertEquals(CommandType.SCORE.getDescription(),
                "score: display the match score");
        assertEquals(CommandType.INVALID.getDescription(),
                "");
    }

    @Test
    void values() {
    }

    @Test
    void valueOf() {
    }
}