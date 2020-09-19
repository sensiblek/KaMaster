package kakao.mft.master.cli;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kakao.mft.master.constant.MasterCmd;
import kakao.mft.master.control.KaAgent;

public class KaAgentCli extends KaCliBase {
	
	private final Logger logger = LoggerFactory.getLogger(KaAgentCli.class);
	
	private List<Thread> agents;
	private boolean isRun;
	
	public KaAgentCli(List<Thread> agents) {
		this.agents = agents;
		this.isRun = true;
	}
	
	@Override
	public void command() {
		while(isRun) {
			
			printUsage();
			
			if (agents.isEmpty()) {
				logger.warn("No Agents");
				isRun = false;
				agents = null;
				return;
			} else {
				int index = 1;
				for(Thread t:agents) {
					String agentName = ((KaAgent)t).getAgentName();
					logger.info("{}. {}", index++, agentName);
				}
			}
			
			MasterCmd cmd = getCommand();
			
			switch(cmd) {
			case X:
				isRun = false;
				agents = null;
				break;
			default:
				if (isDigitCommand()) {
					int agentIndex = getValidCommandNumber(agents.size());
					if (agentIndex >= 0) {
						KaAgent agent = (KaAgent)agents.get(agentIndex-1);
						new KaSelAgentCli(agent, agents).command();
					} else {
						logger.error("Out of range");
					}
				} else {
					printInvalidCommand();
				}
				break;
			}
		}
	}
	
	public void printUsage() {
		logger.info("## Select Send File Agent  ## - select number (x: quit)");
	}
}
