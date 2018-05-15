package item;

public class Boat extends Item {
	
	public Boat() {
		super(new String[] {"boat", "vessel", "ship", "watercraft"}, 
				0, 
				true, 
				"", 
				"", 
				"");
	}
	
	public Boat(int stock) {
		super(new String[] {"boat", "vessel", "ship", "watercraft"}, 
				stock, 
				false, 
				"", 
				"", 
				"");
	}
}
