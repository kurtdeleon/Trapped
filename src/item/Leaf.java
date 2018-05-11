package item;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Leaf extends Item {
	public Leaf() {
		super(new String[] {"leaf", "leaves"}, 
				0, 
				true, 
				"", 
				"You apply the leaves on your wounds. You feel slightly better.", 
				"You don't have extra leaves anymore.");
	}
	
	public void doEffect(Item inventoryItem) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	inventoryItem.RemoveStock(1);
		player.Status.AddHealth(10);
		pw.println( inventoryItem.GetUseMessage() );
	}
}
