package tool.IOManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public class FileManager {

	public FileManager() {

		String FileFolder = System.getenv("APPDATA") + "\\" + "OSRS Merch Tool";

		String os = System.getProperty("os.name").toUpperCase();
		if (os.contains("WIN")) {
			FileFolder = System.getenv("APPDATA") + "\\" + "OSRS Merch Tool";
		}
		if (os.contains("MAC")) {
			FileFolder = System.getProperty("user.home") + "/Library/Application " + "Support"
					+ "OSRS Merch Tool";
		}
		if (os.contains("NUX")) {
			FileFolder = System.getProperty("user.dir") + ".OSRS Merch Tool";
		}

		File directory = new File(FileFolder);

		if (!directory.exists()) {
			//noinspection ResultOfMethodCallIgnored
			directory.mkdir();
		}

		try {
			File file = new File(FileFolder + "\\" + "items.json");

			if (file.createNewFile()){

				FileWriter fileWriter = new FileWriter(file);
				String emptyJson = "[]";

				fileWriter.write(emptyJson);
				fileWriter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
