package chamber;

public interface EnterCondition
{
	public boolean canEnter();
	public String enterMessage();
	public String unableToEnterMessage(); 
}
