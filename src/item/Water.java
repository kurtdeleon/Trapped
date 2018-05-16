package item;

public class Water extends Item {
	
	public Water() {
		super(new String[] {"water", "liquid"}, 
				0, 
				true, 
				"", 
				"", 
				"");
	}

	public Water(int stock) {
		super( new String[]  {"water", "liquid"}, 
				stock, 
				false, 
				"WATER (" + stock + ") acquired.", 
				"", 
				"You have already taken the broken boat." );
	}
}
