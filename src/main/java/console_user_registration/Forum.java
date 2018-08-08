package console_user_registration;

import package_exceptions.InvalidTokenException;
import package_exceptions.UserNotExistException;

import java.util.LinkedList;
import java.util.List;

public class Forum {
    private Token_Service token_service;
    private User_Service_Impl user_service_impl;
    public List<Comment> commentsList = new LinkedList<>();

    public Forum(User_Service_Impl user_service_impl, Token_Service token_service) {
        this.user_service_impl = user_service_impl;
        this.token_service = token_service;
    }

    public void addComment(String text, String token) throws InvalidTokenException{
        token_service.validToken(token);
        Comment comment = new Comment(token_service.extractUserID(token), text);
        commentsList.add(comment);
    }

    public void printAll() throws UserNotExistException {
        if(commentsList.isEmpty())
            System.out.println("There are no comments :(");
        for(int i = 0; i<commentsList.size();i++)
            System.out.println( commentsList.get(i).getUserID() + " " + user_service_impl.findUserByID(commentsList.get(i).getUserID()).getLogin()+ "   " + commentsList.get(i).getMessage());
    }
}