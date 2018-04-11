package chamber;

import java.util.Collection;
import java.util.List;

public interface ChamberBehavior {
	
	public String GetDescription();
	public List<String> GetCommands();
	public List<String> GetRoomItems();
	public List<String> GetInventoryList();
	public String Explore();
	public String Use(String item);
	public String Take(String item);

}
