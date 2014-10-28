package au.myjsf.com.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the OPINIONEWS database table.
 * 
 */
@Entity
public class Opinionews implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

    @Lob()
	@Column(name="DEFINITION_DETAILS")
	private String definitionDetails;

	private String heading;

	@Column(name="IMAGE_URL")
	private String imageUrl;

	private String source;

	@Column(name="SOURCE_URL")
	private String sourceUrl;

    public Opinionews() {
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDefinitionDetails() {
		return this.definitionDetails;
	}

	public void setDefinitionDetails(String definitionDetails) {
		this.definitionDetails = definitionDetails;
	}

	public String getHeading() {
		return this.heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceUrl() {
		return this.sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

}