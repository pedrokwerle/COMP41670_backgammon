package controller;

import userInterface.Fileio;
import userInterface.Keyboard;

import java.io.*;

public class GameTester implements Runnable{

    private final GameMaster gameMaster;
    private final Object lock;

    public GameTester(GameMaster gameMaster, Object lock){
        this.gameMaster = gameMaster;
        this.lock = lock;
    }

    @Override
    public void run() {
        System.out.println("Started1");
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Started2");


        Keyboard key = new Keyboard();
        while (true) {
            if (gameMaster.testMode()) {
                executeCommandsFromFile("/resources/test_commands.txt"); // Replace "commands.txt" with your file name
            } else {
                String userInput = key.getString();
                gameMaster.processInput(userInput); // Send user input to GameMaster
            }
        }
    }

    private void executeCommandsFromFile(String filename) {
        try {
            Fileio reader = new Fileio("/resources/test_commands.txt");

                String line;

                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    synchronized (lock) {
                        gameMaster.processInput(line); // Send commands from file to GameMaster
                    }
                }
                gameMaster.setTestMode(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
