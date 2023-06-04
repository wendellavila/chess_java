package entities;

//import entities.enums.PieceColor;
import entities.exceptions.CheckmateException;
import entities.exceptions.InvalidMoveException;
import entities.exceptions.InvalidNotationException;

import java.util.Scanner;

public class Match {

    public static void playPVPMatch() {

        int turnCount = 1;
        Board board = new Board();
        System.out.println(board);
        try(Scanner sc = new Scanner(System.in)){
            while(true) {
                try {
                    String currentColor = board.getMoveCount() % 2 == 0 ? "Whites" : "Blacks";
                    System.out.println("Turn " + turnCount + " - " + currentColor + " play\nInput: ");
                    board.movePieces(sc.nextLine());
                    turnCount = board.getMoveCount() % 2 == 0 ? turnCount + 1 : turnCount;
                    System.out.println("\n\n" + board);
                }
                catch (InvalidNotationException | InvalidMoveException e){
                    System.out.println(e.getMessage());
                }
                catch (CheckmateException e){
                    System.out.println(e.getMessage());
                    break;
                }
            }
        }
    }

//    public static void replayMatch(List<String> inputHistory){
//
//    }

//    public static void playPVEMatch(PieceColor color){
//        //
//    }
}
