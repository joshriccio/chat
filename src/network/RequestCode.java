package network;

/**
 * Request codes used to communicate with the server
 * 
 * @author Joshua Riccio
 *
 */
public enum RequestCode {
	CONNECT(0), SEND_MESSAGE(1), EXITING(2), REQUEST_USERS_ONLINE(3), NEW_ACCOUNT(4), GET_SALT(5);

	RequestCode(int requestCode) {
	}

}
