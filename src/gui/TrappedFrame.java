package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.lang.reflect.*;
import java.util.*;

import cave.CaveMaker;
import chamber.BaseChamber;

public class TrappedFrame extends JFrame {

    JPanel left, center, right;
    
    JList<String> inventoryList, cmdsList, roomItemsList;
    JTextArea output;
    JTextField input;
    JButton submit;
    JTextField hpBar, hungerBar, timerBar;
    JLabel map;
    JLabel locationLabel, inventoryLabel, cmdsLabel, roomItemsLabel;
    
    CaveMaker cave;

    public TrappedFrame() throws Exception {
        locationLabel = new JLabel("You are in Room xxx");
        hpBar = new JTextField("hp bar goes here");
        hungerBar = new JTextField("hunger bar goes here");
        inventoryLabel = new JLabel("Your Inventory");
        inventoryList = new JList<String>(new String[]{"inventory", "goes here"});
        timerBar = new JTextField("timer goes here");
        output = new JTextArea("game output goes here");
        input = new JTextField("input goes here", 50);
        submit = new JButton("Submit");
        cmdsLabel = new JLabel("Available Commands");
        cmdsList = new JList<String>(new String[]{"available","commands","go here"});
        roomItemsLabel = new JLabel("Room Items");
        roomItemsList = new JList<String>(new String[]{"room items","go here"});

        ImageIcon im = new ImageIcon("trapped_map.png");
        map = new JLabel(im);
        
        hpBar.setEditable(false);
        hungerBar.setEditable(false);
        timerBar.setEditable(false);

        submit.addActionListener(new ButtonListener());

        Container ct = this.getContentPane();
        ct.setLayout(new BorderLayout());

        left = new JPanel();
        left.setLayout(new GridBagLayout());
        left.setBorder(new EmptyBorder(0, 0, 0, 5));
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        // c.ipady = 200;
        left.add(locationLabel, c);
        c.gridy = 1;
        left.add(map, c);
        c.ipady = 0;
        c.gridy = 2;
        left.add(hpBar, c);
        c.gridy = 3;
        left.add(hungerBar, c);
        c.gridy = 4;
        // c.weighty = 0.5;
        left.add(inventoryLabel, c);
        c.gridy = 5;
        c.ipadx = 100;
        c.ipady = 120;
        c.weighty = 0.0;
        inventoryList.setBorder(new EmptyBorder(5,5,5,5));
        left.add(new JScrollPane(inventoryList), c);
        ct.add(left, "West");       

        center = new JPanel();
        center.setLayout(new BorderLayout());
        center.add(timerBar, "North");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 0, 10, 10));
        output.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(output, "Center");

        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        bottom.add(input);
        bottom.add(submit);
        mainPanel.add(bottom, "South");

        JPanel right = new JPanel();
        right.setLayout(new GridBagLayout());
        right.setBorder(new EmptyBorder(0, 20, 0, 10));
        GridBagConstraints c2 = new GridBagConstraints();

        c2.gridx = 0;
        c2.gridy = 0;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.ipady = 10;
        right.add(cmdsLabel, c2);
        c2.gridy = 1;
        c2.ipady = 120;
        cmdsList.setBorder(new EmptyBorder(5,5,5,5));
        right.add(new JScrollPane(cmdsList), c2);
        c2.gridy = 2;
        c2.ipady = 10;
        right.add(roomItemsLabel, c2);
        c2.gridy = 3;
        c2.ipady = 120;
        roomItemsList.setBorder(new EmptyBorder(5,5,5,5));
        right.add(new JScrollPane(roomItemsList), c2);
        mainPanel.add(right, "East");

        center.add(mainPanel, "Center");
        ct.add(center, "Center");
        
        
        cave = new CaveMaker();
		cave.Load();
		updateAll(cave);
    }
    
    public void updateAll(CaveMaker cv) throws Exception {
    	updateOutput(cv.PrintDescription());
		Class cm = Class.forName("cave.CaveMaker");
		Field cur = cm.getDeclaredField("currentChamber");
		cur.setAccessible(true);
		BaseChamber bc = (BaseChamber) cur.get(cv);
		updateCmdsList(bc.GetCommands().stream().toArray(String[]::new));
		updateRoomItemsList(bc.GetRoomItems().stream().toArray(String[]::new));
    }

    public void sendInput(String inputCmd) throws Exception {
        // To-do: feed this to CaveMaker.perform or something
//        updateInventoryList(new String[]{"updated", "inventory", "list", "with", "even", "more", "items!!!", "1", "2", "69"});
//        updateTimerBar("time is ticking...");
//        updateOutput("something happened...");
//        updateHpBar("HP: OVER 9000");
//        updateLocationLabel("You are now in room 12");
//        JOptionPane.showMessageDialog(null, "Got this text from the input: " + inputCmd);
    	
    	String inp = input.getText();
    	if ( inp.equalsIgnoreCase("exit") )
		{
			System.out.println("\nTRAPPED is now closing. Thanks for playing.");
			System.out.println("-Kurt de Leon & Brian Guadalupe");
//			break;
		}
		else
		{
			int index = inp.indexOf(' ');
			
			if (index > -1)
			{
				String action = inp.substring( 0, index );
				String subject = inp.substring( index + 1 );
				
				if ( action.equalsIgnoreCase("go") || action.equalsIgnoreCase("move") )
				{
					updateAll(cave);
					updateOutput(cave.Move( subject ));
				}
				else 
				{
					updateAll(cave);
					updateOutput(cave.Perform( action, subject ));
				}
			}
			else
			{
				updateOutput(cave.Perform( inp, null ));
			}
		}
    }

    public void updateTimerBar(String newText) {
        timerBar.setText(newText);
    }
    public void updateHpBar(String newText) {
        hpBar.setText(newText);
    }
    public void updateHungerBar(String newText) {
        hungerBar.setText(newText);
    }
    public void updateOutput(String newText) {
        output.setText(newText);
    }
    public void updateLocationLabel(String newText) {
        locationLabel.setText(newText);
    }

    public void updateInventoryList(String[] items) {
        inventoryList.setListData(items);
    }
    public void updateCmdsList(String[] items) {
        cmdsList.setListData(items);
    }
    public void updateRoomItemsList(String[] items) {
        roomItemsList.setListData(items);
    }

    class ButtonListener implements ActionListener {
        public ButtonListener() {}
        public void actionPerformed(ActionEvent ae) {
            String inp = input.getText();
            try {
				sendInput(inp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
