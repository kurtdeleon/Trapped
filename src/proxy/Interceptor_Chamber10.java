package proxy;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import annotation.Interceptor;

@Interceptor(code="chamber10-000001")
public class Interceptor_Chamber10 {
	
	// mini game variables
	private static Timer t;
	private static int timeLeft = 10;
	private static boolean allCorrect = true;
	
	public static boolean canEnter()
	{
		HashMap<String, String> riddles = new HashMap<String, String>();
		riddles.put("Feed me and I will live, but give me a drink and I will die.\n\nWhat am I?", "fire");
		riddles.put("I have hundreds of legs but I can only lean;\nYou make me feel dirty so you feel clean.\n\nWhat am I?", "broom");
		riddles.put("I always run but never walk,\nOften murmur but never talk,\nHave a bed but never sleep,\nHave a mouth but never eat.\n\nWhat am I?", "river");
		riddles.put("You saw me where I never was and where I could not be.\nAnd yet within that very place,\nmy face you do often see.\n\nWhat am I?", "reflection");
		riddles.put("Each morning I appear \nTo lie at your feet, \nAll day I will follow you\nNo matter how fast you run,\nYet I nearly perish \r\nIn the midday sun.\n\nWhat am I?", "shadow");
		
		JOptionPane.showMessageDialog(null, "So near, yet so far. You suddenly come across five riddles to solve.", "But wait!", JOptionPane.WARNING_MESSAGE);
		JOptionPane.showMessageDialog(null, "Answer each riddle correctly within ten seconds and you'll arrive in safety.\nOtherwise, it's game over for you.", "But wait!", JOptionPane.WARNING_MESSAGE);
		
		for (String rid : riddles.keySet()) {
			timeLeft = 10;
			
			t = new Timer(1000, new ActionListener() {
				@Override
		        public void actionPerformed(ActionEvent e) {
					System.out.println(timeLeft + " seconds left");
					if (timeLeft-- <= 0) {
						JOptionPane.showMessageDialog(null, "Too slow! \nPress Enter key in the input box to proceed.", "Time's up!", JOptionPane.ERROR_MESSAGE);
						t.stop();
						allCorrect = false;
					}
				}
			});
			t.start();
			
			String playerAnswer = JOptionPane.showInputDialog(rid);
			String correctAnswer = riddles.get(rid);
			t.stop();
			try {
				boolean isCorrect = playerAnswer.trim().equalsIgnoreCase(correctAnswer);
				if (timeLeft > 0) {
					if (isCorrect) {
						JOptionPane.showMessageDialog(null, "Correct!", "Whew!", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Wrong!", "Ouch!", JOptionPane.ERROR_MESSAGE);
					}
					allCorrect &= isCorrect;	
				}
			} catch (Exception e ) {
				allCorrect = false;
			}
			if (!allCorrect) break;
		}
		return allCorrect;
	}
	
	public static String enterMessage()
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
    	pw.println("You miraculously reach land again after what seemed like forever.");
    	pw.println("While everything's spinning, you are coughing up dirty water.");
    	pw.println("But you're alive. You made it.");
    	pw.println();
    	try {player.Inventory.EmptyBackpack(); } catch (Exception e) {} 
        return sw.toString();
	}
	
	public static String unableToEnterMessage()
	{
		StringWriter sw = new StringWriter();
    	PrintWriter pw = new PrintWriter(sw);
    	
        pw.println("Your life passes by as your vision darkens.");
        pw.println("I guess this is the end...");
        pw.println("GAME OVER.");
    	pw.println();
    	
    	chamber.GameState.PLAYER_DEAD = true;
        return sw.toString();
	}
}
