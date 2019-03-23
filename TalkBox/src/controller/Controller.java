package controller;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import Logger.ActionLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import gui.MainFrame;
import model.TalkBox;

public class Controller {
	
	private TalkBox talkbox;
	private MainFrame view;
	private ActionLogger log;
	
	//talkbox save to object  set audiofile and talbox create . 
	
	public Controller() {
		this.talkbox = new TalkBox();//initialize with talkbox setter method
		log = new ActionLogger("src\\Logger\\log.txt", true);
	}
	
	/**
	 * adds an audio set to the list of audio sets
	 * @param audioset the audio set to be added to the database
	 */
	public void addAudioSet(List<String> audioset) {
		talkbox.setAudioFileNames(audioset);
		
	/**
	 * gets the path to the audio sets and returns it as a Path
	 * @return the path to the audio sets
	 */
	}
	public Path getPath() {
		return talkbox.getRelativePathToAudioFiles();
	}
	
	/**
	 * gets the database of audio sets and returns it
	 * @return the database of audio sets
	 */
	public String[][] getFileNames() {
		return talkbox.getAudioFileNames();
	}
	
	/**
	 * gets the number of audio sets in the database and returns it
	 * @return the number of audio sets in the database
	 */
	public int getNumberOfAudioSets() {
		return talkbox.getNumberOfAudioSets();
	}
	
	public int getNumberOfButtons() {
		return talkbox.getNumberOfAudioButtons();
	}
	
	/**
	 * adds audio file to the audio set at the specified index
	 * @param idx the index specifying which audio set to add the audio file
	 * @param fileName the String path of the file to add
	 */
	public void addAudio(int idx, String fileName) {
		talkbox.addAudio(idx, fileName);
	}
	
	/**
	 * gets the audio set at the given index and returns it
	 * @param idx the index to retrieve the audio set from
	 * @return the audio set
	 */
	public List<String> getAudioList (int idx) {
		return talkbox.getAudioList(idx);
	}
	
	/**
	 * removes the audio file from the audio set at the specified index in the database
	 * @param idx the index of the audio set to remove from
	 * @param fileName the file to be removed
	 */
	public void removeAudio(int idx, String fileName) {
		talkbox.removeAudio(idx, fileName);
	}
	
//	public void generatePreview(ArrayList<String> custom) {
//		view.revalidate();
//	}
	
	public void setAudioSetname(String setname) {
		talkbox.setAudiosetNames(setname);
	}
//	
	public String getAudioSetname(int idx) {
		return talkbox.getAudiosetNames(idx);
	}
//	
	public List<String> getAudiosetNameslist() {
		return talkbox.getAudiosetNameslist();

	}
	
	public void save(File file) throws Exception {
		FileOutputStream fileOutputStream = new FileOutputStream(file);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        try {
			objectOutputStream.writeObject(talkbox);
			objectOutputStream.flush();
		    objectOutputStream.close();
		        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
     
	}
	public void load(File file) throws Exception {
		FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        this.talkbox= (TalkBox) objectInputStream.readObject();
        objectInputStream.close();
     
	}
	
	public static Image scaleIcon(ImageIcon icon, int resize) {
		Image image = icon.getImage();
		image = image.getScaledInstance(
				image.getWidth(null) / resize, image.getHeight(null) / resize, 
				Image.SCALE_SMOOTH);
		return image;
		
	}
	
	public static Dimension getIconDimensions(ImageIcon icon) {
		Dimension result = new Dimension(icon.getIconWidth(), icon.getIconHeight());
		return result;
	}
	
	public void log(String text) {
		try {
			log.writeToFile(text);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void terminateLogger() {
		try {
		log.writeToFile("end of log");
		log.setAppend(false);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
