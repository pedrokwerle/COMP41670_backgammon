package model;

import userInterface.AsciiArt;
import userInterface.ColorsAscii;
import userInterface.Displayable;
import userInterface.PixelAscii;

import java.util.ArrayList;

public class Checker implements Displayable {



    public Checker(){
        this.asciiArt = new AsciiArt("src/model/checker.txt");

    }

    // AsciiArt section
    AsciiArt asciiArt;
    @Override
    public void renderArt() {
        try {
            this.asciiArt.clearComponents();
            this.asciiArt.loadArt();
        } catch (Exception e) {
            System.out.println("File read error");
        }
    }

    @Override
    public AsciiArt getArt() {
        return this.asciiArt;
    }
}

