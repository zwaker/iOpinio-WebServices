package au.myjsf.com.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the countries database table.
 * 
 */
@Entity
public class Countries implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String code;

	@Column(name="full_name")
	private String fullName;

	private String iso3;

	private String name;

	private int number;

	//bi-directional many-to-one association to Continents
    @ManyToOne
	private Continents continent;

    public Countries() {
    }

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIso3() {
		return this.iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Continents getContinent() {
		return this.continent;
	}

	public void setContinent(Continents continent) {
		this.continent = continent;
	}
	
}