package view;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import network.Request;
import network.RequestCode;
import network.Response;
import network.ResponseCode;
import network.Server;

/**
 * The main user interface for the chat client
 * 
 * @author Joshua Riccio
 *
 */
public class ChatWindow extends JFrame {
	private static final long serialVersionUID = 5875046651800072284L;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Messages messages;
	private TextArea textarea;
	private JTextPane textpane;
	private String name;
	private String conversation;

	/**
	 * The chat window's constructor
	 * @param conversation 
	 * @param messages 
	 */
	public ChatWindow(Messages messages, String conversation) {
		this.setTitle("Chat");
		this.setSize(600, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.messages = messages;
		this.conversation = conversation;
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
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				Request request = new Request(RequestCode.EXITING);
				try {
					oos.writeObject(request);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}
		});
	}
}
