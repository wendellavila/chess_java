package entities;

import entities.enums.PieceColor;

public abstract class Piece {
    private char icon;
    private PieceColor pieceColor;
    protected int currentRow, currentCol;
    protected boolean[][] permittedMoves;
    protected int moveCount = 0;
    protected int lastMoved = 0;
    protected Board board;
    protected boolean isCheckingKing;

    public Piece(PieceColor pieceColor, int initialRow, int initialCol, Board board, char icon){
        this.pieceColor = pieceColor;
        this.currentRow = initialRow;
        this.currentCol = initialCol;
        this.icon = icon;
        this.board = board;
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

    @Override
    public String toString() {
        return String.valueOf(icon);
    }
}
