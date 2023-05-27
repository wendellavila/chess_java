package entities.exceptions;

import entities.utils.ANSIColors;

public class CheckmateException extends Exception {
    public CheckmateException(String winner){
        super(ANSIColors.ANSI_GREEN + "Checkmate! - " + winner + " wins!" + ANSIColors.ANSI_RESET);
    }
}
