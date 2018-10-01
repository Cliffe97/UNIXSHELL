package cs131.pa1.filter.sequential;
import java.io.*;

import cs131.pa1.filter.Message;

public class CatFilter extends SequentialFilter{
	
	
	protected String fileName;
	protected BufferedReader reader;
	public boolean fileNotFound;
	
	public CatFilter(String fileName) {
		this.fileName = fileName;
		this.fileNotFound = true;
	}
	
	@Override
	public void process() {
		try{
			reader = new BufferedReader(new FileReader(new File(SequentialREPL.currentWorkingDirectory + System.getProperty("file.separator") + fileName)));
			for(String line; (line = reader.readLine()) != null; ){
				output.add(line);
			}
			SequentialCommandBuilder.validFile = true;
		} catch (IOException e) {
			SequentialCommandBuilder.validFile = false;
			System.out.print(Message.FILE_NOT_FOUND.with_parameter("cat " + fileName));
		}
	}

	@Override
	protected String processLine(String line) {
		return null;
	}
}
