package view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JFrame;
import javax.swing.JTextPane;

import network.Request;
import network.RequestCode;

/**
 * The main user interface for the chat client
 * 
 * @author Joshua Riccio
 *
 */
public class ChatWindow extends JFrame {
	private static final long serialVersionUID = 5875046651800072284L;
	private ObjectOutputStream oos;
	private Messages messages;
	private TextArea textarea;
	private JTextPane textpane;
	private String conversation;
	private String name;

	/**
	 * The chat window's constructor
	 * @param username 
	 * @param conversation 
	 * @param messages 
	 */
	public ChatWindow(String username, ObjectOutputStream oos) {
		this.setTitle("Chat");
		this.setSize(600, 400);
		this.setResizable(false);
		this.oos = oos;
		this.messages = new Messages();
		this.conversation = new String();
		this.name = username;
		this.textpane = new JTextPane();
		setListeners();
		this.textarea = new TextArea(textpane);
		this.add(messages, BorderLayout.CENTER);
		this.add(textarea, BorderLayout.SOUTH);
	}

	private void setListeners() {
		this.textpane.addKeyListener(new KeyListener() {
			private boolean firstmessage = true;

			@Override
			public void keyPressed(KeyEvent event) {
			}

			@Override
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					if (firstmessage) {
						textarea.clearText();
						firstmessage = false;
					} else {
						String message = "";
						if (textarea.getMessage().length() > 1) {
							message = textarea.getMessage().substring(0, textarea.getMessage().length() - 2);
						}
						Request request = new Request(RequestCode.SEND_MESSAGE, name, message);
						textarea.clearText();
						try {
							oos.writeObject(request);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent event) {
			}
		});
	}
	
	public void updateConversation(String name, String message){
		conversation = conversation + name + message + "\n";
		messages.setText(conversation);
	}
}
