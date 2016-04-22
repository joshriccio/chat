package Network;

import java.io.Serializable;

public class Request implements Serializable{
	
	private static final long serialVersionUID = -4927401626139503305L;
	private RequestCode code;
	private String name;
	private String message;
	
	public Request(RequestCode code, String name){
		this.code = code;
		this.name = name;
	}
	
	public Request(RequestCode code, String name, String message){
		this.code = code;
		this.name = name;
		this.message = message;
	}
	
	public Request(RequestCode exiting) {
		this.code = exiting;
	}

	public RequestCode getCode(){
		return this.code;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getMessage(){
		return this.message;
	}

}
