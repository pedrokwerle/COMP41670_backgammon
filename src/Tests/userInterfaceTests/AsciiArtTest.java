package Tests.userInterfaceTests;

import model.Checker;
import org.junit.jupiter.api.Test;
import userInterface.AsciiArt;
import userInterface.ColorsAscii;
import userInterface.Displayable;
import userInterface.PixelAscii;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AsciiArtTest {

    @Test
    void componentTest() {
        Checker testChecker = new Checker(ColorsAscii.WHITE);
        testChecker.getArt().addComponent(testChecker, 0, 0);
        ArrayList<Displayable> componentList = new ArrayList<>();
        componentList= testChecker.getArt().getComponents();
        assertTrue(componentList.get(0).getArt().isComposite());
        componentList.get(0).getArt().clearComponents();
        assertEquals(componentList.size(),0);
    }

    @Test
    void setFileLocation() {
    }

    @Test
    void loadArt() {
        ArrayList<ArrayList<PixelAscii>> testArt = new ArrayList<>();
        ArrayList<PixelAscii> rowOne = new ArrayList<>();
        PixelAscii testChar = new PixelAscii('╔', ColorsAscii.WHITE);
        rowOne.add(testChar);
        PixelAscii testChar1 = new PixelAscii('═', ColorsAscii.WHITE);
        rowOne.add(testChar1);
        rowOne.add(testChar1);
        rowOne.add(testChar1);
        PixelAscii testChar3 = new PixelAscii('╗', ColorsAscii.WHITE);
        rowOne.add(testChar3);

        ArrayList<PixelAscii> rowTwo = new ArrayList<>();

        PixelAscii testChar4 = new PixelAscii('╚', ColorsAscii.WHITE);
        rowTwo.add(testChar4);
        PixelAscii testChar5 = new PixelAscii('═', ColorsAscii.WHITE);
        rowTwo.add(testChar5);
        rowTwo.add(testChar5);
        rowTwo.add(testChar5);
        PixelAscii testChar6 = new PixelAscii('╝', ColorsAscii.WHITE);
        rowTwo.add(testChar6);

        testArt.add(rowOne);
        testArt.add(rowTwo);

        Checker testChecker = new Checker(ColorsAscii.WHITE);
        try {
            testChecker.getArt().loadArt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ArrayList<ArrayList<PixelAscii>> receivedArt = new ArrayList<>();
        receivedArt = testChecker.getArt().getUniqueArt();

        for(int i = 0; i < receivedArt.size(); i++){
            for(int j =0; j < receivedArt.get(i).size(); j++){
                PixelAscii pixel1 = receivedArt.get(i).get(j);
                PixelAscii pixel2 = testArt.get(i).get(j);
                assertEquals(pixel1.getCharacter() ,pixel2.getCharacter());
                assertEquals(pixel1.getColor() ,pixel2.getColor());
            }
        }

    }
}