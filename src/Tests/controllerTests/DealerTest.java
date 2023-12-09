package Tests.controllerTests;

import controller.Dealer;
import model.BackgammonTable;
import model.Checker;
import org.junit.jupiter.api.Test;
import userInterface.ColorsAscii;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {
    @Test
    void moveAChecker() {
        int testMoveFrom = 3;
        int testMoveTo = 5;
        BackgammonTable table = new BackgammonTable();
        initializeEmptyBoard(table);
        Checker testChecker = new Checker(ColorsAscii.RED);
        table.getLane(testMoveFrom).addChecker(testChecker);
        Dealer testDealer = new Dealer(table);
        testDealer.moveAChecker(testMoveFrom,testMoveTo);
        assertEquals(1, table.getLane(testMoveTo).getSize());
    }

    private static void initializeEmptyBoard(BackgammonTable table) {

        table.initializeBoard();
        for (int i = 0; i < table.getLanes().size(); i++){
            for (int j = 0; j < table.getLane(i).getSize(); j++){
                table.getLane(i).removeChecker();
            }
        }
    }

    @Test
    void moveToBar() {
        int testMoveFrom = 3;
        BackgammonTable table = new BackgammonTable();
        initializeEmptyBoard(table);
        Checker testRedChecker = new Checker(ColorsAscii.RED);
        table.getLane(testMoveFrom).addChecker(testRedChecker);
        Dealer testDealer = new Dealer(table);
        testDealer.moveToBar(testMoveFrom);
        assertEquals(table.getLane(testMoveFrom).getSize(),0);
        assertEquals(table.getBarArea().getSize(),1);
    }

    @Test
    void moveFromBar() {
        int testMoveTo = 3;
        BackgammonTable table = new BackgammonTable();
        initializeEmptyBoard(table);
        Checker testRedChecker = new Checker(ColorsAscii.RED);
        table.getBarArea().addChecker(testRedChecker);
        Checker testWhiteChecker = new Checker(ColorsAscii.WHITE);
        table.getBarArea().addChecker(testWhiteChecker);
        assertEquals(table.getBarArea().getSize(),2);
        Dealer testDealer = new Dealer(table);
        testDealer.moveFromBar(testMoveTo, testRedChecker.getColor());
        assertEquals(table.getLane(testMoveTo).getSize(),1);
        assertEquals(table.getBarArea().getSize(),1);
    }

    @Test
    void bearCheckerOff() {
        int testMoveFrom = 9;
        BackgammonTable table = new BackgammonTable();
        initializeEmptyBoard(table);
        Checker testRedChecker = new Checker(ColorsAscii.RED);
        table.getLane(testMoveFrom).addChecker(testRedChecker);
        Dealer testDealer = new Dealer(table);
        testDealer.bearCheckerOff(9);
        assertEquals(table.getLane(testMoveFrom).getSize(),0);
        assertEquals(table.getRedBearArea().getSize(),1);
    }
}