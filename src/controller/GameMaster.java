package controller;
import model.*;
import userInterface.AsciiString;
import userInterface.ColorsAscii;
import userInterface.DisplayManager;
import userInterface.Keyboard;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameMaster {
    ArrayList<String> moveLog;
    Player player1;
    Player player2;
    Player playerTurn;
    Player nextPlayerTurn;
    Player winnerPlayer;
    DisplayManager displayManager;
    Dealer dealer;
    BackgammonTable table;
    Keyboard key;
    String userInput;
    CommandType commandType;

    public GameMaster() {
        this.key = new Keyboard();
    }

    public ArrayList<ArrayList<Integer>> listMoves(){
        ArrayList<Lane> lanes = new ArrayList<>();
        ArrayList<ArrayList<Integer>> moves = new ArrayList<>();


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
                        move.add(i);
                        move.add(24);
                        moves.add(move);
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
        Keyboard key = new Keyboard();
        System.out.println("Please input Player 1's name: ");
        this.player1 = new Player(key.getString(), ColorsAscii.WHITE);

        System.out.println("Please input Player 2's name: ");
        this.player2 = new Player(key.getString(), ColorsAscii.RED);

        System.out.println("Player 1: "+player1.getPlayerName()+", Player 2: "+player2.getPlayerName());

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

        this.displayManager = new DisplayManager(40,500);

        table = new BackgammonTable();
        table.initializeBoard();

        displayManager.addToCache(playerTurn.getDie().get(0),22,14);
        displayManager.addToCache(playerTurn.getDie().get(1),72,14);

        printMoves(BackgammonTable.BOTTOM_OFF_FRAME);
        this.dealer = new Dealer(table);
        // Initialization complete

        this.gameLoop();
        System.out.println("Congratulations, " + winnerPlayer.getPlayerName() +" has won the game!");
    }
    public void gameLoop(){
        boolean gameOver = false;
        while(!gameOver){
            displayManager.addToCache(0,table, 0, 0);
            setPlayerFrame();
            displayManager.printDisplay();
            displayManager.clearCache();

            takeInput();
            interpretCommand();
            executeCommand();

            gameOver = isGameOver(table);
            playerTurn = nextPlayerTurn;
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
    public void takeInput(){
        System.out.println(playerTurn.getPlayerName()+" please enter your command: ");
        userInput = key.getString();
    }
    public void interpretCommand(){

        int numMoves = listMoves().size();
        if(numMoves == 0){
            numMoves = 1; // might be dangerous
        }
        if (Objects.equals(userInput.toLowerCase(), "quit")) {
            commandType = CommandType.QUIT;
        }
        else if (Objects.equals(userInput.toLowerCase(), "roll")){
            commandType = CommandType.ROLL;
        }
        else if (userInput.toLowerCase().matches("["+(char)('a'-1)+'-'+ (char)('a'+numMoves) + "]")){
            commandType = CommandType.MOVE;
        }
        else if (Objects.equals(userInput.toLowerCase(), "hint") || Objects.equals(userInput.toLowerCase(), "help")){
            commandType = CommandType.HINT;
        }
        else if (Objects.equals(userInput.toLowerCase(), "pip")){
            commandType = CommandType.PIP;
        }
        else if(Objects.equals(userInput.toLowerCase(), "test")){
            commandType = CommandType.TEST;
        }
        else if(userInput.toLowerCase().matches("dice\\s\\d+\\s\\d+")){
            commandType = CommandType.DICE;
        }
        else commandType = CommandType.INVALID;
    }

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
                    printMoves(BackgammonTable.BOTTOM_OFF_FRAME+1);
                }

                nextPlayerTurn = playerTurn;
                break;
            case MOVE:
                moveCommand();
                if (this.playerTurn.getDie().isEmpty()){
                    if (player1 == playerTurn) nextPlayerTurn = player2;
                    else nextPlayerTurn = player1;
                    playerTurn.setHasRolled(false);
                }
                printMoves(BackgammonTable.BOTTOM_OFF_FRAME+1);
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

                break;
            case INVALID:
                displayManager.addToCache(new AsciiString("Invalid command. Please type 'help' or 'hint' to see the list of commands."), 0 ,BackgammonTable.BOTTOM_OFF_FRAME);
                nextPlayerTurn = playerTurn;
                break;
            default:
        }
    }

    public void moveCommand(){
        int moveIndex = userInput.getBytes()[0]-97;
        ArrayList<ArrayList<Integer>> moves = listMoves();
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
        else if (table.getLanes().get(unOrder(movePair.get(1))).getColour() != playerTurn.getPlayerColour() && table.getLanes().get(unOrder(movePair.get(1))).getSize() == 1){
            barMove(movePair);
        }
        else if (movePair.get(0) == -1) fromBarMove(movePair);
        else normalMove(movePair);
    }
    private void normalMove(ArrayList<Integer> movePair){
        dealer.moveAChecker(unOrder(movePair.get(0)),unOrder(movePair.get(1)));
    }

    private void fromBarMove(ArrayList<Integer> movePair) {
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
        String[] tokens = userInput.split("\\s");
        Dice die_1 = new Dice();
        Dice die_2 = new Dice();
        int num1 = Integer.parseInt(tokens[1]);
        int num2 = Integer.parseInt(tokens[2]);
        die_1.setValue(num1);
        die_2.setValue(num2);
        playerTurn.setMoveDie(new ArrayList<Dice>(List.of(die_1, die_2)));
        printMoves(BackgammonTable.BOTTOM_OFF_FRAME);


    }

    public int unOrder(int moveIndex){
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

}
