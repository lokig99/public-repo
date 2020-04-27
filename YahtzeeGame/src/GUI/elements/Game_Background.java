package GUI.elements;

import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.textures.TextureLoader;

public class Game_Background extends JPanel {
	
	
	private static final long serialVersionUID = 6072649627681724170L;
	
	JFrame root;
	transient BufferedImage background;
	transient Image bgd_scaled;
	
	public Game_Background(JFrame root) {
		
			background = TextureLoader.background_game;

		this.root = root;
	
		loadTextures();
	}
	
	
	void loadTextures() {
		
		bgd_scaled = background.getScaledInstance(root.getWidth(), root.getHeight(), 0);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(bgd_scaled, null, this);
	}
}
