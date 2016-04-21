package Network;

public class Request {
	
	private Object[] messages;
	
	public Request(RequestCode code, String name){
		this.messages = new String[2];
		this.messages[0] = code;
		this.messages[1] = name;
	}
	
	public Object[] getMessages(){
		return this.messages;
	}

}
