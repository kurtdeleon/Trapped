package item;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Cockroach extends Item {
	public Cockroach() {
		super(new String[] {"cockroach", "insect"},  
			0, 
			true, 
			"", 
			"You've been told that insects are rich in protein.\nYou feel fuller, but you've probably contracted something.", 
			"It's in your stomach.");
	}
	
	public void doEffect(Item inventoryItem) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	inventoryItem.RemoveStock(1);
		player.Status.RemoveHealth(50);
		player.Status.AddHunger(30);
		pw.println( inventoryItem.GetUseMessage() );
	}
}
