package controller;

import java.util.Objects;

public class Commander {
    public static CommandType interpretCommand(String currentInput){
        // lowercase and remove leading or trailing spaces
        currentInput = currentInput.toLowerCase().trim();
        CommandType commandType;
        if (Objects.equals(currentInput, "quit")) {
            commandType = CommandType.QUIT;
        }
        else if (Objects.equals(currentInput, "roll")){
            commandType = CommandType.ROLL;
        }
        else if (currentInput.matches("[a-z]")){
            commandType = CommandType.MOVE;
        }
        else if (Objects.equals(currentInput, "hint") || Objects.equals(currentInput.toLowerCase(), "help")){
            commandType = CommandType.HINT;
        }
        else if (Objects.equals(currentInput, "pip")){
            commandType = CommandType.PIP;
        }
        else if(currentInput.matches("test\\s.+")){
            commandType = CommandType.TEST;
        }
        else if(currentInput.matches("dice\\s+\\d\\s+\\d")){
            commandType = CommandType.DICE;
        }
        else if(currentInput.matches("score")){
            commandType = CommandType.SCORE;
        }
        else commandType = CommandType.INVALID;

        return commandType;
    }
}
