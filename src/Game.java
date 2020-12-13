
public class Game {
	boolean[][] board;
	int[] players;
	int whosTurn;
	int boxesLeft;

	Game(int n, int m, int numberOfPlayers) {
		boxesLeft = n * m;
		board = new boolean[(n * 2) + 1][];
		for (int i = 0; i < board.length; i++) {
			if (i % 2 == 0) {
				board[i] = new boolean[m];
			} else {
				board[i] = new boolean[m + 1];
			}
		}
		players = new int[numberOfPlayers];
	}

	public boolean[][] getBoard() {
		return this.board;
	}

	public void makeMove(int i, int j) {

		boolean playerMadeBox = false;
		if (i % 2 == 0) { // Vertical line
			if (i > 0 && board[i - 1][j] && board[i - 1][j + 1] && board[i - 2][j]) { // Right
				boxesLeft--;
				players[whosTurn]++;
				playerMadeBox = true;
			}
			if (i < board.length - 1 && board[i + 1][j] && board[i + 1][j + 1] && board[i + 2][j]) { // Left
				players[whosTurn]++;
				boxesLeft--;
				playerMadeBox = true;
			}

		} else { // Horizontal line
			if (j < board[0].length - 1 && board[i - 1][j] && board[i + 1][j] && board[i][j + 1]) { // Top
				players[whosTurn]++;
				boxesLeft--;
				playerMadeBox = true;
			}
			if (j > 0 && board[i - 1][j - 1] && board[i + 1][j - 1] && board[i][j - 1]) { // Bottom
				players[whosTurn]++;
				boxesLeft--;
				playerMadeBox = true;
			}
		}
		if (!playerMadeBox) {
			int winner = -1;
			for (int k = 0; k < players.length; k++) {

			}
			if (boxesLeft == 0) {
				System.out.println("Game over");
			}
			whosTurn = (whosTurn + 1) % players.length;
		}
	}

}
