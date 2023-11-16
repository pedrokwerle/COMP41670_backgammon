
import userInterface.*;
import model.*;
import java.util.*;


public class Main {
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

        //

        DisplayManager displayManager = new DisplayManager(30,100);

        BackgammonTable board = new BackgammonTable();
        board.initializeBoard();
        displayManager.addToCache(board,0,2);

        displayManager.printDisplay();

        // Initialization complete
        Player playerTurn = player1;
        while(true){
            Keyboard key = new Keyboard();
            System.out.println(playerTurn.getPlayerName()+" please enter your command: ");
            String input =  key.getString();
            input = input.toLowerCase();
            ArrayList<Dice> die;
            if (Objects.equals(input, "quit")) System.exit(42);
            else if (Objects.equals(input, "roll")) {
                die = playerTurn.rollMoves();
                displayManager.addToCache(die.get(0),25,11);
                displayManager.addToCache(die.get(1),74,11);
            }





            if(Objects.equals(playerTurn, player1)){
                playerTurn = player2;
            }
            else{
                playerTurn = player1;
            }

            displayManager.printDisplay();
        }



    }
}
