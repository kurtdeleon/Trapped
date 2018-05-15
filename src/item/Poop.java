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
	
	public Poop(int stock) {
		super( new String[] {"poop", "shit", "feces"}, 
			stock, 
			false,
			"AAAaaaAAAAaaA\nPOOP (" + stock + ") acquired.",
			"", 
			"WHY DID YOU EVEN TAKE IT" );
	}
	
	public void doEffect() {
		GameState.PLAYER_DEAD = true;
	}
}
