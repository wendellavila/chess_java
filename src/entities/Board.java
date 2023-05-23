package entities;

import entities.enums.Color;

public class Board {
    Piece[][] positions = new Piece[8][8];

    public static final String ANSI_GREEN_BG = "\u001B[48;5;107m";
    public static final String ANSI_BROWN_BG = "\u001B[48;5;95m";
    public static final String ANSI_BLACK = "\u001B[38;5;236m";
    public static final String ANSI_WHITE = "\u001B[38;5;229m";
    public static final String ANSI_RESET = "\u001B[0m";

    public Board(){

        positions[0][0] = new Rook(Color.WHITE, 0, 0);
        positions[0][1] = new Knight(Color.WHITE, 0, 1);
        positions[0][2] = new Bishop(Color.WHITE, 0, 2);
        positions[0][3] = new King(Color.WHITE, 0, 3);
        positions[0][4] = new Queen(Color.WHITE, 0, 4);
        positions[0][5] = new Bishop(Color.WHITE, 0, 5);
        positions[0][6] = new Knight(Color.WHITE, 0, 6);
        positions[0][7] = new Rook(Color.WHITE, 0, 7);

        for(int j=0; j < 8; j++){
            positions[1][j] = new Pawn(Color.WHITE, 1, j);
            for (int i = 2; i < 6; i++){
                positions[i][j] = null;
            }
            positions[6][j] = new Pawn(Color.BLACK, 6, j);
        }

        positions[7][0] = new Rook(Color.BLACK, 7, 0);
        positions[7][1] = new Knight(Color.BLACK, 7, 1);
        positions[7][2] = new Bishop(Color.BLACK, 7, 2);
        positions[7][3] = new King(Color.BLACK, 7, 3);
        positions[7][4] = new Queen(Color.BLACK, 7, 4);
        positions[7][5] = new Bishop(Color.BLACK, 7, 5);
        positions[7][6] = new Knight(Color.BLACK, 7, 6);
        positions[7][7] = new Rook(Color.BLACK, 7, 7);
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
