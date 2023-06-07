package entities;

import entities.enums.PieceColor;

public class Knight extends Piece {

    public Knight(PieceColor color, Position position, Board board){
        super(color, position, board, '♞', "N");
    }

    public Knight(PieceColor color, int row, int col, Board board, int moveCount, int lastMoved){
        super(color, row, col, board, '♞', "N", moveCount, lastMoved);
    }

    public void calculateValidMoves(){
        //resetting status variables
        isCheckingKing = false;
        validMoves = new boolean[8][8];

        //navigating two row squares, one col square
        for(int i : new int[]{-2, 2}){
            for(int j : new int[]{-1, 1}){
                if(((position.getRow() + i) < 8) && ((position.getRow() + i) >= 0) && ((position.getCol() + j) < 8) && ((position.getCol() + j) >= 0)){
                    Piece piece = board.getPiece(position.getRow() + i, position.getCol() + j);
                    if(piece == null){
                        validMoves[position.getRow() + i][position.getCol() + j] = true;
                    }
                    else if(piece.getColor() != color){
                        validMoves[position.getRow() + i][position.getCol() + j] = true;
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
                if(((position.getRow() + i) < 8) && ((position.getRow() + i) >= 0) && ((position.getCol() + j) < 8) && ((position.getCol() + j) >= 0)){
                    Piece piece = board.getPiece(position.getRow() + i, position.getCol() + j);
                    if(piece == null){
                        validMoves[position.getRow() + i][position.getCol() + j] = true;
                    }
                    else if(piece.getColor() != color){
                        validMoves[position.getRow() + i][position.getCol() + j] = true;
                        if(piece instanceof King){
                            isCheckingKing = true;
                        }
                    }
                }
            }
        }
    }
}
