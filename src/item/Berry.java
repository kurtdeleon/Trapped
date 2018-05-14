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
	
	public void doEffect() {
		player.Status.AddHunger(30);
	}
}
