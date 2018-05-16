package item;

public class ClayPot extends Item {
	
	public ClayPot() {
		super(new String[] {"clay pot", "pot", "pail", "container"}, 
				0, 
				true, 
				"", 
				"", 
				"");
	}

	public ClayPot(int stock) {
		super( new String[] {"clay pot", "pot", "pail", "container"}, 
			stock, 
			false, 
			"CLAY POT (" + stock + ") acquired.", 
			"", 
			"You took the clay pot earlier, remember?" );
	}
}
