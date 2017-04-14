package view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import model.User;
import network.Request;
import network.RequestCode;
import network.Server;

/**
 * The main user interface for the chat client
 * 
 * @author Joshua Riccio
 *
 */
public class ChatWindow extends JFrame {
	private static final long serialVersionUID = 5875046651800072284L;
	private ObjectOutputStream oos;
	private Socket socket;
	private Messages messages;
	private TextArea textarea;
	private JTextPane textpane;
	private String conversation;
	private String name;
	Vector<User> usersInChat;

	/**
	 * The chat window's constructor
	 * 
	 * @param username
	 * @param conversation
	 * @param messages
	 */
	public ChatWindow(String username, ObjectOutputStream oos) {
		this.setTitle("Chat");
		this.setSize(600, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.messages = new Messages();
		this.conversation = new String();
		this.name = username;
		this.textpane = new JTextPane();
		setListeners();
		this.textarea = new TextArea(textpane);
		this.add(messages, BorderLayout.CENTER);
		this.add(textarea, BorderLayout.SOUTH);
		usersInChat = new Vector<User>();
	}

	private void setListeners() {
		this.textpane.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent event) {
			}

			@Override
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
					String message = "";
					if (textarea.getMessage().length() > 1) {
						message = textarea.getMessage().substring(0, textarea.getMessage().length() - 2);
					}
					Request request = new Request(RequestCode.SEND_MESSAGE, name, message);
					textarea.clearText();
					try {
						for(User user : usersInChat){
							socket = new Socket(user.getIp().substring(1), User.PORT_NUMBER);
							oos = new ObjectOutputStream(socket.getOutputStream());
							oos.writeObject(request);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent event) {
			}
		});
	}

	public void updateConversation(String name, String message) {
		conversation = conversation + name + message + "\n";
		messages.setText(conversation);
	}

	public void addUser(User user) {
		usersInChat.addElement(user);
	}
}
