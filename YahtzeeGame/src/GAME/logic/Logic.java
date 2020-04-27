package GAME.logic;

import GAME.players.Player;

public class Logic {
	
	
	public static int TablePoints(Player player, int category) {
			
			switch(category) {
			
			//Górna czêœæ tabelki_______________________________________________
			

				//Jedynki
				case 0: {
			
					int score = 0;
					
					for(int i = 0; i<player.getCurrentDices().length; i++) {
						
						if(player.getCurrentDices()[i] == 1)
							
							score += 1;	
					}
					
					return score;
				}
				
				//Dwójki
				case 1: {
					
					int score = 0;
					
					for(int i = 0; i<player.getCurrentDices().length; i++) {
						
						if(player.getCurrentDices()[i] == 2)
							
							score += 2;	
					}
					
					return score;	
				}
				
				//Trójki
				case 2: {
					
					int score = 0;
					
					for(int i = 0; i<player.getCurrentDices().length; i++) {
						
						if(player.getCurrentDices()[i] == 3)
							
							score += 3;		
					}
				
					return score;
				}
			
				//Czwórki
				case 3: {
					
					int score = 0;
					
					for(int i = 0; i<player.getCurrentDices().length; i++) {
						
						if(player.getCurrentDices()[i] == 4)
							
							score += 4;		
					}
				
					return score;
				}
				
				//Pi¹tki
				case 4: {
					
					int score = 0;
					
					for(int i = 0; i<player.getCurrentDices().length; i++) {
						
						if(player.getCurrentDices()[i] == 5)
							
							score += 5;		
					}
				
					return score;
				}
				
				//Szóstki
				case 5: {
					
					int score = 0;
					
					for(int i = 0; i<player.getCurrentDices().length; i++) {
						
						if(player.getCurrentDices()[i] == 6)
							
							score += 6;		
					}
				
					return score;
				}
				
				
				//Dolna czêœæ tabelki__________________________________________________
				
				//3 jednakowe
				case 6: {
					
					int score = 0;
					
					for(int i = 0; i<player.getCurrentDices().length; i++)
					
						score += player.getCurrentDices()[i];
					
					if(player.getJoker())
						
						return score;
					
					for(int i = 1; i<=6; i++) {
						
						int eqDices = 0;
					
						for(int j = 0; j<player.getCurrentDices().length; j++) {
							
							if(player.getCurrentDices()[j] == i)
								
								eqDices++;
						}
						
						if(eqDices == 3)
							
							return score;
					}
					
					return 0;	
				}
				
				//4 jednakowe
				case 7: {
					
					int score = 0;
					
					for(int i = 0; i<player.getCurrentDices().length; i++)
					
						score += player.getCurrentDices()[i];
					
					if(player.getJoker())
						
						return score;
					
					for(int i = 1; i<=6; i++) {
						
						int eqDices = 0;
					
						for(int j = 0; j<player.getCurrentDices().length; j++) {
							
							if(player.getCurrentDices()[j] == i)
								
								eqDices++;
						}
						
						if(eqDices == 4)
							
							return score;
					}
					
					return 0;	
				}
				
				//Full
				case 8: {
					
					if(player.getJoker())
						
						return 25;
					
					//sprawdzenie czy s¹ 3 jednakowe
					for(int i = 1; i<=6; i++) {
						
						int eqDices = 0;
						int eqScore = 0;
					
						for(int j = 0; j<player.getCurrentDices().length; j++) {
							
							eqScore = i;
							
							if(player.getCurrentDices()[j] == i) 
								
								eqDices++;	
						}
						//jezeli s¹ 3 jednakowe to szuka pary
						if(eqDices == 3) {
							
							for(int k = 1; k<=6; k++) {
								
								int DicePair = 0;
							
								for(int l = 0; l<player.getCurrentDices().length; l++) {
									
									if(player.getCurrentDices()[l] == k && eqScore != k)
										
										DicePair++;	
								}
								
								if(DicePair == 2)
									
									return 25;	
							}
						}
					}
			
					return 0;	
				}
				
				//Ma³y strit - cztery kosci z oczkami po kolei
				case 9: {
					
					if(player.getJoker())
						
						return 30;
					
					int condCount = 0;
					
					//znajdowanie najmniejszej wartosci koœci
					
					int minDice = player.getCurrentDices()[0];
					
					for(int i = 1; i < player.getCurrentDices().length; i++) {
						
						minDice = Math.min(minDice, player.getCurrentDices()[i]);
					}
		
					int nextDice = minDice;
					
					for(int i = 0; i < player.getCurrentDices().length; i++) {
					
						nextDice++;
						
						if(condCount >= 3) {
							
							break;
						}
					
						for(int j = 0; j < player.getCurrentDices().length; j++) {
							
							if(nextDice == player.getCurrentDices()[j]) {
								
								condCount++;
								break;
							}
						}	
					}
					
					if(condCount == 3) {
						
						return 30;
					}
					
					return 0;	
				}
				
				//Du¿y strit
				case 10: {
					
					if(player.getJoker())
						
						return 40;
					
					int condCount = 0;
					
					//znajdowanie najmniejszej wartosci koœci
					
					int minDice = player.getCurrentDices()[0];
					
					for(int i = 1; i < player.getCurrentDices().length; i++) {
						
						minDice = Math.min(minDice, player.getCurrentDices()[i]);
					}
		
					int nextDice = minDice;
					
					for(int i = 0; i < player.getCurrentDices().length; i++) {
					
						nextDice++;
					
						for(int j = 0; j < player.getCurrentDices().length; j++) {
							
							if(nextDice == player.getCurrentDices()[j]) {
								
								condCount++;
								break;
							}
						}	
					}
					
					if(condCount == 4) {
						
						return 40;
					}
					
					return 0;
				}
				
				//Genera³ Yahtzee
				case 11: {
					
					boolean condition = false;
					
					for(int i = 1; i<=6; i++) {
						
						int eqDice = i;
						
						for(int j = 0; j<player.getCurrentDices().length; j++)
							
							if(eqDice == player.getCurrentDices()[j]) 
								
								condition = true;
						
							else {
								
								condition = false;
								break;
							}
						
						if(condition)
							
							return 50;
						}
					
	 				return 0;	
				}
			
				//Szansa
				case 12: {
					
					int score = 0;
					
					for(int i = 0; i<player.getCurrentDices().length; i++)
					
						score += player.getCurrentDices()[i];
					
					return score;
				}
			}
		
			//koniec tabelki___________________________________________________________
			
			//jezeli zotanie wybrana nieistniejaca kategoria
			return 0;
	}	
}
