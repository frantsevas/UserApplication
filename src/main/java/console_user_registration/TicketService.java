package console_user_registration;

import package_exceptions.InvalidTokenException;
import package_exceptions.NoRightAddCommentException;
import package_exceptions.TicketNotExistException;
import package_exceptions.UserNotExistException;

import java.util.LinkedList;
import java.util.List;

public class TicketService {
    List<Ticket> ticketsList = new LinkedList<>();

    private Token_Service token_service;
    private User_Service_Impl user_service_impl;


    public TicketService(Token_Service token_service, User_Service_Impl user_service_impl) {
        this.token_service = token_service;
        this.user_service_impl = user_service_impl;
    }

    private long id;
    private Ticket ticket;
    private Ticket tempElem;

    public void createTicket(String token, String message, String subject) throws InvalidTokenException {
        token_service.validToken(token);
        id = ticketsList.isEmpty() ? 0 : ((LinkedList<Ticket>) ticketsList).getLast().getId() + 1;
        ticket = new Ticket(id, token_service.extractUserID(token), message, subject);
        ticketsList.add(ticket);
    }

    public void addComment(String token, long ticketId, String message) throws InvalidTokenException, UserNotExistException, NoRightAddCommentException {
        token_service.validToken(token);
        UserRole curRole = user_service_impl.findUserByID(token_service.extractUserID(token)).getRole();
        if (!(curRole == UserRole.Admin || curRole == UserRole.Support || ticket.getUserId() == token_service.extractUserID(token)))
            throw new NoRightAddCommentException();
        Comment comment = new Comment(user_service_impl.findUserByID(token_service.extractUserID(token)).getLogin(), message);
        for (int i = 0; i < ticketsList.size(); i++) {
            tempElem = ticketsList.get(i);
            if (tempElem.getId() == ticketId) //TODO optimize get (DONE)
                tempElem.getAnswersList().add(comment);
        }
    }

    public void printAllTickets() {
        for(int i = 0; i <  + ticketsList.size();i++)
            System.out.println(ticketsList.get(i).toString());  //TODO override method toString in Ticket class and use him (DONE)
    }

    public void readTicket(long id) throws TicketNotExistException {
        tempElem = findTicketByID(id);
        System.out.println("Ticket id: " + tempElem.getId() + "\nUser id: " + tempElem.getUserId() + "\nSubject: " + tempElem.getSubject() + "\nMessage: " + tempElem.getMessage() + "\nСomments: ");
        for(int i = 0; i <  + tempElem.getAnswersList().size();i++)
            System.out.println(tempElem.getAnswersList().get(i));
    }

    public Ticket findTicketByID(long id) throws TicketNotExistException {
        for(int i=0; i<ticketsList.size(); i++) {
            tempElem = ticketsList.get(i);
            if(tempElem.getId() == id)
                return tempElem;
        }
        throw new TicketNotExistException();
    }


}

