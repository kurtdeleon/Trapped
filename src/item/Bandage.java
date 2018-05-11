package item;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Bandage extends Item {
	
	public Bandage() {
		super(new String[] {"bandages", "bandage", "medicine"}, 
				0, 
				true, 
				"", 
				"You put bandages on your wounds. You feel slightly better.", 
				"Seems like you've run out of bandages... damn." );
	}
	
	public void doEffect(Item inventoryItem) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	inventoryItem.RemoveStock(1);
		player.Status.AddHunger(80);
		pw.println( inventoryItem.GetUseMessage() );
	}
}
