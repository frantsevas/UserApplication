package console_user_registration;

import package_exceptions.LoginNotUniqueException;
import package_exceptions.InvalidUserDataException;

public interface User_Service {
	public void registrate(String login, String password) throws LoginNotUniqueException;
	public String login(String login, String password) throws InvalidUserDataException;
}
