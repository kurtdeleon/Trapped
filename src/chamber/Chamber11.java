package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import annotation.Locked;

@Chamber
@Locked(code="chamber11-000001")
public class Chamber11 extends BaseChamber implements ChamberBehavior {

	private boolean hasAccessed = false;
	
	@Direction(direction="east", accessible=false, accessMessage="You don't have enough energy to go back.")
	private Chamber10 east;
	
	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	if (hasAccessed)
		{
			pw.println("You are now in CHAMBER 11.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
			pw.println("You crawl towards a group of spelunkers having breakfast.");
		    pw.println("They notice you, and they come running.");
		    pw.println("Thank God.");
		    pw.println("I can finally take a rest from all this bullshit.");
		    pw.println("Why did I end up there anyway...?");
		    pw.println();
		}
		
        return sw.toString();
	}


	@Override
	@Command(command="explore")
	public String Explore() {
		return "You're blacking out...";
	}
	
	@Command(command="rest")
	public String Rest() {
		chamber.GameState.PLAYER_DEAD = true;
		return "You're too tired to think. The only thing you're thinking of is finally escaping.\nYou black out.";
	}
	
	@Override
	@Command(command="use")
	public String Use(String item) {
		return super.Use(item);
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