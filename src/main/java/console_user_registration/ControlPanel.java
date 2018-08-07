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
	private Session session = new Session();
	
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
						instruction();
						break;
					}	
					case 2: {
						System.out.println("Enter login:");
						String userLog = in.next();
						System.out.println("Enter password:");
						String userPass = in.next();
						session.saveToken(uS.login(userLog, userPass));
						instruction();
						break;
					}
					case 3: {
						System.out.print("Your token:");
						System.out.println(session.getCurrentToken());
						uS.getUserInfo(session.getCurrentToken());
						instruction();
						break;
					}
					case 4: {
						optionForum();
					}
					case 5: {
						session.clearSessionData();
						System.out.println("Session data were cleaned");
						instruction();
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
		System.out.println("Available commands : \n1 - registration \n2 - login \n3 - info about user \n4 - option Forum \n5 - clear session data \nenter command:");
	}

	public void instruction2() {
		System.out.println("Available commands : \n1 - Add comment \n2 - View all comments \n3 - Back \nenter command:");
	}

	public void optionForum() throws InvalidTokenException, UserNotExistException {
		instruction2();
		while(true) {
			try {
				int com = Integer.valueOf(in.next());
				in.skip("\n");
				switch (com) {
					case 1: {
						System.out.println("Enter comment:");
						String comment = in.nextLine();
						System.out.print("Your token:");
						System.out.println(session.getCurrentToken());
						forum.addComment(comment, session.getCurrentToken());
						System.out.println("Comment is added");
						instruction2();
						break;
					}
					case 2: {
						forum.printAll();
						instruction2();
						break;
					}
					case 3: {
						instruction();
						return;
					}
				}
			}
			catch (IOException e){
					System.out.println(e.getClass());
					instruction2();
			}
		}
	}
}
