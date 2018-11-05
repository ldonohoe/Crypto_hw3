# Crypto_HW3

# Description of The Algorithm

In Blum Goldwasser Encryption, the key is computed using predetermined values p and q. These values must be chosen to be two prime numbers, which are not equal to eachother, and such that q = p = 3 mod 4. The public key, N, is then computed as p * q. The private key is the factorization of N, (p,q). 

# Encryption

Encryption is then as follows. Given a message m, converted to a string of L bits, a random number r, is chosen between 1 and N. This is used to compute X0, as r^2 mod N. Then, for each bit in m, we generate the keystream. For each value from 0 to L, being the number of bits, and set bi to the least significant bit of xi. The next value of x, x,i+1, is then equal to (xi)^2 mod N. The bits of the cipher are then computed using the keystream, where the cipher bit is equal to the corresponding bits in the message and keystream xored together. This is repeated for each bit, and the result is the encrypted message. 

# Decryption 

Decryption Follows in a similar fashion. It recieves the cipher stream, as well as the final value of x from encryption. This final value of x is used to reverse engineer the previous x values. This is done by essentially reversing the process from the encryption. Once the initial x value is calculated, the same process follows from encryption, and the plaintext is retrieved. 

# Other Remarks

For my implementation, I chose to use java, and the program can be run using javac HW3.java followed by java HW3. I found that there were some functions that were not built into Java, and I had to implement them myself. I used a basic rule of logarithms to implement log base 2, for ease of binary operations. Secondly, I ran into an issue with some of the calculations using modulus and high exponents. There is no build-in function for integers to have exponentiation with modulus at each step, so I found that the data type BigInteger instead had this functionality. Otherwise, the high values in the exponentiation would overflow the integer limit, and the encryption or decryption would fail.
