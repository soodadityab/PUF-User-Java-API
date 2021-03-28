# PUF-User-Java-API

Java APIs that enable software applications to interact with and use hardware Physically Unclonable Functions (PUFs) for secure key derivation, encryption and hash operations. These APIs are directly executed in a trusted enclave for enhanced security and privacy.

Research Problem: 
Billions of consumer Electronic-Health-Records (EHRs) are continuously generated via smart health-tracking devices. Constant exploitation of these private records leads to ransomware, identity theft, and health fraud. Existing cryptosystems tasked with protecting these records are weak because they allow attackers to obtain the key from the software with relative ease. I propose a novel hardware cryptosystem that uses Physically Unclonable Functions with Confidential Computing for enhancing the security of user-EHRs.

Threat Model: 
Trusted Computing Base for EHRs: Health App, OS, PUF, Software Guard Extensions (SGX), CPU
Trusted Computing Base for encryption keys: CPU, PUF, SGX. THE OS AND BIOS ARE NOT TRUSTED
Security features address: Software side channels, local malware, OS-privileged attacks

Novel Hardware Cryptosystem: 
Due to inherently unpredictable silicon fluctuations during  manufacturing, each Physically Unclonable Function is a random, unique, immutable, and unclonable 'digital fingerprint'. The PUF employs its underlying physical characteristics to generate a secret 256-bit value. I propose that the PUF is embedded on the CPU, where this secret contributes towards generating a secure AES key.
The PUF secret is first hashed using SHA-512, and the resulting digest is sent to a key derivation function (KDF). This, along with the Application ID and Salt (provided by the Health App sending the EHRs), are all inputted to the KDF to generate a secure AES key.

Although the application seed components are essential to ensuring the uniqueness of each key (using one key for all encryption tasks makes it easier to break the encryption), the key security is largely provided by the PUF. Since KDFs require the same seed to produce the same key (such as for decryption), it is crucial that each PUF maintains its physical properties -and hence its secret- throughout these tasks. Existing protocols for PUF error correction are able to significantly minimize this risk, making this concern posed toward the PUF-based key derivation negligible. 

Upon being derived, the symmetric encryption key is used with Intel AES-NI hardware encryption to securely encrypt the medical data records. AES-NI directly runs the encryption rounds on the CPU in constant time, defending against various software side channel attacks (namely timing and cache side channels). In addition, my data indicated that incorporating AES-NI in my system accelerates the rate of encryption for all modes and EHR block sizes, demonstrating that my system enhances both the security and performance when compared to the control (software AES implementation).

As outlined in the threat model, my cryptosystem is designed to protect the key from software side channels (through the implementation of AES-NI), local malware, and OS-privileged root attacks. The latter two are defended against by my specific usage of Intel SGX.

SGX is Intel's instruction set for implementing confidential computing on Intel CPU. SGX ensures that when it is callibrated, the BIOS will set aside a portion of the device's memory for trusted operations that are only accessible to the CPU. The CPU will also perform access control operations and encryption on the secure computing enclave to prevent higher privileged software like the OS and BIOS from accessing the contents of this memory.

All the aforementioned cryptographic operations (including key derivation and encryption) that are necessary for encrypting the records transpire solely within this protected enclave:
- The key is securely derived in the enclave using a trusted built-in hardware path from the PUF that contains the 256-bit secret
- The plaintext EHRs are delivered to the enclave via a hardware path from the Health App
- AES-NI encryption using the PUF-derived key and integrity protection are performed on the EHRs
- The EHR ciphertext is sent to the application for storage

For decryption:
- EHR ciphertext is sent from the Health App to the enclave
- The KDF is seeded with the same inputs, resulting in the rederivation of the encryption key that was used to encrypt the records
- EHRs are decrypted and sent to the Health App through the trusted hardware path, arriving in the clear text. This is why the OS and Health App must be trusted for the EHRs, but not for the key*
- The EHRs are then displayed to the user

In the event that an adversary manages to corrupt the OS or impmlant malware on the device, they will find that they cannot breach the key from the software since the key's entire life-cycle is restricted to the CPU-protected SGX encalve.

Intel SGX SDK GitHub is here: https://github.com/intel/linux-sgx

API Class Hierarchy: 
To determine which cryptographic algorithms were optimal for my design, I developed a set of APIs that grant functionality to my system (software prototype) and allow me to ultimately test it under simulated conditions. The class hierarchy models my system architecture. I tested and subsequently analyzed the performance of different hash, software encryption, and hardware encryption algorithms in my prototype, using the APIs to execute features like key derivation. OpenSSL and JCE were used to import the cryptography algorithms.

After testing various encryption and hash algorithms, my results indicated the following:
- AES-NI, in addition to being more secure, outperformed AES for all EHR block sizes tested
- AES-NI-256-GCM was chosen for encryption due to its high throughput, strong key size, and the additional bonus of authenticated encryption (using GHASH)
- SHA-512 was selected for hash due to its ability to maintain a strong efficiency without compromising on its collision resistance

Enhancements to Intel CPU: 
Through my research, I have identified the following security enhancements that can be made to the Intel CPUs:
- There is a need for SHA-512 new instructions that permit hardware hashing
- Hardware protected path from the SGX to the PUF, which can be accessed by SGX enclaves
- Hardware protected path from the IO (display, keyboard, etc) to the SGX enclave. This removes the Health Application from the trusted computing base for EHRs.
