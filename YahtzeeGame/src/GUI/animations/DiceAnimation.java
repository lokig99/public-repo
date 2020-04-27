package GUI.animations;

import java.util.ArrayList;
import GUI.elements.DiceModel;

public class DiceAnimation {
	
	private ArrayList<DiceModel> dice = new ArrayList<DiceModel>();

	public DiceAnimation() {}
	
	public DiceAnimation(ArrayList<DiceModel> dice) {
		
		this.dice = dice;
	}
	
	
	//dodawanie obiektów koœci
	public void addDiceModel(DiceModel dice) {
		
		this.dice.add(dice);
	}
	
	
	public void removeDiceModel(DiceModel dice) {
		
		while(this.dice.contains(dice))
			
			this.dice.remove(dice);
	}
	

	/*ANIMACJA ODBIJANIA SIÊ KOŒCI OD STO£U_________________________________*/
	void bounceAnimation(DiceModel dice) {
		
			
			dice.resetAllCoordinates();
			dice.setPosY(40);
			dice.repaint();

			int currentPosY = dice.getPosY();
			final int finalSize = (int)(dice.getOriginSize()*1.5);
						
			for(int j = 0; j < 3; j++) {
				
				//BOUNCE UP_______________________________________________________
				for(int i = 0; i < (finalSize - dice.getOriginSize()); i++) {
					
					dice.setDiceSize(dice.getOriginSize()+i);
					dice.setPosY(currentPosY + i*2);
	
					//SHOWING RANDOM VALUES DURING ANIMATION
					if(dice.getDiceSize() == dice.getOriginSize()) {
						
						dice.setDiceValue( (int) (Math.random()*6+1) );
					}
					
					dice.repaint();
					
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				currentPosY = dice.getPosY();
				
				//BOUNCE BACK DOWN
				for(int i = 0; i < (finalSize - dice.getOriginSize()); i++) {
					
					dice.setDiceSize(dice.getDiceSize()-1) ;
					dice.repaint();

					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
	}
	
	
	void turnBackAll(long tempo) {
		
		for(DiceModel dice: this.dice) {
			
			final int startPosY = dice.getPosY();
			
			for(int i = 0; i < (startPosY - dice.originPosY); i++) {
				
				dice.setPosY(dice.getPosY()-1);
				dice.repaint();
			
				try {
					Thread.sleep(tempo);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		for(DiceModel dice: this.dice) {
			
			dice.resetAllCoordinates();
		}
	}
	
	
	void turnBack(long tempo, DiceModel dice) {
		

			final int startPosY = dice.getPosY();
			
			for(int i = 0; i < (startPosY - dice.originPosY); i++) {
				
				dice.setPosY(dice.getPosY()-1);
				dice.repaint();
			
				try {
					Thread.sleep(tempo);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
			dice.resetAllCoordinates();
		
	}
	
	
	void selectionAnimation() {
		
		for(DiceModel dice: this.dice) {
			
			final int endPosY = (int) (dice.getPosY()*1.5);
			
			for(int i = 0; i < (endPosY - dice.originPosY); i++) {
				
				dice.setPosY(dice.getPosY()+1);
				dice.repaint();
				
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
		
	public void performRollAnimationAll() {
		
		for(DiceModel d: this.dice) {
			
			this.bounceAnimation(d);
		}
		
		this.turnBackAll(3);
	}
	
	
	public void performRollAnimation(DiceModel dice) {
		
		this.bounceAnimation(dice);
		this.turnBack(3,dice);
	}
	
	
	public void hideAll() {
		
		for(DiceModel d: this.dice) {
			
			d.setPosY(-d.originPosY);
			d.repaint();
		}
	}
	

	public void performSelectionAnimation() {
		
		this.selectionAnimation();
	}
	
	
	public void performTurnBackAnimation() {
		
		this.turnBackAll(2);
	}
}