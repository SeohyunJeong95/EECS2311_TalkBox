package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ToolBarS extends JPanel implements ActionListener {
	private JButton configButton;
	private InitiateSim is;
	private boolean started;

	
	public ToolBarS() {
		
		setBorder(BorderFactory.createEtchedBorder());
		configButton = new JButton("Start Simulator");
		configButton.addActionListener(this);
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(configButton);
		started = false;
	}
	
	public void setInitListener(InitiateSim listener) {
		this.is = listener;
	}
	
	public void turnOffStartButton() {
		this.configButton.setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if (clicked == configButton) {
			if (is != null) {
				is.shouldStart(true);
				started = true;
			}
		}
		
	}
	
	public void turnOffStart() {
		configButton.setEnabled(false);
	}
	
	public void turnOnStart() {
		configButton.setEnabled(true);
	}
	
	public boolean getStarted() {
		return started;
	}
}
