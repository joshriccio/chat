package model;

import java.io.Serializable;

import network.Password;

/**
 * THe user class holds user data, hashed password, and salt
 * 
 * @author Joshua Riccio
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 4723737477026081669L;
	private String username;
	private String password;
	private String salt;
	boolean online;

	/**
	 * The User constructor
	 * 
	 * @param username
	 *            the users name
	 * @param password
	 *            the users password
	 */
	public User(String username, String password) {
		this.salt = Password.generateSaltValue();
		this.username = username;
		this.password = Password.generateSecurePassword(password, this.salt);
	}

	/**
	 * Returns the username
	 * 
	 * @return returns the username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Returns the salt
	 * 
	 * @return returns the salt
	 */
	public String getSalt() {
		return this.salt;
	}

	/**
	 * Authenticates the password
	 * 
	 * @param hashedpassword
	 *            the hashed password
	 * @return True if authenticated, false otherwise
	 */
	public boolean authenticatePassword(String hashedpassword) {
		if (this.password.equals(hashedpassword))
			return true;
		else
			return false;
	}
}
