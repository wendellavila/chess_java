package entities;

import entities.enums.PieceColor;

public class Bishop extends Piece {

    public Bishop(PieceColor color, Position position, Board board){
        super(color, position, board, '♝', "B");
    }

    public Bishop(PieceColor color, Position position, Board board, int moveCount, int lastMoved){
        super(color, position, board, '♝', "B", moveCount, lastMoved);
    }

    public void calculateValidMoves(){
        //resetting status variables
        resetMovesInfo();
        Position destination;

        //diagonals
        int[][] directions = {{1,1}, {1,-1}, {-1,1}, {-1, -1}};
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
