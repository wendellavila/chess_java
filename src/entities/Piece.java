package entities;

import entities.enums.Color;

public abstract class Piece {
    private char icon;
    private Color color;
    protected int currentRow, currentCol;
    protected boolean[][] permittedMoves;

    public Piece(Color color, int initialRow, int initialCol, char icon){
        this.color = color;
        this.currentRow = initialRow;
        this.currentCol = initialCol;
        this.icon = icon;
    }

    public void updatePosition(int row, int col){
        currentRow = row;
        currentCol = col;
    }

    public abstract void calculatePermittedMoves(Piece[][] positions);

    public boolean isMovePermitted(int row, int col){
        return permittedMoves[row][col];
    }

    public Color getColor(){
        return color;
    }

    @Override
    public String toString() {
        return String.valueOf(icon);
    }
}