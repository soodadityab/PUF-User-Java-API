/*
Author: Aditya Sood
Date: 01/05/2021

Class Functionality:
Initialize the 256-bit PUF secret
Since PUFs on CPUs are not currently available for distribution, this value has been simulated
When CPU embedded PUFs become accessible, this value will be provided by the physical properties of the PUF (rather than the arbitrary value created below)
*/


public class PUF {
	
	protected String hw_puf;
	
	public PUF() {
		super();
		this.hw_puf = "1011010100101010010101000010101010100010101000000001001010001110101101010010101001010100001010101010001010100000000100101000111010110101001010100101010000101010101000101010000000010010100011101011010100101010010101000010101010100010101000000001001010001110";
	}
	
}
