package network;

import java.io.Serializable;
import java.util.Vector;

import model.User;

/**
 * Response utilizes the command design pattern to wrap meta-data into a
 * serializable class that sends a message from the server to the client.
 * 
 * @author Joshua Riccio
 *
 */
public class Response implements Serializable {

	private static final long serialVersionUID = 280307447949100811L;
	private String name;
	private String message;
	private ResponseCode code;
	private Vector<User> userslist;
	private String salt;

	/**
	 * Constructor to create a response with a code, and a users name, and a
	 * message (String).
	 * 
	 * @param code
	 *            ResponseCode enum
	 * @param name
	 *            The name for the response
	 */
	public Response(ResponseCode code, String name, String message) {
		this.name = name;
		this.message = message;
		this.code = code;
	}

	/**
	 * Constructor to create a response with a code, and a users name.
	 * 
	 * @param code
	 *            ResponseCode enum
	 * @param name
	 *            The name for the response
	 */
	public Response(ResponseCode code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * Constructor to create a response with a code.
	 * 
	 * @param code
	 *            ResponseCode enum
	 */
	public Response(ResponseCode code) {
		this.code = code;
	}

	/**
	 * Gets the requests code
	 * 
	 * @return Returns the response's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the requests code
	 * 
	 * @return Returns the response's message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the requests code
	 * 
	 * @return Returns the response's code
	 */
	public ResponseCode getCode() {
		return code;
	}

	/**
	 * Sets the list of users
	 * 
	 * @param userslist
	 *            the list of users online
	 */
	public void addUserList(Vector<User> userslist) {
		this.userslist = userslist;
	}

	/**
	 * Returns the user list from this response
	 * 
	 * @return the user list from this response
	 */
	public Vector<User> getUserList() {
		return this.userslist;
	}

	/**
	 * Gets the salt from the request
	 * 
	 * @return the salt
	 */
	public String getSalt() {
		return this.salt;
	}

	/**
	 * Sets the requests salt
	 * 
	 * @param salt
	 *            the salt value
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
}
