import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PlayerView extends JPanel implements MouseListener {
	Game game;
	int[][] filledInBoxes;
	float stepSize;
	int dotSize;
	Color[] playerColors = { new Color(3, 0, 5), new Color(250, 0, 5), new Color(3, 0, 250) };

	PlayerView(Game game, int width, int height) {
		this.game = game;
		filledInBoxes = new int[game.n][game.m];
		for (int[] i : filledInBoxes) {
			Arrays.fill(i, -1);
		}
		JFrame frame = new JFrame("PlayerView");
		frame.add(this);
		frame.addMouseListener(this);
		frame.setVisible(true);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// System.out.println(game.n + " " + game.m);
		stepSize = Math.min(this.getWidth() / (game.n + 2), this.getHeight() / (game.m + 2));
		dotSize = (int) (stepSize / 5);
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
		// System.out.println(game.n);
		super.paintComponent(g);
		stepSize = Math.min(this.getWidth() / (game.n + 2), this.getHeight() / (game.m + 2));
		dotSize = (int) (stepSize / 5);
		for (int i = 0; i < game.board.length; i++) {
			for (int j = 0; j < game.board[i].length; j++) {
				g.setColor(playerColors[game.board[i][j] + 1]);
//				if (board[i][j] != -1) {
//					
//				} else {
//					g.setColor(new Color(22, 226, 77));
//				}
				drawLine(i, j, g);
			}
		}
		g.setColor(new Color(0, 0, 255));
		// System.out.println(game.n);
		for (int i = 1; i <= game.n + 1; i++) {
			for (int j = 1; j <= game.m + 1; j++) {
				g.fillRect((int) (i * stepSize), (int) (j * stepSize), dotSize, dotSize);
			}
		}
		g.setColor(new Color(27, 55, 9));
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		for (int i = 0; i < game.n; i++) {
			for (int j = 0; j < game.m; j++) {
				if (filledInBoxes[i][j] != -1)
					g.drawString(filledInBoxes[i][j] + "", (int) ((i + 1.5) * stepSize), (int) ((j + 1.5) * stepSize));
			}
		}
		g.setColor(playerColors[game.whosTurn + 1]);
		// System.out.println(game.whosTurn);
		g.drawString("Player:" + game.whosTurn, 100, 100);
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
		// System.out.println(dotSize + " " + stepSize);
		// System.out.println(x + " " + y + " " + xSteps + " " + ySteps);
		System.out.print("Player Made Move: ");
		if (xSteps > 1 && ySteps > 1 && xSteps - 2 < game.n && ySteps - 2 < game.m) {
			// System.out.println((float) x % stepSize + " " + (float) y % stepSize);
			if ((float) x % stepSize < dotSize) {
				System.out.println(((int) xSteps - 1) * 2 + " " + (int) (ySteps - 1));
				filledInBoxes = game.makeMove(((int) xSteps - 1) * 2, (int) (ySteps - 1), filledInBoxes);
				// System.out.println("worked1");
			} else if ((float) y % stepSize < dotSize) {
				System.out.println((((int) xSteps - 1) * 2) + 1 + " " + (int) (ySteps - 1));
				filledInBoxes = game.makeMove((((int) xSteps - 1) * 2) + 1, (int) (ySteps - 1), filledInBoxes);
				// System.out.println("worked");
			}
		}

		game.decrementTurn();
		this.repaint();
		game.incrementTurn();
		// System.out.println(game.whosTurn);
		if (game.whosTurn == 0)
			return;
		while (game.whosTurn == 1) {
			int[] minMaxMove = Algorithms.minMax(0, 4, game);
			// System.out.println("Aftert makin"+game.whosTurn);
			System.out.println("Ai made move: " + minMaxMove[0] + " " + minMaxMove[1]);
			filledInBoxes = game.makeMove(minMaxMove[0], minMaxMove[1], filledInBoxes);
			// System.out.println(game.whosTurn);
			game.decrementTurn();
			this.repaint();
			game.incrementTurn();
			// System.out.println("done");
		}

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