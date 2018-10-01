package cs131.pa1.filter.sequential;
public class GrepFilter extends SequentialFilter {
	
	protected String key;
	
	
	public GrepFilter(String key) {
		this.key = key;
	}
	
	
	public String processLine(String line) {
		if(line.contains(key)) {
			return line;
		} else {
			return null;
		}
	}
}
