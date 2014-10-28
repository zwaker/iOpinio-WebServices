package au.myjsf.com.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the OPINIONEWS_ISSUE database table.
 * 
 */
@Entity
@Table(name="OPINIONEWS_ISSUE")
public class OpinionewsIssue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="ACTIVE_FOR_ISSUE")
	private String activeForIssue;

	@Column(name="ISSUE_ID")
	private int issueId;

	@Column(name="OPINIONEWS_ID")
	private int opinionewsId;

    public OpinionewsIssue() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActiveForIssue() {
		return this.activeForIssue;
	}

	public void setActiveForIssue(String activeForIssue) {
		this.activeForIssue = activeForIssue;
	}

	public int getIssueId() {
		return this.issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public int getOpinionewsId() {
		return this.opinionewsId;
	}

	public void setOpinionewsId(int opinionewsId) {
		this.opinionewsId = opinionewsId;
	}

}