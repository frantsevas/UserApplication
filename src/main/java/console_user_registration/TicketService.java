package console_user_registration;

import package_exceptions.InvalidTokenException;
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
    public void createTicket(String token, String message) throws InvalidTokenException {
        token_service.validToken(token);
        if(ticketsList.isEmpty()) {
            id = 0;
            ticket = new Ticket(id, token_service.extractUserID(token), message);
        }
        else {
                id = ((LinkedList<Ticket>) ticketsList).getLast().getId();
                ticket = new Ticket(id, token_service.extractUserID(token), message);
            }
            ticketsList.add(ticket);
    }

    public void addComment(String token, String message) throws InvalidTokenException, UserNotExistException {
        token_service.validToken(token);
        UserRole creatorRole = user_service_impl.findUserByID(ticket.getUserId()).getRole();
        UserRole curRole = user_service_impl.findUserByID(token_service.extractUserID(token)).getRole();
        if(curRole == UserRole.Admin || curRole == UserRole.Support || curRole == creatorRole) {
            Comment comment = new Comment(token_service.extractUserID(token), message);
            for (int i = 0; i < ticketsList.size(); i++) {
                if (ticketsList.get(i).getId() == id)
                    ticketsList.get(i).getAnswersList().add(comment);
            }
        }
    }
}
