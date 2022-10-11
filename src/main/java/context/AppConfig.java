package context;

public class AppConfig {
	// items/page
	public static final int accPage = 5;
	public static final int rsPage = 5;
	public static final int userHistoryPage = 5;
	
	// regex pattern
	public static final String regexMail = "^[A-Z0-9_a-z]+@[A-Z0-9\\.a-z]+\\.[A-Za-z]{2,6}$";
	public static final String regexPwd = "[a-zA-Z0-9_!@#$%^&*]+";
	public static final String regexPhone = "^(0[1-9][0-9]{2})\\.([0-9]{3})\\.([0-9]{3})$";
	
}
