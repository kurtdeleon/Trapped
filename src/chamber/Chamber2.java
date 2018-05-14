package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import item.Item;

@Chamber
public class Chamber2 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;
	private boolean hasExplored = false;
	private boolean hasInvestigated = false;
	
	private Item VINES = new Item( new String[] {"vine", "vines", "tough vines", "rope"}, 
			3, false, "VINES (3) acquired.", "", 
			"Small vines exist where you took the tough ones earlier." );
	
	private Item WHITE_MUSHROOM = new Item( new String[] {"white mushroom"}, 
			1, false, "WHITE MUSHROOM (1) acquired.", "", 
			"You have already put the white mushrooms in your backpack." );

	@Direction(direction="north", accessible=true, accessMessage="")
	private Chamber3 north;
	@Direction(direction="south", accessible=false, accessMessage="Huge rocks block the path. You hear water running somewhere...")
	private Chamber11 south;
	@Direction(direction="west", accessible=true, accessMessage="")
	private Chamber7 west;
	@Direction(direction="east", accessible=true, accessMessage="")
	private Chamber1 east;
	
	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasAccessed)
		{
			pw.println("You are now in CHAMBER 2.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
	        pw.println("You enter a chamber that feels cool and slightly damp.");
	        pw.println("Drowsiness sets in even though you just woke up.");
	        pw.println("The sound of huge rocks falling awakens your senses.");
	        pw.println("What the hell just happened?");
	        pw.println();
	        pw.println("You decide to INVESTIGATE the cause of the noise.");
	        pw.println();
		}
		
        return sw.toString();
	}

	@Override
	public List<String> GetCommands() {
		List<String> commands = super.GetCommands();
		if ( !VINES.HasStock() && !WHITE_MUSHROOM.HasStock() )
		{
			commands.remove("take");
		}
		if ( !VINES.HasStock() && hasExplored )
		{
			commands.remove("cut");
		}
		
		return commands;
	}
	
	@Command(command="investigate")
	public String Investigate()
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasInvestigated)
		{
			pw.println("The cool breeze relaxes you.");
			pw.println("Sadly, you find nothing new.");
			pw.println();
		}
		else
		{
			hasInvestigated = true;
			pw.println("You try to figure out where the deafening sound came from.");
	        pw.println("A huge pile of rocks cover what seems to be a path to somewhere.");
	        pw.println("Some light peeks through the gaps in the pile of rubble.");
	        pw.println("You try to get nearer and you hear the sound of running water.");
	        pw.println();
			pw.println("Huh... weird. The pile won't budge. You decide to find another way around.");
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
			pw.println("The cool breeze relaxes you.");
			pw.println("Sadly, you find nothing new.");
			pw.println();
		}
		else
		{
			hasExplored = true;
			pw.println("Looking around, you find some hanging vines that look tough.");
	        pw.println("A few meters from the vines, you see a white mushroom that looks quite... holy.");
			pw.println("Its extreme whiteness captivates you.");
			pw.println();
			pw.println("Up north, you see a small cliff where some rocks are jutting forward.");
			pw.println("Maybe it's time to test your rock-climbing skills.");
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
	    		pw.println("You figure it would be best to CUT the vines with something.");
		    	pw.println("Hmmm... the rock I got earlier seems sharp enough.");
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
			if ( player.Inventory.SHARP_ROCK.HasStock() )
			{
				pw.println("Holding the sharp rock on the duller end, you start cutting the vines.");
				pw.println("After what seems like forever, you finally detached them from their roots.");
				pw.println("These will prove useful in escaping, you think to yourself.");
				pw.println();
				pw.println(super.Take("vines"));
				pw.println();
			}
			else
			{
				pw.println("Cut these with what, your teeth?");
				pw.println();
			}
			
			return sw.toString();
		}
		else if ( VINES.CheckIfItemName(item) && VINES.HasStock() )
		{
			return super.Take("vines") + "\n";
		}
		
		return "Doesn't seem like a good idea to cut these.\n";
	}
}
