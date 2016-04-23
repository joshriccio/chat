package model;

import network.Password;

public class User {
	
	private String username;
	private String password;
	private String salt;
	boolean online;
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
		this.salt = Password.generateSaltValue();	
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
