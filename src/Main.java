import model.Checker;
import model.LaneDownward;
import userInterface.ColorsAscii;
import userInterface.DisplayManager;
import model.*;
import java.util.*;


public class Main {
    public static final int LANE_SPACING = 8;
    public static void main(String[] args) {
        demo();
    }

    public static void demo(){
        Scanner Main = new Scanner(System.in);
        System.out.println("Please input Player 1's name:");
        Player player1 = new Player(Main.nextLine());

        System.out.println("Please input Player 2's name:");
        Player player2 = new Player(Main.nextLine());

        System.out.println(player1.getPlayerName()+player2.getPlayerName());
        Checker frame = new Checker(ColorsAscii.WHITE);
        frame.getArt().setFileLocation("src/model/board_frame.txt");
        //

        DisplayManager displayManager = new DisplayManager(30,100);
        displayManager.addToCache(frame,0,0);


        ArrayList<Lane> lanes = new ArrayList<>();
        // top lanes left
        int xpos = 2;
        int ypos = 2;
        for(int laneNum = 0; laneNum<6; laneNum++){
            LaneDownward lane = new LaneDownward();
            lanes.add(lane);
            displayManager.addToCache(lane,xpos,ypos);
            xpos += LANE_SPACING;
        }

        // top lanes rigth
        xpos = 2+6*LANE_SPACING+1;
        ypos = 2;
        for(int laneNum = 0; laneNum<6; laneNum++){
            LaneDownward lane = new LaneDownward();
            lanes.add(lane);
            displayManager.addToCache(lane,xpos,ypos);
            xpos += LANE_SPACING;
        }

        // bottom lanes left
        xpos = 2;
        ypos = 22;
        for(int laneNum = 0; laneNum<6; laneNum++){
            LaneUpward lane = new LaneUpward();
            lanes.add(lane);
            displayManager.addToCache(lane,xpos,ypos);
            xpos += LANE_SPACING;
        }

        // bottom lanes right
        xpos = 2+6*LANE_SPACING+1;
        ypos = 22;
        for(int laneNum = 0; laneNum<6; laneNum++){
            LaneUpward lane = new LaneUpward();
            lanes.add(lane);
            displayManager.addToCache(lane,xpos,ypos);
            xpos += LANE_SPACING;
        }

        for(int i = 0; i<2; i++){
            lanes.get(11).addChecker(new Checker(ColorsAscii.WHITE));
        }
        for(int i = 0; i<2; i++){
            lanes.get(23).addChecker(new Checker(ColorsAscii.RED));
        }

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
        color = ColorsAscii.RED;
        for(int j = 4; j<24; j += 12){
            for(int i = 0; i<3; i++){
                lanes.get(j).addChecker(new Checker(color));
            }
            color = ColorsAscii.WHITE;

        }


        displayManager.printDisplay();
    }
}
