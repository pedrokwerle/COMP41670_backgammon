
import controller.Dealer;
import controller.GameMaster;
import controller.GameTester;
import userInterface.*;
import model.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        demo();
    }

    public static void demo(){
        Object lock = new Object(); // Create a lock object


        GameMaster gameMaster = new GameMaster(lock);
        Thread gameMasterThread = new Thread(gameMaster);

        GameTester gameTester = new GameTester(gameMaster, lock);
        Thread inputThread = new Thread(gameTester);

        gameMasterThread.start(); // Start the GameMaster thread
        inputThread.start(); // Start the GameTester thread

        try {
            gameMasterThread.join(); // Wait for GameMaster thread to finish
            inputThread.join(); // Wait for GameTester thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
