import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidParameterException;

public class Server implements Runnable {
	
	private int listenPort;
	private byte[] zeroes;
	
	public Server(int listenPort) {
		if (listenPort < Constants.MIN_PORT || listenPort > Constants.MAX_PORT) {
			throw new InvalidParameterException(Error.INVALID_PORT_NUMBER);
		}
		this.listenPort = listenPort;
		zeroes = new byte[Constants.BYTES_PER_KILOBYTE];
	}

	public void run() {
		try (
			ServerSocket serverSocket = new ServerSocket(listenPort);
			Socket clientSocket = serverSocket.accept();
			InputStream in = clientSocket.getInputStream();
		) {
			int bytesRead;
			int bytesRecieved = 0;
			while (!clientSocket.isConnected()) {
				;
			}
			long startTime = System.currentTimeMillis();
			while ((bytesRead = in.read(zeroes)) != -1) {
				bytesRecieved += bytesRead;
			}
			long endTime = System.currentTimeMillis();
			in.close();
			serverSocket.close();
			System.out.printf(
				"recieved=%d KB rate=%1.3f Mbps\n", 
				bytesRecieved / Constants.BYTES_PER_KILOBYTE, 
				(bytesRecieved / (Constants.KILOBYTES_PER_MEGABYTE * Constants.BYTES_PER_KILOBYTE) * Constants.BITS_PER_BYTE) /
				(((double) endTime - startTime ) / Constants.MILLISECONDS_PER_SECOND)
			);
		} catch (IOException e) {
			throw new RuntimeException("Socket was unable to be created.");
		}
	}
}