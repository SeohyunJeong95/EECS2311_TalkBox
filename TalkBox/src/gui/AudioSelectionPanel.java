package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import controller.Controller;

public class AudioSelectionPanel extends JPanel {
	private JComboBox audioSelection;
	private JList audioList;

	private JScrollPane scr_audio;
	private JList<String> audioData;

	private JButton playButton;
	private JButton setButton;
	private JButton setButton2;
	private JButton removeset;
	private JButton add_set;
	private SelectionListener selectionListener;
	private PlayListener playListener;
	private SetListener setListener;
	private JCheckBox checkBox;
	private boolean isChecked;
	protected Controller controller;
	private int numofaudioset;
	private DefaultComboBoxModel comboModel;
	ArrayList<String> audioset;
	private RemoveListener removeListener;
	private JButton undo;
	private ClearListener clearListener;
	private AddListener addListener;
	private JLabel audioSetNameLabel;
	private JTextField audioSetName;

	public AudioSelectionPanel() {
		// initialize
		comboModel = new DefaultComboBoxModel();
		audioSelection = new JComboBox();
		audioList = new JList();

		//audio-Data (with Scrollbar)
		audioData = new JList(getAudioList().toArray());
		audioData.setBorder(BorderFactory.createEtchedBorder());
		audioData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		audioData.setLayoutOrientation(JList.VERTICAL);
		audioData.setVisibleRowCount(5);
		scr_audio = new JScrollPane(audioData);
		scr_audio.setMinimumSize(new Dimension(100, 50));
		scr_audio.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		audioSetNameLabel = new JLabel("AudioSet Name : ");
		audioSetName = new JTextField(10);
		



		// pause-play
		ImageIcon playIcon = new ImageIcon("src//icons//play-pause.png");
		playIcon.setImage(Controller.scaleIcon(playIcon, 8));
		playButton = new JButton(playIcon);
		playButton.setPreferredSize(Controller.getIconDimensions(playIcon));

		// select button
		setButton = new JButton("Select audioset >>");

		setButton2 = new JButton("Select audiofile >>");
		// add
		add_set = new JButton("Compile into New Set");

		// remove
		ImageIcon removeIcon = new ImageIcon("src//icons//remove.png");
		removeIcon.setImage(Controller.scaleIcon(removeIcon, 8));
		removeset = new JButton(removeIcon);
		removeset.setPreferredSize(Controller.getIconDimensions(removeIcon));

		// clear
		undo = new JButton("Clear Selections");

		checkBox = new JCheckBox();
		setButton.setEnabled(false);
		setButton2.setEnabled(false);
		add_set.setEnabled(false);
		undo.setEnabled(false);
		isChecked = false;
		controller = new Controller();
		audioset = new ArrayList<String>();

		// Actions
		audioSelection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectionListener != null) {
					int selection = audioSelection.getSelectedIndex();
					if (selection > 0) {
						selectionListener.setAudioSelection(selection - 1);
					}
				}
			}
		});

		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection = (String) audioList.getSelectedValue();
				int idx = audioSelection.getSelectedIndex();
				if (playListener != null && idx > 0) {
					playListener.setFileName(idx, selection);
				}
				controller.log("play button pressed to preview (" + selection + ")");
			}

		});

		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isChecked = checkBox.isSelected();
				if (isChecked) {
					setButton.setEnabled(isChecked);
					setButton2.setEnabled(isChecked);
					add_set.setEnabled(isChecked);
					controller.log("check box [create custom audio set] enabled");
				} else {
					undo.setEnabled(false);
					controller.log("check box disabled");
				}

			}

		});
		setButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection = (String) audioList.getSelectedValue();
				int idx = audioSelection.getSelectedIndex();
				if (setListener != null && selection != null ) {
						audioset.add((String) audioList.getSelectedValue());
					    setListener.setup(idx, selection);
				}
				controller.log("setButton [Select >>] pressed, (" + selection + ") added to the audioset");
			}

			});
		
		setButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection2 = (String) audioData.getSelectedValue();
				int idx = audioSelection.getSelectedIndex();
				if (setListener != null && selection2 != null) {
					audioset.add((String) audioData.getSelectedValue());
					setListener.setup(idx, selection2);
				}
				controller.log("setButton [Select >>] pressed, (" + selection2 + ") added to the audioset");
			}

		});


		add_set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String setname =audioSetName.getText();
				addAudioSet(setname);
				controller.addAudioSet(new LinkedList<>(audioset));
				// use controller to generate new preview
				// controller.generatePreview(audioset);
				audioset.clear();
				checkBox.setSelected(false);
				setButton.setEnabled(false);
				setButton2.setEnabled(false);
				add_set.setEnabled(false);
				undo.setEnabled(false);
				controller.log("add_set [Compile into new audio set] pressed");

				if (addListener != null) {
					addListener.clearSetup(true);
				}
			}

		});

		removeset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = audioSelection.getSelectedIndex() - 1;
				String file = (String) audioList.getSelectedValue();
				if (removeListener != null && n >= 0) {
					removeListener.setRemoveInfo(n, file);
					controller.log("audio file (" + file + ") removed from audio set");
				}
			}
		});

		undo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (clearListener != null) {
					clearListener.clear(true);
					audioset.clear();
					undo.setEnabled(false);
				}

			}
		});
		
		

		Border innerBorder = BorderFactory.createTitledBorder("Select Audio");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		layoutComponents();

	}

	public void setSelectionListener(SelectionListener listener) {
		this.selectionListener = listener;
	}

	public void setRemoveListener(RemoveListener listener) {
		this.removeListener = listener;
	}

	public void setPlayListener(PlayListener listener) {
		this.playListener = listener;
	}

	public void setSetListener(SetListener listener) {
		this.setListener = listener;
	}

	public void setClearListener(ClearListener listener) {
		this.clearListener = listener;
	}

	public void setAddListener(AddListener listener) {
		this.addListener = listener;
	}

	public List<String> getnewaudiolist() {
		return audioset;
	}

	public boolean isChecked() {
		return checkBox.isSelected();
	}

	//audioset name setting 
	public void addAudioSet(String name) {
		
		if(name.equals("")) {
		comboModel.addElement("Audio Set " + numofaudioset);
		}else {
			comboModel.addElement(name);
		}
		numofaudioset++;

	}

	public void refre_audio() {
		comboModel.removeAllElements();
		comboModel.addElement("");
		numofaudioset = 0;
		numofaudioset++;
		for (int i = 0; i < controller.getNumberOfAudioSets(); i++) {
			addAudioSet("");
		}
	}

	public void def_audioset() {

		comboModel.addElement("");
		numofaudioset++;
		comboModel.addElement("Audio Set 1");
		numofaudioset++;
		comboModel.addElement("Audio Set 2");
		numofaudioset++;
		comboModel.addElement("Audio Set 3");
		numofaudioset++;
		comboModel.addElement("Audio Set 4");
		numofaudioset++;
		audioSelection.setModel(comboModel);
		audioSelection.setSelectedIndex(0);

	}

	private void layoutComponents() {

		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		/*
		 * First Row
		 * 
		 * [audioSelection] [vv drop down (audiolist) vv] [audio Data dropdown bar]
		 * 
		 */
		gc.gridy = 0;

		gc.weightx = 0.1;
		gc.weighty = 0.1;

		gc.gridx = 0;

		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 2);
		add(audioSelection, gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(audioList, gc);

		gc.gridx = 3;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(scr_audio,gc);
	
		

		/*
		 * Next Row
		 * 
		 * [play] [remove]
		 * 
		 */
		gc.gridy++;
		gc.weightx = 0.1;
		gc.weighty = 0.3;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(playButton, gc);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(removeset, gc);

		/*
		 * Next Row
		 * 
		 * Create Custom Audio Set: [ ]
		 * 
		 */
		gc.gridy++;
		gc.weightx = 0.2;
		gc.weighty = 0.2;

		//
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(new JLabel("Create Custom Audio Set: "), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(checkBox, gc);

		/*
		 * Next Row
		 * 
		 * [<< clear] [select >>] [select audiofiles >>]
		 * 
		 */

		gc.gridy++;
		gc.weightx = 0.2;
		gc.weighty = 0.2;

		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(undo, gc);

		gc.gridx = 1;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(setButton, gc);

		gc.gridx = 2;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(setButton2, gc);
		/*
		 * Next Row
		 * 
		 * [Add Set]
		 * 
		 */
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 1;

		//
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(audioSetNameLabel, gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.insets = new Insets(0, 0, 0, 0);
		add(audioSetName, gc);
		
		gc.gridx = 2;
		gc.insets = new Insets(0, 0, 0, 5);
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		add(add_set, gc);

	}

	public void setJList(String[] audioSet) {
		DefaultListModel listModel = new DefaultListModel();
		for (int i = 0; i < audioSet.length; i++) {
			listModel.addElement(audioSet[i]);
		}
		audioList.setModel(listModel);
		// audioList.setPreferredSize(new Dimension(110, 68));
		audioList.setBorder(BorderFactory.createEtchedBorder());
		// audioList.setSelectedIndex(0);

	}

	public void turnOffUndo() {
		undo.setEnabled(false);
	}

	public void turnOnUndo() {
		undo.setEnabled(true);
	}

	private List<String> getAudioList() {
		File f = new File("src//audio");
		List<String> filesname = new ArrayList<String>(Arrays.asList(f.list()));
		List<String> listWav = new ArrayList<>();
		for (int i = 0; i < filesname.size(); i++) {
			String str = filesname.get(i);
			listWav.add(str);
		}
		return listWav;
	}

}
