package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.List;
import java.util.Random;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;

import player.Status;

@Chamber
public class Chamber5 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;
	private boolean hasPlayed = false;
	private boolean gameStarted = false;
	private boolean genieSummoned = false;
	
	// mini-game variables
	private Random rand = new Random();
	private int n1 = 0;
	private int n2 = 0;
	private int ans = 0;
	private int count = 0;

	@Direction(direction="south", accessible=true, accessMessage="")
	private Chamber4 south;
	
	@Override
	public String GetDescription() {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if (hasAccessed)
		{
			pw.println("You are now in CHAMBER 5.");
	        pw.println();
		}
		else
		{
			hasAccessed = true;
	        pw.println("You enter a small chamber with a pedestal in the middle.");
	        pw.println("On the pedestal, there is a small jade statue that seems to be missing 2 pieces.");
	        pw.println("You feel compelled to PLACE your jade pieces here.");
	        pw.println();
		}
		
        return sw.toString();
	}

	@Override
	public List<String> GetCommands() {
		List<String> commands = super.GetCommands();
		commands.remove("follow");
		commands.remove("ask");
		commands.remove("answer");
		if (genieSummoned) commands.add("follow");
		if (gameStarted) {
			commands.add("ask");
			commands.add("answer");
		}
		if (hasPlayed) {
			commands.remove("follow");
			commands.remove("ask");
			commands.remove("answer");
		}
		
		return commands;
	}
	
	@Override
	@Command(command="explore")
	public String Explore() {
		return "There's not much to explore. It is just a small room, after all.";
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
	
	@Command(command="place")
	public String Place(String item) throws Exception
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
		if ( player.Inventory.JADE.CheckIfItemName(item) )
		{
			if ( player.Inventory.JADE.GetStock() >= 2 )
			{
				pw.println("You place the jade pieces into the holes.");
				pw.println("Everything suddenly shakes and the jade statue glows bright.");
				pw.println("You hear a loud voice.");
				pw.println("You want to FOLLOW where the voice comes from...");
				genieSummoned = true;
			}
			else
			{
				pw.println("What jade? You don't have any.");
				pw.println();
			}
			
			return sw.toString();
		}
		
		return "It doesn't fit.";
	}
	
	@Command(command="follow")
	public String follow() throws Exception {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	if (!genieSummoned) {
    		return "Follow what?";
    	} else {
    		pw.println("Poof! A genie appears...");
    		pw.println("He now wants you to prove your prowess in solving math problems.");
    		pw.println("A word of warning though: You can *die* by playing this game. Good luck!");
    		pw.println("You may ASK for the first problem.");
    		gameStarted = true;
    		generateQuestion();
    	}
    	return sw.toString();
	}
	
	@Command(command="ask")
	public String ask() throws Exception {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	if (!gameStarted) {
    		return "Huh?";
    	} else {
    		if (count > 5) {
    			if (Status.GetHealth() > 0) {
    				pw.println("Congrats, I guess.");
    				pw.println("The genie lets you live another day...");
    			} else {
    				pw.println("Tough luck!");
    				pw.println("And just like that, the genie sucks out your soul. It's game over for you...");
    				GameState.PLAYER_DEAD = true;
    			}
    			hasPlayed = true;
    		} else {
    			pw.println("Here goes item " + count + ": " + String.format("What is %d * %d?", n1, n2));
    			pw.println("Supply your ANSWER by saying: 'answer <number>'");
    		}
    	}
    	return sw.toString();
	}
	
	@Command(command="answer") 
	public String answer(String playerAnswer) throws Exception {
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	if (!gameStarted) {
    		return "What's that?";
    	} else {
    		try {
    			int ansInt = Integer.parseInt(playerAnswer);
    			if (ans == ansInt) {
    				Status.AddHealth(15); Status.AddHunger(15);
    				pw.println("Correct! You gain 15 health, 15 hunger.");
    			} else {
    				Status.RemoveHealth(20); Status.RemoveHunger(20);
    				pw.println("Wrong! You lose 20 health, 20 hunger.");
    			}
    		} catch (Exception e) {
    			Status.RemoveHealth(20); Status.RemoveHunger(20);
    			pw.println("Wrong! You lose 20 health, 20 hunger.");
    		}
    		if (count < 5)
    			pw.println("You may ASK for the "+(count == 4 ? "last" : "next")+" problem.");
    		else
    			pw.println("You may now ASK the genie about your fate.");
    		GameState.PLAYER_DEAD = false; // delay their deaths first
    		generateQuestion();
    	}
    	
    	return sw.toString();
	}
	
	public void generateQuestion() {
		n1 = rand.nextInt(89) + 10;
		n2 = rand.nextInt(9) + 1;
		ans = n1*n2;
		count++;
	}
	
	@Override
	public String SaveRoomData() {
		return super.SaveRoomData();
	}
}