package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import GameLogic.Saper;

public class GameScreen {

	private JFrame gameFrame;
	private JPanel gamePanel;
	private JPanel gamePanelBack;
	private JPanel board;
	private GridLayout boardLayout;
	private Field[][] boardFields;

	private Long startTime;
	private Long endTime;

	private GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	private final int SCREEN_WIDTH = gd.getDisplayMode().getWidth();
	private final int SCREEN_HEIGHT = gd.getDisplayMode().getHeight();

	public GameScreen() {

		gameFrame = new JFrame("Saper");
		gamePanel = new JPanel();
		gamePanelBack = new JPanel();
		startTime = 0L;
		endTime = 0L;
	}

	public void display() {

		inputBoardSize();

		updateBoard();

		Saper.prepareBoard();

		checkGameFrameLargerThanScreenResolution();

		initializeFields();

		prepareGamePanel();

		prepareGameFrame();

		startTime = System.currentTimeMillis();
	}

	private void checkGameFrameLargerThanScreenResolution() {

		int expectedFrameHeight = (Textures.FIELD_SIZE() * Saper.getBoardSizeX()) + Textures.FIELD_SIZE_ORIGINAL();
		int expectedFrameWidth = (Textures.FIELD_SIZE() * Saper.getBoardSizeY()) + Textures.FIELD_SIZE_ORIGINAL();

		if (expectedFrameHeight > SCREEN_HEIGHT || expectedFrameWidth > SCREEN_WIDTH) {

			Textures.reduce_field_scale_by(1);
			checkGameFrameLargerThanScreenResolution();
		}
	}

	private void inputBoardSize() {

		Scanner sc = new Scanner(System.in);

		System.out.print("Podaj rozmiar planszy <int>: ");
		int input = Integer.parseInt(sc.nextLine());

		if (input > 1)
			Saper.createNewBoard(input);

		sc.close();
	}

	private void updateBoard() {

		boardLayout = new GridLayout(Saper.getBoardSizeX(), Saper.getBoardSizeY());
		board = new JPanel(boardLayout);
		boardFields = new Field[Saper.getBoardSizeX()][Saper.getBoardSizeY()];
	}

	private void initializeFields() {

		for (int i = 0; i < boardFields.length; i++) {

			for (int j = 0; j < boardFields[0].length; j++) {

				Field field = new Field(i, j, this);

				boardFields[i][j] = field;
				Saper.Instance().addSaperListener(boardFields[i][j]);

				board.add(boardFields[i][j]);
			}
		}
	}

	private void prepareGamePanel() {

		gamePanel.setLayout(new BorderLayout());
		gamePanel.add(board, BorderLayout.CENTER);

		gamePanelBack.setBackground(Color.BLACK);
		gamePanelBack.add(gamePanel, BorderLayout.CENTER);
	}

	private void prepareGameFrame() {

		gameFrame.add(gamePanelBack);
		gameFrame.setBackground(null);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setVisible(true);
		gameFrame.setResizable(false);
		gameFrame.pack();
		gameFrame.setLocationRelativeTo(null);
	}

	public void checkGameStatus() {

		endTime = System.currentTimeMillis();
		if (Saper.isGameOver() || Saper.isGameWon()) {

			if (Saper.isGameOver())
				JOptionPane.showMessageDialog(gameFrame,
						"\tPORAZKA\nczas gry:  " + (endTime - startTime) / 1000 + " sek.");
			else
				JOptionPane.showMessageDialog(gameFrame,
						"\tZWYCIESTWO\nczas gry:  " + (endTime - startTime) / 1000 + " sek.");

			gameFrame.setVisible(false);
			gameFrame.dispose();
		}
	}
}
