package item;

import chamber.GameState;

public class Poop extends Item {
	public Poop() {
		super(new String[] {"poop", "shit", "feces"}, 
				0, 
				true, 
				"", 
				"Not sure how you managed to take it... but you decide to eat it.\nGood job, dummy. You died.", 
				"It's in your stomach.");
	}
	
	public void doEffect() {
		GameState.PLAYER_DEAD = true;
	}
}
