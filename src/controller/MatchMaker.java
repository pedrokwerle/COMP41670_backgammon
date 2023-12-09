package controller;

import model.BackgammonTable;
import model.Player;
import userInterface.*;

import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class MatchMaker {
    DisplayManager displayManager;
    Player player1;
    Player player2;
    Keyboard key;
    int matchLength;
    int currentGameNumber;
    BlockingQueue<CommandType> matchChannel;
    Semaphore matchSemaphore;

    public MatchMaker(){
        matchChannel = new LinkedBlockingQueue<>();
        this.displayManager = new DisplayManager(100,500);
        this.key = new Keyboard();
        this.currentGameNumber = 1;
        this.matchSemaphore = new Semaphore(0);
    }

    public void startMatch(){
        matchConfiguration();
        startGame();

    }
    public void endMatch(){

    }
    public synchronized void startGame(){
        Object inputLock = new Object(); // Create a lock object for the input and game thread to communicate

        GameMaster gameMaster = new GameMaster(inputLock, displayManager, matchChannel, matchSemaphore);
        Thread gameMasterThread = new Thread(gameMaster);

        GameTester gameTester = new GameTester(gameMaster, inputLock);
        Thread inputThread = new Thread(gameTester);

        // Give attributes to gameMaster
        gameMaster.player1 = player1;
        gameMaster.player2 = player2;

        gameMasterThread.start(); // Start the GameMaster thread
        inputThread.start(); // Start the GameTester thread
        // Wait for the game to end while responding to requests from the game thread
        while(!gameMaster.gameOver) {
            waitForGame();
        }
        
        // Do all end of game processing
        processGameOver();
        
        try {
            gameMasterThread.join(); // Wait for GameMaster thread to finish
            inputThread.join(); // Wait for GameTester thread to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void matchConfiguration() {
        System.out.println(ColorsAscii.WHITE.toCode() + "Please input Player 1's name: ");
        this.player1 = new Player(key.getString(), ColorsAscii.WHITE);

        System.out.println(ColorsAscii.WHITE.toCode() + "Please input Player 2's name: ");
        this.player2 = new Player(key.getString(), ColorsAscii.RED);

        System.out.println(ColorsAscii.WHITE.toCode() + "Select match length: ");
        String input =  key.getString().toLowerCase().trim();
        while(!input.matches("\\d+")){
            System.out.println(ColorsAscii.WHITE.toCode() + "Please enter a valid number: ");
            input =  key.getString().toLowerCase().trim();
        }
        this.matchLength = Integer.parseInt(input);

    }

    private void processGameOver() {
        if(currentGameNumber == matchLength){
            endMatch();
        }
        else {
            currentGameNumber += 1;
            System.out.println(ColorsAscii.WHITE.toCode() + "Starting game number " + currentGameNumber);
            try {
                this.wait(2000); // Waits for 2 seconds to start new game
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            startGame();
        }
    }

    public void waitForGame(){
        try {
            CommandType commandType = matchChannel.take(); // Blocks until data is available
            this.executeCommand(commandType);
            matchSemaphore.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    public void executeCommand(CommandType commandType){
        switch (commandType){
            case SCORE:
                String message = "The current score tally is:\n" +
                        player1.getPlayerName() + " has " + player1.getMatchPoints() + " points\n" +
                        player2.getPlayerName() + " has " + player2.getMatchPoints() + " points\n" +
                        "Current game is game number " + currentGameNumber+
                        "\nMatch length is " + this.matchLength + " games";
                displayManager.addToCache(new AsciiString(message),0,BackgammonTable.BOTTOM_OFF_FRAME);
                break;
            default:
                // When the command type is not something the matchmaker can handle
                // it either goes back to sleep (if the game is not over)
                // or wakes up and notices that the game ended
        }
    }
}
