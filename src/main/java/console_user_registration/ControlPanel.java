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
	private TicketService ticketService = new TicketService(tS, uS);
	
	Scanner in = new Scanner(System.in);

	//TODO delete unused exceptions
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
						instruction(); //TODO extract for avoid duplicate
						break;
					}	
					case 2: {
						System.out.println("Enter login:");
						String userLog = in.next();
						System.out.println("Enter password:");
						String userPass = in.next();
						session.saveToken(uS.login(userLog, userPass));
						instruction(); // duplicate
						break;
					}
					case 3: {
						System.out.print("Your token:");
						System.out.println(session.getCurrentToken());
						uS.getUserInfo(session.getCurrentToken());
						instruction(); // duplicate
						break;
					}
					case 4: {
						optionForum();
					}
					case 5: {
						session.clearSessionData();
						System.out.println("Session data were cleaned");
						instruction(); // duplicate
					}
					case 6: {
						System.out.println("Enter subject:");
						String subject = in.next();
						System.out.println("Enter message:");
						String message = in.next(); //TODO message can be with space like " 121 31 ad", use another method
						ticketService.createTicket(session.getCurrentToken(), message, subject);
						ticketService.printAllTickets();
						break;
					}
					case 7: {
						System.out.println("Enter comment:");
						String message = in.next();
						ticketService.addComment(session.getCurrentToken(), message);
						System.out.println("Enter ticketID:");
						long id = in.nextLong();
						ticketService.readTicket(id);
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
						instruction2(); //TODO
						break;
					}
					case 2: {
						forum.printAll();
						instruction2(); //TODO
						break;
					}
					case 3: {
						instruction(); //TODO
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
