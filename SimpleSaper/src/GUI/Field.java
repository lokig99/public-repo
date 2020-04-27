package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import GameLogic.Saper;
import GameLogic.SaperCaller;
import GameLogic.SaperListener;

@SuppressWarnings("serial")
public class Field extends JPanel implements ActionListener, SaperListener {

	private GameScreen gameScreen;

	private JLabel label;
	private JButton fieldButton;

	private boolean isRevealed;
	private boolean isFlagged;
	private boolean isBomb;

	private int value;
	private int cordX;
	private int cordY;
	private int bomb_FontSize;
	private int number_FontSize;

	public Field(int cordX, int cordY, GameScreen gameScreen) {

		this.cordX = cordX;
		this.cordY = cordY;
		this.isBomb = Saper.isBomb(cordX, cordY);
		this.value = Saper.getValue(cordX, cordY);
		this.gameScreen = gameScreen;
		this.bomb_FontSize = (int) (Textures.FIELD_SIZE() * 0.65f);
		this.number_FontSize = (int) (Textures.FIELD_SIZE() * 0.5f);

		createLabel();
		this.add(label);

		initializeButton();
		this.add(getFieldButton());

		this.setPreferredSize(new Dimension(Textures.FIELD_SIZE(), Textures.FIELD_SIZE()));
		this.isRevealed = false;
		this.isFlagged = false;
	}

	private void initializeButton() {

		setFieldButton(new JButton());
		getFieldButton().setPreferredSize(new Dimension(Textures.FIELD_SIZE(), Textures.FIELD_SIZE()));
		getFieldButton().setOpaque(false);
		getFieldButton().setContentAreaFilled(false);
		getFieldButton().setBorderPainted(false);
		getFieldButton().addActionListener(this);
		getFieldButton().setModel(new FixedStateButtonModel());
		getFieldButton().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON3) {

					Saper.flagField(getCordX(), getCordY());
					setFlagged(!isFlagged);
					repaint();
					gameScreen.checkGameStatus();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
		});
	}

	private void createLabel() {

		if (isBomb) {

			this.label = new JLabel("B");
			this.label.setForeground(Color.RED);
			this.label.setFont(new Font("DialogInput", Font.BOLD, bomb_FontSize));
		} else {

			this.label = new JLabel(Integer.toString(value));

			switch (this.value) {

			case 1:
				label.setForeground(Color.BLUE);
				break;
			case 2:
				label.setForeground(Color.GREEN);
				break;
			case 3:
				label.setForeground(Color.CYAN);
				break;
			case 4:
				label.setForeground(Color.ORANGE);
				break;
			default:
				label.setForeground(Color.MAGENTA);
			}

			this.label.setFont(new Font("DialogInput", Font.BOLD, number_FontSize));
		}

		this.label.setVisible(false);
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		if (isRevealed)
			g2d.drawImage(Textures.FIELD_REVEALED(), 0, 0, Textures.FIELD_SIZE(), Textures.FIELD_SIZE(), this);
		else {
			g2d.drawImage(Textures.FIELD_HIDDEN(), 0, 0, Textures.FIELD_SIZE(), Textures.FIELD_SIZE(), this);

			if (isFlagged)
				g2d.drawImage(Textures.FLAG(), 0, 0, Textures.FIELD_SIZE(), Textures.FIELD_SIZE(), this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (this.fieldButton.equals((JButton) e.getSource())) {

			if (Saper.revealField(getCordX(), getCordY()))
				revealField();

			gameScreen.checkGameStatus();
		}
	}

	@Override
	public void saperUpdateReceived(int eventNumber) {

		if (eventNumber == SaperCaller.REVEAL_FIELD)
			revealField();
	}

	private void revealField() {

		setRevealed(true);
		this.fieldButton.setEnabled(false);
		repaint();
	}

	// Getters & Setters________________________________________

	public boolean isFlagged() {
		return isFlagged;
	}

	public void setFlagged(boolean isFlagged) {

		this.isFlagged = isFlagged;
	}

	public JButton getFieldButton() {
		return fieldButton;
	}

	public void setFieldButton(JButton fieldButton) {
		this.fieldButton = fieldButton;
	}

	public void setRevealed(boolean val) {

		if (this.value != 0 || isBomb)
			this.label.setVisible(val);
		this.isRevealed = val;
	}

	public boolean isRevealed() {

		return this.isRevealed;
	}

	@Override
	public int getCordX() {
		return cordX;
	}

	@Override
	public int getCordY() {
		return cordY;
	}
}

/*
 * Nowy model przycisku z nadpisanymi odpowiednimi metodami, żeby uniknąć
 * niepotrzebnego wywoływania metody paintComponent() za każdym razem gdy
 * najedzie się kursorem na klawisz lub kiedy już jest wciśnięty
 */
@SuppressWarnings("serial")
class FixedStateButtonModel extends DefaultButtonModel {

	@Override
	public boolean isPressed() {
		return false;
	}

	@Override
	public boolean isRollover() {
		return false;
	}

	@Override
	public void setRollover(boolean b) {
		// NOOP
	}
}
