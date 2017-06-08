package tool;

import java.awt.*;

/**
 * Created by Alex on 6/7/2017.
 *
 * tool.Main class for OSRS Merch Tool. Starts the tool.Gui
 */
public class Main {

	/**
	 * Application Launches here
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Gui frame = new Gui();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
