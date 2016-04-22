package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class TextArea extends JPanel{

	private static final long serialVersionUID = -8716145497904982869L;
	private JTextPane chat;
	
	public TextArea(JTextPane chat){
		this.chat = chat;
		this.chat.setEditable(true);
		this.chat.setBackground(Color.white);
		this.chat.setPreferredSize(new Dimension(580, 100));
		this.add(this.chat, BorderLayout.CENTER);
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
