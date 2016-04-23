package network;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class Password {

	/**
	 * This method generates a secure password
	 * 
	 * @param password
	 *            The password that is not hashed
	 * @param salt
	 *            The salt value for the user
	 * @return a secure password
	 */
	public static String generateSecurePassword(String pass, String salt){

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(salt.getBytes());
		byte[] bytes = md.digest(pass.getBytes());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	/**
	 * This method generates a salt value.
	 * 
	 * @return a salted string
	 */
	public static String generateSaltValue() {
		SecureRandom sr = null;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
		}
		byte[] saltVal = new byte[16];
		sr.nextBytes(saltVal);
		return saltVal.toString();
	}

}