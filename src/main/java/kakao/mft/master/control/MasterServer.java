package kakao.mft.master.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MasterServer extends Thread {
	
	private final Logger logger = LoggerFactory.getLogger(MasterServer.class);
	
	private int port;
	private ServerSocket serverSocket;
	private boolean isRun;
	private List<Thread> agents;
	
	public MasterServer(int port, List<Thread> agents) {
		this.port = port;
		this.isRun = true;
		this.agents = agents;
		this.setName("MFTMasterServer-Thread-"+this.getId());
	}
	
	public boolean shutdown() {
		isRun = false;
		try {
			serverSocket.close();			
		} catch (IOException ignore) {}
		for(Thread t:agents) {
			((KaAgent)t).closeSocket();
		}
		return true;
	}
	
	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(port, 200);
			logger.info("MasterServer start {}", serverSocket);	
			while(isRun) {
				Socket socket = serverSocket.accept();
				Thread agent = new KaAgent(socket, agents);
				agents.add(agent);
				agent.start();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
