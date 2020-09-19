package kakao.mft.master.cli;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kakao.mft.master.constant.MasterCmd;

public abstract class KaCliBase {
	
	private final Logger logger = LoggerFactory.getLogger(KaCliBase.class);
	
	abstract public void command();
	private Scanner scan = new Scanner(System.in);
	protected String lastCommand;
	
	public void closeScan() {
		scan.close();
	}
	
	protected MasterCmd getCommand() {
		lastCommand = scan.next();
		MasterCmd cmd;
		
		try {
			cmd = MasterCmd.valueOf(lastCommand.toUpperCase());				
		} catch (IllegalArgumentException ignore) { 
			cmd = MasterCmd.UNKOWN;
		}
		
		return cmd;
	}
	
	protected void printInvalidCommand() {
		logger.error("Invalid command '{}'", lastCommand);
	}
	
	protected boolean isDigitCommand() {
		return lastCommand.matches("^\\d+$");
	}
	
	protected int getValidCommandNumber(int size) {
		int num = Integer.parseInt(lastCommand);
		return (num < 1 || num > size) ? -1:num;	
	}
}
