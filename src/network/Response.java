package network;

import java.io.Serializable;

public class Response implements Serializable{

	private static final long serialVersionUID = 7044358694627183903L;
	private String name;
	private String message;
	private ResponseCode code;
	
	public Response(ResponseCode code, String name, String message) {
		this.setName(name);
		this.setMessage(message);
		this.setCode(code);
	}

	public Response(ResponseCode userDisconnected, String name) {
		this.code = userDisconnected;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseCode getCode() {
		return code;
	}

	public void setCode(ResponseCode code) {
		this.code = code;
	}
}
