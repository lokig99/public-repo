package GUI.elements;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import GUI.textures.TextureLoader;

public class Paper extends JPanel {
	

	private static final long serialVersionUID = -27382936462929511L;

	
	transient BufferedImage background;
	transient Image scaled;
	
	JPanel root;
	
	public Paper(JPanel root) {
		
		this.root = root;
		background = TextureLoader.paper_texture;
		scaled = background;
	}
	
	
	public void adjustSize() {
		
		scaled = background.getScaledInstance(root.getWidth(), root.getHeight(), 0);
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
	
		g2d.drawImage(scaled, null, this);
	}
}


