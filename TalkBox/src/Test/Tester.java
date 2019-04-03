package Test;

import org.junit.jupiter.api.*;

import controller.Controller;
import gui.MainFrame;
import model.TalkBox;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
import java.util.List;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

public class Tester {
	
	private MainFrame mainframe;
	private TalkBox talkbox;
	private Robot bot;
	
	public void setup() {
		talkbox = new TalkBox();
	}
	
	public void setupGUI() throws AWTException {
		mainframe = new MainFrame();
		mainframe.showIt();
		bot = new Robot();
	}
	
	public List<String> setupTestSet() {
		List<String> testSet = new LinkedList<>();
		  testSet.add("hi.wav");
		  testSet.add("goodbye.wav");
		  testSet.add("excuseme.wav");
		  testSet.add("time.wav");
		  testSet.add("tired.wav");
		  testSet.add("Yes.wav");
		  testSet.add("Cool.wav");
		  testSet.add("BONJOUR.wav");
		  testSet.add("Alright.wav");
		  testSet.add("No.wav");
		return testSet;
	}
	@Test
	public void test_model_getAudioList() {
		setup();
		List<String> tester = setupTestSet();
		assertEquals(tester, talkbox.getAudioList(0), "default test set was not successfully added");
	}
	
	@Test
	public void test_model_getNumberOfAudioButtons() {
		setup();
		assertEquals(5, talkbox.getNumberOfAudioButtons(), "number of buttons should be 5");
	}
	
	@Test
	public void test_model_getNumberOfAudioSets() {
		setup();
		List<String> tester = setupTestSet();
		setupTestSet();
		assertEquals(1, talkbox.getNumberOfAudioSets());
	}
	
	@Test
	public void test_model_setAudioFileNames() {
		setup();
		List<String> tester = setupTestSet();
		setupTestSet();
		talkbox.setAudioFileNames(tester);
		assertEquals(2, talkbox.getNumberOfAudioSets(), "audioFileNames did not grow when set was added");
		assertEquals(true, talkbox.getAudioList(1).contains("hi.wav"), "audioFileNames should contain hi.wav at the last index");
	}
	
	@Test
	public void test_controller_addAudioSet() {
		Controller controller = new Controller();
		List<String> tester = new LinkedList<>();
		tester.add("hi.wav");
		tester.add("time.wav");
		int oldCount = controller.getNumberOfAudioSets();
		controller.addAudioSet(tester);
		assertEquals(oldCount + 1, controller.getNumberOfAudioSets(), 
				"controller did not grow audioFileNames");
		assertEquals(tester, controller.getAudioList(1),
				"controller did not add tester successfully");	
	}
	
	@Test
	public void test_controller_addAudio() {
		Controller controller = new Controller();
		controller.addAudio(0, "testFile");
		assertEquals(true, controller.getAudioList(0).contains("testFile"),
				"tester should contain testFile");
	}
	
	@Test
	public void test_controller_removeAudio() {
		Controller controller = new Controller();
		controller.removeAudio(0, "hi.wav");
		assertEquals(false, controller.getAudioList(0).contains("hi.wav"),
				"hi.wav should have been removed from audioFileNames[0]");
		
	}
	@Test
	public void test_controller_getNumberOfButtons() {
		Controller controller = new Controller();
		assertEquals(5, controller.getNumberOfButtons());
	}
	@Test
	public void test_model_AddIcon() {
		setup();
		LinkedList<String> iconlist = setupIconTestSet();
		talkbox.addIconbtn(iconlist);
		assertEquals(iconlist, talkbox.getAudioIconDatabyidx(1));
	}
	
	@Test
	public void test_controller_RemoveIcon() {
		setup();
		LinkedList<String> iconlist = setupIconTestSet();
		Controller controller = new Controller();
		controller.addIconBtn(iconlist);
		controller.removeIcon(1,   0);
    	assertEquals(false, controller.getAudioIconDatabyidx(1).contains("happy.png"),
			"happy.png should have been removed");
	}
	
	/*
	@Test
	public void testGUI() throws AWTException {
		setupGUI();
		Robot bot = new Robot();
		bot.mouseMove(100, 140);
		mouseClick();
		bot.mouseMove(100, 170);
		mouseClick();
		bot.mouseMove(950, 120);
		mouseClick();
		bot.mouseMove(120, 110);
		mouseClick();
		bot.mouseMove(100, 560);
		try {Thread.sleep(2000);}catch (InterruptedException e) {}
		assertEquals(true, mainframe.getToolBarS().getStarted(),
				"start button was not pressed");
		assertEquals(true, mainframe.getMainFrameSim().getSoundPlayed(),
				"sound button was not pressed");
	}
	
	
	// bot helper class
	public void mouseClick() {
		try {Thread.sleep(100);}catch (InterruptedException e) {}
		bot.mousePress(InputEvent.BUTTON1_MASK);
		try {Thread.sleep(100);}catch (InterruptedException e) {}
		bot.mouseRelease(InputEvent.BUTTON1_MASK);
		try {Thread.sleep(100);}catch (InterruptedException e) {}
	}
	*/
}
