package GAME.serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import GAME.players.Player;

public class LoadSave {
	
	ObjectOutputStream so;
	ObjectInputStream is;
	private ArrayList<Player> players;
	private boolean loadingSuccesful;
	
	public LoadSave() {}
	
	public void saveGame() throws IOException {
		
		try {
			
			so = new ObjectOutputStream(new FileOutputStream("save1.ser"));
			so.writeObject(players);
			so.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "ERROR: FILE NOT FOUND!");
		} 
	}
	
	
	@SuppressWarnings("unchecked")
	public void loadGame() {
		
		setLoadingSuccesful(false);
		
		try {
			
			is = new ObjectInputStream(new FileInputStream("save1.ser"));

			Object obj1 = is.readObject();
			players = (ArrayList<Player>) obj1;
			System.out.println("wczytano ");
			setLoadingSuccesful(true);
			
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			if(e instanceof IOException)
				JOptionPane.showMessageDialog(null, "ERROR: NO SAVE FILE!");
			else
				JOptionPane.showMessageDialog(null, "ERROR: SAVE FILE IS CORRUPTED!");		
		} 	
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public boolean isLoadingSuccesful() {
		return loadingSuccesful;
	}

	public void setLoadingSuccesful(boolean loadingSuccesful) {
		this.loadingSuccesful = loadingSuccesful;
	}
}
