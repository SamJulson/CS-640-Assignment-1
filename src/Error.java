public class Error {
	public static String INVALID_OR_MISSING_ARGUMENTS = "Error: missing or additional arguments";
	public static String INVALID_PORT_NUMBER = "Error: port number must be in the range 1024 to 65535";
	
	public static void exitError(Exception e) {
		System.out.println(e.getMessage());
		System.exit(1);
	}
	
}
