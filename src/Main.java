public class Main {
	
	private static final String SERVER_FLAG = "-s";
	private static final String CLIENT_FLAG = "-c";
	private static final String HOST_PARAM = "-h";
	private static final String PORT_PARAM = "-p";
	private static final String TIME_PARAM = "-t";
	
	// TODO: Refactor the main function
	public static void main(String[] args) {
		boolean isClient = false;
		int port = 0;
		int timeInSeconds = 0;
		String serverHostname = "";
		
		// For every string in the argument list
		for (int i = 0; i < args.length; i++) {
			// If the string is a...
			switch(args[i]) {
				// server flag
				case SERVER_FLAG:
					// we are a server!
					isClient = false;
					break;
				case CLIENT_FLAG:
					// we are a client!
					isClient = true;
					break;
				// host, port, time flags
				case HOST_PARAM:
				case PORT_PARAM:
				case TIME_PARAM:
					// If the option is available
					if (i + 1 < args.length) {
						// If the string is...
						switch (args[i]) {
							// a host flag
							case HOST_PARAM:
								serverHostname = args[i+1];
							// a port flag
							case PORT_PARAM:
								int portArg = 0;
								
								// Try to parse the port, if we fail we shut down the user.
								try {
									portArg = Integer.parseInt(args[i+1]);
								} catch (NumberFormatException e) {
									Error.exitError(new IllegalArgumentException(Error.INVALID_OR_MISSING_ARGUMENTS));
								}
								
								port = portArg;
								break;
							// a time flag
							case TIME_PARAM:
								
								// try to parse the time, if we fail, we shut down the user
								try {
									timeInSeconds = Integer.parseInt(args[i+1]);
								} catch (NumberFormatException e) {
									Error.exitError(new IllegalArgumentException(Error.INVALID_OR_MISSING_ARGUMENTS));
								}
								break;
							default:
								break;
						}
					} else {
						
					}
					break;
				default:
					break;
			}
		}
		
		Runnable program = null;
		try {
			if (isClient) {
				program = new Client(serverHostname, port, timeInSeconds);
			} else {
				program = new Server(port);
			}
		} catch (Exception e) {
			Error.exitError(new IllegalArgumentException(Error.INVALID_OR_MISSING_ARGUMENTS));
		}
		program.run();
		System.exit(0);
	}
}