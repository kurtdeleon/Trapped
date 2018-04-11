package proxy;

import annotation.Chamber;
import annotation.Interceptor;

@Chamber
@Interceptor(code="chamber11-000001")
public class Interceptor_Chamber11 {
	public static boolean canEnter()
	{
		if ( chamber.GameState.CHAMBER11_OPEN )
		{
			return true;
		}
		return false;
	}
	
	public static String enterMessage()
	{
    	return "You seem to have made it.";  
	}
	
	public static String unableToEnterMessage()
	{
		return "You're too far, and too tired. Get closer by CRAWLing.";
	}
}
