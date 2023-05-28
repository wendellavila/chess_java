package entities;

import entities.enums.PieceColor;

public abstract class Piece {

    private final char icon;
    private final String notationSymbol;
    protected final PieceColor pieceColor;
    protected int currentRow, currentCol;
    protected Board board;
    protected boolean[][] permittedMoves;
    protected int moveCount;
    protected int lastMoved;
    protected boolean isCheckingKing;

    public Piece(PieceColor pieceColor, int initialRow, int initialCol, Board board, char icon, String notationSymbol){
        this.pieceColor = pieceColor;
        this.currentRow = initialRow;
        this.currentCol = initialCol;
        this.icon = icon;
        this.board = board;
        this.notationSymbol = notationSymbol;
        moveCount = 0;
        lastMoved = 0;
    }

    public Piece(PieceColor pieceColor, int initialRow, int initialCol, Board board, char icon, String notationSymbol, int moveCount, int lastMoved){
        this.pieceColor = pieceColor;
        this.currentRow = initialRow;
        this.currentCol = initialCol;
        this.icon = icon;
        this.board = board;
        this.notationSymbol = notationSymbol;
        this.moveCount = moveCount;
        this.lastMoved = lastMoved;
    }

    public void updatePosition(int row, int col, int boardMoveCount){
        currentRow = row;
        currentCol = col;
        moveCount++;
        lastMoved = boardMoveCount;
    }

    public abstract void calculatePermittedMoves();

    public boolean isMovePermitted(int row, int col){
        return permittedMoves[row][col];
    }

    public PieceColor getColor(){
        return pieceColor;
    }

    public int getMoveCount(){
        return moveCount;
    }

    public int getLastMove(){
        return lastMoved;
    }

    public String getNotationSymbol(){
        return notationSymbol;
    }

    @Override
    public String toString() {
        return String.valueOf(icon);
    }
}
