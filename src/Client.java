import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Arrays;

public class Client implements Runnable {
	private String serverHostname;
	private int serverPort;
	private int timeInSeconds;
	private byte[] zeroes;
	
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
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
		) {
			long startTime = System.currentTimeMillis();
			int kbytesSent = 0;
			while (System.currentTimeMillis() - startTime < timeInSeconds * 1000) {
				out.print(zeroes);
				out.flush();
				kbytesSent++;
			}
			out.close();
			clientSocket.close();
			System.out.printf("sent=%d KB rate=%1.3d\n", kbytesSent, kbytesSent / (double) timeInSeconds);
		} catch (IOException e) {
			throw new RuntimeException("Socket was unable to be created.");
		}
	}
}