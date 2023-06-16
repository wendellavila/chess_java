package entities;

import entities.enums.PieceColor;

public class Knight extends Piece {

    public Knight(PieceColor color, Position position, Board board){
        super(color, position, board, '♞', "N");
    }

    public Knight(PieceColor color, Position position, Board board, int moveCount, int lastMoved){
        super(color, position, board, '♞', "N", moveCount, lastMoved);
    }

    public void calculateValidMoves(){
        //resetting status variables
        resetMovesInfo();
        Position destination;

        //navigating two row squares, one col square
        for(int i : new int[]{-2, 2}){
            for(int j : new int[]{-1, 1}){
                destination = new Position(position.getRow() + i, position.getCol() + j);
                if(destination.isValid()){
                    Piece piece = board.getPiece(destination);
                    if(piece == null){
                        setValidMove(destination);
                    }
                    else if(piece.getColor() != color){
                        setValidMove(destination);
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
                destination = new Position(position.getRow() + i, position.getCol() + j);
                if(destination.isValid()){
                    Piece piece = board.getPiece(destination);
                    if(piece == null){
                        setValidMove(destination);
                    }
                    else if(piece.getColor() != color){
                        setValidMove(destination);
                        if(piece instanceof King){
                            isCheckingKing = true;
                        }
                    }
                }
            }
        }
    }
}
