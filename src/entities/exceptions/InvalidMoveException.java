package entities.exceptions;

import entities.utils.ANSIColors;

public class InvalidMoveException extends Exception {

    public InvalidMoveException(String invalidMove, String message){
        super(ANSIColors.ANSI_RED + invalidMove + ": Invalid move.\n" + ANSIColors.ANSI_YELLOW + message + ANSIColors.ANSI_RESET);
    }
}
