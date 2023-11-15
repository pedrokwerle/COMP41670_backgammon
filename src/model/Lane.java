package model;

import userInterface.AsciiArt;
import userInterface.Displayable;

import java.util.ArrayList;

public abstract class Lane implements Displayable {
    ArrayList<Checker> checkers;
    public Lane(){
        this.checkers = new ArrayList<>();
        this.asciiArt = new AsciiArt("/resources/empty_lane.txt");
    }
    int size(){
        return this.checkers.size();
    }

    Checker removeChecker(){
        Checker checker;
        checker = this.checkers.get(this.checkers.size()-1);
        this.checkers.remove(this.checkers.size()-1);
        return checker;
    }
    public void addChecker(Checker checker){
        this.checkers.add(checker);
    }


    //AsciiArt
    AsciiArt asciiArt;

    @Override
    public AsciiArt getArt() {
        return this.asciiArt;
    }
}
