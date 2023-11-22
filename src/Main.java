
import controller.Dealer;
import controller.GameMaster;
import userInterface.*;
import model.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        demo();
    }

    public static void demo(){
        GameMaster GM = new GameMaster();
        GM.startGame();

    }
}
