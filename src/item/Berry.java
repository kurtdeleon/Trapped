package item;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Berry extends Item {
	public Berry() {
		super(new String[] {"berry", "berries", "strawberries", "fruit"}, 
			0, 
			true, 
			"", 
			"They taste a bit sweet, but mostly bland.", 
			"You ran out of berries." );
	}
	
	public void doEffect(Item inventoryItem) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	inventoryItem.RemoveStock(1);
		player.Status.AddHunger(30);
		pw.println( inventoryItem.GetUseMessage() );
	}
}
