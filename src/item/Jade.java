package item;

public class Jade extends Item {
	
	public Jade() {
		super(new String[] {"jade", "jade piece"}, 
			0, 
			true,
			"", 
			"", 
			"" );
	}

	public Jade( int stock )
	{
		super( new String[] {"jade", "jade piece"}, 
			stock, 
			false, 
			"JADE (" + stock + ") acquired.", 
			"", 
			"You have already taken the JADE you found.");
	}
}
