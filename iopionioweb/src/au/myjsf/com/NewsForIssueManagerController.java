package au.myjsf.com;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import au.myjsf.com.entity.Opinionews;
import au.myjsf.com.entity.OpinionewsDemography;
import au.myjsf.com.entity.OpinionewsIssue;


@Named
@RequestScoped
public class NewsForIssueManagerController implements Serializable {

	private static final long serialVersionUID = 1L;

private Opinionews news=new Opinionews();
private OpinionewsIssue opinionewsIssue=new OpinionewsIssue();




public Opinionews getNews() {
	return news;
}

public void setNews(Opinionews news) {
	this.news = news;
}


public OpinionewsIssue getOpinionewsIssue() {
	return opinionewsIssue;
}

public void setOpinionewsIssue(OpinionewsIssue opinionewsIssue) {
	this.opinionewsIssue = opinionewsIssue;
}

public UserService getUserService() {
	return userService;
}

public void setUserService(UserService userService) {
	this.userService = userService;
}
@Inject
private UserService userService;
private java.lang.String hello="ttttttHIS IS GOOD";
@PostConstruct
public void loadFromDB(){
	userService.retrieveNewsForCurrentIssue();
	
}

public void addNewRecord(){
	/*iintelRecordItem=new IintelRecordItem();
	iintelRecords.setId(0);
	iintelDashboard.setId(0);
	miscelleneous.setId(0);
	joke.setId(0);
	healthTip.setId(0);
	recipe.setId(0);
	iintelNews.setId(0);
	photoStand.setId(0);
	videoTube.setId(0);*/
	
	//setIintelRecordsId("");
	//saveIintelProcessAll();
}

public void clear(){
	/*iintelRecordItem=new IintelRecordItem();
	setIintelRecordsId("");
	iintelRecords=new IintelRecords();
	phrase=new Phrase();
	miscelleneous=new Miscellaneous();
	joke=new Joke();
	healthTip=new HealthTip();
	recipe = new Recipe();
	iintelNews=new Iintelnews();
	photoStand=new Photostand();
	videoTube=new Videotube();
	iintelDashboard=new IintelDashboard();*/
}


public String saveOrUpdate(){
	//loadFromDB();
	saveNewsForIssue(news,opinionewsIssue);
	return null;
	
	
}

public String saveNewsForIssue(Opinionews news,OpinionewsIssue opinionewsIssue){
	userService.saveNewsForIssue(news, opinionewsIssue);
	//IintelRecordItem iintelRecordItem=iintelMangerService.saveIintelProcess(iintelRecords,iintelDashboard,miscelleneous, healthTip, joke, recipe, photoStand, videoTube, iintelNews);
	//setIintelRecordsId(new Integer(iintelRecordItem.getIintelRecordsId()).toString());
	return null;
	
}


}
