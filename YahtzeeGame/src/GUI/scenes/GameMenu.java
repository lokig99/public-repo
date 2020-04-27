package GUI.scenes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import GAME.controller.GameController;
import GAME.players.ComputerPlayer;
import GAME.players.HumanPlayer;
import GAME.players.Player;
import GAME.serialization.LoadSave;
import GUI.elements.Game_Background;
import GUI.textures.TextureLoader;

public class GameMenu implements ActionListener {
	
	JFrame frame;
	
	GameController gc;
	
	public void displayMenu() {
		
		frame = new JFrame("Yahtzee! - MainMenu");
		frame.setSize(400,400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(TextureLoader.dice_value_5);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		JPanel mainBackground = new JPanel();
		mainBackground.setLayout(new GridLayout(2,1));
		
		JButton launch = new JButton("New Game");
		launch.addActionListener(this);
		
		JButton load = new JButton("Continue Saved Game");
		load.addActionListener(new loadGame());
		
		mainBackground.add(launch);
		mainBackground.add(load);
		
		frame.add(mainBackground);
		
		frame.validate();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		frame.dispose();
		
		GameConfig gf = new GameConfig();
		gf.display();
		
	}
		

	/************************************************************************/
	
	
	class GameConfig implements ActionListener, MouseListener {
		
		
		JFrame frame1;
		
		JButton launchButton;
		
		boolean[] playersActive = {true, true, true, true};
		boolean anyPlayerActive;
		boolean anyPlayerNoName;
		
		final int maxPlayers = 4;
		
		ArrayList<Player> players = new ArrayList<>();
		
		ArrayList<JCheckBox> playerActive = new ArrayList<>();
		ArrayList<JTextField> playerNames = new ArrayList<>();
		ArrayList<JComboBox<String>> playerOption = new ArrayList<>();
		
		
		void display() {
			
			frame1 = new JFrame("Yahtzee! - Configure");
			frame1.setSize(600,400);
			frame1.setVisible(true);
			frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame1.setIconImage(TextureLoader.dice_value_5);
			frame1.setLocationRelativeTo(null);
			frame1.setResizable(false);
			
			//panel t�a
			Game_Background mainBgdPanel = new Game_Background(frame1);
			mainBgdPanel.setLayout(new BorderLayout());
			frame1.add(mainBgdPanel);		
			
			JPanel buttonPanel = new JPanel(new GridLayout(1,3));
			buttonPanel.setOpaque(false);
			buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 170, 20, 170));
			
			mainBgdPanel.add(BorderLayout.SOUTH, buttonPanel);
			
			launchButton = new JButton("Rozpocznij Grę!");
			launchButton.setFont(new Font("DialogInput",Font.BOLD, 20));
			launchButton.addActionListener(this);
			
			buttonPanel.add(launchButton);
			
			JPanel optionBackground = new JPanel();
			optionBackground.setOpaque(false);
			optionBackground.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			mainBgdPanel.add(optionBackground);
			
			
			GridLayout optionLayout = new GridLayout(4, 3);
			optionLayout.setHgap(15);
			optionLayout.setVgap(15);
			
			optionBackground.setLayout(optionLayout);
			
			String[] options = {"Człowiek", "Komputer"};
			
			for(int i = 0; i < maxPlayers; i++) {
				
				JCheckBox playerA = new JCheckBox(Integer.toString(i+1));
				playerA.setSelected(true);
				playerA.setForeground(Color.WHITE);
				playerA.setOpaque(false);
				playerA.setFont(new Font("DialogInput",Font.BOLD, 32));
				playerA.addActionListener(this);
				
				JTextField playerName = new JTextField("podaj imię gracza");
				playerName.addActionListener(this);
				playerName.setColumns(1);
				playerName.addMouseListener(this);
				playerName.addKeyListener(new KeyAdapter() {
					
					public void keyTyped(KeyEvent e) { 
				        if (playerName.getText().length() >= 12 ) // limit textfield to 12 characters
				            e.consume(); 
					}
				});
				
				JComboBox<String> playerO = new JComboBox<String>(options);
				playerO.addActionListener(this);
				
				this.playerActive.add(playerA);
				this.playerNames.add(playerName);
				this.playerOption.add(playerO);
				
				
				optionBackground.add(this.playerActive.get(i));
				optionBackground.add(this.playerNames.get(i));
				optionBackground.add(this.playerOption.get(i));
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if(e.getSource().equals(this.launchButton)) {
				
				this.anyPlayerActive = false;
				this.anyPlayerNoName = false;
				
				for(int i = 0; i < this.playersActive.length; i++) {
					
					if(this.playersActive[i]) {
						
						this.anyPlayerActive = true;
						
						if(playerNames.get(i).getText().equals("podaj imię gracza") || playerNames.get(i).getText().matches("")) {
							
							this.anyPlayerNoName = true;
						}
					}
				}
				
				if(!this.anyPlayerActive) {
					
					JOptionPane.showMessageDialog(null, "Nie wybrano żadnego gracza!");
				}
				else if(this.anyPlayerNoName) {
					
					JOptionPane.showMessageDialog(null, "Niektórzy gracze nie mają podanego imienia!");	
				}
				else {
					
					for(int i = 0; i < maxPlayers; i++) {
						
						if(playersActive[i]) {
							
							if(this.playerOption.get(i).getSelectedItem().equals("Komputer")) {
								
								this.players.add(new ComputerPlayer(this.playerNames.get(i).getText()));
							}
							else {
								
								this.players.add(new HumanPlayer(this.playerNames.get(i).getText()));
							}
						}
					}
					
					frame1.dispose();
					
					MainWindow m = new MainWindow();
					m.DisplayWindow();
				
					SwingWorker<Void, Void> worker = new SwingWorker<Void,Void>() {
						
						@Override
						public Void doInBackground() throws Exception {
							
							gc = new GameController(m);
							gc.setPlayers(players);
							m.setGc(gc);
							
							gc.startGame();
							
							return null;
						}
						
						@Override
						public void done() {
							
						}	
					};
					
					worker.execute();
				}
			}
			
			/**********PRZYCISK AKTYWACJI GRACZA**************************/		
			else if(this.playerActive.contains(e.getSource())) {
				
				JCheckBox box = (JCheckBox) e.getSource();
				
				int boxIndex = Integer.parseInt(box.getText()) -1;
				
				if(box.isSelected()) {
					
					this.playerNames.get(boxIndex).setEnabled(true);
					this.playerOption.get(boxIndex).setEnabled(true);	
					this.playersActive[boxIndex] = true;
				} 
				else {
					
					this.playerNames.get(boxIndex).setEnabled(false);
					this.playerOption.get(boxIndex).setEnabled(false);
					this.playersActive[boxIndex] = false;
				}
			}
			
			else if(this.playerNames.contains(e.getSource())) {
				
				//boolean isEditing;
				
				//JTextField txt = (JTextField) e.getSource();
				
				//if(txt.addmo)
				
			}
			
			else if(this.playerOption.contains(e.getSource())) {
				
				@SuppressWarnings("unchecked")
				JComboBox<String> box = (JComboBox<String>) e.getSource();
				
				//znajdowanie indeksu obiektu w jego li�cie
				
				int index = 0;
				
				for(int i = 0; i < this.playerOption.size(); i++) {
					
					if(box.equals(this.playerOption.get(i))) {
						
						index = i;
						break;
					}
				}
				
				if(box.getSelectedItem().equals("Komputer")) {
					
					this.playerNames.get(index).setText("CPU" + (index+1));
					this.playerNames.get(index).setEditable(false);
				}
				else {
					
					this.playerNames.get(index).setText("podaj imię gracza");
					this.playerNames.get(index).setEditable(true);
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			 
			System.out.println("dziala");
			
			JTextField txt = (JTextField) e.getSource();
			
			if(txt.getText().equals("podaj imię gracza")) {
				
				txt.setText("");
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

	/****************************************************************************/
	
	class loadGame implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		
			MainWindow m = new MainWindow();
			
			gc = new GameController(m);
			gc.setSavedGame(true);
			
			LoadSave ls = new LoadSave(); 	
			ls.loadGame();
			
			if(ls.isLoadingSuccesful()) {
				
				m.DisplayWindow();
				frame.dispose();
				gc.setPlayers(ls.getPlayers());
			
				//DEBUG
				for(Player p: ls.getPlayers()) {
					
					System.out.println(p.isCurrentPlayer());
				}
				
				System.out.println("\n\n\n");
				
				//DEBUG
				for(Player p: gc.getPlayers()) {
					
					System.out.println(p.isCurrentPlayer());
				}
				
				System.out.println("\n\n\n");
				
				int minRound = gc.getPlayers().get(0).getCurrentRound();
				
				gc.setLastRound(minRound);
				gc.setCurrentRound(minRound);
			
				/***************************************************************************/
				
				SwingWorker<Void, Void> worker = new SwingWorker<Void,Void>() {
					
					@Override
					public Void doInBackground() throws Exception {
						
						m.setGc(gc);
						
						for(Player p: gc.getPlayers()) {
							
							System.out.println(p.isCurrentPlayer());
						}
						System.out.println("\n\n\n");
						gc.startGame();
						
						return null;
					}
				
					@Override
					public void done() {
						
					}	
				};
				
				worker.execute();
			}
		}	
	}
}
