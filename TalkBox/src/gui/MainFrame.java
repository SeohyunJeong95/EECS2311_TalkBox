package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Logger.TBCLog;
import controller.Controller;
import utils.Stereo;

public class MainFrame extends JFrame {
	private AudioSelectionPanel audioSelectionPanel;
	private ToolBar toolBar;
	private SetupPanel setupPanel;
	private ToolBarS toolBarS;
	private Controller controller;
	private Stereo audioPlayer;
	private MainFrameSim mfs;
	
	private TBCLog tbc;
	private JFileChooser jfilechooser;
	private RecordDialog recordDialog;
	private JFrame reference;
	
	public MainFrame() {
		super("TalkBox Configurator");
		getContentPane().setLayout(new BorderLayout());
		setJMenuBar(createMenuBar());
		
		//Initialize
		audioSelectionPanel = new AudioSelectionPanel(this);
		audioSelectionPanel.def_audioset();
		
		toolBar = new ToolBar();
		setupPanel = new SetupPanel();
		controller = audioSelectionPanel.controller;
		tbc = controller.tbc;
		audioPlayer = new Stereo();
		setToolBarS(new ToolBarS());
		mfs = new MainFrameSim(controller, this);
		String s = System.getProperty("user.dir"); 
		jfilechooser = new JFileChooser(s);
		jfilechooser.setFileFilter(new ImportExtensionFilter());
		recordDialog = new RecordDialog(this, controller);
		reference = this;
		getToolBarS().turnOffStart();

		//Actions
		
		audioSelectionPanel.setSelectionListener(new SelectionListener() {
			public void setAudioSelection(int n) {
			  refreshJList(n);
			  setupSim(n);
		      recordDialog.setAudioIndex(n);
		      getToolBarS().turnOnStart();
			}
			
		});
		
		audioSelectionPanel.setAddSetListener(() -> {
			setupSim(controller.getLastIndex());
			toolBarS.turnOnStart();
		});
				
		audioSelectionPanel.setPlayListener(new PlayListener() {
			
			public void setFileName(int idx, String fileName) {
				String path = controller.getPath().toString();
				String completePath = path +  "\\" + fileName; //**
				audioPlayer.playMusic(fileName);
			}
		});
			 
		audioSelectionPanel.setSetListener(new SetListener() {
			public void setup(int idx, String fileName) {
				if (audioSelectionPanel.isChecked() && fileName != null) {
					setupPanel.appendText(fileName + "\n");
					if(!setupPanel.isEmpty()) {
						audioSelectionPanel.turnOnUndo();
					}
				} else {
					
				}
			}
			
		});
		
		audioSelectionPanel.setClearListener(new ClearListener() {

			public void clear(boolean b) {
				if (b) {
					setupPanel.removeText();
				}
			}
			
		});
		
		audioSelectionPanel.setRemoveListener(new RemoveListener() {

			@Override
			public void setRemoveInfo(int idx, String file) {
				controller.removeAudio(idx, file);
				refreshJList(idx);
				setupSim(idx);
			}
			
			@Override
			public void iconRemoveInfo(int setidx, int elementidx) {
				controller.removeIcon(setidx, elementidx);
				refreshJList(setidx);
				setupSim(setidx);
			}
			
		});
		
		audioSelectionPanel.setAddListener(new AddListener() {
			public void clearSetup(boolean b) {
				if (b) {
					setupPanel.removeText();
				}
				
			}
			
		});
		
		getToolBarS().setInitListener(new InitiateSim() {
			public void shouldStart(boolean b) {
				if (b) {
					mfs.showIt();
					controller.log("SIMULATOR STARTED");
					MainFrame.this.setVisible(false);
					getToolBarS().turnOffStartButton();
				
				}
			}
		});
		
		toolBar.setRecord(new InitiateSim() {
			@Override
			public void shouldStart(boolean b) {
				if (b) {
					recordDialog.setVisible(true);
				}
			}
		});
		
		recordDialog.setSetListener(new SetListener() {

			@Override
			public void setup(int idx, String fileName) {
				controller.addAudio(idx, fileName);
				refreshJList(idx);
				audioSelectionPanel.searchAudio("");
				setupSim(idx);
			}
			
		});
		
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int action = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure?", "Exit", JOptionPane.YES_OPTION);
				if (action == JOptionPane.YES_OPTION) {
					controller.terminateLogger();
					dispose();
					System.gc();	
				}
			}
			
		});
		//Add
		getContentPane().add(audioSelectionPanel, BorderLayout.WEST);
		getContentPane().add(toolBar, BorderLayout.NORTH);
		getContentPane().add(setupPanel, BorderLayout.CENTER);
		getContentPane().add(toolBarS, BorderLayout.SOUTH);	
		
	}
	
	
	

	public void showIt() {
		setSize(550,700);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem exportDataItem = new JMenuItem("Export Data");
		JMenuItem importDataItem = new JMenuItem("Import Data");
		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem tbclogger = new JMenuItem("TBC Log");
		
		fileMenu.add(exportDataItem);
		fileMenu.add(importDataItem);
		fileMenu.addSeparator();
		fileMenu.add(tbclogger);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		
		menuBar.add(fileMenu);
		importDataItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(jfilechooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.load(jfilechooser.getSelectedFile());
						audioSelectionPanel.refre_audio();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		exportDataItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(jfilechooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						controller.save(jfilechooser.getSelectedFile());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
	
		tbclogger.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tbc.showIt();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
				}		
			}
		);
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure?", "Exit", JOptionPane.YES_OPTION);
				if (action == JOptionPane.YES_OPTION) {
					
					dispose();
					System.gc();
				}
				
			}
		});
		
		return menuBar;
	}
	
	public void refreshJList(int idx) {
		String[][] audioFileSet = controller.getFileNames();
		String[] audioSet = audioFileSet[idx];
		audioSelectionPanel.setJList(audioSet); //setting 
	}
	
	public void setupSim(int n) {
		  mfs.setIdx(n);
		  mfs.setButtons(n);
	      mfs.setSwapButtons();
	}
	
	public ToolBarS getToolBarS() {
		return toolBarS;
	}

	public void setToolBarS(ToolBarS toolBarS) {
		this.toolBarS = toolBarS;
	}
	
	public MainFrameSim getMainFrameSim() {
		return mfs;
	}
	public AudioSelectionPanel getAUdioSelectionpanel() {
		return audioSelectionPanel;
	}
	
	public int getlength() {
		return setupPanel.getLength();
	}
}




