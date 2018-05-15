package cave;

public class RegisteredSessionState implements SessionState {
	String playerName;
	
	public RegisteredSessionState(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public void setState(String name) {
		// TODO Auto-generated method stub
		
	}
}
