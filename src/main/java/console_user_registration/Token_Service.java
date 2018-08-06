package console_user_registration;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;
import package_exceptions.InvalidTokenException;

public class Token_Service {
	
	List<Token> tokensCollection = new LinkedList<>();
	
	public String getToken(long userID) {
		for(int i=0; i<tokensCollection.size(); i++) {
			if(tokensCollection.get(i).getUserID()==userID)
				return tokensCollection.get(i).getToken();		
		}
		Token token = new Token(userID, userID + "_" + generateHash());
		tokensCollection.add(token);
		return token.getToken();			       
	}
	
	public String generateHash() {
		int length = 2;
		boolean useLetters = true;
		boolean useNumbers = false;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		return generatedString;
	}
	
	public void validToken(String token) throws InvalidTokenException {
		for(int i=0; i<tokensCollection.size(); i++) {
			if(tokensCollection.get(i).getToken().equals(token)) {
				//System.out.println("token is valid");
				return;
				}
		}
		throw new InvalidTokenException();
	}
	
	public long extractUserID(String token) {
		return Long.valueOf(token.substring(0, token.indexOf('_')));
	}
}