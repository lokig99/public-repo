package GAME.players;

import GAME.logic.Logic;

public class ComputerPlayer extends Player {
	
	private static final long serialVersionUID = -6778737820465515714L;
	private int bestChoice;
	private boolean[] dicesToRoll = new boolean[5];
	private boolean rollDice = false;
	private boolean wantToRoll = false;
	
	
	public ComputerPlayer(String name) {
		
		super(name);
		
		for(int i = 0; i < this.getDicesToRoll().length; i++) {
			
			this.getDicesToRoll()[i] = false;
		}
	}
	
	//sprawdzenie najlepszej mo¿liwoœci
	public void checkPossibleMoves() {
		
		this.setBestChoice(0);
		
		for(int i = 0; i < this.TableCheck.length; i++) {
			
			if(this.TableCheck[i]) {
				
				if(Logic.TablePoints(this, i) > Logic.TablePoints(this, this.getBestChoice())) {
					
					this.setBestChoice(i);	
				}
			}
		}
		
		
		//je¿eli za wszystkie jest 0 a pierwsza opcja jest zu¿yta
		if(this.bestChoice == 0 && !this.TableCheck[0]) {
			
			for(int i = 1; i < 13; i++) {
				
				if(this.TableCheck[i]) {
					
					this.bestChoice = i;
					break;
				}
			}	
		}	
	}
	
	//decyzja o przerzuceniu koœci
	public void checkRollDices() {
		
		this.setRollDice(false);
		this.wantToRoll = false;
		
		if(bestChoice == 11 || bestChoice == 10 || bestChoice == 9 || bestChoice == 8 || bestChoice == 7 || bestChoice == 6 ) {
			
			for(int i = 0; i < this.getDicesToRoll().length; i++) {
				
				getDicesToRoll()[i] = false;
			}	
		}
		else {
			
			this.wantToRoll = true;
			
			for(int i = 0; i < this.getDicesToRoll().length; i++) {
						
				if(this.currentDices[i] == bestChoice+1) {
							
					getDicesToRoll()[i] = false;
				}
				else {
							
					getDicesToRoll()[i] = true;
					this.setRollDice(true);
				}
			}	
		}
		
		
		//je¿eli impas
		
		if(!this.isRollDice() && this.bestChoice >= 6 && this.wantToRoll) {
			
			this.setRollDice(true);
			
			for(int i = 0; i < this.getDicesToRoll().length; i++) {
				
				getDicesToRoll()[i] = true;
			}
		}	
	}
	
	public int getBestChoice() {
		return bestChoice;
	}


	public void setBestChoice(int bestChoice) {
		this.bestChoice = bestChoice;
	}

	public boolean isRollDice() {
		return rollDice;
	}

	public void setRollDice(boolean rollDice) {
		this.rollDice = rollDice;
	}

	public boolean[] getDicesToRoll() {
		return dicesToRoll;
	}

	public void setDicesToRoll(boolean[] dicesToRoll) {
		this.dicesToRoll = dicesToRoll;
	}

	public boolean isWantToRoll() {
		return wantToRoll;
	}

	public void setWantToRoll(boolean wantToRoll) {
		this.wantToRoll = wantToRoll;
	}
}
