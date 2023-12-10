package Tests.controllerTests;

import controller.Commander;
import model.CommandType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommanderTest {
    @Test
    void interpretCommandTest(){
        assertSame(CommandType.QUIT, Commander.interpretCommand("Quit"));
        assertSame(CommandType.QUIT, Commander.interpretCommand("quit"));

        assertSame(CommandType.ROLL, Commander.interpretCommand("Roll"));
        assertSame(CommandType.ROLL, Commander.interpretCommand("roll"));

        assertSame(CommandType.MOVE, Commander.interpretCommand("A"));
        assertSame(CommandType.MOVE, Commander.interpretCommand("a"));

        assertSame(CommandType.MOVE, Commander.interpretCommand("c"));
        assertSame(CommandType.MOVE, Commander.interpretCommand("C"));

        assertSame(CommandType.MOVE, Commander.interpretCommand("z"));
        assertSame(CommandType.MOVE, Commander.interpretCommand("Z"));

        assertSame(CommandType.HINT, Commander.interpretCommand("hint"));
        assertSame(CommandType.HINT, Commander.interpretCommand("help"));

        assertSame(CommandType.PIP, Commander.interpretCommand("pip"));

        assertSame(CommandType.TEST, Commander.interpretCommand("test test.txt"));

        assertSame(CommandType.SCORE, Commander.interpretCommand("score"));

        assertSame(CommandType.INVALID, Commander.interpretCommand("sdkfhkah "));
    }
}