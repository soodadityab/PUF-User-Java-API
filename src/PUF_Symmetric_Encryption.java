import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class PUF_Symmetric_Encryption extends PUF{

	public PUF_Symmetric_Encryption() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String encrypt_aes256_gcm(String EHR_Data, String PUF_Secret) {
		static String plainText = "Patient #1: Blood Pressure:120/90; Heart Rate: 78";
	    public static final int AES_KEY_SIZE = 256;
	    public static final int GCM_IV_LENGTH = 12;
	    public static final int GCM_TAG_LENGTH = 16;
	    
	    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(AES_KEY_SIZE);
       
        // Generate Key
        SecretKey key = keyGenerator.generateKey();
        byte[] IV = new byte[GCM_IV_LENGTH];
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);

        System.out.println("Original Text : " + plainText);
        
	}

	public String encrypt_aes256_gcm(String EHR_Data, String PUF_Secret) {
		
	}
	
	public String encrypt_aes128_gcm(String EHR_Data, String PUF_Secret) {
		
	}	
	
	public String encrypt_aes128_gcm(String EHR_Data, String PUF_Secret) {
		
	}
}
