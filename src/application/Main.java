package application;

import entities.Board;
import entities.exceptions.CheckmateException;
import entities.exceptions.InvalidMoveException;
import entities.exceptions.InvalidNotationException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Board board = new Board();
        Scanner sc = new Scanner(System.in);
        int turnCount = 1;

        System.out.println(board);
        String currentColor = board.getMoveCount() % 2 == 0 ? "Whites" : "Blacks";
        System.out.println("Turn " + turnCount + " - " + currentColor + " play\nInput: ");

        while(true) {
            try {
                board.movePieces(sc.nextLine());
                turnCount = board.getMoveCount() % 2 == 0 ? turnCount + 1 : turnCount;
                System.out.println("\n\n" + board);
                currentColor = board.getMoveCount() % 2 == 0 ? "Whites" : "Blacks";
                System.out.println("Turn " + turnCount + " - " + currentColor + " play\nInput: ");
            }
            catch (InvalidNotationException | InvalidMoveException e){
                System.out.println(e.getMessage());
            }
            catch (CheckmateException e){
                System.out.println(e.getMessage());
                break;
            }
        }
        sc.close();
    }
}
