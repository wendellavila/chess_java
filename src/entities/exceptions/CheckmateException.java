package entities.exceptions;

public class CheckmateException extends Exception {
    public CheckmateException(String winner){
        super("Checkmate! - " + winner + " wins!");
    }
}
