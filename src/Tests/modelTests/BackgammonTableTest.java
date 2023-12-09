package Tests.modelTests;
import model.*;

import model.BackgammonTable;
import org.junit.jupiter.api.Test;
import userInterface.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class BackgammonTableTest {
    private BackgammonTable theBackgammonTable = new BackgammonTable();
    @Test
    void initializeBoard() {
        theBackgammonTable.initializeBoard();
    }

    @Test
    void testLanesInitialization(){
        theBackgammonTable.initializeBoard();
        assertEquals(theBackgammonTable.getLanes().size(), BackgammonTable.LANES_PER_ROW*2);
    }
    @Test
    public void testCheckersInitialization() {
        theBackgammonTable.initializeBoard();
        ArrayList<Checker> lane11Checkers = theBackgammonTable.getLanes().get(11).getCheckers();
        assertEquals(2, lane11Checkers.size());
        assertTrue(lane11Checkers.stream().allMatch(checker -> checker.getColor() == ColorsAscii.WHITE));

        ArrayList<Checker> lane23Checkers = theBackgammonTable.getLanes().get(23).getCheckers();
        assertEquals(2, lane23Checkers.size());
        assertTrue(lane23Checkers.stream().allMatch(checker -> checker.getColor() == ColorsAscii.RED));

        ArrayList<Checker> lane0Checkers = theBackgammonTable.getLanes().get(0).getCheckers();
        assertEquals(5, lane0Checkers.size());
        assertTrue(lane0Checkers.stream().allMatch(checker -> checker.getColor() == ColorsAscii.WHITE));

        ArrayList<Checker> lane6Checkers = theBackgammonTable.getLanes().get(6).getCheckers();
        assertEquals(5, lane6Checkers.size());
        assertTrue(lane6Checkers.stream().allMatch(checker -> checker.getColor() == ColorsAscii.RED));

        ArrayList<Checker> lane12Checkers = theBackgammonTable.getLanes().get(12).getCheckers();
        assertEquals(5, lane12Checkers.size());
        assertTrue(lane12Checkers.stream().allMatch(checker -> checker.getColor() == ColorsAscii.RED));

        ArrayList<Checker> lane18Checkers = theBackgammonTable.getLanes().get(18).getCheckers();
        assertEquals(5, lane18Checkers.size());
        assertTrue(lane18Checkers.stream().allMatch(checker -> checker.getColor() == ColorsAscii.WHITE));

        ArrayList<Checker> lane4Checkers = theBackgammonTable.getLanes().get(4).getCheckers();
        assertEquals(3, lane4Checkers.size());
        assertTrue(lane4Checkers.stream().allMatch(checker -> checker.getColor() == ColorsAscii.RED));

        ArrayList<Checker> lane16Checkers = theBackgammonTable.getLanes().get(16).getCheckers();
        assertEquals(3, lane16Checkers.size());
        assertTrue(lane16Checkers.stream().allMatch(checker -> checker.getColor() == ColorsAscii.WHITE));
    }

    @Test
    void getLane() {
        theBackgammonTable.initializeBoard();
        Lane lane = theBackgammonTable.getLane(4);
        assertNotNull(lane);
    }

    @Test
    void getLanes() {
        theBackgammonTable.initializeBoard();
        ArrayList<Lane> lanes = theBackgammonTable.getLanes();
        assertNotNull(lanes);
    }

    @Test
    void getBarArea() {
        theBackgammonTable.initializeBoard();
        Lane barArea = theBackgammonTable.getBarArea();
        assertNotNull(barArea);
    }

    @Test
    void getRedBearArea() {
        theBackgammonTable.initializeBoard();
        Lane redBearArea = theBackgammonTable.getRedBearArea();
        assertNotNull(redBearArea);
    }

    @Test
    void getWhiteBearArea() {
        theBackgammonTable.initializeBoard();
        Lane whiteBearArea = theBackgammonTable.getWhiteBearArea();
        assertNotNull(whiteBearArea);
    }

    @Test
    void renderArt() {
    }

    @Test
    void getArt() {
    }
}