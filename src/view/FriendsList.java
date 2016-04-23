package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 * @author Joshua Riccio
 *
 */
public class FriendsList extends JFrame{

	private static final long serialVersionUID = -2585106853637231791L;
	private JPanel panel;
	private DefaultListModel<String> listmodel;
	private JList<String> list;
	private JScrollPane scrollpane;
	
	/**
	 * Constructor for FriendsList
	 */
	public FriendsList(){
		setupWindow();
	}

	private void setupWindow() {
		this.setTitle("Net Chat");
		this.setSize(300, 700);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.panel = new JPanel();
		this.listmodel = new DefaultListModel<String>();
		this.list = new JList<String>();
		this.scrollpane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setPreferredSize(new Dimension(200, 600));
		setLayout(new BorderLayout());
		this.add(scrollpane, BorderLayout.CENTER);
	}

}
