package cave;

import java.io.File;

public class SaveDataManager {
	File savefolder;
	File[] saveFiles;
	
	public SaveDataManager()
	{
		savefolder = new File("your/path");
		saveFiles = savefolder.listFiles();
	}

	public File GetSaveData( String username )
	{
		for (File file : saveFiles) {
		    if ( file.getName().equalsIgnoreCase( username )) {
		        return file;
		    }
		}
		
		return null;
	}
	
}
