package Logger;
import java.io.BufferedReader;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.IOException;

 

import javax.swing.JFrame;

import javax.swing.JTextArea;

import javax.swing.SwingUtilities;

 

import java.io.BufferedReader;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.IOException;

 

import javax.swing.JFrame;

import javax.swing.JTextArea;

import javax.swing.SwingUtilities;

 

public class TBCLog extends JFrame {

               

                File file;

                JTextArea text;

                BufferedReader in;

               

                public TBCLog() {

                               

                                super("TBCLog");

                               

                                this.file = new File("Action_Log\\log.txt");

                                this.text = new JTextArea();

                                try {

                                                this.in = new BufferedReader(new FileReader(file));

                                } catch (FileNotFoundException e) {

                                                // TODO Auto-generated catch block

                                                e.printStackTrace();

                                }             

                               

                                setVisible(false);

                                               

                }

               

                public void read() throws IOException {
                                String line = in.readLine();
                                while (line != null) {
                                                text.append(line + "\n");
                                                line = in.readLine();
                                }
                }

               

                public void update() throws IOException{

                                this.read();

                }

               

                public void showIt() throws IOException {
                                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                getContentPane().add(text);
                                setSize(100, 100);
                                setVisible(true); 

                }

                
                public static void main(String[] args) throws IOException {
                	
                	TBCLog logger = new TBCLog();	
                	logger.showIt();
                	logger.read();
                }

 

}