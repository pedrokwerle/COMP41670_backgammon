package Tests.modelTests;

import model.Dice;
import model.Player;
import org.junit.jupiter.api.Test;
import userInterface.ColorsAscii;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void canBearOffFlagTest() {
        Player player = new Player("TestPlayer", ColorsAscii.RED);
        assertFalse(player.getCanBearOff());
        player.setCanBearOff(true);
        assertTrue(player.getCanBearOff());
    }

    @Test
    void playerNameInteractionTest() {
        Player player = new Player("TestPlayer", ColorsAscii.RED);
        String playerTestName = "ThisIs a tEst 123 &^..#";
        player.setPlayerName(playerTestName);
        assertSame(player.getPlayerName(),playerTestName);
    }

    @Test
    void getPlayerColour() {
        ColorsAscii playerTestColor = ColorsAscii.RED;
        Player player = new Player("TestPlayer", playerTestColor);
        assertSame(player.getPlayerColour(), playerTestColor);
        playerTestColor = ColorsAscii.WHITE;
        Player player2 = new Player("TestPlayer", playerTestColor);
        assertSame(player2.getPlayerColour(), playerTestColor);
    }

    @Test
    void rollMovesTest() {
        Player player = new Player("TestPlayer", ColorsAscii.RED);
        player.rollMoves();
        assertTrue(player.getDie().size() == 2 || player.getDie().size() == 4, "The dice array should have either 2 or 4 elements");

        if (player.getDie().size() == 4) {
            int firstValue = player.getDie().get(0).getValue();
            assertEquals(firstValue, player.getDie().get(1).getValue());
            assertEquals(firstValue, player.getDie().get(2).getValue());
            assertEquals(firstValue, player.getDie().get(3).getValue());
        }
    }

    @Test
    void setDie() {
        Player player = new Player("TestPlayer", ColorsAscii.RED);
        int value1 = 3;
        int value2 = 4;
        Dice dice1 = new Dice();
        dice1.setValue(value1);
        Dice dice2 = new Dice();
        dice1.setValue(value2);
        ArrayList<Dice> testRolls = new ArrayList<>();
        testRolls.add(dice1);
        testRolls.add(dice2);
        player.setDie(testRolls);
        assertSame(player.getDie(),testRolls);
    }
    @Test
    void PipScoreTest() {
        Player player = new Player("TestPlayer", ColorsAscii.RED);
        int testPipScore = 30;
        player.setPipScore(testPipScore);
        assertSame(testPipScore, player.getPipScore());
    }
    @Test
    void HasRolledTest() {
        Player player = new Player("TestPlayer", ColorsAscii.RED);
        assertFalse(player.getHasRolled());
        boolean testHasRolled = true;
        player.setHasRolled(testHasRolled);
        assertSame(player.getHasRolled(), testHasRolled);
    }
}