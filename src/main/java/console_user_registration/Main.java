package console_user_registration;

import package_exceptions.InvalidTokenException;
import package_exceptions.InvalidUserDataException;
import package_exceptions.LoginNotUniqueException;
import package_exceptions.UserNotExistException;

public class Main {

	public static void main(String[] args) throws LoginNotUniqueException, InvalidUserDataException, InvalidTokenException, UserNotExistException {
		ControlPanel cP = new ControlPanel();
		cP.start();
	}
}
