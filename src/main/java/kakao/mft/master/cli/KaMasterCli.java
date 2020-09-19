package kakao.mft.master.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kakao.mft.master.KaMaster;
import kakao.mft.master.constant.MasterCmd;

public class KaMasterCli extends KaCliBase {
	
	private final Logger logger = LoggerFactory.getLogger(KaMasterCli.class);
	
	private KaMaster master;
	
	public KaMasterCli(KaMaster master) {
		this.master = master;
	}
	
	@Override
	public void command() {
		while(true) {
			printUsage();
			MasterCmd cmd = getCommand();
			
			switch(cmd) {
			case EXIT:
				master.stop();
				master = null;
				closeScan();
				System.exit(0);
				break;
			case LIST:
				new KaAgentCli(master.getAgents()).command();
				break;
			default:
				printInvalidCommand();
				break;
			}
		}
	}
	
	public void printUsage() {
		logger.info("## Master Command ## - list, exit");
	}
	
}
