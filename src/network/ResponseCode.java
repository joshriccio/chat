package network;

/**
 * Response codes used to communicate from the server to the client.
 * 
 * @author Joshua Riccio
 *
 */
public enum ResponseCode {
	NEW_MESSAGE(0), SUCCESS(1), USER_DISCONNECTED(2), USERS_LIST_SENT(3), NEW_USER_CONNECTED(4);

	ResponseCode(int value) {

	}

}
