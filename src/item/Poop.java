package item;

import java.io.PrintWriter;
import java.io.StringWriter;

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
	
	public void doEffect(Item inventoryItem) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	this.RemoveStock(1);
		pw.println( this.GetUseMessage() );
		GameState.PLAYER_DEAD = true;
	}
}
