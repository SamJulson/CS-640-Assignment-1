import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.security.InvalidParameterException;
import java.util.Arrays;
//Joe was here
public class Client implements Runnable {
	private String serverHostname;
	private int serverPort;
	private int timeInSeconds;
	private byte[] zeroes;
	
	private static final int KILOB_PER_MEGAB = 1000;
	private static final int BITS_PER_BYTE = 8;
	private static final int MILLIS_PER_SEC = 1000;
	
	public Client(String serverHostname, int serverPort,  int timeInSeconds) {
		if (serverPort < 1024 || serverPort > 65535) {
			throw new InvalidParameterException(Error.INVALID_PORT_NUMBER);
		}
		this.serverHostname = serverHostname;
		this.serverPort = serverPort;
		this.timeInSeconds = timeInSeconds;
		this.zeroes = new byte[1000];
		Arrays.fill(zeroes, (byte)0);
	}
	
	public void run() {
		try(
			Socket clientSocket = new Socket(serverHostname, serverPort);
			OutputStream out = clientSocket.getOutputStream();
		) {
			long startTime = System.currentTimeMillis();
			int kbytesSent = 0;
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
				(kbytesSent / KILOB_PER_MEGAB * BITS_PER_BYTE) / (((double) endTime - startTime ) / MILLIS_PER_SEC)
			);
		} catch (IOException e) {
			throw new RuntimeException("Socket was unable to be created.");
		}
	}
}