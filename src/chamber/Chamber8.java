package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import annotation.Locked;

@Chamber
@Locked(code="chamber8-000001")
public class Chamber8 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;

	@Direction(direction="north", accessible=false, accessMessage="I don't think your flimsy boat can handle upward streams.")
	private Chamber4 north;
	@Direction(direction="south", accessible=true, accessMessage="")
	private Chamber10 south;
	
	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	if (hasAccessed)
		{
			pw.println("You are now in CHAMBER 8.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
			pw.println("You enter a small island with pretty much nothing in it.");
		    pw.println("Huh... this kinda seems like the small room before a boss fight.");
		    pw.println("You chuckle to yourself.");
		    pw.println();
		}
    	
        return sw.toString();
	}

	@Override
	@Command(command="explore")
	public String Explore() {
		return "There's not much to explore. It is just a small island, after all.";
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
