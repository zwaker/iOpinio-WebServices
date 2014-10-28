/**
 * 
 */
package au.myjsf.com;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.criterion.Property;

import au.myjsf.com.entity.Countries;
import au.myjsf.com.entity.Opinionews;
import au.myjsf.com.entity.OpinionewsDemography;
import au.myjsf.com.entity.OpinionewsIssue;
import au.myjsf.com.entity.VoteItem;
import au.myjsf.com.entity.VoteOlditem;
import au.myjsf.com.entity.Vote_OldItem_V1;

/**
 * @author ArShaN
 * 
 */
@LocalBean
@Stateful
@Named
@RequestScoped
public class UserService {

	/* private User user = new User(); */
	private VoteItem voteItem = new VoteItem();
	private Vote_OldItem_V1 voteOldItem = new Vote_OldItem_V1();

	/*
	 * public User getUser() { return user; }
	 * 
	 * public void setUser(User user) { this.user = user; }
	 */

	public VoteItem getVoteItem() {
		return voteItem;
	}

	public void setVoteItem(VoteItem voteItem) {
		this.voteItem = voteItem;
	}

	public VoteItem processVoteItem() {

		return em.find(VoteItem.class, 2);

	}

	public VoteOlditem retrieveOldVoteItem() {

		return em.find(VoteOlditem.class, 3);

	}

	public void saveVoteItem(VoteItem voteItemToSave) {
		VoteItem vi = em.find(VoteItem.class, 2);
		vi.setNcount(voteItemToSave.getNcount());
		vi.setYcount(voteItemToSave.getYcount());
		em.persist(vi);
	}

	public Vote_OldItem_V1 getVoteOldItem() {
		return voteOldItem;
	}

	public void setVoteOldItem(Vote_OldItem_V1 voteOldItem) {
		this.voteOldItem = voteOldItem;
	}

	@PersistenceContext(unitName = "mypersistence")
	private EntityManager em;

	public void saveNewOpinion(String newOpinion) {
		VoteItem existingVoteItem = em.find(VoteItem.class, 2);
		VoteOlditem oldVoteItem = retrieveOldVoteItem();
		oldVoteItem.setName(existingVoteItem.getName());
		oldVoteItem.setNCount(existingVoteItem.getNcount());
		oldVoteItem.setYCount(existingVoteItem.getYcount());
		em.persist(oldVoteItem);
		
		Vote_OldItem_V1 oldVoteItemArchive = new Vote_OldItem_V1();
		oldVoteItemArchive.setName(existingVoteItem.getName());
		oldVoteItemArchive.setNCount(existingVoteItem.getNcount());
		oldVoteItemArchive.setYCount(existingVoteItem.getYcount());
		em.persist(oldVoteItemArchive);
		existingVoteItem.setName(newOpinion);
		existingVoteItem.setNcount("0");
		existingVoteItem.setYcount("0");
		em.persist(existingVoteItem);
	}

	public Opinionews retrieveWorldNews() {
		Session session = ((org.hibernate.ejb.EntityManagerImpl) em
				.getDelegate()).getSession();

		List<OpinionewsDemography> itemList = session
				.createCriteria(OpinionewsDemography.class)
				.add(Property.forName("demographyType").eq("CONTINENT")
						.ignoreCase())
				.add(Property.forName("demographyId").eq("WL")
						.ignoreCase())
				.add(Property.forName("activeForNews").eq("Y").ignoreCase())
				.list();
		OpinionewsDemography opinionewsDemography = null;
		if (!itemList.isEmpty()) {
			opinionewsDemography = itemList.get(0);
		}
		Opinionews worldNews = em.find(Opinionews.class,
				opinionewsDemography.getOpinionewsId());
		return worldNews;

	}

	public Opinionews retrieveNewsByUserContinent(String countryName) {
		// IintelRecordItem iintelRecordItem=em.find(IintelRecordItem.class,
		// id);
		Opinionews continentNews = null;
		Session session = ((org.hibernate.ejb.EntityManagerImpl) em
				.getDelegate()).getSession();
		Countries country = searchCountryByCountryName(countryName);
		if (country != null) {
			List<OpinionewsDemography> itemList = session
					.createCriteria(OpinionewsDemography.class)
					.add(Property
							.forName("demographyId")
							.eq(searchCountryByCountryName(countryName)
									.getContinent().getCode()).ignoreCase())
					.add(Property.forName("demographyType").eq("CONTINENT")
							.ignoreCase())
					.add(Property.forName("activeForNews").eq("Y").ignoreCase())
					.list();
			OpinionewsDemography opinionewsDemography = null;
			if (!itemList.isEmpty()) {
				opinionewsDemography = itemList.get(0);
				continentNews = em.find(Opinionews.class,
						opinionewsDemography.getOpinionewsId());
			}
			
		}
		return continentNews;
	}

	public Countries searchCountryByCountryName(String countryName) {
		Session session = ((org.hibernate.ejb.EntityManagerImpl) em
				.getDelegate()).getSession();

		List<Countries> countriesList = session.createCriteria(Countries.class)
				.add(Property.forName("name").eq(countryName).ignoreCase())
				.list();
		Countries country = null;
		if (!countriesList.isEmpty()) {
			country = countriesList.get(0);
		}
		return country;
	}

	public List<Vote_OldItem_V1> retrieveOldOpinionItems() {
		// IintelRecordItem iintelRecordItem=em.find(IintelRecordItem.class,
		// id);
		Session session = ((org.hibernate.ejb.EntityManagerImpl) em
				.getDelegate()).getSession();

		List<Vote_OldItem_V1> itemList = session
				.createCriteria(Vote_OldItem_V1.class).setMaxResults(10)
				.addOrder(Property.forName("id").desc())
				.addOrder(Property.forName("datetime").desc()).list();

		return itemList;
	}

	public Opinionews retrieveNewsForCurrentIssue() {
		// IintelRecordItem iintelRecordItem=em.find(IintelRecordItem.class,
		// id);
		Opinionews newsForIssue = null;
		Session session = ((org.hibernate.ejb.EntityManagerImpl) em
				.getDelegate()).getSession();

		List<OpinionewsIssue> itemList = session
				.createCriteria(OpinionewsIssue.class)
				.add(Property.forName("activeForIssue").eq("Y"))
				.list();

		OpinionewsIssue opinionewsIssue = null;
		if (!itemList.isEmpty()) {
			opinionewsIssue = itemList.get(0);
		}
		newsForIssue = em.find(Opinionews.class,
				opinionewsIssue.getOpinionewsId());
		return newsForIssue;
	}
	public void saveNews(Opinionews opinionews,
			OpinionewsDemography opinionewsDemography) {
		// TODO Auto-generated method stub
		// make the previous one inactive
		Session session = ((org.hibernate.ejb.EntityManagerImpl) em
				.getDelegate()).getSession();

		List<OpinionewsDemography> itemList = session
				.createCriteria(OpinionewsDemography.class)
				.add(Property.forName("demographyId")
						.eq(opinionewsDemography.getDemographyId())
						.ignoreCase())
				.add(Property.forName("demographyType").eq("CONTINENT")
						.ignoreCase())
				.add(Property.forName("activeForNews").eq("Y").ignoreCase())
				.list();
		if (!itemList.isEmpty()) {
			OpinionewsDemography oldOpinionewsDemographybyDemography = itemList
					.get(0);
			oldOpinionewsDemographybyDemography.setActiveForNews("N");
		}
		Opinionews savedOpinionews = saveOpinionews(opinionews);
		opinionewsDemography.setOpinionewsId(savedOpinionews.getId());
		opinionewsDemography.setActiveForNews("Y");
		saveOpinionewsDemography(opinionewsDemography);
	}

	public Opinionews saveOpinionews(Opinionews opinionews) {
		opinionews = em.merge(opinionews);
		return opinionews;
	}

	public OpinionewsDemography saveOpinionewsDemography(
			OpinionewsDemography opinionewsDemography) {
		opinionewsDemography = em.merge(opinionewsDemography);
		return opinionewsDemography;
	}

	public void saveNewsForIssue(Opinionews opinionews,
			OpinionewsIssue opinionewsIssue) {
		Session session = ((org.hibernate.ejb.EntityManagerImpl) em
				.getDelegate()).getSession();

		List<OpinionewsIssue> itemList = session
				.createCriteria(OpinionewsIssue.class)
				.add(Property.forName("activeForIssue").eq("Y").ignoreCase())
				.list();
		if (!itemList.isEmpty()) {
			OpinionewsIssue oldOpinionewsIssue = itemList.get(0);
			oldOpinionewsIssue.setActiveForIssue("N");
		}
		Opinionews savedOpinionews = saveOpinionews(opinionews);
		opinionewsIssue.setOpinionewsId(savedOpinionews.getId());
		opinionewsIssue.setActiveForIssue("Y");
		//we always have one issue record in our vote item table, hence the following hard code value!!!
		opinionewsIssue.setIssueId(2);
		saveOpinionewsIssue(opinionewsIssue);
	}

	public OpinionewsIssue saveOpinionewsIssue(OpinionewsIssue opinionewsIssue) {
		opinionewsIssue = em.merge(opinionewsIssue);
		return opinionewsIssue;
	}

}
