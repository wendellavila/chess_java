package entities.exceptions;

public class InvalidMoveException extends Exception {

    private static final String ANSI_RED = "\u001B[38;5;202m";
    private static final String ANSI_YELLOW = "\u001B[38;5;220m";
    private static final String ANSI_RESET = "\u001B[0m";

    public InvalidMoveException(String invalidMove, String message){
        super(ANSI_RED + invalidMove + ": Invalid move.\n" + ANSI_YELLOW + message + ANSI_RESET);
    }
}
