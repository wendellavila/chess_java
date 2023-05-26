package application;

import entities.Board;
import entities.exceptions.InvalidMoveException;
import entities.exceptions.InvalidNotationException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Board board = new Board();
        System.out.println(board.toString());

        Scanner sc = new Scanner(System.in);
        int turnCount = 1;

        while(true) {
            try {
                String currentColor = board.getMoveCount() % 2 == 0 ? "Whites" : "Blacks";
                System.out.print("Turn " + turnCount + " - " + currentColor + " play\nInput: ");
                board.movePieces(sc.nextLine());
                turnCount = board.getMoveCount() % 2 == 0 ? turnCount + 1 : turnCount;
                System.out.println("\n\n" + board.toString());
            } catch (InvalidNotationException e) {
                System.out.println(e.getMessage());
            } catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
            }
        }
        //sc.close();
    }
}
