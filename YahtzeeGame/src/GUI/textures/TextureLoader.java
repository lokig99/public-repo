package GUI.textures;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class TextureLoader{
	
	private static TextureLoader INSTANCE = new TextureLoader();

	public static BufferedImage dice_value_1;
	public static BufferedImage dice_value_2;
	public static BufferedImage dice_value_3;
	public static BufferedImage dice_value_4;
	public static BufferedImage dice_value_5;
	public static BufferedImage dice_value_6;
	public static BufferedImage shadow_dice;
	public static BufferedImage background_dice;
	public static BufferedImage background_game;
	public static BufferedImage paper_texture;
	
	private TextureLoader() {
		
		load();
	}
	
	public static void load() {
		
		try {
			
			dice_value_1 = ImageIO.read(new File("textures/dice_value_1.png"));
			dice_value_2 = ImageIO.read(new File("textures/dice_value_2.png"));
			dice_value_3 = ImageIO.read(new File("textures/dice_value_3.png"));
			dice_value_4 = ImageIO.read(new File("textures/dice_value_4.png"));
			dice_value_5 = ImageIO.read(new File("textures/dice_value_5.png"));
			dice_value_6 = ImageIO.read(new File("textures/dice_value_6.png"));
			shadow_dice = ImageIO.read(new File("textures/dice_shadow.png"));
			background_dice = ImageIO.read(new File("textures/background.jpg"));
			background_game = ImageIO.read(new File("textures/game_background.jpg"));
			paper_texture = ImageIO.read(new File("textures/paper.jpg"));
			
			System.out.println("[" + new Date().toString() +"]" + "\t" + " Game textures loaded succesfuly");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "ERROR: TEXTURES DID NOT LOAD CORRECTLY!");
		}
	}
	
	
	public void loadJAR() {
		
		try {
			
			dice_value_1 = ImageIO.read(getClass().getResource("/textures/dice_value_1.png"));
			dice_value_2 = ImageIO.read(getClass().getResource("/textures/dice_value_2.png"));
			dice_value_3 = ImageIO.read(getClass().getResource("/textures/dice_value_3.png"));
			dice_value_4 = ImageIO.read(getClass().getResource("/textures/dice_value_4.png"));
			dice_value_5 = ImageIO.read(getClass().getResource("/textures/dice_value_5.png"));
			dice_value_6 = ImageIO.read(getClass().getResource("/textures/dice_value_6.png"));
			shadow_dice = ImageIO.read(getClass().getResource("/textures/dice_shadow.png"));
			background_dice = ImageIO.read(getClass().getResource("/textures/background.jpg"));
			background_game = ImageIO.read(getClass().getResource("/textures/game_background.jpg"));
			paper_texture = ImageIO.read(getClass().getResource("/textures/paper.jpg"));
			
			System.out.println("[" + new Date().toString() +"]" + "\t" + " Game textures loaded succesfuly");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}


	public static TextureLoader getInstance() {
		return INSTANCE;
	}
}