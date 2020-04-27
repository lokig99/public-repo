package GUI.scenes;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import GAME.controller.GameController;
import GUI.animations.DiceAnimation;
import GUI.controller.MainController;
import GUI.elements.DiceModel;
import GUI.elements.Game_Background;
import GUI.elements.Paper;
import GUI.elements.Scoreboard;
import GUI.textures.TextureLoader;

public class MainWindow {
	

	public JFrame frame;
	
	JPanel mainBackgroundPanel;
	JPanel toolbarBgdPanel;
	JPanel toolbarButtonPanel;
	JPanel scoreBgdPanel;
	JPanel gameBgdPanel;     
	JPanel gameTopBgdPanel;
	JPanel rollButtonPanel;
	JPanel choiceTableBgdPanel;
	JPanel TopTableBgdPanel;
	JPanel BottomTableBgdPanel;
	JPanel actionButtonBgdPanel;
	JPanel DiceBgdPanel;
	JPanel DiceMainPanel;
	
	JLabel scoreLabel;
	public JLabel roundLabel;
	public JLabel playerLabel;

	public JButton rollButton;
	public JButton saveButton;
	public JButton loadGame;
	public JButton acceptActionButton;
	public JButton historyActionButton;
	public ButtonGroup tableCheckBoxesGroup = new ButtonGroup();
	
	public ArrayList<JCheckBox> tableCheckBoxes = new ArrayList<>();
	ArrayList<JPanel> dice_BgdPanels = new ArrayList<>();
	public ArrayList<DiceModel> DiceModel = new ArrayList<>();
	public ArrayList<JButton> diceButton = new ArrayList<>();

	
	public final int diceAmount = 5;
	private final int maxPlayers = 4;
	private int currentCheckBox = 0;
	private int rerollCount = 0;
	
	public JLabel[][] scores = new JLabel[maxPlayers][3];
	
	
	DiceAnimation rollAnimation = new DiceAnimation();
	Game_Background game_bgd;
	Scoreboard sb;
	
	public boolean checkboxSelected = false;
	
	public JTextArea scoreAchieved;
	public JTextArea history = new JTextArea();
	
	MainController mc = new MainController(this);
	private GameController gc;
	
	
	public void DisplayWindow() {
		
		/***********Ramka programu*****************************************/
		
		frame = new JFrame("Yahtzee!");
		frame.setSize(1280,1024);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
		frame.setResizable(false);
		frame.setIconImage(TextureLoader.dice_value_5);
		frame.setLocation(0, 0);
		//frame.setLocationRelativeTo(null);
		
		saveButton = new JButton("Zapisz Grę");
		saveButton.addActionListener(mc);
		
		
		JMenuBar xd = new JMenuBar();
		xd.setVisible(true);
		xd.setEnabled(true);
		xd.add(saveButton);
		
		
	//	xd.add(loadGame);
		game_bgd = new Game_Background(frame);
		frame.setJMenuBar(xd);

		/*_________________________________________________________________*/
		
		game_bgd.setLayout(new BorderLayout());
		frame.getContentPane().add(game_bgd);
		frame.validate();
		
		/***********Glowny panel tla programu*******************************/
		
		mainBackgroundPanel = new JPanel();
		mainBackgroundPanel.setLayout(new BorderLayout());
		mainBackgroundPanel.setOpaque(false);
		mainBackgroundPanel.setBackground(Color.GRAY);
		
		game_bgd.add(mainBackgroundPanel);
		frame.validate();
		
		/*__________________________________________________________________*/
		
		
		/*********Panel tablicy wynikow****************************************/
		
		sb = new Scoreboard();
		sb.setLayout(new BorderLayout());
		
		mainBackgroundPanel.add(BorderLayout.EAST, sb);
		frame.validate();
	
		scoreBgdPanel = new JPanel();
		scoreBgdPanel.setOpaque(false);
		scoreBgdPanel.setLayout(new BoxLayout(scoreBgdPanel, BoxLayout.Y_AXIS));
		scoreBgdPanel.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
		
		scoreLabel = new JLabel("Tabela Wyników");
		scoreLabel.setFont(new Font("DialogInput",Font.BOLD, 30));
		scoreLabel.setForeground(Color.WHITE);
		scoreBgdPanel.add(scoreLabel);
		
		for(int i = 0; i < scores.length; i++) {
			
			JSeparator ScoreBoardSeparator = new JSeparator();
			scoreBgdPanel.add(ScoreBoardSeparator);
			JLabel score = new JLabel();
			score.setFont(new Font("DialogInput",Font.BOLD, 30));
			score.setForeground(Color.WHITE);
			
			this.scores[i][0] = score;
			scoreBgdPanel.add(scores[i][0]);
			
			for(int j = 1; j <= 2; j++) {
				
				JLabel scoreT = new JLabel();
				scoreT.setFont(new Font("DialogInput",Font.BOLD, 24));
				scoreT.setForeground(Color.WHITE);
				
				this.scores[i][j] = scoreT;
				scoreBgdPanel.add(scores[i][j]);
			}	
		}
		
		JSeparator ScoreBoardSeparator = new JSeparator();
		scoreBgdPanel.add(ScoreBoardSeparator);
		
		
		sb.add( scoreBgdPanel);
		frame.validate();
		
		/*_________________________________________________________________________*/
		
		gameBgdPanel = new JPanel();
		gameBgdPanel.setOpaque(false);
		gameBgdPanel.setLayout(new BorderLayout());
		
		mainBackgroundPanel.add(BorderLayout.CENTER, gameBgdPanel);
		
		gameTopBgdPanel = new JPanel(null);
		gameTopBgdPanel.setLayout(new GridLayout(1,3));
		gameTopBgdPanel.setOpaque(false);
		gameTopBgdPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
	
		gameBgdPanel.add(BorderLayout.NORTH, gameTopBgdPanel);
		
		JPanel westTopPanel = new JPanel();
		westTopPanel.setOpaque(false);
		JPanel centerTopPanel = new JPanel();
		centerTopPanel.setOpaque(false);
		JPanel eastTopPanel = new JPanel();
		eastTopPanel.setOpaque(false);
		
		gameTopBgdPanel.add(westTopPanel);
		gameTopBgdPanel.add(centerTopPanel);
		gameTopBgdPanel.add(eastTopPanel);
		
		
		roundLabel = new JLabel("Runda: 1");
		roundLabel.setFont(new Font("DialogInput",Font.BOLD, 30));
		roundLabel.setForeground(Color.WHITE);
		
		playerLabel = new JLabel(); 
		playerLabel.setFont(new Font("DialogInput",Font.BOLD, 24));
		playerLabel.setForeground(Color.WHITE);
		
		
		
		rollButtonPanel = new JPanel();
		rollButtonPanel.setOpaque(false);
		
		rollButton = new JButton("RZUCAJ");
		rollButton.setFont(new Font("DialogInput",Font.BOLD, 45));
		
		rollButtonPanel.add(rollButton);
		
		westTopPanel.add(roundLabel);
		centerTopPanel.add(rollButton);	
		eastTopPanel.add(playerLabel);
		
		//panel wyborow gracza (tabelka z gry yahtzee)
		GridLayout cTableLayout = new GridLayout(1,2);
		
		cTableLayout.setHgap(10);
		cTableLayout.setVgap(10);
		
		choiceTableBgdPanel = new JPanel();
		
		BoxLayout cTableLayout2 = new BoxLayout(choiceTableBgdPanel, BoxLayout.X_AXIS);
		
		
		choiceTableBgdPanel.setOpaque(false);
		choiceTableBgdPanel.setPreferredSize(new Dimension(0, 250));
		choiceTableBgdPanel.setLayout(cTableLayout2);
		choiceTableBgdPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 0, 20));
		 
	
		TopTableBgdPanel = new JPanel(new BorderLayout());
		GridLayout cbLayout = new GridLayout(3,2);
		TopTableBgdPanel.setBackground(Color.WHITE);
		
		Paper paper0 = new Paper(TopTableBgdPanel);
		paper0.setLayout(cbLayout);
		paper0.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 20));
		
		JPanel separator1 = new JPanel();
		separator1.setOpaque(false);
		separator1.setPreferredSize(new Dimension(10,0));
		
		JPanel separator2 = new JPanel();
		separator2.setOpaque(false);
		separator2.setPreferredSize(new Dimension(10,0));

		
		
		TopTableBgdPanel.add(paper0);		
		
		
		
		BottomTableBgdPanel = new JPanel(new BorderLayout());
		
		Paper paper1 = new Paper(BottomTableBgdPanel);
		paper1.setLayout(cbLayout);
		paper1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 40));
		BottomTableBgdPanel.add(paper1);	

		//Stworzenie check box�w
		tableCheckBoxes.add(new JCheckBox("Jedynki"));
		tableCheckBoxes.add(new JCheckBox("Dwójki"));
		tableCheckBoxes.add(new JCheckBox("Trójki"));
		tableCheckBoxes.add(new JCheckBox("Czwórki"));
		tableCheckBoxes.add(new JCheckBox("Piątki"));
		tableCheckBoxes.add(new JCheckBox("Szóstki"));
		tableCheckBoxes.add(new JCheckBox("Trzy jednakowe"));
		tableCheckBoxes.add(new JCheckBox("Cztery jednakowe"));
		tableCheckBoxes.add(new JCheckBox("Full"));
		tableCheckBoxes.add(new JCheckBox("Mały strit"));
		tableCheckBoxes.add(new JCheckBox("Duży strit"));
		tableCheckBoxes.add(new JCheckBox("Generał"));
		tableCheckBoxes.add(new JCheckBox("Szansa"));
		
	
		

		for(int i = 0; i<tableCheckBoxes.size(); i++) {
			
			tableCheckBoxesGroup.add(tableCheckBoxes.get(i));
			tableCheckBoxes.get(i).setContentAreaFilled(false);
			tableCheckBoxes.get(i).setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			tableCheckBoxes.get(i).addActionListener(mc);
			
			if(i < 6) {
				
				paper0.add(tableCheckBoxes.get(i));
			}
			else {
				
				paper1.add(tableCheckBoxes.get(i));
			}
		}
		
		//dodanie paneli tablic wyboru do glownego panelu tla tablic
		choiceTableBgdPanel.add(TopTableBgdPanel);
		choiceTableBgdPanel.add(separator1);
		choiceTableBgdPanel.add(BottomTableBgdPanel);
		choiceTableBgdPanel.add(separator2);
		
		frame.validate();
	
		
		//Dodanie dodatkowym przyciskow akcji gracza
		acceptActionButton = new JButton("Akceptuj wybór");
		acceptActionButton.addActionListener(mc);
		acceptActionButton.setFont(new Font("DialogInput",Font.BOLD, 18));
		historyActionButton = new JButton("Historia rozgrywki");
		historyActionButton.addActionListener(mc);
		historyActionButton.setFont(new Font("DialogInput",Font.BOLD, 18));
		
		GridLayout actionButtonLayout = new GridLayout(2,1);
		actionButtonLayout.setHgap(20);
		actionButtonLayout.setVgap(20);
		
		actionButtonBgdPanel = new JPanel(actionButtonLayout);
		actionButtonBgdPanel.setOpaque(false);
		actionButtonBgdPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		actionButtonBgdPanel.add(acceptActionButton);
		actionButtonBgdPanel.add(historyActionButton);

		choiceTableBgdPanel.add(actionButtonBgdPanel);
		
		//dodanie panela tabelek do panelu tla gry
		gameBgdPanel.add(BorderLayout.SOUTH, choiceTableBgdPanel);
		
		//dodanie paneli pod wizualizacje kosci
		frame.validate();
		
		DiceBgdPanel = new JPanel(new BorderLayout());
		DiceBgdPanel.setOpaque(false);
		gameBgdPanel.add(BorderLayout.CENTER, DiceBgdPanel);

		DiceMainPanel = new JPanel();
		DiceMainPanel.setOpaque(false);
		
		GridLayout diceLayout = new GridLayout(1,5);
		DiceMainPanel.setLayout(diceLayout);
		DiceMainPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		
		DiceBgdPanel.add(BorderLayout.CENTER, DiceMainPanel);
		
		frame.validate();

		for(int i = 0; i < diceAmount; i++) {
			
			JPanel diceBgd = new JPanel(new BorderLayout());
			dice_BgdPanels.add(diceBgd);
			DiceMainPanel.add(dice_BgdPanels.get(i));
		}
		
		frame.validate();
	
		for(int i = 0; i < diceAmount; i++) {
			
			//tworzenie kosci
			DiceModel dice = new DiceModel(dice_BgdPanels.get(i), i+1);
			DiceModel.add(dice);
			
			dice_BgdPanels.get(i).add(DiceModel.get(i));
			rollAnimation.addDiceModel(DiceModel.get(i));
			
			//tworzenie przyciskow kosci
			JButton dButton = new JButton();
			dButton.setOpaque(false);
			dButton.setContentAreaFilled(false);
			dButton.setBorderPainted(false);
			dButton.addActionListener(mc);
			
			diceButton.add(dButton);
			
			DiceModel.get(i).setLayout(new BorderLayout());
			DiceModel.get(i).add(diceButton.get(i));
		}

		scoreAchieved = new JTextArea();
		scoreAchieved.setOpaque(false);
		scoreAchieved.setWrapStyleWord(true);
		scoreAchieved.setLineWrap(true);
		scoreAchieved.setEditable(false);
		scoreAchieved.setForeground(Color.WHITE);
		scoreAchieved.setFont(new Font("DialogInput",Font.BOLD, 24));
		
		diceButton.get(2).setLayout(new BoxLayout(diceButton.get(2), BoxLayout.Y_AXIS));
		diceButton.get(2).add(scoreAchieved);		
		
		
		rollButton.addActionListener(mc);
		
		frame.validate();
		
		tableCheckBoxes.add(new JCheckBox("tmp"));
		tableCheckBoxesGroup.add(tableCheckBoxes.get(13));
		
		hideAllDices();
	}
	
	
	
	public void roll(DiceModel dice) {
		
		rollAnimation.performRollAnimation(dice);
	}
	
	public void rollAll() { 
		
		for(JButton b: diceButton) {
			
			b.setEnabled(false);
		}
	 	rollAnimation.performRollAnimationAll();
	 	for(JButton b: diceButton) {
			
			b.setEnabled(true);
		}
	}
	
	
	public void hideAllDices() {
		
		for(JButton b: diceButton) {
			
			b.setEnabled(false);
		}
		rollAnimation.hideAll();
	}
	
	public void turnbackAll() {

		for(JButton b: diceButton) {
			
			b.setSelected(false);
			b.setEnabled(false);
		}
		
	 	rollAnimation.performTurnBackAnimation();
	 	
	 	for(JButton b: diceButton) {
			
			b.setEnabled(true);
		}
	}


	public int getRerollCount() {
		return rerollCount;
	}


	public void setRerollCount(int rerollCount) {
		this.rerollCount = rerollCount;
	}



	public int getCurrentCheckBox() {
		return currentCheckBox;
	}



	public void setCurrentCheckBox(int currentCheckBox) {
		this.currentCheckBox = currentCheckBox;
	}



	public GameController getGc() {
		return gc;
	}



	public void setGc(GameController gc) {
		this.gc = gc;
	}
}
