package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

public class Server {
	public static final String ADDRESS = "localhost";
	public static final int PORT_NUMBER = 4001;
	private static ServerSocket serverSocket;
	private static Socket socket;
	static HashMap<String,ObjectOutputStream> usersmap;
	static Vector<String> userslist;
	
	public static void main(String[] args){
		
		usersmap = new HashMap<>();
		userslist = new Vector<String>();
		boolean isRunning = true;
		serverSocket = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			while(isRunning){
				socket = serverSocket.accept();
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());			
				Request request = (Request)ois.readObject();
	
				if(request.getCode() == RequestCode.CONNECT){
					try {
						Response response = new Response(ResponseCode.SUCCESS, null, null);
						oos.writeObject(response);
						usersmap.put(request.getName(), oos);
						userslist.addElement(request.getName());
						ClientHandler clienthandler = new ClientHandler(oos, ois, request);
						clienthandler.start();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			isRunning = false;
		}
	}
}

class ClientHandler extends Thread{
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String name;
	private boolean isRunning;
	
	public ClientHandler(ObjectOutputStream oos, ObjectInputStream ois, Request request){
		this.oos = oos;
		this.ois = ois;
		this.name = request.getName();
		this.isRunning = true;
	}
	
	@Override
	public void run(){
		while(isRunning){
			try {
				Request request = (Request)ois.readObject();
				if(request.getCode() == RequestCode.SEND_MESSAGE){
					sendMessageToClients(request.getMessage());
				}
				
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendMessageToClients(String message) {
		Response response = new Response(ResponseCode.NEW_MESSAGE, this.name, message);
		for(String user : Server.userslist){
			try {
				Server.usersmap.get(user).writeObject(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
