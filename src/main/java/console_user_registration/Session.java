package console_user_registration;

import package_exceptions.CurrentTokenNotExistException;

public class Session {
    private String currentToken;

    public void saveToken(String token){
        currentToken = token;
    }

    public String getCurrentToken() throws CurrentTokenNotExistException {
        if(currentToken == null)
            throw new CurrentTokenNotExistException();
        return currentToken;
    }

    public void clearSessionData(){
        currentToken = null;
    }
}
