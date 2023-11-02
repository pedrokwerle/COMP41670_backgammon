package model;

import userInterface.AsciiArt;
import userInterface.Displayable;

import java.util.ArrayList;

public class BackgammonTable implements Displayable {
    ArrayList<Lane> lanes;
    public BackgammonTable(){
        this.lanes = new ArrayList<>();
    }

    @Override
    public void renderArt() {

    }

    @Override
    public AsciiArt getArt() {
        return null;
    }
}
