 package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileFilter;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.Controller;

public class AudioSelectionPanel extends JPanel {
	private JComboBox audioSelection;
	private JList audioList;

	private JScrollPane scr_audio;
	private JScrollPane audioList2;
	public JList<String> audioData;
	private ArrayList<String> iconData;
	private JButton playButton;
	public JButton setButton2;
	private JButton removeset;
	private JButton remove_img;
	private JButton add_set;
	private SelectionListener selectionListener;
	private PlayListener playListener;
	private SetListener setListener;
	private AddSetListener addSetListener;
	public JCheckBox checkBox;
	private boolean isChecked;
	protected Controller controller;
	private int numofaudioset;
	private DefaultComboBoxModel comboModel;
	public ArrayList<String> audioset;
	private RemoveListener removeListener;
	private JButton undo;
	private boolean iconSelectFlag = false;
	private ClearListener clearListener;
	private AddListener addListener;
	private JLabel audioSetNameLabel;
	public JTextField audioSetName;
	private JLabel searchLabel;
	private JTextField searchAudio;
	private DefaultListModel<String> DefaultListModel = new DefaultListModel();
	private JFrame iconPopUp;
	private Container container;
	private JButton icon_btn;
	private JLabel logo;
	private JLabel prevmes;
	private JLabel img;
	private JLabel icon_label;
	private JFileChooser jfilechooser;
	private JButton addIconBtn;
	private SetupPanel setupPanel;
	private MainFrame mf;
	
	public AudioSelectionPanel(MainFrame mf) {
		// initialize
		this.mf = mf;
		comboModel = new DefaultComboBoxModel();
		audioSelection = new JComboBox();
		audioList = new JList();
		setupPanel = new SetupPanel();

		// for icon popup
		iconData = new ArrayList<>();
		iconPopUp = new JFrame("Icon");
		iconPopUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		iconPopUp.setBounds(250, 100, 400, 300);
		container = iconPopUp.getContentPane();
		container.setLayout(new GridLayout(6,1));
		logo = new JLabel("Select an icon for this button");
		logo.setHorizontalAlignment(JLabel.CENTER);
		logo.setBounds(20, 5, 250, 30);
		icon_btn = new JButton("Browse directory");
		icon_btn.setBounds(200, 5, 150, 30);
		container.add(logo);
		container.add(icon_btn);
		remove_img = new JButton("Delete current icon");
		remove_img.setBounds(150, 120, 150, 30);
		container.add(remove_img);
		icon_label = new JLabel("No icon selected");
		icon_label.setHorizontalAlignment(JLabel.CENTER);
		icon_label.setBounds(140, 150, 220, 30);
		container.add(icon_label);
		addIconBtn = new JButton("Add Selected Icon");
		container.add(addIconBtn);
		addIconBtn.setEnabled(false);
		
		
		initPopUpWindow();

		// audio-Data (with Scrollbar)
		defmodel();
		audioData = new JList(DefaultListModel);
		audioData.setBorder(BorderFactory.createEtchedBorder());
		audioData.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		audioData.setLayoutOrientation(JList.VERTICAL);
		audioData.setVisibleRowCount(5);
		scr_audio = new JScrollPane(audioData);
		scr_audio.setMinimumSize(new Dimension(100, 50));
		scr_audio.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		audioData.setSelectedIndex(0);
		
		
		// scrollPane for audio List
		audioList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		audioList2 = new JScrollPane(audioList);
		audioList2.setMinimumSize(new Dimension(130, 100));
		audioList2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// search audiofile
		searchLabel = new JLabel("Search your Audio files: ");
		searchAudio = new JTextField(10);

		// set audioset name
		audioSetNameLabel = new JLabel("Audio Set Name: ");
		audioSetName = new JTextField(10);
		audioSetName.setEditable(false);

		// pause-play
		ImageIcon playIcon = new ImageIcon("icons/play-pause.png");
		playIcon.setImage(Controller.scaleIcon(playIcon, 8));
		playButton = new JButton(playIcon);
		playButton.setPreferredSize(Controller.getIconDimensions(playIcon));

		setButton2 = new JButton("Add " + (String) audioData.getSelectedValue() + " as button 1");
		// add
		add_set = new JButton("Create into Audio Set");

		// remove
		ImageIcon removeIcon = new ImageIcon("icons/remove.png");
		removeIcon.setImage(Controller.scaleIcon(removeIcon, 8));
		removeset = new JButton(removeIcon);
		removeset.setPreferredSize(Controller.getIconDimensions(removeIcon));

		// clear
		undo = new JButton("Clear Selections");

		checkBox = new JCheckBox();
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
					controller.log("audio at index " + selection + "selected");
					if (selection > 0) {
						selectionListener.setAudioSelection(selection - 1);
					}
				}
			}
		});
		
		// detecting swaps in audio list for button naming conventions
		audioData.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setButton2.setText("Add " + (String) audioData.getSelectedValue() + " as button " + (mf.getlength()+1));
			}
		});
		
		audioData.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
            	setButton2.setText("Add " + (String) audioData.getSelectedValue() + " as button " + (mf.getlength()+1));
            }

            @Override
            public void keyReleased(KeyEvent e) { 
            	setButton2.setText("Add " + (String) audioData.getSelectedValue() + " as button " + (mf.getlength()+1));
            }

            @Override
            public void keyTyped(KeyEvent e) { 
            	setButton2.setText("Add " + (String) audioData.getSelectedValue() + " as button " + (mf.getlength()+1));
            }
        });
		
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection;
				if(audioList.getSelectedIndex() < 9) {
				selection = (((String) audioList.getSelectedValue()) + ".wav").substring(3);
				} else {
				selection = (((String) audioList.getSelectedValue()) + ".wav").substring(4);
				}
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
					setButton2.setEnabled(isChecked);
					audioSetName.setEditable(true);
					controller.log("check box [create custom audio set] enabled");
				} else {
					audioSetName.setEditable(false);
					audioSetName.setText("");
					setButton2.setEnabled(isChecked);
					add_set.setEnabled(isChecked);
					undo.setEnabled(false);
					controller.log("check box [create custom audio set] disabled");
				}

			}

		});
		
		setButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iconPopUp.setVisible(true);
				icon_btn.setEnabled(true);
				icon_label.setText("No icon selected");
				remove_img.setEnabled(false);
				controller.log("Added a button from user's own audio files.");
			}

		});

		searchAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchAudio(searchAudio.getText());
				controller.log("User searched for " + searchAudio.getText() + " in the list");
			}
		});

		// dynamic text checking for searching audio files
		searchAudio.getDocument().addDocumentListener(new DocumentListener() {
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
				if (searchAudio.getText().equals("")) {
					searchAudio("");
				} else {
					searchAudio(searchAudio.getText());
				}

			}
		});

		// dynamic text checking for audiosetname
		audioSetName.getDocument().addDocumentListener(new DocumentListener() {
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
				if (audioSetName.getText().equals("")) {
					add_set.setEnabled(false);
				} else {
					add_set.setEnabled(true);
				}

			}
		});

		add_set.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String setname = audioSetName.getText();
				addAudioSet(setname);
				controller.setAudioSetname(setname);
				controller.addAudioSet(new LinkedList<>(audioset));
				controller.addIconBtn(new LinkedList<>(iconData));
				iconData.removeAll(iconData);
				// use controller to generate new preview
				// controller.generatePreview(audioset);
				if (addSetListener != null) {
					addSetListener.dynamicSetup();
				}
				audioset.clear();
				checkBox.setSelected(false);
				setButton2.setEnabled(false);
				add_set.setEnabled(false);
				undo.setEnabled(false);
				audioSetName.setText("");
				audioSetName.setEditable(false);
				controller.log("add_set [create into audio set] pressed");

				if (addListener != null) {
					addListener.clearSetup(true);
				}
			}

		});

		removeset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = audioSelection.getSelectedIndex() - 1;
				String file = (((String) audioList.getSelectedValue()) + ".wav").substring(3);
				int n1 = audioList.getSelectedIndex();
				if (removeListener != null && n >= 0) {
					removeListener.setRemoveInfo(n, file);
					removeListener.iconRemoveInfo(n, n1);
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
					controller.log("clear selections button pressed");
				}

			}
		});

		Border innerBorder = BorderFactory.createTitledBorder("");
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

	public void setAddSetListener(AddSetListener listener) {
		this.addSetListener = listener;
	}

	public List<String> getnewaudiolist() {
		return audioset;
	}

	public boolean isChecked() {
		return checkBox.isSelected();
	}

	// get audioset name from user. no input => defname(Audio Set + numofaudioset)
	public void addAudioSet(String name) {
		if (name.equals("")) {
			comboModel.addElement("Audio Set " + numofaudioset);
		} else {
			comboModel.addElement(name);
		}
		numofaudioset++;

	}

	// importing
	public void refre_audio() {
		comboModel.removeAllElements();
		comboModel.addElement("");
		numofaudioset = 0;
		numofaudioset++;
		for (int i = 0; i < controller.getNumberOfAudioSets(); i++) { // 4
			addAudioSet(controller.getAudioSetname(i));

		}
	}

	// audioData default model
	public void defmodel() {
		ArrayList<?> searchlist = (ArrayList) getAudioList();
		searchlist.stream().forEach((a) -> {
			String name = a.toString();
			DefaultListModel.addElement(name);
		});
	}

	// search audio by Jtextfield
	public void searchAudio(String searchTerm) {
		DefaultListModel<String> fil = new DefaultListModel();
		ArrayList<?> searchlist = (ArrayList) getAudioList();
		searchlist.stream().forEach((a) -> {
			String name = a.toString().toLowerCase();
			String oriname = a.toString();
			if (name.contains(searchTerm.toLowerCase())) {
				fil.addElement(oriname);
			}
		});
		DefaultListModel = fil;
		audioData.setModel(DefaultListModel);

	}

	public void def_audioset() {

		comboModel.addElement("Select Audio Set");
		numofaudioset++;
		comboModel.addElement("Audio Set 1");
		numofaudioset++;
		audioSelection.setModel(comboModel);
		audioSelection.setSelectedIndex(0);

	}

	private void layoutComponents() {
		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		// row 1 - Audio set selector
		gc.gridy = 0;

		gc.weightx = 0.01;
		gc.weighty = 0.01;

		gc.gridx = 0;
		gc.gridwidth = 2;
		gc.ipadx = 100;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(audioSelection, gc);

		// row 2 - Audio set .wav file list
		gc.gridy = 1;

		gc.weightx = 0.01;
		gc.weighty = 0.01;
		gc.ipadx = 0;
		gc.gridx = 0;
		gc.gridwidth = 2;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(audioList2, gc);

		// row 3 - play and remove buttons
		JPanel customPanel0 = new JPanel(new GridLayout(1, 3));
		customPanel0.add(playButton);
		JButton blank = new JButton("blank");
		blank.setVisible(false);
		customPanel0.add(blank);

		customPanel0.add(removeset);

		gc.gridy = 2;

		gc.weightx = 0.01;
		gc.weighty = 0.01;

		gc.gridx = 0;
		gc.gridwidth = 1;
		gc.ipadx = 15;
		gc.ipady = 15;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(customPanel0, gc);

		// row 4 - Audio file searching
		JPanel searchPanel = new JPanel(new GridLayout(1, 2));
		searchPanel.add(searchLabel);
		searchPanel.add(searchAudio);

		gc.gridy = 3;

		gc.gridwidth = 1;
		gc.weightx = 0.01;
		gc.weighty = 0.01;

		gc.ipadx = 0;
		gc.ipady = 0;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(searchPanel, gc);

		// row 5 - Audio file list
		gc.gridy = 4;

		gc.weightx = 0.01;
		gc.weighty = 0.01;

		gc.gridx = 0;
		gc.gridwidth = 2;
		gc.ipadx = 160;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(scr_audio, gc);

		// row 6 - Create custom set
		gc.gridy = 5;

		gc.weightx = 0.01;
		gc.weighty = 0.01;

		gc.gridx = 0;
		gc.gridwidth = 1;
		gc.ipadx = 0;
		gc.ipady = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(new JLabel("Check below box to create custom Audio Set"), gc);

		gc.gridx = 1;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(checkBox, gc);

		// row 7 - Create custom set
		gc.gridy = 6;

		gc.weightx = 0.01;
		gc.weighty = 0.01;

		gc.gridx = 0;
		gc.gridwidth = 2;
		gc.ipadx = 0;
		gc.ipady = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(checkBox, gc);

		// row 8 - more custom set
		JPanel customPanel3 = new JPanel(new GridLayout(1, 1));
		customPanel3.add(setButton2);

		gc.gridy = 7;

		gc.weightx = 0.01;
		gc.weighty = 0.01;

		gc.gridx = 0;
		gc.gridwidth = 1;
		gc.ipadx = 0;
		gc.ipady = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(customPanel3, gc);

		// row 9 - more custom set
		gc.gridy = 8;

		gc.weightx = 0.01;
		gc.weighty = 0.01;

		gc.gridx = 0;
		gc.gridwidth = 2;
		gc.ipadx = 0;
		gc.ipady = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(undo, gc);

		// row 10 - more custom set
		JPanel customPanel4 = new JPanel(new GridLayout(1, 2));
		customPanel4.add(audioSetNameLabel);
		customPanel4.add(audioSetName);

		gc.gridy = 9;

		gc.weightx = 0.01;
		gc.weighty = 0.01;

		gc.gridx = 0;
		gc.gridwidth = 2;
		gc.ipadx = 0;
		gc.ipady = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(customPanel4, gc);

		// row 11 - more custom set
		gc.gridy = 10;

		gc.weightx = 0.01;
		gc.weighty = 0.01;

		gc.gridx = 0;
		gc.gridwidth = 2;
		gc.ipadx = 0;
		gc.ipady = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.insets = new Insets(0, 0, 0, 0);
		add(add_set, gc);

		// GridBagConstraints gc = new GridBagConstraints();

		/*
		 * First Row
		 * 
		 * Search Audio:
		 * 
		 */
		/*
		 * gc.gridy = 0;
		 * 
		 * gc.weightx = 0.01; gc.weighty = 0.01;
		 * 
		 * gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new
		 * Insets(0, 0, 0, 5); add(searchLabel, gc);
		 * 
		 * /*gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets =
		 * new Insets(0, 0, 0, 0); add(searchAudio, gc);
		 * 
		 * /* Next Row
		 * 
		 * [audioSelection] [vv drop down (audiolist) vv] [audio Data dropdown bar]
		 * 
		 */
		/*
		 * gc.gridy = 1;
		 * 
		 * gc.weightx = 0.1; gc.weighty = 0.1;
		 * 
		 * gc.gridx = 0; gc.fill = GridBagConstraints.NONE; gc.anchor =
		 * GridBagConstraints.FIRST_LINE_END; gc.insets = new Insets(0, 0, 0, 2);
		 * add(audioSelection, gc);
		 * 
		 * gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets =
		 * new Insets(0, 0, 0, 0); add(audioList, gc);
		 * 
		 * gc.gridx = 2; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new
		 * Insets(0, 0, 0, 5); add(scr_audio, gc);
		 */

		/*
		 * Next Row
		 * 
		 * [play] [remove]
		 * 
		 */
		/*
		 * gc.gridy++; gc.weightx = 0.1; gc.weighty = 0.3;
		 * 
		 * gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_START;
		 * add(playButton, gc); gc.anchor = GridBagConstraints.FIRST_LINE_END;
		 * add(removeset, gc);
		 */

		/*
		 * Next Row
		 * 
		 * Create Custom Audio Set: [ ]
		 * 
		 */
		/*
		 * gc.gridy++; gc.weightx = 0.2; gc.weighty = 0.2;
		 * 
		 * // gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets =
		 * new Insets(0, 0, 0, 5); add(new JLabel("Create Custom Audio Set: "), gc);
		 * 
		 * gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets =
		 * new Insets(0, 0, 0, 0); add(checkBox, gc);
		 */

		/*
		 * Next Row
		 * 
		 * [<< clear] [select >>] [select audiofiles >>]
		 * 
		 */

		/*
		 * gc.gridy++; gc.weightx = 0.2; gc.weighty = 0.2;
		 * 
		 * gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_START; add(undo, gc);
		 * 
		 * gc.gridx = 1; gc.insets = new Insets(0, 0, 0, 0); gc.anchor =
		 * GridBagConstraints.FIRST_LINE_END; add(setButton, gc);
		 * 
		 * gc.gridx = 2; gc.insets = new Insets(0, 0, 0, 0); gc.anchor =
		 * GridBagConstraints.FIRST_LINE_END; add(setButton2, gc);
		 */
		/*
		 * Next Row
		 * 
		 * [Add Set]
		 * 
		 */
		/*
		 * gc.gridy++; gc.weightx = 1; gc.weighty = 1;
		 * 
		 * 
		 * gc.gridx = 0; gc.anchor = GridBagConstraints.FIRST_LINE_END; gc.insets = new
		 * Insets(0, 0, 0, 5); add(audioSetNameLabel, gc);
		 * 
		 * gc.gridx = 1; gc.anchor = GridBagConstraints.FIRST_LINE_START; gc.insets =
		 * new Insets(0, 0, 0, 0); add(audioSetName, gc);
		 * 
		 * gc.gridx = 2; gc.insets = new Insets(0, 0, 0, 5); gc.anchor =
		 * GridBagConstraints.FIRST_LINE_START; add(add_set, gc);
		 */

	}

	public void setJList(String[] audioSet) {
		DefaultListModel listModel = new DefaultListModel();
		for (int i = 0; i < audioSet.length; i++) {
			listModel.addElement(i + 1 + ". " + audioSet[i].substring(0, audioSet[i].length() - 4));
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

	// get Audiolist from "audio" folder
	private List<String> getAudioList() {
		File f = new File("audio/");
		List<String> filesname = new ArrayList<String>(Arrays.asList(f.list()));
		List<String> listWav = new ArrayList<>();
		for (int i = 0; i < filesname.size(); i++) {
			String str = filesname.get(i);
			listWav.add(str);
		}
		return listWav;
	}

	private void initPopUpWindow() {
		remove_img.setEnabled(false);
		String s = System.getProperty("user.dir");
		jfilechooser = new JFileChooser(s);
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
		jfilechooser.setFileFilter(imageFilter);
		icon_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jfilechooser.showOpenDialog(AudioSelectionPanel.this) == JFileChooser.APPROVE_OPTION) {
					try {
						String iconPath = jfilechooser.getSelectedFile().getAbsolutePath();
						ImageIcon playIcon5 = new ImageIcon(iconPath);
						playIcon5.setImage(Controller.scaleIcon(playIcon5, 8));
						img = new JLabel(playIcon5);
						img.setPreferredSize(Controller.getIconDimensions(playIcon5));
						//img.setBounds(130,30, 125, 125);
						container.add(img);
						remove_img.setEnabled(true);
						icon_btn.setEnabled(false);
						icon_label.setText("Icon applied");
						icon_label.setBounds(110, 150, 220, 30);
						iconData.add(iconPath);
						controller.log("Icon file (" + iconPath + ") chosen from file browser.");
						addIconBtn.setEnabled(true);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				container.repaint();
			}
		});
		
		addIconBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selection2 = (String) audioData.getSelectedValue();
				int idx = audioSelection.getSelectedIndex();
				if (setListener != null && selection2 != null) {
					audioset.add((String) audioData.getSelectedValue());
					setListener.setup(idx, selection2);
				}
				setButton2.setText("Add " + (String) audioData.getSelectedValue() + " as button " + (mf.getlength()+1));
				System.out.println(setupPanel.getLength());
				Img_repaint();
				controller.log("Icon added to button and button added to set.");
				addIconBtn.setEnabled(false);
				iconPopUp.dispose();
			}
		});

		remove_img.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Img_repaint();
				iconData.remove(iconData.size() - 1);
				iconData.add("");
				icon_label.setText("Icon has been removed. Please reselect an icon.");
				remove_img.setEnabled(false);
				icon_btn.setEnabled(true);
				addIconBtn.setEnabled(false);
				controller.log("Icon removed from button and ready for re-selection.");
			}
		});
	}

	public void Img_repaint() {
		container.remove(img);
		container.revalidate();
		container.repaint();

	}

}
