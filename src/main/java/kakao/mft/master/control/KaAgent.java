package kakao.mft.master.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kakao.mft.master.cli.KaSelAgentCli;

public class KaAgent extends Thread {
	
	private final Logger logger = LoggerFactory.getLogger(KaAgent.class);
	
	private String agentName;
	private int receivePort;
	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	private List<Thread> agents;
	private List<String> fileList;
	private KaSelAgentCli cmd;
	
	public KaAgent(Socket socket, List<Thread> agents) {
		this.socket = socket;
		this.agents = agents;
		fileList = new ArrayList<>();
	}
	
	@Override
	public void run() {
		try {
			is = socket.getInputStream();
			os = socket.getOutputStream();
			dis = new DataInputStream(is);
			dos = new DataOutputStream(os);
		} catch (IOException ioe) {
			logger.error("KaAgent initialize fail {}", socket, ioe);
		}
		
		if (isSocketAlive()) {
			try {
				while(isSocketAlive()) {
					String clientMessage = dis.readUTF();
					
					if ("init".equals(clientMessage)) {
						agentName = dis.readUTF();
						receivePort = dis.readInt();
						logger.debug("{} agent connected to {}", agentName, socket);
						setName(agentName + "-Thread-" + this.getId());
					} else
					if (clientMessage.startsWith("success")) {
						String[] msgs = clientMessage.split(":");
						logger.debug("File transfer success " + msgs[1]);
					} else 
					if ("files".equals(clientMessage)) {
						int length = dis.readInt();
						fileList.clear();
						for(int i=0; i < length; i++) {
							fileList.add(dis.readUTF());
						}
						cmd.fileListHandler(fileList);
					}
				}				
			} catch (SocketException se) {
				logger.error("KaAgent error", se);
				removeThread();
			} catch (IOException e) {
				logger.error("KaAgent error", e);
				removeThread();
			}
		}
	}
	
	public void removeThread() {
		agents.remove(this);
	}
	
	public void setCmdNodeCtl(KaSelAgentCli cmd) {
		this.cmd = cmd;
	}
	
	public void getFileList(int page) {
		sendUTFMessage("files:"+page);
	}
	
	private void sendUTFMessage(String msg) {
		try {
			dos.writeUTF(msg);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private void sendInt(int num) {
		try {
			dos.writeInt(num);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public String getAgentName() {
		return this.agentName;
	}
	
	public InetAddress getInetAddress() {
		return socket.getInetAddress();
	}
	
	public int getReceivePort() {
		return receivePort;
	}
	
	public void sendToFile(String host, int port, String fileName) {
		sendUTFMessage("send");
		sendUTFMessage(host);
		sendInt(port);
		sendUTFMessage(fileName);
	}
	
	public boolean isSocketAlive() {
		return socket.isConnected() && !socket.isClosed();
	}
	
	public void closeSocket() {
		if (!socket.isClosed()) {
			logger.debug("Close {} connection", agentName);
			try {
				socket.close();			
			} catch (IOException ignore) {}
		}
	}
}
