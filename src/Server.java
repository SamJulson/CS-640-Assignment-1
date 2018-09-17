import java.security.InvalidParameterException;

public class Server implements Runnable {
	
	private int listenPort;
	
	public Server(int listenPort) {
		if (listenPort < 1024 || listenPort > 65535) {
			throw new InvalidParameterException(Error.INVALID_PORT_NUMBER);
		}
		this.listenPort = listenPort;
	}

	public void run() {
		// TODO: Implement Server side
	}
}
