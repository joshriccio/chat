package network;

import java.io.Serializable;

/**
 * Response utilizes the command design pattern to wrap meta-data into a
 * serializable class that sends a message from the server to the client.
 * 
 * @author Joshua Riccio
 *
 */
public class Response implements Serializable {

	private static final long serialVersionUID = 7044358694627183903L;
	private String name;
	private String message;
	private ResponseCode code;

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
}
