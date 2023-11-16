package controller;
import model.*;
import userInterface.ColorsAscii;


import java.util.ArrayList;

public class GameMaster {
    ArrayList<String> moveLog;
    public GameMaster(){
        this.moveLog = new ArrayList<>();
    }

    public void listMoves(BackgammonTable board,Player player, ArrayList<Dice> die){
        ArrayList<Lane> lanes = null;

        // reordering the lanes for each colour to make finding the possible moves easier.
        if (player.getPlayerColour() == ColorsAscii.WHITE){
            for (int i = BackgammonTable.TOTAL_LANES-1; i >= BackgammonTable.LANES_PER_ROW; i--){
                lanes.add(board.getLane(i));
            }
            for (int i = 0; i < BackgammonTable.LANES_PER_ROW; i++){
                lanes.add(board.getLane(i));
            }
        } else if (player.getPlayerColour() == ColorsAscii.RED) {
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
                    // check if moving from this lane to that with the dice is valid
                    if (board.getLane(i+die.get(j).getValue()).getColour() == player.getPlayerColour()){
                        //is a possible move, just adds to the lanes
                    }
                    else if(board.getLane(i+die.get(j).getValue()).getSize() == 1){
                        // will kill the enemy piece and send it to the bar
                    }
                    else if(board.getLane(i+die.get(j).getValue()).getSize() == 0) {
                        //is a possible move, just adds to the lanes
                    }
                    else if (i+die.get(j).getValue() == 24) {
                        // can bear off ** need to add checker that all pieces are home corner**
                    }
                }
            }
        }
    }
}
