package inputmode;

import gui.TrappedFrame;

public class CommandLine implements InputMode {
	
	private TrappedFrame tf;
	
	public CommandLine( TrappedFrame tf ) {
		this.tf = tf;
	}

	@Override
	public void read(String input) {
		try {
			tf.sendInput(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
