package item;

public class WhiteMushroom extends Item {
	public WhiteMushroom() {
		super(new String[] {"white mushroom"}, 
			0, 
			true, 
			"", 
			"You decide to eat it after some time of deliberation.\nIt tastes like death. Bad idea.", 
			"You can't find any white mushrooms in your backpack." );
	}
	
	public void doEffect(Item inventoryItem) {
		player.Status.RemoveHealth(30);
		player.Status.RemoveHunger(20);
	}
}
