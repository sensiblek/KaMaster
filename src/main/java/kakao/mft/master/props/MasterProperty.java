package kakao.mft.master.props;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import kakao.mft.master.constant.MasterPropertyKey;

public class MasterProperty implements MasterPropertyKey {
	
	private Properties prop = new Properties();
	
	private MasterProperty() {
		load();
	}
	
	private synchronized void load() {
		String path = System.getProperty(MASTER_CONFIG);
		Path filePath = null;

		if (path == null) {	
			Path rootPath = Paths.get("");
			filePath = rootPath.resolve("config/" + MASTER_CONFIG_FILE);
		} else {
			filePath = Paths.get(path);
		}
		
		try {
			FileInputStream fis = new FileInputStream(filePath.toFile());
			prop.load(fis);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	private static class Singleton {
		private static final MasterProperty INSTANCE = new MasterProperty();
	}
	
	public static MasterProperty getInstance() {
		return Singleton.INSTANCE;
	}
	
	public int getPort() {
		return getPropInt(MASTER_PORT);
	}
	
	public int getShutdown() {
		return getPropInt(MASTER_SHUTDOWN);
	}
	
	private int getPropInt(String key) {
		String value = prop.getProperty(key);
		return Integer.parseInt(value);
	}
}
