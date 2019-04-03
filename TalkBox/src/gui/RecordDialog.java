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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import controller.Controller;
import utils.Recorder;

public class RecordDialog extends JDialog {
	
	private JTextField audioFileName;
	private JToggleButton recordButton;
	private int audioIndex;
	private String fileName;
	private Recorder recorder;
	private SetListener setListener;
	private Controller controller;
	private JLabel status;
	private File f;
	
	public int getAudioIndex() {
		return audioIndex;
	}

	public void setAudioIndex(int audioIndex) {
		this.audioIndex = audioIndex;
	}
	
	public void setSetListener(SetListener listener) {
		this.setListener = listener;
	}
	
	public RecordDialog(JFrame parent, Controller arg_controller) {
		super(parent, "Record Audio", false);
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		audioFileName = new JTextField(10);
		recordButton = new JToggleButton("Record");
		recordButton.setEnabled(false);
		recorder = new Recorder();
		status = new JLabel();
		
		controller = arg_controller;
		
		f = new File("audio/");
		audioFileName.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
			    changed();
			  }
			  public void removeUpdate(DocumentEvent e) {
			    changed();
			  }
			  public void insertUpdate(DocumentEvent e) {
			    changed();
			  }

			  public void changed() {
			     if (audioFileName.getText().equals("")) {
			       recordButton.setEnabled(false);
			     }
			     else {
			       recordButton.setEnabled(true);
			    }

			  }
			});
		
		
		recordButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!validateFilename(audioFileName.getText())) {
					controller.log("recording attempt, but filename was not valid");
					return;
				}
				RecordDialog.this.fileName = audioFileName.getText();
				JToggleButton button = (JToggleButton) e.getSource();
				if (button == recordButton) {
					if (button.isSelected()) {
						audioFileName.setEditable(false);
						String path = System.getProperty("user.dir");
						String completeFileSource = path + "//audio//" + RecordDialog.this.fileName + ".wav";
				
						recorder.startRecording(completeFileSource);
						button.setText("Stop");
						controller.log("recordButton [Record] pressed, recording audio...");
						
					} else {
						recorder.stopRecording();
						button.setText("Record");
//						if (RecordDialog.this.setListener != null) {
//							RecordDialog.this.setListener.setup(RecordDialog.this.audioIndex, RecordDialog.this.fileName + ".wav");
//						}
						controller.log("recordButton [Record] pressed. Recording saved @ (" + RecordDialog.this.fileName + ".wav" + ")");
						audioFileName.setText("");
						audioFileName.setEditable(true);
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
			
		setSize(250, 200);
		setLocationRelativeTo(parent);
		
		/* Next Row */
		gc.gridy++;
		add(status, gc);
	}

	public boolean validateFilename(String newFile) {
		boolean validated = true;
		status.setText("");
		for (File a : f.listFiles()) {
			if (f.getName().equals(newFile + ".wav")) {
				validated = false;
				status.setText("Filename already exists!");
			}
		}
		return validated;
	}
}

