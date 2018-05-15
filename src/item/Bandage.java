package item;

public class Bandage extends Item {
	
	public Bandage() {
		super(new String[] {"bandages", "bandage", "medicine"}, 
				0, 
				true, 
				"", 
				"You put bandages on your wounds. You feel slightly better.", 
				"Seems like you've run out of bandages... damn." );
	}
	
	public Bandage ( int stock )
	{
		super(new String[] {"bandages", "bandage", "medicine"}, 
				stock, 
				false, 
				"BANDAGES (" + stock + ") acquired.", 
				"", 
				"You have already taken the BANDAGES earlier." );
	}
	
	public void doEffect() {
		player.Status.AddHunger(80);
	}
}
