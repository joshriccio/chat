package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class TextArea extends JPanel{

	private static final long serialVersionUID = -8716145497904982869L;
	private JTextPane chat;
	private JScrollPane scrollpane;
	
	public TextArea(JTextPane chat){
		this.chat = chat;
		this.chat.setEditable(true);
		this.chat.setBackground(Color.white);
		this.chat.setPreferredSize(new Dimension(560, 100));
		this.scrollpane = new JScrollPane(this.chat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(this.scrollpane, BorderLayout.CENTER);
	}
	
	public JTextPane getText(){
		return this.chat;
	}
	
	public String getMessage(){
		return this.chat.getText();
	}
	
	public void clearText(){
		this.chat.setText("");
	}
}
