/**
 * 
 */
package au.myjsf.com;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author ArShaN
 * 
 */

@Named
@RequestScoped
public class NewOpinionController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String newOpinion="";
	private String password="";
	
	@Inject
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@PostConstruct
	public void loadFromDB() {

	}

	
	public String saveNewOpinion() {
		if(password.equals("shantu")){
		userService.saveNewOpinion(getNewOpinion());
		}
		return "newOpinion.xhtml";
	}

	public String getNewOpinion() {
		return newOpinion;
	}

	public void setNewOpinion(String newOpinion) {
		this.newOpinion = newOpinion;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
}
