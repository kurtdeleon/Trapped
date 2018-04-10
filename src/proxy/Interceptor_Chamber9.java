package proxy;

import annotation.Interceptor;

@Interceptor(code="chamber9-000001")
public class Interceptor_Chamber9 {
	public static boolean canEnter()
	{
		if ( chamber.GameState.CHAMBER9_OPEN )
		{
			return true;
		}
		return false;
	}
	
	public static String enterMessage()
	{
    	return "Brushing off the dirt from the crumbled wall, you walk through the small entrance.\n";  
	}
	
	public static String unableToEnterMessage()
	{
		return "You can't walk through walls, dummy.\n";  
	}
}
