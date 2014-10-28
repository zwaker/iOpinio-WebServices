package au.myjsf.com.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the OPINIONEWS_DEMOGRAPHY database table.
 * 
 */
@Entity
@Table(name="OPINIONEWS_DEMOGRAPHY")
public class OpinionewsDemography implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="ACTIVE_FOR_NEWS")
	private String activeForNews;

	@Column(name="DEMOGRAPHY_ID")
	private String demographyId;

	@Column(name="DEMOGRAPHY_TYPE")
	private String demographyType;

	@Column(name="OPINIONEWS_ID")
	private int opinionewsId;

    public OpinionewsDemography() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getActiveForNews() {
		return this.activeForNews;
	}

	public void setActiveForNews(String activeForNews) {
		this.activeForNews = activeForNews;
	}

	public String getDemographyId() {
		return this.demographyId;
	}

	public void setDemographyId(String demographyId) {
		this.demographyId = demographyId;
	}

	public String getDemographyType() {
		return this.demographyType;
	}

	public void setDemographyType(String demographyType) {
		this.demographyType = demographyType;
	}

	public int getOpinionewsId() {
		return this.opinionewsId;
	}

	public void setOpinionewsId(int opinionewsId) {
		this.opinionewsId = opinionewsId;
	}

}