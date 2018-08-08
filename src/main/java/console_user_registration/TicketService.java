package console_user_registration;

import java.util.LinkedList;
import java.util.List;

public class TicketService {
    List<Ticket> ticketsList = new LinkedList<>();

    private Token_Service token_service;

    public TicketService(Token_Service token_service) {
        this.token_service = token_service;
    }

    public void createTicket(String token, String message){
        long id;
        long userId = token_service.extractUserID(token);
        Ticket ticket;
        if(ticketsList.isEmpty()) {
            id = 0;
            ticket = new Ticket(id, userId, message);
        }
        else {
                id = ((LinkedList<Ticket>) ticketsList).getLast().getId();
                ticket = new Ticket(id, userId, message);
            }
            ticketsList.add(ticket);
    }

    public void addComment(String token, String message){

    }
}
