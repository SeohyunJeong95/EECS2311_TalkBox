package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class SetupPanel extends JPanel {
private JTextArea textArea;
private int count = 0;
	
	public SetupPanel() {
		textArea = new JTextArea();
		setLayout(new BorderLayout());
		add(new JScrollPane(textArea), BorderLayout.CENTER);
		textArea.setEditable(false);
	}
	
	
	public void appendText (String text) {
		textArea.append(text);
		count++;
	}
	
	public String getText() {
		return textArea.getText();
	}
	
	public boolean isEmpty() {
		return count == 0;
		
	}
	
	public void removeText() {
		textArea.setText("");
		count = 0;
	}
	
	public int getLength() {
		return count;
	}
}
