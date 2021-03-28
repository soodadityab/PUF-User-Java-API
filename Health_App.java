 /*
 Author: Aditya Sood
 Date: 1/05/2021
 
 Class Functionality:
 This class represents the Health Application, which possess the plaintext EHRs for encryption
 A new PUF_Symmetric_Encryption object is initialized and declared
 Three parameters (EHRs, Salt, and App ID) are provided to the encrypt function to generate a key and subsequently use it to encrypt the EHRs
 This also allows the data to be decrypted by taking three parameters (Encrypted EHRs, Salt, App ID). Once again, the last two parameters are used to generate the AES-key for encryption/decryption

 */

public classi Health_App {
	
	public static void main(String[] args){
		
		String	salt = "My Salt for Test";
		String	appl_Id = "Electronic Health Record - SmartWatch - Heart Rate";
		
		PUF_Symmetric_Encryption mySymEncrypt = new PUF_Symmetric_Encryption();
		String encrypted_data = mySymEncrypt.encrypt_aes256_gcm("heart rate: 80, glucose: 180, steps/day: 2000", salt.getBytes(), appl_Id);
		
		String decrypted_data = mySymEncrypt.decrypt_aes256_gcm(encrypted_data, salt.getBytes(), appl_Id);
		
		System.out.println(decrypted_data);
				
	}
}
