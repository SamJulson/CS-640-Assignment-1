import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidParameterException;

public class Server implements Runnable {
	
	private int listenPort;
	private byte[] zeroes;
	
	private static final int KILOB_PER_MEGAB = 1000;
	private static final int BITS_PER_BYTE = 8;
	private static final int MILLIS_PER_SEC = 1000;
	
	public Server(int listenPort) {
		if (listenPort < 1024 || listenPort > 65535) {
			throw new InvalidParameterException(Error.INVALID_PORT_NUMBER);
		}
		this.listenPort = listenPort;
		zeroes = new byte[1000];
	}

	public void run() {
		try (
			ServerSocket serverSocket = new ServerSocket(listenPort);
			Socket clientSocket = serverSocket.accept();
			InputStream in = clientSocket.getInputStream();
		) {
			while (!clientSocket.isConnected()) {
				;
			}
			int kbytesRecieved = 0;
			long startTime = System.currentTimeMillis();
			while (in.read(zeroes) != -1) {
				kbytesRecieved++;
			}
			long endTime = System.currentTimeMillis();
			in.close();
			serverSocket.close();
			System.out.printf(
				"recieved=%d KB rate=%1.3f Mbps\n", 
				kbytesRecieved, 
				(kbytesRecieved / KILOB_PER_MEGAB * BITS_PER_BYTE) / (((double) endTime - startTime ) / MILLIS_PER_SEC)
			);
		} catch (IOException e) {
			throw new RuntimeException("Socket was unable to be created.");
		}
	}
}