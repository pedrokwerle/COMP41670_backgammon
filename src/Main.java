import model.Checker;
import model.LaneDownward;
import model.LaneUpward;
import userInterface.AsciiString;
import userInterface.Display;
import userInterface.DisplayManager;
import model.*;
import java.util.*;


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
        fakeDemo();
    }

    public static void fakeDemo(){
        Scanner Main = new Scanner(System.in);
        System.out.println("Please input Player 1's name:");
        Player player1 = new Player(Main.nextLine());

        System.out.println("Please input Player 2's name:");
        Player player2 = new Player(Main.nextLine());

        System.out.println(player1.getPlayerName()+player2.getPlayerName());
    }
}
