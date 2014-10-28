package au.myjsf.com;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import au.myjsf.com.entity.Usermessage;

public class UsermessageBean {
	
	private String id;

	private String message;
	
	private String userEnteredTime;

	private String username;

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserEnteredTime() {
		return userEnteredTime;
	}

	public void setUserEnteredTime(String userEnteredTime) {
		this.userEnteredTime = userEnteredTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}
