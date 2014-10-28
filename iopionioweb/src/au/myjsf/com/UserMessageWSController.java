package au.myjsf.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import au.myjsf.com.entity.Usermessage;
import au.myjsf.com.entity.Vote_OldItem_V1;

@Named
@RequestScoped
public class UserMessageWSController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UserMessageService userMessageService;

	public UserMessageService getUserMessageService() {
		return userMessageService;
	}

	public void setUserMessageService(UserMessageService userMessageService) {
		this.userMessageService = userMessageService;
	}

	public JSONArray processUserMessageList() {
		List<UsermessageBean> umbList = new ArrayList<UsermessageBean>();

		List<Usermessage> userMessageList = userMessageService
				.retrieveUserMessages();
		List<JSONObject> umbAsJsonInList = new ArrayList<JSONObject>();

		for (Usermessage um : userMessageList) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String today = sdf.format(Calendar.getInstance().getTime());
			UsermessageBean umb = new UsermessageBean();
			umb.setId(new Integer(um.getId()).toString());
			umb.setMessage(um.getMessage());
			umb.setUserEnteredTime(um.getUserEnteredTime());
			umb.setUsername(um.getUsername());
			JSONObject umAsJsonObject = JSONObject.fromObject(umb);

			umAsJsonObject.accumulate("date", today);
			umbAsJsonInList.add(umAsJsonObject);

		}
		JSONArray umbAsJsonInArray = JSONArray.fromObject(umbAsJsonInList);
		return umbAsJsonInArray;

	}

	public String respondToRequest() {
		HttpServletRequest httpServletRequest = ((HttpServletRequest) (FacesContext
				.getCurrentInstance().getExternalContext().getRequest()));
		String messageType = (String) httpServletRequest
				.getParameter("messageType");
		if (messageType.equalsIgnoreCase("read")) {
			retrieveMessages();

		} else if (messageType.equalsIgnoreCase("add")) {

			saveUserMessage(httpServletRequest);
		}
		return "";
	}

	public void saveUserMessage(HttpServletRequest httpServletRequest) {

		String username = (String) httpServletRequest.getParameter("username");
		String userMessage = (String) httpServletRequest
				.getParameter("message");
		String messageEnteredTime = (String) httpServletRequest
				.getParameter("userEnteredTime");
		Usermessage um = new Usermessage();
		java.util.Date dt = Calendar.getInstance().getTime();
		java.sql.Date sdt = new java.sql.Date(dt.getTime());
		java.sql.Timestamp ts = new java.sql.Timestamp(sdt.getTime());
		um.setTransactionTime(ts);
		um.setMessage(userMessage);
		um.setTransactionTime(ts);
		um.setUserEnteredTime(messageEnteredTime);
		if(username.equals("") || username==null)
			username="guest";
		um.setUsername(username);
		userMessageService.saveUserMessage(um);

	}

	public void retrieveMessages() {
		Map rootJsonMap = new HashMap();
		String jsonObjectAsString = "";
		rootJsonMap.put("userMessages", processUserMessageList());

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

	}

}
