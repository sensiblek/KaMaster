package kakao.mft.master;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kakao.mft.master.cli.KaMasterCli;
import kakao.mft.master.control.MasterServer;
import kakao.mft.master.props.MasterProperty;

public class KaMaster {

	private final Logger logger = LoggerFactory.getLogger(KaMaster.class);
	
	private MasterProperty prop;
	private MasterServer server;

	private List<Thread> agents;
	
	private KaMaster() {
		prop = MasterProperty.getInstance();
		agents = new ArrayList<>();
		Collections.synchronizedCollection(agents);
	}
	
	public void start() {
		logger.info("KaMaster Start...");
		server = new MasterServer(prop.getPort(), agents);
		server.start();
	}
	
	public void stop() {
		server.shutdown();
		logger.info("KaMaster stop...");
	}
	
	public List<Thread> getAgents() {
		return agents;
	}
	
	public void commandRunner() {
		start();
		new KaMasterCli(this).command();
	}

	public static void main(String[] args) {
		KaMaster master = new KaMaster();
		master.commandRunner();
	}
}
