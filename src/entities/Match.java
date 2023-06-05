package entities;

//import entities.enums.PieceColor;
import entities.exceptions.CheckmateException;
import entities.exceptions.InvalidMoveException;
import entities.exceptions.InvalidNotationException;

import java.util.ArrayList;
import java.util.Scanner;

public class Match {

    public static ArrayList<String> playPVPMatch(Scanner sc) {

        int turnCount = 1;
        Board board = new Board();
        System.out.println(board);
        while(true){
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
        return board.getInputHistory();
    }

    public static void replayMatch(ArrayList<String> inputHistory){
        Board board = new Board();
        System.out.println(board);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(String input : inputHistory){
            try {
                board.movePieces(input);
                System.out.println(board);
                Thread.sleep(1500);
            }
            catch (InvalidNotationException | InvalidMoveException e){
                System.out.println(e.getMessage());
            }
            catch (CheckmateException e){
                System.out.println(e.getMessage());
                break;
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

//    public static void playPVEMatch(PieceColor color){
//        //
//    }
}
