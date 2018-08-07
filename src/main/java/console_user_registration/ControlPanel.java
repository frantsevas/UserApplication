package console_user_registration;

import java.io.IOException;
import java.util.Scanner;

import package_exceptions.InvalidTokenException;
import package_exceptions.InvalidUserDataException;
import package_exceptions.LoginNotUniqueException;
import package_exceptions.UserNotExistException;

public class ControlPanel {

	private Token_Service tS = new Token_Service();
	private User_Service_Impl uS = new User_Service_Impl(tS);
	private Forum forum = new Forum(uS, tS);
	
	Scanner in = new Scanner(System.in);
	
	public void start() throws LoginNotUniqueException, InvalidUserDataException, InvalidTokenException, UserNotExistException {
		instruction();
				while(true) {	
					try {
					int command = Integer.valueOf(in.next());
					if(command == -1)
					{
						System.out.println("The end");
						break;
					}
					switch(command)
					{
					case 1: {
						System.out.println("Enter login:");
						String userLog = in.next();
						System.out.println("Enter password:");
						String userPass = in.next();
						uS.registrate(userLog, userPass);
						break;
					}	
					case 2: {
						System.out.println("Enter login:");
						String userLog = in.next();
						System.out.println("Enter password:");
						String userPass = in.next();
						uS.login(userLog, userPass);
						break;
					}
					case 3: {
						System.out.println("Enter token:");
						String userToken = in.next();
						uS.getUserInfo(userToken);
						break;
					}
					case 4: {
						optionForum();
					}
					case 0: {
						instruction();
						break;
					}
					}
				}
		
		catch (IOException e){
			System.out.println(e.getClass());
			System.out.println("Enter command:");
		}
	}
	}
	
	public void instruction() {
		System.out.println("Available commands : \n1 - registration \n2 - login \n3 - info about user \n0 - info about commands \n4 - option Forum \nenter command:");
	}

	public void optionForum() throws InvalidTokenException, UserNotExistException {
		System.out.println("Available commands : \n1 - Add comment \n2 - View all comments \n3 - Back \nenter command:");
		while(true) {
			int com = Integer.valueOf(in.next());
			switch(com) {
				case 1: {
					System.out.println("Enter comment:");
					String comment = in.next();
					System.out.println("Enter token:");
					String tok = in.next();
					forum.addComment(comment, tok);
					System.out.println("Comment is added");
					break;
				}
				case 2: {
					forum.printAll();
					break;
				}
				case 3: {
					return;
				}
			}
		}
	}
}
