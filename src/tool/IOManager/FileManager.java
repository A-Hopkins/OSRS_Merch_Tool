package tool.IOManager;

import java.io.File;
import java.io.IOException;

/**
 * Created by Alex on 6/8/2017.
 *
 */
public class FileManager {

	public FileManager() {

		String FileFolder = System.getenv("APPDATA") + "\\" + "OSRS Merch Tool";

		System.out.println("Searching for system");

		String os = System.getProperty("os.name").toUpperCase();
		if (os.contains("WIN")) {
			FileFolder = System.getenv("APPDATA") + "\\" + "OSRS Merch Tool";
			System.out.println("Found windows");
		}
		if (os.contains("MAC")) {
			FileFolder = System.getProperty("user.home") + "/Library/Application " + "Support"
					+ "OSRS Merch Tool";
			System.out.println("Found mac");
		}
		if (os.contains("NUX")) {
			FileFolder = System.getProperty("user.dir") + ".OSRS Merch Tool";
			System.out.println("Found linux");
		}

		System.out.println("Searching for resource folder");
		File directory = new File(FileFolder);

		if (directory.exists()) {
			System.out.println("Found folder");
		}
		else {
			//noinspection ResultOfMethodCallIgnored
			directory.mkdir();
			System.out.println("Could not find folder so created it");
		}

		try {
			// TODO Make this write an empty JSON on creation
			File file = new File(FileFolder + "\\" + "items.json");

			if (file.createNewFile()){

				System.out.println("File is created!");

			}else{
				System.out.println("File already exists.");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
