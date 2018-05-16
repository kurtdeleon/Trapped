package player;

public class Status {
	private static int health = 40;
	private static int hunger = 80;
	private static String currentChamber = "chamber.Chamber1";
	
	public static int GetHealth() { return health; }
	public static int GetHunger() { return hunger; }
	public static void SetHealth(int newHealth) { health = newHealth; }
	public static void SetHunger(int newHunger) { hunger = newHunger; }
	
	public static void AddHunger(int num)
	{
		hunger += num;
		if ( hunger > 100 )
		{
			hunger = 100;
		}
	}
	
	public static void RemoveHunger(int num)
	{
		hunger -= num;
		if ( hunger < 0 )
		{
			hunger = 0;
			chamber.GameState.PLAYER_HUNGRY = true;
		}
	}
	
	public static void AddHealth(int num)
	{
		health += num;
		if ( health > 100 )
		{
			health = 100;
		}
	}

	public static void RemoveHealth(int num)
	{
		health -= num;
		if ( health < 0 )
		{
			health = 0;
			chamber.GameState.PLAYER_DEAD = true;
		}
	}
	
	public static String GetCurrentChamber() 
	{
		return currentChamber;
	}
	
	public static void SetCurrentChamber( String currentChamber ) 
	{
		Status.currentChamber = currentChamber;
	}
}
