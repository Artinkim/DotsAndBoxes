
public class Runner {
	public static void main(String[] args) {
		Game game = new Game(5, 9, 2);
		GameComponent g = new GameComponent(500, 500, game.getBoard());
		g.board[0][0] = true;

	}
}
