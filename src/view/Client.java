package view;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * The main method for the client
 * 
 * @author Joshua Riccio
 *
 */
public class Client {

	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		}
		SideBar list = new SideBar();
		list.setVisible(true);
	}
}
