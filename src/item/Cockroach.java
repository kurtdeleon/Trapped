package item;

public class Cockroach extends Item {
	public Cockroach() {
		super(new String[] {"cockroach", "insect"},  
			0, 
			true, 
			"", 
			"You've been told that insects are rich in protein.\nYou feel fuller, but you've probably contracted something.", 
			"It's in your stomach.");
	}
	
	public Cockroach(int stock) {
		super( new String[] {"cockroach", "insect"}, 
			stock, 
			false, 
			"COCKROACH (" + stock + ") acquired.", 
			"", 
			"You have already taken the dead COCKROACH earlier. Why did you take it again?" );
	}
	
	public void doEffect(Item inventoryItem) {
		player.Status.RemoveHealth(50);
		player.Status.AddHunger(30);
	}
}
