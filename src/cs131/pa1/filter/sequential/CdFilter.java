package cs131.pa1.filter.sequential;
import java.io.*;
import cs131.pa1.filter.*;

public class CdFilter extends SequentialFilter{

	protected String directory;
	
	
	public CdFilter(String directory) {
		this.directory = directory;
	}
	
	@Override
	public void process() {
		if(directory.equals(".")) {
			return;
		} else if(directory.equals("..")) {
			SequentialREPL.currentWorkingDirectory = SequentialREPL.currentWorkingDirectory.substring(0, SequentialREPL.currentWorkingDirectory.lastIndexOf(System.getProperty("file.separator")));		
		} else {
			File path = new File(SequentialREPL.currentWorkingDirectory + System.getProperty("file.separator") + directory);
			if(path.isDirectory() && path.canRead()) {
				SequentialREPL.currentWorkingDirectory = SequentialREPL.currentWorkingDirectory + System.getProperty("file.separator") + directory;
			} else {
				System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter("cd " + directory));
			}
		}
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}

}
