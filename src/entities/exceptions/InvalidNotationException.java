package entities.exceptions;

import entities.utils.ANSICodes;

public class InvalidNotationException extends Exception {

    public InvalidNotationException(String invalidNotation){
        super(ANSICodes.ANSI_RED + invalidNotation + ": Invalid notation.\n" + ANSICodes.ANSI_YELLOW + "Specify only the origin and destination squares in your input," +
                "\nusing letters a-h for files and numbers 1-8 for ranks.\nEx: \"e2 e4\"" + ANSICodes.ANSI_RESET);
    }
}
