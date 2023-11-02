import model.Checker;
import model.LaneDownward;
import model.LaneUpward;
import userInterface.AsciiString;
import userInterface.Display;
import userInterface.DisplayManager;

public class Main {
    public static void main(String[] args) {
        DisplayManager displayManager = new DisplayManager(50,50);
        AsciiString string = new AsciiString("hello world");
        displayManager.addToCache(string, 24, 24);
        Checker checker1 = new Checker();
        Checker checker2 = new Checker();
        //displayManager.addToCache(checker1, 25, 25);
        LaneDownward lane = new LaneDownward();
        lane.addChecker(checker1);
        lane.addChecker(checker2);
        displayManager.addToCache(lane, 25, 25);



        displayManager.printDisplay();

    }
}
