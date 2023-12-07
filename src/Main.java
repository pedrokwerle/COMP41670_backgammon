
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
        Thread gameTesterThread = new Thread(gameTester);

        gameMasterThread.start(); // Start the GameMaster thread

        synchronized (lock) {
            gameTesterThread.start(); // Start the GameTester thread
            lock.notify(); // Notify GameTester to start
        }

        try {
            gameMasterThread.join(); // Wait for GameMaster thread to finish
            gameTesterThread.join(); // Wait for GameTester thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
