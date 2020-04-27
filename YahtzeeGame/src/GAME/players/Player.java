package GAME.players;

import java.io.Serializable;

import javax.swing.JTextArea;

public abstract class Player implements Serializable {
	

	 	private static final long serialVersionUID = -1323003316707776044L;
		
	 	//sk³adowe klasy Player___________________________________________
		protected String name;
		protected boolean[] TableCheck; //tablica sprawdzajaca czy dane kategorie w tabelce sa juz wykorzystane przez gracza
		protected int ScoreTop;
		protected int ScoreBottom;
		protected boolean BonusUsed;
		protected boolean joker;
		protected int firstGeneral; 
		protected int currentRound;
		protected int[] currentDices;
		protected int[] ScoreRound;
		private boolean currentPlayer;
		private boolean gameSaved;
		
		private JTextArea history = null;
		
		//konstruktor domyœlny____________________________________________
		public Player() {
			
			this.TableCheck = new boolean[13];
			this.ScoreTop = 0;
			this.ScoreBottom = 0;
			this.BonusUsed = false;
			this.joker = false;
			this.firstGeneral = 0;
			this.setCurrentRound(0);
			this.setCurrentPlayer(false);
			this.gameSaved =false;
			
			for(int i = 0; i<this.TableCheck.length; i++)
				
				this.TableCheck[i] = true;	
		}
	 	
		//konstruktor przeci¹¿ony_________________________________________
		public Player(String name) {
			
			this.name = name;
			this.TableCheck = new boolean[13];
			this.ScoreTop = 0;
			this.ScoreBottom = 0;
			this.BonusUsed = false;
			this.joker = false;
			this.firstGeneral = 0;
			this.setCurrentRound(0);
			this.setCurrentPlayer(false);
			this.gameSaved =false;

			for(int i = 0; i<this.TableCheck.length; i++)
				
				this.TableCheck[i] = true;		
		}
		
		//Get-ery_________________________________________________________
		public String getName() {
			
			return this.name;
		}
		
		public boolean getTcheck(int category) {
			
			return this.TableCheck[category];
		}
		
		
		public int getTableScoreT() {
		
			return this.ScoreTop;
		}
		
		
		public int getTableScoreB() {
			
			return this.ScoreBottom;
		}
		
		
		public int getFinalScore() {
			
			return (this.ScoreTop+this.ScoreBottom);
		}
		
		
		public boolean getBonus() {
			
			return this.BonusUsed;
		}
		
		
		public boolean getJoker() {
			
			return this.joker;
		}
		
		
		public int getfirstGeneral() {
			
			return this.firstGeneral;
		}
		
		
		//Set-ety__________________________________________________________
		public void setName(String name) {
			
			this.name = name;
		}
		
		
		public void setTcheck(int category, boolean n) {
			
			this.TableCheck[category] = n;
		}
		
		
		public void addScoreT(int score) {
			
			this.ScoreTop += score;
		}
		
		
		public void addScoreB(int score) {
			
			this.ScoreBottom += score;
		}
		
		
		public void setBonus(boolean n) {
			
			this.BonusUsed = n;
		}
		
		
		public void setJoker(boolean n) {
			
			this.joker = n;
		}
		
		
		public void setfirstGeneral(int n) {
			
			this.firstGeneral = n;
		}

		public int[] getCurrentDices() {
			return currentDices;
		}

		public void setCurrentDices(int[] currentDices) {
			this.currentDices = currentDices;
		}

		public int getCurrentRound() {
			return currentRound;
		}

		public void setCurrentRound(int currentRound) {
			this.currentRound = currentRound;
		}

		public boolean isCurrentPlayer() {
			return currentPlayer;
		}

		public void setCurrentPlayer(boolean currentPlayer) {
			this.currentPlayer = currentPlayer;
		}

		public boolean isGameSaved() {
			return gameSaved;
		}

		public void setGameSaved(boolean gameSaved) {
			this.gameSaved = gameSaved;
		}

		public JTextArea getHistory() {
			return history;
		}

		public void setHistory(JTextArea history) {
			this.history = history;
		}
}
