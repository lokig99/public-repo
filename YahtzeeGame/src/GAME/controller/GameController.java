package GAME.controller;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import GAME.logic.Logic;
import GAME.players.ComputerPlayer;
import GAME.players.HumanPlayer;
import GAME.players.Player;
import GUI.scenes.MainWindow;

public class GameController {

	
	private ArrayList<Player> players = new ArrayList<>();
	private int rounds;
	private int currentRound = 0;
	private int lastRound = 0;
	private String currentPlayer;
	private MainWindow mw;
	private Player cPlayer;
	private boolean savedGame;
	private int currentPlayerIndex;
	
	public GameController(MainWindow mw) {
		
		this.setSavedGame(false);
	
		this.getPlayers().add(new HumanPlayer("Gracz 1"));
		this.getPlayers().add(new ComputerPlayer("CPU01"));
		this.getPlayers().add(new ComputerPlayer("CPU02"));
		this.getPlayers().add(new ComputerPlayer("CPU03"));

		this.setMw(mw);
		this.rounds = 13;
	}

	
	void updateScoreBoard() {
		
		for(int i = 0; i < this.getPlayers().size(); i++) {
			
			getMw().scores[i][0].setText(this.getPlayers().get(i).getName() + ": " + this.getPlayers().get(i).getFinalScore());
			
			for(int j = 1; j <= 2; j++) {
				
				if(j < 2) {
					
					getMw().scores[i][j].setText("Górna tabela: " + this.getPlayers().get(i).getTableScoreT());	
				}
				else {
					
					getMw().scores[i][j].setText("Dolna tabela: " + this.getPlayers().get(i).getTableScoreB());	
				}
			}
		} 
		
		getMw().frame.repaint();
	}
	

	void updateCurrentDices(Player p) {
		
		int[] cDices = new int[getMw().diceAmount];
		
		for(int i = 0; i < cDices.length; i++) {
		
			cDices[i] = getMw().DiceModel.get(i).getDiceValue();
		}
		
		p.setCurrentDices(cDices);
	}
	
	/*************************************************************************/
	
	public void startGame() {
	
	
		for(int i = getCurrentRound(); i < rounds; i++) {
			
			this.setCurrentRound(this.getCurrentRound() + 1);
			
			if(i > 1) {
				
				this.setLastRound(currentRound);
			}
			
			getMw().roundLabel.setText("Runda: " + this.getCurrentRound());
			updateScoreBoard();
			
			if(isSavedGame()) {
				
				mw.history = getPlayers().get(0).getHistory();
				
				for(int k = 0; k < getPlayers().size(); k++) {
					
					if(getPlayers().get(k).isCurrentPlayer()) {
						
						setCurrentPlayerIndex(k);
						break;
					}
				}
			}
			else {
			
				setCurrentPlayerIndex(0);
			} 

			mw.history.setEditable(false);
			mw.history.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
			mw.history.append("Pocz�tek rundy: " + (i+1) + "\n\n");
			
			/****************************************************************************/
			for(int pp = getCurrentPlayerIndex(); pp < getPlayers().size(); pp++ ) {
				
				mw.history.append("\tRunda Gracza: " + getPlayers().get(pp).getName() + "\n");
				
				getMw().scoreAchieved.setVisible(false);
				
				getPlayers().get(pp).setCurrentPlayer(true);
				
				//resetuje ko�ci
				getMw().turnbackAll();
				
				for(JCheckBox cb: getMw().tableCheckBoxes) {
				
					cb.setSelected(true);
				}
				
				//ustawianie checkBox�w gracza
				for(int j = 0; j < getMw().tableCheckBoxes.size()-1; j++) {
					
					if(getPlayers().get(pp).getTcheck(j)) {
						
						getMw().tableCheckBoxes.get(j).setEnabled(true);
						getMw().tableCheckBoxesGroup.add(getMw().tableCheckBoxes.get(j));
					}
					else {
						
				
						getMw().tableCheckBoxes.get(j).setEnabled(false);
						getMw().tableCheckBoxesGroup.remove(getMw().tableCheckBoxes.get(j));
						getMw().tableCheckBoxes.get(j).setSelected(true);
					}		
				}
				
				this.currentPlayer = getPlayers().get(pp).getName();
				
				getMw().playerLabel.setText("Gracz: " + this.currentPlayer);
				getMw().setRerollCount(0);
				
				getMw().acceptActionButton.setEnabled(false);
				getMw().rollButton.setEnabled(false);
				
				getMw().rollAll();
				
				getMw().acceptActionButton.setEnabled(true);
				getMw().rollButton.setEnabled(true);
				
				/*******AKCJA JE�ELI GRACZEM JEST CZ�OWIEK***************************/
				if(getPlayers().get(pp) instanceof HumanPlayer) {
			
					while(true) {
		
						System.out.print("");
						
						if(!getMw().checkboxSelected) {
							
							getMw().acceptActionButton.setEnabled(false);
						}
						else {
							
							getMw().acceptActionButton.setEnabled(true);
						}
						
						if(getMw().acceptActionButton.isSelected()) {
							
							getMw().acceptActionButton.setSelected(false);
							getMw().checkboxSelected = false;
							
							break;
						}
						
						//maksymalnie 2 rzuty ko��mi
						if(getMw().getRerollCount() >= 2) {
							
							getMw().rollButton.setEnabled(false);
						}	
					}
				}
				/**********************************************************************/
				
				/*******AKCJA JE�ELI GRACZEM JEST KOMPUTER*****************************/
				else if(getPlayers().get(pp) instanceof ComputerPlayer) {
					
					updateCurrentDices(getPlayers().get(pp));
					
					((ComputerPlayer) getPlayers().get(pp)).checkPossibleMoves();
					
					((ComputerPlayer) getPlayers().get(pp)).checkRollDices();			
					
					//przerzucanie 1
					if( ((ComputerPlayer) getPlayers().get(pp)).isRollDice() ) {
						
						for(int k = 0; k < getMw().diceAmount; k++) {
							
							if( ((ComputerPlayer)getPlayers().get(pp)).getDicesToRoll()[k] ) {
								
								getMw().roll(getMw().DiceModel.get(k));	
							}
						}
					}
					
					updateCurrentDices(getPlayers().get(pp));
					
				//	((ComputerPlayer) p).checkPossibleMoves();
					
					((ComputerPlayer) getPlayers().get(pp)).checkRollDices();	
					
					//przerzucanie 2
					if( ((ComputerPlayer) getPlayers().get(pp)).isRollDice() ) {
						
						for(int k = 0; k < getMw().diceAmount; k++) {
							
							if( ((ComputerPlayer)getPlayers().get(pp)).getDicesToRoll()[k] ) {
								
								getMw().roll(getMw().DiceModel.get(k));	
							}
						}
					}
					
					//impas
					updateCurrentDices(getPlayers().get(pp));
					
					((ComputerPlayer) getPlayers().get(pp)).checkPossibleMoves();	
					
					
					//wybiera kategorie
					getMw().tableCheckBoxes.get( ((ComputerPlayer) getPlayers().get(pp)).getBestChoice() ).setSelected(true);
					getMw().setCurrentCheckBox( ((ComputerPlayer) getPlayers().get(pp)).getBestChoice() );
					
				}
				
				/*UNIWERSALNE DLA OBU***********************************************/
				
				getPlayers().get(pp).setTcheck(getMw().getCurrentCheckBox(), false);
				
				updateCurrentDices(getPlayers().get(pp));
				
				if(getMw().getCurrentCheckBox() < 6) {
					
					getPlayers().get(pp).addScoreT(Logic.TablePoints(getPlayers().get(pp), getMw().getCurrentCheckBox()));
				}
				
				if(getMw().getCurrentCheckBox() >= 6) {
					
					getPlayers().get(pp).addScoreB(Logic.TablePoints(getPlayers().get(pp), getMw().getCurrentCheckBox()));
				}
			
				
				updateScoreBoard();
				
				getMw().scoreAchieved.setText(getPlayers().get(pp).getName() + " otrzymuje " + Logic.TablePoints(getPlayers().get(pp), getMw().getCurrentCheckBox()) + " pkt!");
				getMw().scoreAchieved.setVisible(true);
				getMw().acceptActionButton.setEnabled(false);
				
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(getPlayers().get(pp).getTableScoreT() >= 63 && !getPlayers().get(pp).getBonus()) {
					
					getPlayers().get(pp).setBonus(true);
					getPlayers().get(pp).addScoreT(35);
					updateScoreBoard();
					
					getMw().scoreAchieved.setText(getPlayers().get(pp).getName() + " otrzymuje bonus 35 pkt!!!");
					
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				mw.history.append("\n\tGracz: " + getPlayers().get(pp).getName() + " otrzymuje: " + Logic.TablePoints(getPlayers().get(pp), getMw().getCurrentCheckBox()) + " pkt!\n");
				mw.history.append("\t_________________________________________________\n\n");
				getPlayers().get(pp).setCurrentPlayer(false);
				getPlayers().get(pp).setCurrentRound(this.getCurrentRound());
				setSavedGame(false);
			}
		}
		
		Player winner = this.getPlayers().get(0);
		for(int n = 1; n<this.getPlayers().size(); n++) {
			
			if(winner.getFinalScore() < this.getPlayers().get(n).getFinalScore())
				
				winner = this.getPlayers().get(n);
			}
		
		getMw().scoreAchieved.setText(winner.getName() + " WYGRYWA!!!");
	}


	public MainWindow getMw() {
		return mw;
	}


	public void setMw(MainWindow mw) {
		this.mw = mw;
	}


	public ArrayList<Player> getPlayers() {
		return players;
	}


	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}


	public int getCurrentRound() {
		return currentRound;
	}


	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}


	public Player getcPlayer() {
		return cPlayer;
	}


	public void setcPlayer(Player cPlayer) {
		this.cPlayer = cPlayer;
	}


	public int getLastRound() {
		return lastRound;
	}


	public void setLastRound(int lastRound) {
		this.lastRound = lastRound;
	}


	public boolean isSavedGame() {
		return savedGame;
	}


	public void setSavedGame(boolean savedGame) {
		this.savedGame = savedGame;
	}


	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}


	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}
}
