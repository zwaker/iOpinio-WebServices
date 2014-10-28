package au.myjsf.com.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the continents database table.
 * 
 */
@Entity
public class Continents implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String code;

	private String name;

	//bi-directional many-to-one association to Countries
	@OneToMany(mappedBy="continent")
	private Set<Countries> countries;

    public Continents() {
    }

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Countries> getCountries() {
		return this.countries;
	}

	public void setCountries(Set<Countries> countries) {
		this.countries = countries;
	}
	
}