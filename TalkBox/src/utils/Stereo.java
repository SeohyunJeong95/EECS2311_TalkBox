package utils;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Stereo {
	
	private File soundfilein;
	private AudioInputStream audioIn;
	private Clip clip = null;
	
	public Stereo() {
		setClip();
	}
	
	private void setClip() {
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void playMusic(String SoundFile) {
	
		clip.stop();
		clip.close();
		
		soundfilein = new File("audio//"+SoundFile);
		
		
		try {
			audioIn = AudioSystem.getAudioInputStream(soundfilein.toURI().toURL());
			clip.open(audioIn);
			clip.start();
		} catch (UnsupportedAudioFileException ee) {
			ee.printStackTrace();
		} catch (IOException ee) {
			ee.printStackTrace();
		} catch (LineUnavailableException ee) {
			ee.printStackTrace();
		}
	}
}
