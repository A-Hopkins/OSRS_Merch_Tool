
import java.awt.*;

/**
 * Created by Alex on 6/7/2017.
 *
 * Main class for OSRS Merch Tool. Starts the Gui
 */
public class Main {

	/**
	 * Application Launches here
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
