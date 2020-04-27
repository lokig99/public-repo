package GUI.elements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Scoreboard extends JPanel {
	

	private static final long serialVersionUID = -7050721011063260524L;

	JFrame root;
	private float opacity;
	
	transient BufferedImage background;
	transient Image bgd_scaled;
	
	public Scoreboard() {
		
		this.setOpacity(0.8f);
		
		try {
			background = ImageIO.read(new File("textures/game_background.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.BLACK);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
	}




	public float getOpacity() {
		return opacity;
	}




	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
}
	


