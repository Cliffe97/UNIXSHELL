package cs131.pa1.filter.sequential;
import java.io.*;

public class ListFilter extends SequentialFilter{

	
	@Override
	public void process() {
		File folder = new File(SequentialREPL.currentWorkingDirectory);
		File[] fileList = folder.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if(!fileList[i].isHidden()) {
				output.add(fileList[i].getName());
			}
		}
	}
	
	
	
	@Override
	protected String processLine(String line) {
		return null;
	}

}
