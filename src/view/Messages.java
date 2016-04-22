package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Messages extends JPanel{

	private static final long serialVersionUID = 386111478676201121L;
	JTextPane messages;
	JScrollPane scrollpane;
	
	public Messages(){
		this.messages = new JTextPane();
		this.messages.setEditable(false);
		this.messages.setBackground(Color.white);
		this.messages.setPreferredSize(new Dimension(560, 250));
		this.scrollpane = new JScrollPane(this.messages, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(this.scrollpane, BorderLayout.CENTER);
		
		this.messages.setText("What is your name? (Use ctrl+enter to send)");
	}
	
	public void setText(String text){
		this.messages.setText(text);
	}
	
}
