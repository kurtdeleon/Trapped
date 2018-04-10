package chamber;

import gui.ChamberEventListener;

public class ChamberEvent {
	private String eventType, eventContent;
	
	public ChamberEvent(String et, String ec) {
		eventType = et;
		eventContent = ec;
	}
	
	public void notify(ChamberEventListener lis) {
		lis.update(this);
	}
	
}
