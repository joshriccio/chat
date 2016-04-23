package model;

import java.io.Serializable;

import network.Password;

public class User implements Serializable{
	
	private String username;
	private String password;
	private String salt;
	boolean online;
	
	public User(String username, String password){
		this.salt = Password.generateSaltValue();
		this.username = username;
		this.password = Password.generateSecurePassword(password, this.salt);	
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getSalt(){
		return this.salt;
	}
	
	public boolean authenticatePassword(String hashedpassword){
		if(this.password.equals(hashedpassword))
			return true;
		else
			return false;
	}
}
