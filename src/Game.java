import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements MouseListener {
	boolean[][] board;
	int[][] filledInBoxes;
	int n;
	int m;
	float stepSize;
	int dotSize;
	int[] players;
	int whosTurn;
	int boxesLeft;

	Game(int n, int m, int numberOfPlayers, int width, int height) {
		boxesLeft = n * m;
		filledInBoxes = new int[n][m];
		for (int[] i : filledInBoxes) {
			Arrays.fill(i, -1);
		}
		board = new boolean[(n * 2) + 1][];
		for (int i = 0; i < board.length; i++) {
			if (i % 2 == 0) {
				board[i] = new boolean[m];
			} else {
				board[i] = new boolean[m + 1];
			}
		}
		players = new int[numberOfPlayers];

		JFrame frame = new JFrame("DotsAndCrosses");
		frame.add(this);
		frame.addMouseListener(this);
		frame.setVisible(true);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		updateBoard(board);
		stepSize = Math.min(this.getWidth() / (n + 2), this.getHeight() / (m + 2));
		dotSize = (int) (stepSize / 5);
	}

	public void updateBoard(boolean[][] board) {
		this.board = board;
		n = board.length / 2;
		m = board[0].length;
		System.out.println(n + " " + m);
	}

	public void makeMove(int i, int j) {
		board[i][j] = true;
		boolean playerMadeBox = false;
		if (i % 2 == 0) { // Vertical line
			if (i > 0 && board[i - 1][j] && board[i - 1][j + 1] && board[i - 2][j]) { // Right
				filledInBoxes[((i - 1) / 2)][j] = whosTurn;
				boxesLeft--;
				players[whosTurn]++;
				playerMadeBox = true;
			}
			if (i < board.length - 1 && board[i + 1][j] && board[i + 1][j + 1] && board[i + 2][j]) { // Left
				filledInBoxes[(i / 2)][j] = whosTurn;
				players[whosTurn]++;
				boxesLeft--;
				playerMadeBox = true;
			}

		} else { // Horizontal line
			if (j < board[0].length - 1 && board[i - 1][j] && board[i + 1][j] && board[i][j + 1]) { // Top
				filledInBoxes[(i / 2)][j] = whosTurn;
				players[whosTurn]++;
				boxesLeft--;
				playerMadeBox = true;
			}
			if (j > 0 && board[i - 1][j - 1] && board[i + 1][j - 1] && board[i][j - 1]) { // Bottom
				filledInBoxes[(i / 2)][j - 1] = whosTurn;
				players[whosTurn]++;
				boxesLeft--;
				playerMadeBox = true;
			}
		}
		if (playerMadeBox) {
			if (boxesLeft == 0) {
				System.out.println("Game over");
				int index = -1;
				int score = -1;
				for (int k = 0; k < players.length; k++) {
					if (players[k] > score) {
						score = players[k];
						index = k;
					}
				}
				System.out.println("Winner is player: " + index + " with " + score + " points");
			}

		} else {
			whosTurn = (whosTurn + 1) % players.length;
		}
	}

	public void drawLine(int i, int j, Graphics g) {
		if (i % 2 == 0) { // Vertical
			g.fillRect((int) ((i / 2 + 1) * stepSize), (int) ((j + 1) * stepSize), dotSize,
					(int) stepSize + dotSize / 2);
		} else { // Horizontal
			g.fillRect((int) ((i / 2 + 1) * stepSize), (int) ((j + 1) * stepSize), (int) stepSize + dotSize / 2,
					dotSize);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		stepSize = Math.min(this.getWidth() / (n + 2), this.getHeight() / (m + 2));
		dotSize = (int) (stepSize / 5);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j]) {
					g.setColor(new Color(0, 0, 255));
				} else {
					g.setColor(new Color(22, 226, 77));
				}
				drawLine(i, j, g);
			}
		}
		g.setColor(new Color(0, 0, 255));
		for (int i = 1; i <= n + 1; i++) {
			for (int j = 1; j <= m + 1; j++) {
				g.fillRect((int) (i * stepSize), (int) (j * stepSize), dotSize, dotSize);
			}
		}
		g.setColor(new Color(27, 55, 9));
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (filledInBoxes[i][j] != -1)
					g.drawString(filledInBoxes[i][j] + "", (int) ((i + 1.5) * stepSize), (int) ((j + 1.5) * stepSize));
			}
		}
		g.setColor(new Color(255, 0, 0));
		g.drawString("Player:" + whosTurn, 100, 100);
//		g.draw3DRect(a, b, 50, 20, false);
//		g.setColor(new Color(255, 0, 0));

//		g.fillRect(this.getWidth() - 10, this.getHeight() - 10, 100, 100);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX() - 8;
		int y = e.getY() - 31;
		float xSteps = x / stepSize;
		float ySteps = y / stepSize;
		System.out.println(dotSize + " " + stepSize);
		System.out.println(x + " " + y + " " + xSteps + " " + ySteps);
		if (xSteps > 1 && ySteps > 1 && xSteps - 2 < n && ySteps - 2 < m) {
			System.out.println("ee");
			System.out.println((float) x % stepSize + " " + (float) y % stepSize);
			if ((float) x % stepSize < dotSize) {
				makeMove(((int) xSteps - 1) * 2, (int) (ySteps - 1));
				System.out.println("worked1");
			} else if ((float) y % stepSize < dotSize) {
				makeMove((((int) xSteps - 1) * 2) + 1, (int) (ySteps - 1));
				System.out.println("worked");
			}
		}
		this.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}