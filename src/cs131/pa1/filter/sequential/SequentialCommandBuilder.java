
package cs131.pa1.filter.sequential;


import java.util.*;

import cs131.pa1.filter.*;



import java.io.*;

public class SequentialCommandBuilder {
	
	private static String[] subCommand;
	public static boolean validFile;
	
	//this method takes input from processControl from REPL
	public static void processControl(String rawCommand) {
		//get a list of filters
		List<SequentialFilter> processList = createFiltersFromCommand(rawCommand);
		if(processList.isEmpty()) {
			return;
		}
		//iterate the list and process the filters
		linkFilters(processList);
		validFile = false;
	}
	//takes raw command and return a filter object
	public static List<SequentialFilter> createFiltersFromCommand(String command){
		//parse the raw command and add them to a list
		List<SequentialFilter> commandList = new LinkedList<>();
		subCommand = command.split("\\|");
		
		for(int i = 0; i < subCommand.length; i++) {
			if(subCommand[i].contains(">")) {
				if(subCommand[i].indexOf(">")==0) {
					System.out.print(Message.REQUIRES_INPUT.with_parameter(subCommand[i]));
				}else {
					SequentialFilter filterBeforeCarrot = constructFilterFromSubCommand(subCommand[i].substring(0,subCommand[i].indexOf(">")));
					commandList.add(filterBeforeCarrot);
					SequentialFilter filterAfterCarrot = constructFilterFromSubCommand(subCommand[i].substring(subCommand[i].indexOf(">"), subCommand[i].length()));
					commandList.add(filterAfterCarrot);
				}
			}else {
				SequentialFilter singleFilter = constructFilterFromSubCommand(subCommand[i]);
				commandList.add(singleFilter);
			}
		}
		return commandList;
	}
	
	private static SequentialFilter determineFinalFilter(String command){
		return null;
	}
	
	private static String adjustCommandToRemoveFinalFilter(String command){
		return null;
	}
	// this method takes every single string of raw command and return objects of filter
	private static SequentialFilter constructFilterFromSubCommand(String subCommand){
		try {
			subCommand = subCommand.trim();
			String[] commandString = subCommand.split(" ");
			if(commandString[0].equals("pwd")) {
				return new PwdFilter();
			}else if(commandString[0].equals("cd")) {
				if(commandString.length<2) {
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter("cd"));
					return null;
				}
				return new CdFilter(commandString[1]);
			}else if(commandString[0].equals("ls")) {
				return new ListFilter();
			}else if(commandString[0].equals("cat")) {
				if(commandString.length <2) {
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter("cat"));
					return null;
				}
				String catString = "";
				for(int i = 1; i < commandString.length; i++) {
					catString += commandString[i] + " ";
				}
				catString = catString.trim();
				CatFilter cattie = new CatFilter(catString);
				return cattie;
			}else if(commandString[0].equals("wc")) {
				return new WcFilter();
			}else if(commandString[0].equals("grep")) {
				if(commandString.length <2 ) {
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter("grep"));
					return null;
				}
				return new GrepFilter(commandString[1]);
			}else if(commandString[0].equals("uniq")) {
				return new UniqFilter();
			}else if(commandString[0].contains(">")) {
				if(commandString[0].length()<2 && commandString.length < 2) {
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(">"));
					return null;
				}else {
					if(commandString.length < 2) {
						return new RedirectFilter(commandString[0].substring(1));
					} else {
						return new RedirectFilter(commandString[1]);
					}
				}
			}else {
				System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand));
			}
		}catch(IllegalArgumentException e) {
			System.out.print(Message.INVALID_PARAMETER.with_parameter(subCommand));
		}
		return null;
	}
			
	
	//this method takes a list of filters and process them
	private static void linkFilters(List<SequentialFilter> filters){
		for(int i = 0; i < filters.size(); i++) {
			SequentialFilter filter = filters.get(i);
			if(filter instanceof GrepFilter || filter instanceof WcFilter || filter instanceof RedirectFilter || filter instanceof UniqFilter) {
				if(filter.input==null) {
					System.out.print(Message.REQUIRES_INPUT.with_parameter(subCommand[i]));
					return;
				}
			} 
			if(filter instanceof CatFilter || filter instanceof CdFilter || filter instanceof PwdFilter || filter instanceof ListFilter) {
				if(filter.input != null) {
					System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(subCommand[i]));
					return;
				}
			}
			if(filter instanceof CdFilter) {
				if(i < filters.size() - 1) {
					validFile = false;
					System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(subCommand[i]));
				}
			}
			if(filter instanceof RedirectFilter) {
				if(i!=filters.size()-1) {
					validFile = false;
					System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(subCommand[i-1].substring(subCommand[i-1].indexOf(">")-1)));
				}
			}
			if(filter == null) {
				return;
			}
			//check if the filter is the last one
			if(i < filters.size() - 1) {
				if(filters.get(i+1)==null) {
					return;
				}
				filter.setNextFilter(filters.get(i+1));
				filter.process();
			}else {
				filter.output = new LinkedList<>();
				filter.process();
				while(!filter.output.isEmpty()) {
					System.out.println(filter.output.poll());
				}
			}
		}
		
	}
	
	
}
