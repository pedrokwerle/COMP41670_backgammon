package Tests.userInterfaceTests;

import model.Checker;
import org.junit.jupiter.api.Test;
import userInterface.ColorsAscii;
import userInterface.DisplayManager;
import userInterface.Displayable;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DisplayManagerTest {

    @Test
    void addToCache() {
        Checker testChecker = new Checker(ColorsAscii.WHITE);
        DisplayManager dispMan = new DisplayManager(10, 10);
        dispMan.addToCache(testChecker, 0 ,0);
        ArrayList<Displayable> dispTest = dispMan.getRenderCache();
        assertEquals(testChecker, dispTest.get(0));
        dispMan.clearCache();
        assertEquals(dispTest.size(), 0);
    }
    @Test
    void renderObject() {
    }

    @Test
    void renderToDisplay() {
    }

    @Test
    void printString() {
    }

    @Test
    void printDisplay() {
    }
}