

public class PUF_App {
	
	public static void main(String[] args){
		
		String	salt = "My Salt for Test";
		String	appl_Id = "Electronic Health Record - SmartWatch - Heart Rate";
		
		PUF_Symmetric_Encryption mySymEncrypt = new PUF_Symmetric_Encryption();
		String encrypted_data = mySymEncrypt.encrypt_aes256_gcm("heart rate: 80, glucose: 180, steps/day: 2000", salt.getBytes(), appl_Id);
		
		String decrypted_data = mySymEncrypt.decrypt_aes256_gcm(encrypted_data, salt.getBytes(), appl_Id);
		
		System.out.println(decrypted_data);
		
		
	}
}
