package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.lang.reflect.*;

import cave.CaveMaker;
import chamber.ChamberBehavior;

public class TrappedFrame extends JFrame {

    JPanel left, center, right;
    
    JList<String> inventoryList, cmdsList, roomItemsList;
    static JTextArea output;
    JTextField input;
    JButton submit;
    JTextField hpBar, hungerBar, timerBar;
    JLabel map;
    JLabel locationLabel, inventoryLabel, cmdsLabel, roomItemsLabel;
    
    CaveMaker cave;

    public TrappedFrame() throws Exception {
        layoutFrame();
        
        cave = new CaveMaker();
		cave.Load();
		updateAll(cave);
    }
    
    public void updateAll(CaveMaker cv) throws Exception {
		Class cm = Class.forName("cave.CaveMaker");
		Field cur = cm.getDeclaredField("currentChamber");
		cur.setAccessible(true);
		ChamberBehavior bc = (ChamberBehavior) cur.get(cv);
		
		String[] room = bc.getClass().getName().split("r");
//		System.out.println(Arrays.toString(room));
		updateLocationLabel("You are now in Chamber " + room[2]);
		
		String desc = bc.GetDescription();
		if (!desc.startsWith("You are now"))
			updateOutput(desc);
		updateCmdsList(bc.GetCommands().stream().toArray(String[]::new));
		updateRoomItemsList(bc.GetRoomItems().stream().toArray(String[]::new));
		updateInventoryList(bc.GetInventoryList().stream().toArray(String[]::new));
		updateHpBar(player.Status.GetHealth());
		updateHungerBar(player.Status.GetHunger());
    }

    public void sendInput(String inputCmd) throws Exception {
    	
    	String inp = input.getText();
    	if ( inp.equalsIgnoreCase("quit") ) {
    		JOptionPane.showMessageDialog(null, "TRAPPED is now closing. Thanks for playing.\n- Kurt de Leon & Brian Guadalupe");
    		System.exit(0);
    	} else if ( inp.equalsIgnoreCase("testsave") ) {
			cave.TestSave();
		} else {
			if (!chamber.GameState.PLAYER_DEAD) {
				int index = inp.indexOf(' ');
				
				if (index > -1)	{
					String action = inp.substring( 0, index );
					String subject = inp.substring( index + 1 );
					
					if ( action.equalsIgnoreCase("go") || action.equalsIgnoreCase("move") )	{
						updateOutput(cave.Move( subject ));
					} 
					else {
						updateOutput(cave.Perform( action, subject ));
					}
				}
				else {
					updateOutput(cave.Perform( inp, null ));
				}
				updateAll(cave);
			}
		}
    }

    public void updateTimerBar(String newText) {
        timerBar.setText(newText);
    }
    public void updateHpBar(int newText) {
        hpBar.setText("HP: " + newText);
    }
    public void updateHungerBar(int newText) {
        hungerBar.setText("Hunger: " + newText);
    }
    public static void updateOutput(String newText) {
    	String old = output.getText();
        output.setText(old + "\n" + newText);
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
    
    public void layoutFrame() {
    	locationLabel = new JLabel("You are now in CHAMBER 1.");
        hpBar = new JTextField("HP:");
        hungerBar = new JTextField("Hunger:");
        inventoryLabel = new JLabel("Your Inventory");
        inventoryList = new JList<String>(new String[]{"inventory", "goes here"});
        timerBar = new JTextField("");
        output = new JTextArea("");
        input = new JTextField("", 50);
        submit = new JButton("Submit");
        cmdsLabel = new JLabel("Available Commands");
        cmdsList = new JList<String>(new String[]{"available","commands","go here"});
        roomItemsLabel = new JLabel("Chamber Items");
        roomItemsList = new JList<String>(new String[]{});

        ImageIcon im = new ImageIcon("trapped_map.png");
        map = new JLabel(im);
        
        hpBar.setEditable(false);
        hungerBar.setEditable(false);
        timerBar.setEditable(false);
        
        input.addKeyListener(new EnterListener());
        submit.addActionListener(new ButtonListener());

        Container ct = this.getContentPane();
        ct.setLayout(new BorderLayout());

        left = new JPanel();
        left.setLayout(new GridBagLayout());
        left.setPreferredSize(new Dimension(200,500));
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
//        c.ipadx = 100;
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
        mainPanel.add(new JScrollPane(output), "Center");

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
        roomItemsList.setPreferredSize(new Dimension(100,120));
        right.add(new JScrollPane(roomItemsList), c2);
        mainPanel.add(right, "East");

        center.add(mainPanel, "Center");
        ct.add(center, "Center");

        JOptionPane.showMessageDialog(null, "WELCOME TO TRAPPED.\nThis is a text-based game.\nPlease enjoy.");
    }
    
    class EnterListener implements KeyListener {
    	
    	@Override
    	public void keyPressed(KeyEvent ke) {
    		if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
    			submit.doClick();
    		}
    	}

		@Override
		public void keyReleased(KeyEvent ke) {}

		@Override
		public void keyTyped(KeyEvent ke) {}
    }

    class ButtonListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            String inp = input.getText();
            try {
				sendInput(inp);
				input.setText("");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
}
