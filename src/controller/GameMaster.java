package controller;
import model.*;
import userInterface.ColorsAscii;
import userInterface.DisplayManager;
import userInterface.Keyboard;


import java.util.ArrayList;
import java.util.Objects;

public class GameMaster {
    ArrayList<String> moveLog;
    Player player1;
    Player player2;
    Player playerTurn;
    Player nextPlayerTurn;
    DisplayManager displayManager;
    Dealer dealer;
    BackgammonTable table;
    Keyboard key;
    String userInput;
    CommandType commandType;

    public GameMaster() {
        this.key = new Keyboard();
    }

    public ArrayList<ArrayList<Integer>> listMoves(BackgammonTable board, Player player, ArrayList<Dice> die){
        ArrayList<Lane> lanes = new ArrayList<>();
        ArrayList<ArrayList<Integer>> moves = new ArrayList<>();

        // reordering the lanes for each colour to make finding the possible moves easier.
        if (player.getPlayerColour() == ColorsAscii.RED){
            for (int i = BackgammonTable.TOTAL_LANES-1; i >= BackgammonTable.LANES_PER_ROW; i--){
                lanes.add(board.getLane(i));
            }
            for (int i = 0; i < BackgammonTable.LANES_PER_ROW; i++){
                lanes.add(board.getLane(i));
            }
        } else if (player.getPlayerColour() == ColorsAscii.WHITE) {
            for (int i = BackgammonTable.LANES_PER_ROW-1; i >= 0; i--){
                lanes.add(board.getLane(i));
            }
            for (int i = BackgammonTable.LANES_PER_ROW; i < BackgammonTable.TOTAL_LANES; i++){
                lanes.add(board.getLane(i));
            }
        }

        for (int i = 0; i < lanes.size(); i++){
            // finding a lane with the players color checker
            if (lanes.get(i).getColour() == player.getPlayerColour()){
                // looping through each of the dice the player rolled
                for (int j = 0;j < die.size();j++){
                    ArrayList<Integer> move = new ArrayList<>();
                    // check if moving from this lane to that with the dice is valid
                    if (i+die.get(j).getValue() >= 24) {
                        // can bear off ** need to add checker that all pieces are home corner**
                        move.add(i);
                        move.add(29);
                        moves.add(move);
                    }
                    else if (lanes.get(i+die.get(j).getValue()).getColour() == player.getPlayerColour()){
                        //is a possible move, just adds to the lanes
                        move.add(i);
                        move.add(i+die.get(j).getValue());
                        moves.add(move);
                    }
                    else if(lanes.get(i+die.get(j).getValue()).getSize() == 1){
                        // will kill the enemy piece and send it to the bar
                        move.add(i);
                        move.add(i+die.get(j).getValue());
                        moves.add(move);
                    }
                    else if(lanes.get(i+die.get(j).getValue()).getSize() == 0) {
                        //is a possible move, just adds to the lanes
                        move.add(i);
                        move.add(i+die.get(j).getValue());
                        moves.add(move);
                    }
                }
            }
        }

        // unshuffle the lane numbers
        for (int i = 0;i < moves.size();i++){
            if (player.getPlayerColour() == ColorsAscii.RED){
                if (moves.get(i).get(0) < BackgammonTable.LANES_PER_ROW){
                    moves.get(i).set(0,BackgammonTable.TOTAL_LANES-1-moves.get(i).get(0));
                }
                else if (moves.get(i).get(0) >= BackgammonTable.LANES_PER_ROW){
                    moves.get(i).set(0,moves.get(i).get(0)-12);
                }
                if (moves.get(i).get(1) < BackgammonTable.LANES_PER_ROW){
                    moves.get(i).set(1,BackgammonTable.TOTAL_LANES-1-moves.get(i).get(1));
                }
                else if (moves.get(i).get(1) >= BackgammonTable.LANES_PER_ROW){
                    moves.get(i).set(1,moves.get(i).get(1)-12);
                }
            }
            else if (player.getPlayerColour() == ColorsAscii.WHITE){
                if (moves.get(i).get(0) < BackgammonTable.LANES_PER_ROW){
                    moves.get(i).set(0,BackgammonTable.LANES_PER_ROW-1-moves.get(i).get(0));
                }
                if (moves.get(i).get(1) < BackgammonTable.LANES_PER_ROW){
                    moves.get(i).set(1,BackgammonTable.LANES_PER_ROW-1-moves.get(i).get(1));
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
        System.out.println(player1.getPlayerName()+" has rolled a "+p1Roll.getValue()+" and "+player2.getPlayerName()+ " has rolled a "+p2Roll.getValue());
        System.out.println(playerTurn.getPlayerName()+" goes first!");

        this.displayManager = new DisplayManager(30,100);

        table = new BackgammonTable();
        table.initializeBoard();
        displayManager.addToCache(table,0,2);

        displayManager.printDisplay();
        this.dealer = new Dealer(table);
        // Initialization complete

        this.gameLoop();
    }
    public void gameLoop(){
        while(true){
            takeInput();
            interpretCommand();
            executeCommand();

            playerTurn = nextPlayerTurn;
        }
    }

    // Command section
    public void takeInput(){
        System.out.println(playerTurn.getPlayerName()+" please enter your command: ");
        userInput = key.getString();
    }
    public void interpretCommand(){
        if (Objects.equals(userInput.toLowerCase(), "quit")) {
            commandType = CommandType.QUIT;
        }
        else if (Objects.equals(userInput.toLowerCase(), "roll")){
            commandType = CommandType.ROLL;
        }
        else if (userInput.matches("[a-zA-Z]")){
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
        else commandType = CommandType.INVALID;
    }

    public void executeCommand(){
        switch (commandType){
            case QUIT:
                System.exit(42);
            case ROLL:
                playerTurn.rollMoves();
                ArrayList<Dice> die = playerTurn.getDie();
                displayManager.addToCache(die.get(0),25,11);
                displayManager.addToCache(die.get(1),74,11);

                ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
                moves = listMoves(table, playerTurn, die);

                System.out.println("Possible moves");
                for (int i=0;i < moves.size(); i++){
                    System.out.print((char)(moves.get(i).get(0)+65));
                    System.out.print((char)(moves.get(i).get(1)+65));
                    System.out.print("\n");
                }
                nextPlayerTurn = playerTurn;
                break;
            case MOVE:
                if (Objects.equals(playerTurn, player1))
                    nextPlayerTurn = player2;
                else nextPlayerTurn = player1;
                break;
            default:
        }
    }

}
