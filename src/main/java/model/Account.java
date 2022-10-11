package model;

public class Account {
	private String user_mail;
	private String password;
	private int account_role;
	private String user_name;
	private String phone_number;
	private int user_status;
	// account_role :	1-> admin1
	//					2-> admin2
	//					0-> normal user
	
	// user_status : 	1-> active
	//					0-> not active
	
	public Account() {
		
	}
	
	public Account(String user_mail, String password, int account_role, String user_name, String phone_number, int user_status) {
		this.user_mail = user_mail;
		this.password = password;
		this.account_role = account_role;
		this.user_name = user_name;
		this.phone_number = phone_number;
		this.user_status = user_status;
	}
	
	public String getUser_mail() {
		return user_mail;
	}
	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAccount_role() {
		return account_role;
	}
	public void setAccount_role(int account_role) {
		this.account_role = account_role;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}	
	public String getPhone_number() {
		return phone_number;
	}
	public void setPnone_number(String pnone_number) {
		this.phone_number = pnone_number;
	}
	public int getUser_status() {
		return user_status;
	}
	public void setUser_status(int user_status) {
		this.user_status = user_status;
	}
}
