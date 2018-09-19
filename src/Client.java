import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.Arrays;

public class Client implements Runnable {
	private String serverHostname;
	private int serverPort;
	private int timeInSeconds;
	private byte[] zeroes;
	
	public Client(String serverHostname, int serverPort,  int timeInSeconds) {
		if (serverPort < Constants.MIN_PORT || serverPort > Constants.MAX_PORT) {
			throw new InvalidParameterException(Error.INVALID_PORT_NUMBER);
		}
		this.serverHostname = serverHostname;
		this.serverPort = serverPort;
		this.timeInSeconds = timeInSeconds;
		this.zeroes = new byte[Constants.BYTES_PER_KILOBYTE];
		Arrays.fill(zeroes, (byte)0);
	}
	
	public void run() {
		try(
			Socket clientSocket = new Socket(serverHostname, serverPort);
			OutputStream out = clientSocket.getOutputStream();
		) {
			int kbytesSent = 0;
			long startTime = System.currentTimeMillis();
			while (System.currentTimeMillis() - startTime < timeInSeconds * 1000) {
				out.write(zeroes);
				out.flush();
				kbytesSent++;
			}
			long endTime = System.currentTimeMillis();
			out.close();
			clientSocket.close();
			System.out.printf(
				"sent=%d KB rate=%1.3f Mbps\n", 
				kbytesSent, 
				(kbytesSent / Constants.KILOBYTES_PER_MEGABYTE * Constants.BITS_PER_BYTE) / 
				(((double) endTime - startTime ) / Constants.MILLISECONDS_PER_SECOND)
			);
		} catch (IOException e) {
			throw new RuntimeException("Socket was unable to be created.");
		}
	}
}