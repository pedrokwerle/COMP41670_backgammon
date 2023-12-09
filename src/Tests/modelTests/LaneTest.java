package Tests.modelTests;

import model.Checker;
import model.LaneDownward;
import model.LaneUpward;
import org.junit.jupiter.api.Test;
import userInterface.ColorsAscii;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LaneTest {

    @Test
    void checkerManipulationTest() {
        LaneUpward laneUpward = new LaneUpward();
        assertEquals(0, laneUpward.getSize());
        Checker testChecker = new Checker(ColorsAscii.RED);
        laneUpward.addChecker(testChecker);
        assertEquals(1, laneUpward.getSize());
        laneUpward.addChecker(testChecker);
        assertEquals(2, laneUpward.getSize());
        laneUpward.removeChecker();
        assertEquals(1, laneUpward.getSize());
        laneUpward.removeChecker();
        assertEquals(0, laneUpward.getSize());
    }


    @Test
    void getColour() {
        LaneDownward laneDownward = new LaneDownward();
        Checker redChecker = new Checker(ColorsAscii.RED);
        Checker whiteChecker = new Checker(ColorsAscii.WHITE);
        laneDownward.addChecker(redChecker);
        assertSame(laneDownward.getColour(),redChecker.getColor());
        laneDownward.removeChecker();
        assertSame(laneDownward.getColour(),ColorsAscii.NOCOLOR);
        laneDownward.addChecker(whiteChecker);
        assertSame(laneDownward.getColour(),whiteChecker.getColor());
    }

    @Test
    void getCheckers() {
        Checker checker1 = new Checker(ColorsAscii.RED);
        Checker checker2 = new Checker(ColorsAscii.WHITE);
        Checker checker3 = new Checker(ColorsAscii.BLACK);
        Checker checker4 = new Checker(ColorsAscii.BLUE);

        LaneDownward lane = new LaneDownward();
        lane.addChecker(checker1);
        lane.addChecker(checker2);
        lane.addChecker(checker3);
        lane.addChecker(checker4);

        ArrayList<Checker> checkers = new ArrayList<>();
        checkers = lane.getCheckers();
        assertSame(checkers.get(0),checker1);
        assertSame(checkers.get(1),checker2);
        assertSame(checkers.get(2),checker3);
        assertSame(checkers.get(3),checker4);
    }

    @Test
    void getArt() {
    }
}