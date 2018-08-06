package console_user_registration;

public class User {
	private long Id;
	private String login;
	private String password;
	public User(String login, String password,long Id)
	{
		this.login = login;
		this.password = password;
		this.Id = Id;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		this.Id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
