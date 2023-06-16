package entities;

import entities.enums.PieceColor;

public class Pawn extends Piece {

    public Pawn(PieceColor color, Position position, Board board){
        super(color, position, board, '♟', "");
    }

    public boolean isMovePromotion(Position destination){
        if(isMoveValid(position)){
            if(color == PieceColor.WHITE && position.getRow() == 6 && destination.getRow() == 7){
                return true;
            }
            else if(color == PieceColor.BLACK && position.getRow() == 1 && destination.getRow() == 0){
                return true;
            }
        }
        return false;
    }

    public boolean isMoveEnPassant(Position destination){
        int direction = color == PieceColor.WHITE ? -1 : 1 ;
        Piece enPassantCapture = board.getPiece(new Position(destination.getRow() + direction, destination.getCol()));

        if(isMoveValid(destination) &&
                board.getPiece(destination) == null &&
                enPassantCapture instanceof Pawn &&
                enPassantCapture.getColor() != color &&
                enPassantCapture.getMoveCount() == 1 &&
                enPassantCapture.getLastMove() == board.getMoveCount()){
            return true;
        }
        return false;
    }

    public void calculateValidMoves(){
        //resetting status variables
        resetMovesInfo();
        Position destination;

        //two square move
        if(moveCount == 0){
            //direction is different for white and black
            int i = color == PieceColor.WHITE ? 2 : -2;
            destination = new Position(position.getRow() + i, position.getCol());
            if(board.getPiece(destination) == null){
                setValidMove(destination);
            }
        }
        //one square movement
        //direction is different for white and black
        int i = color == PieceColor.WHITE ? 1 : -1;
        destination = new Position(position.getRow() + i, position.getCol());
        if(destination.isValid() && board.getPiece(destination) == null){
            setValidMove(destination);
        }
        //regular capture
        if(new Position(position.getRow() + i, position.getCol()).isValid()){
            for(int j : new int[]{1, -1}){
                destination = new Position(position.getRow() + i, position.getCol() + j);
                if(destination.isValid()){
                    Piece target = board.getPiece(destination);
                    if(target != null && target.getColor() != color){
                        setValidMove(destination);
                        if(target instanceof King){
                            isCheckingKing = true;
                        }
                    }
                }
            }
        }
        //en passant
        if((color == PieceColor.WHITE && position.getRow() == 4) || (color == PieceColor.BLACK && position.getRow() == 3)){
            for(int j : new int[]{1, -1}){
                Position targetPosition = new Position(position.getRow(), position.getCol() + j);
                if(targetPosition.isValid()){
                    Piece piece = board.getPiece(targetPosition);
                    destination = new Position(position.getRow() + i, position.getCol() + j);
                    if(piece instanceof Pawn && piece.getColor() != color && piece.getMoveCount() == 1 &&
                            piece.getLastMove() == board.getMoveCount() &&
                            board.getPiece(destination) == null){
                        setValidMove(destination);
                    }
                }
            }
        }
    }
}
