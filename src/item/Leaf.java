package item;

public class Leaf extends Item {
	public Leaf() {
		super(new String[] {"leaf", "leaves"}, 
				0, 
				true, 
				"", 
				"You apply the leaves on your wounds. You feel slightly better.", 
				"You don't have extra leaves anymore.");
	}
	
	public Leaf(int stock) {
		super( new String[] {"leaf", "leaves"}, 
			stock, 
			false, 
			"LEAVES (" + stock + ") acquired.", 
			"", 
			"There are no more leaves." );
	}
	
	public void doEffect(Item inventoryItem) {
		player.Status.AddHealth(10);
	}
}
