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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.messages = new Messages();
		textpane = new JTextPane();
		this.textarea = new TextArea(textpane);
		this.add(messages, BorderLayout.CENTER);
		this.add(textarea, BorderLayout.SOUTH);

		setListeners();
	}

	private void connectToServer(String name, String Message) {
		Request request = new Request(RequestCode.CONNECT, name);
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(request);
			this.conversation = this.conversation + "\n" + (String)ois.readObject();
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
			}

			@Override
			public void keyReleased(KeyEvent event) {
			}

			@Override
			public void keyTyped(KeyEvent event) {
				if(event.getKeyCode() == KeyEvent.VK_ENTER){
					if(firstmessage){
						connectToServer(textarea.getMessage(), "");
						name = textarea.getMessage();
						textarea.clearText();
						firstmessage = false;
					}else{
						connectToServer(name, textarea.getMessage());
						textarea.clearText();
					}
					
				}
			}
			
		});
		
	}

}