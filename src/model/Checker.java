package model;

import userInterface.AsciiArt;
import userInterface.ColorsAscii;
import userInterface.Displayable;
import userInterface.PixelAscii;

import java.util.ArrayList;

public class Checker implements Displayable {



    public Checker(ColorsAscii color){
        this.asciiArt = new AsciiArt("/resources/checker.txt");
        this.color = color;
    }

    // AsciiArt section
    AsciiArt asciiArt;
    ColorsAscii color;

    public ColorsAscii getColor() {
        return color;
    }

    @Override
    public void renderArt() {
        try {
            this.asciiArt.clearComponents();
            this.asciiArt.loadArt();
        } catch (Exception e) {
            System.out.println(e);
        }

        int height = this.asciiArt.getUniqueArt().size();
        int lenght;
        for (int row = 0; row < height; row++) {
            lenght = this.asciiArt.getUniqueArt().get(row).size();
            for (int col = 0; col < lenght; col++) {
                 this.asciiArt.getUniqueArt().get(row).get(col).setColor(this.color);
            }
        }
    }

    @Override
    public AsciiArt getArt() {
        return this.asciiArt;
    }
}

