import java.util.Arrays;

public class Algorithms {

	static int[] minMax(int depth, int limit, Game game) {
		// System.out.println("start game move" + game.whosTurn);
		int[] max = new int[game.playerScores.length];
		Arrays.fill(max, -2);
		int x = -1;
		int y = -1;
		for (int i = 0; i < game.board.length; i++) {
			for (int j = 0; j < game.board[i].length; j++) {
				if (game.board[i][j] == -1) {
					// System.out.println(i + " " + j);
					int lastTurn = game.whosTurn;
					int boxesMade = game.makeMove(i, j);
					int[] temp = minMaxDepth(depth + 1, limit, game);
					game.undoMove(i, j, boxesMade, lastTurn);
					// System.out.println(temp[game.whosTurn]);
					System.out.println(temp[game.whosTurn] + " " + max[game.whosTurn]);
					if (temp[lastTurn] > max[lastTurn]) {
						max = temp;
						x = i;
						y = j;
					} else if (temp[lastTurn] == max[lastTurn]) {
						int sumTemp = 0;
						int sumMax = 0;
						for (int k = 0; k < max.length; k++) {
							if (k != lastTurn) {
								sumTemp += temp[k];
								sumMax += max[k];
							}
						}
						if (sumTemp < sumMax) {
							max = temp;
							x = i;
							y = j;
						}
					}
				}
			}
		}
		System.out.println("GUARANTEED SCORE" + max[game.whosTurn]);
		// System.out.println("fter game move" + game.whosTurn);
		return new int[] { x, y };
	}

	static boolean maxx(int[] temp, int[] max, int turn) {
		if (temp[turn] > max[turn]) {
			max = temp;
			return true;
		} else if (temp[turn] == max[turn]) {
			int sumTemp = 0;
			int sumMax = 0;
			for (int i = 0; i < max.length; i++) {
				if (i != turn) {
					sumTemp += temp[i];
					sumMax += max[i];
				}
			}
			if (sumTemp < sumMax) {
				max = temp;
				return true;
			}
		}
		return false;
	}

	private static int[] minMaxDepth(int depth, int limit, Game game) {
		if (depth == limit || game.boxesLeft == 0)
			return game.playerScores.clone();
		int[] max = new int[game.playerScores.length];
		Arrays.fill(max, -2);
		for (int i = 0; i < game.board.length; i++) {
			for (int j = 0; j < game.board[i].length; j++) {
				if (game.board[i][j] == -1) {
					int lastTurn = game.whosTurn;
					int boxesMade = game.makeMove(i, j);
					int[] temp = minMaxDepth(depth + 1, limit, game);

					game.undoMove(i, j, boxesMade, lastTurn);
					// System.out.println(temp[0] + " " + temp[1]);
					if (temp[lastTurn] > max[lastTurn]) {
						max = temp;
					} else if (temp[lastTurn] == max[lastTurn]) {
						int sumTemp = 0;
						int sumMax = 0;
						for (int k = 0; k < max.length; k++) {
							if (k != lastTurn) {
								sumTemp += temp[k];
								sumMax += max[k];
							}
						}
						if (sumTemp < sumMax) {
							max = temp;
						}
					}
				}
			}
		}
		return max;
	}

//	public static void makeMove(int i, int j, int[][] board, int[] playerScores, int whosTurn, int boxesLeft) {
//		board[i][j] = whosTurn;
//		boolean playerMadeBox = false;
//		if (i % 2 == 0) { // Vertical line
//			if (i > 0 && board[i - 1][j] != -1 && board[i - 1][j + 1] != -1 && board[i - 2][j] != -1) { // Right
//				boxesLeft--;
//				playerScores[whosTurn]++;
//				playerMadeBox = true;
//			}
//			if (i < board.length - 1 && board[i + 1][j] != -1 && board[i + 1][j + 1] != -1 && board[i + 2][j] != -1) { // Left
//				playerScores[whosTurn]++;
//				boxesLeft--;
//				playerMadeBox = true;
//			}
//
//		} else { // Horizontal line
//			if (j < board[0].length - 1 && board[i - 1][j] != -1 && board[i + 1][j] != -1 && board[i][j + 1] != -1) { // Top
//				playerScores[whosTurn]++;
//				boxesLeft--;
//				playerMadeBox = true;
//			}
//			if (j > 0 && board[i - 1][j - 1] != -1 && board[i + 1][j - 1] != -1 && board[i][j - 1] != -1) { // Bottom
//				playerScores[whosTurn]++;
//				boxesLeft--;
//				playerMadeBox = true;
//			}
//		}
//		if (playerMadeBox) {
//			if (boxesLeft == 0) {
//				System.out.println("Game over");
//				int index = -1;
//				int score = -1;
//				for (int k = 0; k < playerScores.length; k++) {
//					if (playerScores[k] > score) {
//						score = playerScores[k];
//						index = k;
//					}
//				}
//				Arrays.fill(playerScores, -1);
//				playerScores[index] = Integer.MAX_VALUE;
//			}
//		} else {
//			whosTurn = (whosTurn + 1) % playerScores.length;
//		}
//	}
}
