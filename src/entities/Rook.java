package entities;

import entities.enums.PieceColor;

public class Rook extends Piece {

    public Rook(PieceColor color, Position position, Board board){
        super(color, position, board, '♜', "R");
    }

    public Rook(PieceColor color, Position position, Board board, int moveCount, int lastMoved){
        super(color, position, board, '♜', "R", moveCount, lastMoved);
    }

    public void calculateValidMoves(){
        //resetting status variables
        isCheckingKing = false;
        validMoves = new boolean[8][8];

        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        for(int[] direction : directions){
            for(int i = 1; i < 8; i++){
                if(new Position(position.getRow() + (i * direction[0]), position.getCol() + (i * direction[1])).isValid()){
                    Piece piece = board.getPiece(position.getRow() + (i * direction[0]), position.getCol() + (i * direction[1]));
                    if(piece == null){
                        validMoves[position.getRow() + (i * direction[0])][position.getCol() + (i * direction[1])] = true;
                    }
                    else {
                        if(piece.getColor() != color){
                            validMoves[position.getRow() + (i * direction[0])][position.getCol() + (i * direction[1])] = true;
                            if(piece instanceof King){
                                isCheckingKing = true;
                            }
                        }
                        break;
                    }
                }
                else {
                    break;
                }
            }
        }
    }
}
