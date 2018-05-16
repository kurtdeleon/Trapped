package item;

public class Berry extends Item {
	public Berry() {
		super(new String[] {"berry", "berries", "strawberries", "fruit"}, 
			0, 
			true, 
			"", 
			"They taste a bit sweet, but mostly bland.", 
			"You ran out of berries." );
	}
	
	public Berry(int stock) {
		super( new String[] {"berry", "berries", "strawberries", "fruit"}, 
			stock, 
			false, 
			"BERRY (" + stock + ") acquired.", 
			"", 
			"You already picked the berries earlier." );
	}
	
	public void doEffect() {
		player.Status.AddHunger(30);
	}
}
