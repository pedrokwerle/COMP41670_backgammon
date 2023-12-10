package model;

import userInterface.AsciiArt;
import userInterface.ColorsAscii;
import userInterface.Displayable;

//
public class DoubleCube implements Displayable {
    int value;

    public DoubleCube() {
        this.value = 1;
        this.asciiArt = new AsciiArt("/resources/double_cube.txt");
        this.color = ColorsAscii.BLUE;
    }

    public void doTheDouble() {
        this.value = this.value * 2;
    }

    public int getValue() {
        return this.value;
    }

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
                Character charc = this.asciiArt.getUniqueArt().get(row).get(col).getCharacter();
                if(charc == 'D'){
                    this.asciiArt.getUniqueArt().get(row).get(col).setCharacter((char) (value +'0'));
                }
            }
        }
    }

    @Override
    public AsciiArt getArt() {
        return this.asciiArt;
    }

}

