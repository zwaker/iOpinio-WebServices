/**
 * 
 */
package au.myjsf.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.myjsf.com.entity.VoteItem;
import au.myjsf.com.entity.VoteOlditem;
import au.myjsf.com.entity.Vote_OldItem_V1;

/**
 * @author ArShaN
 * 
 */

@Named
@RequestScoped
public class MyTest implements Serializable {

	private static final long serialVersionUID = 1L;
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

	public String mytest() {

		System.out.println("S");
		return "";
	}

	public void respond() {
		userService.retrieveNewsByUserContinent("Afghanistan");
		userService.retrieveWorldNews();
		List<Vote_OldItem_V1> oldOpinions=userService.retrieveOldOpinionItems();
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

			HttpServletResponse httpServletResponse = ((HttpServletResponse) (FacesContext
					.getCurrentInstance().getExternalContext().getResponse()));
			httpServletResponse.setContentType("text/plain; charset=UTF-8");
			PrintWriter out = null;
			try {
				out = httpServletResponse.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
			out.println(publishVoteResult
					+ retrieveOldVoteItemInPercentAsString());

			FacesContext.getCurrentInstance().responseComplete();
		}
	}

	private String retrieveOldVoteItemInPercentAsString() {
		VoteOlditem vi = getUserService().retrieveOldVoteItem();
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
		}
		return publishVoteResult;

	}

	private Double roundTwoDecimals(double d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(d));
	}
}
