package entities;

import entities.enums.PieceColor;
import entities.utils.ANSIColors;
import entities.exceptions.CheckmateException;
import entities.exceptions.InvalidMoveException;
import entities.exceptions.InvalidNotationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board {

    private final Piece[][] boardGrid = new Piece[8][8];
    private int moveCount = 0;

    private String capturedFromWhite = "";
    private String capturedFromBlack = "";

    private final LinkedList<NotationEntry> latestPlays = new LinkedList<>();
    private final ArrayList<String> inputHistory = new ArrayList<>();

    //last move coordinates used in toString()
    private final Position lastPlayOrigin = new Position();
    private final Position lastPlayDestination = new Position();

    //regex used by movePieces
    private static final Pattern inputPattern = Pattern.compile("([a-zA-Z])(\\d)\\s?-?([a-zA-Z])(\\d)");

    public Board(){

        boardGrid[0][0] = new Rook(PieceColor.WHITE, new Position(0,0), this);
        boardGrid[0][1] = new Knight(PieceColor.WHITE, new Position(0,1), this);
        boardGrid[0][2] = new Bishop(PieceColor.WHITE, new Position(0,2), this);
        boardGrid[0][3] = new King(PieceColor.WHITE, new Position(0,3), this);
        boardGrid[0][4] = new Queen(PieceColor.WHITE, new Position(0,4), this);
        boardGrid[0][5] = new Bishop(PieceColor.WHITE, new Position(0,5), this);
        boardGrid[0][6] = new Knight(PieceColor.WHITE, new Position(0,6), this);
        boardGrid[0][7] = new Rook(PieceColor.WHITE, new Position(0,7), this);

        for(int j=0; j < 8; j++){
            boardGrid[1][j] = new Pawn(PieceColor.WHITE, new Position(1,j), this);
            for (int i = 2; i < 6; i++){
                boardGrid[i][j] = null;
            }
            boardGrid[6][j] = new Pawn(PieceColor.BLACK, new Position(6,j), this);
        }

        boardGrid[7][0] = new Rook(PieceColor.BLACK, new Position(7,0), this);
        boardGrid[7][1] = new Knight(PieceColor.BLACK, new Position(7,1), this);
        boardGrid[7][2] = new Bishop(PieceColor.BLACK, new Position(7,2), this);
        boardGrid[7][3] = new King(PieceColor.BLACK, new Position(7,3), this);
        boardGrid[7][4] = new Queen(PieceColor.BLACK, new Position(7,4), this);
        boardGrid[7][5] = new Bishop(PieceColor.BLACK, new Position(7,5), this);
        boardGrid[7][6] = new Knight(PieceColor.BLACK, new Position(7,6), this);
        boardGrid[7][7] = new Rook(PieceColor.BLACK, new Position(7,7), this);

        for(int i : new int[]{0, 1, 6, 7}){
            for(int j = 0; j < 8; j++){
                boardGrid[i][j].calculateValidMoves();
            }
        }
    }

    public Piece getPiece(int row, int col){
        return boardGrid[row][col];
    }

    public int getMoveCount(){
        return moveCount;
    }

    public ArrayList<String> getInputHistory(){
        return inputHistory;
    }

    public void movePieces(String input) throws InvalidNotationException, InvalidMoveException, CheckmateException {

        Matcher matcher = inputPattern.matcher(input);

        if(matcher.matches()) {
            Position origin = new Position();
            Position destination = new Position();

            origin.setRow(Integer.parseInt(matcher.group(2)) - 1);
            destination.setRow(Integer.parseInt(matcher.group(4)) - 1);
            //converting letters a-h to integers 0-7
            origin.setCol((int) matcher.group(1).toLowerCase().charAt(0) - (int)'a');
            destination.setCol((int) matcher.group(3).toLowerCase().charAt(0) - (int)'a');

            if(origin.getRow() < 0 || origin.getRow() > 7 || destination.getRow() < 0 || destination.getRow() > 7 ||
                    origin.getCol() < 0 || origin.getCol() > 7 || destination.getCol() < 0 || destination.getCol() > 7){
                throw new InvalidNotationException(input);
            }

            Piece movingPiece = boardGrid[origin.getRow()][origin.getCol()];
            if(movingPiece != null && movingPiece.isMoveValid(destination.getRow(), destination.getCol())){

                Piece pieceDestination = boardGrid[destination.getRow()][destination.getCol()];

                String notation;
                String captureNotation = "";
                String extraNotation = "";

                //regular capture
                if (pieceDestination != null) {
                    if(pieceDestination.getColor() == PieceColor.WHITE){
                        capturedFromWhite += pieceDestination.toString();
                    }
                    else {
                        capturedFromBlack += pieceDestination.toString();
                    }
                    captureNotation = "x";
                }
                //move to empty or castling or en passant
                else {
                    //en passant
                    if(movingPiece instanceof Pawn && ((Pawn) movingPiece).isMoveEnPassant(destination.getRow(), destination.getCol())){
                        //removing enemy pawn after move
                        int direction = movingPiece.getColor() == PieceColor.WHITE ? -1 : 1;
                        Piece enPassantTarget = boardGrid[destination.getRow() + direction][destination.getCol()];

                        if(enPassantTarget.getColor() == PieceColor.WHITE){
                            capturedFromWhite += enPassantTarget.toString();
                        }
                        else {
                            capturedFromBlack += enPassantTarget.toString();
                        }

                        boardGrid[destination.getRow() + direction][destination.getCol()] = null;
                        extraNotation = " e.p.";
                    }
                    //castling
                    else if(movingPiece instanceof King && ((King) movingPiece).isMoveCastling(destination.getRow(), destination.getCol())){
                        //moving rook from left to right of king after king moved
                        if(boardGrid[destination.getRow()][destination.getCol()-1] instanceof Rook){
                            boardGrid[destination.getRow()][destination.getCol()+1] = boardGrid[destination.getRow()][destination.getCol()-1];
                            boardGrid[destination.getRow()][destination.getCol()-1] = null;
                            extraNotation = " (O-O-O)";
                        }
                        //moving rook from right to left of king after king moved
                        else {
                            boardGrid[destination.getRow()][destination.getCol()-1] = boardGrid[destination.getRow()][destination.getCol()+1];
                            boardGrid[destination.getRow()][destination.getCol()+1] = null;
                            extraNotation = " (O-O)";
                        }
                    }
                }

                notation = movingPiece.getNotationSymbol() + matcher.group(1) + matcher.group(2) + captureNotation +
                        matcher.group(3) + matcher.group(4);

                if(movingPiece instanceof Pawn && ((Pawn) movingPiece).isMovePromotion(destination.getRow(), destination.getCol())){
                    movingPiece = new Queen(movingPiece.getColor(), destination.getRow(), destination.getCol(), this,
                            movingPiece.getMoveCount() + 1, moveCount);
                    extraNotation += "=" + movingPiece.getNotationSymbol();
                }
                else {
                    movingPiece.updatePosition(destination.getRow(), destination.getCol(), moveCount);
                }

                boardGrid[destination.getRow()][destination.getCol()] = movingPiece;
                boardGrid[origin.getRow()][origin.getCol()] = null;
                moveCount++;

                lastPlayOrigin.setPosition(origin.getRow(), origin.getCol());
                lastPlayDestination.setPosition(destination.getRow(), destination.getCol());
                inputHistory.add(input);

                if(movingPiece.isCheckingKing){
                    extraNotation += "+";
                }

                if(pieceDestination instanceof King){
                    latestPlays.addFirst(new NotationEntry(notation + extraNotation + "#", movingPiece.getColor(), movingPiece.toString()));
                    if(latestPlays.size() > 8){
                        latestPlays.removeLast();
                    }
                    throw new CheckmateException(movingPiece.getColor().toString());
                }

                for(Piece[] row : boardGrid){
                    for(Piece piece : row){
                        if(piece != null){
                            piece.calculateValidMoves();
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
                if((i == lastPlayOrigin.getRow() && j == lastPlayOrigin.getCol()) || (i == lastPlayDestination.getRow() && j == lastPlayDestination.getCol())){
                    tileColor = ANSIColors.ANSI_YELLOW_BG;
                }
                else {
                    tileColor = (i+j+2) % 2 == 0 ? ANSIColors.ANSI_GREEN_BG : ANSIColors.ANSI_BROWN_BG;
                }

                if(boardGrid[i][j] != null){
                    String pieceColor = boardGrid[i][j].getColor() == PieceColor.WHITE ? ANSIColors.ANSI_WHITE : ANSIColors.ANSI_BLACK;
                    output.append(tileColor).append(" ").append(pieceColor).append(boardGrid[i][j].toString()).append(" ").append(ANSIColors.ANSI_RESET);
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
