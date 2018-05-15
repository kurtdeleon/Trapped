package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import annotation.Locked;

@Chamber
@Locked(code="chamber10-000001")
public class Chamber10 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;
	private boolean hasExplored = false;
	private int hasCrawled = 0;

	@Direction(direction="east", accessible=false, accessMessage="I'm sure you don't wanna go through that again.")
	private Chamber8 east;
	@Direction(direction="west", accessible=true, accessMessage="")
	private Chamber11 west;
	
	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasAccessed)
		{
			pw.println("You are now in CHAMBER 10.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
	        pw.println("Even with your head still hurting, you decide to keep going.");
	        pw.println("You look around groggily and see a faint light to the west, where you're looking at now.");
	        pw.println();
		}
		
        return sw.toString();
	}


	@Override
	@Command(command="explore")
	public String Explore() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	if (hasExplored)
		{
			pw.println("Now's not a good time for that.");
	        pw.println();
		}
		else
		{
			hasExplored = true;
	        pw.println("You seem to have lost your backpack entirely.");
	        pw.println("With no flashlight, you pretty much can't see anything.");
	        pw.println("You're tired and hungry...");
	        pw.println("but you feel hope.");
	        pw.println();
		}
    	
    	return sw.toString();
	}
	
	@Command(command="crawl")
	public String Crawl() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	switch ( hasCrawled )
    	{
    	case 0:
    		pw.println("You crawl...");
    		break;
    	case 1:
    		pw.println("After around 30 minutes of crawling, the faint light has gotten closer.");
    		break;
    	case 2:
    		pw.println("You can make out things around you now.");
    		break;
    	case 3:
    		chamber.GameState.CHAMBER11_OPEN = true;
    		pw.println("Are those spelunkers in the distance?");
    		pw.println("Seems like you made it.");
    		break;
    	default:
    		chamber.GameState.CHAMBER11_OPEN = true;
    		pw.println("You crawl.");
    		break;
    	}
    	
    	hasCrawled++;
    	pw.println();
    	return sw.toString();
	}
	
	@Override
	@Command(command="use")
	public String Use(String item) {
		return "You don't have anything right now.\n";
	}
	
	@Override
	@Command(command="take")
	public String Take(String item) {
		return super.Take(item);
	}
	
	@Override
	public String SaveRoomData() {
		return super.SaveRoomData();
	}
}
