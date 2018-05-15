package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import item.Bandage;
import item.Item;
import item.Jade;
import item.Ration;

@Chamber
public class Chamber1 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;
	private boolean hasExplored = false;
	private int timesPlayerPouredWater = 0;
	
	private Item RATIONS = new Ration(1);
	private Item BANDAGES = new Bandage(1);
	private Item JADE = new Jade(1);
	
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
			pw.println("You are now in CHAMBER 1.");
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
		if ( GameState.CHAMBER9_OPEN )
		{
			commands.remove("pour");
		}
		
		return commands;
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
	
	@Command(command="pour")
	public String Pour(String item)
	{
		if ( !hasExplored )
		{
			return "Go explore first!";
		}
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if ( player.Inventory.WATER.CheckIfItemName(item) && !GameState.CHAMBER9_OPEN )
		{
			if ( player.Inventory.WATER.HasStock() )
			{
				switch ( timesPlayerPouredWater )
		    	{
		    	case 0:
		    		pw.println("Looks like it's kinda working. Might need a few more times for it to break.");
		    		break;
		    	case 1:
		    		pw.println("Getting there... pour more water into it!");
		    		break;
		    	case 2:
		    		pw.println("It's crumbling! Just a little more!");
		    		break;
		    	case 3:
		    		GameState.CHAMBER9_OPEN = true;
		    	}
		    	timesPlayerPouredWater++;
		    	player.Inventory.WATER.RemoveStock(1);
			}
			else
			{
				pw.println("Doesn't seem like you have anything to pour on the wall.");
			}
		}
		else if ( player.Inventory.WATER.CheckIfItemName(item) && GameState.CHAMBER9_OPEN )
		{
			if ( player.Inventory.WATER.HasStock() )
			{
				pw.println("You pour water but it's already open. So nothing really happens.");
				pw.println("You just made a mess, really.");
			}
			else
			{
				pw.println("The chamber's already open.");
				pw.println("Plus, you don't really have anything to pour on the wall.");
			}
		}
		else
		{
			pw.println("Pour what?");
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
	
	@Override
	public String SaveRoomData() {
		return super.SaveRoomData();
	}
}
