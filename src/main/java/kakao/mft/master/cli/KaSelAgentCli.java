package kakao.mft.master.cli;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kakao.mft.master.constant.MasterCmd;
import kakao.mft.master.control.KaAgent;

public class KaSelAgentCli extends KaCliBase {
	
	private Logger logger = LoggerFactory.getLogger(KaSelAgentCli.class);
	
	private KaAgent agent;
	private List<Thread> agents;
	private List<String> fileList;
	private boolean isRun;
	
	public KaSelAgentCli(KaAgent agent, List<Thread> agents) {
		this.agent = agent;
		this.agents = agents;
		this.isRun = true;
	}
	
	@Override
	public void command() {
		agent.setCmdNodeCtl(this);
		
		int page = 0;
		
		while(isRun) {
			printUsage();
			
			MasterCmd cmd = getCommand();
			
			switch(cmd) {
			case X:
				isRun = false;
				agent = null;
				agents = null;
				break;
			case LIST:
				logger.info(" # {}", agent.getAgentName());
				getFileList(page);
				break;
			case NEXT:
				break;
			case PREV:
				break;
			default:
				if (isDigitCommand()) {
					if (fileList == null || fileList.isEmpty()) {
						logger.warn("No Files");
					} else {
						int fileIndex = getValidCommandNumber(fileList.size());
						if (fileIndex >= 0) {
							String fileName = fileList.get(fileIndex-1);
							new KaFileSendCli(agent, agents, fileName).command();
						} else {
							logger.error("Out of range");
						}
					}
				}
				break;
			}			
		}
	}
	
	public void printUsage() {
		logger.info("## Select Files to Send  ## - list, select number (x: quit)");
	}
	
	public void fileListHandler(List<String> fileList) {
		this.fileList = fileList;
		int index = 1;
		for(String fileName: fileList) {
			logger.info("{}. {}", index++, fileName);
		}
	}
	
	private void getFileList(int page) {
		agent.getFileList(page);
	}
}
