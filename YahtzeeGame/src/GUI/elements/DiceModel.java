package GUI.elements;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Date;
import javax.swing.JPanel;
import GUI.textures.TextureLoader;


public class DiceModel extends JPanel {
	
	private static final long serialVersionUID = 5781581783372207840L;
	private JPanel rootPanel;
	private int Size;
	private final int originSize;
	private int posX;
	private int posY;
	private int diceValue;
	
	public final int originPosX;
	public final int originPosY;
	
	transient private BufferedImage dice1;
	transient private BufferedImage dice2;
	transient private BufferedImage dice3;
	transient private BufferedImage dice4;
	transient private BufferedImage dice5;
	transient private BufferedImage dice6;
	transient private BufferedImage shadow;
	transient private Image shadow_dice;
	transient private Image shadow_bgd;
	transient private BufferedImage background;

	
	public DiceModel(JPanel root) {
		
		this.rootPanel = root;
		this.Size = this.rootPanel.getWidth()/2;
		this.posX = (this.rootPanel.getWidth()/4);
		this.posY = (this.rootPanel.getHeight()/2)-this.Size/2;
		this.diceValue = 5;
		this.originSize = this.Size;
		this.originPosX = this.posX;
		this.originPosY = this.posY;
		
		loadTextures();
	}
	
	
	public DiceModel(JPanel root, int value) {
		
		this.rootPanel = root;
		this.Size = this.rootPanel.getWidth()/2;
		this.posX = (this.rootPanel.getWidth()/4);
		this.posY = (this.rootPanel.getHeight()/2)-this.Size/2;
		this.diceValue = value;
		this.originSize = this.Size;
		this.originPosX = this.posX;
		this.originPosY = this.posY;
	
		loadTextures();	
	}
	
	
	void loadTextures() {
	
			dice1 = TextureLoader.dice_value_1; 
			dice2 = TextureLoader.dice_value_2; 
			dice3 = TextureLoader.dice_value_3; 
			dice4 = TextureLoader.dice_value_4; 
			dice5 = TextureLoader.dice_value_5; 
			dice6 = TextureLoader.dice_value_6;
			
			shadow = TextureLoader.shadow_dice; 
			background = TextureLoader.background_dice; 
			
			shadow_dice = shadow.getScaledInstance(Size*2, Size*2, Size*2);
			shadow_bgd = shadow.getScaledInstance(shadow.getWidth()*4,Size*2, Size*2);
			
			System.out.println("[" + new Date().toString() +"]" + "\t" + this.getClass() + " | Dice textures loaded succesfuly");	
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;		
		
		g2d.drawImage(background, 0 , 0 , this);
	
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g2d.drawImage(shadow_bgd,-300, -100, this);
		g2d.drawImage(shadow_bgd, -300, this.getHeight()-80, this);
		
		switch(diceValue) {
		
			case 1: {
		
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				g2d.drawImage(shadow_dice, posX-Size/2, posY-Size/2, this);
				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				g2d.drawImage(dice1.getScaledInstance(Size, Size, Size),posX,posY,this);
				break;
			}
			
			case 2: {
				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				g2d.drawImage(shadow_dice, posX-Size/2, posY-Size/2, this);
				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				g2d.drawImage(dice2.getScaledInstance(Size, Size, Size),posX,posY,this);
				break;
			}
			
			case 3: {
				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				g2d.drawImage(shadow_dice, posX-Size/2, posY-Size/2, this);
				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				g2d.drawImage(dice3.getScaledInstance(Size, Size, Size),posX,posY,this);
				break;
			}
			
			case 4: {

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				g2d.drawImage(shadow_dice, posX-Size/2, posY-Size/2, this);
				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				g2d.drawImage(dice4.getScaledInstance(Size, Size, Size),posX,posY,this);
				break;
			}
			
			case 5: {

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				g2d.drawImage(shadow_dice, posX-Size/2, posY-Size/2, this);
				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				g2d.drawImage(dice5.getScaledInstance(Size, Size, Size),posX,posY,this);
				break;
			}
			
			case 6: {

				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				g2d.drawImage(shadow_dice, posX-Size/2, posY-Size/2, this);
				
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				g2d.drawImage(dice6.getScaledInstance(Size, Size, Size),posX,posY,this);
				break;
			}
		}
	}
	
	public void scale(double x) {
		
		this.Size = (int) (this.Size * x);
		this.repaint();
	}
	
	
	public void resetSize() {
		
		this.Size = this.originSize;
		this.repaint();
	}
	
	
	public void resetAllCoordinates() {
		
		this.posX = this.originPosX;
		this.posY = this.originPosY;
		this.repaint();
	}
	
		
	//Get-ery i Set-ery________________________________________________________________________________
	
	public int getDiceSize() {
		return Size;
	}


	public void setDiceSize(int size) {
		Size = size;
		this.repaint();
	}


	public int getPosX() {
		return posX;
	}


	public void setPosX(int posX) {
		this.posX = posX;
		this.repaint();
	}


	public int getPosY() {
		return posY;
	}


	public void setPosY(int posY) {
		this.posY = posY;
		this.repaint();
	}


	public int getDiceValue() {
		return diceValue;
	}


	public void setDiceValue(int diceValue) {
		this.diceValue = diceValue;
		this.repaint();
	}
	
	
	public int getOriginSize() {
		
		return this.originSize;
	}
}