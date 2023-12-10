package userInterface;

public class PixelAscii {
    char character;
    ColorsAscii color;

    public PixelAscii(char character, ColorsAscii color) {
        this.character = character;
        this.color = color;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public ColorsAscii getColor() {
        return color;
    }

    public void setColor(ColorsAscii color) {
        this.color = color;
    }


}
