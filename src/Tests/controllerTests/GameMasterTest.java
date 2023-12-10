package Tests.controllerTests;

//import controller.CommandType;
import controller.GameMaster;
import controller.MatchMaker;
import model.BackgammonTable;
import model.Checker;
import org.junit.jupiter.api.Test;
import userInterface.ColorsAscii;
import userInterface.Display;
import userInterface.DisplayManager;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.*;

class GameMasterTest {

    @Test
    void listMoves() {
    }

    private static void initializeEmptyBoard(BackgammonTable table) {

        table.initializeBoard();
        for (int i = 0; i < table.getLanes().size(); i++){
            for (int j = 0; j < table.getLane(i).getSize(); j++){
                table.getLane(i).removeChecker();
            }
        }
    }
    @Test
    void startGame() {
    }

    @Test
    void gameLoop() {
    }

    @Test
    void setPlayerFrame() {
    }

    @Test
    void interpretCommand() {
    }

    @Test
    void executeCommand() {
    }

    @Test
    void getTestFilePath() {
    }

    @Test
    void moveCommand() {
    }

    @Test
    void run() {
    }

    @Test
    void testMode() {
    }

    @Test
    void setTestMode() {
    }

    @Test
    void processInput() {
    }

    @Test
    void requestNextInput() {
    }
}