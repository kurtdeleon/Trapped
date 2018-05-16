package item;

public class Ration extends Item {
	
	public Ration() {
		super(new String[] {"rations", "ration", "food"}, 
			0, 
			true, 
			"", 
			"You ate some RATIONS. You feel fuller.", 
			"You don't have any left. You feel regret for eating them so soon." );
	}
	
	public Ration(int stock) {
		 super( new String[] {"rations", "ration", "food"}, 
			stock, 
			false, 
			"RATIONS (" + stock + ") acquired.", 
			"", 
			"You have already taken the RATIONS earlier." );
	}
	
	public void doEffect() {
		player.Status.AddHealth(80);
	}
}
