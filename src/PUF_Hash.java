import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PUF_Hash extends PUF{

	private	String object_salt = "My Object Salt";
	
	public PUF_Hash() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String create_hash_sha256(String Salt, String Appl_Id) {
		
		//Creating the MessageDigest object  
	    MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("MessageDigest not instantiated");
			e.printStackTrace();
		}

	      //Passing data to the created MessageDigest Object
	      md.update(Salt.getBytes());
	      md.update(Appl_Id.getBytes());
	      md.update(object_salt.getBytes());
	      md.update(getHw_puf().getBytes());
	      
	      
	      //Compute the message digest
	      byte[] digest = md.digest();      
	      System.out.println(digest);  
	     
	      //Converting the byte array in to HexString format
	      StringBuffer hexString = new StringBuffer();
	      
	      for (int i = 0;i<digest.length;i++) {
	         hexString.append(Integer.toHexString(0xFF & digest[i]));
	      }
	      System.out.println("Hex format : " + hexString.toString());  
		
		return object_salt;
		
	}
	
}
