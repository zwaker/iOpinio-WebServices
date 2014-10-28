package au.myjsf.com.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the Vote_OldItem_V1 database table.
 * 
 */
@Entity
public class Vote_OldItem_V1 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private Timestamp datetime;

	private String name;

	private String nCount;

	private String yCount;

    public Vote_OldItem_V1() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDatetime() {
		return this.datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
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