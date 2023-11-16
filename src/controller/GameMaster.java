package controller;
import model.*;
import userInterface.ColorsAscii;


import java.util.ArrayList;

public class GameMaster {
    ArrayList<String> moveLog;
    public GameMaster(){
        this.moveLog = new ArrayList<>();
    }

    public ArrayList<ArrayList<Integer>> listMoves(BackgammonTable board,Player player, ArrayList<Dice> die){
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
}
