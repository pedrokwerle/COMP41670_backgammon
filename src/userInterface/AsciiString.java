package userInterface;

import java.util.ArrayList;

public class AsciiString implements Displayable{
    String string;

    AsciiArt asciiArt;

    public AsciiString(String string){
        this.string = string;
        this.asciiArt = new AsciiArt("");
        this.asciiArt.uniqueArt = new ArrayList<>();
        this.asciiArt.uniqueArt.add(new ArrayList<>());

    }

    public void setString(String string){
        this.string = string;
    }
    @Override
    public void renderArt() {
        this.asciiArt.uniqueArt = new ArrayList<>();
        this.asciiArt.uniqueArt.add(new ArrayList<>());
        for (int charNum = 0; charNum < this.string.length(); charNum++){
            this.asciiArt.uniqueArt.get(0).add(new PixelAscii(string.charAt(charNum),ColorsAscii.WHITE));
        }
    }

    @Override
    public AsciiArt getArt() {
        return this.asciiArt;
    }
}
