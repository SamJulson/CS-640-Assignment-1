import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidParameterException;

public class Server implements Runnable {
	
	private int listenPort;
	private byte[] zeroes;
	
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
			long startTime = System.currentTimeMillis();
			int kbytesRecieved = 0;
			while (!clientSocket.isClosed()) {
				in.read(zeroes);
				kbytesRecieved++;
			}
			long endTime = System.currentTimeMillis();
			in.close();
			clientSocket.close();
			serverSocket.close();
			System.out.printf("sent=%d KB rate=%1.3d\n", kbytesRecieved, kbytesRecieved / (double) (endTime - startTime));
		} catch (IOException e) {
			throw new RuntimeException("Socket was unable to be created.");
		}
	}
}
