package au.myjsf.com.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the USERMESSAGE database table.
 * 
 */
@Entity
public class Usermessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String message;

	@Column(name="transaction_time")
	private Timestamp transactionTime;

	@Column(name="user_entered_time")
	private String userEnteredTime;

	private String username;

    public Usermessage() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getTransactionTime() {
		return this.transactionTime;
	}

	public void setTransactionTime(Timestamp transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getUserEnteredTime() {
		return this.userEnteredTime;
	}

	public void setUserEnteredTime(String userEnteredTime) {
		this.userEnteredTime = userEnteredTime;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}