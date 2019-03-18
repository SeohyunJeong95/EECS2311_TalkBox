package playground;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.io.*;


public class Demo extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Demo frame = new Demo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Demo() {
		//
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel lblTalkboxSimulator = new JLabel("TalkBox Simulator");
		lblTalkboxSimulator.setOpaque(true);
		lblTalkboxSimulator.setBackground(Color.LIGHT_GRAY);
		lblTalkboxSimulator.setHorizontalAlignment(SwingConstants.CENTER);
		lblTalkboxSimulator.setForeground(Color.BLUE);
		contentPane.add(lblTalkboxSimulator, BorderLayout.NORTH);
		
		JButton btnAudio = new JButton("Audio 1");
		btnAudio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = System.getProperty("user.dir");
				String file = s + "\\data\\audio\\test1.wav";
				try {
					playSound(file);
				} catch (LineUnavailableException e1) {
					e1.printStackTrace();
				} catch (UnsupportedAudioFileException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnAudio.setForeground(Color.BLUE);
		btnAudio.setBackground(Color.LIGHT_GRAY);
		contentPane.add(btnAudio, BorderLayout.WEST);
		
		JLabel lblClickButtonTo = new JLabel("Click button to hear audio file");
		lblClickButtonTo.setBackground(Color.LIGHT_GRAY);
		lblClickButtonTo.setForeground(Color.GRAY);
		lblClickButtonTo.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblClickButtonTo, BorderLayout.CENTER);
	}
	
	private void playSound(String filepath) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
    	File file = new File(filepath);
        Clip clip = AudioSystem.getClip();
  
        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        clip.open(ais);
        clip.start();
//        SwingUtilities.invokeLater(() -> {
//            JOptionPane.showMessageDialog(null, "Audio playing. Close to stop");
//        });
    }
	
}