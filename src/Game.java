import Model.*;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Game {
    Deque<Player> players;
    Board gameBoard;

    Game(){
        initializeGame();
    }

    public void initializeGame(){
        players = new LinkedList<>();
        PlayingPieceX cross = new PlayingPieceX();
        Player player1 = new Player("Player 1",cross);

        PlayingPieceO circle = new PlayingPieceO();
        Player player2 = new Player("Player 2", circle);

        players.add(player1);
        players.add(player2);

        //init board
        gameBoard = new Board(3);
    }

    public String startGame(){
        boolean noWinner = true;
        while (noWinner){
            //take the playing player out from que
            Player playerTurn = players.removeFirst();

            //get free space from the board
            gameBoard.printBoard();
            List<Pair<Integer,Integer>> freeCells = gameBoard.getFreeCells();
            if(freeCells.isEmpty()){
                noWinner=false;
                continue;
            }

            //user i/p
            System.out.println("Player: " + playerTurn.name + "Enter row, column: ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            int inputRow = Integer.parseInt(values[0]);
            int inputColumn = Integer.parseInt(values[1]);

            //place on board
            boolean pieceAddedSuccessfully = gameBoard.addPiece(inputRow,inputColumn,playerTurn.playingPiece);
            if(!pieceAddedSuccessfully){
                //invalid move - retry
                System.out.println("Incorrect move - try again:");
                players.addFirst(playerTurn);
                continue;
            }
            players.addLast(playerTurn);
            boolean winner = isThereWinner(inputRow,inputColumn,playerTurn.playingPiece.pieceType);
            if(winner){
                return playerTurn.name;
            }
        }
        return "tie";
    }

    private boolean isThereWinner(int inputRow, int inputColumn, PieceType pieceType) {
        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        //need to check in row
        for(int i=0; i<gameBoard.size; i++){
            if (gameBoard.board[inputRow][i] == null || gameBoard.board[inputRow][i].pieceType != pieceType) {
                rowMatch = false;
                break;
            }
        }

        for(int i=0; i<gameBoard.size; i++){
            if (gameBoard.board[i][inputColumn] == null || gameBoard.board[i][inputColumn].pieceType != pieceType) {
                columnMatch = false;
                break;
            }
        }

        for(int i=0, j=0; i<gameBoard.size; i++, j++){
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                diagonalMatch = false;
                break;
            }
        }

        for(int i=0, j=gameBoard.size-1; i<gameBoard.size; i++, j--){
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                antiDiagonalMatch = false;
                break;
            }
        }

        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
    }
}
