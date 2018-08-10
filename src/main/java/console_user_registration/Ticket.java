package console_user_registration;

import java.util.LinkedList;
import java.util.List;

public class Ticket {
    private long id;
    private long userId;
    private String message;
    private List<Comment> answersList;
    private String subject;

    public String getSubject() {
        return subject;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public List<Comment> getAnswersList() {
        return answersList;
    }

    public Ticket(long id, long userId, String message, String subject){
        this.id = id;
        this.message = message;
        this.userId = userId;
        answersList = new LinkedList<Comment>();
        this.subject = subject;
    }

}
