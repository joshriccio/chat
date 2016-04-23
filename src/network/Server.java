package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

import model.User;

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
	static HashMap<String, ObjectOutputStream> userStreamMap;
	static HashMap<String, User> userNameMap;
	static Vector<String> onlinelist;
	private static Vector<User> userlist;

	/**
	 * The main method of the server
	 * 
	 * @param args
	 *            arguments are not used.
	 */
	public static void main(String[] args) {
		System.out.println("Server: server initialized");
		userStreamMap = new HashMap<>();
		userNameMap = new HashMap<>();
		userlist = new Vector<User>();
		onlinelist = new Vector<String>();
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
				if (request.getCode() == RequestCode.GET_SALT) {
					processSaltRequest(oos, request);
				} else if (request.getCode() == RequestCode.CONNECT) {
					processNewConnection(ois, oos, request);
				} else if (request.getCode() == RequestCode.NEW_ACCOUNT) {
					processNewAccount(oos, request);
				}

			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			isRunning = false;
		}
	}

	private static void processSaltRequest(ObjectOutputStream oos, Request request) {
		if (userNameMap.containsKey(request.getName())) {
			try {
				Response response = new Response(ResponseCode.SUCCESS);
				response.setSalt(userNameMap.get(request.getName()).getSalt());
				oos.writeObject(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void processNewAccount(ObjectOutputStream oos, Request request) {
		if (!userlist.contains(request.getUser().getUsername())) {
			try {
				Response response = new Response(ResponseCode.SUCCESS);
				userlist.addElement(request.getUser());
				userNameMap.put(request.getUser().getUsername(), request.getUser());
				oos.writeObject(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				Response response = new Response(ResponseCode.FAILED, null, null);
				oos.writeObject(response);
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void processNewConnection(ObjectInputStream ois, ObjectOutputStream oos, Request request) {
		try {
			if (userNameMap.get(request.getName()).authenticatePassword(request.getMessage())) {
				Response response = new Response(ResponseCode.SUCCESS, null, null);
				oos.writeObject(response);
				userStreamMap.put(request.getName(), oos);
				onlinelist.addElement(request.getName());
				ClientHandler clienthandler = new ClientHandler(ois, request);
				clienthandler.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
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
		synchronized (Server.onlinelist) {
			Response response = new Response(ResponseCode.USERS_LIST_SENT, this.name);
			response.addUserList(Server.onlinelist);
			try {
				Server.userStreamMap.get(this.name).writeObject(response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void initialConnection() {
		synchronized (Server.onlinelist) {
			System.out.println("Server: " + this.name + " has connected");
			Response response = new Response(ResponseCode.NEW_USER_CONNECTED, this.name);
			for (String user : Server.onlinelist) {
				if (!user.equals(this.name)) {
					try {
						Server.userStreamMap.get(user).writeObject(response);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private void closeConnection() {
		synchronized (Server.onlinelist) {
			System.out.println("Server: " + this.name + " has disconnected");
			Server.onlinelist.remove(this.name);
			this.isRunning = false;
			Response response = new Response(ResponseCode.USER_DISCONNECTED, this.name);
			for (String user : Server.onlinelist) {
				try {
					Server.userStreamMap.get(user).writeObject(response);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void sendMessageToClients(String message) {
		synchronized (Server.onlinelist) {
			Response response = new Response(ResponseCode.NEW_MESSAGE, this.name, message);
			for (String user : Server.onlinelist) {
				try {
					Server.userStreamMap.get(user).writeObject(response);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
