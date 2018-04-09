package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import player.Item;

@Chamber
public class Chamber1 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;
	private boolean hasExplored = false;
	
	private Item RATIONS = new Item( new String[] {"rations", "ration", "food"}, 
			2, false, "RATIONS (2) acquired.", "", 
			"You have already taken the RATIONS earlier." );
	private Item BANDAGES = new Item( new String[] {"bandages", "bandage", "medicine"}, 
			1, false, "BANDAGES (1) acquired.", "", 
			"You have already taken the BANDAGES earlier." );
	private Item JADE = new Item( new String[] {"jade", "jade piece"}, 
			1, false, "JADE (1) acquired.", "", 
			"You have already taken the JADE you found.");
	
	@Direction(direction="north", accessible=false, accessMessage="Clearing is too high to reach. Must find another way to get there...")
	private Chamber4 north;
	@Direction(direction="east", accessible=true, accessMessage="")
	private Chamber9 east;
	@Direction(direction="west", accessible=true, accessMessage="")
	private Chamber2 west;

	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasAccessed)
		{
			pw.println("You are now in CHAMBER ONE.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
	        pw.println("You wake up in a dark place with no memories intact.");
	        pw.println("Your head is splitting but you quickly realize that you have to get moving.");
	        pw.println();
	        pw.println("Feeling around in the dark, you stumble upon what feels like a backpack.");
	        pw.println("It's probably yours, you think to yourself.");
	        pw.println("You check inside and you find a flashlight.");
	        pw.println("FLASHLIGHT acquired.");
	        pw.println("Luckily, the flashlight still works.");
	        player.Inventory.HAS_FLASHLIGHT = true;
	        pw.println();
	        pw.println("Maybe you should EXPLORE the chamber and try to find more things.");
	        pw.println();
		}
		
        return sw.toString();
	}

	@Override
	public List<String> GetCommands() {
		List<String> commands = super.GetCommands();
		if ( !RATIONS.HasStock() && !BANDAGES.HasStock() && !JADE.HasStock() )
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
			pw.println("You look around and find more useful things.");
	        pw.println("Near the backpack, you see a weird, glowing piece of jade.");
	        pw.println("It might prove useful in the future, you think to yourself.");
	        pw.println();
			pw.println("Room items have been updated. Use TAKE command to take items.");
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
		if ( hasExplored )
		{
			return super.Take(item);
		}
		return "You should EXPLORE the chamber first.";
	}
}
