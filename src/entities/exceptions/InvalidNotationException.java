package entities.exceptions;

import entities.utils.ANSIColors;

public class InvalidNotationException extends Exception {

    public InvalidNotationException(String invalidNotation){
        super(ANSIColors.ANSI_RED + invalidNotation + ": Invalid notation.\n" + ANSIColors.ANSI_YELLOW + "Specify only the origin and destination squares in your input," +
                "\nusing letters a-h for files and numbers 1-8 for ranks.\nEx: \"e2 e4\"" + ANSIColors.ANSI_RESET);
    }
}
