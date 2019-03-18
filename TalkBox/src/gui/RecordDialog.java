package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import playground.Helpers;
import utils.Recorder;

public class RecordDialog extends JDialog {
	
	private JTextField audioFileName;
	private JToggleButton recordButton;
	private int audioIndex;
	private String fileName;
	private Recorder recorder;
	private SetListener setListener;
	
	public int getAudioIndex() {
		return audioIndex;
	}

	public void setAudioIndex(int audioIndex) {
		this.audioIndex = audioIndex;
	}
	
	public void setSetListener(SetListener listener) {
		this.setListener = listener;
	}

	public RecordDialog(JFrame parent) {
		super(parent, "Record...", false);
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		audioFileName = new JTextField(10);
		recordButton = new JToggleButton("Record");
		recorder = new Recorder();
		
		recordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!validateFilename(audioFileName.getText())) {
					return;
				}
				RecordDialog.this.fileName = audioFileName.getText();
				JToggleButton button = (JToggleButton) e.getSource();
				if (button == recordButton) {
					if (button.isSelected()) {
						String path = System.getProperty("user.dir");
						String completeFileSource = path + "\\bin\\audio\\" + RecordDialog.this.fileName + ".wav";
				
						recorder.startRecording(completeFileSource);
						button.setText("Stop");
						
					} else {
						recorder.stopRecording();
						button.setText("Record");
						if (RecordDialog.this.setListener != null) {
							RecordDialog.this.setListener.setup(RecordDialog.this.audioIndex, RecordDialog.this.fileName + ".wav");
						}
						audioFileName.setText("");
					}
				}
				
				
				
			}
			
		});
		
		gc.gridy = 0;
		
		
		/*First Row */
		gc.weighty = 1;
		gc.weightx = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0,0,0,5);
		add(new JLabel("Name: "), gc);
		
		gc.gridx++;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0,0,0,0);
		add(audioFileName, gc);
		
		/* Next Row */
		
		gc.gridy++;
		gc.weighty = 1;
		gc.weightx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 1;
		
		add(recordButton, gc);
		
		
		
		
		
		
		
		setSize(400, 300);
		setLocationRelativeTo(parent);
		
	}
	
	public boolean validateFilename(String newFile) {
		
		if (newFile.isEmpty()) {
			System.out.println("filename is empty!");
			return false;
		}
		else {
			boolean validated = true;
			File actual = new File("src\\audio");
			for (File f : actual.listFiles()) {
				if (f.getName().equals(newFile + ".wav")) {
					validated = false;
					System.out.println("filename already exists");
				}
			}
			return validated;
		}
	}
}

