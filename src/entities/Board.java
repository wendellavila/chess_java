package entities;

import entities.enums.Color;

public class Board {

    private static final String ANSI_GREEN_BG = "\u001B[48;5;107m";
    private static final String ANSI_BROWN_BG = "\u001B[48;5;95m";
    private static final String ANSI_BLACK = "\u001B[38;5;236m";
    private static final String ANSI_WHITE = "\u001B[38;5;229m";
    private static final String ANSI_RESET = "\u001B[0m";

    private int moveCount;
    private Piece[][] positions = new Piece[8][8];

    public Board(){

        positions[0][0] = new Rook(Color.WHITE, 0, 0, this);
        positions[0][1] = new Knight(Color.WHITE, 0, 1, this);
        positions[0][2] = new Bishop(Color.WHITE, 0, 2, this);
        positions[0][3] = new King(Color.WHITE, 0, 3, this);
        positions[0][4] = new Queen(Color.WHITE, 0, 4, this);
        positions[0][5] = new Bishop(Color.WHITE, 0, 5, this);
        positions[0][6] = new Knight(Color.WHITE, 0, 6, this);
        positions[0][7] = new Rook(Color.WHITE, 0, 7, this);

        for(int j=0; j < 8; j++){
            positions[1][j] = new Pawn(Color.WHITE, 1, j, this);
            for (int i = 2; i < 6; i++){
                positions[i][j] = null;
            }
            positions[6][j] = new Pawn(Color.BLACK, 6, j, this);
        }

        positions[7][0] = new Rook(Color.BLACK, 7, 0, this);
        positions[7][1] = new Knight(Color.BLACK, 7, 1, this);
        positions[7][2] = new Bishop(Color.BLACK, 7, 2, this);
        positions[7][3] = new King(Color.BLACK, 7, 3, this);
        positions[7][4] = new Queen(Color.BLACK, 7, 4, this);
        positions[7][5] = new Bishop(Color.BLACK, 7, 5, this);
        positions[7][6] = new Knight(Color.BLACK, 7, 6, this);
        positions[7][7] = new Rook(Color.BLACK, 7, 7, this);
    }

    public Piece getPieceByPosition(int i, int j){
        return positions[i][j];
    }

    public int getMoveCount(){
        return moveCount;
    }

    @Override
    public String toString() {

        StringBuilder output = new StringBuilder();
        for (int i = 7; i >= 0; i--){
            output.append(" ").append(i+1).append(" ");
            for(int j=0; j < 8; j++){
                //if row number + col number is even, square is light
                String tileColor = (i+j+2) % 2 == 0 ? ANSI_GREEN_BG : ANSI_BROWN_BG;
                if(positions[i][j] != null){
                    String pieceColor = positions[i][j].getColor() == Color.WHITE ? ANSI_WHITE : ANSI_BLACK;
                    output.append(tileColor).append(" ").append(pieceColor).append(positions[i][j].toString()).append(" ").append(ANSI_RESET);
                }
                else {
                    output.append(tileColor).append("   ").append(ANSI_RESET);
                }

            }
            output.append("\n");
        }
        output.append("    a  b  c  d  e  f  g  h");
        return output.toString();
    }
}
