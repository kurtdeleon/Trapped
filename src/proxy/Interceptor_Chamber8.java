package proxy;

import java.io.PrintWriter;
import java.io.StringWriter;

import annotation.Interceptor;

@Interceptor(code="chamber8-000001")
public class Interceptor_Chamber8 {
	public static boolean canEnter()
	{
		if ( player.Inventory.BOAT.HasStock() )
		{
			return true;
		}
		return false;
	}
	
	public static String enterMessage()
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	pw.println("You aboard your small, flimsy boat.");
    	pw.println("Here goes nothing...");
    	pw.println();
    	
        return sw.toString();
	}
	
	public static String unableToEnterMessage()
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
        pw.println("It's too dangerous to just swim through the currents.");
        pw.println("You should probably try to get ahold of something you can ride.");
        pw.println("While it's also dangerous, at least you have a better chance of escaping.");
    	pw.println();
        
        return sw.toString();
	}
}
