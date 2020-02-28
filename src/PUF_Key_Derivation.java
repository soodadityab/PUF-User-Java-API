
public class PUF_Key_Derivation extends PUF_Hash{

	public PUF_Key_Derivation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private PUF_Hash Hash = new PUF_Hash();
	
	private PUF_Hash hkdf = new PUF_Hash();
	
	public String DeriveKey(String Salt, String Application_Id) {
		
		String hash = "test hkdf hash";
		
		return hash; 
	}
	
	

}
