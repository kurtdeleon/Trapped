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
	
	public void doEffect(Item inventoryItem) {
		player.Status.AddHealth(10);
	}
}
