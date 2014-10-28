package au.myjsf.com.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the VoteItem database table.
 * 
 */
@Entity
public class VoteItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String name;

	private String nCount;

	private String yCount;

    public VoteItem() {
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

	public String getNcount() {
		return this.nCount;
	}

	public void setNcount(String nCount) {
		this.nCount = nCount;
	}

	public String getYcount() {
		return this.yCount;
	}

	public void setYcount(String yCount) {
		this.yCount = yCount;
	}

}