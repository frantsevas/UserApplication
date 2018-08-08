package console_user_registration;

import java.util.LinkedList;
import java.util.List;

import package_exceptions.InvalidTokenException;
import package_exceptions.InvalidUserDataException;
import package_exceptions.LoginNotUniqueException;
import package_exceptions.UserNotExistException;

public class User_Service_Impl implements User_Service {

	private Token_Service token_service;

	public User_Service_Impl(Token_Service token_service) {
		this.token_service = token_service;
	}

	List<User> usersList = new LinkedList<>();
	
	@Override
	public void registrate(String login, String password) throws LoginNotUniqueException
	{
		long lastID;
		User user;
		if(!isLoginUniq(login))
			throw new LoginNotUniqueException();
		if(usersList.isEmpty()) {
			lastID = 0;
			user = new User(login, password, lastID);
		}
		else {
			lastID = ((LinkedList<User>) usersList).getLast().getId();
			user = new User(login, password, ++lastID);
		}
		usersList.add(user);
		System.out.println("Welcome in system! \nYour login: " + user.getLogin() + " \nYour password: " + user.getPassword() + " \nYour ID: " + user.getId());
	}

	public boolean isLoginUniq(String login)
	{
		for(int i=0; i<usersList.size(); i++) {
			if(usersList.get(i).getLogin().equals(login))
				return false;
		}
		return true;		
	}

	@Override
	public String login(String login, String password) throws InvalidUserDataException {
		for(int i=0; i<usersList.size(); i++) {
			if(usersList.get(i).getLogin().equals(login) && usersList.get(i).getPassword().equals(password)) {
				System.out.println("You have successfully logged in! Here is your token: " + token_service.getToken(usersList.get(i).getId()));
				return token_service.getToken(usersList.get(i).getId());
			}	
		}
		throw new InvalidUserDataException();
	}

	public void getUserInfo(String token) throws InvalidTokenException, UserNotExistException {
		token_service.validToken(token);
		System.out.println("Your login: " + findUserByID(token_service.extractUserID(token)).getLogin() + "\nYour password: " + findUserByID(token_service.extractUserID(token)).getPassword() + "\nYour ID: " + findUserByID(token_service.extractUserID(token)).getId() + "\nYour role: " + findUserByID(token_service.extractUserID(token)).getRole());
	}
	
	public User findUserByID(long UserID) throws UserNotExistException {
		for(int i=0; i<usersList.size(); i++) {
			if(usersList.get(i).getId() == UserID)
				return usersList.get(i);
		}
		throw new UserNotExistException();
	}
}
