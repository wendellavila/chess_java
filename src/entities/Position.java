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

    public Position(String notation) {
        this.col = (int) notation.charAt(0) - (int) 'a';
        this.row = (int) notation.charAt(1) - 1;
        this.notation = notation;
    }

    private String indexToAlphabetic(int i) {
        if(i > 0 && i < 27){
            return String.valueOf((char)(i + 'a' - 1));
        }
        return null;
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
