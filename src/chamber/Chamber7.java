package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import item.ClayPot;
import item.Item;
import item.Jade;
import item.SharpRock;

@Chamber
public class Chamber7 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;
	private boolean hasExplored = false;
	
	private Item CLAY_POT = new ClayPot(1);
	private Item JADE = new Jade(1);
	private Item SHARP_ROCK = new SharpRock(1);
	
	@Direction(direction="north", accessible=false, accessMessage="Side of the cliff is too slippery to climb on to.\nMust find another way to get there...")
	private Chamber6 north;
	@Direction(direction="east", accessible=true, accessMessage="")
	private Chamber2 east;

	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasAccessed)
		{
			pw.println("You are now in CHAMBER 7.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
	        pw.println("You enter a room that has a huge clearing above.");
	        pw.println("At the north-east portion of the roof, there seems to be a small chamber.");
	        pw.println("The side of the cliff is slanted in a way that you can slide down, but you cannot climb up.");
	        pw.println("Plus, it's too slippery.");
	        pw.println();
	        pw.println("Some greenery peeks through the edges of the chamber above.");
	        pw.println();
		}
		
        return sw.toString();
	}

	@Override
	public List<String> GetCommands() {
		List<String> commands = super.GetCommands();
		if ( !CLAY_POT.HasStock() && !JADE.HasStock() && !SHARP_ROCK.HasStock() )
		{
			commands.remove("take");
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
			pw.println("You stumble upon a clay pot that's surprisingly well-made.");
	        pw.println("Looking inside, you find a small jade piece.");
			pw.println();
			pw.println("You walk forward a bit more and accidentally trip on something.");
			pw.println("Agh, it was a SHARP ROCK!");
			pw.println("You now have a medium-sized cut near your ankle.");
			pw.println("You took a bit of damage there.");
			player.Status.RemoveHealth(15);
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
	
	@Override
	public String SaveRoomData() {
		return super.SaveRoomData();
	}
}
