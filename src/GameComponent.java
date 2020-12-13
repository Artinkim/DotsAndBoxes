import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameComponent extends JPanel implements MouseListener {
	boolean[][] board;
	int n;
	int m;
	float stepSize;
	int dotSize;

	GameComponent(int width, int height, boolean[][] board) {
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

	public void paintComponent(Graphics g) {
		stepSize = Math.min(this.getWidth() / (n + 2), this.getHeight() / (m + 2));
		dotSize = (int) (stepSize / 5);
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j]) {
					g.setColor(new Color(0, 0, 255));
				} else {
					g.setColor(new Color(22, 226, 77));
				}
				if (i % 2 == 0) { // Vertical
					g.fillRect((int) ((i / 2 + 1) * stepSize), (int) ((j + 1) * stepSize), dotSize,
							(int) stepSize + dotSize / 2);
				} else { // Horizontal
					g.fillRect((int) ((i / 2 + 1) * stepSize), (int) ((j + 1) * stepSize), (int) stepSize + dotSize / 2,
							dotSize);
				}
			}
		}
		g.setColor(new Color(0, 0, 255));
		for (int i = 1; i <= n + 1; i++) {
			for (int j = 1; j <= m + 1; j++) {
				g.fillRect((int) (i * stepSize), (int) (j * stepSize), dotSize, dotSize);
			}
		}
//		g.draw3DRect(a, b, 50, 20, false);
//		g.setColor(new Color(255, 0, 0));
//		g.drawString("Hee", 100, 100);
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
			if ((float) x % stepSize < dotSize) { // && y % stepSize > dotSize
				board[((int) xSteps - 1) * 2][(int) (ySteps - 1)] = true;
				System.out.println("worked1");
			} else if ((float) y % stepSize < dotSize) { // x % stepSize > dotSize &&
				board[(((int) xSteps - 1) * 2) + 1][(int) (ySteps - 1)] = true;
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
