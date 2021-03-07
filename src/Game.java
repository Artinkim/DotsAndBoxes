import java.util.Arrays;
import javax.swing.JPanel;

public class Game extends JPanel {
	int[][] board;
	int n;
	int m;
	int[] playerScores;
	int whosTurn;
	int boxesLeft;
	int movesMade;

	Game(int n, int m, int numberOfPlayers) {
		this.n = n;
		this.m = m;
		boxesLeft = n * m;
		this.board = new int[(n * 2) + 1][];
		for (int i = 0; i < board.length; i++) {
			if (i % 2 == 0) {
				board[i] = new int[m];
			} else {
				board[i] = new int[m + 1];
			}
			Arrays.fill(board[i], -1);
		}
		playerScores = new int[numberOfPlayers];
		this.movesMade = 0;
	}

	public void decrementTurn() {
		whosTurn = Math.floorMod((whosTurn - 1), playerScores.length);
	}

	public void incrementTurn() {
		whosTurn = (whosTurn + 1) % playerScores.length;
	}

	public void undoMove(int i, int j, int boxesMade, int lastTurn) {
		// System.out.println(whosTurn+" "+playerScores.length);
		board[i][j] = -1;
		whosTurn = lastTurn;
		// System.out.println(whosTurn);
		playerScores[whosTurn] -= boxesMade;
		boxesLeft += boxesMade;
		movesMade--;
	}

	public int makeMove(int i, int j) {
		board[i][j] = whosTurn;
		movesMade++;
		int boxesMade = 0;
		if (i % 2 == 0) { // Vertical line
			if (i > 0 && board[i - 1][j] != -1 && board[i - 1][j + 1] != -1 && board[i - 2][j] != -1) { // Right
				boxesMade++;
				boxesLeft--;
			}
			if (i < board.length - 1 && board[i + 1][j] != -1 && board[i + 1][j + 1] != -1 && board[i + 2][j] != -1) { // Left
				boxesMade++;
				boxesLeft--;
			}

		} else { // Horizontal line
			if (j < board[1].length - 1 && board[i - 1][j] != -1 && board[i + 1][j] != -1 && board[i][j + 1] != -1) { // Top
				boxesMade++;
				boxesLeft--;
			}
			if (j > 0 && board[i - 1][j - 1] != -1 && board[i + 1][j - 1] != -1 && board[i][j - 1] != -1) { // Bottom
				boxesMade++;
				boxesLeft--;
			}
		}
		playerScores[whosTurn] += boxesMade;
		if (boxesMade > 0) {
			if (boxesLeft == 0) {
				System.out.println("Game over");
				int index = -1;
				int score = -1;
				for (int k = 0; k < playerScores.length; k++) {
					if (playerScores[k] > score) {
						score = playerScores[k];
						index = k;
					}
				}
				Arrays.fill(playerScores, -1);
				playerScores[index] = n * m + 1;
			}
		} else {
			incrementTurn();
		}
		return boxesMade;
	}

	public int[][] makeMove(int i, int j, int[][] filledInBoxes) {
		board[i][j] = whosTurn;
		movesMade++;
		int boxesMade = 0;
		if (i % 2 == 0) { // Vertical line
			if (i > 0 && board[i - 1][j] != -1 && board[i - 1][j + 1] != -1 && board[i - 2][j] != -1) { // Right
				filledInBoxes[((i - 1) / 2)][j] = whosTurn;
				boxesMade++;
				boxesLeft--;

			}
			if (i < board.length - 1 && board[i + 1][j] != -1 && board[i + 1][j + 1] != -1 && board[i + 2][j] != -1) { // Left
				filledInBoxes[(i / 2)][j] = whosTurn;
				playerScores[whosTurn]++;
				boxesMade++;
				boxesLeft--;
			}

		} else { // Horizontal line
			if (j < board[1].length - 1 && board[i - 1][j] != -1 && board[i + 1][j] != -1 && board[i][j + 1] != -1) { // Top
				filledInBoxes[(i / 2)][j] = whosTurn;
				playerScores[whosTurn]++;
				boxesMade++;
				boxesLeft--;
			}
			if (j > 0 && board[i - 1][j - 1] != -1 && board[i + 1][j - 1] != -1 && board[i][j - 1] != -1) { // Bottom
				filledInBoxes[(i / 2)][j - 1] = whosTurn;
				playerScores[whosTurn]++;
				boxesMade++;
				boxesLeft--;
			}
		}
		playerScores[whosTurn] += boxesMade;
		if (boxesMade > 0) {
			if (boxesLeft == 0) {
				System.out.println("Game over");
				int index = -1;
				int score = -1;
				for (int k = 0; k < playerScores.length; k++) {
					if (playerScores[k] > score) {
						score = playerScores[k];
						index = k;
					}
				}
				Arrays.fill(playerScores, -1);
				playerScores[index] = Integer.MAX_VALUE;
			}
		} else {
			incrementTurn();
		}
		return filledInBoxes;
	}

}