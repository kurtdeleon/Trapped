package chamber;

import java.util.List;

import annotation.Chamber;
import annotation.Locked;

@Chamber
@Locked(code="chamber8-000001")
public class Chamber8 extends BaseChamber implements ChamberBehavior {

	@Override
	public String GetDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> GetCommands() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<String> GetRoomItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String Explore() {
		// TODO Auto-generated method stub
		return null;
	}

}