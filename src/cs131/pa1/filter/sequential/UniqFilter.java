package cs131.pa1.filter.sequential;

import java.util.*;

public class UniqFilter extends SequentialFilter{
	
	protected Collection<String> prevLines = new LinkedList<>();

	
	
	@Override
	protected String processLine(String line) {
		if(prevLines.contains(line)) {
			return null;
		} else {
			prevLines.add(line);
			return line;
		}
	}
	
}
