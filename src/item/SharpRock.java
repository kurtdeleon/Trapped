package item;

public class SharpRock extends Item {

	public SharpRock() {
		super(new String[] {"sharp rock", "sharp", "rock", "knife"}, 
				0, 
				true, 
				"", 
				"", 
				"");
	}
	
	public SharpRock(int stock) {
		super( new String[] {"sharp rock", "sharp", "rock", "knife"}, 
			stock, 
			false, 
			"SHARP ROCK (" + stock + ") acquired", 
			"",
			"The rest of the rocks here aren't sharp at all. You decide to ignore them.");
	}
}
