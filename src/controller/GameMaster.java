package controller;
import model.*;
import userInterface.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class GameMaster implements Runnable{
    Player player1;
    Player player2;
    Player playerTurn;
    Player nextPlayerTurn;
    Player winnerPlayer;
    DisplayManager displayManager;
    Dealer dealer;
    BackgammonTable table;
    Keyboard key;
    String currentInput;
    CommandType commandType;
    boolean gameOver;
    int gameValue;

    public GameMaster(Object inputLock, DisplayManager displayManager, BlockingQueue<CommandType> matchChannel, Semaphore matchSemaphore) {
        this.key = new Keyboard();
        this.testMode = false;
        this.inputLock = inputLock;
        this.gameOver = false;
        this.displayManager = displayManager;
        this.matchChannel = matchChannel;
        this.matchSemaphore = matchSemaphore;
        this.gameValue = 1; // Standard game value
    }

    public ArrayList<ArrayList<Integer>> listMoves(){
        ArrayList<Lane> lanes = new ArrayList<>();
        ArrayList<ArrayList<Integer>> moves = new ArrayList<>();

        playerTurn.setCanBearOff(checkCanPlayerBearOff());

        // reordering the lanes for each colour to make finding the possible moves easier.
        if (playerTurn.getPlayerColour() == ColorsAscii.RED){
            for (int i = BackgammonTable.TOTAL_LANES-1; i >= BackgammonTable.LANES_PER_ROW; i--){
                lanes.add(table.getLane(i));
            }
            for (int i = 0; i < BackgammonTable.LANES_PER_ROW; i++){
                lanes.add(table.getLane(i));
            }
        } else if (playerTurn.getPlayerColour() == ColorsAscii.WHITE) {
            for (int i = BackgammonTable.LANES_PER_ROW-1; i >= 0; i--){
                lanes.add(table.getLane(i));
            }
            for (int i = BackgammonTable.LANES_PER_ROW; i < BackgammonTable.TOTAL_LANES; i++){
                lanes.add(table.getLane(i));
            }
        }

        if (this.table.getBarArea().getSize() != 0) {
            for (int i = 0; i < this.table.getBarArea().getSize(); i++){
                ArrayList<Checker> barCheckers = this.table.getBarArea().getCheckers();
                if (barCheckers.get(i).getColor() == playerTurn.getPlayerColour()){
                    for (int j = 0;j < playerTurn.getDie().size();j++){
                        ArrayList<Integer> move = new ArrayList<>();
                        if (lanes.get(-1+playerTurn.getDie().get(j).getValue()).getColour() == playerTurn.getPlayerColour()){
                            //is a possible move, just adds to the lanes
                            move.add(-1);
                            move.add(-1 + playerTurn.getDie().get(j).getValue());
                            moves.add(move);
                        }
                        else if(lanes.get(-1+playerTurn.getDie().get(j).getValue()).getSize() == 1){
                            // will kill the enemy piece and send it to the bar
                            move.add(-1);
                            move.add(-1+playerTurn.getDie().get(j).getValue());
                            moves.add(move);
                        }
                        else if(lanes.get(-1+playerTurn.getDie().get(j).getValue()).getSize() == 0) {
                            //is a possible move, just adds to the lanes
                            move.add(-1);
                            move.add(-1+playerTurn.getDie().get(j).getValue());
                            moves.add(move);
                        }
                    }
                    if (!moves.isEmpty()) {
                        return moves;
                    }
                }
            }
        }

        for (int i = 0; i < lanes.size(); i++){
            // finding a lane with the players color checker
            if (lanes.get(i).getColour() == playerTurn.getPlayerColour()){
                // looping through each of the dice the player rolled
                for (int j = 0;j < playerTurn.getDie().size();j++){
                    ArrayList<Integer> move = new ArrayList<>();
                    // check if moving from this lane to that with the dice is valid
                    if (i+playerTurn.getDie().get(j).getValue() >= 24) {
                        // can bear off ** need to add checker that all pieces are home corner**
                        if (playerTurn.getCanBearOff()){
                            move.add(i);
                            move.add(24);
                            moves.add(move);
                        }
                    }
                    else if (lanes.get(i+playerTurn.getDie().get(j).getValue()).getColour() == playerTurn.getPlayerColour()){
                        //is a possible move, just adds to the lanes
                        move.add(i);
                        move.add(i+playerTurn.getDie().get(j).getValue());
                        moves.add(move);
                    }
                    else if(lanes.get(i+playerTurn.getDie().get(j).getValue()).getSize() == 1){
                        // will kill the enemy piece and send it to the bar
                        move.add(i);
                        move.add(i+playerTurn.getDie().get(j).getValue());
                        moves.add(move);
                    }
                    else if(lanes.get(i+playerTurn.getDie().get(j).getValue()).getSize() == 0) {
                        //is a possible move, just adds to the lanes
                        move.add(i);
                        move.add(i+playerTurn.getDie().get(j).getValue());
                        moves.add(move);
                    }
                }
            }
        }
        return moves;
    }

    public void startGame(){

        boolean hasPlayerOrder = false;
        Dice p1Roll = new Dice();
        Dice p2Roll = new Dice();

        while (!hasPlayerOrder){
            p1Roll.rollDice();
            p2Roll.rollDice();
            if (p1Roll.getValue() > p2Roll.getValue()) {
                this.playerTurn = player1;
                hasPlayerOrder = true;
            }
            else if (p2Roll.getValue() > p1Roll.getValue()){
                this.playerTurn = player2;
                hasPlayerOrder = true;
            }
        }
        ArrayList<Dice> rolls = new ArrayList<>();
        rolls.add(p1Roll);
        rolls.add(p2Roll);
        playerTurn.setDie(rolls);
        playerTurn.setHasRolled(true);
        System.out.println(player1.getPlayerName()+" has rolled a "+p1Roll.getValue()+" and "+player2.getPlayerName()+ " has rolled a "+p2Roll.getValue());
        System.out.println(playerTurn.getPlayerName()+" goes first!");
        nextPlayerTurn = playerTurn;


        table = new BackgammonTable();
        table.initializeBoard();

        displayManager.addToCache(playerTurn.getDie().get(0),22,14);
        displayManager.addToCache(playerTurn.getDie().get(1),72,14);

        printMoves(BackgammonTable.BOTTOM_OFF_FRAME);
        this.dealer = new Dealer(table);
        // Initialization complete


        synchronized (inputLock) {
            inputLock.notify(); // Notify the waiting GameTester thread
        }

        this.gameLoop();
    }
    public void gameLoop(){
        while(!gameOver){
            displayManager.addToCache(0,table, 0, 0);
            setPlayerFrame();
            displayManager.printDisplay();
            displayManager.clearCache();

            String input = requestNextInput();
            commandType = Commander.interpretCommand(input);
            executeCommand();

            gameOver = isGameOver(table);
            playerTurn = nextPlayerTurn;
        }
        this.endGame();
    }
    private void endGame(){
        displayManager.clearCache();
        winnerPlayer.setMatchPoints(player1.getMatchPoints() + gameValue);
        displayManager.addToCache(new AsciiString(winnerPlayer.getPlayerName() +
                " has won the game and " + gameValue + " points"),0,0);
        displayManager.addToCache(new AsciiString("The current score tally is:\n" +
                player1.getPlayerName() + " has " + player1.getMatchPoints() + " points\n" +
                player2.getPlayerName() + " has " + player2.getMatchPoints() + " points"),0,1);
        displayManager.printDisplay();
        displayManager.clearCache();
        try {
            matchChannel.put(CommandType.INVALID); // Tell the match thread what game it is
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void setPlayerFrame(){
        if (Objects.equals(playerTurn.getPlayerColour(), ColorsAscii.RED)){
            Checker frame = new Checker(ColorsAscii.RED); // this color doesn't matter btw
            frame.getArt().setFileLocation("/resources/board_frame_red.txt");
            displayManager.addToCache(0,frame, 0, 0);
        }
        else {
            Checker frame = new Checker(ColorsAscii.WHITE); // this color doesn't matter btw
            frame.getArt().setFileLocation("/resources/board_frame_white.txt");
            displayManager.addToCache(0,frame, 0, 0);
        }
    }


    // Returns true if the game has ended and sets the winning player in winnerPlayer
    private boolean isGameOver(BackgammonTable table){
        Lane redBearArea = table.getRedBearArea();
        Lane whiteBearArea = table.getWhiteBearArea();

        if(redBearArea.getSize() == BackgammonTable.TOTAL_PLAYER_CHECKER_NUM){
            winnerPlayer = playerTurn;
            return true;
        }
        else if (whiteBearArea.getSize() == BackgammonTable.TOTAL_PLAYER_CHECKER_NUM){
            winnerPlayer = playerTurn;
            return true;
        }
        else return false;

    }

    private void checkBearOff(){
        ColorsAscii playerColor = playerTurn.getPlayerColour();
        // check for RED player
        if (playerColor == ColorsAscii.RED){
            boolean canBearOff = true;
            // Set the bounds of the quadrant where the player can bear off
            int lowestLane = 6;
            int highestLane = 11;
            for(int i = 0; i<BackgammonTable.TOTAL_LANES;i++){
                if(lowestLane < i && i < highestLane){

                }
            }
        }

        // check for WHITE player
    }

    // **Command section**



    public void executeCommand(){
        switch (commandType){
            case QUIT:
                System.exit(42);
            case ROLL:
                if (!playerTurn.getHasRolled()){
                    playerTurn.rollMoves();
                    displayManager.addToCache(playerTurn.getDie().get(0),22,14);
                    displayManager.addToCache(playerTurn.getDie().get(1),72,14);

                    printMoves(BackgammonTable.BOTTOM_OFF_FRAME);
                    playerTurn.setHasRolled(true);
                }
                else {
                    displayManager.addToCache(new AsciiString("You have already rolled"),0,BackgammonTable.BOTTOM_OFF_FRAME);
                }
                printMoves(BackgammonTable.BOTTOM_OFF_FRAME+1);
                nextPlayerTurn = playerTurn;
                break;
            case MOVE:
                moveCommand();
                break;
            case PIP:
                pipCommand();
                nextPlayerTurn = playerTurn;
                break;
            case HINT:
                hintCommand();
                nextPlayerTurn = playerTurn;
                break;
            case DICE:
                diceCommand();
                break;
            case TEST:
                testCommand();
                break;
            case INVALID:
                displayManager.addToCache(new AsciiString("Invalid command\nPlease type 'help' or 'hint' to see the list of commands."), 0 ,BackgammonTable.BOTTOM_OFF_FRAME);
                nextPlayerTurn = playerTurn;
                break;
            default:
                wakeDaddyUp();
        }
    }


    private String testFilePath;

    public String getTestFilePath() {
        return testFilePath;
    }

    private void testCommand(){
        this.testMode = true;
        testFilePath = currentInput.split("\\s+",2)[1];
    }

    public void moveCommand(){
        int moveIndex = currentInput.getBytes()[0]-97;
        ArrayList<ArrayList<Integer>> moves = listMoves();

        // When player enters a letter without rolling first
        if(moves.isEmpty()){
            displayManager.addToCache(new AsciiString("You cannot make a move without rolling your dice \nType 'help' or 'hint' to see the list of commands"), 0 ,BackgammonTable.BOTTOM_OFF_FRAME);
            return;
        }
        // When player enters a letter that is not an available move
        if(moveIndex > moves.size()-1) {
            displayManager.addToCache(new AsciiString("You cannot this move, choose from the list of available moves"), 0 ,BackgammonTable.BOTTOM_OFF_FRAME);
            printMoves(BackgammonTable.BOTTOM_OFF_FRAME+1);
            return;
        }
        ArrayList<Integer> movePair = moves.get(moveIndex);
        int diceSize = Math.abs(movePair.get(0)-movePair.get(1));
        for (int i = 0;i < playerTurn.getDie().size(); i++) {
            if (playerTurn.getDie().get(i).getValue() == diceSize) {
                playerTurn.getDie().remove(i);
                break;
            }
        }

        // TODO: make kill move after from bar move
        if (movePair.get(1)>=24){
            bearOffMove(movePair);
        }
        else if (movePair.get(0) == -1) fromBarMove(movePair);
        else if (table.getLanes().get(unOrder(movePair.get(1))).getColour() != playerTurn.getPlayerColour() && table.getLanes().get(unOrder(movePair.get(1))).getSize() == 1){
            barMove(movePair);
        }
        else normalMove(movePair);

        // Finished moving now check if game needs to change player or show their remaining moves
        if (this.playerTurn.getDie().isEmpty()){
            if (player1 == playerTurn) nextPlayerTurn = player2;
            else nextPlayerTurn = player1;
            playerTurn.setHasRolled(false);
        }
        printMoves(BackgammonTable.BOTTOM_OFF_FRAME+1);
    }
    private void normalMove(ArrayList<Integer> movePair){
        dealer.moveAChecker(unOrder(movePair.get(0)),unOrder(movePair.get(1)));
    }

    private void fromBarMove(ArrayList<Integer> movePair) {
        if (table.getLanes().get(unOrder(movePair.get(1))).getColour() != playerTurn.getPlayerColour() && table.getLanes().get(unOrder(movePair.get(1))).getSize() == 1){
            dealer.moveToBar(unOrder(movePair.get(1)));
        }
        dealer.moveFromBar(unOrder(movePair.get(1)), playerTurn.getPlayerColour());
    }
    private void barMove(ArrayList<Integer> movePair){
        dealer.moveToBar(unOrder(movePair.get(1)));
        dealer.moveAChecker(unOrder(movePair.get(0)), unOrder(movePair.get(1)));
    }
    private void bearOffMove(ArrayList<Integer> movePair){
        dealer.bearCheckerOff(unOrder(movePair.get(0)));
    }

    private void printMoves(int yPos) {
        if (playerTurn.getHasRolled()) {
            ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
            moves = listMoves();
            moves = removeDuplicateMoves(moves);
            displayManager.addToCache(new AsciiString("Possible moves"), 0, yPos);
            String string = new String();
            for (int i = 0; i < moves.size(); i++) {
                string = string + (char) (i + 65) + ") ";
                string = string + (24 - moves.get(i).get(0));
                string = string + " -> ";
                string = string + (24 - moves.get(i).get(1));
                string = string + "\n";
            }
            displayManager.addToCache(new AsciiString(string), 0, yPos + 1);
        }
    }

    public static ArrayList<ArrayList<Integer>> removeDuplicateMoves(ArrayList<ArrayList<Integer>> moves){
        ArrayList<ArrayList<Integer>> newMoves = new ArrayList<>();
        for (ArrayList<Integer> move : moves) {
            if (!newMoves.contains(move)) {
                newMoves.add(move);
            }
        }
        return newMoves;
    }

    private void pipCommand(){
        // reordering the lanes for each colour to make finding the possible moves easier.
        ArrayList<Lane> lanes = new ArrayList<>();

        for (int i = BackgammonTable.TOTAL_LANES-1; i >= BackgammonTable.LANES_PER_ROW; i--){
            lanes.add(table.getLane(i));
        }
        for (int i = 0; i < BackgammonTable.LANES_PER_ROW; i++){
            lanes.add(table.getLane(i));
        }

        int redPip = 0;
        int whitePip = 0;

        int laneNumRed = 0;
        int laneNumWhite = 23;
        for (Lane lane : lanes){
            if (Objects.equals(lane.getColour(), ColorsAscii.RED)){
                redPip += lane.getSize()*(24-laneNumRed);
            }
            else if (Objects.equals(lane.getColour(), ColorsAscii.WHITE)){
                whitePip += lane.getSize()*(24-laneNumWhite);
            }
            laneNumRed += 1;
            laneNumWhite -= 1;
        }

        if ( Objects.equals(player1.getPlayerColour(),ColorsAscii.RED)){
            player1.setPipScore(redPip);
            player2.setPipScore(whitePip);
        }
        else {
            player1.setPipScore(whitePip);
            player2.setPipScore(redPip);
        }

        displayManager.addToCache(new AsciiString(player1.getPlayerName() + "'s pip score is: " + player1.getPipScore()), 0, BackgammonTable.BOTTOM_OFF_FRAME);
        displayManager.addToCache(new AsciiString(player2.getPlayerName() + "'s pip score is: " + player2.getPipScore()), 0, BackgammonTable.BOTTOM_OFF_FRAME+1);

    }

    private void hintCommand(){
        displayManager.addToCache(new AsciiString("Available commands"), 0, BackgammonTable.BOTTOM_OFF_FRAME);
        int i = 0;
        for (CommandType command : CommandType.values()){
            if (Objects.equals(command,CommandType.INVALID)){

            }
            else {
                displayManager.addToCache(new AsciiString("--   "+command.getDescription()), 0, BackgammonTable.BOTTOM_OFF_FRAME+i+1);
                i++;
            }
        }
    }

    private void diceCommand(){
        // THIS COMMAND DOES NOT RESULT IN THE MOVES LIST GETTING PRINTED FOR THE PLAYER
        String[] tokens = currentInput.split("\\s");
        Dice die_1 = new Dice();
        Dice die_2 = new Dice();
        int num1 = Integer.parseInt(tokens[1]);
        int num2 = Integer.parseInt(tokens[2]);
        die_1.setValue(num1);
        die_2.setValue(num2);
        playerTurn.setDie(new ArrayList<Dice>(List.of(die_1, die_2)));
        printMoves(BackgammonTable.BOTTOM_OFF_FRAME);
    }

    /** gives you the move index for the current players perspective given then objective index*/
    public int unOrder(int moveIndex){
        if (moveIndex == -1){
            return moveIndex;
        }
        if (playerTurn.getPlayerColour() == ColorsAscii.RED) {
            if (moveIndex < BackgammonTable.LANES_PER_ROW) {
                moveIndex = BackgammonTable.TOTAL_LANES - 1 - moveIndex;
            } else {
                moveIndex =  moveIndex - 12;
            }
        }
        else if (playerTurn.getPlayerColour() == ColorsAscii.WHITE){
            if (moveIndex < BackgammonTable.LANES_PER_ROW){
                moveIndex = BackgammonTable.LANES_PER_ROW-1-moveIndex;
            }
        }
        return moveIndex;
    }

    private boolean checkCanPlayerBearOff(){
        boolean canBearOff = true;
        ColorsAscii currentPlayerColor = playerTurn.getPlayerColour();
        if (currentPlayerColor == ColorsAscii.WHITE){
            for (int i = 0; i < 18; i++) {
                if (table.getLane(i).getColour() == currentPlayerColor){
                    canBearOff = false;
                    break;
                }
            }
        }
        if (currentPlayerColor == ColorsAscii.RED){
            for (int i = 0; i < 6; i++) {
                if (table.getLane(i).getColour() == currentPlayerColor){
                    canBearOff = false;
                    break;
                }
            }
            for (int i = 12; i < 24; i++) {
                if (table.getLane(i).getColour() == currentPlayerColor){
                    canBearOff = false;
                    break;
                }
            }
        }
        return canBearOff;
    }

    // Threaded stuff
    private boolean waitingForInput;
    private boolean testMode;
    private final Object inputLock;
    @Override
    public void run() {
        startGame();
    }

    public synchronized boolean testMode() {
        return this.testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }


    // This method is only used by the GameTester to tell the game thread to process the input it just acquired
    public synchronized void processInput(String input) {
        currentInput = input;
        waitingForInput = false;
        notify(); // Notify that input is ready for processing
        while (!waitingForInput) {
            try {
                wait(); // Wait if input is already present
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }


    // This method is used by the game thread to ask the input thread for another input
    public synchronized String requestNextInput() {
        waitingForInput = true;
        notify(); // Notify that input has been consumed
        while (waitingForInput) {
            try {
                wait(); // Wait for input to be available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return currentInput;
    }

    BlockingQueue<CommandType> matchChannel;
    Semaphore matchSemaphore;

    private synchronized void wakeDaddyUp(){
        try {
            matchChannel.put(commandType); // Tell the match thread what game it is
            matchSemaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
