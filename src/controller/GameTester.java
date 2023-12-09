package controller;

import userInterface.ColorsAscii;
import userInterface.Fileio;
import userInterface.Keyboard;

import java.io.*;

public class GameTester implements Runnable{

    private final GameMaster gameMaster;
    private final Object inputLock;

    public GameTester(GameMaster gameMaster, Object inputLock){
        this.gameMaster = gameMaster;
        this.inputLock = inputLock;
    }

    @Override
    public void run() {
        // Wait at the beginning to be woken up by the game
        // This is very necessary otherwise the threads will be out of sync
        synchronized (inputLock) {
            try {
                inputLock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Keyboard key = new Keyboard();
        while (true) {
            if (gameMaster.testMode()) {
                executeCommandsFromFile(gameMaster.getTestFilePath()); // Replace "commands.txt" with your file name
            } else {
                String userInput = key.getString();
                gameMaster.processInput(userInput); // Send user input to GameMaster
            }
        }
    }

    private void executeCommandsFromFile(String filename) {
        try {
            Fileio reader = new Fileio(filename);

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                synchronized (inputLock) {
                    gameMaster.processInput(line); // Send commands from file to GameMaster
                }
            }
            gameMaster.setTestMode(false);

        } catch (IOException e) {
            // This is what happens if the reading process fails, could be file not found (most common)
            // or something else
            gameMaster.setTestMode(false);
            System.out.println(ColorsAscii.WHITE.toCode() +"Something went wrong, please try again");
        }
    }

}
