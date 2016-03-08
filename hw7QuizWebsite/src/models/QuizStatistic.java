package models;

import java.sql.Timestamp;

public class QuizStatistic implements Comparable{
	int quiz_id;
	int user_id;
	int score;
	Timestamp date;
	long time;
	
	public QuizStatistic(int quiz, int user, Timestamp date, long time, int score) {
		quiz_id = quiz;
		user_id = user;
		this.date = date;
		this.time = time;
		this.score = score;
	}
	
	public void setQuizId(int quizid) {
		quiz_id = quizid;
	}
	
	public int getQuizId() {
		return quiz_id;
	}
	
	public void setUserId(int userid) {
		user_id = userid;
	}
	
	public int getUserId() {
		return user_id;
	}
	
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	public Timestamp getDate() {
		return date;
	}
	
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getTime() {
		return time;
	}
	
	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(Object o) {
		Timestamp d = ((QuizStatistic) o).getDate();
		if(d.after(this.getDate())) return 1;
		else return -1;
	}
}
