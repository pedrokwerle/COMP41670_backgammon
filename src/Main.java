
import controller.Dealer;
import controller.GameMaster;
import controller.GameTester;
import controller.MatchMaker;
import userInterface.*;
import model.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        demo();
    }

    public static void demo(){
        MatchMaker matchMaker = new MatchMaker();
        matchMaker.startMatch();

    }
}
