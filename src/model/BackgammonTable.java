package model;

import userInterface.AsciiArt;
import userInterface.ColorsAscii;
import userInterface.Displayable;

import java.util.ArrayList;
import java.util.Objects;

public class BackgammonTable implements Displayable {
    ArrayList<Lane> lanes;
    public static final int LANES_PER_ROW = 12;
    public static final int TOTAL_LANES = 24;
    public static final int LANE_SPACING = 8;
    public static final int BOTTOM_ROW_POSITION = 24;
    public static final int BOTTOM_OFF_FRAME = 28; // first blank line under the frame



    public BackgammonTable(){
        this.lanes = new ArrayList<>();
        this.asciiArt = new AsciiArt("");
    }

    public void initializeBoard(){
        for (int i = 0; i < LANES_PER_ROW; i++){
            this.lanes.add(new LaneDownward());
        }
        for (int i = 0; i < LANES_PER_ROW; i++){
            this.lanes.add(new LaneUpward());
        }

        // Two checker lanes
        for(int i = 0; i<2; i++){
            lanes.get(11).addChecker(new Checker(ColorsAscii.WHITE));
        }
        for(int i = 0; i<2; i++){
            lanes.get(23).addChecker(new Checker(ColorsAscii.RED));
        }


        // FIVE CHECKER LANES
        ColorsAscii color = ColorsAscii.WHITE;
        for(int j = 0; j<24; j += 6){
            for(int i = 0; i<5; i++){
                lanes.get(j).addChecker(new Checker(color));

            }
            if(Objects.equals(color,ColorsAscii.WHITE ) || (j== 6)){
                color = ColorsAscii.RED;
            }
            else{
                color = ColorsAscii.WHITE;
            }

        }

        // THREE CHECKER LANES
        color = ColorsAscii.RED;
        for(int j = 4; j<24; j += 12){
            for(int i = 0; i<3; i++){
                lanes.get(j).addChecker(new Checker(color));
            }
            color = ColorsAscii.WHITE;

        }
    }

    public Lane getLane(int laneNum){
        return this.lanes.get(laneNum);
    }

    public ArrayList<Lane> getLanes(){
        return this.lanes;
    }



    // Ascii art section:

    AsciiArt asciiArt;

    @Override
    public void renderArt() {
        this.asciiArt.clearComponents();

        int xpos = 2;
        int ypos = 2;

        // top lanes left
        for (int i = 0; i < LANES_PER_ROW/2; i++){
            this.asciiArt.addComponent(this.lanes.get(i),xpos,ypos);
            this.lanes.get(i).renderArt();
            xpos += LANE_SPACING;
        }

        // top lanes right
        xpos += 1;
        for (int i = LANES_PER_ROW/2; i < LANES_PER_ROW; i++){
            this.asciiArt.addComponent(this.lanes.get(i),xpos,ypos);
            this.lanes.get(i).renderArt();
            xpos += LANE_SPACING;
        }

        xpos = 2;
        ypos = BOTTOM_ROW_POSITION;

        // bottom lanes left
        for (int i = LANES_PER_ROW; i < (TOTAL_LANES+LANES_PER_ROW)/2; i++){
            this.asciiArt.addComponent(this.lanes.get(i),xpos,ypos);
            this.lanes.get(i).renderArt();
            xpos += LANE_SPACING;
        }


        // bottom lanes right
        xpos +=1;
        for (int i = (TOTAL_LANES+LANES_PER_ROW)/2; i < TOTAL_LANES; i++){
            this.asciiArt.addComponent(this.lanes.get(i),xpos,ypos);
            this.lanes.get(i).renderArt();
            xpos += LANE_SPACING;
        }



    }

    @Override
    public AsciiArt getArt() {
        return this.asciiArt;
    }
}
