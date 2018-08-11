package console_user_registration;

public class Comment{
    private String login;
    private String message;

    public Comment(String login, String message){
        this.login = login;
        this.message = message;
    }
    public String getLogin() { return login;}
    public String getMessage() { return message;}

    public String toString() {
        return login + ": " + message;
    }
}


