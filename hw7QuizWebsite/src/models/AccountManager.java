package models;

import java.util.HashMap;

public class AccountManager {
	private HashMap<String, String> accountInfo;
	
	public AccountManager() {
		accountInfo = new HashMap<String, String>();
		accountInfo.put("Patrick", "1234");
		accountInfo.put("Molly", "FloPup");
	}
	
	public static boolean accountExists(String username) {
		//TODO: add db connection
		return true;
		//if(accountInfo.get(username) != null) return true;
		//else return false;
	}
	
	public static boolean passwordCorrect(String username, String password) {
		if(accountExists(username)) {
			//TODO: add db
			return true;
//			String realPassword = accountInfo.get(username);
//			if(realPassword.equals(password)) return true;
		}
		return false;
	}
	
	public void createNewAccount(String username, String password) {
		accountInfo.put(username, password);
	}
}
