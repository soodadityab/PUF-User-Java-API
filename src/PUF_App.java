

public class PUF_App {
	
	public static void main(String[] args){
		PUF	myPUF = new PUF("Aditya PUF"); 
		
		System.out.println("The Value of PUF is");
		
		System.out.println(myPUF.getHw_puf());
		
		PUF_Hash	my_hash = new PUF_Hash();
	
		String	test_salt = "My Salt for Test";
		String	test_appl_Id = "Electronic Health Record - SmartWatch - Heart Rate";
		my_hash.create_hash_sha256(test_salt, test_appl_Id);
	}
}
