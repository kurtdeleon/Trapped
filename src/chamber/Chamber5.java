package chamber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import annotation.Chamber;
import annotation.Command;
import annotation.Direction;
import gui.TrappedFrame;
import player.Status;

@Chamber
public class Chamber5 extends BaseChamber implements ChamberBehavior {
	
	private boolean hasAccessed = false;
	private boolean hasPlayed = false;
	
	// mini-game variables
	private int n1 = 0;
	private int n2 = 0;
	private int ans = 0;
	private int timeLeft = 5;
	
	private Timer t;

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
		return super.GetCommands();
	}
	
	@Override
	public List<String> GetRoomItems() {
		return super.GetRoomItems();
	}
	
	@Override
	public List<String> GetInventoryList() {
		return super.GetInventoryList();
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
				TrappedFrame.updateOutput(sw.toString());
				PlayGame();
				return "";
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
	
	public void PlayGame() throws Exception
	{

		Random rand = new Random();
		int questionsLeft = 5;
		
		while ( !hasPlayed )
		{
			//TODO game stuff here
			JOptionPane.showMessageDialog(null, "A genie appears... \nHe now wants you to prove your prowess in solving math problems.", "Poof!", JOptionPane.INFORMATION_MESSAGE);
			JOptionPane.showMessageDialog(null, "You can DIE by playing this game.", "A word of warning", JOptionPane.WARNING_MESSAGE);
			
			while (questionsLeft-- > 0) {
				timeLeft = 5;
				String currStats = String.format("(You currently have %d health, %d hunger)\n", Status.GetHealth(), Status.GetHunger());
				
				t = new Timer(1000, new ActionListener() {
					@Override
			        public void actionPerformed(ActionEvent e) {
						System.out.println(timeLeft + " seconds left");
						if (timeLeft-- <= 0) {
							Status.RemoveHealth(20); Status.RemoveHunger(20);
							JOptionPane.showMessageDialog(null, "Too slow! You lose 20 health, 20 hunger. \nPress Enter key in the input box to proceed.", "Time's up!", JOptionPane.ERROR_MESSAGE);
							t.stop();
						}
					}
				});
				t.start();

				n1 = rand.nextInt(89) + 10;
				n2 = rand.nextInt(9) + 1;
				ans = n1*n2;
				
				String playerAnswer = JOptionPane.showInputDialog(String.format("%sWhat is %d * %d?", currStats, n1, n2));
				t.stop();
				if (timeLeft > 0) {
					try {
						int ansInt = Integer.parseInt(playerAnswer);
						if (ans == ansInt) {
							Status.AddHealth(15); Status.AddHunger(15);
							JOptionPane.showMessageDialog(null, "Correct! You gain 15 health, 15 hunger.", "Whew!", JOptionPane.INFORMATION_MESSAGE);
						} else {
							Status.RemoveHealth(20); Status.RemoveHunger(20);
							JOptionPane.showMessageDialog(null, "Wrong! You lose 20 health, 20 hunger.", "Ouch!", JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e) {
						Status.RemoveHealth(20); Status.RemoveHunger(20);
						JOptionPane.showMessageDialog(null, "Wrong! You lose 20 health, 20 hunger.", "Ouch!", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
				
			
			if (!GameState.PLAYER_DEAD) {
				JOptionPane.showMessageDialog(null, "The genie lets you live another day...", "Congrats, I guess", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "And just like that, the genie sucks out your soul.\n It's game over for you...", "Tough luck!", JOptionPane.WARNING_MESSAGE);
			}
			
			hasPlayed = true; //exit condition
			
		}
		
		/*
		 * This is a time-based game.
		 * A genie appears and demands the player to answer mathematical questions.
		 * Yung medyo madali lang. 2 digit x 1 digit. Give them 5 questions, 5 seconds to answer.
		 * Para may thrill, if correct: +15 health, +15 hunger. if wrong: -20 health, -20 hunger.
		 * They can die from this. :P
		 */
	}

}

