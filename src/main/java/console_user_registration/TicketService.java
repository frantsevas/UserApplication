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

    public void createTicket(String token, String message, String subject) throws InvalidTokenException {
        token_service.validToken(token);
        if (ticketsList.isEmpty()) { //TODO use ternary operator for simplify, if list is empty id = 0 else ...
            id = 0;
            ticket = new Ticket(id, token_service.extractUserID(token), message, subject);
        } else {
            id = ((LinkedList<Ticket>) ticketsList).getLast().getId();
            ticket = new Ticket(++id, token_service.extractUserID(token), message, subject);
        }
        ticketsList.add(ticket);
    }

    public void addComment(String token, String message) throws InvalidTokenException, UserNotExistException, NoRightAddCommentException {
        token_service.validToken(token);
        UserRole curRole = user_service_impl.findUserByID(token_service.extractUserID(token)).getRole();
        if (!(curRole == UserRole.Admin || curRole == UserRole.Support || ticket.getUserId() == token_service.extractUserID(token)))
            throw new NoRightAddCommentException();
        Comment comment = new Comment(token_service.extractUserID(token), message);
        for (int i = 0; i < ticketsList.size(); i++) {
            if (ticketsList.get(i).getId() == id) //TODO optimize get
                ticketsList.get(i).getAnswersList().add(comment);
        }
    }

    public void printAllTickets() {
        Ticket counter;
        for (int i = 0; i < ticketsList.size(); i++) {
            counter = ticketsList.get(i);
            System.out.println("\nTicket id: " + counter.getId() + "   User id: " + counter.getUserId() + "   Subject: " + counter.getSubject() + "\nQuantity of comments: " + counter.getAnswersList().size());
            //TODO override method toString in Ticket class and use him
        }
    }

    public void readTicket(long id) throws TicketNotExistException, UserNotExistException {
        Ticket curTicket = findTicketByID(id);
        System.out.println("Ticket id: " + curTicket.getId() + "\nUser id: " + curTicket.getUserId() + "\nSubject: " + curTicket.getSubject() + "\nMessage: " + curTicket.getMessage() + "\nСomments: ");
        for(int i = 0; i <  + curTicket.getAnswersList().size();i++)
            System.out.println(user_service_impl.findUserByID(curTicket.getUserId()).getLogin() + ": " + curTicket.getAnswersList().get(i));
    }

    public Ticket findTicketByID(long id) throws TicketNotExistException {
        for(int i=0; i<ticketsList.size(); i++) {
            Ticket curEl = ticketsList.get(i);
            if(curEl.getId() == id)
                return curEl;
        }
        throw new TicketNotExistException();
    }


}

