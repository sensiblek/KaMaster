package kakao.mft.master.dbms.h2.dto;

public class TransRequestDto {
	private int id;
	private String origin;
	private String target;
	private String fileName;
	private String fileHash;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileHash() {
		return fileHash;
	}
	public void setFileHash(String fileHash) {
		this.fileHash = fileHash;
	}
	@Override
	public String toString() {
		return "TransRequestDto [id=" + id + ", origin=" + origin + ", target=" + target + ", fileName=" + fileName
				+ ", fileHash=" + fileHash + "]";
	}
}
