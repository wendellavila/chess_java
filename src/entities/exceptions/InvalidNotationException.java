package entities.exceptions;

public class InvalidNotationException extends Exception {
    public InvalidNotationException(String invalidNotation){
        super(invalidNotation + ": Invalid notation.\n Specify only the origin and destination squares in your input.\n Ex: \"e2 e4\"");
    }
}
