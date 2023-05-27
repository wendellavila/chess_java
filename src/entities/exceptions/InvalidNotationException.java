package entities.exceptions;

public class InvalidNotationException extends Exception {
    public InvalidNotationException(String invalidNotation){
        super(invalidNotation + ": Invalid notation.\nSpecify only the origin and destination squares in your input," +
                "\nusing letters a-h for files and numbers 1-8 for ranks.\nEx: \"e2 e4\"");
    }
}
