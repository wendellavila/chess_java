package entities;

public class Position {
    private int row, col;
    private String notation;

    public Position() {
        this.row = -1;
        this.col = -1;
        this.notation = null;
    }

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
        this.notation = indexToAlphabetic(col + 1) + (row + 1);
    }

    private String indexToAlphabetic(int i) {
        if(i > 0 && i < 27){
            return String.valueOf((char)(i + 'a' - 1));
        }
        return null;
    }

    public boolean isValid(){
        return this.row >= 0 && this.row <= 7 && this.col >= 0 && this.col <= 7;
    }

    public String getNotation(){
        return notation;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
        this.notation = indexToAlphabetic(this.col + 1) + (row + 1);
    }

    public void setCol(int col) {
        this.col = col;
        this.notation = indexToAlphabetic(col + 1) + (this.row + 1);
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
        this.notation = indexToAlphabetic(col + 1) + (row + 1);
    }
}
