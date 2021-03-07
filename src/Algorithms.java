import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Algorithms {

	static int[] minMax(int depth, int limit, Game game) {
		// System.out.println("start game move" + game.whosTurn);
		int[] max = new int[game.playerScores.length];
		Arrays.fill(max, game.n * game.m + 1);
		max[game.whosTurn] = -2;
		int x = -1;
		int y = -1;
		AtomicInteger exploredNodes = new AtomicInteger(0);
		HashMap<String, int[]> map = new HashMap<String, int[]>();
		for (int i = 0; i < game.board.length; i++) {
			for (int j = 0; j < game.board[i].length; j++) {
				if (game.board[i][j] == -1) {
					// System.out.println(i + " " + j);
					int lastTurn = game.whosTurn;
					int boxesMade = game.makeMove(i, j);
					int[] temp = minMaxDepth(depth + 1, limit, game, exploredNodes, map);
					game.undoMove(i, j, boxesMade, lastTurn);
					// System.out.println(temp[game.whosTurn]);
					// ystem.out.println(temp[game.whosTurn] + " " + max[game.whosTurn]);
					if (max2(temp, max, game.whosTurn)) {
						x = i;
						y = j;
					}
				}
			}
		}
		System.out.println("Map length" + map.size());
		System.out.println("Explored Nodes: " + exploredNodes);
		System.out.print("GUARANTEED SCORE: ");
		System.out.println(Arrays.toString(max));
		// System.out.println("fter game move" + game.whosTurn);
		return new int[] { x, y };
	}

	static boolean max(int[] temp, int[] max, int turn) {
		if (temp[turn] > max[turn]) {
			for (int i = 0; i < max.length; i++) {
				max[i] = temp[i];
			}
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
				for (int i = 0; i < max.length; i++) {
					max[i] = temp[i];
				}
				return true;
			}
		}
		return false;
	}

	static boolean max2(int[] temp, int[] max, int turn) {
		int sumTemp = 0;
		int sumMax = 0;
		for (int i = 0; i < max.length; i++) {
			if (i != turn) {
				sumTemp += temp[i];
				sumMax += max[i];
			}
		}
		//System.out.println(temp[turn] - sumTemp +" "+(max[turn] - sumMax));
		if (temp[turn] - sumTemp > max[turn] - sumMax) {
			for (int i = 0; i < max.length; i++) {
				max[i] = temp[i];
			}
			return true;
		}
		return false;
	}

	static String positionToString(Game game) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < game.board.length; i++) {
			for (int j = 0; j < game.board[i].length; j++) {
				s.append(game.board[i][j] == -1 ? 0 : 1);
			}
		}
		s.append(game.whosTurn);
//		System.out.println();
//		System.out.println(s.toString());
//		System.out.println();
		return s.toString();
	}

	private static int[] minMaxDepth(int depth, int limit, Game game, AtomicInteger exploredNodes,
			HashMap<String, int[]> map) {
		if (depth == limit || game.boxesLeft == 0) {
			exploredNodes.incrementAndGet();
			return game.playerScores.clone();
		}
		if (map.containsKey(positionToString(game))) {
			return map.get(positionToString(game));
		}
		int[] max = new int[game.playerScores.length];
		Arrays.fill(max, game.n * game.m + 1);
		max[game.whosTurn] = -2;
		for (int i = 0; i < game.board.length; i++) {
			for (int j = 0; j < game.board[i].length; j++) {
				if (game.board[i][j] == -1) {
					int lastTurn = game.whosTurn;
					int boxesMade = game.makeMove(i, j);
					int[] temp = minMaxDepth(depth + 1, limit, game, exploredNodes, map);

					game.undoMove(i, j, boxesMade, lastTurn);
					// System.out.println(temp[0] + " " + temp[1]);

					max2(temp, max, game.whosTurn);
				}
			}
		}
		map.put(positionToString(game), max);
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
