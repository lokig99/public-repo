package GAME.logic;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import GAME.players.HumanPlayer;
import GAME.players.Player;

class LogicTest {

	Player player;
	
	@BeforeEach
	void setUp() throws Exception {
		
		player = new HumanPlayer("test");
	}

	@Test
	void testMalyStrit() {
		
	
		//sprawdzam malego streeta
		int result = 30;
		
		int[] T00 = {2,5,4,3,2};
		player.setCurrentDices(T00);
		assertEquals(result, Logic.TablePoints(player, 9));
		
		int[] T01 = {1,2,3,4,2};
		player.setCurrentDices(T01);
		assertEquals(result, Logic.TablePoints(player, 9));
		
		int[] T02 = {6,5,4,3,1};
		player.setCurrentDices(T02);
		assertEquals(result, Logic.TablePoints(player, 9));
		
		int[] T03 = {6,4,3,5,1};
		player.setCurrentDices(T03);
		assertEquals(result, Logic.TablePoints(player, 9));
	}
	
	
	@Test
	void testDuzyStrit() {
		
		int result = 40;
		
		int[] T00 = {1,5,4,3,2};
		player.setCurrentDices(T00);
		assertEquals(result, Logic.TablePoints(player, 10));
		
		int[] T01 = {1,2,3,4,5};
		player.setCurrentDices(T01);
		assertEquals(result, Logic.TablePoints(player, 10));
		
		int[] T02 = {6,5,4,3,2};
		player.setCurrentDices(T02);
		assertEquals(result, Logic.TablePoints(player, 10));
		
		int[] T03 = {6,4,3,5,2};
		player.setCurrentDices(T03);
		assertEquals(result, Logic.TablePoints(player, 10));
		
		
		
	}
	
	@Test
	void testFull() {
		
		int result = 25;
		
		int[] T00 = {1,2,1,1,2};
		player.setCurrentDices(T00);
		assertEquals(result, Logic.TablePoints(player, 8));
		
		int[] T01 = {4,4,4,2,2};
		player.setCurrentDices(T01);
		assertEquals(result, Logic.TablePoints(player, 8));
		
		int[] T02 = {1,6,1,1,6};
		player.setCurrentDices(T02);
		assertEquals(result, Logic.TablePoints(player, 8));
		
		int[] T03 = {3,4,3,4,3};
		player.setCurrentDices(T03);
		assertEquals(result, Logic.TablePoints(player, 8));
	}
	
	
	@Test
	void testGeneral() {
		
		int result = 50;
		
		int[] T00 = {1,1,1,1,1};
		player.setCurrentDices(T00);
		assertEquals(result, Logic.TablePoints(player, 11));
		
		int[] T01 = {2,2,2,2,2};
		player.setCurrentDices(T01);
		assertEquals(result, Logic.TablePoints(player, 11));
		
		int[] T02 = {4,4,4,4,4};
		player.setCurrentDices(T02);
		assertEquals(result, Logic.TablePoints(player, 11));
		
		int[] T03 = {5,5,5,5,5};
		player.setCurrentDices(T03);
		assertEquals(result, Logic.TablePoints(player, 11));
	}
	
	
	@Test
	void test3Jednakowe() {
		
		int result = 8;
		
		int[] T00 = {2,1,2,1,2};
		player.setCurrentDices(T00);
		assertEquals(result, Logic.TablePoints(player, 6));
		
		result = 16;
	
		int[] T01 = {2,4,6,2,2};
		player.setCurrentDices(T01);
		assertEquals(result, Logic.TablePoints(player, 6));
		
		result = 16;
		
		int[] T02 = {4,3,4,1,4};
		player.setCurrentDices(T02);
		assertEquals(result, Logic.TablePoints(player, 6));
		
		result = 23;
		
		int[] T03 = {5,5,5,2,6};
		player.setCurrentDices(T03);
		assertEquals(result, Logic.TablePoints(player, 6));
	}
}
