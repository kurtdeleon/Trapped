package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;

@Chamber
public class Chamber5 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;
	private boolean hasPlayed = false;

	@Direction(direction="south", accessible=true, accessMessage="")
	private Chamber4 south;
	
	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasAccessed)
		{
			pw.println("You are now in CHAMBER FIVE.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
	        pw.println("You enter a small chamber with a pedestal in the middle.");
	        pw.println("On the pedestal, there is a small jade statue that seems to be missing 2 pieces.");
	        pw.println("You feel compelled to PLACE your jade pieces here.");
	        pw.println();
		}
		
        return sw.toString();
	}

	@Override
	public List<String> GetCommands() {	
		return super.GetCommands();
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
		return "There's not much to explore. It is just a small room, after all.";
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
	
	@Command(command="place")
	public String Place(String item)
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if ( player.Inventory.JADE.CheckIfItemName(item) )
		{
			if ( player.Inventory.JADE.GetStock() >= 2 )
			{
				pw.println("You place the jade pieces into the holes.");
				pw.println("Everything suddenly shakes and the jade statue glows bright.");
				pw.println("You hear a loud voice.");
				PlayGame();
			}
			else
			{
				pw.println("What jade? You don't have any.");
				pw.println();
			}
			
			return sw.toString();
		}
		
		return "It doesn't fit.";
	}
	
	public void PlayGame()
	{
		while ( !hasPlayed )
		{
			//TODO game stuff here
			hasPlayed = true; //exit condition
		}
		
		/*
		 * This is a time-based game.
		 * A genie appears and demands the player to answer mathematical questions.
		 * Yung medyo madali lang. 2 digit x 1 digit. Give them 5 questions, 5 seconds to answer.
		 * Para may thrill, if correct: +15 health, +15 hunger. if wrong: -20 health, -20 hunger.
		 * They can die from this. :P
		 */
	}
}

