package console_user_registration;

public class Comment{
    private long userId;
    private String message;
    public Comment(long userId, String message){
        this.userId = userId;
        this.message = message;
    }
    public long getUserID() { return userId;}
    public String getMessage() { return message;}

    public String toString() {
        return message;
    }
}


