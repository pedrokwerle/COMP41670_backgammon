package controller;

import model.BackgammonTable;
import model.Checker;
import model.MovesLog;
import userInterface.ColorsAscii;

import java.util.Objects;

public class Dealer {
    MovesLog log;
    BackgammonTable table;
     public Dealer(BackgammonTable table){
         this.log = new MovesLog();
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
