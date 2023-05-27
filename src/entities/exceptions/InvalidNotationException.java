package entities.exceptions;

public class InvalidNotationException extends Exception {

    private static final String ANSI_RED = "\u001B[38;5;202m";
    private static final String ANSI_YELLOW = "\u001B[38;5;220m";
    private static final String ANSI_RESET = "\u001B[0m";

    public InvalidNotationException(String invalidNotation){
        super(ANSI_RED + invalidNotation + ": Invalid notation.\n" + ANSI_YELLOW + "Specify only the origin and destination squares in your input," +
                "\nusing letters a-h for files and numbers 1-8 for ranks.\nEx: \"e2 e4\"" + ANSI_RESET);
    }
}
