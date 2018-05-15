package item;

public class Vine extends Item {

	public Vine() {
		super( new String[] {"vine", "vines", "tough vines", "rope"}, 
				0, 
				true, 
				"", 
				"", 
				"" );
	}
	
	public Vine( int stock )
	{
		super( new String[] {"vine", "vines", "tough vines", "rope"}, 
			stock, 
			false, 
			"VINES (" + stock + ") acquired.", 
			"", 
			"Small vines exist where you took the tough ones earlier." );
	}

}
