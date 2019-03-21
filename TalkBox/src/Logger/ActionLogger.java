package Logger;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalTime;

public class ActionLogger {
	
	private String path;
	private boolean append = false;

	
	public ActionLogger(String file_path) {
		path =  file_path;
	}
	
	public ActionLogger(String file_path, boolean append_value) {
		path = file_path;
		try {
			writeToFile("log start");
			writeToFile("");
		} catch (IOException e) {
			e.printStackTrace();
		}
		append = append_value;
	}
	
	public void writeToFile(String text) throws IOException{
		FileWriter write = new FileWriter(path, append);
		PrintWriter printer = new PrintWriter(write);
		printer.printf("<" + java.time.LocalDateTime.now() + ">     " + "%s" + "%n", text);
		printer.close();
	}
	
	public void initializeLogger() throws IOException {
		writeToFile("Log begins");
		append = true;		
	}
	
	public void terminateLogger() throws IOException {
		writeToFile("");
		writeToFile("Log Ends");
		append = false;
	}
	
	public void setAppend(boolean set) {
		append = set;
	}
}
