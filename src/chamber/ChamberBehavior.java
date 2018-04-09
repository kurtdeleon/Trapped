package chamber;

import java.util.List;

public interface ChamberBehavior {
	
	public String GetDescription();
	public List<String> GetCommands();
	public List<String> GetRoomItems();
	public String Explore();
	public String Use(String item);
	public String Take(String item);

}
