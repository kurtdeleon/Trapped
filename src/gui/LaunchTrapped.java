package gui;

import javax.swing.JFrame;

public class LaunchTrapped {
	
	public static void main(String[] args) throws Exception {
        JFrame fr = new TrappedFrame();
        fr.setSize(900,500);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setTitle("Trapped 2.0");
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);
    }
}
