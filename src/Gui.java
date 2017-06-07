import javax.swing.*;

/**
 * Created by Alex on 6/7/2017.
 *
 * Gui Implementation class
 */
public class Gui extends JFrame {
	/**
	 *  Builds the UI of the Application and handles most of the processes.
	 *
	 */
	Gui() {

		/*
		   General Application settings like size and title

		 */
		this.setBounds(100, 100, 800, 560);
		this.setTitle("OSRS Merch Tool | V.01");
		this.getContentPane().setLayout(null);
		this.setResizable(false);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}
}
