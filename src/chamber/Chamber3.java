package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import player.Item;

@Chamber
public class Chamber3 extends BaseChamber implements ChamberBehavior {
	private boolean hasAccessed = false;
	private boolean hasExplored = false;
	private boolean hasUsedStickySubstance = false;
	private int hasTriedToTakePoop = 0;
	private boolean hasTriedThrowing = false;
	
	private Item COCKROACH = new Item( new String[] {"cockroach", "insect"}, 
			1, false, "COCKROACH (1) acquired.", "", 
			"You have already taken the dead COCKROACH earlier. Why did you take it again?" );
	
	private Item POOP = new Item( new String[] {"poop", "shit", "feces"}, 
			1, false, "AAAaaaAAAAaaA\nPOOP (1) acquired.", "", 
			"WHY DID YOU EVEN TAKE IT" );
	
	@Direction(direction="north", accessible=true, accessMessage="")
	private Chamber6 north;
	@Direction(direction="east", accessible=true, accessMessage="")
	private Chamber4 east;
	@Direction(direction="south", accessible=false, accessMessage="You're too scared to go down the cliff again.\nYou decide to find another way down.")
	private Chamber2 south;

	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasAccessed)
		{
			pw.println("You are now in CHAMBER 3.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
	        pw.println("You feel tired from all the rock-climbing, but you made it.");
	        pw.println();
	        pw.println("After a while, you notice that you feel sick to your stomach.");
	        pw.println("Is the air you're taking in poisonous?");
	        pw.println("Seems like it, because it has a purple tint to it.");
	        pw.println("It might be a good idea to move on to other chambers as quickly as possible.");
	        pw.println();
		}
		
        return sw.toString();
	}

	@Override
	public List<String> GetCommands() {
		List<String> commands = super.GetCommands();
		if ( !COCKROACH.HasStock() && !POOP.HasStock() )
		{
			commands.remove("take");
		}
		if ( hasUsedStickySubstance )
		{
			commands.remove("fix");
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
			pw.println();
		}
		else
		{
			hasExplored = true;
			pw.println("You look around and find a small hole where the gas is coming from.");
	        pw.println("Peering into the hole, there's some sort of opaque substance that seems sticky.");
	        pw.println("Kinda smells like rotten eggs.");
	        pw.println("Is that coming from the hole, or the huge, dead cockroach near it?");
	        pw.println("Truly one of life's greatest mysteries.");
			pw.println();
			pw.println("To the north, you also spot a hole that seems to go upwards.");
			pw.println("It's too high to climb into, but you see a sharp rock jutting out of the edge.");
			pw.println("Maybe you could throw something like a rope there, cowboy-style?");
			pw.println();
		}
		
		return sw.toString();
	}
	
	@Override
	@Command(command="use")
	public String Use(String item) {
		return super.Use(item);
	}
	
	@Command(command="throw")
	public String Throw(String item) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if ( player.Inventory.VINES.CheckIfItemName(item) )
		{
			if ( player.Inventory.VINES.HasStock() && !GameState.CHAMBER6_OPEN )
			{
				if ( !hasTriedThrowing )
				{
					pw.println("You tie the vines to make a makeshift lasso.");
					pw.println("Feeling like a cowboy, you throw it.");
				}
				else
				{
					pw.println("You try again.");
				}
				
				pw.println("...");
				
				Random rand = new Random();
				if ( (rand.nextInt(100) + 1) > 80 )
				{
					GameState.CHAMBER6_OPEN = true;
					pw.println("YOU DID IT!");
					pw.println("Jumping for joy, you did not notice that there was poop in front of you.");
					pw.println("You slipped on it, but there's still a good chunk of it.");
				}
				else
				{
					pw.println("You failed. You're not really cut out for this, huh?");
					pw.println("Try again.");
				}
			}
			else if ( player.Inventory.VINES.HasStock() && GameState.CHAMBER6_OPEN )
			{
				pw.println("There's already a vine hanging from the hole. No need for that.");
			}
			else
			{
				pw.println("You know the vines from earlier can be used, but you don't have any.");
			}
		}
		else
		{
			pw.println("Throw what?");
		}
		
		pw.println();
		
		return sw.toString();
	}
	
	@Command(command="fix")
	public String Fix(String item) {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if ( player.Inventory.BROKEN_BOAT.CheckIfItemName(item) )
		{
			if ( player.Inventory.BROKEN_BOAT.HasStock() )
			{
				pw.println("You get some of the stinky substance and put it where the hole in the boat is.");
				pw.println("It immediately hardens, which surprises you.");
				pw.println("What the hell is this thing?");
				pw.println();
				pw.println("You knock on the hardened part and it feels like it can easily hold your weight.");
				pw.println("You now have a fixed, usable boat.");
				player.Inventory.BROKEN_BOAT.RemoveStock(1);
				player.Inventory.BOAT.AddStock(1);
			}
			else
			{
				pw.println("You've already fixed your boat.");
			}
		}
		else
		{
			pw.println("There's nothing to fix here.");
		}
		
		pw.println();
		
		return sw.toString();
	}
	
	@Override
	@Command(command="take")
	public String Take(String item) {
		if ( !hasExplored )
		{
			return "You should EXPLORE the chamber first.\n";
		}
		
		if ( POOP.CheckIfItemName(item) && GameState.CHAMBER6_OPEN )
		{
	    	switch ( hasTriedToTakePoop )
	    	{
	    	case 0:
	    		return "What the hell did you just think?\nWhy do you think taking this poop is a good idea?";
	    	case 1:
	    		return "Dude, stop. Seriously.";
	    	case 2:
	    		return "What is wrong with you!?";
	    	case 3:
	    		return "...please stop...";
	    	}
	    	hasTriedToTakePoop++;
		}

		return super.Take(item);
	}
}
