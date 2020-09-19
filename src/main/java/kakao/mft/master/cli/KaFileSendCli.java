package kakao.mft.master.cli;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kakao.mft.master.constant.MasterCmd;
import kakao.mft.master.control.KaAgent;

public class KaFileSendCli extends KaCliBase {
	
	private final Logger logger = LoggerFactory.getLogger(KaFileSendCli.class);
	
	private KaAgent agent;
	private List<Thread> agents;
	private String fileName;
	
	private boolean isRun;
	
	public KaFileSendCli(KaAgent agent, List<Thread> agents, String fileName) {
		this.agent = agent;
		this.agents = agents;
		this.fileName = fileName;
		this.isRun = true;
	}
	
	@Override
	public void command() {
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
				getTargetAgents();
				break;
			default:
				if (isDigitCommand()) {
					int agentIndex = getValidCommandNumber(agents.size()-1);
					if (agentIndex >= 0) {
						int index = 0;
						KaAgent targetAgent = null;
						for (Thread t:agents) {
							if (t.equals(agent)) continue;
							if (++index == agentIndex) {
								targetAgent = (KaAgent)t;
								break;
							}
						}
						if (targetAgent != null) {
							String host = targetAgent.getInetAddress().getHostAddress();
							int port = targetAgent.getReceivePort();
							logger.debug("{}, {}, {}", targetAgent.getAgentName(), host, port);
							agent.sendToFile(host, port, fileName);
						}
					} else {
						logger.error("Out of range");						
					}
				}
				break;
			}
		}
	}
	
	public void printUsage() {
		logger.info("## Select an agent to transfer files  ## - list, select number (x: quit)");
	}
	
	private void getTargetAgents() {
		int index = 0;
		for(Thread t: agents) {
			if (t.equals(agent)) continue;
			logger.info("{}. {}", ++index, ((KaAgent)t).getAgentName());
		}
		if (index == 0) {
			logger.error("No list of targeted agents");
		}
	}
}
