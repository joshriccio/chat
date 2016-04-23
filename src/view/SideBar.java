package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import network.Request;
import network.RequestCode;
import network.Response;
import network.ResponseCode;
import network.Server;

/**
 * 
 * @author Joshua Riccio
 *
 */
public class SideBar extends JFrame {

	private static final long serialVersionUID = -2585106853637231791L;
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private DefaultListModel<String> listmodel;
	private JList<String> list;
	private JScrollPane scrollpane;
	private ChatWindow chatwindow;
	private String username;

	/**
	 * Constructor for FriendsList
	 */
	public SideBar() {
		setupLogin();
	}

	private void setupLogin() {
		this.setTitle("Net Chat");
		this.setSize(400, 300);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setLocationRelativeTo(null);

		JLabel userlabel = new JLabel("Name");
		this.add(userlabel);

		JTextField usernamefield = new JTextField();
		usernamefield.setPreferredSize(new Dimension(200, 25));
		this.add(usernamefield);

		JLabel passlabel = new JLabel("Password");
		this.add(passlabel);

		JTextField passfield = new JTextField();
		passfield.setPreferredSize(new Dimension(200, 25));
		this.add(passfield);

		JButton loginbtn = new JButton("Connect");
		this.add(loginbtn);

		JLabel newaccount = new JLabel("Create a new account");
		this.add(newaccount);

		Insets insets = this.getInsets();
		Dimension size = usernamefield.getPreferredSize();
		usernamefield.setBounds(150 + insets.left, 50 + insets.top, size.width, size.height);
		size = userlabel.getPreferredSize();
		userlabel.setBounds(30 + insets.left, 50 + insets.top, size.width, size.height);
		size = passfield.getPreferredSize();
		passfield.setBounds(150 + insets.left, 80 + insets.top, size.width, size.height);
		size = passlabel.getPreferredSize();
		passlabel.setBounds(30 + insets.left, 80 + insets.top, size.width, size.height);
		size = loginbtn.getPreferredSize();
		loginbtn.setBounds(170 + insets.left, 110 + insets.top, size.width, size.height);
		size = newaccount.getPreferredSize();
		newaccount.setBounds(150 + insets.left, 210 + insets.top, size.width, size.height);

		loginbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SideBar.this.remove(userlabel);
				SideBar.this.remove(usernamefield);
				SideBar.this.remove(loginbtn);
				SideBar.this.remove(passlabel);
				SideBar.this.remove(passfield);
				SideBar.this.remove(newaccount);
				setupWindow(usernamefield.getText());
				connectToServer();
				setupChatService();
			}
		});

		newaccount.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SideBar.this.remove(newaccount);
				SideBar.this.remove(loginbtn);
				SideBar.this.setSize(400, 310);
				JLabel confirmpass = new JLabel("Confirm Password");
				SideBar.this.add(confirmpass);

				JTextField confirmpassfield = new JTextField();
				confirmpassfield.setPreferredSize(new Dimension(200, 25));
				SideBar.this.add(confirmpassfield);

				Insets insets = SideBar.this.getInsets();
				Dimension size = usernamefield.getPreferredSize();
				size = confirmpass.getPreferredSize();
				confirmpass.setBounds(28 + insets.left, 100 + insets.top, size.width, size.height);
				size = confirmpassfield.getPreferredSize();
				confirmpassfield.setBounds(148 + insets.left, 100 + insets.top, size.width, size.height);

				JButton create = new JButton("Create new account");
				size = create.getPreferredSize();
				create.setBounds(148 + insets.left, 140 + insets.top, size.width, size.height);
				SideBar.this.add(create);
				create.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						JOptionPane.showMessageDialog(null, "Account Created");
						SideBar.this.remove(confirmpass);
						SideBar.this.remove(confirmpassfield);
						SideBar.this.remove(create);
						SideBar.this.remove(userlabel);
						SideBar.this.remove(usernamefield);
						SideBar.this.remove(passlabel);
						SideBar.this.remove(passfield);
						setupLogin();
					}
				});
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
	}

	private void setupChatService() {
		this.chatwindow = new ChatWindow(this.username, this.oos);
	}

	private void connectToServer() {
		Request request = new Request(RequestCode.CONNECT, this.username);
		try {
			socket = new Socket(Server.ADDRESS, Server.PORT_NUMBER);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			oos.writeObject(request);
			Response response = (Response) ois.readObject();
			if (response.getCode() == ResponseCode.SUCCESS) {
				ServerListener serverlistener = new ServerListener();
				serverlistener.start();
				request = new Request(RequestCode.REQUEST_USERS_ONLINE);
				oos.writeObject(request);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void setupWindow(String username) {
		this.setTitle("Net Chat");
		this.setSize(300, 630);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(30, 30);
		this.username = username;
		this.listmodel = new DefaultListModel<String>();
		this.list = new JList<String>(listmodel);
		this.scrollpane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setPreferredSize(new Dimension(200, 600));
		setLayout(new BorderLayout());
		this.add(scrollpane, BorderLayout.CENTER);
		addMenus();
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				Request request = new Request(RequestCode.EXITING);
				try {
					oos.writeObject(request);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}
		});
	}

	private void addMenus() {
		JPopupMenu menu = new JPopupMenu();
		JMenuItem messageItem = new JMenuItem("Send message");
		menu.add(messageItem);
		messageItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				chatwindow.setVisible(true);
			}

		});

		this.list.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					menu.show(e.getComponent(), e.getX(), e.getY());
				}

			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		});

	}

	private class ServerListener extends Thread {
		private boolean isRunning = true;

		@Override
		public void run() {
			while (isRunning) {
				Response response;
				try {
					response = (Response) ois.readObject();
					if (response.getCode() == ResponseCode.USERS_LIST_SENT) {
						processUpdateUserList(response);
					} else if (response.getCode() == ResponseCode.NEW_USER_CONNECTED) {
						processNewUserConnected(response);
					} else if (response.getCode() == ResponseCode.NEW_MESSAGE) {
						processNewMessageRecieved(response);
					} else if (response.getCode() == ResponseCode.USER_DISCONNECTED) {
						processUserDisconnected(response);
					}
				} catch (ClassNotFoundException | IOException e) {
					isRunning = false;
					e.printStackTrace();
				}
			}
		}

		private void processUserDisconnected(Response response) {
			chatwindow.updateConversation(response.getName(), " has disconnected.");
			if (SideBar.this.listmodel.contains(response.getName()))
				SideBar.this.listmodel.removeElement(response.getName());
		}

		private void processNewMessageRecieved(Response response) {
			if (!chatwindow.isVisible())
				chatwindow.setVisible(true);
			chatwindow.updateConversation(response.getName(), ": " + response.getMessage());
		}

		private void processNewUserConnected(Response response) {
			SideBar.this.listmodel.addElement(response.getName());
		}

		private void processUpdateUserList(Response response) {
			Vector<String> users = response.getUserList();
			for (String user : users) {
				if (!SideBar.this.listmodel.contains(user))
					SideBar.this.listmodel.addElement(user);
			}
		}
	}

}
