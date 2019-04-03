package Test;

import org.junit.jupiter.api.*;

import controller.Controller;
import gui.AudioSelectionPanel;
import gui.MainFrame;
import model.TalkBox;
import static org.junit.jupiter.api.Assertions.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class GuiTester {
	private MainFrame frame;
	
	@BeforeEach
	public void setupGUI() throws AWTException {
		    frame = new MainFrame();
	        frame.showIt();
	    
	}
//	@Test
//    public void testCustomCheckboxtest() throws InterruptedException {
//		Thread.sleep(1000);
//        AudioSelectionPanel audioselectionpanel = frame.getAUdioSelectionpanel();
//        audioselectionpanel.checkBox.doClick();
//        assertEquals(true,audioselectionpanel.checkBox.isSelected());
//        Thread.sleep(1000);
//    }
//	@Test
//    public void testSelectButton() throws InterruptedException {
//		Thread.sleep(1000);
//        AudioSelectionPanel audioselectionpanel = frame.getAUdioSelectionpanel();
//        audioselectionpanel.checkBox.doClick();
//        assertEquals(audioselectionpanel.setButton2.getText(),"Add Alright.wav as button 1");
//        Thread.sleep(1000);
//    }
	
//	@Test
//    public void testSelectButton2() throws InterruptedException {
//		Thread.sleep(1000);
//        AudioSelectionPanel audioselectionpanel = frame.getAUdioSelectionpanel();
//        audioselectionpanel.checkBox.doClick();
//        audioselectionpanel.audioData.setSelectedIndex(3);
//        
//        Thread.sleep(2000);
//    }
	
//	@Test
//    public void testAudioNameTextField() throws InterruptedException {
//        Thread.sleep(1000);
//        AudioSelectionPanel audioselectionpanel = frame.getAUdioSelectionpanel();
//        assertEquals(audioselectionpanel.audioSetName.getText(),"");
//        audioselectionpanel.audioSetName.setText("emotion");
//        assertEquals(audioselectionpanel.audioSetName.getText(),"emotion");
//        Thread.sleep(1000);
//    }
	
}
