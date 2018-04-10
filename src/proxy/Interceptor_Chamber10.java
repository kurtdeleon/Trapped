package proxy;

import java.io.PrintWriter;
import java.io.StringWriter;

import annotation.Locked;

@Locked(code="chamber10-000001")
public class Interceptor_Chamber10 {
	public static boolean canEnter()
	{
		while ( true )
		{
			/*
			 * MINIGAME CODE GOES HERE
			 * MINIGAME CODE GOES HERE
			 * MINIGAME CODE GOES HERE
			 * 
			 */
			break;
			//return true; <--- if you win, return true
		}

		return false;
	}
	
	public static String enterMessage()
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	pw.println("You miraculously reach land again after what seemed like forever.");
    	pw.println("While everything's spinning, you are coughing up dirty water.");
    	pw.println("But you're alive. You made it.");
    	pw.println();
    	try {player.Inventory.EmptyBackpack(); } catch (Exception e) {} 
        return sw.toString();
	}
	
	public static String unableToEnterMessage()
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
        pw.println("Your life passes by as your vision darkens.");
        pw.println("I guess this is the end...");
        pw.println("GAME OVER.");
    	pw.println();
    	
    	chamber.GameState.PLAYER_DEAD = true;
        return sw.toString();
	}
}
