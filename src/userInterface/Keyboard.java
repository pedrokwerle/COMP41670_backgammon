package userInterface;

import java.util.Scanner;

public class Keyboard {

    public Keyboard() {
    }

    public String getString() {
        boolean valid = false;
        String userInput = null;
        Scanner scan = new Scanner(System.in);
        userInput = scan.nextLine();

        return userInput;
    }
}
