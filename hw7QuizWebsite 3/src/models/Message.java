package models;


public class Message {
	
	public static final int REGULAR = 0;
	public static final int CHALLENGE = 1;
	public static final int REQUEST = 2;
	
	private int type;
	private int message_id;
	private int sender_id;
	private int recipient_id;
	private String body;
	private int quizId;
	
	public Message(int to, int from, String note, int id) {
		type = REGULAR;
		sender_id = from;
		recipient_id = to;
		body = note;
		message_id = id;
	}
	public Message(int to, int from, int quiz_id, int id) {
		type = CHALLENGE;
		sender_id = from;
		recipient_id = to;
		body = "I bet you can't beat my score on this quiz!";
		message_id = id;
		quizId = quiz_id;
	}
	
	public Message(int to, int from, int id) {
		type = REQUEST;
		sender_id = from;
		recipient_id = to;
		body = "I'd like to be your friend!";
		message_id = id;
	}
	
	public int getType() {
		return type;
	}
	
	public int getSenderId() {
		return sender_id;
	}
	
	public int getRecpientId() {
		return recipient_id;
	}
	
	public String getBody() {
		return body;
	}
	
	public int getMessageId() {
		return message_id;
	}
	
	public int getQuizId() {
		return quizId;
	}
}
