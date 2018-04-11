package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import player.Item;

@Chamber
public class Chamber6 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;
	private boolean hasExplored = false;
	
	private Item BERRY = new Item( new String[] {"berry", "berries", "strawberries", "fruit"}, 
			3, false, "BERRY (3) acquired.", "", 
			"You already picked the berries earlier." );
	
	@Direction(direction="south", accessible=true, accessMessage="")
	private Chamber3 south;
	@Direction(direction="west", accessible=true, accessMessage="")
	private Chamber7 west;
	
	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasAccessed)
		{
			pw.println("You are now in CHAMBER 6.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
	        pw.println("You enter a small clearing with some plants growing.");
	        pw.println("To the west, you there's a cliff that you can slide down to.");
	        pw.println("It's not too steep, and the ground is slippery enough.");
	        pw.println();
		}
		
        return sw.toString();
	}

	@Override
	public List<String> GetCommands() {
		List<String> commands = super.GetCommands();
		if ( !BERRY.HasStock() )
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
			pw.println("You find nothing new.");
			pw.println("Just a few plants, here and there.");
			pw.println();
		}
		else
		{
			hasExplored = true;
			pw.println("Going through the shrubbery, you see what seems to be berries.");
	        pw.println("They're deep purple in color, and look like they taste sweet.");
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
		return super.Take(item);
	}
}

