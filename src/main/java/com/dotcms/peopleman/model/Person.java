package com.dotcms.peopleman.model;

import com.dotmarketing.portlets.contentlet.model.Contentlet;
import com.liferay.portal.model.User;

public class Person {

	private User user = null;
	private Contentlet personContent;

	public String getGender() {

		return (personContent == null) ? null : (String) personContent.getMap().get("gender");
	}

	public void setGender(String gender) {
		personContent.getMap().put("gender", gender);

	}

	public String getTags() {
		return (personContent == null) ? null : (String) personContent.getMap().get("tags");
	}

	public void setTags(String tags) {
		personContent.getMap().put("tags", tags);
	}

	public String getTwitterId() {

		return (personContent == null) ? null : personContent.getMap().get("twitterId").toString();
	}

	public void setTwitterId(String twitterId) {

		personContent.getMap().put("twitterId", twitterId);
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return (personContent == null) ? null : (String) personContent.getMap().get("notes");
	}

	/**
	 * @return the notes
	 */
	public String get(String key) {
		return (personContent == null) ? null : (String) personContent.getMap().get(key);
	}

	/**
	 * @return the notes
	 */
	public void put(String key, Object value) {
		personContent.getMap().put(key, value);
	}

	/**
	 * @param notes
	 *            the notes to set
	 */
	public void setNotes(String notes) {
		personContent.getMap().put("notes", notes);
	}

	/**
	 * @return the personContent
	 */
	public Contentlet getPersonContent() {
		return personContent;
	}

	/**
	 * @param personContent
	 *            the personContent to set
	 */
	public void setPersonContent(Contentlet personContent) {
		this.personContent = personContent;
	}
}
