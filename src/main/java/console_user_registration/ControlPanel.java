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

	public void start() {
		creatingAdmin();
				while(true) {	
					try {
					instruction();
					int command = Integer.valueOf(in.next());
					in.skip("\n");
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
						session.saveToken(uS.login(userLog, userPass));
						break;
					}
					case 3: {
						System.out.print("Your token:");
						System.out.println(session.getCurrentToken());
						uS.getUserInfo(session.getCurrentToken());
						break;
					}
					case 4: {
						optionForum();
						break;
					}
					case 5: {
						session.clearSessionData();
						System.out.println("Session data were cleaned");
						break;
					}
					case 6: {
						optionTicket();
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
		System.out.println("Available commands : \n1 - registration \n2 - login \n3 - info about user \n4 - option Forum \n5 - clear session data \n6 - option Ticket \nenter command:");
	}

	public void instruction2() {
		System.out.println("Available commands : \n1 - Add comment \n2 - View all comments \n3 - Back \nenter command:");
	}

	public void instruction3() {
		System.out.println("Available commands : \n1 - Create ticket \n2 - Add comment \n3 - Print all tickets \n4 - Read ticket \n5 - Back \nenter command:");
	}

	public void optionForum() {
		while(true) {
			try {
				instruction2();
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
			catch (IOException e){
					System.out.println(e.getClass());
					instruction2();
			}
		}
	}

	public void optionTicket() {
		while(true) {
			try {
				instruction3();
				int com = Integer.valueOf(in.next());
				in.skip("\n");
				switch (com) {
					case 1: {
						System.out.println("Enter subject:");
						String subject = in.nextLine();
						System.out.println("Enter message:");
						String message = in.nextLine();
						ticketService.createTicket(session.getCurrentToken(), message, subject);
						System.out.println("Ticket is created");
						break;
					}
					case 2: {
						System.out.println("Enter comment:");
						String message = in.nextLine();
						System.out.println("Enter ticket id:");
						long ticketId = in.nextLong();
						ticketService.addComment(session.getCurrentToken(), ticketId, message);
						System.out.println("Comment is added");
						break;
					}
					case 3: {
						ticketService.printAllTickets();
						break;
					}
					case 4: {
						System.out.println("Enter ticketID:");
						long id = in.nextLong();
						ticketService.readTicket(id);
						break;
					}
					case 5: {
						return;
					}
				}
			}
			catch (IOException e){
				System.out.println(e.getClass());
			}
		}
	}

	public void creatingAdmin(){
		User admin = new User();
		admin.setRole(UserRole.Admin);
		admin.setId(0);
		admin.setLogin("admin");
		admin.setPassword("admin");
		uS.usersList.add(admin);
	}
}
