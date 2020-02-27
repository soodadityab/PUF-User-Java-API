
public class PUF {
	public PUF(String hw_puf) {
		super();
		this.hw_puf = hw_puf;
	}
	
	public PUF() {
		super();
		this.hw_puf = "My Test PUF";
	}
	
	private	String hw_puf;

	public String getHw_puf() {
		return hw_puf;
	}

	public void setHw_puf(String hw_puf) {
		this.hw_puf = hw_puf;
	}
	
}
