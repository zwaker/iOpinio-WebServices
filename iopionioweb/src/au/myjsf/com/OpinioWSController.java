package au.myjsf.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import au.myjsf.com.entity.Opinionews;
import au.myjsf.com.entity.VoteItem;
import au.myjsf.com.entity.Vote_OldItem_V1;

@Named
@RequestScoped
public class OpinioWSController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public JSONObject processVoteItem() {
		JSONObject irAsJsonObject=null;
		HttpServletRequest httpServletRequest = ((HttpServletRequest) (FacesContext
				.getCurrentInstance().getExternalContext().getRequest()));
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		String answer = (String) httpServletRequest.getParameter("answer");
		String voteResult = (String) httpServletRequest
				.getParameter("voteResult");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateWithoutTime = null;
		try {
			dateWithoutTime = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		VoteItem vi = getUserService().processVoteItem();

		if (vi != null) {
			Integer yCount = new Integer(vi.getYcount());
			Integer nCount = new Integer(vi.getNcount());

			if (answer != null && !answer.isEmpty()) {
				if (answer.equals("YES")) {
					yCount++;
					vi.setYcount(yCount.toString());
				} else if (answer.equals("NO")) {
					nCount++;
					vi.setNcount(nCount.toString());
				}
				userService.saveVoteItem(vi);
			}
			String yResult = "0";
			String nResult = "0";
			if (!(yCount == 0 && nCount == 0)) {
				yResult = roundTwoDecimals(
						new Double(
								(new Double(yCount) / (new Double(yCount) + new Double(
										nCount))) * 100)).toString();
				nResult = roundTwoDecimals(
						new Double(
								(new Double(nCount) / (new Double(yCount) + new Double(
										nCount))) * 100)).toString();
			}
			String publishVoteResult = vi.getName() + "," + yResult + ","
					+ nResult + ",";
			IssueResponse ir = new IssueResponse();
			ir.setIssue(vi.getName());
			ir.setyResult(yResult);
			ir.setnResult(nResult);
			ir.setyCount(yCount.toString());
			ir.setnCount(nCount.toString());
			 irAsJsonObject = JSONObject.fromObject(ir);
		}
		return irAsJsonObject;
	}

	public String respondToRequest() {
		HttpServletRequest httpServletRequest = ((HttpServletRequest) (FacesContext
				.getCurrentInstance().getExternalContext().getRequest()));

		String iintelType = (String) httpServletRequest.getParameter("type");
		String countryName = (String) httpServletRequest
				.getParameter("country");

		Map rootJsonMap = new HashMap();
		String jsonObjectAsString = "";
		/*
		 * if (iintelType.equals(IntelType.PHRASE.getType())) {
		 * phrase=iintelMangerService
		 * .retrievePhrase(iintelRecordItem.getPhraseId());
		 * object=JSONObject.fromObject(phrase);
		 * jsonObjectAsString=object.toString(); }
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String today = sdf.format(Calendar.getInstance().getTime());
		JSONObject currentIssueAsJsonObject=processVoteItem();
		currentIssueAsJsonObject.accumulate("date", today);
		rootJsonMap.put("currentIssue", currentIssueAsJsonObject);
		Opinionews newsByContinent = userService
				.retrieveNewsByUserContinent(countryName);
		if(newsByContinent!=null){
		JSONObject newsByContinentAsJsonObject = JSONObject
				.fromObject(newsByContinent);
		newsByContinentAsJsonObject.accumulate("date", today);
		rootJsonMap.put("newsByContinent", newsByContinentAsJsonObject);
		}
		Opinionews currentIssueNews = userService.retrieveNewsForCurrentIssue();
		if(currentIssueNews!=null){
		JSONObject currentIssueNewsAsJsonObject = JSONObject
				.fromObject(currentIssueNews);
		currentIssueNewsAsJsonObject.accumulate("date", today);
		rootJsonMap.put("currentIssueNews", currentIssueNewsAsJsonObject);
		}
		List<Vote_OldItem_V1> oldOpinionItemsList = userService
				.retrieveOldOpinionItems();
		List<JSONObject> irOld=new ArrayList<JSONObject>();
		for (Vote_OldItem_V1 oldVoteItem : oldOpinionItemsList) {	
			JSONObject oldIssueAsJsonObject=retrieveOldVoteItemInPercentAsString(oldVoteItem);
			oldIssueAsJsonObject.accumulate("date", today);
			irOld.add(oldIssueAsJsonObject);
		}
		JSONArray oldOpinionItemsListAsJsonArray = JSONArray
				.fromObject(irOld);
		rootJsonMap.put("oldOpinions", oldOpinionItemsListAsJsonArray);

		Opinionews worldNews = userService.retrieveWorldNews();
		if(worldNews!=null){
		JSONObject worldNewsAsJsonObject = JSONObject
				.fromObject(worldNews);
		worldNewsAsJsonObject.accumulate("date", today);
		rootJsonMap.put("worldNews", worldNewsAsJsonObject);
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.putAll(rootJsonMap);
		jsonObjectAsString = "" + jsonObj.toString() + "";

		HttpServletResponse httpServletResponse = ((HttpServletResponse) (FacesContext
				.getCurrentInstance().getExternalContext().getResponse()));
		httpServletResponse.setContentType("text/json");
		PrintWriter out = null;
		try {
			out = httpServletResponse.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		out.println(jsonObjectAsString);
		FacesContext.getCurrentInstance().responseComplete();
		return "";

	}

	private JSONObject retrieveOldVoteItemInPercentAsString(Vote_OldItem_V1 vi) {
		JSONObject irAsJsonObject=null;
		
		String publishVoteResult = "";
		if (vi != null) {
			Integer yCount = new Integer(vi.getYCount());
			Integer nCount = new Integer(vi.getNCount());
			String yResult = "0";
			String nResult = "0";
			if (!(yCount == 0 && nCount == 0)) {
				yResult = roundTwoDecimals(
						new Double(
								(new Double(yCount) / (new Double(yCount) + new Double(
										nCount))) * 100)).toString();
				nResult = roundTwoDecimals(
						new Double(
								(new Double(nCount) / (new Double(yCount) + new Double(
										nCount))) * 100)).toString();
			}
			publishVoteResult = vi.getName() + "," + yResult + "," + nResult
					+ ",";
			IssueResponse ir = new IssueResponse();
			ir.setIssue(vi.getName());
			ir.setyResult(yResult);
			ir.setnResult(nResult);
			ir.setyCount(yCount.toString());
			ir.setnCount(nCount.toString());
			 irAsJsonObject = JSONObject.fromObject(ir);
		}
		
		
		return irAsJsonObject;

	}

	private Double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
}
