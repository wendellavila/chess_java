package entities;

import entities.enums.PieceColor;

public class Knight extends Piece {

    public Knight(PieceColor pieceColor, int initialRow, int initialCol, Board board){
        super(pieceColor, initialRow, initialCol, board, '♞');
    }

    public void calculatePermittedMoves(){
        //resetting status variables
        isCheckingKing = false;
        permittedMoves = new boolean[8][8];

        //navigating two row squares, one col square
        for(int i : new int[]{-2, 2}){
            for(int j : new int[]{-1, 1}){
                if(((currentRow + i) < 8) && ((currentRow + i) >= 0) && ((currentCol + j) < 8) && ((currentCol + j) >= 0)){
                    Piece piece = board.getPieceByPosition(currentRow + i, currentCol + j);
                    if(piece == null){
                        permittedMoves[currentRow + i][currentCol + j] = true;
                    }
                    else if(piece.getColor() != getColor()){
                        permittedMoves[currentRow + i][currentCol + j] = true;
                        if(piece instanceof King){
                            isCheckingKing = true;
                        }
                    }
                }
            }
        }
        //navigating two col squares, one row square
        for(int i : new int[]{-1, 1}){
            for(int j : new int[]{-2, 2}){
                if(((currentRow + i) < 8) && ((currentRow + i) >= 0) && ((currentCol + j) < 8) && ((currentCol + j) >= 0)){
                    Piece piece = board.getPieceByPosition(currentRow + i, currentCol + j);
                    if(piece == null){
                        permittedMoves[currentRow + i][currentCol + j] = true;
                    }
                    else if(piece.getColor() != getColor()){
                        permittedMoves[currentRow + i][currentCol + j] = true;
                        if(piece instanceof King){
                            isCheckingKing = true;
                        }
                    }
                }
            }
        }
    }
}
