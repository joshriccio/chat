package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * The text area for the user to enter to be sent over the network
 * 
 * @author Joshua Riccio
 *
 */
public class TextArea extends JPanel {

	private static final long serialVersionUID = -8716145497904982869L;
	private JTextPane chat;
	private JScrollPane scrollpane;

	/**
	 * The constructor for the TextArea object
	 * 
	 * @param chat
	 */
	public TextArea(JTextPane chat) {
		this.chat = chat;
		this.chat.setEditable(true);
		this.chat.setBackground(Color.white);
		this.chat.setPreferredSize(new Dimension(560, 100));
		this.scrollpane = new JScrollPane(this.chat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(this.scrollpane, BorderLayout.CENTER);
	}

	/**
	 * Gets the message to be sent
	 * 
	 * @return returns the message that was typed
	 */
	public String getMessage() {
		return this.chat.getText();
	}

	/**
	 * Removes the text from the text area.
	 */
	public void clearText() {
		this.chat.setText("");
	}
}
