package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class User {
	private String username;
	private int id;
	HashMap<String, ArrayList<QuizStatistic>> quizzesTaken;
	
	public User(String name, int id) {
		username = name;
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public int getId() {
		return id;
	}
	
	
	
}
