package entities.exceptions;

import entities.utils.ANSICodes;

public class CheckmateException extends Exception {
    public CheckmateException(String winner){
        super(ANSICodes.ANSI_GREEN + "Checkmate! - " + winner + " wins!" + ANSICodes.ANSI_RESET);
    }
}
