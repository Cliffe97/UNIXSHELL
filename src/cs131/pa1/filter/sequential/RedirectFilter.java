package cs131.pa1.filter.sequential;

import cs131.pa1.filter.*;

import java.io.*;

public class RedirectFilter extends SequentialFilter{
	
	protected BufferedWriter writer;
	protected File file;
	protected boolean append;
	
	public RedirectFilter(String fileName) {
		file = new File(SequentialREPL.currentWorkingDirectory + System.getProperty("file.separator") + fileName);
		if(file.isFile()) {
			append = false;
		} else {
			append = true;
		}
	}

	@Override
	protected String processLine(String line){
		try{
			writer = new BufferedWriter(new FileWriter(file, append));
			writer.write(line + "\n");
			append = true;
			writer.close();
		} catch (IOException e){
			System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter(">"));
		}
		return null;
	}
}
