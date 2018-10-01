package cs131.pa1.filter.sequential;

import java.util.*;

public class WcFilter extends SequentialFilter{
	protected int lineCount = 0;
	protected int wordCount = 0;
	protected int charCount = 0;
	
	
	@Override
	public void process() {
		if(SequentialCommandBuilder.validFile) {
			if(isDone()) {
				output.add("0 0 0");
			}
		}
		while (!input.isEmpty()){
			String line = input.poll();
			String processedLine = processLine(line);
			if (processedLine != null){
				output.add(processedLine);
			}
		}
		
	}

	@Override
	protected String processLine(String line) {
		lineCount++;
		Scanner counter = new Scanner(line);
		while(counter.hasNext()) {
			wordCount++;
			counter.next();
		}
		charCount += line.length();
		counter.close();
		if(isDone()) {
			return lineCount + " " + wordCount + " " + charCount;
		} else {
			return null;
		}
	}
	
	
}
