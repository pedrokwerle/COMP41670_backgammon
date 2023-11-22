package model;

import userInterface.AsciiArt;
import userInterface.AsciiString;

public class LaneUpward extends Lane{

    // AsciiArt section
    public void renderArt() {
        this.asciiArt.clearComponents();
        if(this.checkers.isEmpty()) {
            try {

                this.asciiArt.loadArt();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else {
            int xPos = 0;
            int yPos = -1;
            for (Checker checker : checkers) {
                if(yPos < -10){
                    AsciiString string = new AsciiString(" +"+(this.checkers.size()-5));
                    this.asciiArt.addComponent(string,xPos,yPos+1);
                    string.renderArt();
                }
                else {
                    this.asciiArt.addComponent(checker, xPos, yPos);
                    xPos += 0;
                    yPos -= 2;
                    checker.renderArt();
                }
            }
        }
    }


}
