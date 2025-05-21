import Model.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javafx.util.Pair;

public class TicTacToe {

    Deque<Player> players;
    Board board;

    public void initialize() {

        players = new LinkedList<>();
        PieceX xPiece = new PieceX();
        Player player1 = new Player("Player1", xPiece);

        PieceO oPiece = new PieceO();
        Player player2 = new Player("Player2", oPiece);

        players.add(player1);
        players.add(player2);

        board = new Board(3);

    }

    public String startGame() {
        boolean noWinner = true;

        while (noWinner) {
            Player playerTurn = players.removeFirst();

            board.printBoard();
            List<Pair<Integer, Integer>> freeSpace = board.getFreeCells();
            if (freeSpace.isEmpty()) {
                noWinner = true;
                continue;
            }

            System.out.println("Player : " + playerTurn.getName() + " .Enter row,column :");
            Scanner sc = new Scanner(System.in);
            String in = sc.nextLine();
            String[] input = in.split(",");
            int row = Integer.valueOf(input[0]);
            int col = Integer.valueOf(input[1]);

            boolean pieceAdded = board.addPiece(row, col, playerTurn.piece);
            if (!pieceAdded) {
                System.out.println("Incorredt possition chosen, try again");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);

            boolean winner = isThereWinner(row, col, playerTurn.getPiece().pieceType);
            if (winner) {
                return playerTurn.name;
            }
        }
        return "tie";
    }

    public boolean isThereWinner(int row, int column, PieceType pieceType) {

        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        //need to check in row
        for(int i=0;i<board.size;i++) {

            if(board.board[row][i] == null || board.board[row][i].pieceType != pieceType) {
                rowMatch = false;
            }
        }

        //need to check in column
        for(int i=0;i<board.size;i++) {

            if(board.board[i][column] == null || board.board[i][column].pieceType != pieceType) {
                columnMatch = false;
            }
        }

        //need to check diagonals
        for(int i=0, j=0; i<board.size;i++,j++) {
            if (board.board[i][j] == null || board.board[i][j].pieceType != pieceType) {
                diagonalMatch = false;
            }
        }

        //need to check anti-diagonals
        for(int i=0, j=board.size-1; i<board.size;i++,j--) {
            if (board.board[i][j] == null || board.board[i][j].pieceType != pieceType) {
                antiDiagonalMatch = false;
            }
        }

        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
    }

}
