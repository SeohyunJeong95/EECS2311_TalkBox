package gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImportExtensionFilter extends FileFilter{

	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		if(file.isDirectory()) {
			return true;
		}

		String name = file.getName();
		String ext = ExtensionHelper.getFileExtension(name);
		if(ext == null) {
			return false;
		}
		if(ext.equals(".tbc")) {
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Talkbox database files (*.tbc)";
	}

}
