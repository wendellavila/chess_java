package entities;

import entities.enums.Color;

public abstract class Piece {
    private char icon;
    private Color color;
    private int currentRow;
    private int currentCol;

    public Piece(Color color, int initialRow, int initialCol){
        this.color = color;
        currentRow = initialRow;
        currentCol = initialCol;
    }

    public void updatePosition(int row, int col){
        currentRow = row;
        currentCol = col;
    }

    public void setIcon(char icon){
        this.icon = icon;
    }

    public Color getColor(){
        return color;
    }

    @Override
    public String toString() {
        return String.valueOf(icon);
    }
}
