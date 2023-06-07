package entities.exceptions;

import entities.utils.ANSICodes;

public class InvalidMoveException extends Exception {

    public InvalidMoveException(String invalidMove, String message){
        super(ANSICodes.ANSI_RED + invalidMove + ": Invalid move.\n" + ANSICodes.ANSI_YELLOW + message + ANSICodes.ANSI_RESET);
    }
}
