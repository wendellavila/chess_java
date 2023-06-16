package entities;

import entities.enums.PieceColor;
import entities.exceptions.GameOverException;

import java.util.List;

public abstract class Piece {

    private final char icon;
    private final String notationSymbol;
    protected final PieceColor color;
    protected Position position;
    protected Board board;
    protected boolean[][] validMoves = new boolean[8][8];
    protected int moveCount;
    protected int lastMoved;
    protected boolean isCheckingKing = false;
    protected boolean hasValidMoves = false;

    public Piece(PieceColor color, Position position, Board board, char icon, String notationSymbol){
        this.color = color;
        this.position = position;
        this.icon = icon;
        this.board = board;
        this.notationSymbol = notationSymbol;
        this.moveCount = 0;
        this.lastMoved = 0;
    }

    public Piece(PieceColor color, Position position, Board board, char icon, String notationSymbol, int moveCount, int lastMoved){
        this.color = color;
        this.position = position;
        this.icon = icon;
        this.board = board;
        this.notationSymbol = notationSymbol;
        this.moveCount = moveCount;
        this.lastMoved = lastMoved;
    }

    public void updatePosition(int row, int col, int boardMoveCount){
        position.setPosition(row, col);
        moveCount++;
        lastMoved = boardMoveCount;
    }

    public void setValidMove(Position position){
        validMoves[position.getRow()][position.getCol()] = true;
        hasValidMoves = true;
    }

    public void resetMovesInfo(){
        isCheckingKing = false;
        hasValidMoves = false;
        validMoves = new boolean[8][8];
    }

    public abstract void calculateValidMoves() throws GameOverException;

    public boolean isMoveValid(Position position){
        return validMoves[position.getRow()][position.getCol()];
    }

    public boolean hasValidMoves() throws GameOverException {
        return hasValidMoves;
    }

    public PieceColor getColor(){
        return color;
    }

    public int getMoveCount(){
        return moveCount;
    }

    public int getLastMove(){
        return lastMoved;
    }

    public String getNotationSymbol(){
        return notationSymbol;
    }

    @Override
    public String toString() {
        return String.valueOf(icon);
    }
}
