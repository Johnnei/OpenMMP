package gui;

import javax.swing.JFrame;

/**
 * Manage Popups for House Buying, Trading, etc
 * It's meant to prevent being able to open multiple popups of the same type
 * @author Johnnei
 *
 */
public class PopupManager
{
	JFrame frame;
	public static PopupManager manager = new PopupManager();
	
	private PopupManager() {
	}
	
	public void showFrame(JFrame frame) {
		if(this.frame != null) {
			this.frame.setVisible(false);
			this.frame.dispose();
		}
		this.frame = frame;
		this.frame.setVisible(true);
	}
}
