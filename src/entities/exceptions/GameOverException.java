package entities.exceptions;

import entities.utils.ANSICodes;

public class GameOverException extends Exception {
    public GameOverException(String message){
        super(ANSICodes.ANSI_GREEN + message + ANSICodes.ANSI_RESET);
    }
}
