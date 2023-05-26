package entities;

import entities.enums.Color;
import entities.exceptions.CheckmateException;
import entities.exceptions.InvalidMoveException;
import entities.exceptions.InvalidNotationException;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board {

    //ANSI codes for printing colored text on UNIX-like terminals
    private static final String ANSI_GREEN_BG = "\u001B[48;5;107m";
    private static final String ANSI_BROWN_BG = "\u001B[48;5;95m";
    private static final String ANSI_BLACK = "\u001B[38;5;236m";
    private static final String ANSI_WHITE = "\u001B[38;5;229m";
    private static final String ANSI_RESET = "\u001B[0m";

    private int moveCount = 0;
    private Piece[][] positions = new Piece[8][8];
    private String capturedFromWhite = "";
    private String capturedFromBlack = "";

    //regex used by movePieces
    private static final Pattern inputPattern = Pattern.compile("([a-zA-Z])(\\d)\\s?-?([a-zA-Z])(\\d)");

    public Board(){

        positions[0][0] = new Rook(Color.WHITE, 0, 0, this);
        positions[0][1] = new Knight(Color.WHITE, 0, 1, this);
        positions[0][2] = new Bishop(Color.WHITE, 0, 2, this);
        positions[0][3] = new King(Color.WHITE, 0, 3, this);
        positions[0][4] = new Queen(Color.WHITE, 0, 4, this);
        positions[0][5] = new Bishop(Color.WHITE, 0, 5, this);
        positions[0][6] = new Knight(Color.WHITE, 0, 6, this);
        positions[0][7] = new Rook(Color.WHITE, 0, 7, this);

        for(int j=0; j < 8; j++){
            positions[1][j] = new Pawn(Color.WHITE, 1, j, this);
            for (int i = 2; i < 6; i++){
                positions[i][j] = null;
            }
            positions[6][j] = new Pawn(Color.BLACK, 6, j, this);
        }

        positions[7][0] = new Rook(Color.BLACK, 7, 0, this);
        positions[7][1] = new Knight(Color.BLACK, 7, 1, this);
        positions[7][2] = new Bishop(Color.BLACK, 7, 2, this);
        positions[7][3] = new King(Color.BLACK, 7, 3, this);
        positions[7][4] = new Queen(Color.BLACK, 7, 4, this);
        positions[7][5] = new Bishop(Color.BLACK, 7, 5, this);
        positions[7][6] = new Knight(Color.BLACK, 7, 6, this);
        positions[7][7] = new Rook(Color.BLACK, 7, 7, this);

        for(int i : new int[]{0, 1, 6, 7}){
            for(int j = 0; j < 8; j++){
                positions[i][j].calculatePermittedMoves();
            }
        }
    }

    public Piece getPieceByPosition(int i, int j){
        return positions[i][j];
    }

    public int getMoveCount(){
        return moveCount;
    }

    public void movePieces(String input) throws InvalidNotationException, InvalidMoveException, CheckmateException {

        int originRow, originCol, destinationRow, destinationCol;
        Matcher matcher = inputPattern.matcher(input);

        if(matcher.matches()) {
            originRow = Integer.parseInt(matcher.group(2)) - 1;
            destinationRow = Integer.parseInt(matcher.group(4)) - 1;
            //converting letters a-h to integers 0-7
            originCol = (int) matcher.group(1).toLowerCase().charAt(0) - (int)'a';
            destinationCol = (int) matcher.group(3).toLowerCase().charAt(0) - (int)'a';

            if(originRow < 0 || originRow > 7 || destinationRow < 0 || destinationRow > 7 ||
                    originCol < 0 || originCol > 7 || destinationCol < 0 || destinationCol > 7){
                throw new InvalidMoveException(input);
            }

            Piece movingPiece = positions[originRow][originCol];
            if(movingPiece != null && movingPiece.isMovePermitted(destinationRow, destinationCol)){
                moveCount++;
                Piece destination = positions[destinationRow][destinationCol];
                //capture
                if (destination != null) {
                    if(destination.getColor() == Color.WHITE){
                        capturedFromWhite += destination.toString();
                    }
                    else {
                        capturedFromBlack += destination.toString();
                    }
                }
                positions[destinationRow][destinationCol] = movingPiece;
                positions[originRow][originCol] = null;
                movingPiece.updatePosition(destinationRow, destinationCol, moveCount);

                if(destination instanceof King){
                    throw new CheckmateException();
                }

                for(Piece[] row : positions){
                    for(Piece piece : row){
                        if(piece != null){
                            piece.calculatePermittedMoves();
                        }
                    }
                }
            }
            else {
                throw new InvalidMoveException(input);
            }
        }
        else {
            throw new InvalidNotationException(input);
        }
    }

    @Override
    public String toString(){

        char[] temp = capturedFromBlack.toCharArray();
        Arrays.sort(temp);
        capturedFromBlack = new String(temp);
        temp = capturedFromWhite.toCharArray();
        Arrays.sort(temp);
        capturedFromWhite = new String(temp);

        StringBuilder output = new StringBuilder("    a  b  c  d  e  f  g  h\n");
        for (int i = 7; i >= 0; i--){
            output.append(" ").append(i+1).append(" ");
            for(int j=0; j < 8; j++){
                //if row number + col number is even, square is light
                String tileColor = (i+j+2) % 2 == 0 ? ANSI_GREEN_BG : ANSI_BROWN_BG;
                if(positions[i][j] != null){
                    String pieceColor = positions[i][j].getColor() == Color.WHITE ? ANSI_WHITE : ANSI_BLACK;
                    output.append(tileColor).append(" ").append(pieceColor).append(positions[i][j].toString()).append(" ").append(ANSI_RESET);
                }
                else {
                    output.append(tileColor).append("   ").append(ANSI_RESET);
                }

            }
            output.append(" ").append(i+1).append("\n");
        }
        output.append("    a  b  c  d  e  f  g  h\n\n");
        output.append(" ").append(ANSI_GREEN_BG).append(ANSI_WHITE).append("[").append(ANSI_BLACK)
                .append(capturedFromBlack).append(ANSI_WHITE).append("]").append(ANSI_RESET);

        output.append(" ").append(ANSI_BROWN_BG).append(ANSI_BLACK).append("[").append(ANSI_WHITE)
                .append(capturedFromWhite).append(ANSI_BLACK).append("]").append(ANSI_RESET).append("\n");

        return output.toString();
    }
}
