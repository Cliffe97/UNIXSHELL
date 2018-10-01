package cs131.pa1.filter.sequential;
import java.util.*;
import cs131.pa1.filter.*;

public class SequentialREPL {

	static String currentWorkingDirectory;
	
	
	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the Unix-ish command line.");
		// iterate until user types exit
		while(true){
			System.out.print(Message.NEWCOMMAND);
			String sequential = scanner.nextLine();
			if(sequential.equals("exit")){
				System.out.print(Message.GOODBYE);
				break;
			}
			sequential = sequential.trim();
			if(sequential.isEmpty()) {
				
			}else {
				SequentialCommandBuilder.processControl(sequential);
			}
		}
		scanner.close();
	}

}
