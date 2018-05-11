package item;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Ration extends Item {
	
	public Ration() {
		super(new String[] {"rations", "ration", "food"}, 
			0, 
			true, 
			"", 
			"You ate some RATIONS. You feel fuller.", 
			"You don't have any left. You feel regret for eating them so soon." );
	}
	
	public void doEffect(Item inventoryItem) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		inventoryItem.RemoveStock(1);
		player.Status.AddHealth(80);
		pw.println( inventoryItem.GetUseMessage() );
	}
}
