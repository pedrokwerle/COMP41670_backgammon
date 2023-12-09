package Tests.modelTests;

import model.Checker;
import org.junit.jupiter.api.Test;
import userInterface.ColorsAscii;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

    @Test
    void getColor() {
        ColorsAscii redCheckerColor = ColorsAscii.RED;
        Checker redChcecker = new Checker(redCheckerColor);
        assertSame(redChcecker.getColor(), redCheckerColor);

        ColorsAscii whiteCheckerColor = ColorsAscii.RED;
        Checker whiteChcecker = new Checker(whiteCheckerColor);
        assertSame(whiteChcecker.getColor(), whiteCheckerColor);

    }

    @Test
    void renderArt() {
    }

    @Test
    void getArt() {
    }
}