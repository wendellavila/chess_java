package entities;

import entities.enums.Color;

public abstract class Piece {
    private char icon;
    private Color color;
    protected int currentRow, currentCol;
    protected boolean[][] permittedMoves;
    protected int moveCount = 0;
    protected int lastMoved = 0;
    protected Board board;
    protected boolean isCheckingKing;

    public Piece(Color color, int initialRow, int initialCol, Board board, char icon){
        this.color = color;
        this.currentRow = initialRow;
        this.currentCol = initialCol;
        this.icon = icon;
        this.board = board;
    }

    public void updatePosition(int row, int col){
        currentRow = row;
        currentCol = col;
    }

    public abstract void calculatePermittedMoves();

    public boolean isMovePermitted(int row, int col){
        return permittedMoves[row][col];
    }

    public Color getColor(){
        return color;
    }

    public int getMoveCount(){
        return moveCount;
    }

    @Override
    public String toString() {
        return String.valueOf(icon);
    }
}
