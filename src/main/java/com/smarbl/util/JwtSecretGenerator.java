/**
 * 
 */
package com.smarbl.util;

import java.util.Base64;
import java.security.SecureRandom;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey
/**
public class JwtSecretGenerator {
	public static void main(String[] args) {
		byte[] key = new byte[32]; // 32 bytes = 256-bit key (recommended)
		new SecureRandom().nextBytes(key);
		String secretKey = Base64.getEncoder().encodeToString(key);
		System.out.println("Generated JWT Secret Key: " + secretKey);
	}
}
 
public class JwtKeyGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Secure JWT Secret Key: " + encodedKey);
    }
}**/

//6vHdNcR8v2v0Kp/j1M5P3Z+ZkE27hQ==
;

public class JwtSecretGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("Secure JWT Secret Key: " + encodedKey);
    }//Secure JWT Secret Key: cxy0iPv+eSznpWTEKraFTRrOuOfQkfRJUNwmcwvmTY8=

}