package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import player.Item;

@Chamber
public class Chamber4 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;
	private boolean hasExplored = false;
	
	private Item BROKEN_BOAT = new Item( new String[] {"broken boat", "broken vessel", "broken ship", "broken watercraft"}, 
			1, false, "BROKEN BOAT (1) acquired.", "", 
			"You have already taken the broken boat." );
	
	private Item WATER = new Item( new String[] {"broken boat", "broken vessel", "broken ship", "broken watercraft"}, 
			1, false, "BROKEN BOAT (1) acquired.", "", 
			"You have already taken the broken boat." );
	
	@Direction(direction="north", accessible=true, accessMessage="")
	private Chamber5 north;
	@Direction(direction="south", accessible=true, accessMessage="")
	private Chamber1 south;
	@Direction(direction="west", accessible=true, accessMessage="")
	private Chamber3 west;
	@Direction(direction="east", accessible=true, accessMessage="")
	private Chamber1 east;
	
	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasAccessed)
		{
			pw.println("You are now in CHAMBER EIGHT.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
	        pw.println("You walk towards a small room that has, oddly, a small stream leading to a hole.");
	        pw.println("The hole seems deeper since you can't really see a light anymore.");
	        pw.println("The sound of splashing water is almost deafening.");
	        pw.println("You're sure it's not a waterfall at least, since its slope is not that steep.");
	        pw.println();
	        pw.println("This isn't my way out... is it?");
	        pw.println("You're kidding.");
	        pw.println();
		}
		
        return sw.toString();
	}

	@Override
	public List<String> GetCommands() {
		List<String> commands = super.GetCommands();
		if ( !BROKEN_BOAT.HasStock() )
		{
			commands.remove("take");
		}
		if ( GameState.CHAMBER8_SHORTCUT_OPEN )
		{
			commands.remove("attach");
		}
		
		return commands;
	}
	
	@Override
	public List<String> GetRoomItems() {
		return super.GetRoomItems();
	}
	
	@Override
	@Command(command="explore")
	public String Explore() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasExplored)
		{
			pw.println("You find nothing new.");
			pw.println();
		}
		else
		{
			hasExplored = true;
			pw.println("Looking around, you see another path leading north.");
	        pw.println("Opposite of it is a cliff, and at the bottom seems to be where you came from.");
			pw.println("You spot a huge, sturdy rock that you could ATTACH a rope to.");
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
		
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if ( WATER.CheckIfItemName(item) )
		{
			if ( player.Inventory.CLAY_POT.HasStock() )
			{
				pw.println("You use the pot to fetch some of the water.");
				pw.println("Doesn't seem drinkable, but it might be of use.");
				pw.println(super.Take(item));
				WATER.AddStock(1);
			}
			else
			{
				pw.println("You need something to store the water in first.");
				pw.println();
			}
			
			return sw.toString();
		}
		
		return super.Take(item);
	}
	
	@Command(command="attach")
	public String Attach(String item)
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if ( player.Inventory.VINES.CheckIfItemName(item) )
		{
			if ( player.Inventory.VINES.HasStock() )
			{
				pw.println("You tie the vine around the rope, and the end barely reaches a height that's safe to drop from.");
				pw.println("Seems like a handy shortcut.");
				pw.println();
			}
			else
			{
				pw.println("You need a rope first.");
				pw.println();
			}
			
			return sw.toString();
		}
		
		return "Attach what?\n";
	}
}