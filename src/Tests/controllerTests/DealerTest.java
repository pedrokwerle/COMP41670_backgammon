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
        table.getLane(3).addChecker(testChecker);
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
    }

    @Test
    void moveFromBar() {
    }

    @Test
    void bearCheckerOff() {
    }
}