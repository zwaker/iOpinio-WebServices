package au.myjsf.com.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vote_olditem database table.
 * 
 */
@Entity
@Table(name="vote_olditem")
public class VoteOlditem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String name;

	private String nCount;

	private String yCount;

    public VoteOlditem() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNCount() {
		return this.nCount;
	}

	public void setNCount(String nCount) {
		this.nCount = nCount;
	}

	public String getYCount() {
		return this.yCount;
	}

	public void setYCount(String yCount) {
		this.yCount = yCount;
	}

}