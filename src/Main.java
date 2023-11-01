import userInterface.AsciiString;
import userInterface.Display;
import userInterface.DisplayManager;

public class Main {
    public static void main(String[] args) {
        DisplayManager displayManager = new DisplayManager(50,50);
        AsciiString string = new AsciiString("hello world");
        displayManager.addToCache(string, 0 , 0);
        displayManager.printDisplay();

    }
}
