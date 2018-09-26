import java.security.InvalidParameterException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Iperfer {
	
	private static final String CLIENT_OPTION = "c";
	private static final String SERVER_OPTION = "s";
	private static final String HOST_OPTION = "h";
	private static final String PORT_OPTION = "p";
	private static final String TIME_OPTION = "t";
	
	public static void main(String[] args) {
		Options options = new Options();
		options.addOption(CLIENT_OPTION, false, "Starts the program in client mode");
		options.addOption(SERVER_OPTION, false, "Starts the program in server mode");
		options.addOption(HOST_OPTION, true, "Specifies the server host name. Client only.");
		options.addOption(PORT_OPTION, true, "Specifies the port number. Must be in between 1024 and 65535.");
		options.addOption(TIME_OPTION, true, "Specifies the time in seconds to transmit. Client only.");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			Error.exitError(Error.INVALID_OR_MISSING_ARGUMENTS);
		}
		
		if (cmd.hasOption(CLIENT_OPTION) && cmd.hasOption(SERVER_OPTION)) {
			Error.exitError(Error.INVALID_OR_MISSING_ARGUMENTS);
		}
		
		if (!cmd.hasOption(PORT_OPTION)) {
			Error.exitError(Error.INVALID_OR_MISSING_ARGUMENTS);
		}
		
		if (!cmd.hasOption(SERVER_OPTION) && (!cmd.hasOption(TIME_OPTION) || !cmd.hasOption(HOST_OPTION))) {
			Error.exitError(Error.INVALID_OR_MISSING_ARGUMENTS);
		}
		
		boolean isServer = false;
		int port = 0;
		String hostname = null;
		int timeInSeconds = 0;
		
		isServer = false || cmd.hasOption(SERVER_OPTION);
		hostname = cmd.getOptionValue(HOST_OPTION);
		
		try {
			port = Integer.parseInt(cmd.getOptionValue(PORT_OPTION));
			if (cmd.hasOption(TIME_OPTION)) {
				timeInSeconds = Integer.parseInt(cmd.getOptionValue(TIME_OPTION));
			}
		} catch (NumberFormatException e) {
			Error.exitError(Error.INVALID_OR_MISSING_ARGUMENTS);
		}
		
		Runnable program = null;
		
		try {		
			if (isServer) {
				program = new Server(port);
			} else {
				program = new Client(hostname, port, timeInSeconds);
			}
		} catch (InvalidParameterException e) {
			Error.exitError(Error.INVALID_PORT_NUMBER);
		}
		
		program.run();
		System.exit(0);
	}
}
