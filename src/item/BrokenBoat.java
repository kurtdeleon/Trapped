package item;

public class BrokenBoat extends Item {
	
	public BrokenBoat() {
		super(new String[] {"broken boat", "broken vessel", "broken ship", "broken watercraft"}, 
				0, 
				true, 
				"", 
				"", 
				"");
	}

	public BrokenBoat(int stock) {
		super( new String[] {"broken boat", "broken vessel", "broken ship", "broken watercraft"}, 
			stock,
			false, 
			"BROKEN BOAT (" + stock + ") acquired.", 
			"", 
			"You have already taken the broken boat." );
	}
}
