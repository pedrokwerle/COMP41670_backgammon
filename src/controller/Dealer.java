package controller;

import model.BackgammonTable;
import model.Checker;
import userInterface.ColorsAscii;

import java.util.ArrayList;
import java.util.Objects;

public class Dealer {

    BackgammonTable table;
     public Dealer(BackgammonTable table){
         this.table = table;
     }

     public void moveAChecker(int fromLaneNum, int toLaneNum){
         // lane numbering goes A->0, B->1,...Y->23
         Checker checker = table.getLanes().get(fromLaneNum).removeChecker();
         table.getLanes().get(toLaneNum).addChecker(checker);
     }
     public void moveToBar(int fromLaneNum){
         Checker checker = table.getLanes().get(fromLaneNum).removeChecker();
         table.getBarArea().addChecker(checker);
     }
     public void moveFromBar(int toLaneNum, ColorsAscii color){
         ArrayList<Checker> barCheckers = table.getBarArea().getCheckers();
         for (int i = 0; i < barCheckers.size(); i++){
             if(barCheckers.get(i).getColor() == color){
                 Checker checker = barCheckers.get(i);
                 barCheckers.remove(i);
                 table.getLane(toLaneNum).addChecker(checker);
                 return;
             }
         }
     }

     public void bearCheckerOff(int fromLaneNum){
         Checker checker = table.getLanes().get(fromLaneNum).removeChecker();
         if (Objects.equals(checker.getColor(),ColorsAscii.RED)){
             table.getRedBearArea().addChecker(checker);
         }
         else {
             table.getWhiteBearArea().addChecker(checker);
         }
     }


}
