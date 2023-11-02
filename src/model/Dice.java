package model;
import userInterface.AsciiArt;
import userInterface.ColorsAscii;
import userInterface.Displayable;

import java.util.random.RandomGenerator;

public class Dice implements Displayable {
    private final int NUMBER_OF_SIDES = 6;
    int value;

    public Dice(){
        this.asciiArt = new AsciiArt("src/model/dice.txt");
    }

    public int rollDice(){
        value =  RandomGenerator.getDefault().nextInt(NUMBER_OF_SIDES)+1;
        return value;
    }

    public int getValue(){
        return this.value;
    }



    //AsciiArt section

    AsciiArt asciiArt;
    @Override
    public void renderArt() {
        try {
            this.asciiArt.loadArt();
        }
        catch (Exception e){
            System.out.println("File read error");
        }
        int height = this.asciiArt.getUniqueArt().size();
        int lenght;
        char charc;
        for (int row = 0; row < height; row++) {
            lenght = this.asciiArt.getUniqueArt().get(row).size();
            for (int col = 0; col < lenght; col++) {
                charc = this.asciiArt.getUniqueArt().get(row).get(col).getCharacter();
                switch (charc) {
                    case 'V': // Rank modifier
                        charc = (char) this.value;
                        this.asciiArt.getUniqueArt().get(row).get(col).setCharacter(charc);
                        this.asciiArt.getUniqueArt().get(row).get(col).setColor(ColorsAscii.WHITE);
                        break;
                    default:
                        this.asciiArt.getUniqueArt().get(row).get(col).setColor(ColorsAscii.WHITE);
                        break;
                }
            }

        }
    }


    @Override
    public AsciiArt getArt() {
        return null;
    }
}



