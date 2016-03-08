package models;

import java.util.ArrayList;

public class Quiz {
	private String name;
	private String description;
	private int creator_id; 
	private int quiz_id;
	private ArrayList<Question> questions;

	public Quiz(String name, String descrip, int creator, int quiz) {
		this.name = name;
		description = descrip;
		creator_id = creator;
		quiz_id = quiz;
	}
	
	public int getQuizId() {
		return quiz_id;
	}
	
	public String getName() {
		return name;
	}
	
	
	

}
