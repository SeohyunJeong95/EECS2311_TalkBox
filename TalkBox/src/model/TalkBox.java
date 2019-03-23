package model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TalkBox implements TalkBoxConfiguration {

	// Database where the audio sets are stored. It's a LinkedList (database) containing the 
	private List<List<String>> audioFileNames;
	private List<String> audioSetName;
	// Constructor
	public TalkBox() {
		audioFileNames = new LinkedList<>();
		audioSetName = new ArrayList<>();
		setDefaultAudioset();

	}
	
	/**
	 * Sets up the default audio-sets that are included in the TalkBox.
	 */
	public void setDefaultAudioset() {
		  List<String> set1 = new LinkedList<>(); set1.add("hi.wav");
		  set1.add("goodbye.wav"); set1.add("excuseme.wav"); set1.add("time.wav");
		  set1.add("thankyou.wav"); audioFileNames.add(0, set1);
		  audioSetName.add("");
		  //Set 2
		  List<String> set2 = new LinkedList<>(); set2.add("Yes.wav");
		  set2.add("No.wav"); set2.add("Maybe.wav"); set2.add("Sometimes.wav");
		  set2.add("Ok.wav"); audioFileNames.add(1, set2);
		  audioSetName.add("");
		  
		  //Set 3 
		  List<String> set3 = new LinkedList<>(); set3.add("Happy.wav");
		  set3.add("Sad.wav"); set3.add("Hungry.wav"); set3.add("Bored.wav");
		  set3.add("Tired.wav"); audioFileNames.add(2, set3);
		  audioSetName.add("");
		  
		  //Set 4 
		  List<String> set4 = new LinkedList<>(); set4.add("Cool.wav");
		  set4.add("Sucks.wav"); set4.add("Like.wav"); set4.add("Dont Like.wav");
		  set4.add("Alright.wav"); audioFileNames.add(3, set4);
		  audioSetName.add("");
	
	}

	/**
	 * adds an audio set to the current database of audio sets
	 * @param audioFilesNames
	 */
	public void setAudioFileNames(List<String> audioFilesNames) { //export �Ҷ�.
		this.audioFileNames.add(audioFilesNames);

	}
	public void setAudiosetNames(String name) {
		this.audioSetName.add(name);

	}
	public String getAudiosetNames(int idx) {
		return this.audioSetName.get(idx);

	}
	public List<String> getAudiosetNameslist() {
		return audioSetName;

	}

	

	/**
	 * @return the number of audio buttons
	 * NOTE: currently defaulted at 5
	 */
	@Override
	public int getNumberOfAudioButtons() { // ?
		// TODO Auto-generated method stub
		return 5;
	}

	/**
	 * @return the number of audio sets
	 */
	@Override
	public int getNumberOfAudioSets() { // make gui audiobutton set
		return audioFileNames.size();
	}

	/**
	 * Gets the size of audioFileNames
	 * @return total number of buttons
	 */
	@Override
	public int getTotalNumberOfButtons() {
		// TODO Auto-generated method stub
		int total = 0;
		for (int i = 0; i < audioFileNames.size(); i++) {
			for (int j = 0; j < audioFileNames.get(i).size(); j++) {
				total++;
			}
		}
		return total;
	}

	/**
	 * Gets the beginning of the file path where the audio sets are located
	 * @return the Path where the audio sets are
	 */
	@Override
	public Path getRelativePathToAudioFiles() {
		return Paths.get("src//audio//");

	}

	/**
	 * This method is used mainly to make GUI components. It iterates through each audio set in filename
	 * @return fileNames
	 */
	@Override
	public String[][] getAudioFileNames() {
		int n = getNumberOfAudioSets();
		String[][] fileNames = new String[n][];

		for (int i = 0; i < n; i++) {
			fileNames[i] = new String[audioFileNames.get(i).size()];
			for (int j = 0; j < audioFileNames.get(i).size(); j++) {
				fileNames[i][j] = audioFileNames.get(i).get(j);
			}
		}

		return fileNames;
	}
	
	/**
	 * Adds audio file path to the specified index in audioFileNames 
	 * @param idx the index to store the audio file
	 * @param fileName the audio file to be added
	 */
	public void addAudio(int idx, String fileName) {
		this.audioFileNames.get(idx).add(fileName);
	}
	
	/**
	 * gets the audioList in audioFileNames at the specified index
	 * @param idx the index to retrieve from
	 * @return the audio set stored at idx
	 */
	public List<String> getAudioList(int idx) {
		return this.audioFileNames.get(idx);
	}
	
	/**
	 * removes the audio within the audio set, located at the specified index, with the specified file name
	 * @param idx the index of the audio set to be removed
	 * @param fileName the audio file name to be removed
	 */
	public void removeAudio(int idx, String fileName) {
		List<String> list = this.audioFileNames.get(idx);
		int index = list.indexOf(fileName);
		if (index >=0) {
			list.remove(index);
		}
	}
}
