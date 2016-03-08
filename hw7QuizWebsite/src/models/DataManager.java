package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

public class DataManager {
	private HashMap<Integer, ArrayList<Integer>> friend_list;
	private HashMap<Integer, ArrayList<Integer>> quiz_creation_list;
	private HashMap<Integer, ArrayList<QuizStatistic>> quiz_stats;
	private HashMap<Integer, Quiz> quiz_list;
	private HashMap<String, User> user_list;
	private HashMap<Integer, Message> message_list;
	private HashMap<String, String> passwords;
	private HashMap<Integer, ArrayList<Integer>> sent_messages;
	private HashMap<Integer, ArrayList<Integer>> recieved_messages;
	private DBConnection con;
	public static User DEFAULT_USER = new User("0", 0);
	
	public DataManager() {
		friend_list = new HashMap<>();
		quiz_creation_list = new HashMap<>();
		quiz_list = new HashMap<>();
		user_list = new HashMap<>();
		message_list = new HashMap<>();
		quiz_stats = new HashMap<>();
		sent_messages = new HashMap<>();
		recieved_messages = new HashMap<>();
		passwords = new HashMap<>();
		con = new DBConnection();
	}
	
	public int getNextId() {
		ResultSet rs = con.grabTable("ids");
		int id = 9999;
		try {
			rs.next();
			id = rs.getInt(1);
			con.updateTable("ids", "current_max_id=" + id, "current_max_id=" + (id + 1));
		} catch (Exception e) {
		}
		return ++id;
	}
	
	// User id -> List of Id's for Quiz's by user
   public ArrayList<Integer> getCreatedQuizes(int id) {
		return quiz_creation_list.get(id);
	}
	// Add Quiz by user_id to the Database
	public void addQuiz(int user_id, Quiz quiz) {
		quiz_creation_list.get(user_id).add(quiz.getQuizId());
		quiz_list.put(quiz.getQuizId(), quiz);
		quiz_stats.put(quiz.getQuizId(), new ArrayList<>());
	}
	
	// Quiz_id -> Quiz associated with this Id
	public Quiz getQuiz(int id) {
		return quiz_list.get(id);
	}
	
	// Adds a user, associated with the hashed password
	public void addUser(User user, String pw_hash) {
		user_list.put(user.getUsername(), user);
		initUserLists(user.getId());
		con.addToTable("accounts", getUserValues(user));
		passwords.put(user.getUsername(), pw_hash);
		con.addToTable("passwords", "\"" + user.getUsername() + "\",\"" + pw_hash + "\"");
	}
	
	// Username -> User Account
	public User getUser(String username) {
		return user_list.get(username);
	}
	
	// User_id -> User Account
	public User getUser(int id) {
		return user_list.get(idToUsername(id));
	}
	
	// Check if password is associated with an account
	public boolean checkPassword(String username, String pw_hash) {
		if (user_list.get(username) == null) return false;
		return passwords.get(username).equals(pw_hash);
	}
	
	// Add a friend
	public void addFriend(int user1, int user2) {
		friend_list.get(user1).add(user2);
		friend_list.get(user2).add(user1);
	}
	
	//Check to see if 2 accounts are friends
	public boolean isFriend(int user1, int user2) {
		ArrayList<Integer> friend = friend_list.get(user1);
		return friend.contains(user2);
	}
	
	// Returns all the friends of a suer
	public ArrayList<Integer> getFriends(int user_id) {
		if(friend_list.containsKey(user_id)) return friend_list.get(user_id);
		else return null;
	}
	
	public ArrayList<QuizStatistic> getAllUserQuizStats(int userid) {
		ArrayList<QuizStatistic> userQuizStats = new ArrayList<QuizStatistic>(); 
		ArrayList<Quiz> allQuizzes = getQuizzes();
		for(Quiz quiz: allQuizzes) {
			QuizStatistic stat = getQuizStats(userid, quiz.getQuizId());
			if(stat != null) userQuizStats.add(stat);
		}
		if(!userQuizStats.isEmpty()) return userQuizStats;
		else return null;
	}
	
	//returns at most 5 most recent quizzes taken
	public ArrayList<QuizStatistic> getRecentUserQuizStats(int userid) {
		ArrayList<QuizStatistic> userQuizStats = getAllUserQuizStats(userid);
		ArrayList<QuizStatistic> recentQuizStats = new ArrayList<QuizStatistic>();
		if(userQuizStats != null) {
			for(QuizStatistic stat : userQuizStats) {
				if(recentQuizStats.size() < 5) {
					recentQuizStats.add(stat);
					Collections.sort(recentQuizStats);
				} else {
					if(recentQuizStats.get(4).getDate().before(stat.getDate())) {
						recentQuizStats.remove(4);
						recentQuizStats.add(stat);
						Collections.sort(recentQuizStats);
					}
				}
			}
		}
		if(recentQuizStats.isEmpty()) return null;
		return recentQuizStats;
	}
	
	// Returns Quiz Stats for a given user on a given quiz
	public QuizStatistic getQuizStats(int userid, int quizid) {
		ArrayList<QuizStatistic> stats = quiz_stats.get(quizid);
		for (QuizStatistic q : stats) {
			if(q.getUserId() == userid) {
				return q;
			}
		}
		return null;
	}
	
	// Adds the given quiz_stats to database
	public void addQuizStats(QuizStatistic stats) {
		quiz_stats.get(stats.getQuizId()).add(stats);
		con.addToTable("stats", getStatsValues(stats));
	}
	
	// Gets all created Quizes
	public ArrayList<Quiz> getQuizzes() {
		ArrayList<Quiz> quizzes = new ArrayList<>();
		for (int i : quiz_list.keySet()) {
			quizzes.add(quiz_list.get(i));
		}
		return quizzes;
	}
	
	public ArrayList<Quiz> getRecentQuizzes() {
		ArrayList<Quiz> allQuizzes = getQuizzes();
		ArrayList<Quiz> recentQuizzes = new ArrayList<Quiz>();
		for(Quiz quiz : allQuizzes) {
			if(recentQuizzes.size() < 5) {
				recentQuizzes.add(quiz);
				// TODO: implement sorting quizzes by date
				//Collections.sort(recentQuizzes);
			} else {
//				if(recentQuizzes.get(4).getDate().before(stat.getDate())) {
//					recentQuizzes.remove(4);
//					recentQuizzes.add(quiz);
//					Collections.sort(recentQuizzes);
//				}
			}
		}
		if(recentQuizzes.isEmpty()) return null;
		return recentQuizzes;
	}
	
	// Gets all available users
	public ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<>();
		for (String s : user_list.keySet()) {
			users.add(user_list.get(s));
		}
		return users;
	}
	
	public ArrayList<User> searchUsers(String searchString) {
		ArrayList<User> allUsers = getUsers();
		ArrayList<User> searchResults = new ArrayList<User>();
		for(User user: allUsers) {
			if(user.getUsername().contains(searchString)) searchResults.add(user);
		}
		if(searchResults.isEmpty()) return null;
		else return searchResults;
	}
	
	// Retrieves a message based on its ID
	public Message getMessage(int message_id) {
		return message_list.get(message_id);
	}
	
	// Adds a message to the database
	public void addMessage(Message message) {
		message_list.put(message.getMessageId(), message);
		sent_messages.get(message.getSenderId()).add(message.getMessageId());
		recieved_messages.get(message.getRecpientId()).add(message.getMessageId());
		con.addToTable("messages", getMessageValues(message));
	}
	
	/*
	 * TODO: saving friends
	 * delete messages
	 * remove friends
	 */
	
	public ArrayList<Message> getUserMessages(int userid) {
		ArrayList<Integer> receivedMessageIds = recieved_messages.get(userid);
		ArrayList<Message> receivedMessages = new ArrayList<Message>();
		if(receivedMessageIds != null) {
			for(Integer messageId : receivedMessageIds) {
			receivedMessages.add(getMessage(messageId));
			}
		}
		return receivedMessages;
	}
	
	// Updates Database object
	public void update() {
		useAccountData(con.grabTable("accounts"));
		usePasswordData(con.grabTable("passwords"));
		//useQuizData(con.grabTable("quizes"));
		useStatsData(con.grabTable("stats"));
		useMessageData(con.grabTable("messages"));
	}
	
	// Hash a password
	public static String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			return hexToString(md.digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	private String getUserValues(User user) {
		StringBuffer values = new StringBuffer(100);
		values.append(user.getId());
		values.append(",\"" + user.getUsername() + "\",\"");
		for (int i : friend_list.get(user.getId())) {
			values.append(i + ",");
		}
		values.append("\",\"");
		for (int i : quiz_creation_list.get(user.getId())) {
			values.append(i + ",");
		}
		values.append("\"");
		return values.toString();
	}
	
	private void initUserLists(int user_id) {
		friend_list.put(user_id, new ArrayList<>());
		quiz_creation_list.put(user_id, new ArrayList<>());
		sent_messages.put(user_id, new ArrayList<>());
		recieved_messages.put(user_id, new ArrayList<>());
	}
	
	
	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	private String idToUsername(int id) {
		ArrayList<User> users = getUsers();
		for (User u : users) {
			if (u.getId() == id) return u.getUsername();
		}
		return null;
	}
	
	private void useAccountData(ResultSet rs) {
		try {
			while (rs.next()) {
				User user = new User(rs.getString(2), rs.getInt(1));
				user_list.put(rs.getString(2), user);
				initUserLists(user.getId());
				String friends = rs.getString(3);
				parseFriendsString(friends, rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void parseFriendsString(String list, int user_id) {
		String delim = ",";
		ArrayList<Integer> friends = friend_list.get(user_id);
		StringTokenizer st = new StringTokenizer(list, delim);
		while (st.hasMoreTokens()) {
			friends.add(Integer.parseInt(st.nextToken()));
		}
	}
	
	private void usePasswordData(ResultSet rs) {
		try {
			while (rs.next()) {
				passwords.put(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void useQuizData(ResultSet rs) {
		try {
			while (rs.next()) {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void useStatsData(ResultSet rs) {
		try {
			while (rs.next()) {
				QuizStatistic stat = new QuizStatistic(rs.getInt(2), rs.getInt(1), rs.getTimestamp(4), rs.getLong(5), rs.getInt(3));
				quiz_stats.get(stat.getQuizId()).add(stat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getStatsValues(QuizStatistic stats) {
		StringBuffer value = new StringBuffer(100);
		value.append(
				stats.getQuizId() + "," + stats.getQuizId() + "," + stats.getScore()
				+ "," + stats.getDate() + ",\"" + stats.getTime() + "\"");
		return value.toString();
	}
	
	
	private void useMessageData(ResultSet rs) {
		try {
			while (rs.next()) {
				Message message;
				int type = rs.getInt(4);
				switch (type) {
					case 0: message = new Message(rs.getInt(3), rs.getInt(2), rs.getString(5), rs.getInt(1));
							break;
					case 1: message = new Message(rs.getInt(3), rs.getInt(2), rs.getInt(5), rs.getInt(1));
							break;
					case 2: message = new Message(rs.getInt(3), rs.getInt(2), rs.getInt(1));
							break;
					default: message = null;
				}
				message_list.put(message.getMessageId(), message);
				sent_messages.get(message.getSenderId()).add(message.getMessageId());
				recieved_messages.get(message.getRecpientId()).add(message.getMessageId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getMessageValues(Message message) {
		StringBuffer value = new StringBuffer(100);
		value.append(
				message.getMessageId() + "," + message.getSenderId() + "," + message.getRecpientId() 
				+ "," + message.getType() + ",\"" + message.getBody() + "\"");
		return value.toString();
	}
}
