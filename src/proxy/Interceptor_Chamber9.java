package proxy;

import java.io.PrintWriter;
import java.io.StringWriter;

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
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	pw.println("On the east of where you are, there seems to be a wall that looks brittle.");
    	pw.println("You knock on it and it seems hollow... like there's something beyond it.");
    	pw.println("You try breaking through it but it didn't budge at all.");
    	pw.println("Hm... maybe you could soften it with something?");
    	pw.println();
    	
        return sw.toString();
	}
}
