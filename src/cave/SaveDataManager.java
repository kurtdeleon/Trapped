package cave;

import java.io.File;

public class SaveDataManager {
	File savefolder;
	File[] saveFiles;
	
	public SaveDataManager()
	{
		savefolder = new File("savedata");
		saveFiles = savefolder.listFiles();
	}

	public File GetSaveData( String username )
	{
		for (File file : saveFiles) {
		    if ( file.getName().equalsIgnoreCase( username+".txt" )) {
		        return file;
		    }
		}
		return null;
	}
}
