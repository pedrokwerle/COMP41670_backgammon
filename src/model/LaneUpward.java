package model;

import userInterface.AsciiArt;

public class LaneUpward extends Lane{

    // AsciiArt section
    public void renderArt() {
        this.asciiArt.clearComponents();
        if(this.checkers.isEmpty()) {
            try {

                this.asciiArt.loadArt();
            } catch (Exception e) {
                System.out.println("File read error");
            }
        }
        else {
            int xPos = 0;
            int yPos = -1;
            for (Checker checker : checkers) {
                this.asciiArt.addComponent(checker, xPos, yPos);
                xPos += 0;
                yPos -= 2;
                checker.renderArt();
            }
        }
    }


}
