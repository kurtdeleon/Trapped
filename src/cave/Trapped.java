package cave;

import java.util.Scanner;
import cave.CaveMaker;

public class Trapped {
	private static Scanner scanner;

	public static void main(String[] args) throws Exception
	{
		System.out.println("/////////////////////////////////////////////////////\n");
		System.out.println("WELCOME TO TRAPPED.");
		System.out.println("This is a text-based game.");
		System.out.println("Please enjoy.\n");
		System.out.println("/////////////////////////////////////////////////////\n");
		
		/* Create Cave object */
		CaveMaker cave = new CaveMaker();
		cave.Load();
		
		scanner = new Scanner(System.in);
		
		while ( !chamber.GameState.PLAYER_DEAD )
		{
			System.out.println("What do you want do?");
			String input = scanner.nextLine();
			
			if ( input.equalsIgnoreCase("exit") )
			{
				System.out.println("\nTRAPPED is now closing. Thanks for playing.");
				System.out.println("-Kurt de Leon & Brian Guadalupe");
				break;
			}
			else
			{
				int index = input.indexOf(' ');
				
				if (index > -1)
				{
					String action = input.substring( 0, index );
					String subject = input.substring( index + 1 );
					
					if ( action.equalsIgnoreCase("go") || action.equalsIgnoreCase("move") )
					{
						cave.Move( subject );
					}
					else 
					{
						cave.Perform( action, subject );
					}
				}
				else
				{
					cave.Perform( input, null );
				}
			}
		}
	}
}

