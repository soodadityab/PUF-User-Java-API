import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

public class PUF_Symmetric_Encryption{

	public PUF_Symmetric_Encryption() {
		super();
		// constructor stub
	}

	public String encrypt_aes256_gcm(String EHR_Data, byte[] salt, String app_id) {
		PUF_Hash myPUF = new PUF_Hash(); 
		String myPufHash = myPUF.create_hash_sha256(salt, app_id);
		
		SecretKey key = null;
		byte[] cipherText = null;
		
		try {
			
		

		    PBEKeySpec spec = new PBEKeySpec(myPufHash.toCharArray(), salt, 1000, 256 * 8);
		    key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec);
		    //Cipher aes = Cipher.getInstance("AES");
		    //aes.init(Cipher.ENCRYPT_MODE, key);
			
			
		   // AlgorithmParameterSpec hkdfSpec = new HkdfParameterSpec(myPufHash, salt, Application_id);
		    //KeyDerivation kdf = KeyDerivation.getInstance("HkdfSHA256", hkdfSpec);
		    
		    // Next specify type and length of the desired key and place into parameter spec
		    //DerivedKeyParameterSpec keySpec = new DerivedKeyParameterSpec("AES", 32);

		    // Derive the key
		    //SecretKey derivedAesKey = kdf.deriveKey(keySpec);
		
			
			} catch (GeneralSecurityException ge) {
				System.out.println("Ouch! Caught " + ge);
		}
		
		byte[] IV = new byte[16];
		try {
		// Generating IV.
        SecureRandom random = new SecureRandom();
        random.nextBytes(IV);
        
        System.out.println("Original Text  : "+ EHR_Data);
        
        cipherText = encrypt(EHR_Data.getBytes(),key, IV);
        
		}
		catch (Exception e) {
			System.out.println("general exception caught while encrypting");
		}
        byte[] IvAndCipherText = new byte[16 + cipherText.length];
        System.arraycopy(IV, 0, IvAndCipherText, 0, IV.length);
        System.arraycopy(cipherText, 0, IvAndCipherText, IV.length, cipherText.length);
        String encrypted = Base64.getEncoder().encodeToString(IvAndCipherText);
        System.out.println("Encrypted Text : "+encrypted);
        
        return encrypted;
		
	}
	
	public String decrypt_aes256_gcm(String IvAndCipherText, byte[] salt, String app_id) {
		PUF_Hash myPUF = new PUF_Hash(); 
		String myPufHash = myPUF.create_hash_sha256(salt, app_id);
		
		SecretKey key = null;
		
		try {
			PBEKeySpec spec = new PBEKeySpec(myPufHash.toCharArray(), salt, 1000, 256 * 8);
		    key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(spec);
		    
			} catch (GeneralSecurityException ge) {
				System.out.println("Ouch! Caught " + ge);
				
		}
		String decryptedText = null;
		try {
		byte[] IV = Arrays.copyOfRange(IvAndCipherText.getBytes(), 0, 15);
		
		byte[] cipherText = Arrays.copyOfRange(IvAndCipherText.getBytes(), 16, IvAndCipherText.getBytes().length);
	    decryptedText = decrypt(cipherText, key, IV);
        System.out.println("DeCrypted Text : "+decryptedText);
        
		}
		catch (Exception e) {
			System.out.println("general exception caught while decrypting");
		} 
		return decryptedText;
	}
		
	private static byte[] encrypt (byte[] plaintext,SecretKey key,byte[] IV ) throws Exception
    {
        //Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        //Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        
        //Create IvParameterSpec
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        
        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        
        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plaintext);
        System.out.println("cipher is: "+ cipherText);
        return cipherText;
    }	
	
	
	private static String decrypt (byte[] cipherText, SecretKey key,byte[] IV) throws Exception
    {
        //Get Cipher Instance
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        
        //Create SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        
        //Create IvParameterSpec
        IvParameterSpec ivSpec = new IvParameterSpec(IV);
        
        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        
        //Perform Decryption
        byte[] decryptedText = cipher.doFinal(cipherText);
        
        return new String(decryptedText);
    }
	
	
	
	
//	
//	public String encrypt_aes256_gcm(String EHR_Data, SecretKey PUF_Secret) {
//		static String plainText = "Patient #1: Blood Pressure:120/90; Heart Rate: 78";
//	    public static final int AES_KEY_SIZE = 256;
//	    public static final int GCM_IV_LENGTH = 12;
//	    public static final int GCM_TAG_LENGTH = 16;
//	    
//	    KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(AES_KEY_SIZE);
//       
//        // Generate Key
//        SecretKey key = keyGenerator.generateKey();
//        byte[] IV = new byte[GCM_IV_LENGTH];
//        SecureRandom random = new SecureRandom();
//        random.nextBytes(IV);
//
//        System.out.println("Original Text : " + plainText);
//        
//	}

//	public String encrypt_decrypt_aes128_gcm(String EHR_Data, String PUF_Secret) {
//		// Obtain a AES/GCM cipher to do the enciphering. Must obtain
//        // and use the Parameters for successful decryption.
//        
//		// Create a 128-bit AES key.
//        KeyGenerator kg = KeyGenerator.getInstance("AES");
//        kg.init(128);
//        SecretKey key = kg.generateKey();
//		
//		Cipher encCipher = Cipher.getInstance("AES/GCM/NOPADDING");
//        encCipher.init(Cipher.ENCRYPT_MODE, key);
//        byte[] enc = encCipher.doFinal(data);
//        AlgorithmParameters ap = encCipher.getParameters();
//
//        // Obtain a similar cipher, and use the parameters.
//        Cipher decCipher = Cipher.getInstance("AES/GCM/NOPADDING");
//        decCipher.init(Cipher.DECRYPT_MODE, key, ap);
//        byte[] dec = decCipher.doFinal(enc);
//
//        if (Arrays.compare(data, dec) != 0) {
//            throw new Exception("Original data != decrypted data");
//		
//		return "";
//		
//	}	
	
//	public String encrypt_aes256_cbc(String EHR_Data, String PUF_Secret) {
//		
//	}
//	
//	
//	public String encrypt_aes128_cbc(String EHR_Data, String PUF_Secret) {
//		
//	}
}
