package network;

import java.io.Serializable;

/**
 * Request utilizes the command design pattern to wrap meta-data into a
 * serializable class that sends a message from the chat client to the server.
 * 
 * @author Joshua Riccio
 *
 */
public class Request implements Serializable {

	private static final long serialVersionUID = -4927401626139503305L;
	private RequestCode code;
	private String name;
	private String message;

	/**
	 * Constructor to create a request with a code, and a users name.
	 * 
	 * @param code
	 *            RequestCode enum
	 * @param name
	 *            The name for the request
	 */
	public Request(RequestCode code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * Constructor to create a request with a code, and a users name, and a
	 * message (String)
	 * 
	 * @param code
	 *            RequestCode enum
	 * @param name
	 *            The name for the request
	 * @param message
	 *            THe message to be sent to the server
	 */
	public Request(RequestCode code, String name, String message) {
		this.code = code;
		this.name = name;
		this.message = message;
	}

	/**
	 * Constructor to create a request that the user is exiting the system.
	 * 
	 * @param code
	 *            RequestCode enum
	 */
	public Request(RequestCode code) {
		this.code = code;
	}

	/**
	 * Gets the requests code
	 * 
	 * @return Returns the requests code
	 */
	public RequestCode getCode() {
		return this.code;
	}

	/**
	 * Gets the requests name
	 * 
	 * @return Returns the request's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the requests message
	 * 
	 * @return Returns the request's message
	 */
	public String getMessage() {
		return this.message;
	}

}
