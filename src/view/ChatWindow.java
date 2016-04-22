package view;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextPane;

import Network.Request;
import Network.RequestCode;
import Network.Server;

public class ChatWindow extends JFrame{
	private static final long serialVersionUID = 5875046651800072284L;
	private static Socket socket;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	private Messages messages;
	private TextArea textarea;
	private JTextPane textpane;
	private String name;
	private String conversation;
	
	public ChatWindow(){
		this.setTitle("Chat Server");
		this.setSize(600, 400);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.messages = new Messages();
		this.conversation = "";
		this.textpane = new JTextPane();
		setListeners();
		this.textarea = new TextArea(textpane);
		this.add(messages, BorderLayout.CENTER);
		this.add(textarea, BorderLayout.SOUTH);
	}

	private void connectToServer(String name) {
		Request request = new Request(RequestCode.CONNECT, name);
		try {
			socket = new Socket(Server.ADDRESS, Server.PORT_NUMBER);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(request);
			this.conversation = "";
			this.conversation = this.conversation + (String)ois.readObject() + "\n";
			this.messages.setText(this.conversation);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setListeners() {
		this.textpane.addKeyListener(new KeyListener(){
			private boolean firstmessage = true;
			@Override
			public void keyPressed(KeyEvent event) {
				if(event.isControlDown() && event.getKeyCode() == KeyEvent.VK_ENTER){
					if(firstmessage){
						name = textarea.getMessage();
						connectToServer(name);
						textarea.clearText();
						firstmessage = false;
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent event) {
			}

			@Override
			public void keyTyped(KeyEvent event) {
			}
		});
	}
}