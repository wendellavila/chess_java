package entities;

import entities.enums.PieceColor;
import entities.exceptions.GameOverException;
import entities.exceptions.InvalidMoveException;
import entities.exceptions.InvalidNotationException;
import entities.utils.ANSICodes;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

public class Board {

    private final Piece[][] boardGrid = new Piece[8][8];
    private int moveCount = 0;

    private String capturedFromWhite = "";
    private String capturedFromBlack = "";

    private boolean whiteInCheck = false;
    private boolean blackInCheck = false;

    private final LinkedList<NotationEntry> latestPlays = new LinkedList<>();
    private final ArrayList<String> inputHistory = new ArrayList<>();

    //last move coordinates used in toString()
    private final Position lastPlayOrigin = new Position();
    private final Position lastPlayDestination = new Position();

    private final Scanner sc;

    public Board(Scanner sc){

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
                if(boardGrid[i][j] != null){
                    boardGrid[i][j].calculateValidMoves();
                }

            }
        }
        this.sc = sc;
    }

    public void addToInputHistory(String input){
        inputHistory.add(input);
    }

    public void addToLatestPlays(NotationEntry notationEntry){
        latestPlays.addFirst(notationEntry);
        if(latestPlays.size() > 8){
            latestPlays.removeLast();
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

    public Piece getPromotionPiece(Piece movingPiece, Position destination, String notation){
        notation = notation.toUpperCase();
        switch(notation){
            case "Q" -> {
                return new Queen(movingPiece.getColor(), destination, this,
                        movingPiece.getMoveCount() + 1, moveCount);
            }
            case "R" -> {
                return new Rook(movingPiece.getColor(), destination, this,
                        movingPiece.getMoveCount() + 1, moveCount);
            }
            case "B" -> {
                return new Bishop(movingPiece.getColor(), destination, this,
                        movingPiece.getMoveCount() + 1, moveCount);
            }
            case "N" -> {
                return new Knight(movingPiece.getColor(), destination, this,
                        movingPiece.getMoveCount() + 1, moveCount);
            }
            default -> {
                return null;
            }
        }
    }

    public boolean isWhiteInCheck() {
        return whiteInCheck;
    }

    public boolean isBlackInCheck() {
        return blackInCheck;
    }

    public List<Piece> getPiecesByColor(PieceColor color){
        List<Piece> pieceList = new ArrayList<>();
        for(Piece[] row : boardGrid){
            for(Piece piece : row){
                if(piece != null && piece.getColor() == color){
                    pieceList.add(piece);
                }
            }
        }
        return pieceList;
    }

    public int getPieceCountByColor(PieceColor color){
        return getPiecesByColor(color).size();
    }

    public void movePieces(Position origin, Position destination, String promoteTo) throws InvalidNotationException, InvalidMoveException, GameOverException {

        Piece movingPiece = boardGrid[origin.getRow()][origin.getCol()];
        PieceColor currentColor = getMoveCount() % 2 == 0 ? PieceColor.WHITE : PieceColor.BLACK;

        if(movingPiece != null && movingPiece.isMoveValid(destination) && currentColor == movingPiece.getColor()){

            Piece pieceDestination = boardGrid[destination.getRow()][destination.getCol()];

            String notation;
            String captureNotation = "";
            String extraNotation = "";

            //regular capture
            if(pieceDestination != null){
                if(pieceDestination.getColor() == PieceColor.WHITE){
                    capturedFromWhite += pieceDestination.toString();
                }
                else {
                    capturedFromBlack += pieceDestination.toString();
                }
                captureNotation = "x";
            }
            //move to empty or en passant or castling
            else {
                //move to empty: no extra action needed
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

            notation = movingPiece.getNotationSymbol() + origin.getNotation() + captureNotation +
                    destination.getNotation();

            // pawn promotion
            if(movingPiece instanceof Pawn && ((Pawn) movingPiece).isMovePromotion(destination.getRow(), destination.getCol())){
                //trying to get from input capture
                if(promoteTo != null){
                    movingPiece = getPromotionPiece(movingPiece, destination, promoteTo);
                }
                //if not available, ask user
                else {
                    Piece promotionFromAnswer = null;

                    System.out.println("Pawn promotion: Choose between (Q)ueen, (R)ook, (B)ishop, K(N)ight.");
                    while(promotionFromAnswer == null){
                        System.out.print("Promote to (Q/R/B/N): ");
                        try {
                            promotionFromAnswer = getPromotionPiece(movingPiece, destination, sc.nextLine().substring(0,1));

                            if(promotionFromAnswer == null){
                                throw new InputMismatchException("Invalid input.");
                            }
                            else {
                                movingPiece = promotionFromAnswer;
                                promoteTo = movingPiece.getNotationSymbol();
                            }
                        }
                        catch (InputMismatchException e){
                            System.out.println(e.getMessage());
                        }
                    }
                }
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

            if(promoteTo == null){
                addToInputHistory(origin.getNotation() + " " + destination.getNotation());
            }
            else {
                addToInputHistory(origin.getNotation() + " " + destination.getNotation() + " " + promoteTo);
            }

            if(pieceDestination instanceof King){
                extraNotation += pieceDestination.getColor() == PieceColor.WHITE ? "# (0-1)" : "# (1-0)";

                addToLatestPlays(new NotationEntry(notation + extraNotation, movingPiece.getColor(), movingPiece.toString()));
                throw new GameOverException("Checkmate! - " + movingPiece.getColor() + " wins!");
            }

            whiteInCheck = false;
            blackInCheck = false;
            for(Piece[] row : boardGrid){
                for(Piece piece : row){
                    if(piece != null){
                        piece.calculateValidMoves();
                        if(piece.isCheckingKing){
                            if(piece.color == PieceColor.WHITE){
                                blackInCheck = true;
                            }
                            else {
                                whiteInCheck = true;
                            }
                        }
                    }
                }
            }

            if(movingPiece.isCheckingKing){
                notation += "+";
            }

            addToLatestPlays(new NotationEntry(notation + extraNotation, movingPiece.getColor(), movingPiece.toString()));
        }
        else {
            String input = origin.getNotation() + " " + destination.getNotation();
            if(movingPiece == null){
                throw new InvalidMoveException(input, "Square " + origin.getNotation() + " is empty.");
            }
            else if(currentColor != movingPiece.getColor()){
                throw new InvalidMoveException(input, "Piece in " + origin.getNotation() + " is not yours.");
            }
            else {
                throw new InvalidMoveException(input, "Piece in " + origin.getNotation() + " cannot move to "
                        + destination.getNotation() + ".");
            }
        }
    }

    public String highlightPiece(Position position){
        Piece piece = boardGrid[position.getRow()][position.getCol()];
        if(piece != null && piece.hasValidMoves()){

            char[] temp = capturedFromBlack.toCharArray();
            Arrays.sort(temp);
            capturedFromBlack = new String(temp);
            temp = capturedFromWhite.toCharArray();
            Arrays.sort(temp);
            capturedFromWhite = new String(temp);

            // clearing terminal
            // https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
            System.out.print(ANSICodes.ANSI_CLEAR);
            System.out.flush();

            StringBuilder output = new StringBuilder("\n    a  b  c  d  e  f  g  h       Latest moves\n");
            for (int i = 7; i >= 0; i--){
                output.append(" ").append(i+1).append(" ");
                for(int j=0; j < 8; j++){

                    String tileColor;
                    if(piece.isMoveValid(new Position(i, j))){
                        tileColor = (i+j+2) % 2 == 0 ? ANSICodes.ANSI_LIGHT_BLUE_BG : ANSICodes.ANSI_BLUE_BG;
                    }
                    else {
                        //if row number + col number is even, square is light
                        if((i == lastPlayOrigin.getRow() && j == lastPlayOrigin.getCol()) || (i == lastPlayDestination.getRow() && j == lastPlayDestination.getCol())){
                            tileColor = ANSICodes.ANSI_YELLOW_BG;
                        }
                        else {
                            tileColor = (i+j+2) % 2 == 0 ? ANSICodes.ANSI_GREEN_BG : ANSICodes.ANSI_BROWN_BG;
                        }
                    }

                    if(boardGrid[i][j] != null){
                        String pieceColor = boardGrid[i][j].getColor() == PieceColor.WHITE ? ANSICodes.ANSI_WHITE : ANSICodes.ANSI_BLACK;
                        output.append(tileColor).append(" ").append(pieceColor).append(boardGrid[i][j].toString()).append(" ").append(ANSICodes.ANSI_RESET);
                    }
                    else {
                        output.append(tileColor).append("   ").append(ANSICodes.ANSI_RESET);
                    }

                }
                output.append(" ").append(i+1);
                if(latestPlays.size() > (7 - i)){
                    output.append("    ").append(latestPlays.get(7 - i));
                }
                output.append("\n");
            }
            output.append("    a  b  c  d  e  f  g  h   \n\n");
            output.append(" ").append(ANSICodes.ANSI_GREEN_BG).append(ANSICodes.ANSI_WHITE).append("[").append(ANSICodes.ANSI_BLACK)
                    .append(capturedFromBlack).append(ANSICodes.ANSI_WHITE).append("]").append(ANSICodes.ANSI_RESET);

            output.append(" ").append(ANSICodes.ANSI_BROWN_BG).append(ANSICodes.ANSI_BLACK).append("[").append(ANSICodes.ANSI_WHITE)
                    .append(capturedFromWhite).append(ANSICodes.ANSI_BLACK).append("]").append(ANSICodes.ANSI_RESET).append("\n");

            return output.toString();
        }
        else if(piece == null){
            return ANSICodes.ANSI_RED + "tip " + position.getNotation() + ": Square "+ position.getNotation()
                    +" is empty." + ANSICodes.ANSI_RESET;
        }
        else {
            return ANSICodes.ANSI_RED + "tip " + position.getNotation() + ": Piece in "+ position.getNotation()
                    +" has no available moves." + ANSICodes.ANSI_RESET;
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

        // clearing terminal
        // https://stackoverflow.com/questions/2979383/how-to-clear-the-console-using-java
        System.out.print(ANSICodes.ANSI_CLEAR);
        System.out.flush();

        StringBuilder output = new StringBuilder("\n    a  b  c  d  e  f  g  h       Latest moves\n");
        for (int i = 7; i >= 0; i--){
            output.append(" ").append(i+1).append(" ");
            for(int j=0; j < 8; j++){

                String tileColor;
                //if row number + col number is even, square is light
                if((i == lastPlayOrigin.getRow() && j == lastPlayOrigin.getCol()) || (i == lastPlayDestination.getRow() && j == lastPlayDestination.getCol())){
                    tileColor = ANSICodes.ANSI_YELLOW_BG;
                }
                else {
                    tileColor = (i+j+2) % 2 == 0 ? ANSICodes.ANSI_GREEN_BG : ANSICodes.ANSI_BROWN_BG;
                }

                if(boardGrid[i][j] != null){
                    String pieceColor = boardGrid[i][j].getColor() == PieceColor.WHITE ? ANSICodes.ANSI_WHITE : ANSICodes.ANSI_BLACK;
                    output.append(tileColor).append(" ").append(pieceColor).append(boardGrid[i][j].toString()).append(" ").append(ANSICodes.ANSI_RESET);
                }
                else {
                    output.append(tileColor).append("   ").append(ANSICodes.ANSI_RESET);
                }

            }
            output.append(" ").append(i+1);
            if(latestPlays.size() > (7 - i)){
                output.append("    ").append(latestPlays.get(7 - i));
            }
            output.append("\n");
        }
        output.append("    a  b  c  d  e  f  g  h   \n\n");
        output.append(" ").append(ANSICodes.ANSI_GREEN_BG).append(ANSICodes.ANSI_WHITE).append("[").append(ANSICodes.ANSI_BLACK)
                .append(capturedFromBlack).append(ANSICodes.ANSI_WHITE).append("]").append(ANSICodes.ANSI_RESET);

        output.append(" ").append(ANSICodes.ANSI_BROWN_BG).append(ANSICodes.ANSI_BLACK).append("[").append(ANSICodes.ANSI_WHITE)
                .append(capturedFromWhite).append(ANSICodes.ANSI_BLACK).append("]").append(ANSICodes.ANSI_RESET).append("\n");

        return output.toString();
    }
}