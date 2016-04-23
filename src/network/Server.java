package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

/**
 * The server that processes requests from the chat client and returns
 * responses.
 * 
 * @author Joshua Riccio
 *
 */
public class Server {
	public static final String ADDRESS = "localhost";
	public static final int PORT_NUMBER = 4001;
	private static ServerSocket serverSocket;
	private static Socket socket;
	static HashMap<String, ObjectOutputStream> usersmap;
	static Vector<String> userslist;

	/**
	 * The main method of the server
	 * 
	 * @param args
	 *            arguments are not used.
	 */
	public static void main(String[] args) {
		System.out.println("Server: server initialized");
		usersmap = new HashMap<>();
		userslist = new Vector<String>();
		boolean isRunning = true;
		serverSocket = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;

		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			while (isRunning) {
				socket = serverSocket.accept();
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				Request request = (Request) ois.readObject();

				if (request.getCode() == RequestCode.CONNECT) {
					try {
						Response response = new Response(ResponseCode.SUCCESS, null, null);
						oos.writeObject(response);
						usersmap.put(request.getName(), oos);
						userslist.addElement(request.getName());
						ClientHandler clienthandler = new ClientHandler(ois, request);
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

/**
 * The client handler starts a new thread to process user requests until the
 * user disconnects from the network
 * 
 * @author Joshua Riccio
 *
 */
class ClientHandler extends Thread {
	private ObjectInputStream ois;
	private String name;
	private boolean isRunning;

	/**
	 * The constructor for the client handler.
	 * 
	 * @param ois
	 *            The user's object input stream
	 * @param request
	 *            the initial request to the server from the client
	 */
	public ClientHandler(ObjectInputStream ois, Request request) {
		this.ois = ois;
		this.name = request.getName();
		this.isRunning = true;
	}

	@Override
	public void run() {
		initialConnection();
		while (isRunning) {
			try {
				Request request = (Request) ois.readObject();
				if (request.getCode() == RequestCode.SEND_MESSAGE) {
					sendMessageToClients(request.getMessage());
				} else if (request.getCode() == RequestCode.REQUEST_USERS_ONLINE) {
					sendUsersList();
				} else if (request.getCode() == RequestCode.EXITING) {
					closeConnection();
				}

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendUsersList() {
		Response response = new Response(ResponseCode.USERS_LIST_SENT, this.name);
		response.addUserList(Server.userslist);
		try {
			Server.usersmap.get(this.name).writeObject(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void initialConnection() {
		System.out.println("Server: " + this.name + " has connected");
		Response response = new Response(ResponseCode.NEW_USER_CONNECTED, this.name);
		for (String user : Server.userslist) {
			try {
				Server.usersmap.get(user).writeObject(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void closeConnection() {
		System.out.println("Server: " + this.name + " has disconnected");
		Server.userslist.remove(this.name);
		this.isRunning = false;
		Response response = new Response(ResponseCode.USER_DISCONNECTED, this.name);
		for (String user : Server.userslist) {
			try {
				Server.usersmap.get(user).writeObject(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void sendMessageToClients(String message) {
		Response response = new Response(ResponseCode.NEW_MESSAGE, this.name, message);
		for (String user : Server.userslist) {
			try {
				Server.usersmap.get(user).writeObject(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
