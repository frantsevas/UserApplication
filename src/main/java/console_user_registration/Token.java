package console_user_registration;

public class Token {
	private long userID;
	private String token;
	public Token(long userID, String token)
	{
		this.userID = userID;
		this.token = token;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
