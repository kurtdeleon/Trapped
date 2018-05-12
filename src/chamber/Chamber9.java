package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import annotation.Locked;
import player.Item;

@Chamber
@Locked(code="chamber9-000001")
public class Chamber9 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;
	private boolean hasExplored = false;
	
	private Item VINES = new Item( new String[] {"vine", "vines", "tough vines", "rope"}, 
			1, false, "VINES (1) acquired.", "", 
			"Small vines exist where you took the tough ones earlier." );
	
	private Item LEAVES = new Item( new String[] {"leaf", "leaves"}, 
			5, false, "LEAVES (5) acquired.", "", 
			"There are no more leaves." );

	@Direction(direction="west", accessible=true, accessMessage="")
	private Chamber1 west;

	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasAccessed)
		{
			pw.println("You are now in CHAMBER 9.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
	        pw.println("You enter a chamber that's small and compact.");
	        pw.println("It's actually quite cozy in here.");
	        pw.println("It feels like... home.");
	        pw.println();
		}
		
        return sw.toString();
	}

	@Override
	public List<String> GetCommands() {
		List<String> commands = super.GetCommands();
		if ( !VINES.HasStock() && !LEAVES.HasStock() )
		{
			commands.remove("take");
		}
		
		return commands;
	}
	
	@Override
	public List<String> GetRoomItems() {
		return super.GetRoomItems();
	}

	@Override
	public List<String> GetInventoryList() {
		return super.GetInventoryList();
	}

	@Override
	@Command(command="explore")
	public String Explore() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasExplored)
		{
			pw.println("The cool breeze relaxes you.");
			pw.println("Sadly, you find nothing new.");
			pw.println();
		}
		else
		{
			hasExplored = true;
			pw.println("Looking around, you find some hanging vines that look tough.");
	        pw.println("There are also some medium-sized reddish leaves that might be of use.");
			pw.println("Maybe you could use them to treat your wounds?");
			pw.println();
		}
		
		return sw.toString();
	}
	
	@Override
	@Command(command="use")
	public String Use(String item) {
		return super.Use(item);
	}
	
	@Override
	@Command(command="take")
	public String Take(String item) {
		if ( !hasExplored )
		{
			return "You should EXPLORE the chamber first.\n";
		}
		
		if ( VINES.CheckIfItemName(item) && VINES.HasStock() )
		{
			StringWriter sw = new StringWriter();
	    	PrintWriter pw = new PrintWriter(sw);

	    	if ( player.Inventory.SHARP_ROCK.HasStock() )
			{
	    		pw.println("Shouldn't you CUT the vines instead?");
		    	pw.println();
			}
			else
			{
				pw.println("You pull on the vines with your entire weight but they didn't budge.");
		    	pw.println("The vines are too tough to be just pulled from its roots.");
		    	pw.println("Maybe I could CUT these with something.");
		    	pw.println();
			}
	    	
	    	return sw.toString();
		}
		
		return super.Take(item);
	}
	
	@Command(command="cut")
	public String Cut(String item)
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if ( VINES.CheckIfItemName(item) && VINES.HasStock() )
		{
			pw.println("Holding the sharp rock on the duller end, you start cutting the vines.");
			pw.println("After what seems like forever, you finally detached them from their roots.");
			pw.println("These will prove useful in escaping, you think to yourself.");
			pw.println();
			pw.println(super.Take("vines"));
		}
		else if ( VINES.CheckIfItemName(item) && !VINES.HasStock() )
		{
			pw.println(super.Take("vines"));
		}
		else
		{
			pw.println("Doesn't seem like a good idea to cut these.");
		}
		
		pw.println();
		return sw.toString();
	}
	
	@Override
	public String SaveRoomData() {
		return super.SaveRoomData();
	}
}
