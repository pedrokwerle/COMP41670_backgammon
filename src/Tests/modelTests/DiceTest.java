package Tests.modelTests;

import model.Dice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceTest {
    Dice theDice =  new Dice();

    @Test
    void getValue() {
        int expectedValue = 5;
        theDice.setValue(expectedValue);
        int actualValue = theDice.getValue();
        assertEquals(expectedValue, actualValue);
    }
    @Test
    @DisplayName("Checking that the rolled Dice does not exceed its number of faces")
    void rollDice() {
        theDice.rollDice();
        assertTrue(theDice.getValue() < theDice.NUMBER_OF_SIDES);
        assertTrue(theDice.getValue() > 0);
    }

    @Test
    void setValue() {
        int expectedValue = 2;
        theDice.setValue(expectedValue);
        int actualValue = theDice.getValue();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void renderArt() {
    }

    @Test
    void getArt() {
    }
}