package GUI.controller;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import GAME.serialization.LoadSave;
import GUI.animations.DiceAnimation;
import GUI.elements.DiceModel;
import GUI.scenes.MainWindow;
import GUI.textures.TextureLoader;

public class MainController implements ActionListener {

	private MainWindow mw;
	
	private  boolean gameHistoryFrameDisplayed;
	
	JFrame frame = new JFrame("Historia rozgrywki");
	
	public MainController(MainWindow mw) {
		// TODO Auto-generated constructor stub
		
		this.mw = mw;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		
		/*ROLL BUTTON**************************************************/
		if(event.getSource().equals(mw.rollButton)) {
			
			SwingWorker<Void, Void> worker = new SwingWorker<Void,Void>() {

				@Override
				public Void doInBackground() throws Exception {
					
					mw.rollButton.setEnabled(false);
			
					for(JButton d: mw.diceButton) {
						
						d.setEnabled(false);	
					}
					
					boolean anySelected = false;
					
					for(JButton d: mw.diceButton) {
						
						if(d.isSelected()) {
							
							d.setSelected(false);
							mw.roll((DiceModel) d.getParent());
							anySelected = true;
						}
					}
				
					if(anySelected) mw.setRerollCount(mw.getRerollCount()+1);;
						
					return null;
				}
				
				@Override
				public void done() {
					
					System.out.println("Animacja zako�czona");
					mw.rollButton.setEnabled(true);
					
					for(JButton d: mw.diceButton) {
						
						d.setEnabled(true);	
					}
				}
			};
			
			worker.execute();
		}
		
		/***************************************************************/
		
		
		/*DICE BUTTON SELECTED******************************************/
		else if(mw.diceButton.contains(event.getSource())) {
			
			JButton b = (JButton) event.getSource();
			
			SwingWorker<Void, Void> worker = new SwingWorker<Void,Void>() {
			
				@Override
				public Void doInBackground() throws Exception {
					
					DiceAnimation select = new DiceAnimation();
					select.addDiceModel((DiceModel)b.getParent());
					
					if(!b.isSelected()) {
						
						b.setSelected(true);
						b.setEnabled(false);
						select.performSelectionAnimation();
						b.setEnabled(true);	
					}
					else {
						
						b.setEnabled(false);
						select.performTurnBackAnimation();
						b.setSelected(false);
						b.setEnabled(true);
					}
						
					return null;
				}
				
				@Override
				public void done() {
					
					System.out.println("Animacja zako�czona!");
				}
			};
			
			worker.execute();
		}
		
		/*********************************************************************/
		
		
		/*ACCEPT ACTION BUTTON************************************************/
		
		else if(event.getSource().equals(mw.acceptActionButton)) {
			
			JButton b = (JButton) event.getSource();
		
			b.setSelected(true);
		}
		
		/*********************************************************************/
		
		
		/*CHECKBOXES**********************************************************/
		
		else if(mw.tableCheckBoxes.contains(event.getSource())) {
			
			JCheckBox selectedBox = (JCheckBox) event.getSource();
			
			mw.checkboxSelected = true;
		
			
		switch(selectedBox.getText()) {
		
				case "Jedynki": {
					
					mw.setCurrentCheckBox(0);
					break;
				}
				case "Dwójki":{
					
					mw.setCurrentCheckBox(1);
					break;
				}
				case "Trójki": {
					
					mw.setCurrentCheckBox(2);
					break;
				}
				case "Czwórki": {
					
					mw.setCurrentCheckBox(3);
					break;
				}
				case "Piątki": {
					
					mw.setCurrentCheckBox(4);
					break;
				}
				case "Szóstki": {
					
					mw.setCurrentCheckBox(5);
					break;
				}
				
				case "Trzy jednakowe": {
					
					mw.setCurrentCheckBox(6);
					break;
				}
				case "Cztery jednakowe": {
					
					mw.setCurrentCheckBox(7);
					break;
				}
				case "Full": {
					
					mw.setCurrentCheckBox(8);
					break;
				}
				case "Mały strit": {
					
					mw.setCurrentCheckBox(9);
					break;
				}
				case "Duży strit": {
					
					mw.setCurrentCheckBox(10);
					break;
				}
				case "Generał": {
					
					mw.setCurrentCheckBox(11);
					break;
				}
				case "Szansa": {
					
					mw.setCurrentCheckBox(12);
				}		
			}
		
		System.out.println(mw.getCurrentCheckBox());
		}
		/*********************************************************************/
		
		
		/*SAVE BUTTON**********************************************************/
		
		else if(event.getSource().equals(mw.saveButton)) {
			
			System.out.println("saved Game");
			
			LoadSave sv = new LoadSave();
			mw.getGc().getPlayers().get(0).setGameSaved(true);
			
			mw.history.append("\n\n GRA ZAPISANA  " + new Date());
			mw.history.append("\n________________________________________________\n\n");
			mw.getGc().getPlayers().get(0).setHistory(mw.history);
			sv.setPlayers(mw.getGc().getPlayers());
			
			try {
				
				sv.saveGame();
				JOptionPane.showMessageDialog(mw.frame, "GRA ZOSTAŁA ZAPISANA");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(null, "ERROR: COULD NOT SAVE CORRECTLY!");

			}
		}
		
		/*********************************************************************/
		
		
		/*HISTORY BUTTON******************************************************/
		
		else if(event.getSource().equals(mw.historyActionButton)) {
			
			setGameHistoryFrameDisplayed(frame.isVisible());
			
			if(!isGameHistoryFrameDisplayed()) {
				
				setGameHistoryFrameDisplayed(gameHistoryFrameDisplayed);
				
				SwingWorker<Void, Void> worker = new SwingWorker<Void,Void>() {

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
					
						frame = new JFrame("Historia rozgrywki");
						frame.setSize(500,800);
						frame.setVisible(true);
						frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						frame.setLocationRelativeTo(null);
						frame.setIconImage(TextureLoader.dice_value_5);
						frame.setResizable(false);
						frame.setAlwaysOnTop(true);
						frame.setLocation(mw.frame.getX() + mw.frame.getWidth(), mw.frame.getY());
						
						frame.validate();
						
						JScrollPane scroll = new JScrollPane(mw.history);
						scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
						
						frame.add(scroll);	
						return null;
					}
				};
				
				worker.execute();
			}
			else {
				
				JOptionPane.showMessageDialog(mw.frame, "Okno historii rozgrywki jest już aktywne");
			}
		}
	}


	public boolean isGameHistoryFrameDisplayed() {
		return gameHistoryFrameDisplayed;
	}


	public void setGameHistoryFrameDisplayed(boolean gameHistoryFrameDisplayed) {
		this.gameHistoryFrameDisplayed = gameHistoryFrameDisplayed;
	}
}
