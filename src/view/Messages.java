package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextPane;

public class Messages extends JPanel{

	private static final long serialVersionUID = 386111478676201121L;
	JTextPane messages;
	
	public Messages(){
		this.messages = new JTextPane();
		this.messages.setEditable(false);
		this.messages.setBackground(Color.white);
		this.messages.setPreferredSize(new Dimension(580, 300));
		this.add(this.messages, BorderLayout.CENTER);
		
		this.messages.setText("What is your name?");
	}
	
	public void setText(String text){
		this.messages.setText(text);
	}
	
}
