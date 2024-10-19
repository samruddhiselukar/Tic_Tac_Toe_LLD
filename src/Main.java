public class Main {
    public static void main(String[] args) {
        System.out.println("Tic Tac Toe!");
        Game game = new Game();
        System.out.println("Game winner is: " + game.startGame());
    }
}