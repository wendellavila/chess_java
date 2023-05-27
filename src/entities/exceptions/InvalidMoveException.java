package entities.exceptions;

public class InvalidMoveException extends Exception {
    public InvalidMoveException(String invalidMove, String message){
        super(invalidMove + ": Invalid move. " + message);
    }
}
