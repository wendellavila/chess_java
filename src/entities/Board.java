package entities;

import entities.enums.PieceColor;
import entities.utils.ANSIColors;
import entities.exceptions.CheckmateException;
import entities.exceptions.InvalidMoveException;
import entities.exceptions.InvalidNotationException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board {

    private int moveCount = 0;
    private final Piece[][] positions = new Piece[8][8];
    private String capturedFromWhite = "";
    private String capturedFromBlack = "";
    private final LinkedList<NotationEntry> latestPlays;

    //last move coordinates used in toString()
    private int lastOriginRow = -1;
    private int lastOriginCol = -1;
    private int lastDestinationRow = -1;
    private int lastDestinationCol = -1;

    //regex used by movePieces
    private static final Pattern inputPattern = Pattern.compile("([a-zA-Z])(\\d)\\s?-?([a-zA-Z])(\\d)");

    public Board(){

        positions[0][0] = new Rook(PieceColor.WHITE, 0, 0, this);
        positions[0][1] = new Knight(PieceColor.WHITE, 0, 1, this);
        positions[0][2] = new Bishop(PieceColor.WHITE, 0, 2, this);
        positions[0][3] = new King(PieceColor.WHITE, 0, 3, this);
        positions[0][4] = new Queen(PieceColor.WHITE, 0, 4, this);
        positions[0][5] = new Bishop(PieceColor.WHITE, 0, 5, this);
        positions[0][6] = new Knight(PieceColor.WHITE, 0, 6, this);
        positions[0][7] = new Rook(PieceColor.WHITE, 0, 7, this);

        for(int j=0; j < 8; j++){
            positions[1][j] = new Pawn(PieceColor.WHITE, 1, j, this);
            for (int i = 2; i < 6; i++){
                positions[i][j] = null;
            }
            positions[6][j] = new Pawn(PieceColor.BLACK, 6, j, this);
        }

        positions[7][0] = new Rook(PieceColor.BLACK, 7, 0, this);
        positions[7][1] = new Knight(PieceColor.BLACK, 7, 1, this);
        positions[7][2] = new Bishop(PieceColor.BLACK, 7, 2, this);
        positions[7][3] = new King(PieceColor.BLACK, 7, 3, this);
        positions[7][4] = new Queen(PieceColor.BLACK, 7, 4, this);
        positions[7][5] = new Bishop(PieceColor.BLACK, 7, 5, this);
        positions[7][6] = new Knight(PieceColor.BLACK, 7, 6, this);
        positions[7][7] = new Rook(PieceColor.BLACK, 7, 7, this);

        for(int i : new int[]{0, 1, 6, 7}){
            for(int j = 0; j < 8; j++){
                positions[i][j].calculatePermittedMoves();
            }
        }
        latestPlays = new LinkedList<>();
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
                throw new InvalidNotationException(input);
            }

            Piece movingPiece = positions[originRow][originCol];
            if(movingPiece != null && movingPiece.isMovePermitted(destinationRow, destinationCol)){

                Piece destination = positions[destinationRow][destinationCol];
                String notation;
                String captureNotation = "";
                String extraNotation = "";

                //regular capture
                if (destination != null) {
                    if(destination.getColor() == PieceColor.WHITE){
                        capturedFromWhite += destination.toString();
                    }
                    else {
                        capturedFromBlack += destination.toString();
                    }
                    captureNotation = "x";
                }
                //move to empty or castling or en passant
                else {
                    //en passant
                    if(movingPiece instanceof Pawn && ((Pawn) movingPiece).isMoveEnPassant(destinationRow, destinationCol)){
                        //removing enemy pawn after move
                        int direction = movingPiece.getColor() == PieceColor.WHITE ? -1 : 1;
                        Piece enPassantCapture = positions[destinationRow + direction][destinationCol];

                        if(enPassantCapture.getColor() == PieceColor.WHITE){
                            capturedFromWhite += enPassantCapture.toString();
                        }
                        else {
                            capturedFromBlack += enPassantCapture.toString();
                        }

                        positions[destinationRow + direction][destinationCol] = null;
                        extraNotation = " e.p.";
                    }
                    //castling
                    else if(movingPiece instanceof King && ((King) movingPiece).isMoveCastling(destinationRow, destinationCol)){
                        //moving rook from left to right of king after king moved
                        if(positions[destinationRow][destinationCol-1] instanceof Rook){
                            positions[destinationRow][destinationCol+1] = positions[destinationRow][destinationCol-1];
                            positions[destinationRow][destinationCol-1] = null;
                            extraNotation = " (O-O-O)";
                        }
                        //moving rook from right to left of king after king moved
                        else {
                            positions[destinationRow][destinationCol-1] = positions[destinationRow][destinationCol+1];
                            positions[destinationRow][destinationCol+1] = null;
                            extraNotation = " (O-O)";
                        }
                    }
                }

                notation = movingPiece.getNotationSymbol() + matcher.group(1) + matcher.group(2) + captureNotation +
                        matcher.group(3) + matcher.group(4);

                if(movingPiece instanceof Pawn && ((Pawn) movingPiece).isMovePromotion(destinationRow, destinationCol)){
                    movingPiece = new Queen(movingPiece.getColor(), destinationRow, destinationCol, this,
                            movingPiece.getMoveCount() + 1, moveCount);
                    extraNotation += "=" + movingPiece.getNotationSymbol();
                }
                else {
                    movingPiece.updatePosition(destinationRow, destinationCol, moveCount);
                }

                positions[destinationRow][destinationCol] = movingPiece;
                positions[originRow][originCol] = null;
                moveCount++;
                lastOriginRow = originRow;
                lastOriginCol = originCol;
                lastDestinationRow = destinationRow;
                lastDestinationCol = destinationCol;

                if(movingPiece.isCheckingKing){
                    extraNotation += "+";
                }

                if(destination instanceof King){
                    latestPlays.addFirst(new NotationEntry(notation + extraNotation + "#", movingPiece.getColor(), movingPiece.toString()));
                    if(latestPlays.size() > 8){
                        latestPlays.removeLast();
                    }
                    throw new CheckmateException(movingPiece.getColor().toString());
                }

                for(Piece[] row : positions){
                    for(Piece piece : row){
                        if(piece != null){
                            piece.calculatePermittedMoves();
                        }
                    }
                }

                latestPlays.addFirst(new NotationEntry(notation + extraNotation, movingPiece.getColor(), movingPiece.toString()));
                if(latestPlays.size() > 8){
                    latestPlays.removeLast();
                }
            }
            else {
                if(movingPiece == null){
                    throw new InvalidMoveException(input, "Square " + matcher.group(1) + matcher.group(2) + " is empty.");
                }
                else {
                    throw new InvalidMoveException(input, "Piece in " + matcher.group(1) + matcher.group(2) + " cannot move to "
                            + matcher.group(3) + matcher.group(4) + ".");
                }

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

        StringBuilder output = new StringBuilder("    a  b  c  d  e  f  g  h       Latest moves\n");
        for (int i = 7; i >= 0; i--){
            output.append(" ").append(i+1).append(" ");
            for(int j=0; j < 8; j++){
                //if row number + col number is even, square is light
                String tileColor;
                if((i == lastOriginRow && j == lastOriginCol) || (i == lastDestinationRow && j == lastDestinationCol)){
                    tileColor = ANSIColors.ANSI_YELLOW_BG;
                }
                else {
                    tileColor = (i+j+2) % 2 == 0 ? ANSIColors.ANSI_GREEN_BG : ANSIColors.ANSI_BROWN_BG;
                }

                if(positions[i][j] != null){
                    String pieceColor = positions[i][j].getColor() == PieceColor.WHITE ? ANSIColors.ANSI_WHITE : ANSIColors.ANSI_BLACK;
                    output.append(tileColor).append(" ").append(pieceColor).append(positions[i][j].toString()).append(" ").append(ANSIColors.ANSI_RESET);
                }
                else {
                    output.append(tileColor).append("   ").append(ANSIColors.ANSI_RESET);
                }

            }
            output.append(" ").append(i+1);
            if(latestPlays.size() > (7 - i)){
                output.append("    ").append(latestPlays.get(7 - i));
            }
            output.append("\n");
        }
        output.append("    a  b  c  d  e  f  g  h   \n\n");
        output.append(" ").append(ANSIColors.ANSI_GREEN_BG).append(ANSIColors.ANSI_WHITE).append("[").append(ANSIColors.ANSI_BLACK)
                .append(capturedFromBlack).append(ANSIColors.ANSI_WHITE).append("]").append(ANSIColors.ANSI_RESET);

        output.append(" ").append(ANSIColors.ANSI_BROWN_BG).append(ANSIColors.ANSI_BLACK).append("[").append(ANSIColors.ANSI_WHITE)
                .append(capturedFromWhite).append(ANSIColors.ANSI_BLACK).append("]").append(ANSIColors.ANSI_RESET).append("\n");

        return output.toString();
    }
}
