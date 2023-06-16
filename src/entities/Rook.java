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
        resetMovesInfo();
        Position destination;

        int[][] directions = {{1,0}, {-1,0}, {0,1}, {0,-1}};
        for(int[] direction : directions){
            for(int i = 1; i < 8; i++){
                destination = new Position(position.getRow() + (i * direction[0]), position.getCol() + (i * direction[1]));
                if(destination.isValid()){
                    Piece piece = board.getPiece(destination);
                    if(piece == null){
                        setValidMove(destination);
                    }
                    else {
                        if(piece.getColor() != color){
                            setValidMove(destination);
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
