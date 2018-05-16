package inputmode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import gui.TrappedFrame;

public class ReadFromFile implements InputMode {

	private TrappedFrame tf;
	
	public ReadFromFile( TrappedFrame tf ) {
		this.tf = tf;
	}

	@Override
	public void read(String input) {
		File runFile = new File("runfiles/" + input);
		Scanner sc = null;
		try {
			sc = new Scanner( runFile );
			while ( sc.hasNext() ) {
				String command = sc.nextLine();
				System.out.println(command);
				try {
					tf.sendInput(command);
				} catch (Exception e) { e.printStackTrace(); }
			}
		} catch (FileNotFoundException e1) {
			System.out.println("File not found.");
		}
	}
}
