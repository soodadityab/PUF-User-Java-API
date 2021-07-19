# PUF-User-Java-API

Java APIs that enable software applications to interact with and use hardware Physically Unclonable Functions (PUFs) for secure key derivation, encryption and hash operations. These APIs are directly executed in a trusted enclave for enhanced security and privacy.

API Class Hierarchy: 
To determine which cryptographic algorithms were optimal for my design, I developed a set of APIs that grant functionality to my system (software prototype) and allow me to ultimately test it under simulated conditions. The class hierarchy models my system architecture. I tested and subsequently analyzed the performance of different hash, software encryption, and hardware encryption algorithms in my prototype, using the APIs to execute features like key derivation. OpenSSL and JCE were used to import the cryptography algorithms.
