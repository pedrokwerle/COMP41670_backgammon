import model.Checker;
import userInterface.AsciiString;
import userInterface.Display;
import userInterface.DisplayManager;

public class Main {
    public static void main(String[] args) {
        DisplayManager displayManager = new DisplayManager(50,50);
        AsciiString string = new AsciiString("hello world");
        displayManager.addToCache(string, 25, 25);
        Checker checker1 = new Checker();
        displayManager.addToCache(checker1, 25, 25);
        displayManager.printDisplay();

    }
}
