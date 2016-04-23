package network;

/**
 * Request codes used to communicate with the server
 * 
 * @author Joshua Riccio
 *
 */
public enum RequestCode {
	CONNECT(0), SEND_MESSAGE(1), EXITING(2);

	RequestCode(int requestCode) {
	}

}
